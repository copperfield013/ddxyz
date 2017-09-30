<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="canteen-delivery-wares-group-manage">
	<div class="wares-group-list">
		<table class="table">
			<thead>
				<tr>
					<th>序号</th>
					<th>名称</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<c:forEach items="${groups }" var="group" varStatus="i">
						<tr data-id="${group.id }">
							<td>${i.index + 1 }</td>
							<td>${group.name }</td>
							<td>
								<a href="javascript:;" class="order-up">上移</a>
								<a href="javascript:;" class="order-down">下移</a>
								<a href="javascript:;" class="delete">删除</a>
							</td>
						</tr>
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<script>
	seajs.use(['ajax', 'dialog'], function(Ajax, Dialog){
		var $page = $('#canteen-delivery-wares-group-manage');
		
		$('.order-up', $page).click(function(){move($(this).closest('tr').attr('data-id'), true)});
		$('.order-down', $page).click(function(){move($(this).closest('tr').attr('data-id'), false)});
		$('.delete', $page).click(function(){
			
		});
		function move(groupId, isUp){
			if(groupId){
				Ajax.ajax('admin/canteen/delivery/move_group',{
					groupId	: groupId,
					isUp	: isUp
				}, function(data){
					if(data.status === 'suc'){
						$page.getLocatePage().refresh();
					}else{
						Dialog.notice('操作失败', 'error');
					}
				});
			}
		}
	})
</script>