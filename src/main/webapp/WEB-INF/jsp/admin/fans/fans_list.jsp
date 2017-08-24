<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="fans-list">
	<nav style="padding: 1em 0">
		<form id="conditdion-form" class="form-inline" action="admin/fans/list" >
		</form>
	</nav>

	<table class="table">
		<thead>
			<tr>
				<th>序号</th>
				<th>头像</th>
				<th>昵称</th>
				<th>备注名</th>
				<th>居住城市</th>
				<th>性别</th>
				<th>关注时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${fansInfo.user_info_list }" var="fans" varStatus="i">
				<tr data-openid="${fans.openid }" data-nickname="${fans.nickname }">
					<td>${i.index + 1}</td>
					<td>
						<img alt="${fans.nickname }头像" src="${fans.headimgurl }" style="width:21px; height:21px;"/>
					</td>
					<td>${fans.nickname }</td>
					<td>
						<c:choose>
							<c:when test="${empty fans.remark  }">
								-
							</c:when>
							<c:otherwise>
								${fans.remark }
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						${fans.country }-${fans.province }-${fans.city }
					</td>
					<td>
						<c:choose>
							<c:when test="${fans.sex == 1 }">
								男
							</c:when>
							<c:when test="${fans.sex == 2 }">
								女
							</c:when>
							<c:otherwise>
								未知
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<jsp:useBean id="dateValue" class="java.util.Date"/>
						<jsp:setProperty name="dateValue" property="time" value="${fans.subscribe_time*1000 }"/>
						<fmt:formatDate value="${dateValue }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<a href="#" class="fans-edit-a">编辑</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
</div>
<script>
	$(function(){
		seajs.use(['dialog'], function(Dialog){
			var $page = $("#fans-list");
			$(".fans-edit-a", $page).click(function(){
				var $row = $(this).closest('tr[data-openid]');
				var openid = $row.attr('data-openid');
				var nickname = $row.attr('data-nickname');
				Dialog.openDialog("admin/fans/edit?openId=" + openid, "编辑" + nickname +"备注名", "fans-edit");
			});
		});
	})
</script>
