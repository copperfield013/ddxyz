<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<nav style="padding: 1em 0" id="canteen-wares-list">
	<form class="form-inline" action="admin/config/plan/plan-list" >
		<div class="form-group">
			<label class="form-control-title" for="contactName">日期</label>
			<input type="text" class="form-control timeRange" id="orderTimeRange" name="orderTimeRange" readonly="readonly" 
	                			css-cursor="text" />
		</div>
		<button type="submit" class="btn btn-default">查询</button>
		<a class="btn btn-primary tab" href="admin/canteen/config/generate_delivery" title="创建配送" target="canteen-batch-delivery" >创建</a>
		<a class="btn btn-primary tab" 
			href="admin/canteen/config/batch_delivery" title="批量生成配送"
			target="canteen-config-batch-delivery">批量生成</a>
		<span style="float: right;">
			<fmt:formatDate value="${criteria.startDate }" pattern="yyyy-MM-dd"/>
			~
			<fmt:formatDate value="${criteria.endDateIncluded }" pattern="yyyy-MM-dd"/>
		</span>
	</form>
	<div>
		
	</div>
	<table class="table">
		<thead>
			<tr>
				<th>序号</th>
				<th>餐品名称</th>
				<th>预约时间</th>
				<th>分发时间</th>
				<th>分发地点</th>
				<th>预定情况</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${deliveryWaresList }" var="dWares" varStatus="i">
				<tr>
					<td>${i.index + 1 }</td>
					<td>${dWares.name }</td>
					<td>
						<fmt:formatDate value="${dWares.orderOpenTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
					 	~
					 	<fmt:formatDate value="${dWares.orderCloseTime }" pattern="yyyy-MM-dd HH:mm:ss"/>	
					</td>
					<td>
						<fmt:formatDate value="${dWares.claimStartTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
					 	~
					 	<fmt:formatDate value="${dWares.claimEndTime }" pattern="yyyy-MM-dd HH:mm:ss"/>	
					 </td>
					 <td>
					 	${dWares.locationName }
					 </td>
					 <td>
					 	${dWares.currentCount }/${dWares.maxCount == null? '无限制': dWares.maxCount}
					 </td>
					<td>
						<a href="#" class="dWares-pause">暂停预定</a>
						<a href="#" class="dWares-del">删除</a>
					</td> 
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
</nav>
<script>
	$(function(){
		seajs.use(['ajax', 'dialog', 'utils'], function(Ajax, Dialog, utils){
			var $page = $('#canteen-wares-list');
			$('.timeRange', $page).datepicker({
				format	:	'yyyy-mm-dd'
			});
		});
		
	});
</script>

