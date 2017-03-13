<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<base href="${basePath }" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
<link href="${basePath }media/weixin/plugins/jquery.mobile-1.4.5/jquery.mobile-1.4.5.css" rel="stylesheet" />

<link href="${basePath }media/weixin/main/css/weixin-main.css" rel="stylesheet" />

<script src="${basePath }media/jquery-1.11.3.js"></script>
<script src="${basePath }media/jquery-1.11.3.js"></script>
<script src="${basePath }media/weixin/plugins/jquery.mobile-1.4.5/jquery.mobile-1.4.5.js"></script>
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
		    	'$CPF'		: 'COMMON/cpf/cpf-core.js',
				'utils'		: 'COMMON/cpf/cpf-utils.js',
				'console'	: 'COMMON/cpf/cpf-console.js',
				'wxpay'		: 'COMMON/cpf/cpf-wxpay.js',
				'wxconfig'	: 'MAIN/weixin-sdkconfig.js'
				//..其他模块
		  	}
		});
		define('$paramMap', function(require, exports){
			try{
				$.extend(exports, $.parseJSON('${$paramMapJson}'));
			}catch(e){}
		});
		seajs.use('MAIN/weixin-main.js');
	});
	
</script>