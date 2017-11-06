<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>确认订单</title>
	<jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include-kanteen.jsp"></jsp:include>
    <link rel="stylesheet" href="media/weixin/kanteen/css/kanteen-order.css?v=3">
    <link rel="stylesheet" href="media/weixin/kanteen/css/kanteen-base.css?v=2">
    <script src="media/weixin/plugins/pushbutton/pushbutton.min.js"></script>
    	
    <!-- <script src="media/weixin/kanteen/js/kanteen-order.js"></script> -->
</head>

<body>

    <form id="canteen-order-information">
        <section class="canteen-user-information-basic">
            <div class="canteen-user-information-basic_list">
                <span class="canteen-user-information-basic_label">领取人</span>
                <input id="receiverName" type="text" value="${receiver.name }" placeholder="请输入姓名">
            </div>
            <div class="canteen-user-information-basic_list">
                <span class="canteen-user-information-basic_label">手机号码</span>
                <input id="receiverContact" tSype="text"  value="${receiver.contact }" placeholder="请输入联系方式">
            </div>
            <div class="canteen-user-information-basic_list">
                <span class="canteen-user-information-basic_label">部门</span>
                <input id="receiverDepart" tSype="text"  value="${receiver.depart }" placeholder="请输入领取人部门">
            </div>
        </section>

        <section class="canteen-user-information-detail">
            <div class="canteen-user-information-basic_important">
                <div class="canteen-user-information-basic_list">
                    <span class="canteen-user-information-basic_label">领取地点</span>
                    <label id="fetchSite" class="canteen-user-infomation-basic_select">请选择领取地点</label>
                </div>
                <div class="canteen-user-information-basic_list">
                    <span class="canteen-user-information-basic_label">领取时间</span>
                    <label id="fetchTime" class="canteen-user-infomation-basic_select">请选择领取时间</label>
                </div>
            </div>
            <div class="canteen-user-information-basic_list">
                <span class="canteen-user-information-basic_label">支付方式</span>
                <label id="payWay" class="canteen-user-infomation-basic_select">请选择支付方式</label>
            </div>
            <div class="canteen-user-information-basic_mark">
                <p class="canteen-user-information-basic__mark_label">买家备注</p>
                <div class="canteen-user-information-basic__mark_textarea">
                    <textarea  id="remark" placeholder="请填写备注（可不写）"></textarea>
                </div>
            </div>
        </section>

        <section class="canteen-order-information">
            <p class="canteen-order-information_title">
                <i class="canteen-order-information_logo"></i>
                <span class="canteen-order-information_logoname">林家食堂</span>
            </p>
            <div class="canteen-order-information_lists">
            	<c:forEach items="${trolley.validWares }" var="wares">
	            	<div class="canteen-order-information_list" data-dwid="${wares.distributionWaresId }" data-count="${wares.count }">
	                    <span class="canteen-order-information_list_name">
	                    	<span class="kanteen-order-wares-option-name">${wares.waresName }</span>
	                    	<span class="kanteen-order-wares-option-desc">${wares.optionDesc }</span>
	                    </span>
	                    <span class="canteen-order-information_list_count canteen-icon canteen-close-icon">${wares.count }</span>
	                    <span class="canteen-order-information_list_price canteen-icon canteen-rmb-icon"><fmt:formatNumber value="${wares.basePrice/100 * wares.count }" pattern="0.00" /> </span>
	                </div>
            	</c:forEach>
                
            </div>
            <p class="canteen-order-information_price">
                <span class="canteen-order-information_price_text">商品总价</span>
                <span class="canteen-order-information_price_totalprice canteen-icon canteen-rmb-icon"><fmt:formatNumber value="${trolley.totalValidPrice/100 }" pattern="0.00" /> </span>
            </p>
        </section>
    </form>

    <footer class="canteen-order-footer">
        <div class="canteen-order-total-price">
            <span class="canteen-order-total-price_text">合计：</span>
            <span class="canteen-order-total-price_price canteen-icon canteen-rmb-icon"><fmt:formatNumber value="${trolley.totalValidPrice/100 }" pattern="0.00" /> </span>
        </div>
        <a href="javascript:;" id="order-submit" class="order-submit">提交订单</a>
    </footer>
    <section id="sitebutton"></section>
    <section id="timebutton"></section>
    <section id="paybutton"></section>
    <script type="text/javascript">
    	$(function(){
    		document.onreadystatechange = function () {
	    		seajs.use(['utils', 'ajax', 'wxconfig'], function(Utils, Ajax, WX){
	    			var selectedDeilvery = null,
	    				selectedPayway = null,
	    				selectedLocationId = null;
	    			
	    			
	   		        var siteDom = document.getElementById("fetchSite");
	   		        var timeDom = document.getElementById("fetchTime");
	   		        var payDom = document.getElementById("payWay");
	   		        
	   		        var locationIdDom = document.getElementById('locationId');
	   		        
	   		        var deliveriesMap = {};
	   		        var locations = [];
	   		        var paywayMap = {
	   		        	'key_1'	: [{
	   		        		text	: '微信支付',
	   		        		key		: 'wxpay'
	   		        	}],
	   		        	'key_2'	: [{
	   		        		text	: '现场支付',
	   		        		key		: 'spot'
	   		        	}],
	   		        	'key_3'	: [{
	   		        		text	: '微信支付',
	   		        		key		: 'wxpay'
	   		        	},{
	   		        		text	: '现场支付',
	   		        		key		: 'spot'
	   		        	}]
	   		        };
	   		        try{
	   		        	var deliveries = $.parseJSON('${deliveriesJson}');
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
	   		        }catch(e){console.error(e);}
	   		        
	   		        var locationSel = null,
	   		        	timeSel = null,
	   		        	paywaySel = null;
	   		        
	   		        
	   		        
	   		        //实例化pushbutton
	   		        locationSel = new Pushbutton('#sitebutton', {
	   		            data	: locations,
	   		            // 点击回调 返回true 则不隐藏弹出框
	   		            onClick: function (e1) {
	   		            	if(!$(e1.target).is('.pushbutton-cancel')){
	   		            		try{
	   		            			if(selectedLocationId != e1.data.locationId){
		   		            			selectedLocationId = e1.data.locationId;
		   		            			selectedDelivery = null;
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
				   		            					if(selectedDelivery != e2.data.delivery){
						   		            				selectedDelivery = e2.data.delivery;
						   		            				selectedPayway = null;
						   		            				paywaySel = null;
						   		            				payDom.innerHTML = '请选择支付方式';
						   		            				
						   		            				timeDom.innerHTML = '<span style="font-size:13px">' + e2.data.text + '</span>';
						   		            				paywaySel = new Pushbutton('#paybutton', {
							   		            					data	: e2.data.payways,
							   		            					onClick	: function(e3){
								   		            					if(!$(e3.target).is('.pushbutton-cancel')){
								   		            						try{
									   		            						selectedPayway = e3.data.key;
								   		            							payDom.innerHTML = e3.data.text;
								   		            						}catch(e){}
								   		            					}
							   		            					}
						   		            				});
				   		            					}
			   		            					}catch(e){}
			   		            				}
			   		            			}
			   		            		});
	   		            			}
	   		            		}catch(e){}
	   		            		
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
	   		        
	   		     	var hasDelivery = '${hasDelivery}' == 'true';
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
			    				Ajax.postJson('weixin/kanteen/confirm_order/${distributionId}', order, function(data){
			    					if(data.status === 'suc'){
			    						if(data.payParameter){
			    							seajs.use(['order/order-pay'], function(OrderPay){
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
			    							});
			    						}else if(data.ordered){
			    							Tips.alert('订单已创建。请在可领取时间段内到指定地点领取。', function(){
			    								window.location.href = 'weixin/kanteen/order_list/week';
			    							});
			    						}
			    					}else{
			    						Tips.alert('订单提交失败');
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
		    			if(selectedDelivery == null){
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
		    			console.log('check-suc');
		    			return {
		    				receiverName	: receiverName,
		    				receiverContact	: receiverContact,
		    				receiverDepart	: receiverDepart,
		    				deliveryId		: selectedDelivery.id,
		    				locationName	: selectedDelivery.locationName,
		    				startTimeStr	: Utils.formatDate(new Date(selectedDelivery.startTime), 'yyyy-MM-dd hh:mm'),
		    				endTimeStr		: Utils.formatDate(new Date(selectedDelivery.endTime), 'yyyy-MM-dd hh:mm'),
		    				paywayName		: $(payDom).text(),
		    				payway			: selectedPayway,
		    				sections		: sections,
		    				totalPrice		: parseFloat('${trolley.totalValidPrice }'),
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
							waresChain += `\${section.waresName}×\${section.count},`;
						}
						waresChain = waresChain && waresChain.substring(0, waresChain.length - 1);
						content +=
								`
								<p><label>商品：</label>\${waresChain}</p>
								<p><label>合计：</label>￥\${order.totalPrice.toFixed(2)}</p>
								<p><label>领取人姓名：</label>\${order.receiverName}</p>
								<p><label>联系方式：</label>\${order.receiverContact}</p> 
								<p><label>部门：</label>\${order.receiverDepart}</p> 
								<p><label>领取地点：</label>\${order.locationName}</p> 
								<p><label>领取开始时间：</label>\${order.startTimeStr}</p>
								<p><label>领取结束时间：</label>\${order.endTimeStr}</p>
								<p><label>付款方式：</label>\${order.paywayName}</p>
								<p><label>备注：</label>\${order.remark}</p>`
						Tips.confirm({
							define: '确认',
							cancel: '取消',
							content: `<div id="order-detail">\${content}</div>`,
							after: function(b){
								if(b){
									callback();
								}
							}
						});
					}	    		
	    		});
	    		
	    		
	    		
   		    }
    	});
    </script>
</body>

</html>