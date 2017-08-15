<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
	#canteen-week-table .row.delivery-row:not(:last-child){
		border-bottom: 1px solid #ccc;
	}
	#canteen-week-table .row.delivery-row{
		padding: 10px 0;
	}
	#canteen-week-table .order-wares-item{
		display: block;
	}
	#canteen-week-table table.table.td{
		text-align: center;
		vertical-align: middle;
	}
	#canteen-week-table .head-operate{
		float: right; 
	}
	#canteen-week-table .head-operate>.btn{
		margin: 0, 5px;
	}
</style>
<nav style="padding: 1em 0" id="canteen-week-table">
	<form class="form-inline" action="admin/canteen/config/week_delivery" >
		<div class="form-group">
			<label class="form-control-title" for="contactName">日期</label>
			<input type="text" class="form-control" id="selectDate" name="date" readonly="readonly" css-cursor="text" value="${criteria.date }" />
		</div>
		<button type="submit" class="btn btn-default">查询</button>
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
								<label class="col-lg-4"><br/>预定时间：<br/></label>
								<div class="col-lg-8">
									<fmt:formatDate value="${delivery.openTime }" pattern="yyyy年MM月dd日 HH时mm分" /><br/>
									~<br/>
									<fmt:formatDate value="${delivery.closeTime }" pattern="yyyy年MM月dd日 HH时mm分" />
								</div>
							</div>
							<div class="row delivery-row">
								<label class="col-lg-4"><br/>分发时间：<br/></label>
								<div class="col-lg-8">
									<fmt:formatDate value="${delivery.timePoint }" pattern="yyyy年MM月dd日 HH时mm分" /><br/>
									~<br/>
									<fmt:formatDate value="${delivery.claimEndTime }" pattern="yyyy年MM月dd日 HH时mm分" />
								</div>
							</div>
							<div class="row delivery-row">
								<label class="col-lg-4">分发点：</label>
								<div class="col-lg-8">${delivery.locationName }</div>
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
						<h3 class="panel-title">商品列表</h3>
					</div>
					<div class="panel-body">
						<table class="table">
							<thead>
								<tr>
									<th>序号</th>
									<th>产品名称</th>
									<th>收件人</th>
									<th>份数</th>
									<th>单价</th>
									<th>下单时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${orderItems }" var="item" varStatus="i">
									<tr order-id="${item.orderId }">
										<td>${i.index + 1 }</td>
										<td>${item.waresName }</td>
										<td>${item.receiverName }</td>
										<td>${item.count }</td>
										<td><fmt:formatNumber value="${item.unitPrice /100 }" pattern="0.00" /> 元/${item.priceUnit } </td>
										<td><fmt:formatDate value="${item.orderTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
			var $page = $('#canteen-week-table');
			$('#selectDate', $page).datepicker({
				format		: 'yyyy-mm-dd',
				weekStart	: 1,
				daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],  
                monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',  
                        '七月', '八月', '九月', '十月', '十一月', '十二月' ]
			});
		});
		
	});
</script>

