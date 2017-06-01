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
						<div class="col-lg-1 control-label">下单日期</div>
						<div class="col-lg-3">
							<input type="text" class="form-control datepicker" readonly="readonly" 
			               	css-cursor="text" data-date-format="yyyy-mm-dd"/>
						</div>
						<div class="col-lg-1 control-label">时间点</div>
						<div class="col-lg-3">
							<select class="form-control">
								<option>8点</option>
								<option>9点</option>
								<option>10点</option>
								<option>11点</option>
								<option>12点</option>
								<option>13点</option>
								<option>14点</option>
								<option>15点</option>
								<option>16点</option>
								<option>17点</option>
								<option>18点</option>
								<option>19点</option>
								<option>20点</option>
								<option>21点</option>
								<option>22点</option>
							</select>
						</div>
						<div class="col-lg-1 control-label">配送地点</div>
						<div class="col-lg-3">
							<input type="text" class="form-control"  />
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-1 control-label">打印状态</div>
						<div class="col-lg-3">
							<select class="form-control">
								<option>全部</option>
								<option>未打印</option>
								<option>已打印</option>
							</select>
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
							<tr>
								<td>13</td>
								<td>50</td>
								<td>2</td>
							</tr>
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
				Tab.openFrameInTab('admin/production/query', 'production_query');
			});
		})
	});
</script>