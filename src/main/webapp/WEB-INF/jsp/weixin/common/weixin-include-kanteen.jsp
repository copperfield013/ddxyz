<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<base href="${basePath }" />
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1, user-scalable=0, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<!-- 引入IconFont字体 -->
<link rel="stylesheet" type="text/css" href="${basePath }media/common/plugins/iconfont/iconfont.css">
<link rel="stylesheet" href="media/weixin/main/css/weixin-main.css?${GLOBAL_VERSION }">
<link rel="stylesheet" href="media/weixin/kanteen/css/reset.css?${GLOBAL_VERSION }">
<link rel="stylesheet" href="media/weixin/plugins/iTip/iTip.css?${GLOBAL_VERSION }">

<script src="${basePath }media/weixin/main/js/jquery-3.1.1.min.js"></script>
<script src="${basePath }media/common/plugins/jquery.tmpl.js"></script>
<script src="media/weixin/kanteen/js/mobile-adaptation.js"></script>
<script src="media/weixin/plugins/iTip/iTip.js?${GLOBAL_VERSION }"></script>
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
		    	'$CPF'		: 'COMMON/cpf/cpf-core.js?${GLOBAL_VERSION}',
				'utils'		: 'COMMON/cpf/cpf-utils.js?${GLOBAL_VERSION}',
				'console'	: 'COMMON/cpf/cpf-console.js?${GLOBAL_VERSION}',
				'wxpay'		: 'COMMON/cpf/cpf-wxpay.js?${GLOBAL_VERSION}',
				'ajax'		: 'COMMON/cpf/cpf-ajax.js?${GLOBAL_VERSION}',
				'page'		: 'COMMON/cpf/cpf-refer-test.js?${GLOBAL_VERSION}',
				'dialog'	: 'COMMON/cpf/cpf-refer-test.js?${GLOBAL_VERSION}',
				'wxconfig'	: 'MAIN/weixin-sdkconfig.js?${GLOBAL_VERSION}',
				'adsblock'	: 'COMMON/cpf/cpf-adsblock.js?${GLOBAL_VERSION}',
				'checkbox'	: 'COMMON/cpf/cpf-checkbox.js?${GLOVAL_VERSION}'
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