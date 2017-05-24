<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<nav style="padding: 1em 0" id="delivery-plan-list">
	<form class="form-inline" action="admin/config/plan/plan-list" >
		<div class="form-group">
			<label class="form-control-title" for="contactName">配送地点</label>
			<input type="text" css-width="8em" class="form-control" id="locationName" name="locationName" placeholder="配送地点" value="${criteria.locationName }">
		</div>
		<button type="submit" class="btn btn-default">查询</button>
		<a 	class="btn btn-default tab" 
			href="admin/config/plan/plan-add" title="添加新计划"
			target="plan-add-page"
			>添加新计划</a>
	</form>
	<table class="table">
		<thead>
			<tr>
				<th>序号</th>
				<th>配送周期</th>
				<th>配送地点</th>
				<th>周期开始时间</th>
				<th>周期结束时间</th>
				<th>配送最大数</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="deliveryPlan" varStatus="i">
				<tr data-id="${deliveryPlan.id }">
					<td>${i.index + 1}</td>
					<td><a href="#" page-type="tab" target="@plan-detail-${deliveryPlan.id }" title="">${deliveryPlan.period }</a></td>
					<td>${deliveryPlan.location.name }</td>
					<td><fmt:formatDate value="${deliveryPlan.startDate }" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatDate value="${deliveryPlan.endDate }" pattern="yyyy-MM-dd"/></td>
					<td>${deliveryPlan.maxCount }</td>
					<td>
						<c:choose>
							<c:when test="${deliveryPlan.disabled == null }">
								${delivery.disabled }
								<a href="#" class="plan-disabled">禁用</a>
							</c:when>
							<c:otherwise>
								<a href="#" class="plan-abled">启用</a>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
</nav>
<script>
	$(function(){
		seajs.use(['ajax', 'dialog', 'utils'], function(Ajax, Dialog, utils){
			var planList = $("#delivery-plan-list");
			/* $("#add_new_plan", planList).click(function(){
				Dialog.openDialog("admin/config/plan/plan-add","添加新计划","plan-add");
			}); */
		
			$(".plan-disabled", planList).click(function(){
				changePlanDisabled.apply(this, [1, '确定禁用该配送计划？']);
				return false;
			});
			
			$(".plan-abled", planList).click(function(){
				changePlanDisabled.apply(this, ['', '确认启用该配送计划？']);
				return false;
			});
		
			function changePlanDisabled(status, confirmMsg){
				var $row = $(this).closest('tr[data-id]');
				var planId = $row.attr('data-id');
				var page = $(this).getLocatePage();
				if(planId){
					Dialog.confirm(confirmMsg,function(isYes){
						if(isYes){
							Ajax.ajax("admin/config/plan/change-disabled", {
								'id' : planId,
								'disabled' : status	
							},{
								page : page
							});
						}
					});
				}
			}
		});
		
	});
</script>

