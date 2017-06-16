<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
 <link href="media/admin/plugins/beyond/css/typicons.min.css" rel="stylesheet" />
<script src="${basePath }media/admin/plugins/printArea/jquery.PrintArea.js"></script>
<style>
	/* .condition-area {
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
	} */
	
	body,html {
    padding:0;
    margin:0;
    font-size: 13px;
    width:100%;
    height:100%;
}
body{ -webkit-user-select:none; -moz-user-select:none; -ms-user-select:none; user-select:none; }
* {
    box-sizing: border-box;
}
.ui-page,.ui-body,.ui-content,.ui-content>form{
    width:100%;
    height:100%;
}
.ui-content {
					position:relative;
				}
.condition-area {
    width:100%;
    padding:0.6em 2em;
    height:4em;
    line-height: 4em;
    border-bottom:1px solid #000;
}
.condition-area > div.timepicker-area,
.condition-area > div.time-choose-area,
.condition-area > div.address-choose-area,
.condition-area > div.print-status-area {
    float:left;
    width:11.2em;
    border:1px solid #d5d5d5;
    height:2.8em;
    line-height: 2.8em;
    margin-right:0.5em;
   text-align: center;
    background-color:#ffffff;
}


div.condition-area > div.tea-number-area {
    float:right;
    margin-right:0.2em;
}

div.content-area {
    position:absolute;
    top:4.6em;
    bottom:0;
    left:0;
    right:0;
}
div.content-area > div.detail-operation {
    height:3em;
    line-height: 3em;
    margin-right:2em;
    position: relative;
    z-index: 9;
}
div.content-area > div.detail-operation >div {
    float:right;
    border:1px solid transparent;
}
div.content-area > div.detail-operation >div > i{
    margin-left:1em;
    cursor: pointer;
    display:inline-block;
    text-align:center;
    width:2.8em;
    height:2.4em;
    line-height:2.4em;
    border-radius:0.6em;
}
div.content-area > div.detail-operation >div > i#refresh {
  margin-top:0.2em;
}
div.content-area > div.detail-operation >div > i:hover {
	color:#ffffff;
	background-color:#044d11;
}
div.content-area > div.detail-operation >div > i:before {
    font-size: 2em;
}
div.content-area > div.detail-operation >div > i#refresh:before {
   font-size: 2.4em;
}
div.content-area >div.detail-info-area {
   position: absolute;
    top:3em;
    left:0;
    right:0;
    bottom:0;
    margin-top:-4em;
}
div.content-area >div.detail-info-area >div.detail-info {
    float:left;
    width:29%;
    height:40%;
    margin-left:4%;
    border:1px solid #cccccc;
    margin-top:4em;
    background-color:#ffffff;
    color:#000000;
    padding:0 1em;
}

div.content-area >div.detail-info-area >div.detail-info.active {
    background-color:#044D11;
    color:#ffffff;
    border:1px solid transparent;
}

div.content-area >div.detail-info-area >div.detail-info > label {
    display: none;
}

div.content-area >div.detail-info-area >div.detail-info:first-child,
div.content-area >div.detail-info-area >div.detail-info:nth-child(4){
    margin-left:2%;
}

/*方便媒体查询 选择器简便书写*/
div.detail-info >p {
    margin-top:1.1em;
}
div.detail-info >p > i.single-printer{
    float:right;
    color:#000000;
    width:3em;
    height:3em;
    cursor: pointer;
    position: relative;
    z-index: 9;
}
div.content-area >div.detail-info-area >div.detail-info.active >p > i.single-printer {
    color:#ffffff;
}
div.detail-info >p > i.single-printer:before{
    font-size: 2em;
}
@media screen and (max-height:680px){
    div.detail-info >p {
        margin:1em 0;
        line-height:1.5em;
    }
    div.content-area >div.detail-info-area >div.detail-info {
        margin-top:1em;
    }
    div.content-area >div.detail-info-area >div.detail-info {
    	height: 45%;
    }
    .condition-area {
    	padding: 0.6em 0.1em;
    }
     div.content-area >div.detail-info-area {
        margin-top:-1em;
    }
}
@media screen and (max-height:620px){
    div.content-area >div.detail-info-area >div.detail-info {
        margin-top:0.5em;
    }
    div.content-area >div.detail-info-area {
        margin-top:0em;
    }
    div.content-area >div.detail-info-area >div.detail-info {
    	padding:0 0.1em 0 0.5em;
    }
    div.content-area > div.detail-operation {
    	height: 2em;
    	line-height: 2em;
    }
    div.content-area >div.detail-info-area {
	    top: 2em;
    }
    div.detail-info >p {
    	line-height:1.3em;
    }
}
@media screen and (max-height:590px){
    div.detail-info >p {
    	line-height:1em;
    }
}
@media screen and (max-height:520px){
    div.detail-info >p {
    	line-height:0.5em;
    }
}
@media screen and (max-width:1080px){
    div.content-area >div.detail-info-area >div.detail-info {
        width:32%;
        margin-left:1%;
    }
    div.content-area >div.detail-info-area >div.detail-info:first-child, 
    div.content-area >div.detail-info-area >div.detail-info:nth-child(4) {
    	margin-left:1%;
    }
}
@media screen and (max-width:1000px){
    div.content-area >div.detail-info-area >div.detail-info {
        width:32%;
        margin-left:1%;
    }
    div.detail-info >p {
        
    }
}
@media screen and (max-width:800px){
    div.content-area >div.detail-info-area >div.detail-info {
        width:32%;
        margin-left:1%;
    }
    div.detail-info >p > i.single-printer {
    	bottom:0.1em;
    }
    div.detail-info >p {
       
    }
}

@media screen and (max-width:640px){
    div.content-area >div.detail-info-area >div.detail-info {
        height:45%;
    }
    div.detail-info >p {
        margin:0 0 0 0 ;
        padding:0;
    }
}
</style>
<c:set var="withTimer" value="${param.withTimer }" />
<%-- <div class="ui-page" id="delivery-list-page">
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
</div> --%>
<div class="ui-page" id="product-list-page">
    <div class="ui-body">
        <div class="ui-content">
            <form action="admin/production/product-list">
                <div class="condition-area">
                    <div class="timepicker-area">
                        <span>下单时间：2017-5-31</span>
                    </div>
                    <div class="time-choose-area">
                        <span>时间档：15点档</span>
                    </div>
                    <div class="address-choose-area">
                        <span>配送地点：杭钻大厦</span>     
                    </div>
                    <div class="print-status-area">
                        <span>打印状态：未打印</span> 
                    </div>
                    <div class="tea-number-area">
                        共<span>50</span>杯，已选择<span>1</span>杯
                    </div>
                </div>
                <div class="content-area">
                    <div class="detail-operation">
                            <div>
                                <i class="typcn typcn-arrow-left-thick operation-icon" id="to-left" title="前一页"></i>

                                <i class="typcn typcn-arrow-right-thick operation-icon" id="to-right" title="后一页"></i>

                                <i class="typcn typcn-input-checked operation-icon" id="all-cheaked" title="全选"></i>

                                <i class="typcn typcn-printer operation-icon" id="printer" title="打印选中项"></i>

                                <i class="typcn typcn-refresh-outline operation-icon" id="refresh-all" title="自动刷新"></i>

                                <i class="typcn typcn-refresh operation-icon" id="refresh" title="刷新"></i>
                            </div>
                    </div>
                    <div class="detail-info-area">
                        <div class="detail-info">
                            <label>
                                <input type="checkbox" name="order-number" class="pitch-on-order" value="1"/>
                                <span class="text"></span>
                            </label>
                            <p>
                                编号:
                                <span class="order-id">000220170421201459</span>
                                                                                            （<span class="order-status">未打印</span>）
                            </p>
                            <p>
                                付款时间:
                                <span class="payment-time">20:15:00</span>&nbsp;
                                <span class="dispatching-time">15点档</span>&nbsp;
                                分发号:<span class="dispatching-number">1</span>
                            </p>
                            <p>
                                <span>奶茶</span>&nbsp;
                                规格:<span class="tea-standard">中杯</span>&nbsp;
                                甜度:<span class="tea-sweet">5分甜</span>&nbsp;
                                冰度:<span class="tea-temperature">冰</span>
                            </p>
                            <p>
                                搭配:&nbsp;<span class="tea-match">红茶</span>&nbsp;
                                加料:&nbsp;<span class="tea-add">珍珠、波霸、椰果</span>
                            </p>
                            <p>
                                配送地址:<span class="dispatching-address">杭钻大厦</span>
                            </p>
                            <p>
                                联系号码:<span class="phone-number">13588888888</span>
                                <i class="typcn typcn-printer single-printer" title="打印当前项"></i>
                            </p>
                        </div>
                        <div class="detail-info">
                            <label>
                                <input type="checkbox" name="order-number" class="pitch-on-order" value="1"/>
                                <span class="text"></span>
                            </label>
                            <p>
                                编号:
                                <span class="order-id">000220170421201459</span>
                                                                                            （<span class="order-status">未打印</span>）
                            </p>
                            <p>
                                付款时间:
                                <span class="payment-time">20:15:00</span>&nbsp;
                                <span class="dispatching-time">15点档</span>&nbsp;
                                分发号:<span class="dispatching-number">1</span>
                            </p>
                            <p>
                                <span>奶茶</span>&nbsp;
                                规格:<span class="tea-standard">中杯</span>&nbsp;
                                甜度:<span class="tea-sweet">5分甜</span>&nbsp;
                                冰度:<span class="tea-temperature">冰</span>
                            </p>
                            <p>
                                搭配:&nbsp;<span class="tea-match">红茶</span>&nbsp;
                                加料:&nbsp;<span class="tea-add">珍珠、波霸、椰果</span>
                            </p>
                            <p>
                                配送地址:<span class="dispatching-address">杭钻大厦</span>
                            </p>
                            <p>
                                联系号码:<span class="phone-number">13588888888</span>
                                <i class="typcn typcn-printer single-printer" title="打印当前项"></i>
                            </p>
                        </div>
                        <div class="detail-info">
                            <label>
                                <input type="checkbox" name="order-number" class="pitch-on-order" value="1"/>
                                <span class="text"></span>
                            </label>
                            <p>
                                编号:
                                <span class="order-id">000220170421201459</span>
                                                                                            （<span class="order-status">未打印</span>）
                            </p>
                            <p>
                                付款时间:
                                <span class="payment-time">20:15:00</span>&nbsp;
                                <span class="dispatching-time">15点档</span>&nbsp;
                                分发号:<span class="dispatching-number">1</span>
                            </p>
                            <p>
                                <span>奶茶</span>&nbsp;
                                规格:<span class="tea-standard">中杯</span>&nbsp;
                                甜度:<span class="tea-sweet">5分甜</span>&nbsp;
                                冰度:<span class="tea-temperature">冰</span>
                            </p>
                            <p>
                                搭配:&nbsp;<span class="tea-match">红茶</span>&nbsp;
                                加料:&nbsp;<span class="tea-add">珍珠、波霸、椰果</span>
                            </p>
                            <p>
                                配送地址:<span class="dispatching-address">杭钻大厦</span>
                            </p>
                            <p>
                                联系号码:<span class="phone-number">13588888888</span>
                                <i class="typcn typcn-printer single-printer" title="打印当前项"></i>
                            </p>
                        </div>
                        <div class="detail-info">
                            <label>
                                <input type="checkbox" name="order-number" class="pitch-on-order" value="1"/>
                                <span class="text"></span>
                            </label>
                            <p>
                                编号:
                                <span class="order-id">000220170421201459</span>
                                                                                            （<span class="order-status">未打印</span>）
                            </p>
                            <p>
                                付款时间:
                                <span class="payment-time">20:15:00</span>&nbsp;
                                <span class="dispatching-time">15点档</span>&nbsp;
                                分发号:<span class="dispatching-number">1</span>
                            </p>
                            <p>
                                <span>奶茶</span>&nbsp;
                                规格:<span class="tea-standard">中杯</span>&nbsp;
                                甜度:<span class="tea-sweet">5分甜</span>&nbsp;
                                冰度:<span class="tea-temperature">冰</span>
                            </p>
                            <p>
                                搭配:&nbsp;<span class="tea-match">红茶</span>&nbsp;
                                加料:&nbsp;<span class="tea-add">珍珠、波霸、椰果</span>
                            </p>
                            <p>
                                配送地址:<span class="dispatching-address">杭钻大厦</span>
                            </p>
                            <p>
                                联系号码:<span class="phone-number">13588888888</span>
                                <i class="typcn typcn-printer single-printer" title="打印当前项"></i>
                            </p>
                        </div>
                        <div class="detail-info">
                            <label>
                                <input type="checkbox" name="order-number" class="pitch-on-order" value="1"/>
                                <span class="text"></span>
                            </label>
                            <p>
                                编号:
                                <span class="order-id">000220170421201459</span>
                                                                                            （<span class="order-status">未打印</span>）
                            </p>
                            <p>
                                付款时间:
                                <span class="payment-time">20:15:00</span>&nbsp;
                                <span class="dispatching-time">15点档</span>&nbsp;
                                分发号:<span class="dispatching-number">1</span>
                            </p>
                            <p>
                                <span>奶茶</span>&nbsp;
                                规格:<span class="tea-standard">中杯</span>&nbsp;
                                甜度:<span class="tea-sweet">5分甜</span>&nbsp;
                                冰度:<span class="tea-temperature">冰</span>
                            </p>
                            <p>
                                搭配:&nbsp;<span class="tea-match">红茶</span>&nbsp;
                                加料:&nbsp;<span class="tea-add">珍珠、波霸、椰果</span>
                            </p>
                            <p>
                                配送地址:<span class="dispatching-address">杭钻大厦</span>
                            </p>
                            <p>
                                联系号码:<span class="phone-number">13588888888</span>
                                <i class="typcn typcn-printer single-printer" title="打印当前项"></i>
                            </p>
                        </div>
                        <div class="detail-info">
                            <label>
                                <input type="checkbox" name="order-number" class="pitch-on-order" value="1"/>
                                <span class="text"></span>
                            </label>
                            <p>
                                编号:
                                <span class="order-id">000220170421201459</span>
                                                                                            （<span class="order-status">未打印</span>）
                            </p>
                            <p>
                                付款时间:
                                <span class="payment-time">20:15:00</span>&nbsp;
                                <span class="dispatching-time">15点档</span>&nbsp;
                                分发号:<span class="dispatching-number">1</span>
                            </p>
                            <p>
                                <span>奶茶</span>&nbsp;
                                规格:<span class="tea-standard">中杯</span>&nbsp;
                                甜度:<span class="tea-sweet">5分甜</span>&nbsp;
                                冰度:<span class="tea-temperature">冰</span>
                            </p>
                            <p>
                                搭配:&nbsp;<span class="tea-match">红茶</span>&nbsp;
                                加料:&nbsp;<span class="tea-add">珍珠、波霸、椰果</span>
                            </p>
                            <p>
                                配送地址:<span class="dispatching-address">杭钻大厦</span>
                            </p>
                            <p>
                                联系号码:<span class="phone-number">13588888888</span>
                                <i class="typcn typcn-printer single-printer" title="打印当前项"></i>
                            </p>
                        </div>            
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
$(function(){
	seajs.use(['ajax','dialog','utils', 'timer'], function(Ajax, Dialog, utils, Timer){
		var a = '<%=request.getParameter("withTimer") %>';
		var $page = $('#product-list-page');
	
		//订单选择方法
		function pitchOn(warp,checked,allChecked){
	        var that = warp;
	        var thisChecked = checked;
	       if( allChecked !==undefined){
	           if(allChecked == -1){
	               that.removeClass("active");
	               thisChecked.prop("checked",false);
	           }else {
	               that.addClass("active");
	               thisChecked.prop("checked",true);
	           }
	       }else{
	           if(!that.hasClass("active")){
	               that.addClass("active");
	               thisChecked.prop("checked",true);
	           }else {
	               that.removeClass("active");
	               thisChecked.prop("checked",false);
	           }
	       }
	    }
		//单选 全选 调用 订单选择方法
		$(".detail-info").on("click",function(){
	        var warp = $(this);
	        var checked = $(this).find("input");
	        pitchOn(warp,checked);
    	});
    	$("#all-cheaked").on("click",function(){
	        var warp = $(".detail-info");
	        var checked = $(".pitch-on-order");
	        var checkedArray = [];
	        warp.each(function(){
	           checkedArray.push($(this).hasClass('active'));;
	        });
	        var allChecked = checkedArray.indexOf(false);
	        pitchOn(warp,checked,allChecked);
    	})
    	// 单独打印图标点击事件
    	 $(".single-printer").on("click",function(event){
    		 event.stopPropagation();
        
    	})	
	})
}) 
	/* $(function(){
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
	}) */
</script>