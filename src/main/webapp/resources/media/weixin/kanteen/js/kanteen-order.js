$(function(){
	seajs.use(['utils', 'ajax', 'wxconfig', '$CPF', 'checkbox'], function(Utils, Ajax, WX, $CPF, CCK){
		Utils.bindOrTrigger('whenAttributeInited', function(attrMap){
			var deliveryMethod = null;
			var currentDelivery = {};
			var 
				selectedPayway = null,
				selectedLocationId = null,
				deliveryFee = 0;
			var $price = $('.canteen-order-total-price_price');
			$price.data('originPrice', attrMap.trolleyTotalValidPrice);
			var siteDom = document.getElementById("fetchSite");
			var timeDom = document.getElementById("fetchTime");
			var payDom = document.getElementById("payWay");
			
			
			
			var locationIdDom = document.getElementById('locationId');
			
			var deliveriesMap = {};
			var locations = [];
			var paywayMap = attrMap.paywayMap;
			$CPF.showLoading('mobile');
			var deliveries = [];
			try{
				deliveries = attrMap.deliveriesJson;
				for(var i in deliveries){
					var locationId = deliveries[i].locationId;
					var location = deliveriesMap['lid_' + locationId];
					var data = {
							delivery	: deliveries[i],
							text		: Utils.formatDate(new Date(deliveries[i].startTime), 'yyyy-MM-dd hh:mm')
							+ '~'
							+ Utils.formatDate(new Date(deliveries[i].endTime), 'yyyy-MM-dd hh:mm'),
							payways		: paywayMap['key_' + deliveries[i].payWay]
					};
					if(location){
						var j = 0;
						while(j < location.deliveries.length && deliveries[i].startTime > location.deliveries[j].delivery.startTime) j++;
						location.deliveries.splice(j, 0, data);
					}else{
						locations.push(deliveriesMap['lid_' + locationId] = {
								deliveries	: [data],
								locationId	: locationId,
								text		: deliveries[i].locationName
						});
					}
				}
			}catch(e){
				Tips.alert('页面初始化失败，请尝试刷新页面');
			}finally{
				$CPF.closeLoading();
			}
			
			var locationSel = null,
			timeSel = null,
			paywaySel = null;
			var deliveryTimeButton = null;
			
			function setSelectedDelivery(delivery){
				deliveryFee = 0;
				var originPrice = $price.data('originPrice');
				$price.text(parseFloat(originPrice / 100).toFixed(2))
				if(delivery != null){
					if(deliveryMethod == 'home' && delivery.fee){
						deliveryFee = delivery.fee;
						$price.text(parseFloat((originPrice + delivery.fee) / 100).toFixed(2));
					}
					initPaywayButton(paywayMap['key_' + delivery.payWay])
				}
				currentDelivery[deliveryMethod] = delivery;
			}
			function getSelectedDelivery(){
				return currentDelivery[deliveryMethod];
			}
			var paywayCache = {};
			function initPaywayButton(payways){
				var cachedPayway = paywayCache[deliveryMethod];
				selectedPayway = null;
				paywaySel = null;
				paywayCache[deliveryMethod] = null;
				payDom.innerHTML = '请选择支付方式';
				if(payways){
					if(cachedPayway){
						selectedPayway = cachedPayway;
						payDom.innerHTML = selectedPayway.text;
						paywayCache[deliveryMethod] = cachedPayway;
					}
					paywaySel = new Pushbutton('#paybutton', {
						data	: payways,
						onClick	: function(e3){
							if(!$(e3.target).is('.pushbutton-cancel')){
								try{
									selectedPayway = e3.data.key;
									payDom.innerHTML = e3.data.text;
									paywayCache[deliveryMethod] = e3.data;
								}catch(e){}
							}
						}
					});
				}
			}
			
			try{
				var group = CCK.bind($('[name="deliveryMethod"]'), 0, function(checked){
					checked = checked[0];
					if(checked === 'fixed'){
						//TODO: 定点配送表单
						deliveryMethod = checked;
						$('.delivery-method-fixed').show();
						$('.delivery-method-home').hide();
					}else if(checked === 'home'){
						//TODO: 上门配送表单
						deliveryMethod = checked;
						$('.delivery-method-home').show();
						$('.delivery-method-fixed').hide();
					}
					var delivery = getSelectedDelivery();
					initPaywayButton(delivery? paywayMap['key_' + delivery.payWay]: null);
					$('#delivery-fee-row').hide();
				});
			}catch(e){console.error(e)}
			
			//实例化pushbutton
			locationSel = new Pushbutton('#sitebutton', {
				data	: locations,
				// 点击回调 返回true 则不隐藏弹出框
				onClick: function (e1) {
					if(!$(e1.target).is('.pushbutton-cancel')){
						try{
							if(selectedLocationId != e1.data.locationId){
								selectedLocationId = e1.data.locationId;
								setSelectedDelivery(null);
								selectedPayway = null;
								timeSel = null;
								paywaySel = null;
								timeDom.innerHTML = '请选择领取时间';
								payDom.innerHTML = '请选择支付方式';
								
								siteDom.innerHTML = e1.data.text;
								timeSel = new Pushbutton('#timebutton', {
									data	: e1.data.deliveries,
									onClick	: function(e2){
										if(!$(e2.target).is('.pushbutton-cancel')){
											try{
												timeDom.innerHTML = '<span style="font-size:13px">' + e2.data.text + '</span>';
												setSelectedDelivery(e2.data.delivery);
												initPaywayButton(e2.data.payways);
											}catch(e){}
										}
									}
								});
							}
						}catch(e){console.error(e)}
						
					}
				},
				isShow: false   
			});
			
			siteDom.addEventListener('click', function (e) {
				if(locationSel){
					locationSel.show();
				}
			}, false);
			
			timeDom.addEventListener('click', function (e) {
				if(timeSel){
					timeSel.show();
				}
			}, false);
			payDom.addEventListener('click', function (e) {
				if(paywaySel){
					paywaySel.show();
				}
			}, false);
			
			var hasDelivery = attrMap.hasDelivery;
			if(!hasDelivery){
				Tips.alert('当前没有可用配送');
			}
			$('#order-submit').on('touchend', function(){
				try{
					if(!hasDelivery){
						Tips.alert('当前没有可用配送');
						return false;
					}
					var order = getSubmitOrder();
					showOrderMsg(order, function(){
						$CPF.showLoading('mobile');
						Ajax.postJson('weixin/kanteen/confirm_order/' + attrMap.distributionId, order, function(data){
							if(data.status === 'suc'){
								if(data.payParameter){
									seajs.use(['order/order-pay'], function(OrderPay){
										try{
											OrderPay.doPay('weixin/kanteen/order_paied', data.payParameter, data.orderId, function(){
												//后台支付成功
												Tips.alert({
													content	: '支付成功',
													after	: function(){
														window.location.href = 'weixin/kanteen/order_list/week';
													}
												});
											}, function(){
												//后台支付失败
												Tips.alert({
													content	: '没有支付',
													after	: function(){
														window.location.href = 'weixin/kanteen/order_list/week';
													}
												});
											}, function(){
												//后台支付失败
												Tips.alert({
													content	: '支付已取消',
													after	: function(){
														window.location.href = 'weixin/kanteen/order_list/week';
													}
												});
											});
										}catch(e){
											console.error(e);
											Tips.alert('调用支付接口时发生错误，请点击确定后前往订单列表完成支付');
											window.location.href = 'weixin/kanteen/order_list/week';
										}
									});
								}else if(data.ordered){
									Tips.alert('订单已创建。请在可领取时间段内到指定地点领取。', function(){
										window.location.href = 'weixin/kanteen/order_list/week';
									});
								}
							}else{
								Tips.alert('订单提交失败');
								$CPF.closeLoading();
							}
						});
					});
				}catch(e){
					console.log(e);
				}
				
			});
			
			
			function getSubmitOrder(){
				var receiverName = $('#receiverName').val(),
				receiverContact = $('#receiverContact').val(),
				receiverDepart = $('#receiverDepart').val();
				if(receiverName === ''){
					showErrorMsg('请输入领取人姓名');
				}
				if(!Utils.testContactNumber(receiverContact)){
					showErrorMsg('请输入正确的领取人手机号');
				}
				if(selectedLocationId == null){
					showErrorMsg('请选择领取地点');
				}
				if(getSelectedDelivery() == null){
					showErrorMsg('请选择领取时间');
				}
				if(selectedPayway == null){
					showErrorMsg('请选择支付方式');
				}
				var sections = [];
				$('.canteen-order-information_lists .canteen-order-information_list').each(function(){
					var $this = $(this);
					sections.push({
						distributionWareId	: $this.attr('data-dwid'),
						count				: parseInt($this.attr('data-count')),
						waresName			: $this.find('.canteen-order-information_list_name').text()
					});
				});
				return {
					receiverName	: receiverName,
					receiverContact	: receiverContact,
					receiverDepart	: receiverDepart,
					deliveryId		: getSelectedDelivery().id,
					locationName	: getSelectedDelivery().locationName,
					startTimeStr	: Utils.formatDate(new Date(getSelectedDelivery().startTime), 'yyyy-MM-dd hh:mm'),
					endTimeStr		: Utils.formatDate(new Date(getSelectedDelivery().endTime), 'yyyy-MM-dd hh:mm'),
					paywayName		: $(payDom).text(),
					payway			: selectedPayway,
					sections		: sections,
					totalPrice		: parseFloat(attrMap.trolleyTotalValidPrice + deliveryFee),
					remark			: $('#remark').val()
				};
			}
			function showErrorMsg(msg){
				Tips.alert({
					content: msg,
					define: '知道了'
				});
				$.error(msg);
			}
			function showOrderMsg(order, callback){
				var content = '<h3 style="font-size:1.3em">确认订单</h3>';
				var waresChain = '';
				for(var i in order.sections){
					var section = order.sections[i];
					waresChain += section.waresName + '×' + section.count + ',';
				}
				waresChain = waresChain && waresChain.substring(0, waresChain.length - 1);
				content +=
					'<p><label>商品：</label>' + waresChain + '</p>' + 
					'<p><label>合计：</label>￥' + (order.totalPrice / 100).toFixed(2) + '</p>' + 
					'<p><label>领取人姓名：</label>' + order.receiverName + '</p>' + 
					'<p><label>联系方式：</label>' + order.receiverContact + '</p>' +  
					'<p><label>部门：</label>' + order.receiverDepart + '</p>' + 
					'<p><label>领取地点：</label>' + order.locationName + '</p>' +
					'<p><label>领取开始时间：</label>' + order.startTimeStr + '</p>' +
					'<p><label>领取结束时间：</label>' + order.endTimeStr + '</p>' + 
					'<p><label>付款方式：</label>' + order.paywayName + '</p>' +
					'<p><label>备注：</label>' + order.remark + '</p>';
				Tips.confirm({
					define: '确认',
					cancel: '取消',
					content: '<div id="order-detail">' + content + '</div>',
					after: function(b){
						if(b){
							callback();
						}
					}
				});
			}
			Utils.bindTap($('#homeDeliveryAddress').closest('.delivery-method-home'), function(){
				selectAddress();
			});
			function selectAddress(fn){
				var $container = $('<div class="choose-address-container">')
				$container.append('<div class="choose-address-cover">')
				var $iframe = $('<iframe class="choose-address">').attr('src', 'weixin/amap').appendTo($container);
				$container.appendTo(document.body);
				$iframe[0].onload = function(){
					var win = $iframe[0].contentWindow;
					win.addressSelected = function(tip){
						console.log(tip);
						filterDelivery(tip.location);
						$('#delivery-fee-row').hide();
						$('#homeDeliveryAddress').text(tip.name || '');
						$('#homeDeliveryLongitude').val(tip.location.lng);
						$('#homeDeliveryLatitude').val(tip.location.lat);
						$('#homeDeliveryAddressId').val(tip.id)
						$('#homeDeliveryFullAddress').val(tip.district + '-' + tip.address);
						win.doCanceled();
					}
					win.doCanceled = function(){
						$container.remove();
					}
				};
			}
			
			/**
			 * 根据上门配送地址的坐标筛选配送
			 */
			function filterDelivery(location){
				var arr = [];
				setSelectedDelivery(null);
				$('#deliveryTime').text('请选择配送时间');
				payDom.innerHTML = '请选择支付方式';
				if(location && typeof location.lat == 'number' && typeof location.lng ==='number'){
					for(var i in deliveries){
						var delivery = deliveries[i];
						if(delivery.locationCoordinate && delivery.maxDistance > 0){
							var split = delivery.locationCoordinate.split(',');
							var lng = parseFloat(split[0]),
								lat = parseFloat(split[1]);
							var distance = Utils.distanceBetween({
									lng	: lng,
									lat	: lat
							}, location);
							if(distance <= parseFloat(delivery.maxDistance) / 10 ){
								arr.push({
									delivery	: delivery,
									text		: Utils.formatDate(new Date(delivery.startTime), 'yyyy-MM-dd hh:mm')
												+ '~' 
												+ Utils.formatDate(new Date(delivery.endTime), 'yyyy-MM-dd hh:mm')
								});
							}
						}
					}
				}
				if(arr.length > 0){
					deliveryTimeButton = new Pushbutton('#deliveryTimeButton', {
						data	: arr,
						onClick	: function(e){
							$('#delivery-fee-row').hide();
							$('#deliveryTime').text('请选择配送时间');
							payDom.innerHTML = '请选择支付方式';
							try{
								if(!$(e.target).is('.pushbutton-cancel') && e.data.delivery){
									$('#deliveryTime').html($('<span>').css('font-size', '13px').text(e.data.text));
									if(delivery.fee > 0){
										$('#deliveryFee').text((parseFloat(delivery.fee) / 100).toFixed(2));
									}
									$('#delivery-fee-row').show();
									setSelectedDelivery(e.data.delivery);
								}
							}catch(e){}
						}
					});
				}else{
					Tips.alert('当前送货地址没有可用配送');
				}
			}
			//绑定配送地点的选择框
			$('#deliveryTime').click(function(){
				if(deliveryTimeButton){
					deliveryTimeButton.show();
				}
			});
			
		});
	});
});