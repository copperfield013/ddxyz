<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <title>订单列表</title>
    <jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
    <link rel="stylesheet" href="${basePath }media/weixin/main/css/order.css?${RES_STAMP}">
    <script src="${basePath }media/weixin/main/js/ix.js?${RES_STAMP}"></script>
    <script>
    $(function() {
        //ix.init();
        
        ix.list('order-list',{
			url : 'weixin/ydd/orderData',
		    data : {
		        // 自定义参数
		    },
		    pageNo : 'pageNo',  // 当前页码的key
		    callback : function(boxs) {
		        // 回调 this是当前容器，boxs是加载的数据项
		    }
		});
    });
    </script>
</head>
<body class="ui-page">
<main>
	<div class="ui-body">
		<div class="ui-content">
			<div id="order-list"  class="page-list">
				
			</div>
		</div>
	</div>
	<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
    <!-- 待收货 -->
    <%-- <div class="order">
        <p class="number">订单号：M1790378T1<i class="todo">待收货</i></p>
        <p class="detail">
            <img src="${basePath }media/weixin/main/image/thumb-tea1.jpg" alt="奶茶1">
            <span class="name">奶茶<i>￥12.0</i></span>
            <a href="javascript:;" class="btn">再次购买</a>
        </p>
        <p class="result">
            <span class="time">2016/06/21 9:30:25</span>
            <span class="count">总计<b>1</b>杯</span>
            <span class="money">总价：<b>￥12.0</b></span>
        </p>
    </div> --%>
    <!-- 已取消 -->
    <%-- <div class="order">
        <p class="number">订单号：M1790378T1<i class="cancel">已取消</i></p>
        <p class="detail">
            <img src="${basePath }media/weixin/main/image/thumb-tea1.jpg" alt="奶茶1">
            <span class="name">奶茶<i>￥12.0</i></span>          
            <span class="option">四季春茶|去冰|大杯|红豆、仙草、波霸、珍珠、椰果</span>
        </p>
        <p class="detail">
            <img src="${basePath }media/weixin/main/image/thumb-tea2.jpg" alt="奶茶2">
            <span class="name">玛奇朵<i>￥14.0</i></span>         
        </p>
        <p class="result">
            <span class="time">2016/06/21 9:30:25</span>
            <span class="count">总计<b>3</b>杯</span>
            <span class="money">总价：<b>￥40.0</b></span>
        </p>
        <p class="tool">
            <a href="javascript:;" class="btn">继续支付</a>
        </p>
    </div> --%>
</main>
	<footer>
	    <a href="weixin/ydd?${RES_STAMP }" class="order-link">首页</a>
	    <a href="weixin/ydd/order?${RES_STAMP }" class="main">立即下单</a>
	</footer>
	<script type="text/javascript">
		$(function(){
			seajs.use('ydd/ydd-order-list');
		});
	</script>
</body>
</html>