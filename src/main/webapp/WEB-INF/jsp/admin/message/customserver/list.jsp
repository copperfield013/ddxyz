<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="custom-server-list">
	<nav style="padding: 1em 0">
		<form id="condition-form" class="form-inline" action="admin/message/customServer/list" >
			<button id="custom-server-add-button" type="button" class="btn btn-default">添加</button>
		</form>
	</nav>

	<table class="table">
		<thead>
			<tr>
				<th>序号</th>
				<th>客服账号</th>
				<th>客服昵称</th>
				<th>绑定微信号</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${customs.kf_list }" var="custom" varStatus="i">
				<tr data-account="${custom.kf_account }" data-nickname="${custom.kf_nick }">
					<td>${i.index + 1}</td>
					<td>
						<%-- <a href="admin/message/detail?id=${messageConfig.id }" page-type="tab" target="@message-config-detail-${messageConfig.id }" title="匹配规则"> --%>
							${custom.kf_account }
						<!-- </a> -->
					</td>
					<td>${custom.kf_nick }</td>
					<td>
						<c:choose>
							<c:when test="${not empty custom.kf_wx }">
								${custom.kf_wx }
							</c:when>
							<c:otherwise>
								${custom.invite_wx }
									<c:choose>
										<c:when test="${custom.invite_status == 'waiting' }">
											（等待接受）
										</c:when>
										<c:when test="${custom.invite_status == 'rejected' }">
											（拒绝接受）
										</c:when>
										<c:when test="${custom.invite_status == 'expired' }">
											（已过期）
										</c:when>
									</c:choose>
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<!-- <a href="#" class="message-config-edit-a">编辑</a> -->
						<c:if test="${empty custom.kf_wx }">
							<a href="#" class="custom-server-invite-a">邀请</a>
						</c:if>
						<a href="#" class="custom-server-edit-a">编辑</a>
						<a href="#" class="custom-server-del-a">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%-- <div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div> --%>
</div>
<script>
	$(function(){
		seajs.use(['tab', 'ajax', 'dialog'], function(Tab, Ajax, Dialog){
			var customServerList = $("#custom-server-list");
			$("#custom-server-add-button", customServerList).click(function(){
				Tab.openInTab("admin/message/customServer/add", "custom-server-add", "添加新客服");
			});
			
			//邀请绑定微信号
			$(".custom-server-invite-a", customServerList).click(function(){
				var $row = $(this).closest('tr[data-account]');
				var kf_account = $row.attr('data-account');
				console.log("------------");
				Tab.openInTab("admin/message/customServer/invite?kf_account=" + kf_account, "custom-server-invite", "邀请绑定客服账号");
			});
			
			//删除客服
			$(".custom-server-del-a", customServerList).click(function(){
				var $row = $(this).closest('tr[data-account]');
				var kf_account = $row.attr('data-account');
				Dialog.confirm("确定删除客服？", function(isYes){
					if(isYes){
						Ajax.ajax("admin/message/customServer/delete",{
							kf_account	:	kf_account
						}, {
							page	:	customServerList.getLocatePage()
						});	
					}
				});
			});
			
			//编辑客服
			$(".custom-server-edit-a", customServerList).click(function(){
				var $row = $(this).closest('tr[data-account]');
				var kf_account = $row.attr('data-account');
				var nickname = $row.attr('data-nickname');
				console.log("kf_account:" + kf_account + "。nickname：" + nickname);
				Tab.openInTab("admin/message/customServer/edit?kf_account=" + kf_account + "&nickname=" + nickname, "custom-server-edit", "编辑客服信息");
			});
		});
	});
</script>

