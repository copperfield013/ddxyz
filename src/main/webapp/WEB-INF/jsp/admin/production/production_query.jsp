<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath }" />
		<title>产品列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
		<%
			response.setHeader("X-Frame-Options", "SAMEORIGIN");
		%>
		<jsp:include page="/WEB-INF/jsp/admin/common/admin-include.jsp"></jsp:include>
		<!--Basic Styles-->
	    <link href="media/admin/plugins/beyond/css/bootstrap.min.css" rel="stylesheet" />
	    <link href="media/admin/plugins/beyond/css/font-awesome.min.css" rel="stylesheet" />
	
	    <!--Beyond styles-->
	    <link id="beyond-link" href="media/admin/plugins/beyond/css/beyond.min.css" rel="stylesheet" type="text/css" />
	    <!--  -->
	    <link href="media/admin/cpf/css/cpf-main.css" rel="stylesheet" type="text/css" />
	    <style type="text/css">
	    	.order-info span, .receiver-info span {
				margin-left: 1em;
				margin-right: 1em;
			}
			span.order-status {
			    color: #ff0000;
			    font-weight: bold;
			    font-size: 1.1em;
			}
			.order-time {
				font-size: 0.7em;
				margin: 5px 0 5px 2em;
				font-weight: bold;
				color: #999;
			}
			.product-item {
				border : 1px solid #3498DB;
				border-radius: 5px;
				margin: 15px 40px 15px 0;
				padding:15px;
				position:  relative;
				overflow: hidden;
			}
			.product-info li {
				list-style-type: none;
			}
			.product-info li span {
				margin-right:1em;
				display: block;
				margin-bottom: 2px;
				font-size: 14px;
			}
			.order-info, .product-info {
				border-bottom: 1px solid #ccc;
				margin-bottom: 5px;
				padding-bottom: 5px;
			}
			.query-condition {
			    font-size: 1.1em;
			    font-weight: bold;
			    border: 1px solid;
			    display: inline-block;
			    border-radius: 2em;
			    padding: 3px 10px;
			    background-color: #044d22;
			    color: #fff;
			}
	    </style>
	</head>
	<body style="overflow-y: auto;">
		<div class="">
			<div class="page-header">
				<div class="header-title">
					<h1>生产管理-产品列表</h1>
				</div>
			</div>
			<div class="page-body">
				<div class="col-md-12 col-lg-12">
					<div class="row">
						<div class="col-md-3 col-lg-3">
							<div class="query-condition">
								订单日期：2017-5-27
							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-12 col-md-12">
					<div class="row col-product-item">
						<div class="row product-header">
							<div class="col-lg-3 col-md-3">
								<span class="checkbox">
									<label>
										<input type="checkbox" class="item-check" value="158" >
										<span class="text">订单编号:000220170421201459</span>
									</label>
								</span>
							</div>
							<div class="col-lg-1 col-md-1">已打印</div>
						</div>
						<div class="row product-desc">
							<div class="col-lg-3 col-md-3">付款时间：2017-04-21 20:15:00</div>
							<div class="col-lg-offset-1 col-lg-2 col-md-offset-1 col-md-2">预定时间：15点档</div>
						</div>
						<div class="row product-content">
							<div class="row">
								<div class="col-lg-1 col-md-1">奶茶</div>
								<div class="col-lg-offset-1 col-lg-2 col-md-offset-1 col-md-2">搭配：红茶</div>
							</div>
							<div class="row">
								<div class="col-lg-2 col-md-2">规格：中杯</div>
								<div class="col-lg-offset-1 col-lg-2 col-md-offset-1 col-md-2">甜度：5分甜</div>
								<div class="col-lg-offset-1 col-lg-2 col-md-offset-1 col-md-2">冰度：热</div>
							</div>
							<div class="row">
								<div class="col-lg-12 col-md-12">加料：珍珠&nbsp;波霸&nbsp;仙草</div>
							</div>
						</div>
						<div class="row product-footer">
							<div class="col-lg-4 col-md-4">配送地址：杭钻大厦</div>
							<div class="col-lg-offset-1 col-lg-4 col-md-offset-1 col-md-4">联系号码：12345678901</div>
							<div class="col-lg-offset-1 col-lg-1 col-md-offset-1 col-md-1"><a>打印</a></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			$(function(){
				
				
				
				
			});
		</script>
	</body>
</html>