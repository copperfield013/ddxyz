<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
	#canteen-week-orders .row.delivery-row:not(:last-child){
		border-bottom: 1px solid #ccc;
	}
	#canteen-week-orders .row.delivery-row{
		padding: 10px 0;
	}
	#canteen-week-orders .order-wares-item{
		display: block;
	}
	#canteen-week-orders table.table.td{
		text-align: center;
		vertical-align: middle;
	}
	#canteen-week-orders .head-operate{
		float: right; 
		line-height: 1em;
	    height: 1em;
	    padding: 0;
	}
	#canteen-week-orders .head-operate>.btn{
		margin: 0, 5px;
	}
	#canteen-week-orders tr.order-completed{
		color: #00AA00;
	}
	#canteen-week-orders tr.order-closed{
		color: #afafaf;
	}
	#canteen-week-orders tr.order-miss{
		color: #EEC900;
	}
	#canteen-week-orders .fa-btn{
		margin-right: 0.3em;
		cursor: pointer;
		font-size: 1.1em;
	}
	#canteen-week-orders .fa-btn:HOVER{
		color: #ccc;
	}
	#canteen-week-orders .operate-cell span{
		color: #9f9f9f;
	}
</style>
<nav style="padding: 1em 0" id="canteen-week-orders">
	<form class="form-inline" action="admin/canteen/manage/week_orders" >
		<div class="form-group">
			<label class="form-control-title" for="contactName">日期</label>
			<input type="text" class="form-control" id="selectDate" name="date" readonly="readonly" css-cursor="text" value="${criteria.date }" />
		</div>
		<button type="submit" class="btn btn-default">查询</button>
	</form>
	<div class="row" style="margin-top: 1em;">
		<div class="col-lg-4">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">本周分发信息(<fmt:formatDate value="${criteria.startDate }" pattern="MM月dd日" />~<fmt:formatDate value="${criteria.endDateIncluded }" pattern="MM月dd日" />)</h3>
				</div>
				<div class="panel-body">
					<c:choose>
						<c:when test="${delivery != null }">
							<div class="row delivery-row">
								<label class="col-lg-4 "><br/>预定时间：<br/></label>
								<div class="col-lg-8 ">
									<fmt:formatDate value="${delivery.openTime }" pattern="yyyy年MM月dd日 HH时mm分" /><br/>
									~<br/>
									<fmt:formatDate value="${delivery.closeTime }" pattern="yyyy年MM月dd日 HH时mm分" />
								</div>
							</div>
							<div class="row delivery-row">
								<label class="col-lg-4 "><br/>分发时间：<br/></label>
								<div class="col-lg-8 ">
									<fmt:formatDate value="${delivery.timePoint }" pattern="yyyy年MM月dd日 HH时mm分" /><br/>
									~<br/>
									<fmt:formatDate value="${delivery.claimEndTime }" pattern="yyyy年MM月dd日 HH时mm分" />
								</div>
							</div>
							<div class="row delivery-row">
								<label class="col-lg-4 ">分发点：</label>
								<div class="col-lg-8 ">${delivery.locationName }</div>
							</div>
							<div class="row delivery-row">
								<label class="col-lg-4 ">订单总金额：</label>
								<div class="col-lg-8 "><fmt:formatNumber value="${totalAmount /100 }" pattern="0.00" />元</div>
							</div>
							<div class="row delivery-row">
								<label class="col-lg-3">待完成：</label>
								<div class="col-lg-3">
									${stat.effective }
									<c:if test="${stat.effective > 0 }">
										<i class="fa fa-mail-reply-all fa-btn" 
											id="batch-complete-all" title="完成所有订单"
											confirm="是否完成本次分发所有待完成的订单(${stat.effective }个订单)？" 
											href="admin/canteen/manage/batch_complete_orders/${delivery.id }"
											style="margin-left: 0.3em; margin-right: 0;"></i>
									</c:if>
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
							<c:if test="${now.time >= delivery.claimEndTime.time }">
								<div class="row delivery-row">
									<div class="col-lg-3 col-lg-offset-3">
										<input type="button" class="btn btn-primary" value="完成全部订单" />
									</div>
								</div>
							</c:if>
						</c:when>
						<c:otherwise>
							<div>
								本周没有数据
							</div>
						</c:otherwise>
					</c:choose>
					
				</div>
			</div>
		</div>
		<c:if test="${delivery != null }">
			<div class="col-lg-8">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">
							订单列表
							<span class="widget-buttons head-operate">
								<span class="input-icon" style="top:-3px">
									<input type="text" class="form-control input-xs" id="keyword" name="keyword" value="${criteria.keyword }" placeholder="部门或姓名或手机号">
									<i class="glyphicon glyphicon-search blue"></i>
								</span>
							</span>
							<span class="head-operate">
								<i class="fa fa-print fa-btn" id="print-btn" title="打印所有"></i>
								<i class="fa fa-download fa-btn" id="download-btn" title="导出" style="margin-right: 1em;"></i>
								<span name="containsDefault" value="true" class="contains-checkbox cpf-checkbox cpf-checkbox-xs ${criteria.containsDefault? 'checked': '' }">未完成</span>
								<span name="containsCompleted" value="true" class="contains-checkbox cpf-checkbox cpf-checkbox-xs ${criteria.containsCompleted? 'checked': '' }" >已完成</span>
								<span name="containsClosed" value="true" class="contains-checkbox cpf-checkbox cpf-checkbox-xs ${criteria.containsClosed? 'checked': '' }" >已关闭</span>
								<span name="containsCanceled" value="true" class="contains-checkbox cpf-checkbox cpf-checkbox-xs ${criteria.containsCanceled? 'checked': '' }" >用户取消</span>
								<span name="containsMiss" value="true" class="contains-checkbox cpf-checkbox cpf-checkbox-xs ${criteria.containsMiss? 'checked': '' }" >未领取</span>
							</span>
						</h3>
						
					</div>
					<div class="panel-body">
						<table class="table">
							<thead>
								<tr>
									<th width="10%">序号</th>
									<th width="10%">领取人</th>
									<th width="10%">部门</th>
									<th width="12%">手机号</th>
									<th width="21%">订单明细</th>
									<th width="13%">应收款</th>
									<th width="12%">下单时间</th>
									<th width="12%">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${orderItems }" var="item" varStatus="i">
									<c:set var="itemStatus" >
										<c:choose>
											<c:when test="${item.cancelStatus == 'closed' }" >closed</c:when>
											<c:when test="${item.cancelStatus == 'miss' }">miss</c:when>
											<c:when test="${item.cancelStatus == 'canceled' }">canceled</c:when>
											<c:when test="${item.status == 2 }">completed</c:when>
											<c:otherwise>ordered</c:otherwise>
										</c:choose>
									</c:set>
									<tr order-id="${item.orderId }" class="order-${itemStatus }">
										<td>
											<span class="cpf-checkbox" name="orderId" value="${item.orderId }">${i.index + 1 }</span>
										</td>
										<td>${item.receiverName }</td>
										<td>${item.depart }</td>
										<td>${item.receiverContact }</td>
										<td>
											<c:forEach items="${item.waresItemsList }" var="waresItem">
												<span class="order-wares-item">${waresItem.waresName }×${waresItem.count }·${waresItem.priceUnit }</span>
											</c:forEach>
										</td>
										<td><fmt:formatNumber value="${item.totalPrice / 100 }" pattern="0.00" />元</td>
										<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /> </td>
										<td class="operate-cell">
											<c:choose>
												<c:when test="${itemStatus == 'ordered' && now.time < delivery.claimEndTime.time}">
													<span>未完成</span><br/>
													<a confirm="确认关闭订单？" href="admin/canteen/manage/close_order/${item.orderId }">关闭订单</a>
													<br/>
												</c:when>
												<c:when test="${itemStatus == 'ordered' && now.time >= delivery.claimEndTime.time}">
													<span>未完成</span><br/>
													<a confirm="确认订单已完成？" href="admin/canteen/manage/complete_order/${item.orderId }">完成订单</a><br/>
													<a confirm="确认将订单状态设置为未领取？" href="admin/canteen/manage/miss_order/${item.orderId }">设为未领取</a>
													<br/>
												</c:when>
												<c:when test="${itemStatus == 'completed' }">
													<span>已完成</span><br/>
													<a confirm="确认取消订单的完成状态？" href="admin/canteen/manage/cancel_complete/${item.orderId }">取消完成</a>
													<br/>
												</c:when>
												<c:when test="${itemStatus == 'closed' }">
													<span>已关闭</span><br/>
													<a confirm="确认取消订单的关闭状态？" href="admin/canteen/manage/cancel_close/${item.orderId }">取消关闭</a>
													<br/>
												</c:when>
												<c:when test="${itemStatus == 'miss' }">
													<span>未领取</span><br/>
													<a confirm="确认取消订单的未领取状态？" href="admin/canteen/manage/cancel_miss/${item.orderId }">取消未领取</a>
													<br/>
												</c:when>
												<c:when test="${itemStatus == 'canceled' }">
													<span>已取消</span>
												</c:when>
											</c:choose>
											<!-- <a class="print-order-tag" href="#">打印</a> -->
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
					</div>
				</div>
			</div>
		</c:if>
	</div>
	<script type="text/x-jquery-tmpl" id="print-table">
		<div class="wrapper">
			<div class="table-page">
				<table>
					<colgroup>
						<col width="20%" />
						<col width="20%" />
						<col width="20%" />
						<col width="20%" />
						<col width="20%" />
					</colgroup>
					<tbody>
						<tr>
							<td colspan="5">订单明细</td>
						</tr>
						<tr>
							<td>订单号:</td>
							<td colspan="4">\${orderCode }</td>
						</tr>
						<tr>
							<td colspan="5">
								{{each(i, orderItem) orderItems}}
									<div>\${orderItem.waresName }×\${orderItem.count }</div>
								{{/each}}
							</td>
						</tr>
						<tr>
							<td>领取人:</td>
							<td colspan="2">\${receiverName }</td>
							<td>部门:</td>
							<td>\${depart }</td>
						</tr>
						<tr>
							<td>联系号码:</td>
							<td colspan="2">\${receiverContact }</td>
							<td>总价:</td>
							<td>\${totalPrice}元</td>
						</tr>
					</tbody>
				</table>
			<div>
		</div>	
	</script>
	
</nav>
<script>
	$(function(){
		seajs.use(['ajax', 'dialog', 'utils'], function(Ajax, Dialog, utils){
			var $page = $('#canteen-week-orders');
			$('#selectDate', $page).datepicker({
				format		: 'yyyy-mm-dd',
				weekStart	: 1,
				daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],  
                monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',  
                        '七月', '八月', '九月', '十月', '十一月', '十二月' ]
			});
			function appendToForm(formData){
				$('.contains-checkbox :checkbox', $page).each(function(){
					var $this = $(this);
					formData.append($this.attr('name'), $this.is(':checked'));
				});
				formData.append('keyword', $('#keyword', $page).val() || '');
				formData.append('deliveryId', '${delivery.id}');
			}
			
			$('form', $page).on('cpf-submit', function(e, formData){
				appendToForm(formData);
			});
			$('.contains-checkbox', $page).on('cpf-checked-change', function(checked){
				$('form', $page).submit();
			});
			$('#keyword', $page).keypress(function(e){
				if(e.keyCode == 13){
					$('form', $page).submit();
				}
			});
			$('#download-btn', $page).click(function(){
				var formData = new FormData($('form', $page)[0]);
				appendToForm(formData);
				var data = {};
				formData.forEach(function(val, name){data[name] = val});
				var parameter = $.param(data);
				Ajax.download('admin/canteen/manage/export_orders?' + parameter);
			});
			/* $('#export', $page).click(function(){
				Ajax.download('admin/canteen/manage/export_orders/${delivery.id}');
				return false;
			}); */
			/* $('.print-order-tag', $page).click(function(){
				var orderId = $(this).closest('tr').attr('order-id');
				Ajax.ajax('admin/canteen/manage/order_tag', {
					orderId	: orderId
				}, {
					whenSuc	: function(data){
						var $printPage = $('#print-table', $page).tmpl(data);
						$printPage.printArea({ 
				    		mode 		: "iframe", 
				    		extraHead 	: '<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="chrome=1"/>',
				    		extraCss	: $('base').attr('href') + 'media/admin/canteen/css/order-tag.css',
				    		posWidth	: '60mm',
				    		posHeight	: '40mm'
				    	});
					}
				});
				
				
			}); */
			/* $('#print-all', $page).click(function(){
				Ajax.ajax('admin/canteen/manage/order_tag_all',{
					deliveryId	: '${delivery.id}'
				}, {
					whenSuc	: function(data){
						var orders = data.orders;
						var $container = $('<div>');
						for(var i in orders){
							var $printPage = $('#print-table', $page).tmpl(orders[i]);
							$container.append($printPage);
						}
						$container.printArea({ 
				    		mode 		: "iframe", 
				    		extraHead 	: '<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="chrome=1"/>',
				    		extraCss	: $('base').attr('href') + 'media/admin/canteen/css/order-tag.css',
				    		posWidth	: '60mm',
				    		posHeight	: '40mm'
				    	});
						
					}
				});
			}); */
			
			$('#print-btn', $page).click(function(){
				var formData = new FormData($('form', $page)[0]);
				appendToForm(formData);
				
				Ajax.ajax('admin/canteen/manage/print_orders', formData, 
					function(data, responseType){
						if(responseType === 'html'){
							var $printPage = $('<div>');
							$printPage.append(data);
							$printPage.printArea({ 
					    		mode 		: "iframe", 
					    		extraHead 	: '<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="chrome=1"/>',
					    		extraCss	: $('base').attr('href') + 'media/admin/canteen/css/print-orders.css',
					    		posWidth	: '210mm',
					    		posHeight	: '297mm'
					    	});
						}
					}
				);
			});
		});
		
	});
</script>

