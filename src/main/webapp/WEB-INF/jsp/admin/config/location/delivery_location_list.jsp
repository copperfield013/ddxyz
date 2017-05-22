<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<nav style="padding: 1em 0" id="delivery-location-list">
	<form class="form-inline" action="admin/config/location/list" >
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
</nav>
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
			<tr data-id="${planDelivery.id }">
				<td>${i.index + 1}</td>
				<td>
					<a href="admin/config/location/detail?id=${location.id }" page-type="tab" target="@delivery-location-detail-${location.id }" title="${location.name }">
						${location.name }
					</a>
				</td>
				<td>${location.code }</td>
				<td>${location.address }</td>
				<td>
					
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
<script>
	$(function(){
		seajs.use(['ajax', 'tab', 'utils'], function(Ajax, Tab, utils){
			var deliveryLocationList = $("#delivery-location-list");
			$("#add-new-location", deliveryLocationList).click(function(){
				Tab.openInTab('admin/config/location/add','location-add', '添加配送地点');
			});
		});
	});
</script>

