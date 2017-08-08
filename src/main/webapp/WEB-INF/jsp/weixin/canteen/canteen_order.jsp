<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <title>下单</title>
    <jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
    <link href="media/weixin/canteen/css/canteen-order.css" type="text/css" rel="stylesheet">
</head>
<body>
<form class="validate" action="index.html" method="post">
<main class="form">
    <!-- 配送信息 -->
    <div class="send-info">
    	
        <p>领取时间<span class="send-date"><fmt:formatDate value="${delivery.timePointStart }" pattern="yyyy-MM-dd"/></span>
        <span class="send-time"><fmt:formatDate value="${delivery.timePointStart }" pattern="HH:mm"/>~<fmt:formatDate value="${delivery.timePointEnd }" pattern="HH:mm"/></span></p>
        <p>领取地址<span class="send-address">${delivery.locationName }</span></p>
    </div>
    <!-- 用户信息 -->
    <div class="user-info">
        <h4>用户信息</h4>
        <p class="layout-flex">
        	<span class="user-name form-label">姓名</span>
        	<input id="receiverName" class="form-input input-box" type="text" value="${userInfo.name }" placeholder="请输入您的姓名">
        </p>
        <p class="layout-flex">
        	<span class="user-name form-label">部门</span>
        	<input id="depart" class="form-input input-box" type="text" value="${userInfo.depart }" placeholder="请输入您所在部门">
        </p>
        <p class="layout-flex">
        	<span class="user-name form-label">联系方式</span>
        	<input id="contact" class="form-input input-box" type="text" value="${userInfo.contact }" placeholder="请输入您的联系方式">
        </p>
    </div>
    <div class="booking-info">
        <h4>预定信息</h4>
        <p class="layout-flex layout-select">
        	<span class="dishs-kind form-label">菜品</span>
        	<select class="form-input" id="waresName">
        		<c:forEach items="${delivery.waresList }" var="cWares">
        			<option value="${cWares.dWaresId }">${cWares.waresName }</option>
        		</c:forEach>
			</select>
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
        	<span class="surplus-count-label">可供余量：</span>
        	<span class="surplus-count">10</span>
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
    	<textarea id="comment" class="input-box" placeholder="请输入备注详情"></textarea>
    </p>
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
			$('#waresName').change(function(){
				var deliveryWaresId = $(this).val();
				var wares = delivery.waresMap['id_' + deliveryWaresId];
				$('.unit-price').text(parseFloat(wares.price/100).toFixed(2));
				$('.price-unit').text(wares.priceUnit);
				if(wares.maxCount && wares.maxCount > 0){
					$('.surplus-count').text(wares.maxCount - wares.currentCount);
					$('.remain-container').show();
				}else{
					$('.remain-container').hide();
				}
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
			function deleteRow(){
				var totalPrice = 0;
				$('.goods-operate').unbind();
				$('.goods-operate').on('click',function(){
					$(this).parent().remove();
					var num = $('.order-info .data-row').length;
		    		totalPrice = calculate(num);
		    		$('.price').text(totalPrice);
				})			
			}
			
			//菜品数量增加减少
			$('.operate-count').on('click','i',function(e){
				e.stopPropagation();
				var _this = $(this).attr("class");
				var count = parseFloat($('.dishCount').text());
				var maxCount = parseFloat($('.surplus-count').text());
				if( _this === 'dishs-minus' && count >1){
					count -=1;
				}else if( _this === 'dishs-plus' && count < maxCount){
					count +=1;
				}
				$('.dishCount').text(count);
			})
			
			//添加菜品到订单事件绑定
			$('.add').on('click',function(){
				var waresName = $('#waresName option:selected').text();
				var dishCount = $('.dishCount').text();
				var unitPrice = $('.unit-price').text();
				var dWaresId = $('#waresName').val();
				var totalPrice = 0;
				
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
	    		deleteRow();
			})
			
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
			
			$('#submit').click(function(){
				var receiverName = $('#receiverName').val(),
					depart = $('#depart').val(),
					contact = $('#contact').val(),
					comment = $('#comment').val(),
					totalPrice = $('#total-price').text();
				
				if(!receiverName){
					alert('请输入收货人姓名');
					return false;
				}
				if(!contact){
					alert('请输入联系方式');
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
						count			: count
					});
				});
				
				var parameter = {
					deliveryId	: '${delivery.deliveryId}',
					userId		: '${user.id}',
					receiverName: receiverName,
					depart		: depart,
					contact		: contact,
					comment		: comment,
					totalPrice	: parseFloat(totalPrice) * 100,
					orderItems	: orderItems
				};
				
				seajs.use(['ajax'], function(Ajax){
					Ajax.postJson('weixin/canteen/doOrder', parameter, function(data){
						console.log(data);
					});
				});
				
			});
			
		})
	</script>
</body>
</html>