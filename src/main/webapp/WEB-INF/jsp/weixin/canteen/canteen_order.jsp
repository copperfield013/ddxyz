<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <title>下单</title>
    <jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
    <link href="media/weixin/canteen/css/canteen-order.css" type="text/css" rel="stylesheet">
    <style>
    	.shade {
    		position:absolute;
    		top:0;
    		bottom:0;
    		left:0;
    		right:0;
    		background-color:rgba(0,0,0,0.2);
    		z-index:9;
    	}
    	.shade >div {
    		font-size:20px;
    		width: 12em;
		    height: 6em;
		    border-radius: 0.4em;
		    background-color: rgba(250,250,250,1);
		    position: absolute;
		    top: 30%;
		    left: 50%;
		    margin-left: -6em;	
		    text-align: center;
		    padding-top:0.5em;
			
    	}
    	.shade >div >p {
    		padding:0;
    		margin:0;
    		height:1.6em
    	}
    	.shade >div >div.shade-operation {
    		margin-bottom:0.5em;
    	}
    	.shade >div >div.shade-operation:after{
    		content:'';
    		display:block;
    		clear:both;
    	}
    	.shade span.shade-warn {
    		display:block;
    		float:left;
    		margin-left:1em;
    		width:1.1em;
    		height:1.1em;
    		line-height:1.1em;
    		text-align:center;
    		border-radius:50%;
    		color:#F0B840;
    		border:2px solid #F0B840;
    	}
    	.shade span.shade-close {
    		font-size:40px;
    		display:block;
    		float:right;
    		margin-right:0.5em;
    		width:0.55em;
    		height:0.55em;
    		line-height:0.55em;
    		text-align:center;
    		color:#004621;
    	}
    	.overtime-shade p.time-range{
    		font-size: 0.5em;
    		color: #999;
    	}
    </style>
	
</head>
<body>
<c:set var="pDelivery" value="${delivery.plainDelivery }" />
<c:if test="${delivery == null }">
	<div class="shade">
		<div>
			<div class="shade-operation">
				<span class="shade-warn">!</span>
			</div>
			<p>等待商家发布</p>
			<p>暂停接单!</p>
		</div>
	</div>
</c:if>
<c:if test="${delivery != null && overtime }">
	<div class="shade overtime-shade">
		<div>
			<div class="shade-operation">
				<span class="shade-warn">!</span>
			</div>
			<p>本周下单时间已结束</p>
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
        <p class="layout-flex layout-select" id="wares-select">
        	<span class="dishs-kind form-label">菜品</span>
        	<select class="form-input" id="waresName">
        		<c:forEach items="${delivery.waresList }" var="cWares">
        			<option value="${cWares.dWaresId }">${cWares.waresName }</option>
        		</c:forEach>
			</select>
			<span style="color: #666">菜品选择</span>
        </p>
        <p>
        	<span class="form-label">单价</span>
        	<span class="unit-price"></span><i class="price-unit"></i>
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
    	<textarea id="comment" class="input-box" placeholder="请输入备注详情"></textarea>
    </p>
    <!-- 用户信息 -->
    <div class="user-info" style="margin-bottom: 3em;">
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
					count -=1;
				}else if( $this.is('.dishs-plus') && count < maxCount){
					count +=1;
				}
				$('.dishCount').text(count);
			});
			
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
					clearInterval(showAlert);
				}, 5000);
				
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
						count			: count
					});
				});
				if(orderItems.length == 0){
					alert('请至少添加一个商品后提交');
					return false;
				}
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
				if(confirm('确认提交订单？')){
					seajs.use(['ajax'], function(Ajax){
						Ajax.postJson('weixin/canteen/doOrder', parameter, function(data){
							if(data.status === 'suc'){
								alert('订单创建成功');
								location.href = 'weixin/canteen/order_list';
							}else{
								alert('订单创建失败');
							}
						});
					});
				}
			});
		});
	</script>
</body>
</html>