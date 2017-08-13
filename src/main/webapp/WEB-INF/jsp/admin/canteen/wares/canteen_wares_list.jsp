<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<nav style="padding: 1em 0" id="canteen-wares-list">
	<form class="form-inline" action="admin/canteen/manage/week_orders" >
		<div>
			<a class="btn btn-primary tab" title="创建餐品" href="admin/canteen/wares/add" target="@canteen_wares_add">创建餐品</a>
		</div>
	</form>
	<div class="row" style="margin-top: 1em;">
		<table class="table">
			<thead>
				<tr>
					<th>序号</th>
					<th>名称</th>
					<th>单价</th>
					<th>单位</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${waresList }" var="wares" varStatus="i">
					<tr data-id="${wares.id }">
						<td>${i.index + 1 }</td>
						<td>${wares.name }</td>
						<td><fmt:formatNumber value="${wares.basePrice / 100}" pattern="0.00" /></td>
						<td>${wares.priceUnit }</td>
						<td><fmt:formatDate value="${wares.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
						<td>
							<a class="update tab" title="餐品修改" href="admin/canteen/wares/update/${wares.id }" target="@canteen_wares_update_${wares.id }">修改</a>
							<a class="wares-${wares.disabled == 1? 'enable': 'disable' }" href="#">${wares.disabled == 1? '启用': '禁用' }</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</nav>
<script>
	$(function(){
		seajs.use(['ajax', 'dialog', 'utils'], function(Ajax, Dialog, utils){
			var $page = $('#canteen-wares-list');
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
						var waresId = $(dom).closest('tr').attr('data-id');
						Ajax.ajax('admin/canteen/wares/' + disabled + '/' + waresId, {}, {
							page	: $page.getLocatePage()
						});
					}	
				});
				
			}
		});
	});
</script>

