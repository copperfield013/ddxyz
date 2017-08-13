<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
	<link type="text/css" rel="stylesheet" href="${basePath }media/admin/canteen/css/order-tag.css" />
</head>
<c:set var="order" value="${cOrder.pOrder }" />
<body>
	<div id="print-area">
		<table>
			<colgroup>
				<col width="20%" />
				<col width="20%" />
				<col width="20%" />
				<col width="20%" />
				<col width="20%" />
			</colgroup>
			<tbody>
				<tr>
					<td colspan="5">订单明细</td>
				</tr>
				<tr>
					<td>订单号</td>
					<td colspan="4">${order.orderCode }</td>
				</tr>
				<tr>
					<td colspan="5">
						<c:forEach items="${orderItems }" var="orderItem">
						 	<div>${orderItem.waresName }×${orderItem.count }</div>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td>领取人</td>
					<td colspan="2">${order.receiverName }</td>
					<td>部门</td>
					<td>${cOrder.depart }</td>
				</tr>
				<tr>
					<td>联系号码</td>
					<td colspan="2">${order.receiverContact }</td>
					<td>总价</td>
					<td><fmt:formatNumber value="${order.totalPrice / 100}" pattern="0.00" /></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>