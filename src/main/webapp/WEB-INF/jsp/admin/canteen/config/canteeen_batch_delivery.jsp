<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="canteen-batch-delivery">
	<div class="page-header">
		<div class="header-title">
			<h1>创建分发</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="bv-form form-horizontal validate-form" action="admin/canteen/config/do_batch_delivery">
					<div class="form-group">
						<label class="col-lg-2 control-label" for="code">预定时间</label>
						<div class="col-lg-3">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">分发时间</label>
						<div class="col-lg-3">
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="name">餐品</label>
						<div class="col-lg-3">
							<select>
								<option></option>
							</select>
							<input type="button" value="创建餐品" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-1 col-lg-2">
							<input type="button" class="btn" id="custom-server-submit-btn" value="提交" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<script>
	$(function(){
		var customConfigAdd = $("#custom-server-add");
		$("#custom-server-submit-btn", customConfigAdd).click(function(){
			$("#custom-server-add-form").submit();
		});
	});
</script>