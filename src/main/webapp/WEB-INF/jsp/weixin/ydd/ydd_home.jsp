<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
	<title>点点新意</title>
	<jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
	
    <link rel="stylesheet" href="${basePath }media/weixin/main/css/index.css">
    <script src="${basePath }media/weixin/main/js/jquery-3.1.1.min.js"></script>
    <script src="${basePath }media/weixin/main/js/ix.js"></script>
    <script>
    $(function() {
        ix.init();
    });
    </script>
</head>
<body>
    <main>
        <!-- 头部banner -->
        <header>
            <img src="${basePath }media/weixin/main/image/banner-index.jpg" alt="点点心意">
        </header>
        <!-- 时间档 -->
        <div class="block timeline">
            <h4>时间档<pre>高亮表示该时段有奶茶供应</pre></h4>
            <c:forEach items="${timePointItems }" var="timePoint" varStatus="i">
            	${i.index % 3 == 0? '<p>':'' }
                <a href="javascript:;" data-value="${timePoint.hour }" class="time-point ${timePoint.closed? 'no': 'yes' }">${timePoint.hour }点</a>
            	${i.index % 3 == 2 || i.last? '</p>' : '' }
            </c:forEach>
        </div>
        <!-- 奶茶种类 -->
        <div class="block milktea">
            <h4>奶茶种类</h4>
            <c:forEach items="${drinkTypes }" var="drinkType" >
	            <p>
	                <img src="${basePath }${drinkType.pictureUri }" alt="奶茶1">
	                <span class="name">${drinkType.name }</span>
	                <span class="price">
	                    <b>￥<fmt:formatNumber value="${drinkType.basePrice / 100}" pattern="0.0" />元/杯</b><i>（大杯+￥3）</i>
	                </span>
	            </p>
            </c:forEach>
        </div>
        <!-- 奶茶领取地址 -->
        <div class="block address">
            <h4>奶茶领取地址<pre>请您在所选时间到所选地址领取奶茶</pre></h4>
           	<c:forEach items="${deliveryLocations }" var="deliveryLocation">
	            <p>
	                <img src="${basePath }${deliveryLocation.pictureUri}" alt="${deliveryLocation.name }">
	                <span class="name">${deliveryLocation.name }</span>
	                <span class="addr">地址：${deliveryLocation.address }</span>
	            </p>
           	</c:forEach>
        </div>
    </main>
    <footer>
        <a href="weixin/ydd/orderList?${RES_STAMP }" class="order-link">订单</a>
        <a href="weixin/ydd/order?${RES_STAMP }" class="main">立即下单</a>
    </footer>
    <script type="text/javascript">
    	$(function(){
    		seajs.use([], function(){
    			$('.time-point.yes').click(function(){
    				var hour = $(this).attr('data-value');
    				location.href = 'weixin/ydd/order?${RES_STAMP }&deliveryHour=' + hour;
    			});
    		});
    	});
    </script>
</body>
</html>