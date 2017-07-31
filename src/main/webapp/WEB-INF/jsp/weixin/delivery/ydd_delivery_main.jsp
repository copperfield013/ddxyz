<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <title>订单列表</title>
    <jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
</head>
<body class="ui-page">
	<div id="main">
		<div style="height: 100px;"></div>
		<a id="scan" href="javascript:;" >扫描</a>
	</div>
	<script type="text/javascript">
		$(function(){
			seajs.use(['wxconfig'], function(Wx){
				$('#scan').click(function(){
					var WxApi = Wx.LocalApi;
					WxApi.scanQrCode(function(code){
						$.post('delivery/ajaxResolveQrCode',{
							code	: code
						}, function(res){
							if(typeof res === 'string'){
								res = $.parseJSON(res);
							}
							alert(res.origin);
						});
					});
				});
			});
		});
	
	
	</script>
</body>
</html>