<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>订单列表</title>
	<jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include-kanteen.jsp"></jsp:include>
    <link rel="stylesheet" href="media/weixin/kanteen/css/kanteen-order-list.css?1">
    <script src="media/weixin/kanteen/js/kanteen-order-list.js"></script>
    <script src="${basePath }media/weixin/main/js/ix.js"></script>
</head>

<body>
    <header>
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

        <!--订单nav-->
        <div class="order-nav">
            <a class="order-week" href="javascript:">本周订单</a>
            <a class="order-total" href="javascript:">全部订单</a>
            <i class="order-nav-border"></i>
        </div>
    </header>
    <div class="canteen-order-list-wrap ix-wrapper">
    	<div class="ui-body">
	    	<div class="ix-content">
		    	<div id="order-list" class="page-list"></div>
	    	</div>
    	</div>
    	<%-- <c:forEach items="${orderList }" var="order">
    		<c:set var="pOrder" value="${order.plainOrder }" />
	        <section class="canteen-order-list">
	            <p class="canteen-order-list_header">
	                <i class="canteen-order-list_logo"></i>
	                <span class="canteen-order-list_logoname">${pOrder.merchantName }</span>
	                <span class="canteen-order-list_status order-status-${pOrder.status }">${orderStatusMap[pOrder.status] }</span>
	            </p>
	            <div class="canteen-order-list_lists">
	            	<c:forEach items="${order.sectionList}" var="section">
		                <div class="canteen-order-list_list">
		                    <img class="canteen-order-list_list_image" src="${section.thumbUri }">
		                    <span class="canteen-order-list_list_name">${section.waresName }</span>
		                    <div class="canteen-order-list_list_rightbox">
		                        <span class="canteen-order-list_list_price canteen-icon canteen-rmb-icon">${section.totalPrice }</span>
		                        <span class="canteen-order-list_list_count canteen-icon canteen-close-icon">${section.count }</span>
		                    </div>
		                </div>
	            	</c:forEach>
	            </div>
	            <p class="canteen-order-list_list_totalprice">
	                <span class="canteen-order-list_list_totalprice_text">合计：</span>
	                <span class="canteen-order-list_list_totalprice_number canteen-icon canteen-rmb-icon"><fmt:formatNumber value="${pOrder.totalPrice /100 }" pattern="0.00" /></span>
	            </p>
	            <div class="canteen-order-list_list_information_box">
	                <p class="canteen-order-list_list_information">
	                    <span class="canteen-order-list_list_label">领取人</span>
	                    <span class="canteen-order-list_lsit_name">${pOrder.receiverName }</span>
	                </p>
	                <p class="canteen-order-list_list_information">
	                    <span class="canteen-order-list_list_label">领取时间</span>
	                    <span class="canteen-order-list_list_rtime"><fmt:formatDate value="${deliveryMap[pOrder.deliveryId].startTime }" pattern="yyyy-MM-dd HH:mm:ss" /> </span>
	                </p>
	                <p class="canteen-order-list_list_information">
	                    <span class="canteen-order-list_list_label">部门</span>
	                    <span class="canteen-order-list_lsit_department">${pOrder.receiverDepart }</span>
	                </p>
	                <p class="canteen-order-list_list_information">
	                    <span class="canteen-order-list_list_label">备注</span>
	                    <span class="canteen-order-list_lsit_mark">${pOrder.remark }</span>
	                </p>
	                <p class="canteen-order-list_list_information">
	                    <span class="canteen-order-list_list_label">联系号码</span>
	                    <span class="canteen-order-list_lsit_tel">${pOrder.receiverContact }</span>
	                </p>
	                <p class="canteen-order-list_list_information">
	                    <span class="canteen-order-list_list_label">创建时间</span>
	                    <span class="canteen-order-list_lsit_stime"><fmt:formatDate value="${pOrder.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></span>
	                </p>
	                <p class="canteen-order-list_list_information">
	                    <span class="canteen-order-list_list_label">订单编号</span>
	                    <span class="canteen-order-list_lsit_number">${pOrder.orderCode }</span>
	                </p>
	                <p class="canteen-order-list_list_information">
	                    <span class="canteen-order-list_list_label">截止时间</span>
	                    <span class="canteen-order-list_lsit_etime"><fmt:formatDate value="${deliveryMap[pOrder.deliveryId].endTime }" pattern="yyyy-MM-dd HH:mm:ss" /> </span>
	                </p>
	            </div>
	            <div class="canteen-order-list_operation">
	                <div class="canteen-order-list_more">
	                	查看更多
	                    <i class="canteen-order-list_more_icon canteen-icon canteen-toright-icon"></i>
	                </div>
	                <div class="canteen-order-list_button">
	                    <!--暂时不需要-->
	                    <!--<a class="canteen-order-alter" href="javascript:">修改订单</a>-->
	                    <a class="canteen-order-delete" href="javascript:">删除订单</a>
	                </div>
	            </div>
	        </section>
    	</c:forEach> --%>
    <!-- 
        <section class="canteen-order-list">
            <p class="canteen-order-list_header">
                <i class="canteen-order-list_logo"></i>
                <span class="canteen-order-list_logoname">林家食堂</span>
                <span class="canteen-order-list_status">待领取</span>
            </p>
            <div class="canteen-order-list_lists">
                <div class="canteen-order-list_list">
                    <img class="canteen-order-list_list_image" src="media/weixin/kanteen/static/image/meal_example.png">
                    <span class="canteen-order-list_list_name">卤鸡腿</span>
                    <div class="canteen-order-list_list_rightbox">
                        <span class="canteen-order-list_list_price canteen-icon canteen-rmb-icon">20.00</span>
                        <span class="canteen-order-list_list_count canteen-icon canteen-close-icon">1</span>
                    </div>
                </div>
                <div class="canteen-order-list_list">
                    <img class="canteen-order-list_list_image" src="media/weixin/kanteen/static/image/meal_example.png">
                    <span class="canteen-order-list_list_name">卤鸡腿</span>
                    <div class="canteen-order-list_list_rightbox">
                        <span class="canteen-order-list_list_price canteen-icon canteen-rmb-icon">20.00</span>
                        <span class="canteen-order-list_list_count canteen-icon canteen-close-icon">1</span>
                    </div>
                </div>
                <div class="canteen-order-list_list">
                    <img class="canteen-order-list_list_image" src="media/weixin/kanteen/static/image/meal_example.png">
                    <span class="canteen-order-list_list_name">卤鸡腿</span>
                    <div class="canteen-order-list_list_rightbox">
                        <span class="canteen-order-list_list_price canteen-icon canteen-rmb-icon">20.00</span>
                        <span class="canteen-order-list_list_count canteen-icon canteen-close-icon">1</span>
                    </div>
                </div>
            </div>
            <p class="canteen-order-list_list_totalprice">
                <span class="canteen-order-list_list_totalprice_text">合计：</span>
                <span class="canteen-order-list_list_totalprice_number canteen-icon canteen-rmb-icon">40.00</span>
            </p>
            <div class="canteen-order-list_list_information_box">
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">领取人</span>
                    <span class="canteen-order-list_lsit_name">王杰</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">领取时间</span>
                    <span class="canteen-order-list_list_rtime">2017-09-01 16:00:00</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">部门</span>
                    <span class="canteen-order-list_lsit_department">文化部</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">备注</span>
                    <span class="canteen-order-list_lsit_mark">切好</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">联系号码</span>
                    <span class="canteen-order-list_lsit_tel">13888888888</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">创建时间</span>
                    <span class="canteen-order-list_lsit_stime">2017-08-30 18:00:00</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">订单编号</span>
                    <span class="canteen-order-list_lsit_number">20170930012545896545</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">截止时间</span>
                    <span class="canteen-order-list_lsit_etime">2020-09-19 18:00:00</span>
                </p>
            </div>
            <div class="canteen-order-list_operation">
                <div class="canteen-order-list_more">
                    查看更多
                    <i class="canteen-order-list_more_icon canteen-icon canteen-toright-icon"></i>
                </div>
                <div class="canteen-order-list_button">
                    暂时不需要
                    <a class="canteen-order-alter" href="javascript:">修改订单</a>
                    <a class="canteen-order-delete" href="javascript:">删除订单</a>
                </div>
            </div>
        </section>
        <section class="canteen-order-list">
            <p class="canteen-order-list_header">
                <i class="canteen-order-list_logo"></i>
                <span class="canteen-order-list_logoname">林家食堂</span>
                <span class="canteen-order-list_status">待领取</span>
            </p>
            <div class="canteen-order-list_lists">
                <div class="canteen-order-list_list">
                    <img class="canteen-order-list_list_image" src="media/weixin/kanteen/static/image/meal_example.png">
                    <span class="canteen-order-list_list_name">卤鸡腿</span>
                    <div class="canteen-order-list_list_rightbox">
                        <span class="canteen-order-list_list_price canteen-icon canteen-rmb-icon">20.00</span>
                        <span class="canteen-order-list_list_count canteen-icon canteen-close-icon">1</span>
                    </div>
                </div>
                <div class="canteen-order-list_list">
                    <img class="canteen-order-list_list_image" src="media/weixin/kanteen/static/image/meal_example.png">
                    <span class="canteen-order-list_list_name">卤鸡腿</span>
                    <div class="canteen-order-list_list_rightbox">
                        <span class="canteen-order-list_list_price canteen-icon canteen-rmb-icon">20.00</span>
                        <span class="canteen-order-list_list_count canteen-icon canteen-close-icon">1</span>
                    </div>
                </div>
                <div class="canteen-order-list_list">
                    <img class="canteen-order-list_list_image" src="media/weixin/kanteen/static/image/meal_example.png">
                    <span class="canteen-order-list_list_name">卤鸡腿</span>
                    <div class="canteen-order-list_list_rightbox">
                        <span class="canteen-order-list_list_price canteen-icon canteen-rmb-icon">20.00</span>
                        <span class="canteen-order-list_list_count canteen-icon canteen-close-icon">1</span>
                    </div>
                </div>
            </div>
            <p class="canteen-order-list_list_totalprice">
                <span class="canteen-order-list_list_totalprice_text">合计：</span>
                <span class="canteen-order-list_list_totalprice_number canteen-icon canteen-rmb-icon">40.00</span>
            </p>
            <div class="canteen-order-list_list_information_box">
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">领取人</span>
                    <span class="canteen-order-list_lsit_name">王杰</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">领取时间</span>
                    <span class="canteen-order-list_list_rtime">2017-09-01 16:00:00</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">部门</span>
                    <span class="canteen-order-list_lsit_department">文化部</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">备注</span>
                    <span class="canteen-order-list_lsit_mark">切好</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">联系号码</span>
                    <span class="canteen-order-list_lsit_tel">13888888888</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">创建时间</span>
                    <span class="canteen-order-list_lsit_stime">2017-08-30 18:00:00</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">订单编号</span>
                    <span class="canteen-order-list_lsit_number">20170930012545896545</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">截止时间</span>
                    <span class="canteen-order-list_lsit_etime">2020-09-19 18:00:00</span>
                </p>
            </div>
            <div class="canteen-order-list_operation">
                <div class="canteen-order-list_more">
                    查看更多
                    <i class="canteen-order-list_more_icon canteen-icon canteen-toright-icon"></i>
                </div>
                <div class="canteen-order-list_button">
                    暂时不需要
                    <a class="canteen-order-alter" href="javascript:">修改订单</a>
                    <a class="canteen-order-delete" href="javascript:">删除订单</a>
                </div>
            </div>
        </section> -->
        
    </div>


    <footer class="canteen-order-list-footer">
        <a class="canteen-footer-goto-home" href="canteen_home.html">去下单</a>
    </footer>
    <script type="text/javascript">
    	$(function(){
    		seajs.use(['utils'], function(Utils){
    			ix.list('order-list', {
    				url	: 'weixin/kanteen/order_list_data',
    				data: {
    					// 自定义参数
    				},
    				pageNo : 'pageNo', // 当前页码的key
    				callback : function(boxs){
    					 // 回调 this是当前容器，boxs是加载的数据项
    				}
    			});
    		});
    	});
    </script>
</body>

</html>