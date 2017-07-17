<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="message-config-edit">
	<div class="page-header">
		<div class="header-title">
			<h1>编辑</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form id="message-config-edit-form" class="bv-form form-horizontal validate-form" action="admin/message/autoReply/doEdit">
					<input type="hidden" id="messageConfigIdHidden" name="id" value="${messageConfig.id }"/>
					<div class="form-group">
						<label class="col-lg-1 control-label" for="name">规则内容</label>
						<div class="col-lg-3">
							<input type="hidden" name="ruleExpression" value="${messageConfig.ruleExpression }"/>
							${messageConfig.ruleExpression }
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-1 control-label" for="address">自动回复内容</label>
						<div class="col-lg-3">
							<input type="text" id="back-content" class="form-control" name="backContent" value="${messageConfig.backContent }"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-1 control-label" for="map">优先级</label>
						<div class="col-lg-3">
							<input id="level" class="form-control" type="number" name="level" value="${messageConfig.level }"
								data-bv-notempty="true"
								data-bv-notempty-message="优先级不能为空"
							/>
					    </div>
					</div>
					<div class="form-group">
						<label class="col-lg-1 control-label" for="map">备注</label>
						<div class="col-lg-3">
							<input type="text" id="remarks" class="form-control" name="remarks" value="${messageConfig.remarks }"/>
					    </div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-1 col-lg-2">
							<input type="submit" class="btn" id="message-config-submit-btn" value="提交" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
