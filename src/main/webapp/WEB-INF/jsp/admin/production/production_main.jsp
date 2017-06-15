<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
	#production_main .stat-title {
	    color: #555;
	    font-size: 1.2em;
	    width: 100%;
	    height: 2.5em;
	    line-height: 2.5em;
	    padding-left: 15px;
	    margin-bottom: 10px;
	    font-weight: bold;
	    text-align: center;
	    border-top: 1px solid #dfdfdf;
	}
	#production_main .stat-opr{
	    height: 2em;
	    line-height: 2em;
	    padding-right: 3em;
	    font-size: 1.3em;
	}
	#production_main .stat-opr>*{
		float: right;
		margin-right: 1em;
	}
</style>
<div id="production_main">
	<div class="page-header">
		<div class="header-title">
			<h1>生产管理</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="col-lg-12">
			<!-- 查询条件区域 -->
			<div class="row">
				<form class="bv-form form-horizontal">
					<div class="form-group">
						<div class="col-lg-1 control-label">付款日期</div>
						<div class="col-lg-3">
							<input type="text" id="pay-time" name="payTime" class="form-control datepicker" readonly="readonly" 
			               	css-cursor="text" data-date-format="yyyy-mm-dd"/>
						</div>
						<div class="col-lg-1 control-label">时间点</div>
						<div class="col-lg-3">
							<select class="form-control" id="time-point-select" name="timePoint">
								<option value="">请选择</option>
								<c:forEach items="${timePointList }" var="timePoint">
									<option value="${timePoint.hour }">${timePoint.hour }点</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-lg-1 control-label">配送地点</div>
						<div class="col-lg-3">
							<!-- <input type="text" class="form-control"  /> -->
							<select class="form-control" id="location-select" name="locationId">
								<option value="">请选择</option>
								<c:forEach items="${locationList }" var="location">
									<option value="${location.id }">${location.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-1 control-label">打印状态</div>
						<div class="col-lg-3">
							<select class="form-control" id="print-status" name="printStatus">
								<option value="">全部</option>
								<option value="0">未打印</option>
								<option value="1">已打印</option>
							</select>
						</div>
						<div class="col-lg-1 control-label">订单号</div>
						<div class="col-lg-3">
							<input id="order-code" name="orderCode" type="text" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-4 col-lg-4">
							<input type="button" class="btn btn-block btn-primary" id="query" value="查询" >
						</div>
					</div>
				</form>
			</div>
			
			<!-- 统计区域 -->
			<div class="row">
				<div class="stat-title">
					今日生产情况统计
				</div>
				<div class="stat-opr">
					<a href="#" class="fa fa-refresh" title="刷新" ></a>
				</div>
				<div class="col-lg-12">
					<table class="table">
						<thead>
							<tr>
								<th>时间点</th>
								<th>待打印杯数</th>
								<th>已打印杯数</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${timePointList }" var="timePoint">
								<tr>
									<td>${timePoint.hour }</td>
									<td>${printedMap[timePoint] }</td>
									<td>${notPrintMap[timePoint] }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			
		</div>
	</div>
</div>
<script>
	$(function(){
		seajs.use(['tab'], function(Tab){
			var $page = $('#production_main');
			$('#query', $page).click(function(){
				var url = 'admin/production/query?payTimeStr='+ $("#pay-time", $page).val() 
							+ '&timePoint=' + $("#time-point-select option:selected", $page).val()
							+ '&locationId=' + $("#location-select option:selected", $page).val()
							+ '&locationName=' + encodeURI(encodeURI($("#location-select option:selected", $page).text()))
							+ '&printStatus=' + $("#print-status option:selected", $page).val()
							+ '&orderCode=' + $("#order-code", $page).val();
				/* url = encodeURI(url); */
				Tab.openFrameInTab(url, 'production_query');
			});
		})
	});
</script>