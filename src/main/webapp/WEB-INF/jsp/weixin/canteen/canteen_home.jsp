<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
	<title>${WXAPP.cname }</title>
	<jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
</head>
<style>
body {
	webkit-tap-highlight-color:rgba(255,255,255,0);
}
img {
	width:100%;
}
.clearfix:after {
	content:'';
	display:block;
	clear:both;
}
.block {
   	margin: 20px 15px 40px;
}
.block h4 {
    font-size: 17px;
}
.block h4:before {
    font-family: icon;
    margin-right: 8px;
}
.block p {
	margin-top:20px;
}
.block p span {
    display: block;
    line-height: 30px;
    /* overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis; */
}
.weekMenu h4:before {
    content: '\e604';
}
.weekMenu p img {
    display: block;
    float: left;
    width: 110px;
    height: 90px;
    margin-right: 20px;
}
.weekMenu p .name {
    font-size: 18px;
    padding-top: 0.5em;
}
.weekMenu p .price b {
    font-weight: normal;
    font-size: 16px;
}
.weekMenu p .price i {
    font-style: normal;
  
}

.address h4:before {
    content: '\e750';
}
.address p img {
    display: block;
    float: left;
    width: 110px;
    height: auto;
    margin-right: 20px;
}
.address p .name {
    font-size: 18px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
.address p .name:after {
    font-family: icon;
    content: '\e65b';
    margin-left: .5em;
}
.address p .addr {
    line-height: 1.5em;
    height: 3em;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: normal;
}
footer .order-link {
    color: #663300;
}
footer .main {
    background-color: #663300;
}
</style>
<body>
	  <main>
        <!-- 头部banner -->
        <header>
            <img src="${basePath }media/weixin/main/image/banner-index.jpg" alt="点点心意">
        </header>
        <!-- 本周菜单 -->
        <div class="block weekMenu">
            <h4>本周菜单</h4>
            	<p class="clearfix">
	                <img src="${basePath }/media/weixin/main/image/thumb-shop1.jpg" alt="${drinkType.name }">
	                <span class="name">西湖龙井大烤鸭</span>
	                <span class="price">
	                     <b>单价：<i>20元/只</i></b>
	                </span>
	            </p>
	            <p class="clearfix">
	                <img src="${basePath }/media/weixin/main/image/thumb-shop2.jpg" alt="${drinkType.name }">
	                <span class="name">鸡爪</span>
	                <span class="price">
	                     <b>单价：<i>20</i>元/斤</b>
	                </span>
	            </p>
	            <p class="clearfix">
	                <img src="${basePath }/media/weixin/main/image/thumb-shop1.jpg" alt="${drinkType.name }">
	                <span class="name">小龙虾</span>
	                <span class="price">
	                    <b>单价：<i>50元</i>/斤</b>
	                </span>
	            </p>
        </div>
        <!-- 领取地址 -->
        <div class="block address">
            <h4>领取地址</h4>           	
	           <p class="clearfix">
	               <img src="http://192.168.1.129:8080/ddxyz/media/weixin/main/image/thumb-shop1.jpg" alt="${deliveryLocation.name }">
	               <span class="name">东部软件园</span>
	               <span class="addr">地址：东部软件园东部软件园东部软件园</span>
	           </p>
	           <p class="clearfix">
	               <img src="http://192.168.1.129:8080/ddxyz/media/weixin/main/image/thumb-shop1.jpg" alt="${deliveryLocation.name }">
	               <span class="name">浙江大学西溪校区</span>
	               <span class="addr">地址：东部软件园东部软件园东部软件园</span>
	           </p>
	           <p class="clearfix">
	               <img src="http://192.168.1.129:8080/ddxyz/media/weixin/main/image/thumb-shop1.jpg" alt="${deliveryLocation.name }">
	               <span class="name">浙江大学紫荆港校区</span>
	               <span class="addr">地址：东部软件园东部软件园东部软件园</span>
	           </p>
        </div>
    </main>
    <footer>
        <a href="weixin/canteen/order_list" class="order-link">订单</a>
        <a href="weixin/canteen/order" class="main">立即下单</a>
    </footer>
</body>
</html>