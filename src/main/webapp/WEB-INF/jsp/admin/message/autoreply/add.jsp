<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="message-config-add">
	<div class="page-header">
		<div class="header-title">
			<h1>添加新的自动回复</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form id="message-config-add-form" class="bv-form form-horizontal validate-form" action="admin/message/autoReply/doAdd">
					<div class="form-group">
						<label class="col-lg-1 control-label" for="code">类型</label>
						<div class="col-lg-3">
							<select id="type" name="type">
								<option value="1">包含</option>
								<option value="2">开头为</option>
								<option value="3">结尾为</option>
								<option value="4">完全等于</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-1 control-label" for="name">规则内容</label>
						<div class="col-lg-3">
							<input type="text" id="rule-expression" class="form-control" name="ruleExpression" placeholder="请输入规则内容"
								data-bv-notempty="true"
								data-bv-notempty-message="规则内容不能为空"
							/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-1 control-label" for="address">自动回复内容</label>
						<div class="col-lg-3">
							<input type="text" id="back-content" class="form-control" name="backContent" placeholder="请输入自动回复内容"
								data-bv-notempty="true"
								data-bv-notempty-message="自动回复内容不能为空"
							/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-1 control-label" for="map">优先级</label>
						<div class="col-lg-3">
							<input id="level" class="form-control" type="number" name="level"
								value="${maxLevel + 1 }"
								data-bv-notempty="true"
								data-bv-notempty-message="优先级不能为空"
							/>
					    </div>
					</div>
					<div class="form-group">
						<label class="col-lg-1 control-label" for="map">备注</label>
						<div class="col-lg-3">
							<input type="text" id="remarks" class="form-control" name="remarks" placeholder="请输入备注"/>
					    </div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-1 col-lg-2">
							<input type="button" class="btn" id="message-config-submit-btn" value="提交" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<script>
	$(function(){
		var messageConfigAdd = $("#message-config-add");
		$("#message-config-submit-btn", messageConfigAdd).click(function(){
			$("#message-config-add-form").submit();
		});
	});
</script>