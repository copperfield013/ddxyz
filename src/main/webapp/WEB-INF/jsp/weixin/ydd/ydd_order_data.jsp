<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<c:forEach items="${orderDrinkList }" var="orderDrink">
			    	<div class="order">
			    		<p class="number">订单号：${orderDrink.orderCode }
			    			<c:choose>
			    				<c:when test="${orderDrink.canceledStatus == 'canceled' }">
			    					<i>已取消</i>
			    				</c:when>
			    				<c:when test="${orderDrink.canceledStatus == 'closed' }">
			    					<i>已关闭</i>
			    				</c:when>
			    				<c:when test="${orderDrink.canceledStatus == 'refunded' }">
			    					<i>已退款</i>
			    				</c:when>
				    			<c:otherwise>
					    			<c:choose>
					    				<c:when test="${orderDrink.orderStatus == 0}">
					    					<i class="todo">待支付</i>
					    				</c:when>
					    				<c:when test="${orderDrink.orderStatus == 1}">
					    					<i class="">已支付</i>
					    				</c:when>
					    				<c:when test="${orderDrink.orderStatus == 2}">
					    					<i class="">已完成</i>
					    				</c:when>
					    				<c:when test="${orderDrink.orderStatus == 3}">
					    					<i class="">已评价</i>
					    				</c:when>
					    			</c:choose>
				    			</c:otherwise>
			    			</c:choose>
			    		</p>
			    		<c:forEach items="${orderDrink.orderDrinkItems }" var="item">
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
			   				<span class="time"><fmt:formatDate value="${orderDrink.orderTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
			   				<span class="count">总计<b>${orderDrink.cupCount }</b>杯</span>
			   				<span class="money">总价：<b>￥${orderDrink.totalPrice/100 }</b></span>
			   			</p>
			   			<p class="operate" data-oid="${orderDrink.id }">
			   				<c:if test="${orderDrink.canceledStatus == null }">
			   					<c:choose>
				    				<c:when test="${orderDrink.orderStatus == 0}">
				    					<span class="operate-button pay" data-opr="pay">继续支付</span>
				    				</c:when>
				    				<c:when test="${orderDrink.orderStatus == 1}">
				    					<span class="operate-button" data-opr="confirm">确认收货</span>
				    				</c:when>
				    				<c:when test="${orderDrink.orderStatus == 2}">
				    					<span class="operate-button" data-opr="appraise">评价</span>
				    				</c:when>
				    				<c:when test="${orderDrink.orderStatus == 3}">
				    					
				    				</c:when>
				    			</c:choose>
			   				</c:if>
			   			</p>
			    	</div>
			    </c:forEach>
