<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <title>订单列表</title>
    <jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
</head>
<style>
body,html {
	webkit-tap-highlight-color:rgba(0,0,0,0);
}
.page-list {
	padding:0 8px;
}
i {
	font-style:normal;
}
.order {
    border-top: 1px solid #E3E1DE;
    background-color: #fff;
    padding-top:5px;
    margin-bottom:10px;
}
.order:first-child {
    border-top: none;
}
.order p {
    padding: 0 15px;
    line-height: 30px;
    position: relative;
}
.order p:after {
    content: " ";
    display: block;
    height: 0;
    clear: both;
}
.order .detail {
    background-color: #FAFAF9;
    margin-top: 6px;
    padding: 15px;
}
.order .detail img {
    display: block;
    float: left;
    width: 70px;
    height: 70px;
    margin-right: 20px;
}
.order .detail .name {
	float: left;
    height: 70px;
    line-height: 70px;
}
.order .detail .name i {
	margin-left:0.5em;
}
.order .detail .price {
	float: right;
    height: 70px;
    line-height: 70px;
    margin-right:10px;
}
.order .result {
    text-align: right;
    font-size: 12px;
}
p.operate {
    text-align: right;
    height: 3em;
    line-height: 3em;
}
p.operate .operate-button {
	width: 6em;
    height: 1.8em;
    line-height: 1.8em;
    border: 1px solid #999999;
    display: inline-block;
    text-align: center;
    border-radius: 1em;
    color: #777777;
 
}
p.operate .operate-revise {
	width: 6em;
    height: 1.8em;
    line-height: 1.8em;
    border: 1px solid #f74342;
    display: inline-block;
    text-align: center;
    border-radius: 1em;
    color: #f74342;
}
.timePoint, .time, .location {
	float:left;
}
.order-detail-wrap {
	font-size:12px;
	display:none;
	overflow:hidden;
	
}
.order-detail-wrap.active {
	display:block;
}
.circle-point-icon {
	color:#999999;
	float:left;
	width:1.4em;
	height:1.4em;
	line-height:1.4em;
	text-align:center;
	font-size:1.4em;	
	vertical-align:middle;
	margin-top:0.3em;
	transition:all linear .2s;
	-ms-transition:all linear .2s;
	-moz-transition:all linear .2s;
	-webkit-transition:all linear .2s;
	-o-transition:all linear .2s;
	transform:rotate(90deg);
	-ms-transform:rotate(90deg); 	
	-moz-transform:rotate(90deg); 	
	-webkit-transform:rotate(90deg); 
	-o-transform:rotate(90deg); 	
	webkit-tap-highlight-color:rgba(0,0,0,0);
}
.circle-point-icon:before {
	font-family:icon;
	content:"\e600";	
	webkit-tap-highlight-color:rgba(0,0,0,0);
}
.circle-point-icon.active {
	transform:rotate(-90deg);
	-ms-transform:rotate(-90deg); 	
	-moz-transform:rotate(-90deg); 	
	-webkit-transform:rotate(-90deg); 
	-o-transform:rotate(-90deg); 	
}
.hideinfo>span {
	display:block;
}
footer .order-link {
    color: #663300;
}
footer .main {
    background-color: #663300;
}
</style>
<body class="ui-page">
	<main>
		<div class="ui-body">
			<div class="ui-content">
				<div id="order-list"  class="page-list">
					
				   	<div class="order">
				   		<p class="number">订单号：170509161232		
				   		</p>				   		
				   		<p class="detail">
				    		<img src="http://192.168.1.129:8080/ddxyz/media/weixin/main/image/h_milk_tea_2h.png" alt="奶茶">
				            <span class="name">烤鸭<i>x1</i></span>
				            <span class="price">￥20.0</span>				       
				    	</p>
				    	<p class="detail">
				    		<img src="http://192.168.1.129:8080/ddxyz/media/weixin/main/image/h_milk_tea_2h.png" alt="奶茶">
				            <span class="name">鸡爪<i>x2</i></span>
				            <span class="price">￥40.0</span>				       
				    	</p>
				    	<p class="detail">
				    		<img src="http://192.168.1.129:8080/ddxyz/media/weixin/main/image/h_milk_tea_2h.png" alt="奶茶">
				            <span class="name">小龙虾<i>x5</i></span>
				            <span class="price">￥251.0</span>				       
				    	</p>
				  		<p class="result">
				  			<span class="timePoint">领取时间：2017-08-11 16:00:00</span>
				  			<span class="money">总价：<b>￥0.01</b></span>
				  		</p>
				  		<p class="result">
				  			<span class="location">领取地点：东部软件园</span>
				  		</p>
				  		<div class="order-detail-wrap">	
				  			<p class="hideinfo">
				  				<span class="ordertime">创建时间：2017-08-11 14:00:00</span>
				  				<span class="takemen">收货人：张荣波</span>
				  				<span class="takePhone">联系号码：13588888888</span>
				  				<span class="takeport">部门：研发部</span>
				  			</p>
				  		</div>				  		
				  		<p class="operate">
				  			<i class="circle-point-icon"></i>			  				
				  			<span class="operate-button">取消订单</span>	
				  			<span class="operate-revise">修改订单</span>			  				
				  		</p>
				   	</div>
				   	
				</div>
			</div>
		</div>
	</main>
	<footer>
	    <a href="weixin/canteen/home" class="order-link">首页</a>
	    <a href="weixin/canteen/order" class="main">立即下单</a>
	</footer>
<script>
	$(function(){
		$('.circle-point-icon').on('click',function(){
			var isOpen = $('.order-detail-wrap').hasClass('active');
			if( isOpen ){
				$('.order-detail-wrap').removeClass('active');
				$(this).removeClass("active");
			}else {
				$('.order-detail-wrap').addClass('active');
				$(this).addClass("active")
			}			
		})
	})
</script>
</body>
</html>