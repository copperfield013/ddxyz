<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="location-add">
	<div class="page-header">
		<div class="header-title">
			<h1>添加配送地点</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form id="location-add-form" class="bv-form form-horizontal validate-form" action="admin/config/location/doAdd">
					<div class="form-group">
						<label class="col-lg-1 control-label" for="code">编码</label>
						<div class="col-lg-3">
							<input id="location-code" class="form-control" type="text" name="code" placeholder="长度为4的数字"
								data-bv-notempty="true"
								data-bv-notempty-message="编码不能为空"
								pattern="^[0-9]{4}$"
								data-bv-regexp-message="必须是长度为4的数字"
								data-bv-remote="true"
								data-bv-remote-url="admin/config/location/checkCode"
								data-bv-remote-message="编码已经存在"
							/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-1 control-label" for="name">名称</label>
						<div class="col-lg-3">
							<input id="location-name" class="form-control" type="text" name="name" placeholder="配送地点名称"
								data-bv-notempty="true"
								data-bv-notempty-message="配送地点不能为空"
							/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-1 control-label" for="address">详细地址</label>
						<div class="col-lg-3">
							<input id="location-address" class="form-control" type="text" name="address"
								data-bv-notempty="true"
								data-bv-notempty-message="详细地址不能为空"
							/>
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-1 col-lg-2">
							<input type="submit" class="btn" id="location-add-submit-btn" value="提交" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<script>
	$(function(){
		seajs.use(['ajax', 'dialog'], function(Ajax, Dialog){
		});
	});
</script>