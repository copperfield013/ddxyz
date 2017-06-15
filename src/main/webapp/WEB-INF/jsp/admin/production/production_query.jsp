<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<jsp:include page="/WEB-INF/jsp/admin/common/admin-include.jsp"></jsp:include>
<script src="media/admin/plugins/beyond/js/jquery-ui-1.10.4.custom.js"></script>
<script src="media/admin/plugins/beyond/js/skins.min.js"></script>
<link href="${basePath }media/admin/plugins/beyond/css/typicons.min.css" rel="stylesheet" />
<script src="${basePath }media/admin/plugins/printArea/jquery.PrintArea.js"></script>
<!DOCTYPE html>
<html>
	<head>
		<title>产品列表</title>
		<%response.setHeader("X-Frame-Options", "SAMEORIGIN");%>
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
				.condition-area > div.print-status-area,
				.condition-area > div.order-code-area {
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
		<div>
			<!-- <div class="page-header">
				<div class="header-title">
					<h1>生产管理-产品列表</h1>
				</div>
			</div> -->
				<div class="ui-page" id="product-list-page">
				    <div class="ui-body">
				        <div class="ui-content">
				            <form action="admin/production/query" id="production-query-form">
				            	<input type="hidden" name="pageNo" id="page-no" value="${pageInfo.pageNo }">
				            	<input type="hidden" name="withTimer" id="withTimer" value="${withTimer }"/>
				                <div class="condition-area">
				                    <c:if test="${not empty criteria.payTimeStr }">
				                    <div class="timepicker-area">
				                        <span>下单时间：${criteria.payTimeStr }</span>
				                        <input type="hidden" id="pay-time-str" name="payTimeStr" value="${criteria.payTimeStr }"/>
				                    </div>
			                	</c:if>
			                	<c:if test="${not empty criteria.timePoint }">
				                    <div class="time-choose-area">
				                        <span>时间档：${criteria.timePoint }点档</span>
				                        <input type="hidden" id="time-point" name="timePoint" value="${criteria.timePoint }"/>
				                    </div>
			                	</c:if>
			                	<c:if test="${not empty criteria.locationId}">
				                    <div class="address-choose-area">
				                        <span>配送地点：${criteria.locationName }</span>     
				                        <input type="hidden" id="location-id" name="locationId" value="${criteria.locationId }"/>
				                        <input type="hidden" id="location-name" name="locationName" value="${criteria.locationName }"/>
				                    </div>
			                	</c:if>
			                	<c:if test="${not empty criteria.printStatus}">
			                		<div class="print-status-area">
				                        <span>打印状态：
				                        	<c:choose>
				                        		<c:when test="${criteria.printStatus == 0 }">
				                        			未打印
				                        		</c:when>
				                        		<c:otherwise>
				                        			已打印
				                        		</c:otherwise>
				                        	</c:choose>
				                        </span> 
				                        <input type="hidden" id="printStatus" name="print-status" value="${criteria.printStatus }"/>
				                    </div>
			                	</c:if>
			                	<c:if test="${not empty criteria.orderCode }">
			                		<div class="order-code-area">
			                			<span>订单号：${criteria.orderCode }</span>
			                			<input type="hidden" id="order-code" name="orderCode" value="${criteria.orderCode }"/>
			                		</div>
			                	</c:if>
				                    <div class="tea-number-area">
				                    	共<span>${pageInfo.count }</span>杯，已选择<span id="select-count">0</span>杯
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
				                       <c:forEach items="${productList }" var="product">
				                    	<div class="detail-info">
				                            <label>
				                                <input type="checkbox" name="drink-product-id" class="pitch-on-order" value="${product.drinkProductId }"/>
				                                <span class="text"></span>
				                            </label>
				                            <p>
				                               	 编号:
				                                <span class="order-code">${product.orderCode }</span>
				                                                                                            （<span class="order-status">
				                               		<c:choose>
						                        		<c:when test="${product.productStatus > 1 }">
						                        			已打印
						                        		</c:when>
						                        		<c:otherwise>
						                        			未打印
						                        		</c:otherwise>
						                        	</c:choose>
				                                </span>）
				                            </p>
				                            <p>
				                               	 付款时间:
				                                <span class="payment-time"><fmt:formatDate value="${product.payTime }" pattern="HH:mm:ss"/></span>&nbsp;
				                                <span class="dispatching-time"><fmt:formatDate value="${product.timePoint }" pattern="HH"/>点档</span>&nbsp;
				                                	分发号:<span class="dispatching-number">${product.dispenseKey }</span>
				                            </p>
				                            <p>
				                                <span>${product.drinkName }</span>&nbsp;
				                               	 规格:<span class="tea-standard">${cupSizeMap[product.cupSize] }</span>&nbsp;
				                               	 甜度:<span class="tea-sweet">${sweetnessMap[product.sweetness] }</span>&nbsp;
				                               	 冰度:<span class="tea-temperature">${heatMap[product.heat] }</span>
				                            </p>
				                            <p>
				                               	 搭配:&nbsp;<span class="tea-match">${product.teaAdditionName }</span>&nbsp;
				                               	 加料:&nbsp;
				                               	 <span class="tea-add">
				                               	 	<c:forEach items="${product.additions }" var="addition">
				                               	 		${addition.additionTypeName }
				                               	 	</c:forEach>
												</span>
				                            </p>
				                            <p>
				                            	配送地址:<span class="dispatching-address">${product.locationName }</span>
				                            </p>
				                            <p>
				                               	 联系号码:<span class="phone-number">${product.receiverContact }</span>
				                                <i class="typcn typcn-printer single-printer" title="打印当前项"></i>
				                            </p>
				                        </div>
			                    	</c:forEach>
				                    </div>
				                </div>
				            </form>
				        </div>
				    </div>
				</div>

		</div>
		<script src="media/admin/plugins/beyond/js/bootstrap.js"></script>
	    <script src="media/admin/plugins/beyond/js/toastr/toastr.js"></script>
	    <script src="media/admin/plugins/beyond/js/beyond.min.js"></script>
	    
		<!-- 页面加载时把各个JS模块加载到容器当中 -->
		<script type="text/javascript">
			$(function(){
				
			});
		</script>
		
		<script type="text/javascript">
			$(function(){
				seajs.config({
					base	: '${basePath}media/admin/',
					paths	: {
						COMMON	: '${basePath}media/common/',
						MAIN	: '${basePath}media/admin/main/js/'
					},
				  	alias	: {
				  		'$CPF'		: 'COMMON/cpf/cpf-core.js',
						'utils'		: 'COMMON/cpf/cpf-utils.js',
						'page'		: 'COMMON/cpf/cpf-page.js',
						'dialog'	: 'COMMON/cpf/cpf-dialog.js',
						'paging'	: 'COMMON/cpf/cpf-paging.js',
						'tree'		: 'COMMON/cpf/cpf-tree.js',
						'form'		: 'COMMON/cpf/cpf-form.js',
						'tab' 		: 'COMMON/cpf/cpf-tab.js',
						'ajax'		: 'COMMON/cpf/cpf-ajax.js',
						'css'		: 'COMMON/cpf/cpf-css.js',
						'timer'		: 'COMMON/cpf/cpf-timer.js',
						'console'	: 'COMMON/cpf/cpf-console.js',
						'control'	: 'COMMON/cpf/cpf-control.js'
						//..其他模块
				  	}
				});
				seajs.use('COMMON/cpf/cpf-main.js', function(){
					
					$('iframe',window.parent.document).css("display","block")
					seajs.use(['ajax','dialog','utils'], function(Ajax, Dialog, utils){
						var a = '<%=request.getParameter("withTimer") %>';
						var $page = $('#product-list-page');
						var pageCount = Math.ceil('${pageInfo.count }' / '${pageInfo.pageSize }');
						var withTimer = $("#withTimer", $page).val();
						var autoFunction;
						
						autoQuery();
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
					       	getCheckedCount();
				       	}
						//单选 全选 调用 订单选择方法
						$(".detail-info", $page).on("click",function(){
							stopAutoQuery();
					        var warp = $(this);
					        var checked = $(this).find("input");
					        pitchOn(warp,checked);
				    	});
						//上一页
						$("#to-left", $page).on("click", function(){
							stopAutoQuery();
							var pageNo = $("#page-no").val();
							if(pageNo <= 1){
								return;
							}
							pageNo  = Number(pageNo) - 1;
							$("#page-no").val(pageNo);
							$("#production-query-form").submit();
						});
						//下一页
						$("#to-right", $page).on("click", function(){
							stopAutoQuery();
							var pageNo = $("#page-no").val();
							if(pageNo >= pageCount){
								return;
							}
							pageNo  = Number(pageNo) + 1;
							$("#page-no", $page).val(pageNo);
							$("#production-query-form").submit();
						});
						//全选
				    	$("#all-cheaked", $page).on("click",function(){
				    		stopAutoQuery();
					        var warp = $(".detail-info");
					        var checked = $(".pitch-on-order");
					        var checkedArray = [];
					        warp.each(function(){
					           checkedArray.push($(this).hasClass('active'));;
					        });
					        var allChecked = checkedArray.indexOf(false);
					        pitchOn(warp,checked,allChecked);
				    	});
				    	
				    	//打印选中项
				    	$("#printer", $page).on("click", function(event){
				    		event.stopPropagation();
				    		stopAutoQuery();
				    		var productIds = "";
							var checkedCount = $(".detail-info.active", $page).each(function(){
								productIds += $(this).find("input[name='drink-product-id']").val() + ",";
							}).length;
							productIds = productIds.substring(0, productIds.length - 1);
							if(checkedCount > 0){
								Dialog.confirm('是否打印' + checkedCount + '条数据？', function(isYes){
									if(isYes){
										printProduct(productIds);
									}
								});
							}else{
								Dialog.notice("请选择要打印的产品！","warning");
							}
							/* if(checkedCount > 0){
								printProduct(productIds);
							}else{
								alert("请选择要打印的产品！");
							} */
				    		
				    	});
				    	// 单独打印图标点击事件
				    	 $(".single-printer", $page).on("click",function(event){
				    		 event.stopPropagation();
				    		 var productionId = $(this).closest(".detail-info").find("input[name='drink-product-id']").val();
				    		 stopAutoQuery();
				    		 printProduct(productionId);
				    	});
				    	//自动刷新
				    	$("#refresh-all", $page).on("click", function(){
				    		withTimer = 'true';
				    		$("#withTimer", $page).val(withTimer);
				    		autoQuery();
				    	})
				    	
				    	//刷新
				    	$("#refresh", $page).on("click", function(){
				    		stopAutoQuery();
				    		querySubmit();
				    	});
				    	//获取当前选中的数目
				    	function getCheckedCount(){
				    		var length = $(".detail-info.active", $page).length;
				    		$("#select-count").text(length);
				    	}
				    	
				    	//打印产品
				    	function printProduct(productIds){
							Ajax.ajax('admin/production/product-print', {
								productIds : productIds
							}, function(html){
								var options = { 
							    		mode 		: "iframe", 
							    		extraHead 	: headElements,
							    		extraCss	: $('base').attr('href') + 'media/admin/production/PrintArea_fac.css'
							    	};
								var headElements ='<meta charset="utf-8" />,<meta http-equiv="X-UA-Compatible" content="chrome=1"/>';
								var $container = $('.print-page', html);
								$container.printArea(options);
							});
						}
				    	//定时提交查询
				    	function autoQuery(){
				    		if(withTimer == 'true'){
				    			autoFunction = setTimeout('querySubmit()', 5000);
				    		}
				    	}
				    	
				    	//关闭自动查询
				    	function stopAutoQuery(){
				    		if(withTimer == 'true'){
				    			withTimer = 'false';
				    			$("#withTimer", $page).val(withTimer);
				    			clearTimeout(autoFunction);
				    		}
				    	}
				    	//单次提交查询
				    	window.querySubmit = function(){
				    		$("#page-no", $page).val(1);
				    		$("#production-query-form").submit();
				    	}
					});
				});
				
				
				
				
			});
		</script>
	</body>
</html>