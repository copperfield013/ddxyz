<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<title>配销列表</title>
<div id="distribution-list">
	<div class="page-header">
		<div class="header-title">
			<h1>配销列表</h1>
		</div>
	</div>
	<nav style="padding: 1em 0" id="order-nav">
		<form class="form-inline" action="admin/kanteen/distribution" >
			<div class="form-group">
				<label class="form-control-title" for="startTime">开始时间</label>
				<input type="text" class="form-control" id="name" name="name" placeholder="名称" value="${criteria.name }">
			</div>
			<button type="submit" class="btn btn-primary">查询</button>
			<a href="admin/kanteen/distribution/add" target="distribution_add" title="添加配销" class="tab btn btn-default">添加</a>
		</form>
	</nav>
	<table class="table">
		<thead>
			<tr>
				<th>序号</th>
				<th>编号</th>
				<th>菜单名称</th>
				<th>可预订时间</th>
				<th>配送次数</th>
				<th>修改时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${distributionList }" var="distribution" varStatus="i">
				<tr data-id="${distribution.id }" >
					<td>${i.index + 1}</td>
					<td><a href="admin/kanteen/distribution/detail/${distribution.id }">${distribution.code }</a></td>
					<td><a href="#" >${distribution.menuName }</a></td>
					<td><fmt:formatDate value="${distribution.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/> 
						~ <fmt:formatDate value="${distribution.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td>
						<c:choose>
							<c:when test="${distribution.deliveryCount > 0 }">
								<a class="tab" href="admin/kanteen/delivery?distributionId=${distribution.id }" target="distribution_delivery_list_${distribution.id }">${distribution.deliveryCount }</a>
							</c:when>
							<c:otherwise>${distribution.deliveryCount }</c:otherwise>
						</c:choose>
					</td>
					<td><fmt:formatDate value="${distribution.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
						<a confirm="确定发布？发布后的配销无法删除（可以禁用）" href="admin/kanteen/distribution/enable/${distribution.id }">发布</a>
						<a class="tab" target="delivery_add" title="创建配送" href="admin/kanteen/delivery/add/${distribution.id }">创建配送</a>
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
		});
	});
</script>

