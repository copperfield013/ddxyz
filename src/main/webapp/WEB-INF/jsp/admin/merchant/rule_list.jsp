<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
</style>
<nav style="padding: 1em 0" id="merchant-disabled-rule-list">
	<div class="form-group">
		<a class="btn btn-default tab" href="admin/merchant/rule/addRule" title="添加新规则" target="mechant-disabled-rule-add">添加新规则</a>
		<!-- <a class="btn btn-default tab" href="admin/merchant/rule/merchantOpen" title="强行开启">强行开启</a>
		<a class="btn btn-default tab" href="admin/merchant/rule/merchantDisabled" title="强行关闭">强行关闭</a> -->
		<button id="merchant-open" type="button" class="btn btn-default">
		<c:choose>
			<c:when test='${forceOpen}'>
				取消强行开启
			</c:when>
			<c:otherwise>
				强行开启
			</c:otherwise>
		</c:choose>
		</button>
		<button id="merchant-disabled" type="button" class="btn btn-default ">
			<c:choose>
				<c:when test='${forceDisabled}'>
					取消强行关闭
				</c:when>
				<c:otherwise>
					强行关闭
				</c:otherwise>
			</c:choose>
		</button>
	</div>
	<table class="table">
		<thead>
			<tr>
				<th>序号</th>
				<th>规则</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ruleList }" var="rule" varStatus="i">
				<tr data-id="${rule.id }">
					<td>${i.index + 1}</td>
					<td>${rule.rule }</td>
					<td><fmt:formatDate value="${rule.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><a href="#" class="rule-delete">删除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
</nav>
<script>
	$(function(){
		seajs.use(['ajax', 'tab', 'utils', 'dialog'], function(Ajax, Tab, utils, Dialog){
			var merchantRuleList = $("#merchant-disabled-rule-list");
			$("#merchant-open", merchantRuleList).click(function(){
				Dialog.confirm("确定强行开启？", function(isYes){
					if(isYes){
						Ajax.ajax('admin/merchant/rule/merchantOpen', null, {
							page	: merchantRuleList.getLocatePage()
						});
					}
				})
			});
			
			$("#merchant-disabled", merchantRuleList).click(function(){
				Dialog.confirm("确定强行关闭？", function(isYes){
					if(isYes){
						Ajax.ajax('admin/merchant/rule/merchantDisabled', null, {
							page	: merchantRuleList.getLocatePage()
						});
					}
				})
			});
			
			$(".rule-delete", merchantRuleList).click(function(){
				var $row = $(this).closest('tr[data-id]');
				var ruleId = $row.attr('data-id');
				Dialog.confirm("确定删除？", function(isYes){
					if(isYes){
						Ajax.ajax('admin/merchant/rule/delete', {
							ruleId : ruleId
						}, {
							page	: merchantRuleList.getLocatePage()
						});
					}
				})
			});
			
		});
	});
</script>

