<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<base href="${basePath }" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">

<!--Basic Styles-->
<link href="media/admin/plugins/beyond/css/bootstrap.min.css" rel="stylesheet" />
<link href="media/admin/plugins/beyond/css/font-awesome.min.css" rel="stylesheet" />

<!--Beyond styles-->
<link id="beyond-link" href="media/admin/plugins/beyond/css/beyond.min.css" rel="stylesheet" type="text/css" />
<!--  -->
<link href="media/admin/cpf/css/cpf-main.css" rel="stylesheet" type="text/css" />

<!-- jQuery -->
<script src="${basePath }media/jquery-1.11.3.js"></script>
<!-- SeaJS -->
<script src="${basePath }media/sea-debug.js"></script>
<!-- 页面加载时把各个JS模块加载到容器当中 -->
<script type="text/javascript">
	$(function(){
		seajs.config({
			base	: '${basePath}media/admin/',
			paths	: {
				COMMON	: '${basePath}media/common/',
				MAIN	: '${basePath}media/admin/main/js/'
			},
		  	alias	: {
		    	'$CPF'		: 'COMMON/cpf/cpf-core.js',
				'utils'		: 'COMMON/cpf/cpf-utils.js',
				'console'	: 'COMMON/cpf/cpf-console.js',
				'print'		: 'COMMON/cpf/cpf-print.js',
				'ajax'		: 'COMMON/cpf/cpf-ajax.js',
				'page'		: 'COMMON/cpf/cpf-page.js',
				'dialog'	: 'COMMON/cpf/cpf-dialog.js',
				'timer'		: 'COMMON/cpf/cpf-timer.js',
				'upload'	: 'COMMON/cpf/cpf-upload.js'
				//..其他模块
		  	}
		});
		seajs.use('MAIN/admin-main.js');
	});
	
</script>