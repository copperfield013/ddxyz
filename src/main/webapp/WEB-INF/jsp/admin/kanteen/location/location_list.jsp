<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<nav style="padding: 1em 0" id="location-list">
	<form class="form-inline" action="admin/kanteen/location/list" >
		<div class="form-group">
        	<label class="form-control-title" for="code">编码</label>
            <div class="input-group">
               <input type="text" css-width="8em" class="form-control" id="code" name="code" placeholder="编码" value="${criteria.code }">
           	</div>
        	<label class="form-control-title" for="name">配送地点</label>
            <div class="input-group">
               <input type="text" css-width="8em" class="form-control" id="name" name="name" placeholder="配送地点" value="${criteria.name }">
           	</div>
			<label class="form-control-title" for="address">详细地址</label>
			<input type="text" css-width="8em" class="form-control" id="address" name="address" placeholder="详细地址" value="${criteria.address }">
		</div>
		<button type="reset" class="btn btn-default">清空</button>
		<button type="submit" class="btn btn-default">查询</button>
		<button id="add-new-location" type="button" class="btn btn-default">添加</button>
	</form>

	<table class="table">
		<thead>
			<tr>
				<th>序号</th>
				<th>编码</th>
				<th>配送地点</th>
				<th>详细地址</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${locations }" var="location" varStatus="i">
				<tr data-id="${location.id }">
					<td>${i.index + 1}</td>
					<td>
						<a href="admin/kanteen/location/detail/${location.id }" page-type="tab" target="@location_detail_${location.id }" title="${location.name }">
							${location.code }
						</a>
					</td>
					<td>${location.name }</td>
					<td>${location.address }</td>
					<td>
						<a confirm="确定删除？" href="admin/kanteen/location/delete/${location.id }">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
</nav>
<script>
	$(function(){
		seajs.use(['ajax', 'tab', 'utils', 'dialog'], function(Ajax, Tab, utils, Dialog){
			var $page = $("#location-list");
			$("#add-new-location", $page).click(function(){
				Tab.openInTab('admin/kanteen/location/add','location-add', '添加配送地点');
			});
			
			$(".del-location-a", $page).click(function(){
				var $row = $(this).closest('tr[data-id]');
				var locationId = $row.attr('data-id');
				var page = $(this).getLocatePage();
				if(locationId){
					Dialog.confirm("确定删除该配送地点？", function(isYes){
						if(isYes){
							Ajax.ajax("admin/kanteen/location/delete", {
								'id'	: locationId
							},{
								page	: page
							});
						}
					});
				}
			});
		});
	});
</script>

