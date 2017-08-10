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
        <%-- <header>
            <img src="${basePath }media/weixin/main/image/banner-index.jpg" alt="点点心意">
        </header> --%>
        <!-- 本周菜单 -->
        <div class="block weekMenu">
            <h4>本周菜单</h4>
            	<c:forEach items="${delivery.waresList }" var="dWares">
	            	<p class="clearfix">
		                <img src="${basePath }${dWares.thumbUri}" alt="${dWares.waresName }">
		                <span class="name">${dWares.waresName }</span>
		                <span class="price">
		                     <b>单价：<i><fmt:formatNumber value="${dWares.price/100 }" pattern="0.00" /> ${dWares.priceUnit }</i></b>
		                </span>
		            </p>
            	</c:forEach>
            
        </div>
        <!-- 领取地址 -->
        <div class="block address">
            <h4>领取地址</h4>         
				<c:forEach items="${locations }" var="location">
					<p class="clearfix">
					    <img src="${location.pictureUrl }" alt="${location.name }">
					    <span class="name">${location.name }</span>
					    <span class="addr">地址：${location.address }</span>
					</p>
				</c:forEach>
        </div>
    </main>
    <footer>
        <a href="weixin/canteen/order_list" class="order-link">订单</a>
        <a href="weixin/canteen/order" class="main">立即下单</a>
    </footer>
</body>
</html>