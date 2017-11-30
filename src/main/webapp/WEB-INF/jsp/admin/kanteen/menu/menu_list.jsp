<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="menu-list">
	<nav style="padding: 1em 0" id="order-nav">
		<form class="form-inline" action="admin/kanteen/menu" >
			<div class="form-group">
				<label class="form-control-title" for="wareName">名称</label>
				<input type="text" class="form-control" id="name" name="name" placeholder="名称" value="${criteria.name }">
			</div>
			<button type="submit" class="btn btn-primary">查询</button>
			<a href="admin/kanteen/menu/add" target="menu_add" title="添加菜单" class="tab btn btn-default">添加</a>
		</form>
	</nav>
	<table class="table">
		<thead>
			<tr>
				<th>序号</th>
				<th>菜单名称</th>
				<th>描述</th>
				<th>商品组数</th>
				<th>商品数</th>
				<th>更改时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${menuList }" var="menu" varStatus="i">
				<tr data-id="${menu.id }" >
					<td>${i.index + 1}</td>
					<td>
						<a class="tab" target="menu_update_${menu.id }" title="修改菜单" href="admin/kanteen/menu/update/${menu.id }">${menu.name }</a>
					</td>
					<td>${menu.description }</td>
					<td>${menu.groupCount }</td>
					<td>${menu.waresCount }</td>
					<td><fmt:formatDate value="${menu.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
						<c:set var="disabled" value="${menu.disabled == 1 }" />
						<c:set var="operate" value="${disabled?'启用':'禁用' }" />
						<a confirm="确认${operate }？" href="admin/kanteen/menu/${disabled?'enable':'disable' }/${menu.id}" >${operate }</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
</div>
<script>
	$(function(){
		seajs.use(['ajax', 'dialog', 'utils'], function(Ajax, Dialog, utils){
			var $page = $('#menu-list');
		});
	});
</script>

