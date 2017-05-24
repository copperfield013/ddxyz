<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<nav style="padding: 1em 0" id="delivery-info-list">
	<form id="condition-form" class="form-inline" action="admin/config/info/list" >
		<div class="form-group">
        	<label class="form-control-title" for="receiveTime">配送日期</label>
            <div class="input-group">
               <input type="text" class="form-control" id="receiveTime" name="receiveTime" readonly="readonly" 
                value="${criteria.receiveTime }" css-width="15em"  css-cursor="text" data-date-format="yyyy-mm-dd"/>
           	</div>
			<label class="form-control-title" for="locationName">配送地点</label>
			<input type="text" css-width="8em" class="form-control" id="locationName" name="locationName" placeholder="配送地点" value="${criteria.locationName }">
		</div>
		<button id="clear-all-condition" type="button" class="btn btn-default">清空</button>
		<button type="submit" class="btn btn-default">查询</button>
		<button id="search-all-delivery-info" type="button" class="btn btn-default">刷新</button>
	</form>
</nav>
<table class="table">
	<thead>
		<tr>
			<th>序号</th>
			<th>配送时间</th>
			<th>配送地点</th>
			<th>关闭时间</th>
			<th>最大配送</th>
			<th>已分配量</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list }" var="planDelivery" varStatus="i">
			<tr data-id="${planDelivery.id }">
				<td>${i.index + 1}</td>
				<td>
					<%-- <a href="#" page-type="tab" target="@delivery-info-detail-${planDelivery.id }" title="配送时间"> --%>
						<fmt:formatDate value="${planDelivery.timePoint }" pattern="yyyy-MM-dd HH:mm:ss"/>
					<!-- </a> -->
				</td>
				<td>${planDelivery.locationName }</td>
				<td><fmt:formatDate value="${planDelivery.closeTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${planDelivery.maxCount }</td>
				<td>${planDelivery.currentCount }</td>
				<td>
					
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
<script>
	$(function(){
		seajs.use(['ajax', 'dialog', 'utils'], function(Ajax, Dialog, utils){
			var deliveryInfoList = $("#delivery-info-list");
			$("#receiveTime", deliveryInfoList).datepicker();
			
			$("#clear-all-condition", deliveryInfoList).click(function(){
				clearInput();
			});
			
			$("#search-all-delivery-info", deliveryInfoList).click(function(){
				Dialog.confirm("确定要重新加载？",function(isYes){
					if(isYes){
						Ajax.ajax('admin/config/info/showToday', null, {
							page	: deliveryInfoList.getLocatePage()
						});
					}
				});
			});
			
			//清除查询表单的条件input
			function clearInput(){
				var conditionForm = $("#condition-form",deliveryInfoList);
				$(':input', conditionForm).not(':button, :submit').val('');
			}
		});
	});
</script>

