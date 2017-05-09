<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="dialog">
	<div id="dialog-cover"></div>
	<div id="dialog-warp">
		<div class="dialog-title">请选择要再次购买的配送</div>
		<div class="dialog-content-wrapper">
			<ul class="dialog-content">
				<c:forEach items="${deliveryMap }" var="deliveryEntry">
					<c:set var="timePoint" value="${deliveryEntry.key }" />
					<li>
						<div class="dialog-distribution-time">
							配送时间档： <i class="dialog-distribution-time">${timePoint.hour }点档</i>
						</div>
						<div class="distribution-address-warp">
							<c:forEach items="${deliveryEntry.value }" var="delivery">
								<div class="dialog-distribution-address TH-market" data-id="${delivery.id }">
									${delivery.locationName }<i> <b></b>
									</i>
								</div>
							</c:forEach>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="dialog-button">
			<a href="javascript:;" class="dialog-button-cancel">取消</a> <a
				href="javascript:;" class="dialog-button-sure">确认</a>
		</div>
	</div>
</div>
