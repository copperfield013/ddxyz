<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div style="display: none;">
	<c:forEach items="${orderDrinkItemMap[plainOrder] }" step="2" varStatus="i">
		<div class="PrintArea print-wrapper">
			<c:set var="index" value="${i.index }" />
			<div class="print-content tttt">
				<div class="print-title-area">
					<div class="print-title">
						点点新意
					(<span class="print-divide">
						<fmt:formatNumber value="${index/2 + 1 }" pattern="0"/>
						/
						<fmt:formatNumber type="number" value="${fn:length(orderDrinkItemMap[plainOrder])/2 }" maxFractionDigits="0" />
					</span>)
					</div>
				</div>
				<div class="print-desc-area">
					<div class="print-desc">
						<span class="print-desc-title">订单编号:</span>
						<span class="print-desc-content print-order-code">${plainOrder.orderCode }</span>
					</div>
					<div class="print-desc float-right">
						<span class="print-desc-title">合计:</span>
						<span class="print-desc-content print-order-price"><fmt:formatNumber value="${plainOrder.totalPrice/100 }" pattern="0.00"></fmt:formatNumber>元</span>
					</div>
				</div>
				<div class="print-part-area">
					<c:forEach items="${orderDrinkItemMap[plainOrder] }" var="orderDrinkItem" begin="${index }" end="${index + 1 }">
						<div class="print-part-row">
								<span class="print-content-title">${orderDrinkItem.drinkName }</span>
								<span id="view-name">${cupSizeMap[orderDrinkItem.cupSize] }·${orderDrinkItem.teaAdditionName }</span>
								<span id="heat">${heatMap[orderDrinkItem.heat] }</span>
								<span id="sweetness">${sweetnessMap[orderDrinkItem.sweetness] }</span>
							</div>
							<div class="print-addition-row">
								<span class="print-content-title">加料：</span>
								<span id="addition-area">
									<c:forEach items="${addtionMap[orderDrinkItem] }" var="addition">
										<label>${addition.additionTypeName }</label>
									</c:forEach>
								</span>
							</div>
					</c:forEach>
				</div>
				<div class="print-foot-area">
					<div class="print-addr-area">
						<span>配送地址：</span>
						<span class="print-addr">${plainOrder.locationName }</span>
					</div>
					<div>
						联系号码：<span class="print-contact">${fn:substring(plainOrder.receiverContact, 0, 3)}****${fn:substring(plainOrder.receiverContact, 7, 11) }</span>
						<span class="print-greet">欢迎下次光临</span>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>
</div>