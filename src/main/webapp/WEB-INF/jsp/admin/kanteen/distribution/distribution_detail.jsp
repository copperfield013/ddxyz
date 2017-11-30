<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
	#distribution-detail .row.delivery-row:not(:last-child){
		border-bottom: 1px solid #ccc;
	}
	#distribution-detail .row.delivery-row{
		padding: 10px 0;
	}
</style>
<nav style="padding: 1em 0" id="distribution-detail">
	<div class="row" style="margin-top: 1em;">
		<div class="col-lg-4">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">配销${distribution.code }信息(<fmt:formatDate value="${distribution.startTime }" pattern="MM月dd日" />~<fmt:formatDate value="${distribution.endTime }" pattern="MM月dd日" />)</h3>
				</div>
				<div class="panel-body">
					<div class="row delivery-row">
						<label class="col-lg-4 "><br/>预定时间：<br/></label>
						<div class="col-lg-8 ">
							<fmt:formatDate value="${distribution.startTime }" pattern="yyyy年MM月dd日 HH时mm分" /><br/>
							~<br/>
							<fmt:formatDate value="${distribution.endTime }" pattern="yyyy年MM月dd日 HH时mm分" />
						</div>
					</div>
					<div class="row delivery-row">
						<label class="col-lg-4 ">订单总金额：</label>
						<div class="col-lg-8 "><fmt:formatNumber value="${totalValidAmount /100 }" pattern="0.00" />元</div>
					</div>
					<div class="row delivery-row">
						<label class="col-lg-3">待完成：</label>
						<div class="col-lg-3">
							${stat.effective }
						</div>
						<label class="col-lg-3">总订单：</label>
						<div class="col-lg-3">${stat.totalCount }</div>
					</div>
					<div class="row delivery-row">
						<label class="col-lg-3">已完成：</label>
						<div class="col-lg-3">${stat.completed }</div>
						<label class="col-lg-3">未领取：</label>
						<div class="col-lg-3">${stat.missed }</div>
					</div>
					
					<div class="row delivery-row">
						<label class="col-lg-3">已关闭：</label>
						<div class="col-lg-3 ">${stat.closed }</div>
						<label class="col-lg-3">已取消：</label>
						<div class="col-lg-3">${stat.canceled }</div>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${orderStatItems != null }">
			<div class="col-lg-8">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">产品列表</h3>
					</div>
					<div class="panel-body">
						<table class="table">
							<thead>
								<tr>
									<th>序号</th>
									<th>产品名称</th>
									<th>单价</th>
									<th>预定情况</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${orderStatItems }" var="item" varStatus="i">
									<tr data-id="${item.distributionWaresId }">
										<td>${i.index + 1 }</td>
										<td>${item.waresGroupName }/${item.waresName }</td>
										<td><fmt:formatNumber value="${item.unitPrice/100 }" pattern="0.00"/>元/${item.priceUnit }</td>
										<td>${item.currentCount }/${item.maxCount == null || item.maxCount ==0 ? '不限' :item.maxCount }</td>
										<td>
											<a class="dwares-${item.disabledDistribution == 1? 'enable': 'disable' }" href="#">${item.disabledDistribution == 1? '恢复预定': '暂停预定' }</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</c:if>
	</div>
</nav>
<script>
	$(function(){
		seajs.use(['ajax', 'dialog', 'utils'], function(Ajax, Dialog, utils){
			var $page = $('#canteen-week-delivery');
			$('#selectDate', $page).datepicker({
				format		: 'yyyy-mm-dd',
				weekStart	: 1,
				daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],  
                monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',  
                        '七月', '八月', '九月', '十月', '十一月', '十二月' ]
			});
			
			$('.dwares-enable', $page).click(function(){
				disableWares(this, 'enable');
			});
			$('.dwares-disable', $page).click(function(){
				disableWares(this, 'disable');
			});
			function disableWares(dom, disabled){
				Dialog.confirm('确定' + (disabled == 'disable' ? '暂停预定？': '恢复预定？'), function(yes){
					if(yes){
						var dwaresId = $(dom).closest('tr').attr('data-id');
						Ajax.ajax('admin/canteen/delivery/disable_wares/' + disabled + '/' + dwaresId, {}, {
							page	: $page.getLocatePage()
						});
					}	
				});
				
			}
			
		});
		
	});
</script>

