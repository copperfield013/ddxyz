<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
    
    <title>林家食堂</title>
	<jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include-kanteen.jsp"></jsp:include>
    <link rel="stylesheet" href="media/weixin/kanteen/css/kanteen-index.css">
    <script src="media/weixin/kanteen/js/kanteen-index.js"></script>
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
        	<c:forEach items="${menu.menuItemMap }" var="menuItem">
        		<c:set var="group" value="${menuItem.key }" />
	            <a data-key="${group.id }" href="javascript:" class="canteen-meal-nav_button canteen-icon canteen-hot-sell-icon active">${group.name }</a>
        	</c:forEach>
        </nav>
        <!--餐品列表部分-->
        <!--data-uid是标识，需要和对应的nav模块菜单中的 data-key 的值一样，可以换为后台已有标识字段  :String -->
        <!--data-prouid是商品标识，可替换为后台已有字段，具有唯一性与商品一一对应 :String -->
        <c:forEach items="${menu.menuItemMap }" var="menuItem">
       		<c:set var="group" value="${menuItem.key }" />
             <div data-uid="${group.id }" class="canteen-meal-list active">
             	<header class="canteen-meal-list_title canteen-icon canteen-title-bar-icon">${group.name }</header>
             	<c:forEach items="${menuItem.value }" var="wares">
            		<div data-prouid="${wares.distributionWaresId }" class="canteen-meal-list_menu" >
						<img class="canteen-meal-list_image" src="${wares.picUri }" alt="${wares.waresName }">
						<div class="canteen-meal-list_menu_detail">
							<p class="canteen-meal-list_menu_name">${wares.waresName }</p>
		                    <p class="canteen-meal-list_menu_sales">
		                    	<c:if test="${wares.maxCount != null }">
		                    		<span>余量${wares.maxCount }</span>
		                    	</c:if>
		                        <span>已售${wares.currentCount }</span>
		                    </p>
		                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
		                        <span class="">${wares.basePrice }${wares.priceUnit }</span>
		                    </p>
		                    <div class="canteen-meal-list_menu_button">
		                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
		                        <span class="canteen-meal-list_menu_count"></span>
		                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
		                    </div>
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
        <a href="canteen_order.html" class="settlement">选好了</a>
    </footer>


    <!--购物车展示模块，配合遮罩层-->
    <section class="shopping-car-show">
        <div class="shopping-car-show_title">
            <span class="shopping-car-show_title_text">购物车</span>
            <span class="shopping-car-show_title_empty canteen-icon canteen-empty-icon">清空</span>
        </div>
        <div class="shopping-car-show_list_wrap">
            <div data-orderuid="${trolley.plainTrolley.id }" class="shopping-car-show_list">
				<c:forEach items="${trolley.validWares }" var="trolleyWares">
	                <span class="shopping-car-show_list_name">${trolleyWares.waresName }</span>
	                <span class="shopping-car-show_list_price canteen-icon canteen-rmb-icon">
	                    <fmt:formatNumber value="${trolleyWares.basePrice }" />${trolleyWares.priceUnit }
	                </span>
	                <div class="shopping-car-show_list_button">
	                    <a href="javascript:" class="shopping-car-show_list_minus canteen-icon canteen-minus-icon"></a>
	                    <span class="shopping-car-show_list_count">${trolleyWares.count }</span>
	                    <a href="javascript:" class="shopping-car-show_list_add canteen-icon canteen-add-icon"></a>
	                </div>
				</c:forEach>
            </div>
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
	                <p class="canteen-information-alert_collectionplace">领取地点${i.index + 1 }：${delivery.name }</p>
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
    <script type="text/javascript">
    	$(function(){
    		seajs.use(['ajax'], function(Ajax){
	    		Kanteen.bindChange(function(cartData){
	    			Ajax.ajax('weixin/kanteen/uploadCart', 
	    					$.extend(cartData, {merchantId: 1}), function(data){
	    				if(data.status != 'suc'){
	    					console.error('系统错误');
	    				}
	    			})
	    		});
    		});
    	});
    </script>
</body>

</html>