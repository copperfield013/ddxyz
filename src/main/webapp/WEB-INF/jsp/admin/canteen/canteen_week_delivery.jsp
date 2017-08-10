<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<nav style="padding: 1em 0" id="canteen-week-delivery">
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
	</form>
	<div class="row">
		<div class="col-lg-4">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">本周分发信息(<fmt:formatDate value="${criteria.startDate }" pattern="MM月dd日" />~<fmt:formatDate value="${criteria.endDate }" pattern="MM月dd日" />)</h3>
				</div>
				<div class="panel-body">
					<div class="row">
						<label class="col-lg-4">预定时间：</label>
						<div class="col-lg-8">
							<fmt:formatDate value="${delivery.openTime }" pattern="yyyy年MM月dd日 HH时mm分" />
							~
							<fmt:formatDate value="${criteria.closeTime }" pattern="yyyy年MM月dd日 HH时mm分" />
						</div>
					</div>
					<div class="row">
						<label class="col-lg-4">分发时间：</label>
						<div class="col-lg-8">
							<fmt:formatDate value="${delivery.startTime }" pattern="yyyy年MM月dd日 HH时mm分" />
							~
							<fmt:formatDate value="${criteria.endTime }" pattern="yyyy年MM月dd日 HH时mm分" />
						</div>
					</div>
					<div class="row">
						<label class="col-lg-4">分发点：</label>
						<div class="col-lg-8">${delivery.locationName }</div>
					</div>
				</div>
			</div>
		</div>
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
								<th>单价</th>
								<th>预定情况</th>
								<th>操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			
		</div>
	</div>
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

