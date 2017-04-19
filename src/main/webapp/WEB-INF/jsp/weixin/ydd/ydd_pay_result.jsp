<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>下单</title>
    	<jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
	</head>
	<body>
		<c:if test="${payResult == 'suc' }">
			<div class="pay-suc">
				支付成功
			</div>
		</c:if>
		<c:if test="${payResult == 'fail' }">
			<div class="pay-fail">
				支付失败
			</div>
		</c:if>
		<div>
			
		</div>
		<script type="text/javascript">
			$(function(){
				
			});
		</script>
	</body>
</html>