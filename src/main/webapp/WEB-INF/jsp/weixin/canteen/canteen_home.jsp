<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
	<title>${WXAPP.cname }</title>
	<base>
	<jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
	<link href="media/weixin/canteen/css/canteen-home.css" type="text/css" rel="stylesheet" />
</head>
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