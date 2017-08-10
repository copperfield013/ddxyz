<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<c:forEach items="${orderList }" var="orderItem">
	<div class="order">
		<p class-="number">订单号：${orderItem.orderCode }
		</p>
		<c:forEach items="${waresList[orderItem] }" var="item">
			<p class="detail">
				<img alt="" src="${basePath }${item.thumbUri}">
				<span class="name">${item.waresName }<i>x${item.count }</i></span>
				<span class="price">￥<fmt:formatNumber value="${item.unitPrice/100 }" pattern="0.00"/></span>
			</p>
		</c:forEach>
		<p class="result">
  			<span class="timePoint">领取时间：<fmt:formatDate value="${orderItem.timePoint }" pattern="HH:mm:ss" /></span>
  			<span class="money">总价：<b>￥${orderItem.totalPrice/100 }</b></span>
  		</p>
  		<p class="result">
  			<span class="location">领取地点：${orderItem.locationName }</span>
  		</p>
  		<div class="order-detail-wrap">	
  			<p class="hideinfo">
  				<span class="ordertime">创建时间：<fmt:formatDate value="${orderItem.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></span>
  				<span class="takemen">收货人：${orderItem.receiverName }</span>
  				<span class="takePhone">联系号码：${orderItem.receiverContact }</span>
  				<span class="takeport">部门：${canteenOrderMap[orderItem].depart }</span>
  			</p>
  		</div>	
  		<p class="operate">		
  			<i class="circle-point-icon"></i>		  				
  			<span class="operate-button">取消订单</span>	
  			<span class="operate-revise">修改订单</span>			  				
  		</p>
	</div>
</c:forEach>
<script>
	$('.circle-point-icon').on('click',function(){
		var  isOpen = $(this).hasClass('active');
		if( isOpen ){
			$(this).parent().prev('.order-detail-wrap').removeClass('active');
			$(this).removeClass("active");
		}else {
			$(this).parent().prev('.order-detail-wrap').addClass('active');
			$(this).addClass("active")
		}
	});
</script>