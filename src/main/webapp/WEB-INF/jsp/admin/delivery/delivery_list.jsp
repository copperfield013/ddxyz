<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<script src="${basePath }media/admin/plugins/printArea/jquery.PrintArea.js"></script>
<style>
	.condition-area {
		margin-top: 10px;
	}
	.order-info span, .receiver-info span {
		margin-left: 1em;
		margin-right: 1em;
	}
	span.order-status {
		    color: #ff0000;
		    font-weight: bold;
		    font-size: 1.1em;
	}
	.order-time {
		font-size: 0.7em;
		margin: 5px 0 5px 2em;
		font-weight: bold;
		color: #999;
	}
	.product-item {
		border : 1px solid #3498DB;
		border-radius: 5px;
		margin: 15px 40px 15px 0;
		padding:15px;
		position:  relative;
		overflow: hidden;
	}
	.product-info li {
		list-style-type: none;
	}
	.product-info li span {
		margin-right:1em;
		display: block;
		margin-bottom: 2px;
		font-size: 14px;
	}
	.order-info, .product-info {
		border-bottom: 1px solid #ccc;
		margin-bottom: 5px;
		padding-bottom: 5px;
	}
</style>
<c:set var="withTimer" value="${param.withTimer }" />
<div class="ui-page" id="delivery-list-page">
	<div class="ui-body">
		<div class="ui-content">
			<form class="" action="admin/delivery/delivery_list">
				<div class="condition-area">
					<label>订单编号：</label>
					<input type="text" class="form-control" css-display="inline-block" css-width="10em" name="orderCode" value="${criteria.orderCode }" />
					<label>订单日期：</label>
					<input type="text" class="form-control" id="timeRange" name="timeRange" readonly="readonly" value="${criteria.timeRange }" css-width="20em"  css-cursor="text" />
					<label>预定时间点：</label>
					<select id="timePoint" name="timePoint" data-value="${criteria.timePoint }">
						<option value="">--全部--</option>
						<c:forEach var="i" begin="9" end="21" step="1"> 
							<option value="${i }">${i }点</option>
						</c:forEach>
					</select>
					<select name="hasPrinted" data-value="${criteria.hasPrinted }">
						<option value="">--全部--</option>
						<option value="-1">未打印</option>
						<option value="1">已打印</option>
					</select>
					<label>配送地点：</label>
					<input type="text" class="form-control" css-width="8em" name="locationName" value="${criteria.locationName }" />
					
					<input type="hidden" name="withTimer" id="withTimer" value="${withTimer }"/>
					<div class="widget-buttons">
					    <div class="btn-group" id="query-btn-group">
					        <a class="btn" id="query">查询</a>
					        <a class="btn dropdown-toggle" data-toggle="dropdown"><i class="fa fa-angle-down"></i></a>
					        <ul class="dropdown-menu pull-left">
					            <li>
					                <a class="btn" id="auto-query">自动查询</a>
					                <a class="btn" id="cancel-query" style="display: none;">暂停查询</a>
					            </li>
					        </ul>
					    </div>
					</div>
					<!-- <input id="confirm" class="btn" style="width:4em;" type="submit" value="检索"/> -->
				</div>
			</form>
			<div class="product-info-area">
				<ul class="product-list">
					<c:forEach items="${orderList }" var="plainOrder">
						<li class="product-item">
							<div class="order-info">	
								<span class="order-code">
									<input type="hidden" id="orderIdHidden" name="orderId" value="${plainOrder.id }">
									订单编号:${plainOrder.orderCode }
								</span>
								<span class="order-status">正在制作${completedCountMap[plainOrder] }杯</span>
								<div class="order-time">
									<span>下单时间：<fmt:formatDate value="${plainOrder.createTime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
									<span>付款时间：<fmt:formatDate value="${plainOrder.payTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
									<span>配送时间：<fmt:formatDate value="${plainOrder.timePoint }" pattern="HH"></fmt:formatDate>点档</span>
								</div>
							</div>
							<div class="product-info">
								<ul>
									<c:forEach items="${itemMap[plainOrder] }" var="plainOrderDrinkItem">
										<li>
											<span>
												${plainOrderDrinkItem.drinkName }:&nbsp;&nbsp;
												${plainOrderDrinkItem.teaAdditionName }&nbsp;&nbsp;
												${cupSizeMap[plainOrderDrinkItem.cupSize] }&nbsp;&nbsp;
												${sweetnessMap[plainOrderDrinkItem.sweetness] }&nbsp;&nbsp;
												${heatMap[plainOrderDrinkItem.heat] }&nbsp;&nbsp;
												加料：
												<c:forEach items="${additionMap[plainOrderDrinkItem] }" var="addition">
													${addition.additionTypeName }
												</c:forEach>
											</span>
										</li>
									</c:forEach>
								</ul>	
							</div>
							<div class="receiver-info">
								<span class="receiver-addr">配送地址：${plainOrder.locationName }</span>
								<span class="receiver-phone">联系电话：${plainOrder.receiverContact }</span>
								<a href="#" class="print-link-a">打印</a>
							</div>
						</li>
					</c:forEach>
				</ul>
				<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>				
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		seajs.use(['ajax','dialog','utils', 'timer'], function(Ajax, Dialog, utils, Timer){
			var $page =$('#delivery-list-page');
			$('#timeRange', $page).daterangepicker({
				format 				: 'YYYY-MM-DD HH:mm:ss',
				timePicker			: true,
				timePicker12Hour	: false,
				timePickerIncrement : 5,
				separator			: '~',
				locale				: {
					applyLabel	: '确定',
	                cancelLabel: '取消',
	                fromLabel: '从',
	                toLabel: '到'
				}
			});
			
			//执行查询
			function query(){
				$('#delivery-list-page form').submit();
			}
			var queryTimer = null;
			var timerId = 'delivery_list_timer';
			//开启定时器延迟查询
			function startTimer(){
				queryTimer = Timer.createTimer({
					id		: timerId,
					interval: 5000,
					callback: function(){
						//如果当前页面
						var withTimer = $('#delivery-list-page #withTimer').val();
						console.log(withTimer);
						if(withTimer == 'true'){
							query();
						}else{
							Timer.removeTimer(timerId);
						}
					}
				});
				queryTimer.start();
			}
			//关闭定时器
			function stopTimer(){
				Timer.removeTimer(timerId);
			}
			
			function showQuery(opt){
				var $group = $('#query-btn-group', $page),
					$menu = $('.dropdown-menu', $group),
					$showBtn = $group.children('a.btn').first(),
					$toShowBtn = null;
				$('<li>').append($showBtn).prependTo($menu);
				if(opt === 'cancel'){
					$group.find('#auto-query').hide();
					$toShowBtn = $group.find('#cancel-query');
				}else if(opt === 'auto'){
					$group.find('#cancel-query').hide();
					$toShowBtn = $group.find('#auto-query');
				}else{
					$group.find('#cancel-query').hide();
					$toShowBtn = $group.find('#query');
				}
				$toShowBtn.prependTo($group).show();
			}
			
			var withTimer = '${withTimer}';
			
			if(withTimer == 'true'){
				//当前是自动查询
				showQuery('cancel');
			}
			//点击查询按钮回调
			$('#query', $page).click(function(){
				$('#withTimer', $page).val('');
				stopTimer();
				query();
			});
			
			//点击自动查询按钮回调
			$('#auto-query', $page).click(function(){
				$('#withTimer', $page).val('true');
				showQuery('cancel');
				startTimer();
			});
			
			$('#cancel-query', $page).click(function(){
				$('#withTimer', $page).val('');
				stopTimer();
				showQuery('auto');
			});
			
			$('.print-link-a', $page).click(function(){
				var link_a = $(this).closest("li");

				var orderId = link_a.find("input[name='orderId']").val();
				console.log(orderId);
				printOrder(orderId);
			});
			
			function printOrder(orderId){
				Ajax.ajax('admin/delivery/delivery-print', {
					orderId: orderId
				}, function(html){
					var options = { 
				    		mode 		: "iframe", 
				    		extraHead 	: headElements,
				    		extraCss	: $('base').attr('href') + 'media/admin/delivery/PrintArea.css?${RES_STAMP}'
				    	};
					var headElements ='<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="chrome=1"/>';
					var $container = $('.PrintArea', html);
					$container.printArea(options);
				});
			}
		})
	})
</script>