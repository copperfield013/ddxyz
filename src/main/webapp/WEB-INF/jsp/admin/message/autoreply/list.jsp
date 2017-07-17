<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="message-config-list">
	<nav style="padding: 1em 0" >
		<form id="condition-form" class="form-inline" action="admin/message/autoReply/list" >
			<%-- <div class="form-group">
	        	<label class="form-control-title" for="receiveTime">配送日期</label>
	            <div class="input-group">
	               <input type="text" class="form-control" id="receiveTime" name="receiveTime" readonly="readonly" 
	                value="${criteria.receiveTime }" css-width="15em"  css-cursor="text" data-date-format="yyyy-mm-dd"/>
	           	</div>
				<label class="form-control-title" for="locationName">配送地点</label>
				<input type="text" css-width="8em" class="form-control" id="locationName" name="locationName" placeholder="配送地点" value="${criteria.locationName }">
			</div>
			<button id="clear-all-condition" type="button" class="btn btn-default">清空</button>
			<button type="submit" class="btn btn-default">查询</button>
			<button id="search-all-delivery-info" type="button" class="btn btn-default">刷新</button> --%>
			<button id="message-config-add-button" type="button" class="btn btn-default">添加</button>
		</form>
	</nav>

	<table class="table">
		<thead>
			<tr>
				<th>序号</th>
				<th>规则</th>
				<th>回复内容</th>
				<th>优先级</th>
				<th>备注</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${messageConfigList }" var="messageConfig" varStatus="i">
				<tr data-id="${messageConfig.id }">
					<td>${i.index + 1}</td>
					<td>
						<a href="admin/message/detail?id=${messageConfig.id }" page-type="tab" target="@message-config-detail-${messageConfig.id }" title="匹配规则">
							${messageConfig.ruleExpression }
						</a>
					</td>
					<td>${messageConfig.backContent }</td>
					<td>${messageConfig.level }</td>
					<td>${messageConfig.remarks }</td>
					<td><fmt:formatDate value="${messageConfig.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
						<a href="#" class="message-config-edit-a">编辑</a>
						<a href="#" class="message-config-del-a">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
</div>
<script>
	$(function(){
		seajs.use(['tab', 'dialog', 'ajax'], function(Tab, Dialog, Ajax){
			var messageConfigList = $("#message-config-list");
			//添加新自动回复
			$("#message-config-add-button", messageConfigList).click(function(){
				Tab.openInTab("admin/message/autoReply/add", "message-config-add", "添加新的自动回复");
			});
			//编辑自动回复
			$(".message-config-edit-a", messageConfigList).click(function(){
				var $row = $(this).closest('tr[data-id]');
				var messageConfigId = $row.attr('data-id');
				//var page = $(this).getLocatePage();
				Tab.openInTab("admin/message/autoReply/edit?id=" + messageConfigId, "message-config-edit", "编辑");
			});
			
			//删除
			$(".message-config-del-a", messageConfigList).click(function(){
				var $row = $(this).closest('tr[data-id]');
				var messageConfigId = $row.attr('data-id');
				Dialog.confirm("确定删除？", function(isYes){
					if(isYes){
						Ajax.ajax("admin/message/autoReply/delete", {
							id	:	messageConfigId
						}, {
							page	:	messageConfigList.getLocatePage()
						});
					}
				});
			});
		});
	});
</script>

