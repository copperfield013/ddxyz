<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <title>修改订单</title>
    <jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
    <link href="media/weixin/canteen/css/canteen-order.css" type="text/css" rel="stylesheet">
</head>
<body>
<c:set var="pDelivery" value="${delivery.plainDelivery }" />
<c:if test="${pDelivery != null && overtime }">
	<div class="shade overtime-shade">
		<div>
			<div class="shade-operation">
				<span class="shade-warn">!</span>
			</div>
			<p>不在该订单的可修改时间</p>
			<p class="time-range">
				<fmt:formatDate value="${pDelivery.openTime }" pattern="MM月dd日HH时mm分" />
       			~
       			<fmt:formatDate value="${pDelivery.closeTime }" pattern="MM月dd日HH时mm分" />
			</p>
		</div>
	</div>
</c:if>
<form class="validate" action="index.html" method="post">
<main class="form">
    <!-- 配送信息 -->
    <div class="send-info">
    	<p>
        	<span class="stat-label">领取时间</span>
        	<span class="send-date stat-value">
        		<span><fmt:formatDate value="${delivery.timePointStart }" pattern="MM月dd日HH时mm分"/></span>
        		~
        		<span><fmt:formatDate value="${delivery.timePointEnd }" pattern="MM月dd日HH时mm分"/></span>
        	</span>
        </p>
        <p>
        	<span class="stat-label">领取地址</span>
        	<span class="send-address stat-value">${delivery.locationName }</span>
        </p>
    </div>
    <div class="booking-info">
        <h4>预定信息</h4>
        <p class="layout-flex layout-select">
        	<span class="dishs-kind form-label">菜品</span>
        	<select class="form-input" id="waresName" name="waresName">
        		<c:forEach items="${delivery.waresList }" var="cWares">
        			<option value="${cWares.dWaresId }">${cWares.waresName }</option>
        		</c:forEach>
			</select>
			<label style="color: #666" for="waresName">菜品选择</label>
        </p>
        <p>
        	<span class="form-label">单价</span>
        	<span class="unit-price">20</span><i class="price-unit">元/只</i>
        </p>
        <p>
        	<span class="dishs-count form-label">数量</span>
        	<span class="operate-count">
        		<i class="dishs-minus"></i>
        		<i class="dishCount">1</i>
        		<i class="dishs-plus"></i>
        	</span>
        </p>
        <p class="clearfix remain-row">
        	<span class="remain-container" style="visibility: hidden;">
	        	<span class="surplus-count-label">可供余量：</span>
    	    	<span class="surplus-count"></span>
        	</span>
        	<span class="add">添加</span>
        </p>
        <div class="order-info" id="order-items">
        	<ul class="title-row clearfix">
        		<li class="goods-name">商品名称</li>
        		<li class="goods-count">数量</li>
        		<li class="goods-price">单价</li>
        		<li class="goods-operate">操作</li>
        	</ul>
        </div>
        <p class="total-price">
        	<span>总价：</span><span class="price" id="total-price">0.0</span>
        </p>
    </div>
    <p class="remarks">
    	<span>备注</span>
    	<textarea id="comment" class="input-box" placeholder="请输入备注详情">${order.comment }</textarea>
    </p>
    <!-- 用户信息 -->
    <div class="user-info" style="margin-bottom: 3em;">
        <h4>用户信息</h4>
        <p class="layout-flex">
        	<span class="user-name form-label">姓名</span>
        	<input id="receiverName" class="form-input input-box" type="text" value="${user.name }" placeholder="请输入您的姓名">
        </p>
        <p class="layout-flex">
        	<span class="user-name form-label">部门</span>
        	<input id="depart" class="form-input input-box" type="text" value="${user.depart }" placeholder="请输入您所在部门">
        </p>
        <p class="layout-flex">
        	<span class="user-name form-label">联系方式</span>
        	<input id="contact" class="form-input input-box" type="text" value="${user.contact }" placeholder="请输入您的联系方式">
        </p>
    </div>
   
</main>
</form>
	<footer>
		    <a href="weixin/canteen/order_list" class="order-link">订单</a>
		    <a href="javascript:void(0);" id="submit" class="main">提交订单</a>
	</footer>
	<script type="text/tmpl" id="data-row">
		<ul class="data-row clearfix" data-id="\${dWaresId}">
			<li class="goods-name">\${waresName}</li>
			<li class="goods-count">\${count}</li>
			<li class="goods-price">￥<span>\${unitPrice}</span></li>
			<li class="goods-operate">删除</li>
		</ul>
	</script>
		<script>
		$(function(){
			var delivery = $.parseJSON('${delivery.json}');
			var dWaresCountMap = {};
			
			function getDeliveryWares(deliveryWaresId){
				return delivery.waresMap['id_' + deliveryWaresId];
			}
			function getDeliveryWaresCountInPage(deliveryWaresId){
				return dWaresCountMap['id_' + deliveryWaresId] || 0;
			}
			function addDeliveryWaresCountInPage(deliveryWaresId, count){
				var currCount = getDeliveryWaresCountInPage(deliveryWaresId) || 0;
				dWaresCountMap['id_' + deliveryWaresId] = currCount + parseInt(count);
			}
			
			function refreshRemain(){
				var dWaresId = $('#waresName').val();
				var dWares = getDeliveryWares(dWaresId);
				if(dWares.maxCount && dWares.maxCount > 0){
					$('.surplus-count').text(dWares.maxCount - dWares.currentCount - getDeliveryWaresCountInPage(dWaresId));
					$('.remain-container').css('visibility', 'visible');
				}else{
					$('.remain-container').css('visibility', 'hidden');
				}
			}
			
			$('#waresName').change(function(){
				var deliveryWaresId = $(this).val();
				var wares = getDeliveryWares(deliveryWaresId);
				$('.unit-price').text(parseFloat(wares.price/100).toFixed(2));
				$('.price-unit').text('元/' + wares.priceUnit);
				refreshRemain();
			}).trigger('change');
			
			
			//计算总价
			function calculate(num){
				var totalPrice = 0;
				var presentCount = 0;
				var presentPrice = 0;
				for (var i=0; i< num ; i++){
					presentCount = parseFloat($($('.order-info .data-row')[i]).children('.goods-count').text());
					presentPrice = parseFloat($($('.order-info .data-row')[i]).children('.goods-price').children('span').text());
					totalPrice += presentCount * presentPrice;
				}
				return totalPrice;
			}
			
			//删除事件绑定
			$('#order-items').on('click', '.goods-operate', function(){
				var $dataRow = $(this).closest('.data-row').remove();
				var dWaresId = $dataRow.attr('data-id')
				var count = parseInt($dataRow.find('.goods-count').text());
				
				var num = $('.order-info .data-row').length;
	    		var totalPrice = calculate(num);
	    		$('.price').text(totalPrice);
				addDeliveryWaresCountInPage(dWaresId, -count);
	    		refreshRemain();
				return false;
			});
			
			
			//菜品数量增加减少
			$('.operate-count').on('click','i',function(e){
				e.stopPropagation();
				var _this = $(this).attr("class");
				var $this = $(this);
				var count = parseInt($('.dishCount').text());
				var maxCount = parseInt($('.surplus-count').text()) || 1000;
				if($this.is('.dishs-minus') && count >1){
					count --;
				}else if( $this.is('.dishs-plus') && count < maxCount){
					count ++;
				}
				$('.dishCount').text(count);
			});
			
			function addItem(params){
				params.unitPrice = parseFloat(params.unitPrice / 100).toFixed(2); 
				var $row = $('#data-row').tmpl(params);
				
				$('.order-info').append($row);
				var num = $('.order-info .data-row').length;
	    		totalPrice = calculate(num);
	    		$('.price').text(parseFloat(totalPrice).toFixed(2));
			}
			
			var items = $.parseJSON('${orderItemsJson}');
			
			for(var i in items){
				addItem(items[i]);
			}
			
			//添加菜品到订单事件绑定
			$('.add').on('click',function(){
				var waresName = $('#waresName option:selected').text();
				var dishCount = $('.dishCount').text();
				var unitPrice = $('.unit-price').text();
				var dWaresId = $('#waresName').val();
				var totalPrice = 0;
				
				var dWares = getDeliveryWares(dWaresId);
				if(dWares.maxCount > 0 && dWares.maxCount - dWares.currentCount < getDeliveryWaresCountInPage(dWaresId) + parseInt(dishCount)){
					alert('添加失败，可供余量不足');
					return false;
				}
				
				var $row = $('#data-row').tmpl({
					waresName	: waresName,
					count		: dishCount,
					unitPrice	: unitPrice,
					dWaresId	: dWaresId
				});
				
				$('.order-info').append($row);
				var num = $('.order-info .data-row').length;
	    		totalPrice = calculate(num);
	    		$('.price').text(parseFloat(totalPrice).toFixed(2));
	    		addDeliveryWaresCountInPage(dWaresId, dishCount);
	    		refreshRemain();
	    		return false;
			});
			
			//输入框绑定事件
			$('.input-box').on('focus',function(e){
				e.stopPropagation();
				if (/Android/gi.test(navigator.userAgent)) {
					window.addEventListener('resize', function () {
						if (document.activeElement.tagName == 'INPUT' || document.activeElement.tagName == 'TEXTAREA') {						
								//h5特有属性,webkit专用方法
								document.activeElement.scrollIntoViewIfNeeded();
						}
					})
				}
			});
			
			function dropToBottom(){
				$('main')[0].scrollTop = $('main')[0].scrollHeight;
				var showAlert = setInterval(function(){
					$('.user-info').toggleClass('alert-info');
				}, 500);
				setTimeout(function(){
					$('.user-info').addClass('alert-info')
					clearInterval(showAlert);
				}, 3000);
				
			}
			
			$('#submit').click(function(){
				var receiverName = $('#receiverName').val(),
					depart = $('#depart').val(),
					contact = $('#contact').val(),
					comment = $('#comment').val(),
					totalPrice = $('#total-price').text();
				
				if(!receiverName){
					alert('请输入收货人姓名');
					dropToBottom();
					return false;
				}
				if(!contact){
					alert('请输入联系方式');
					dropToBottom();
					return false;
				}
				var orderItems = [];
				$('#order-items').children('.data-row').each(function(){
					var $this = $(this);
					var dWaresId = $this.attr('data-id'),
						waresId = delivery.waresMap['id_' + dWaresId].waresId,
						count = $this.find('.goods-count').text();
					orderItems.push({
						deliveryWaresId	: dWaresId,
						waresId			: waresId,
						count			: parseInt(count)
					});
				});
				if(orderItems.length == 0){
					alert('请至少添加一个商品后提交');
					return false;
				}
				var parameter = {
						originOrderId: '${order.id}',
						deliveryId	: '${delivery.deliveryId}',
						userId		: '${user.userId}',
						receiverName: receiverName,
						depart		: depart,
						contact		: contact,
						comment		: comment,
						totalPrice	: parseFloat(totalPrice) * 100,
						orderItems	: orderItems
					};
				var orderDetail = 
					'领取人：' + receiverName + '\n' + 
					'联系号码：' + contact + '\n' + 
					'部门：' + depart + '\n' + 
					'总金额：' + totalPrice + '元\n明细：'
					;
				var totalCountMap = {};
				for(var i in orderItems){
					var orderItem = orderItems[i];
					var countItem = totalCountMap['id_' + orderItem.deliveryWaresId];
					if(!countItem){
						countItem = {
							dWares 	: getDeliveryWares(orderItem.deliveryWaresId),
							count 	: orderItem.count
						};
						totalCountMap['id_' + orderItem.deliveryWaresId] = countItem;
					}else{
						countItem.count += orderItem.count;
					}
				}
				for(var i in totalCountMap){
					orderDetail += totalCountMap[i].dWares.waresName + '×' + totalCountMap[i].count + ',';
				}
				orderDetail = orderDetail.substr(0, orderDetail.length - 1);
				
				if(confirm('确认提交订单？\n' + orderDetail)){
					seajs.use(['ajax'], function(Ajax){
						Ajax.postJson('weixin/canteen/doUpdateOrder', parameter, function(data){
							if(data.status === 'suc'){
								alert('订单修改成功');
								location.href = 'weixin/canteen/order_list';
							}else{
								alert('订单修改失败');
							}
						});
					});
				}
			});
		});
	</script>
	
</body>
</html>