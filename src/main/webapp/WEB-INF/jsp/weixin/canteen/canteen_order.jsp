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
        <p>领取时间<span class="send-date">2017-08-04</span><span class="send-time">16:00~18:00</span></p>
        <p>领取地址<span class="send-address">浙江大学紫荆港校区</span></p>
    </div>
    <!-- 用户信息 -->
    <div class="user-info">
        <h4>用户信息</h4>
        <p class="layout-flex">
        	<span class="user-name form-label">姓名</span>
        	<input id="userName" class="form-input input-box" type="text" name="userName" value="" placeholder="请输入您的姓名">
        </p>
        <p class="layout-flex layout-select">
        	<span class="user-name form-label">部门</span>
        	<select id="userBranch" class="form-input">
    			<option value="1">清洁部门</option>
    			<option value="2">飞天部门</option>
    			<option value="3">钻地部门</option>
			</select>
        </p>
        <p class="layout-flex">
        	<span class="user-name form-label">联系方式</span>
        	<input id="userTelphone" class="form-input input-box" type="text" name="userName" value="" placeholder="请输入您的联系方式">
        </p>
    </div>
    <div class="booking-info">
        <h4>预定信息</h4>
        <p class="layout-flex layout-select">
        	<span class="dishs-kind form-label">菜品</span>
        	<select class="form-input" id="dishName">
    			<option value="1">烤鸭</option>
    			<option value="2">烤鸡</option>
    			<option value="3">西湖大大大醋鱼</option>
			</select>
        </p>
        <p>
        	<span class="form-label">单价</span>
        	<span class="unit-price">20</span><i>元/只</i>
        </p>
        <p>
        	<span class="dishs-count form-label">数量</span>
        	<span class="operate-count">
        		<i class="dishs-minus"></i>
        		<i class="dishs-text">1</i>
        		<i class="dishs-plus"></i>
        	</span>
        </p>
        <p class="clearfix">
        	<span class="surplus-count-label">可供余量：</span>
        	<span class="surplus-count">10</span>
        	<span class="add">添加</span>
        </p>
        <div class="order-info">
        	<ul class="title-row clearfix">
        		<li class="goods-name">商品名称</li>
        		<li class="goods-count">数量</li>
        		<li class="goods-price">单价</li>
        		<li class="goods-operate">操作</li>
        	</ul>
        	<ul class="data-row clearfix">
        		<li class="goods-name">北京烤鸭</li>
        		<li class="goods-count">2</li>
        		<li class="goods-price">￥<span>1.0</span></li>
        		<li class="goods-operate">删除</li>
        	</ul>
        	<ul class="data-row clearfix">
        		<li class="goods-name">哈尔滨佛南学院西红柿炒蛋</li>
        		<li class="goods-count">1</li>
        		<li class="goods-price">￥<span>1.0</span></li>
        		<li class="goods-operate">删除</li>
        	</ul>
        	<ul class="data-row clearfix">
        		<li class="goods-name">大中华地区福建省厦门市厦门大学第一食堂月饼焖西瓜</li>
        		<li class="goods-count">1</li>
        		<li class="goods-price">￥<span>100000.0</span></li>
        		<li class="goods-operate">删除</li>
        	</ul>
        	<ul class="data-row clearfix">
        		<li class="goods-name">桐乡阿能面</li>
        		<li class="goods-count">1</li>
        		<li class="goods-price">￥<span>1.0</span></li>
        		<li class="goods-operate">删除</li>
        	</ul>
        </div>
        <p class="total-price">
        	<span>总价：</span><span class="price">0.0</span>
        </p>
    </div>
    <p class="remarks">
    	<span>备注</span>
    	<textarea id="bookingRemarks" class="input-box" placeholder="请输入备注详情"></textarea>
    </p>
</main>
</form>
<footer>
	    <a href="weixin/canteen/order_list" class="order-link">订单</a>
	    <a href="javascript:void(0);" class="main">提交订单</a>
</footer>
</body>
<script>
	$(function(){
		
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
			var count = parseFloat($('.dishs-text').text());
			var maxCount = parseFloat($('.surplus-count').text());
			if( _this === 'dishs-minus' && count >1){
				count -=1;
			}else if( _this === 'dishs-plus' && count < maxCount){
				count +=1;
			}
			$('.dishs-text').text(count);
		})
		
		//添加菜品到订单事件绑定
		$('.add').on('click',function(){
			var dishName = $('#dishName option:selected').text();
			var dishCount = $('.dishs-text').text();
			var unitPrice = $('.unit-price').text(); 
			var totalPrice = 0;
			var rowHtml = "<ul class='data-row clearfix'>"+
					    		"<li class='goods-name'>"+dishName+"</li>"+
					    		"<li class='goods-count'>"+dishCount+"</li>"+
					    		"<li class='goods-price'>￥<span>"+unitPrice+"</span></li>"+
					    		"<li class='goods-operate'>删除</li>"+
    						"</ul>"
			$('.order-info').append(rowHtml);
			var num = $('.order-info .data-row').length;
    		totalPrice = calculate(num);
    		$('.price').text(totalPrice);
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
		})
	})
</script>
</html>