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
							<input id="check-all" type="button" value="勾选全部" >
							<input id="uncheck-all" type="button" value="取消勾选"/>
						</span>
						<span>
							<input id="print-checked" type="button" value="打印勾选" />
						</span>
						<span>
							<input id="confirm" class="btn" style="width:4em;" type="submit" value="确定"/>
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
						<!-- <li class="product-item">
							<div class="order-info">
								<span class="order-code">订单编号:M12345678</span>
								<span class="order-status">[已发货]</span>
								<div class="order-time">
									<span>下单时间：2017-04-25 11:00:03</span>
									<span>付款时间：2017-04-25 11:01:10</span>
									<span>预定时间：7点档</span>
								</div>
							</div>
							<div class="product-info">
								<ul>
									<li>
										<span>奶茶&nbsp;&nbsp;搭配：红茶&nbsp;规格：中杯&nbsp;甜度：3分甜&nbsp;冰度：5分&nbsp;加料：珍珠</span>
									</li>
									<li>
										<span>奶茶&nbsp;&nbsp;搭配：绿茶&nbsp;规格：中杯&nbsp;甜度：5分甜&nbsp;冰度：5分&nbsp;加料：珍珠</span>
									</li>
								</ul>
							</div>
							<div class="receiver-info">
								<span class="receiver-addr">配送地址：天虹商场</span>
								<span class="receiver-phone">联系电话：1234567890</span>
								<a href="#" >打印</a>
							</div>
						</li> -->
					</ul>
					<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		seajs.use(['ajax','dialog'], function(Ajax, Dialog){
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
				var aaa = $(this).closest("li");
				var productId = aaa.find(".item-check").val();
				console.log(aaa);
				console.log(productId);
				printProduct(productId);
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