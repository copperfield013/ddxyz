<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="wares-list">
	<nav style="padding: 1em 0" id="order-nav">
		<form class="form-inline" action="admin/kanteen/waresgroup" >
			<div class="form-group">
				<label class="form-control-title" for="wareName">名称</label>
				<input type="text" class="form-control" id="wareName" name="wareName" placeholder="名称" value="${criteria.groupName }">
			</div>
			<button type="submit" class="btn btn-primary">查询</button>
			<a href="admin/kanteen/waresgroup/add" target="waresgroup_add" title="添加商品组" class="tab btn btn-default">添加</a>
		</form>
	</nav>
	<table class="table">
		<thead>
			<tr>
				<th>序号</th>
				<th>商品组名称</th>
				<th>描述</th>
				<th>商品数</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${waresGroups }" var="waresGroup" varStatus="i">
				<tr data-id="${waresGroup.id }" >
					<td>${i.index + 1}</td>
					<td>
						<a class="tab" target="waresgroup_update_${waresGroup.id }" title="修改商品组" href="admin/kanteen/waresgroup/update/${waresGroup.id }">${waresGroup.name }</a>
					</td>
					<td>${waresGroup.description }</td>
					<td>${waresGroup.waresCount }</td>
					<td><fmt:formatDate value="${waresGroup.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
						<c:set var="disabled" value="${waresGroup.disabled == 1 }" />
						<a class="waresgroup-${disabled? 'disable': 'enable' }">${disabled? '启用': '禁用' }</a>
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
			var $page = $('#wares-list');
			console.log($page);
			$('.wares-enable', $page).click(function(){
				disableWares(this, 'enable');
			});
			$('.wares-disable', $page).click(function(){
				disableWares(this, 'disable');
			});
			function disableWares(dom, disabled){
				Dialog.confirm('确定' + (disabled == 'disable' ? '禁用': '启用'), function(yes){
					if(yes){
						var waresgroupId = $(dom).closest('tr').attr('data-id');
						Ajax.ajax('admin/kanteen/waresgroup/' + disabled + '/' + waresgroupId, {}, {
							page	: $page.getLocatePage()
						});
					}	
				});
				
			}
		});
	});
</script>

