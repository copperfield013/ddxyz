<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="fans-edit">
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form id="fans-edit-form" class="bv-form form-horizontal validate-form" action="admin/fans/doEdit">
					<input type="hidden" id="openid" name="openid" value="${fans.openid }"/>
					<div class="form-group">
						<label class="col-lg-2 control-label">昵称</label>
						<div class="col-lg-3">
							${fans.nickname }
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="remark">备注名</label>
						<div class="col-lg-3">
							<input type="text" class="form-control" id="remark" name="remark" value="${fans.remark }">
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-2 col-lg-2">
							<input type="submit" class="btn" id="fans-edit-submit-btn" value="提交" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
