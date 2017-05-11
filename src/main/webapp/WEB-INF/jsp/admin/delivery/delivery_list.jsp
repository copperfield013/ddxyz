<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<script src="${basePath }media/admin/plugins/printArea/jquery.PrintArea.js"></script>
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
<div class="ui-page" id="delivery-list-page">
	<div class="ui-body">
		<div class="ui-content">
			<form class="" action="admin/delivery/delivery_list">
				<div class="condition-area">
					<label>订单编号：</label>
					<input type="text" class="form-control" css-display="inline-block" css-width="10em" name="orderCode" value="${criteria.orderCode }" />
					<label>订单日期：</label>
					<input type="text" class="form-control" id="timeRange" name="timeRange" readonly="readonly" value="${criteria.timeRange }" css-width="25em"  css-cursor="text" css-display="inline-block"/>
					<label>预定时间点：</label>
					<select id="timePoint" name="timePoint">
						<option value="">--全部--</option>
						<c:forEach var="i" begin="9" end="21" step="1"> 
							<option value="${i }" ${criteria.timePoint == i? 'selected="selected"': '' }>${i }点</option>
						</c:forEach>
					</select>
					<label>配送地点：</label>
					<input type="text" class="form-control" css-display="inline-block" css-width="8em" name="locationName" value="${criteria.locationName }" />
					<input id="confirm" class="btn" style="width:4em;" type="submit" value="检索"/>
				</div>
			</form>
			<div class="product-info-area">
				<ul class="product-list">
					<c:forEach items="${orderList }" var="plainOrder">
						<li class="product-item">
							<div class="order-info">	
								<span class="order-code">
									<input type="hidden" id="orderIdHidden" name="orderId" value="${plainOrder.id }">
									订单编号:${plainOrder.orderCode }
								</span>
								<span class="order-status">[已发货]</span>
								<div class="order-time">
									<span>下单时间：<fmt:formatDate value="${plainOrder.createTime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
									<span>付款时间：<fmt:formatDate value="${plainOrder.payTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
									<span>预定时间：<fmt:formatDate value="${plainOrder.timePoint }" pattern="HH"></fmt:formatDate>点档</span>
								</div>
							</div>
							<div class="product-info">
								<ul>
									<c:forEach items="${itemMap[plainOrder] }" var="plainOrderDrinkItem">
										<li>
											<span>
												${plainOrderDrinkItem.drinkName }:&nbsp;&nbsp;
												${plainOrderDrinkItem.teaAdditionName }&nbsp;&nbsp;
												${cupSizeMap[plainOrderDrinkItem.cupSize] }&nbsp;&nbsp;
												${sweetnessMap[plainOrderDrinkItem.sweetness] }&nbsp;&nbsp;
												${heatMap[plainOrderDrinkItem.heat] }&nbsp;&nbsp;
												加料：
												<c:forEach items="${additionMap[plainOrderDrinkItem] }" var="addition">
													${addition.additionTypeName }
												</c:forEach>
											</span>
										</li>
									</c:forEach>
								</ul>	
							</div>
							<div class="receiver-info">
								<span class="receiver-addr">配送地址：${plainOrder.locationName }</span>
								<span class="receiver-phone">联系电话：${plainOrder.receiverContact }</span>
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
			var $page =$('#delivery-list-page');
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
			
			
			$('.print-link-a', $page).click(function(){
				var link_a = $(this).closest("li");

				var orderId = link_a.find("input[name='orderId']").val();
				console.log(orderId);
				printOrder(orderId);
			});
			
			function printOrder(orderId){
				Ajax.ajax('admin/delivery/delivery-print', {
					orderId: orderId
				}, function(html){
					var options = { 
				    		mode 		: "iframe", 
				    		extraHead 	: headElements,
				    		extraCss	: $('base').attr('href') + 'media/admin/delivery/PrintArea.css?${RES_STAMP}'
				    	};
					var headElements ='<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="chrome=1"/>';
					var $container = $('.PrintArea', html);
					$container.printArea(options);
				});
			}
		})
	})
</script>