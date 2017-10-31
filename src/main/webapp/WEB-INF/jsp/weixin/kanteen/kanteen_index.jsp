<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
    
    <title>林家食堂</title>
	<jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include-kanteen.jsp"></jsp:include>
    <link rel="stylesheet" href="media/weixin/kanteen/css/kanteen-index.css?3">
    <script src="media/weixin/kanteen/js/kanteen-index.js?2"></script>
</head>

<body>
    <header class="page-header">
        <!--搜索框-->
        <form class="canteen-search-bar_form">
            <div class="canteen-search-bar_box">
                <i class="canteen-icon canteen-search-icon"></i>
                <input id="canteenSearch" type="text" name="canteenSearch" placeholder="搜索商品">
                <i class="canteen-search-bar_clear canteen-icon canteen-search-cancel-icon"></i>
            </div>
            <label for="canteenSearch" class="canteen-search-bar_label">
                <i class="canteen-icon canteen-search-icon"></i>
                <span>搜索商品</span>
            </label>
        </form>

        <!--展示信息-->
        <section class="canteen-information-area">
            <div class="canteen-information_basic">
                <!--<img id="canteenLogo" src="static/image/canteen_logo.png" alt="林家食堂">-->
                <div class="canteen-information_basic_detail">
                    <p class="canteen-information_bookingtime">预订时间：<fmt:formatDate value="${distribution.startTime }" pattern="yyyy-MM-dd HH:mm"/>~<fmt:formatDate value="${distribution.endTime }" pattern="yyyy-MM-dd HH:mm"/> </p>
                </div>
            </div>
            <div class="canteen-information_announcement">
                <span class="canteen-information_announcement_text canteen-icon canteen-announcement-icon">公告内容：</span>
                <!--动画部分-->
                <div class="canteen-information_announcement_detail">
                    <ul class="canteen-information_announcement_carousel">
                    	<c:forEach items="${merchant.announces }" var="announce">
                    		<li>${announce }</li>
                    	</c:forEach>
                    </ul>
                </div>
            </div>
        </section>
    </header>

    <!--主体部分包裹层-->
    <div class="canteen-main-wrap">
        <!--餐品选择导航-->
        <!--data-key是标识，需要和对应的展示列表 data-uid 的值一样，可以换为后台已有标识字段 -->
        <nav class="canteen-meal-nav">
        	<c:forEach items="${menu.menuItemMap }" var="menuItem" varStatus="i">
        		<c:set var="group" value="${menuItem.key }" />
	            <a data-key="${group.id }" href="javascript:" class="canteen-meal-nav_button canteen-icon canteen-hot-sell-icon ${i.index==0?'active':'' }">${group.name }</a>
        	</c:forEach>
        </nav>
        <!--餐品列表部分-->
        <!--data-uid是标识，需要和对应的nav模块菜单中的 data-key 的值一样，可以换为后台已有标识字段  :String -->
        <!--data-prouid是商品标识，可替换为后台已有字段，具有唯一性与商品一一对应 :String -->
        <c:forEach items="${menu.menuItemMap }" var="menuItem" varStatus="i">
       		<c:set var="group" value="${menuItem.key }" />
             <div data-uid="${group.id }" class="canteen-meal-list ${i.index==0?'active':'' }">
             	<header class="canteen-meal-list_title canteen-icon canteen-title-bar-icon">${group.name }</header>
             	<c:forEach items="${menuItem.value }" var="wares">
            		<div data-wares-id="${wares.waresId }" 
            			data-prouid="${wares.distributionWaresId }" 
            			data-base-price="<fmt:formatNumber value="${wares.basePrice/100 }" pattern="0.00" />" 
            			class="canteen-meal-list_menu ${menu.waresOptionGroupMap[wares.waresId] != null? 'wares-with-option' : ''}" >
						<img class="canteen-meal-list_image" src="${wares.picUri }" alt="${wares.waresName }">
						<div class="canteen-meal-list_menu_detail">
							<p class="canteen-meal-list_menu_name">${wares.waresName }</p>
		                    <p class="canteen-meal-list_menu_sales">
		                    	<c:if test="${wares.maxCount != null }">
		                    		<span>余量${wares.maxCount }</span>
		                    	</c:if>
		                        <span>已售${wares.currentCount }份</span>
		                    </p>
		                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
		                        <span class=""><fmt:formatNumber value="${wares.basePrice/100 }" pattern="0.00" />元/${wares.priceUnit }</span>
		                    </p>
		                    <c:choose>
		                    	<c:when test="${menu.waresOptionGroupMap[wares.waresId] != null}">
				                    <a class="kanteen-optionsgroup" href="javascript:;">选规格</a>
		                    	</c:when>
		                    	<c:otherwise>
		                    		<div class="canteen-meal-list_menu_button">
				                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
				                        <span class="canteen-meal-list_menu_count"></span>
				                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
				                    </div>
		                    	</c:otherwise>
		                    </c:choose>
		                </div>
            		</div> 		
             	</c:forEach>
             </div>
       	</c:forEach>
    </div>

    <!--底部-->
    <footer class="page-footer shopping-car_empty">
        <div class="shopping-car canteen-icon canteen-shopping-car-icon">
            <span class="shopping-car-totalprice canteen-icon canteen-rmb-icon">购物车是空的</span>
            <i class="shopping-car-totalcount"></i>
        </div>
        <a href="weixin/kanteen/order/${distribution.id }" id="submit-order" class="settlement">选好了</a>
    </footer>


    <!--购物车展示模块，配合遮罩层-->
    <section class="shopping-car-show">
        <div class="shopping-car-show_title">
            <span class="shopping-car-show_title_text">购物车</span>
            <span class="shopping-car-show_title_empty canteen-icon canteen-empty-icon">清空</span>
        </div>
        <div class="shopping-car-show_list_wrap">
        </div>

    </section>

    <!--信息展示模块，配合遮罩层-->
    <div class="canteen-information-alert-component">
        <section class="canteen-information-alert">
        <div class="canteen-information-alert_scroll">
            <div class="canteen-information-alert_banner">${merchant.name }</div>
            <div class="canteen-information-alert_basic">
                <p class="canteen-information-alert_bookingtime">预订时间：<fmt:formatDate value="${distribution.startTime }" pattern="yyyy-MM-dd HH:mm"/>~<fmt:formatDate value="${distribution.endTime }" pattern="yyyy-MM-dd HH:mm"/> </p>
                <c:forEach items="${deliveries }" var="delivery" varStatus="i">
	                <p class="canteen-information-alert_collectionplace">领取地点${i.index + 1 }：${delivery.locationName }</p>
	                <p class="canteen-information-alert_collectiontime">领取时间${i.index + 1 }：<fmt:formatDate value="${delivery.startTime }" pattern="yyyy-MM-dd HH:mm"/>~<fmt:formatDate value="${delivery.endTime }" pattern="yyyy-MM-dd HH:mm"/></p>
                </c:forEach>
            </div>
            <div class="canteen-information-alert_announcement">
                <span class="canteen-information-alert_announcement-text">商家公告</span>
                <c:forEach items="${merchant.announces }" var="announce">
	                <p class="canteen-information-alert_announcement_list">${announce }</p>
                </c:forEach>
            </div>
        </div>
    </section>
    <i class="canteen-information-alert_close canteen-icon canteen-close-icon"></i>
    </div>
    


    <!--遮罩-->
    <div class="canteen-shadow"></div>
    
    <script id="options-tmpl" type="text/jquery-tmpl">
		<div class="options-panel">
			<h3>\${wares.waresName}</h3>
			<div class="option-groups-container">
				{{each(i, group) groups}}
					<div class="options-group 
						{{if group.multiple == 1}}options-group-multiple {{/if}} 
						{{if group.required == 1}}options-group-required {{/if}}
						"
						data-id="\${group.id}">
						<h4>\${group.title}</h4>
						<p>
							{{each(j, option) group.options}}
								<span 
								{{if group.required == 1 && j == 0}}
									class="active"
								{{/if}}
								data-id="\${option.id}">\${option.name}</span>
							{{/each}}
						</p>
					</div>
				{{/each}}
			</div>
			<div class="options-footer">
				<span class="options-price"><span class="option-wares-base-price">\${wares.basePriceFloat}</span>元/\${wares.priceUnit}</span>
			</div>
		</div>
	</script>
    
    <script type="text/javascript">
    	$(function(){
    		seajs.use(['ajax'], function(Ajax){
    			var trolleyFlag = false;
    			function initTrolley(ca){
    				if(!trolleyFlag){
		    			trolletFlag = true;
		    			var validWareses = [];
		    			try{
		    				validWareses = $.parseJSON('${validWares}');
		    			}catch(e){}
		    			for(var i in validWareses){
		    				var validWares = validWareses[i];
		    				var distributionWaresId = validWares.distributionWaresId;
		    				if(validWares.wareOptionIds && validWares.wareOptionIds.length > 0){
		    					var totalPrice = validWares.count * validWares.basePrice;
		    					
		    					Kanteen.ca.shoppingCar(totalPrice / 100, "add");
		    					var desc = validWares.optionNames.join();
	    						Kanteen.ca.addOptionWares(distributionWaresId, validWares.count, validWares.basePrice / 100, desc, validWares.wareOptionIds, validWares.id);
		    				}else{
			    				ca.triggerAddTrolley(distributionWaresId, validWares.count, validWares.id);
		    				}
		    			}
    				}
	    		}
    			initTrolley(Kanteen.ca);
    			
	    		Kanteen.bindChange(function(cartData, updateTwIdFn){
	    			Ajax.postJson('weixin/kanteen/commit_trolley', 
	    					{
	    					distributionId: '${distribution.id}', 
	    					trolleyData: cartData
	    				}, function(data){
	    				if(data.status === 'interrupted'){
	    					console.log('提交了新的更新购物车请求，当前请求被系统中断');
	    				}else if(data.status != 'suc'){
	    					console.error('系统错误');
	    				}else{
	    					updateTwIdFn(data.tempTrolleyWaresData);
	    				}
	    			});
	    		}).afterInit(initTrolley);
	    		$(document).on('touchend', '.options-group span', function(e){
	    			var $option = $(e.target);
	    			var isActive = $option.is('.active'),
	    				isMulti = $option.closest('.options-group').is('.options-group-multiple'),
	    				isRequired = $option.closest('.options-group').is('.options-group-required');
	    			if(isActive && isRequired && $option.siblings().filter('span.active').length == 0){
    					return false;
	    			}else{
		    			if(isMulti){
		    				$option.toggleClass('active', !isActive);
		    			}else{
		    				var $active = $option.closest('p').find('span.active'); 
		    				$option.addClass('active');
		    				$active.removeClass('active');
		    			}
	    			}
	    			return false;
	    		});
    			var optionGroupJson = $.parseJSON('${menu.optionGroupJson}');
	    		$(document).on('click', 'a.kanteen-optionsgroup', function(e){
	    			var waresId = $(e.target).closest('[data-wares-id]').attr('data-wares-id'),
	    				distributionWaresId = $(e.target).closest('[data-prouid]').attr('data-prouid');
	    			if(waresId){
	    				var data = optionGroupJson[optionGroupJson.waresKeyPrefix + waresId];
	    				if(data){
	    					data.wares.basePriceFloat = parseFloat(data.wares.basePrice / 100).toFixed(2);
			    			var $content = $('#options-tmpl').tmpl(data);
			    			
			    			Tips.confirm({
			    				content	: $content.prop('outerHTML'),
			    				width	: 3/4,
			    				after	: function(b){
			    					if(b){
			    						var $range = $(Tips.Box);
			    						var optionIds = [];
			    						var desc = [];
			    						$('.options-group span.active', $range).each(function(){
			    							var optionId = $(this).attr('data-id');
			    							optionIds.push(optionId);
			    							desc.push($(this).text());
			    						});
			    						var price = parseFloat($('.option-wares-base-price', $range).text());
			    						Kanteen.ca.shoppingCar(price, "add");
			    						Kanteen.ca.addOptionWares(distributionWaresId, 1, price, desc.join(), optionIds);
			    					}
			    				}
			    			});
	    				}
	    			}
	    		});
	    		
    		});
    	});
    </script>
</body>

</html>