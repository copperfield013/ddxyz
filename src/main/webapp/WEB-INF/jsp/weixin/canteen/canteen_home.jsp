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
	<c:set var="pDelivery" value="${delivery.plainDelivery }" />
	<c:if test="${delivery == null }">
		<div class="shade">
			<div>
				<div class="shade-operation">
					<span class="shade-warn">!</span>
					<span id="shade-close" class="shade-close">×</span>
				</div>
				<p>等待商家发布</p>
				<p>暂停接单!</p>
			</div>
		</div>
	</c:if>
	  <main>
        <!-- 头部banner -->
        <%-- <header>
            <img src="${basePath }media/weixin/main/image/banner-index.jpg" alt="点点心意">
        </header> --%>
        <!-- 本周菜单 -->
        <header>
            <img src="${basePath }media/weixin/canteen/image/banner-canteen.png" alt="林业厅之家">
        </header>
        <div class="block weekMenu">
            <h4>本周菜单</h4>
            	<c:forEach items="${delivery.waresList }" var="dWares">
	            	<p class="clearfix">
		                <img src="${basePath }${dWares.thumbUri}" alt="${dWares.waresName }">
		                <span class="name">${dWares.waresName }</span>
		                <span class="price">
		                     <b>单价：<i><fmt:formatNumber value="${dWares.price/100 }" pattern="0.00" />元/${dWares.priceUnit }</i></b>
		                </span>
		            </p>
            	</c:forEach>
            
        </div>
        <div class="block time-range">
        	<h4>下单和领取时间</h4>
        	<p>
        		<span>
        			下单时间：
        			<fmt:formatDate value="${pDelivery.openTime }" pattern="MM月dd日HH时mm分" />
        			~
        			<fmt:formatDate value="${pDelivery.closeTime }" pattern="MM月dd日HH时mm分" />
        		</span>
        	</p>
        	<p>
        		<span>
        			领取时间：
        			<fmt:formatDate value="${pDelivery.timePoint }" pattern="MM月dd日HH时mm分" />
        			~
        			<fmt:formatDate value="${pDelivery.claimEndTime }" pattern="MM月dd日HH时mm分" />
        			
        		</span>
        	</p>
        </div>
        <!-- 领取地址 -->
        <div class="block address">
            <h4>领取地址</h4>         
				<c:forEach items="${locations }" var="location">
					<p class="clearfix">
					    <img src="${basePath }${location.pictureUri }" alt="${location.name }">
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