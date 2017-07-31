<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<link type="text/css" rel="stylesheet" href="${basePath}media/admin/production/PrintArea_fac.css">
<div>
	<div class="print-page">
		<c:forEach items="${list }" var="productItem">
			<div class="PrintArea print-wrapper">
				<div class="print-content tttt">
					<div class="print-title-area">
						<div class="print-title">
							点点新意
						</div>
					</div>
					<div class="print-desc-area">
						<div class="print-desc">
							<span class="print-desc-title">订单编号:</span>
							<span class="print-desc-content print-order-code">${productItem.orderCode }</span>
							<span class="print-desc-content print-milk-divide"></span>
						</div>
						<div class="print-desc float-right">
							<span class="print-desc-title">时间档:</span>
							<span class="print-desc-content order-time"><fmt:formatDate value="${productItem.timePoint }" pattern="HH"/>点档</span>
						</div>
					</div>
					<div class="print-main-area">
						<div class="print-part-area">
							<div class="print-part-row">
								<span id="view-name">${productItem.drinkName }</span>
								<span id="cup-size">${cupSizeMap[productItem.cupSize] }</span>
								<span id="tea-addition">${productItem.teaAdditionName }</span>
								<span id="heat">${heatMap[productItem.heat] }</span>
								<span id="sweetness">${sweetnessMap[productItem.sweetness] }</span>
							</div>
							<div class="print-addition-row">
								<span class="print-content-title">加料：</span>
								<span id="addition-area">
									<c:forEach items="${productItem.additions }" var="addition">
										<label>${addition.additionTypeName }</label>
									</c:forEach>
								</span>
							</div>
							<div class="print-amount-area">
								<span>价格：</span>
								<span class="print-order-price"><fmt:formatNumber value="${productItem.price/100 }" pattern="0.00" />元</span>
							</div>
						</div>
						<div class="print-qrcode-area">
							<img class="qrcode" src="${basePath }qrcodes/${qrCodeMap[productItem.productId] }.png?size=200" />
						</div>
					</div>
					<div class="print-foot-area">
						<div class="print-addr-area">
							<span>配送地址：</span>
							<span class="print-addr">${productItem.locationName }</span>
						</div>
						<div class="print-contact-area">
							联系号码：<span class="print-contact">${fn:substring(productItem.receiverContact, 0, 3)}****${fn:substring(productItem.receiverContact, 7, 11) }</span>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>