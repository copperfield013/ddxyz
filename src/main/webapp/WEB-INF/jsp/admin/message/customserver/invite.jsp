<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="custom-server-invite">
	<div class="page-header">
		<div class="header-title">
			<h1>邀请绑定客服账号</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form id="custom-server-invite-form" class="bv-form form-horizontal validate-form" action="admin/message/customServer/doInvite">
					<div class="form-group">
						<label class="col-lg-2 control-label" for="code">客服账号</label>
						<div class="col-lg-3">
							<input type="hidden" id="kf-account-hidden" name="kf_account" value="${kf_account }"/>
							${kf_account }
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="name">受邀请微信号</label>
						<div class="col-lg-3">
							<input type="text" id="invite-wx" class="form-control" name="invite_wx" placeholder="请输入受邀请微信号"
								data-bv-notempty="true"
								data-bv-notempty-message="受邀请微信号不能为空"
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
		var customConfigInvite = $("#custom-server-invite");
		$("#custom-server-submit-btn", customConfigInvite).click(function(){
			$("#custom-server-invite-form").submit();
		});
	});
</script>