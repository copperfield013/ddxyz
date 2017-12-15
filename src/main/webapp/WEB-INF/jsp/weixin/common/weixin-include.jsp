<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<base href="${basePath }" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">


<!-- fromXu Start -->
<meta charset="utf-8"/>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="-1">
<meta name="format-detection" content="telephone=no"><!-- 禁止iphone修改数字样式 -->
<meta name="viewport" content="minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,width=device-width,user-scalable=no">
<meta name="renderer" content="webkit">

<link rel="stylesheet" href="${basePath }media/weixin/main/css/templete.css?${GLOBAL_VERSION}">
<link rel="stylesheet" href="${basePath }media/weixin/main/css/common.css?${GLOBAL_VERSION}">

<!-- fromXu End -->

<!-- 引入IconFont字体 -->
<link rel="stylesheet" type="text/css" href="${basePath }media/common/plugins/iconfont/iconfont.css">

<script src="${basePath }media/weixin/main/js/jquery-3.1.1.min.js"></script>
<script src="${basePath }media/common/plugins/jquery.tmpl.js"></script>
<%--<script src="${basePath }media/weixin/plugins/jquery.mobile-1.4.5/jquery.mobile-1.4.5.js"></script> --%>
<!-- SeaJS -->
<script src="${basePath }media/sea-debug.js"></script>

<!-- 页面加载时把各个JS模块加载到容器当中 -->
<script type="text/javascript">
	$(function(){
		seajs.config({
			base	: '${basePath}media/weixin/',
			paths	: {
				COMMON	: '${basePath}media/common/',
				MAIN	: '${basePath}media/weixin/main/js/'
			},
		  	alias	: {
		    	'$CPF'		: 'COMMON/cpf/cpf-core.js?${RES_STAMP}',
				'utils'		: 'COMMON/cpf/cpf-utils.js?${RES_STAMP}',
				'console'	: 'COMMON/cpf/cpf-console.js?${RES_STAMP}',
				'wxpay'		: 'COMMON/cpf/cpf-wxpay.js?${RES_STAMP}',
				'ajax'		: 'COMMON/cpf/cpf-ajax.js?${RES_STAMP}',
				'page'		: 'COMMON/cpf/cpf-refer-test.js?${RES_STAMP}',
				'dialog'	: 'COMMON/cpf/cpf-refer-test.js?${RES_STAMP}',
				'wxconfig'	: 'MAIN/weixin-sdkconfig.js?${RES_STAMP}',
				'adsblock'	: 'COMMON/cpf/cpf-adsblock.js?${RES_STAMP}'
				//..其他模块
		  	}
		});
		define('$paramMap', function(require, exports){
			try{
				$.extend(exports, $.parseJSON('${$paramMapJson}'));
			}catch(e){}
		});
		seajs.use('MAIN/weixin-main.js?${RES_STAMP}');
	});
	
</script>