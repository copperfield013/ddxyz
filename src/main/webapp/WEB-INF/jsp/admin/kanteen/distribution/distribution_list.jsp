<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="distribution-list">
	<nav>
		
	</nav>
	<table class="table">
		<thead>
			<tr>
				<th>序号</th>
				<th>菜单名称</th>
				<th>可预订时间</th>
				<th>配送次数</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${distributionList }" var="distribution" varStatus="i">
				<tr data-id="${distribution.id }" >
					<td>${i.index + 1}</td>
					<td><a href="#" >${distribution.menuName }</a></td>
					<td><fmt:formatDate value="${distribution.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/> 
						~ <fmt:formatDate value="${distribution.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					<td>${distribution.deliveryCount }</td>
					<td>
						<a>禁用</a>
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

