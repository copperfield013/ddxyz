<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <title>订单列表</title>
    <jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
</head>
<style>
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
    border: 1px solid #663300;
    display: inline-block;
    text-align: center;
    border-radius: 0.6em;
    font-weight: 700;
    color: #663300;
    box-shadow: 0px 0px 1px #663300;
}
p.operate .operate-revise {
	width: 4em;
    height: 1.8em;
    line-height: 1.8em;
    border: 1px solid #f74342;
    display: inline-block;
    text-align: center;
    border-radius: 0.6em;
    margin-left: 0.5em;
    color: #f74342;
    font-weight: 700;
    box-shadow: 0px 0px 1px #f74342;
}
.timePoint,
.time {
	float:left;
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
				  			<span class="timePoint">时间档：18点</span>
				  			<span class="location">配送地点：天虹商场</span>
				  		</p>
				  		<p class="result">
				  			<span class="time">创建时间：2017-05-09 16:12:33</span>
				  			<span class="money">总价：<b>￥0.01</b></span>
				  		</p>
				  		<p class="operate">				  				
				  			<span class="operate-button">取消订单</span>	
				  			<span class="operate-revise">修改</span>			  				
				  		</p>
				   	</div>
				   	
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
				  			<span class="timePoint">时间档：18点</span>
				  			<span class="location">配送地点：天虹商场</span>
				  		</p>
				  		<p class="result">
				  			<span class="time">创建时间：2017-05-09 16:12:33</span>
				  			<span class="money">总价：<b>￥0.01</b></span>
				  		</p>
				  		<p class="operate">				  				
				  			<span class="operate-button">取消订单</span>	
				  			<span class="operate-revise">修改</span>			  				
				  		</p>
				   	</div>
				   	
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
				  			<span class="timePoint">时间档：18点</span>
				  			<span class="location">配送地点：天虹商场</span>
				  		</p>
				  		<p class="result">
				  			<span class="time">创建时间：2017-05-09 16:12:33</span>
				  			<span class="money">总价：<b>￥0.01</b></span>
				  		</p>
				  		<p class="operate">				  				
				  			<span class="operate-button">取消订单</span>	
				  			<span class="operate-revise">修改</span>			  				
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
</body>
</html>