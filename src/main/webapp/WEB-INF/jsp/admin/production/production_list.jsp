<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
	.ui-content{
		margin-top: 1em;
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
<script src="${basePath }media/admin/plugins/printArea/jquery.PrintArea.js"></script>
<div class="ui-page" id="product-list-page">
	<div class="ui-body">
		<div class="ui-content">
			<form class="" action="admin/production/product-list">
				<div class="condition-area">
					<label>订单日期：</label>
					<input type="text" class="form-control" id="timeRange" name="timeRange" readonly="readonly" value="${criteria.timeRange }" css-width="25em"  css-cursor="text" css-display="inline-block"/>
					<label>预定时间：</label>
					<select id="timePoint" name="timePoint" >
						<option value="">--全部--</option>
						<c:forEach var="i" begin="9" end="21" step="1"> 
							<option value="${i }" ${criteria.timePoint == i? 'selected="selected"': '' }>${i }点</option>
						</c:forEach>
					</select>
					<input id="confirm" class="btn" style="width:4em;" type="submit" value="确定"/>
					<div class="widget-buttons">
					    <div class="btn-group">
					        <a class="btn" id="check-all">勾选所有</a>
					        <a class="btn dropdown-toggle" data-toggle="dropdown"><i class="fa fa-angle-down"></i></a>
					        <ul class="dropdown-menu dropdown-white pull-left">
					            <li>
					                <a class="btn" id="uncheck-all">取消勾选</a>
					            </li>
					        </ul>
					    </div>
					</div>
					<!-- <input id="check-all" type="button" class="btn" style="width:6em;" type="submit" value="勾选全部" >
					<input id="uncheck-all" type="button" class="btn" style="width:6em;" type="submit" value="取消勾选"/>
					<input id="print-checked" type="button"class="btn" style="width:6em;" type="submit" value="打印勾选" /> -->
					打印前 <input type="number" min="1" class="form-control" css-display="inline-block" id="printCount"  name="printCount" css-width="8em"/> 条
					<input id="print-count" type="button" class="btn" style="width:4em;" type="submit" value="打印">
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
								<span class="order-status">${statusCnameMap[productItem.productStatus] }</span>
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
<script type="text/javascript">
	$(function(){
		seajs.use(['ajax','dialog','utils'], function(Ajax, Dialog, utils){
			var $page =$('#product-list-page');
			$('#timeRange', $page).daterangepicker({
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
			
			$('#check-all', $page).click(function(){
				$(':checkbox.item-check', $page).prop('checked', true);
			});
			
			$('#uncheck-all', $page).click(function(){
				$(':checkbox.item-check', $page).prop('checked', false);
			});
			$('#print-checked', $page).click(function(){
				var cOrders = [], cOrderIds = [], $checkboxs = [];
				var productIds = "";
				$(':checkbox.item-check:checked:visible', $page).each(function(){
					productIds += $(this).val()+",";
				});
				productIds = productIds.substring(0, productIds.length-1);
				if(productIds.length > 0){
					printProduct(productIds);
				}else{
					Dialog.notice("请选择要打印的产品！","warning");
				}
			});
			
			$('.print-link-a', $page).click(function(){
				var link_a = $(this).closest("li");
				var productId = link_a.find(".item-check").val();
				printProduct(productId);
			});
			
			$('#print-count', $page).click(function(){
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