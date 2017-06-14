<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<link href="../../media/admin/plugins/beyond/css/typicons.min.css" rel="stylesheet" />
<!DOCTYPE html>
<html>
	<head>
		<title>产品列表</title>
		<%response.setHeader("X-Frame-Options", "SAMEORIGIN");%>
		<jsp:include page="/WEB-INF/jsp/admin/common/admin-include.jsp"></jsp:include>
		<style>
				body,html {
				    padding:0;
				    margin:0;
				    font-size: 13px;
				    width:100%;
				    height:100%;
				    background-color:#fbfbfb;
				}
				body{ -webkit-user-select:none; -moz-user-select:none; -ms-user-select:none; user-select:none; }
				* {
				    box-sizing: border-box;
				}
				.ui-page,.ui-body,.ui-content,.ui-content>form{
				    width:100%;
				    height:100%;
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
				@media screen and (max-height:635px){
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
				@media screen and (max-height:575px){
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
				@media screen and (max-height:545px){
				    div.detail-info >p {
				    	line-height:1em;
				    }
				}
				@media screen and (max-height:475px){
				    div.detail-info >p {
				    	line-height:0.5em;
				    }
				}
				@media screen and (max-width:856px){
				    div.content-area >div.detail-info-area >div.detail-info {
				        width:32%;
				        margin-left:1%;
				    }
				    div.content-area >div.detail-info-area >div.detail-info:first-child, 
				    div.content-area >div.detail-info-area >div.detail-info:nth-child(4) {
				    	margin-left:1%;
				    }
				}
				@media screen and (max-width:776px){
				    div.content-area >div.detail-info-area >div.detail-info {
				        width:32%;
				        margin-left:1%;
				    }
				    div.detail-info >p {
				       
				    }
				}
				@media screen and (max-width:576px){
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
				
				@media screen and (max-width:416px){
				    div.content-area >div.detail-info-area >div.detail-info {
				        height:45%;
				    }
				    div.detail-info >p {
				        margin:0 0 0 0 ;
				        padding:0;
				    }
				}
		</style>
	</head>
	<body>
		<div class="">
			<!-- <div class="page-header">
				<div class="header-title">
					<h1>生产管理-产品列表</h1>
				</div>
			</div> -->
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

		</div>
		<script type="text/javascript">
			$(function(){
				window.parent.document
				$('iframe',window.parent.document).css("display","block")
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
				
			});
		</script>
	</body>
</html>