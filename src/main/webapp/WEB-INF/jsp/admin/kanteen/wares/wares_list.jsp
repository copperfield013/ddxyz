<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
	#wares-list a.waresName {
		position: relative;
	}
	#wares-list img.wares-thumb {
	    width: 100px;
	    height: 100px;
	    position: absolute;
	    left: 0;
	    top: 1.5em;
	    display: none;
	    z-index: 1000;
	    border: 1px solid #ccc;
	}
	#wares-list a.waresName:HOVER>img.wares-thumb {
		display: block !important;
	}
}
</style>
<div id="wares-list">
	<nav style="padding: 1em 0" id="order-nav">
		<form class="form-inline" action="admin/kanteen/wares" >
			<div class="form-group">
				<label class="form-control-title" for="wareName">商品名</label>
				<input type="text" class="form-control" id="wareName" name="wareName" placeholder="商品名" value="${criteria.waresName }">
			</div>
			<button type="submit" class="btn btn-primary">查询</button>
			<a href="admin/kanteen/wares/add" target="wares_add" title="添加商品" class="tab btn btn-default">添加</a>
		</form>
	</nav>
	<table class="table">
		<thead>
			<tr>
				<th>序号</th>
				<th>商品名称</th>
				<th>单价</th>
				<th>单位</th>
				<th title="选项组数/选项总数">选项数(G/O)</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${waresList }" var="wares" varStatus="i">
				<tr data-id="${wares.id }" >
					<td>${i.index + 1}</td>
					<c:set var="src">
						<c:choose>
							<c:when test="${wares.thumbUri != null }">
								${basePath }${wares.thumbUri }
							</c:when>
							<c:otherwise>
								${basePath }media/admin/canteen/image/no-img.png
							</c:otherwise>
						</c:choose>
					</c:set>
					<td><a href="#" class="waresName" >${wares.name }<img class="wares-thumb" src="${src }"></a></td>
					<td><fmt:formatNumber value="${wares.basePrice / 100}" pattern="0.00" /> </td>
					<td>${wares.priceUnit }</td>
					<td>
						<c:set var="optionCount" value="${optionCountMap[wares.id] }" />
						<c:choose>
							<c:when test="${optionCount != null && optionCount > 0 }">
								${optionGroupCountMap[wares.id] }/${optionCount }
							</c:when>
							<c:otherwise>-</c:otherwise>
						</c:choose>
					</td>
					<td><fmt:formatDate value="${wares.createTime }"  pattern="yyyy-MM-dd HH:mm:ss" /> </td>
					<td>
						<a class="update tab" title="餐品修改" href="admin/kanteen/wares/update/${wares.id }" target="wares_update_${wares.id }">修改</a>
						<a class="wares-${wares.disabled == 1? 'enable': 'disable' }" href="#">${wares.disabled == 1? '启用': '禁用' }</a>
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
						var waresId = $(dom).closest('tr').attr('data-id');
						Ajax.ajax('admin/kanteen/wares/' + disabled + '/' + waresId, {}, {
							page	: $page.getLocatePage()
						});
					}	
				});
				
			}
		});
	});
</script>

