<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<base href="${basePath }" />
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="ie=edge">

<link rel="stylesheet" href="media/weixin/kanteen/css/reset.css?v=1.1.4">


<script src="${basePath }media/weixin/main/js/jquery-3.1.1.min.js"></script>
<script src="${basePath }media/common/plugins/jquery.tmpl.js"></script>
<script src="media/weixin/kanteen/js/mobile-adaptation.js"></script>
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