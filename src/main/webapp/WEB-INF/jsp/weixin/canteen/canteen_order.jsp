<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <title>下单</title>
    <jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
</head>
<style>
i {
	font-style:normal;
}
.clearfix:after {
	content:'';
	display:block;
	clear:both;
}
.send-info p {
	height:3.6em;
	line-height:3.6em;
	border-bottom: 1px solid #E6E3E5;
    padding: 0 1em;
}
.send-date,.send-address {
	margin-left:1em;
}
.send-time {
	margin-left:5px;
}
.user-info p {
	height:3.6em;
	line-height:3.6em;
	border-bottom: 1px solid #E6E3E5;
    padding: 0 1em;
}
.user-info p.layout-flex {
	display:flex;
}

.user-info p.layout-select:after {
	font-family: icon;
	display:inline-block;
	content:"\e600";
	color:#C8C8CD;
	float:right;
	margin-right:0.5em;
	height:3.6em;
	line-height:3.6em;
}
.form-label {
	display:inline-block;
	width:5em;
	margin-right:1em;
}
.form-input {
	flex:1;
	-webkit-appearance: none;
}
.booking-info p {
	height: 3.6em;
    line-height: 3.6em;
    border-bottom: 1px solid #E6E3E5;
    padding: 0 1em;
}
.booking-info p.layout-flex {
	display:flex;
}
.operate-count {
	display:inline-block;
}
.dishs-minus,
.dishs-plus  {
	display:inline-block;
	font-family: icon;
    border-radius: 50%;
    width: 1.5em;
    height: 1.5em;
    line-height: 1.5em;
    text-align: center;
    font-size: 15px;
    border: 1px solid #663300;
    vertical-align:middle;
}
.dishs-minus {
	color: #663300;
    background-color: #ffffff;
}
.dishs-plus {
	color:#ffffff;
	background-color:#663300;
}
.dishs-plus:before {
	content:'\e619';
}
.dishs-minus:before {
	content:'\e632';
}
.dishs-text {
	margin:0 0.5em;
}
.surplus-count-label,
.surplus-count		  {
	color:#f74342;
	font-size:12px;
	display: inline-block;
    height: 2em;
    line-height: 2em;
    margin-top: 0.25em;
}
.add {
	display:block;	
    width: 4em;
    height: 2em;
    line-height: 2em;
    float: right;
    background-color: #663300;
    text-align: center;
    color: #ffffff;
    border-radius: 0.8em;
    margin-top:0.8em;
}
.order-info {
	position:relative;
}
.order-info:after {
	position: absolute;
    right: 36px;
    content: " ";
    display: block;
    width: 0;
    height: 0;
    border-left: 8px solid transparent;
    border-right: 8px solid transparent;
    border-bottom: 10px solid #F3F2F1;
    top: -9px;
}
.order-info ul li {
	float:left;
	text-align:center;
	box-sizing:border-box;
}
.title-row {
	background-color:#F3F2F1;
	font-size:15px;
	height:3em;
	line-height:3em;
}
.data-row li {
	padding:0.5em 0;
	word-wrap:break-word
}
li.goods-name {
	width:45%;
	padding-left:1em;
}
li.goods-count {
	width:15%;
}
li.goods-price,
li.goods-operate {
	width:20%;
}
ul.data-row li.goods-operate {
	color:#663300;
}
.total-price {
	text-align:right;
	background-color: #FAFAF9;
}
.total-price .price {
	color:#f74342;
}
.remarks {
	padding:0 1em;
	position:relative;
	margin-bottom:35px;
}
.remarks > span {
	display:block;
    line-height: 35px;
}
.remarks textarea {
    display:block;
    width:100%;
    height:80px;
	background-color: #FAFAF9;
	border-radius: 10px; 
    border: 1px solid #ccc;
    border-top: none;
    border-bottom: none;
    padding: 5px 1em 5px 2em; 
    box-sizing: border-box; 
}
.remarks:before {
	font-family: icon;
    content: "\e7c5";
    position: absolute;
    top: 40px;
    left: 1.4em;
    color: #aaa;
}

footer .order-link {
    color: #663300;
}
footer .main {
    background-color: #663300;
}
</style>
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