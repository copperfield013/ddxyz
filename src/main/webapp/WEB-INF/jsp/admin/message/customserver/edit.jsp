<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="custom-server-edit">
	<div class="page-header">
		<div class="header-title">
			<h1>编辑</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form id="custom-server-edit-form" class="bv-form form-horizontal validate-form" action="admin/message/customServer/doEdit">
					<input type="hidden" id="kf-account" name="kf_account" value="${kf_account }"/>
					<div class="form-group">
						<label class="col-lg-1 control-label">客服账号</label>
						<div class="col-lg-3">
							${kf_account }
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-1 control-label" for="name">客服昵称</label>
						<div class="col-lg-3">
							<input type="text" id="nickname" class="form-control" name="nickname" value="${nickname }"/>
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-1 col-lg-2">
							<input type="submit" class="btn" id="custom-server-submit-btn" value="提交" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
