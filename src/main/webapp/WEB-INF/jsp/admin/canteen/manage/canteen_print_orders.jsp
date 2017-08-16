<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="print-page">
	<table>
		<colgroup>
			<col width="5%">
			<col width="10%">
			<col width="10%">
			<col width="20%">
			<col width="30%">
			<col width="10%">
			<col width="15%">
		</colgroup>
		<thead>
			<tr>
				<th colspan="7">
					<fmt:formatDate value="${Monday }" pattern="yyyy年MM月dd日"/>
					~
					<fmt:formatDate value="${Sunday }" pattern="yyyy年MM月dd日"/>
					订购信息汇总
				</th>
			</tr>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>部门</th>
				<th>联系方式</th>
				<th>订购信息</th>
				<th>总价</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${items }" var="item" varStatus="i">
				<tr>
					<td>${i.index + 1 }</td>
					<td>${item.receiverName }</td>
					<td>${item.depart }</td>
					<td>${item.receiverContact }</td>
					<td>
						<c:forEach items="${item.waresItemsList }" var="wares">
							<span>${wares.waresName }×${wares.count }·${wares.priceUnit }</span>
						</c:forEach>
					</td>
					<td>
						<fmt:formatNumber value="${item.totalPrice / 100}" pattern="0.00" />元
					</td>
					<td>
						${item.comment }
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>