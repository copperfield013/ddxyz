<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <title>订单列表</title>
    <jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
    <script src="${basePath }media/weixin/main/js/ix.js"></script>
</head>
<style>
body,html {
	-webkit-tap-highlight-color:rgba(0,0,0,0);
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