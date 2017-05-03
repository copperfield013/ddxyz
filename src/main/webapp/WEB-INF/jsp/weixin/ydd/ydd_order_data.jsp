<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<c:forEach items="${orderDrinkList }" var="orderItem">
	<c:set var="orderExpired" value="${currentTime >= orderItem.payExpireTime }" />
   	<div class="order">
   		<p class="number">订单号：${orderItem.orderCode }
   			<c:choose>
   				<c:when test="${orderItem.canceledStatus == 'canceled' }">
   					<i>已取消</i>
   				</c:when>
   				<c:when test="${orderItem.canceledStatus == 'closed' }">
   					<i>已关闭</i>
   				</c:when>
   				<c:when test="${orderItem.canceledStatus == 'refunded' }">
   					<i>已退款</i>
   				</c:when>
    			<c:otherwise>
	    			<c:choose>
	    				<c:when test="${orderItem.orderStatus == 0}">
	    					<i class="todo">
	    						${orderExpired ? '未支付': '待支付' }
	    					</i>
	    				</c:when>
	    				<c:when test="${orderItem.orderStatus == 1}">
	    					<i class="">已支付</i>
	    				</c:when>
	    				<c:when test="${orderItem.orderStatus == 2}">
	    					<i class="">已完成</i>
	    				</c:when>
	    				<c:when test="${orderItem.orderStatus == 3}">
	    					<i class="">已评价</i>
	    				</c:when>
	    			</c:choose>
    			</c:otherwise>
   			</c:choose>
   		</p>
   		<c:forEach items="${orderItem.orderDrinkItems }" var="item">
   			<p class="detail">
    			<img src="${basePath }media/weixin/main/image/thumb-tea1.jpg" alt="奶茶1">
            	<span class="name">${item.drinkName }<i>￥<fmt:formatNumber value="${item.price/100 }" pattern="0.00"/></i></span>
            	 <span class="option">
            	 	${item.teaAdditionName }|${sweetnessMap[item.sweetness] }|${heatMap[item.heat] }|${cupSizeMap[item.cupSize] }
            	 	</br>
            	 	<c:forEach items="${item.additions }" var="addition">
            	 		${addition.additionTypeName }&nbsp;
            	 	</c:forEach>
            	 </span>
            	<!-- <a href="javascript:;" class="btn">再次购买</a> -->
    		</p>
  			</c:forEach>
  			<p class="result">
  				<span class="timePoint">配送时间点：<fmt:formatDate value="${orderItem.timePoint }" pattern="HH" />点</span>
  				<span class="location">配送地点：${orderItem.locationName }</span>
  			</p>
  			<p class="result">
  				<span class="time">创建时间：<fmt:formatDate value="${orderItem.orderTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
  				<span class="count">总计<b>${orderItem.cupCount }</b>杯</span>
  				<span class="money">总价：<b>￥${orderItem.totalPrice/100 }</b></span>
  			</p>
  			<p class="operate" data-oid="${orderItem.id }">
  				<c:if test="${orderItem.orderStatus != 0 || orderExpired }">
  					<span class="operate-button" data-opr="buy-again">再次购买</span>
  				</c:if>
  				<c:if test="${orderItem.canceledStatus == null }">
	  				<c:choose>
	    				<c:when test="${orderItem.orderStatus == 0}">
	    					<c:if test="${!orderExpired }">
	    						<i class="operate-tip">
	    							请在
	    							<fmt:formatDate value="${orderItem.payExpireTime }" pattern="HH:mm:ss"/>
	    							前支付
	    						</i>
	    						<span class="operate-button light-btn" data-opr="pay">继续支付</span>
	    					</c:if>
	    				</c:when>
	    				<c:when test="${orderItem.orderStatus == 1}">
	    					<c:if test="${refundableMap[orderItem.id] == true }">
		   						<span class="operate-button" data-opr="refund">申请退款</span>
		   					</c:if>
	    					<span class="operate-button light-btn" data-opr="complete">确认收货</span>
	    				</c:when>
	    				<c:when test="${orderItem.orderStatus == 2}">
	    					<span class="operate-button" data-opr="appraise">评价</span>
	    				</c:when>
	    				<c:when test="${orderItem.orderStatus == 3}">
	    					
	    				</c:when>
	    			</c:choose>
  				</c:if>
  			</p>
   	</div>
</c:forEach>
