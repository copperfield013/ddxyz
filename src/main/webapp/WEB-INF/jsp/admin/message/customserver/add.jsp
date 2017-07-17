<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="custom-server-add">
	<div class="page-header">
		<div class="header-title">
			<h1>添加新客服</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form id="custom-server-add-form" class="bv-form form-horizontal validate-form" action="admin/message/customServer/doAdd">
					<div class="form-group">
						<label class="col-lg-2 control-label" for="code">客服账号前缀</label>
						<div class="col-lg-3">
							<input type="text" id="kf-account-prefix" class="form-control" name="kf_account_prefix" placeholder="请输入账号前缀"
									data-bv-notempty="true"
									data-bv-notempty-message="客服账号前缀不能为空"
								/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">公众号微信号</label>
						<div class="col-lg-3">
							${wxAccount }
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="name">客服昵称</label>
						<div class="col-lg-3">
							<input type="text" id="nickname" class="form-control" name="nickname" placeholder="请输入昵称"
								data-bv-notempty="true"
								data-bv-notempty-message="客服昵称不能为空"
							/>
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