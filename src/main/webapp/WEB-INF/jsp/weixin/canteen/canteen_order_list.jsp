<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <title>订单列表</title>
    <jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
    <link type="text/css" rel="stylesheet" href="${basePath }media/weixin/canteen/css/canteen-order-list.css" />
    <script src="${basePath }media/weixin/main/js/ix.js"></script>
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
	    <a href="weixin/canteen/home" class="order-link">首页</a>
	    <a href="weixin/canteen/order" class="main">立即下单</a>
	</footer>
	<script type="text/javascript">
		$(function(){
			ix.list('order-list', {
				url	: 'weixin/canteen/order_data',
				data: {
					// 自定义参数
				},
				pageNo : 'pageNo', // 当前页码的key
				callback : function(boxs){
					 // 回调 this是当前容器，boxs是加载的数据项
				}
			});
			seajs.use('ydd/ydd-order-list');
			
			/* $('.circle-point-icon').on('click',function(){
				var  isOpen = $(this).hasClass('active');
				if( isOpen ){
					$(this).parent().prev('.order-detail-wrap').removeClass('active');
					$(this).removeClass("active");
				}else {
					$(this).parent().prev('.order-detail-wrap').addClass('active');
					$(this).addClass("active")
				}
			}); */
			function lookMore(obj){
				var  isOpen = $(obj).hasClass('active');
				if( isOpen ){
					$(obj).parent().prev('.order-detail-wrap').removeClass('active');
					$(obj).removeClass("active");
				}else {
					$(obj).parent().prev('.order-detail-wrap').addClass('active');
					$(obj).addClass("active")
				}
			}
			$('#order-list').on('click', '.circle-point-icon', function(){
				lookMore(this);
				return false;
			}).on('click', '.operate-revise', function(){
				var orderId = $(this).closest('.order').attr('data-id');
				location.href = 'weixin/canteen/update_order/' + orderId;
				return false;
			}).on('click', '.operate-button', function(){
				if(confirm('确认取消订单？')){
					var orderId = $(this).closest('.order').attr('data-id');
					$.post('weixin/canteen/doCancelOrder/' + orderId, {}, function(data){
						if(data.status === 'suc'){
							alert('操作成功');
							location.reload();
						}else{
							alert('操作失败');
						}
					})
					
				}
			});
		});
		
		
	</script>
</body>
</html>