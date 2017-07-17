<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>

<div class="detail" id="message-config-${messageConfig.id }">
	<div class="page-header">
		<div class="header-title">
			<h1>信息详情</h1>
		</div>
	</div>
	<div class="page-body">
		<input id="messageConfigIdHidden" type="hidden" name="id" value="${messageConfig.id }">
		<div class="col-12">
			<div class="row">
				<div class="col-lg-6">
					<label class="col-lg-4">规则</label>
					<div class="col-lg-8">${messageConfig.ruleExpression }</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6">
					<label class="col-lg-4">自动回复内容</label>
					<div class="col-lg-8">${messageConfig.backContent }</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6">
					<label class="col-lg-4">优先级</label>
					<div class="col-lg-8">${messageConfig.level }</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6">
					<label class="col-lg-4">备注</label>
					<div class="col-lg-8">${messageConfig.remarks }</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6">
					<label class="col-lg-4">创建时间</label>
					<div class="col-lg-8"><fmt:formatDate value="${messageConfig.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></div>
				</div>
			</div>
		</div>
	</div>
	<a href="admin/message/autoReply/edit?id=${messageConfig.id }" class="btn">编辑</a>
</div>