<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<script src="${basePath }media/admin/plugins/printArea/jquery.PrintArea.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>生产管理</title>
</head>
<body>
	<div class="ui-page">
		<div class="ui-body">
			<div class="ui-content">
				<form class="" action="admin/production/product-list">
					<div class="condition-area">
						<span>
							订单日期：<input type="text" class="form-control" id="timeRange" name="timeRange" readonly="readonly" 
		                	value="${criteria.timeRange }" css-width="25em"  css-cursor="text" css-display="inline-block"/>
						</span>
						<span>
							预定时间：
							<select id="timePoint" name="timePoint">
								<option value="">--全部--</option>
								<option value="11" <c:if test="${criteria.timePoint == 11}">selected="selected"</c:if>>11点</option>
								<option value="12" <c:if test="${criteria.timePoint == 12}">selected="selected"</c:if>>12点</option>
								<option value="13" <c:if test="${criteria.timePoint == 13}">selected="selected"</c:if>>13点</option>
								<option value="14" <c:if test="${criteria.timePoint == 14}">selected="selected"</c:if>>14点</option>
								<option value="15" <c:if test="${criteria.timePoint == 15}">selected="selected"</c:if>>15点</option>
								<option value="16" <c:if test="${criteria.timePoint == 16}">selected="selected"</c:if>>16点</option>
							</select>
						</span>
						<span>
							<input id="confirm" class="btn" style="width:4em;" type="submit" value="确定"/>
						</span>
						<span>
							<input id="check-all" type="button" class="btn" style="width:6em;" type="submit" value="勾选全部" >
							<input id="uncheck-all" type="button" class="btn" style="width:6em;" type="submit" value="取消勾选"/>
						</span>
						<span>
							<input id="print-checked" type="button"class="btn" style="width:6em;" type="submit" value="打印勾选" />
						</span>
						<span>
							选择打印前<input type="text" id="printCount"  name="printCount" css-width="3em"/>
							<input id="print-count" type="button" class="btn" style="width:4em;" type="submit" value="打印">
						</span>
					</div>
				</form>
				<div class="product-info-area">
					<ul class="product-list">
						<%-- <jsp:include page="production_data.jsp"></jsp:include> --%>
						<c:forEach items="${list }" var="productItem">
							<li class="product-item">
								<div class="order-info">	
									<span class="order-code">
										<input type="checkbox" class="item-check" value="${productItem.drinkProductId }" style="margin-top:1px;left:5px;opacity:1;"/>
										订单编号:${productItem.orderCode }
									</span>
									<span class="order-status">[已发货]</span>
									<div class="order-time">
										<span>下单时间：<fmt:formatDate value="${productItem.orderCreateTime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
										<span>付款时间：<fmt:formatDate value="${productItem.payTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
										<span>预定时间：<fmt:formatDate value="${productItem.timePoint }" pattern="HH"></fmt:formatDate>点档</span>
									</div>
								</div>
								<div class="product-info">
									<ul>
										<li>
											<span>${productItem.drinkName }</span>
											<span>搭配：${productItem.teaAdditionName }</span>
											<span>规格：${cupSizeMap[productItem.cupSize] }	甜度：${sweetnessMap[productItem.sweetness] }	冰度：${heatMap[productItem.heat] }</span>
											<span>加料：
												<c:forEach items="${productItem.additions }" var="addition">
													${addition.additionTypeName }
												</c:forEach>
											</span>
										</li>
									</ul>	
								</div>
								<div class="receiver-info">
									<span class="receiver-addr">配送地址：${productItem.locationName }</span>
									<span class="receiver-phone">联系电话：${productItem.receiverContact }</span>
									<a href="#" class="print-link-a">打印</a>
								</div>
							</li>
						</c:forEach>
					</ul>
					<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>				
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		seajs.use(['ajax','dialog','utils'], function(Ajax, Dialog, utils){
			$('#timeRange').daterangepicker({
				format 				: 'YYYY-MM-DD HH:mm:ss',
				timePicker			: true,
				timePicker12Hour	: false,
				timePickerIncrement : 5,
				separator			: '~',
				locale				: {
					applyLabel	: '确定',
	                cancelLabel: '取消',
	                fromLabel: '从',
	                toLabel: '到'
				}
			});
			
			$('#check-all').click(function(){
				$(':checkbox.item-check').prop('checked', true);
			});
			
			$('#uncheck-all').click(function(){
				$(':checkbox.item-check').prop('checked', false);
			});
			$('#print-checked').click(function(){
				var cOrders = [], cOrderIds = [], $checkboxs = [];
				var productIds = "";
				$(':checkbox.item-check:checked:visible').each(function(){
					productIds += $(this).val()+",";
				});
				productIds = productIds.substring(0, productIds.length-1);
				if(productIds.length > 0){
					printProduct(productIds);
				}else{
					Dialog.notice("请选择要打印的产品！","warning");
				}
			});
			
			$('.print-link-a').click(function(){
				var link_a = $(this).closest("li");
				var productId = link_a.find(".item-check").val();
				printProduct(productId);
			});
			
			$('#print-count').click(function(){
				var printCount = $("#printCount").val();
				var timeRange = '${criteria.timeRange}';
				var count = '${pageInfo.count}';
				if(printCount ==''){
					Dialog.notice("请输入要打印的条数！","warning");
					return;
				}else if(!utils.isInteger(printCount)){
					Dialog.notice("请输入整数！","warning");
					return;
				}else if(printCount > count){
					Dialog.notice("您要打印的条数超出范围！","warning");
					return;
				}
				Ajax.ajax('admin/production/print-specify-count-product',{
					printCount: printCount,
					timeRange:timeRange
				},function(html){
					var options = { 
				    		mode 		: "iframe", 
				    		extraHead 	: headElements,
				    		extraCss	: $('base').attr('href') + 'media/admin/production/PrintArea_fac.css'
				    	};
					var headElements ='<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="chrome=1"/>';
					var $container = $('.print-page', html);
					$container.printArea(options);
				});
			});
			
			function printProduct(productIds){
				Ajax.ajax('admin/production/product-print', {
					productIds : productIds
				}, function(html){
					var options = { 
				    		mode 		: "iframe", 
				    		extraHead 	: headElements,
				    		extraCss	: $('base').attr('href') + 'media/admin/production/PrintArea_fac.css'
				    	};
					var headElements ='<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="chrome=1"/>';
					var $container = $('.print-page', html);
					$container.printArea(options);
				});
			}
		})
	})
</script>
<style>
.condition-area {
	margin-top: 10px;
}
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
</style>
</html>