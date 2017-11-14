<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<c:forEach items="${orderList }" var="order">
	<c:set var="pOrder" value="${order.plainOrder }" />
    <section class="canteen-order-list" data-id="${pOrder.id }">
        <p class="canteen-order-list_header" href="weixin/kanteen">
            <a class="canteen-order-list_logo"></a>
            <span class="canteen-order-list_logoname">${pOrder.merchantName }</span>
            <span class="canteen-order-list_status order-status-${pOrder.status }">
            	${pOrder.status == 'default' && pOrder.payExpiredTime != null && pOrder.payExpiredTime.time < now.time ? '支付超时': orderStatusMap[pOrder.status]  }
            </span>
        </p>
        <div class="canteen-order-list_lists">
        	<c:forEach items="${order.sectionList}" var="section">
                <div class="canteen-order-list_list">
                    <img class="canteen-order-list_list_image" src="${section.thumbUri }">
                    <span class="canteen-order-list_list_name">
                    	<span class="kanteen-section-wares-name">${section.waresName }</span>
                    	<span class="kanteen-scrtion-option-desc">${section.optionsDesc }</span>
                    </span>
                    <div class="canteen-order-list_list_rightbox">
                        <span class="canteen-order-list_list_price canteen-icon canteen-rmb-icon"><fmt:formatNumber value="${section.totalPrice /100 }" pattern="0.00" /> </span>
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
                <span class="canteen-order-list_list_label">创建时间</span>
                <span class="canteen-order-list_lsit_stime"><fmt:formatDate value="${pOrder.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></span>
            </p>
            <p class="canteen-order-list_list_information">
                <span class="canteen-order-list_list_label">领取人</span>
                <span class="canteen-order-list_lsit_name">${pOrder.receiverName }</span>
            </p>
            <p class="canteen-order-list_list_information">
                <span class="canteen-order-list_list_label">领取时间</span>
                <span class="canteen-order-list_list_rtime"><fmt:formatDate value="${deliveryMap[pOrder.deliveryId].startTime }" pattern="yyyy-MM-dd HH:mm:ss" /> </span>
            </p>
            <p class="canteen-order-list_list_information">
                <span class="canteen-order-list_list_label">领取地点</span>
                <span class="canteen-order-list_list_rtime">${deliveryMap[pOrder.deliveryId].locationName }</span>
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
                <c:if test="${order.canDelete }">
	                <a class="canteen-order-delete" href="javascript:">删除订单</a>
                </c:if>
                <c:if test="${order.canCancel }">
	                <a class="canteen-order-cancel">取消订单</a>
                </c:if>
                <c:if test="${pOrder.status == 'default' && pOrder.payExpiredTime != null && pOrder.payExpiredTime.time > now.time }">
	                <a class="canteen-order-pay" href="javascript:">继续支付</a>
                </c:if>
            </div>
        </div>
    </section>
</c:forEach>
