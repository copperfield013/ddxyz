<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<c:forEach items="${list }" var="productItem">
	<li class="product-item">
		<div class="order-info">
			<span class="order-code">订单编号:${productItem.orderCode }</span>
			<span class="order-status">[已发货]</span>
			<div class="order-time">
				<span>下单时间：<fmt:formatDate value="${productItem.orderCreateTime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
				<span>付款时间：2017-04-25 11:01:10</span>
				<span>预定时间：<fmt:formatDate value="${productItem.timePoint }" pattern="HH"></fmt:formatDate>点档</span>
			</div>
		</div>
		<div class="product-info">
			<ul>
				<li>
					<%-- <span>${productItem.drinkName }&nbsp;&nbsp;搭配：${productItem.teaAdditionName }&nbsp;规格：${cupSizeMap[productItem.cupSize] }&nbsp;甜度：${sweetness[productItem.sweetness] }&nbsp;冰度：${heatMap[productItem.heat] }&nbsp;加料：珍珠</span> --%>
					<span>${productItem.drinkName }</span>
					<span>搭配：${productItem.teaAdditionName }</span>
					<span>规格：${cupSizeMap[productItem.cupSize] }	甜度：${sweetness[productItem.sweetness] }	冰度：${heatMap[productItem.heat] }</span>
					<span>加料：
						<c:forEach items="${productItem.additions }" var="addition">
							${addition.additionTypeName }
						</c:forEach>
					</span>
				</li>
			</ul>
		</div>
		<div class="receiver-info">
			<span class="receiver-addr">配送地址：${productItem.locationName }</span>
			<span class="receiver-phone">联系电话：${productItem.receiverContact }</span>
			<a href="#" >打印</a>
		</div>
	</li>
</c:forEach>