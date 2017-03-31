<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<base href="${basePath }" />
		<title></title>
		<jsp:include page="/WEB-INF/jsp/admin/common/admin-include.jsp"></jsp:include>
	</head>
	<body>
		<div>
			<input type="button" value="测试" id="test" />
		</div>
		<script type="text/javascript">
		$(function(){
			seajs.use(['ajax'], function(Ajax){
				function testJson(){
					Ajax.postJson('admin/demo/testJson', {
						a	: '1',
						b	: 2
					}, function(json){
						console.log(json);
					});
				}
				$('#test').click(testJson);
			});
		});
		</script>
	</body>
</html>