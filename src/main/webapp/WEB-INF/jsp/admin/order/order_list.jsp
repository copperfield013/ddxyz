<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
</style>
<nav style="padding: 1em 0" id="order-nav">
	<form class="form-inline" action="admin/order-manage/order-list" >
		<div class="form-group">
			<label class="form-control-title">付款时间</label>
	        <div class="controls" css-display="inline-block">
	            <div class="input-group">
	                <input type="text" class="form-control" id="timeRange" name="timeRange" readonly="readonly" 
	                	value="${criteria.timeRange }" css-width="25em"  css-cursor="text" />
	                <span class="input-group-addon">
	                    <i class="fa fa-calendar"></i>
	                </span>
	            </div>
	        </div>
			<label class="form-control-title" for="orderCode">订单号</label>
			<input type="text" css-width="8em" class="form-control" id="orderCode" name="orderCode" placeholder="订单号" value="${criteria.orderCode }">
			<label class="form-control-title" for="contactName">配送地点</label>
			<input type="text" css-width="8em" class="form-control" id="locationName" name="locationName" placeholder="配送地点" value="${criteria.locationName }">
			<label class="form-control-title" for="contactNumber">时间档</label>
			<select id="timePoint" name="timePoint">
				<option value="">--全部--</option>
				<c:forEach var="i" begin="9" end="21" step="1"> 
					<option value="${i }" ${criteria.timePoint == i? 'selected="selected"': '' }>${i }点</option>
				</c:forEach>
			</select>
		</div>
		<button type="submit" class="btn btn-default">查询</button>
	</form>
</nav>
<table class="table">
	<thead>
		<tr>
			<th>序号</th>
			<th>订单号</th>
			<th>配送地点</th>
			<th>配送时间档</th>
			<th>联系号码</th>
			<th>杯数</th>
			<th>付款时间</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${orderList }" var="order" varStatus="i">
			<tr data-id="${order.id }" data-code="${order.orderCode }">
				<td>${i.index + 1}</td>
				<td><a href="admin/order-manage/order-detail?id=${order.id }" page-type="tab" target="@order-detail-${order.id }" title="订单${order.orderCode }">${order.orderCode }</a></td>
				<td>${order.locationName }</td>
				<td>
					<fmt:formatDate value="${order.timePoint }" pattern="HH"/>点档
				</td>
				<td>${order.receiverContact }</td>
				<td>${cupCountMap[order.id] }</td>
				<td>
					<fmt:formatDate value="${order.payTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<a href="#" class="">退款</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
<script>
	$(function(){
		seajs.use(['ajax', 'dialog', 'utils'], function(Ajax, Dialog, utils){
			var nav = $("#order-nav");
			$('#timeRange', nav).daterangepicker({
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
	               /*  weekLabel: 'W',
	                customRangeLabel: 'Custom Range',
	                daysOfWeek: moment()._lang._weekdaysMin.slice(),
	                monthNames: moment()._lang._monthsShort.slice(),
	                firstDay: 0 */
				}
			});
		});
	});
</script>

