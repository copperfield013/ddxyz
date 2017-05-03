<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <title>订单列表</title>
    <jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
    <link rel="stylesheet" href="${basePath }media/weixin/main/css/order.css?${RES_STAMP}">
    <script src="${basePath }media/weixin/main/js/ix.js?${RES_STAMP}"></script>
</head>
<body class="ui-page">
	<main>
		<div class="ui-body">
			<div class="ui-content">
				<div id="order-list"  class="page-list"></div>
			</div>
		</div>
	</main>
	<footer>
	    <a href="weixin/ydd?${RES_STAMP }" class="order-link">首页</a>
	    <a href="weixin/ydd/order?${RES_STAMP }" class="main">立即下单</a>
	</footer>
	<script type="text/javascript">
		$(function(){
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
			seajs.use('ydd/ydd-order-list');
		});
	</script>
</body>
</html>