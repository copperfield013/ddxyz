<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="order-list">
	<div class="page-header">
		<div class="header-title">
			<h1>订单列表</h1>
		</div>
	</div>
	<div class="page-body">
		<nav>
			<form class="form-inline" action="${action }">
				<input type="hidden" name="distributionId" value="${criteria.distributionId }" />
				<input type="hidden" name="deliveryId" value="${criteria.deliveryId }" />
				<div class="form-group">
					<label class="form-control-title" for="orderCode">订单号</label>
					<input type="text" class="form-control" id="orderCode" name="orderCode" />
				</div>
			</form>
		</nav>
		<table class="table">
			<thead>
				<tr>
					<th>序号</th>
					<th>订单号</th>
					<th>下单时间</th>
					<th>收货人</th>
					<th>收货联系方式</th>
					<th>选择配送</th>
					<th>订单状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderList }" var="orderItem" varStatus="i">
					<c:set var="order" value="${orderItem.order }" />
					<c:set var="delivery" value="${order.delivery }" />
					<tr data-id="${order.id }">
						<td>${i.index + 1 }</td>
						<td>${order.code }</td>
						<td><fmt:formatDate value="${order.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /> </td>
						<td>${order.receiverName }</td>
						<td><a href="admin/kanteen/delivery/detail/${delivery.id }">${delivery.code }</a></td>
						<td>
							<c:if test="${order.payway == 'wxpay' }">
								<a confirm="确认为订单${order.code }退款？" href="admin/kanteen/order/refund/${order.id }">退款</a>
							</c:if>
							<c:if test="${order.payway == 'spot' }">
								<a confirm="确认取消订单${order.code }" href="admin/kanteen/order/cancel/${order.id }">取消</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
	</div>
</div>
<script type="text/javascript">
	
</script>