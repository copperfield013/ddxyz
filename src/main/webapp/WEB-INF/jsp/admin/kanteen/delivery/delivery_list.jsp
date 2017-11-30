<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<title>配送列表（配销${distribution.code }）</title>
<div id="delivery-list">
	<div class="page-header">
		<div class="header-title">
			<h1>配送列表（配销${distribution.code }）</h1>
		</div>
	</div>
	<nav style="padding: 1em 0" id="order-nav">
		<form class="form-inline" action="admin/kanteen/delivery" >
			<input type="hidden" name="distributionId" value="${criteria.distributionId }" />
			<div class="form-group">
				<label class="form-control-title" for="startTime">开始时间</label>
				<input type="text" class="form-control" id="name" name="name" placeholder="名称" value="">
			</div>
			<button type="submit" class="btn btn-primary">查询</button>
			<c:if test="${criteria.distributionId != null}">
				<a href="admin/kanteen/delivery/add/${criteria.distributionId }" target="delivery_add" title="添加配送" class="tab btn btn-default">添加配送</a>
			</c:if>
		</form>
	</nav>
	<table class="table">
		<thead>
			<tr>
				<th>序号</th>
				<th>编码</th>
				<th>配送地点</th>
				<th>配送时间</th>
				<th>支付方式</th>
				<th>订单数</th>
				<th>修改时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${items }" var="item" varStatus="i">
				<c:set var="delivery" value="${item.delivery }" />
				<c:set var="menu" value="${item.menu }" />
				<tr data-id="${delivery.id }" >
					<td>${i.index + 1}</td>
					<td><a class="tab" target="delivery_detail_${delivery.id }" title="配送详情(${delivery.code })" href="admin/kanteen/delivery/detail/${delivery.id }">${delivery.code }</a></td>
					<td><a href="#" >${delivery.locationName}</a></td>
					<td><fmt:formatDate value="${delivery.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/> 
						~ <fmt:formatDate value="${delivery.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td>${paywayMap[delivery.payWay ] }</td>
					<td>
						<c:choose>
							<c:when test="${item.orderCount > 0 }">
								<a class="tab" href="admin/kanteen/order?deliveryId=${delivery.id }" target="delivery_order_list_${delivery.id }">${item.orderCount }</a>
							</c:when>
							<c:otherwise>
								${item.orderCount }
							</c:otherwise>
						</c:choose>
					</td>
					<td><fmt:formatDate value="${delivery.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
						<a>发布</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
</div>
<script>
	$(function(){
		seajs.use(['ajax', 'dialog', 'utils'], function(Ajax, Dialog, utils){
		});
	});
</script>

