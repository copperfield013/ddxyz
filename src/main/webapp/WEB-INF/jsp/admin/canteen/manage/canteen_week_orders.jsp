<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
	#canteen-week-orders .row.delivery-row:not(:last-child){
		border-bottom: 1px solid #ccc;
	}
	#canteen-week-orders .row.delivery-row{
		padding: 10px 0;
	}
	#canteen-week-orders .order-wares-item{
		display: block;
	}
	#canteen-week-orders table.table.td{
		text-align: center;
		vertical-align: middle;
	}
	#canteen-week-orders .head-operate{
		float: right; 
	}
</style>
<nav style="padding: 1em 0" id="canteen-week-orders">
	<form class="form-inline" action="admin/canteen/manage/week_orders" >
		<div class="form-group">
			<label class="form-control-title" for="contactName">日期</label>
			<input type="text" class="form-control" id="selectDate" name="date" readonly="readonly" css-cursor="text" value="${criteria.date }" />
		</div>
		<button type="submit" class="btn btn-default">查询</button>
		<c:if test="${delivery == null }">
			<a class="btn btn-primary tab" href="admin/canteen/config/generate_delivery?date=${criteria.date }" title="创建配送" target="canteen-batch-delivery" >创建</a>
		</c:if>
	</form>
	<div class="row" style="margin-top: 1em;">
		<div class="col-lg-4">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">本周分发信息(<fmt:formatDate value="${criteria.startDate }" pattern="MM月dd日" />~<fmt:formatDate value="${criteria.endDateIncluded }" pattern="MM月dd日" />)</h3>
				</div>
				<div class="panel-body">
					<c:choose>
						<c:when test="${delivery != null }">
							<div class="row delivery-row">
								<label class="col-lg-4 "><br/>预定时间：<br/></label>
								<div class="col-lg-8 ">
									<fmt:formatDate value="${delivery.openTime }" pattern="yyyy年MM月dd日 HH时mm分" /><br/>
									~<br/>
									<fmt:formatDate value="${delivery.closeTime }" pattern="yyyy年MM月dd日 HH时mm分" />
								</div>
							</div>
							<div class="row delivery-row">
								<label class="col-lg-4 "><br/>分发时间：<br/></label>
								<div class="col-lg-8 ">
									<fmt:formatDate value="${delivery.timePoint }" pattern="yyyy年MM月dd日 HH时mm分" /><br/>
									~<br/>
									<fmt:formatDate value="${delivery.claimEndTime }" pattern="yyyy年MM月dd日 HH时mm分" />
								</div>
							</div>
							<div class="row delivery-row">
								<label class="col-lg-4 ">分发点：</label>
								<div class="col-lg-8 ">${delivery.locationName }</div>
							</div>
						</c:when>
						<c:otherwise>
							<div>
								本周没有数据
							</div>
						</c:otherwise>
					</c:choose>
					
				</div>
			</div>
		</div>
		<c:if test="${delivery != null }">
			<div class="col-lg-8">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">
							商品列表
							<a id="export" class="btn btn-xs btn-info head-operate" href="#">导出</a>
						</h3>
						
					</div>
					<div class="panel-body">
						<table class="table">
							<thead>
								<tr>
									<th>序号</th>
									<th>订单号</th>
									<th>领取人</th>
									<th>部门</th>
									<th>手机号</th>
									<th>订单明细</th>
									<th>应收款</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${orderItems }" var="item" varStatus="i">
									<tr order-id="${item.orderId }">
										<td>${i.index + 1 }</td>
										<td>${item.orderCode }</td>
										<td>${item.receiverName }</td>
										<td>${item.depart }</td>
										<td>${item.receiverContact }</td>
										<td>
											<c:forEach items="${item.waresItemsList }" var="waresItem">
												<span class="order-wares-item">${waresItem.waresName }×${waresItem.count }</span>
											</c:forEach>
										</td>
										<td><fmt:formatNumber value="${item.totalPrice / 100 }" pattern="0.00" />元</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
					</div>
				</div>
			</div>
		</c:if>
	</div>
</nav>
<script>
	$(function(){
		seajs.use(['ajax', 'dialog', 'utils'], function(Ajax, Dialog, utils){
			var $page = $('#canteen-week-orders');
			$('#selectDate', $page).datepicker({
				format		: 'yyyy-mm-dd',
				weekStart	: 1
			});
			$('#export', $page).click(function(){
				location.href = 'admin/canteen/manage/export_orders/${delivery.id}';
			});
			
		});
		
	});
</script>

