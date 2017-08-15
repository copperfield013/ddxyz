<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<c:forEach items="${orderList }" var="orderItem">
	<div class="order" data-id="${orderItem.orderId }">
		<p class="head">
			<span class="order-code">订单号：${orderItem.orderCode }</span>
			<c:if test="${orderItem.canceledStatus != null }">
				<span class="order-status">已取消</span>
			</c:if>
		</p>
		<c:forEach items="${waresList[orderItem] }" var="item">
			<p class="detail">
				<img alt="" src="${basePath }${item.thumbUri}">
				<span class="name">${item.waresName }<i>x${item.count }</i></span>
				<span class="price">￥<fmt:formatNumber value="${item.unitPrice/100 }" pattern="0.00"/></span>
			</p>
		</c:forEach>
		<p class="result">
  			<span class="timePoint">领取时间：<fmt:formatDate value="${orderItem.timePoint }" pattern="yyyy-MM-dd HH:mm:ss" /></span>
  			<span class="money">总价：<b>￥${orderItem.totalPrice/100 }</b></span>
  		</p>
  		<p class="result">
  			<span class="location">领取地点：${orderItem.locationName }</span>
  		</p>
  		<div class="order-detail-wrap">	
  			<p class="hideinfo">
  				<span class="takemen">收货人：${orderItem.receiverName }</span>
  				<span class="takeport">部门：${orderItem.depart }</span>
  				<span class="takePhone">联系号码：${orderItem.receiverContact }</span>
  				<span class="closetime">领取截止时间：<fmt:formatDate value="${orderItem.deliveryEndTime }" pattern="yyyy-MM-dd HH:mm:ss" /></span>
  				<span class="ordertime">创建时间：<fmt:formatDate value="${orderItem.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></span>
  			</p>
  		</div>	
  		<p class="operate">		
  			<i class="circle-point-icon" onclick="lookMore(this)"></i>		 
  			<c:if test="${orderItem.orderStatus < 2 && orderItem.canceledStatus == null }">
	  			<span class="operate-button">取消订单</span>
	  			<span class="operate-revise">修改订单</span>
  			</c:if> 				
  		</p>
	</div>
</c:forEach>