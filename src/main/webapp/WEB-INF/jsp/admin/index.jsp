<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>点点新意管理系统</title>
		<link rel="icon" href="favicon.ico" type="image/x-icon" />
		<link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>
		<jsp:include page="/WEB-INF/jsp/admin/common/admin-include.jsp"></jsp:include>
	    <!--Basic Scripts-->
	    <script src="media/admin/plugins/beyond/js/jquery-ui-1.10.4.custom.js"></script>
	    <!--Skin Script: Place this script in head to load scripts for skins and rtl support-->
	    <script src="media/admin/plugins/beyond/js/skins.min.js"></script>
	    <script src="media/admin/plugins/beyond/js/datetime/bootstrap-datepicker.js"></script>
	    <script src="media/admin/plugins/beyond/js/datetime/bootstrap-timepicker.js"></script>
	    <script src="media/admin/plugins/beyond/js/datetime/moment.js"></script>
	    <script src="media/admin/plugins/beyond/js/datetime/daterangepicker.js"></script>
	    <script src="media/admin/plugins/bootstrapt-treeview/dist/bootstrap-treeview.min.js"></script>
	    <script src="media/admin/plugins/beyond/js/validation/bootstrapValidator.js"></script>
	    <script src="media/admin/plugins/beyond/js/tagsinput/bootstrap-tagsinput.js"></script>
	    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=YhhQe0yOr3L0f1mHLZcjHIZyvQixl4Zq"></script>
	</head>
	<body>
		<div class="navbar">
	        <div class="navbar-inner">
	            <div class="navbar-container">
	                <div class="navbar-header pull-left">
	                    <a href="#" class="navbar-brand">
	                        <small>
	                            <img src="media/admin/main/image/ydd-logo.png" alt="" />
	                        </small>
	                    </a>
	                </div>
	                <div class="sidebar-collapse" id="sidebar-collapse">
	                    <i class="collapse-icon fa fa-bars"></i>
	                </div>
				</div>
			</div>
		</div>
	    <div class="main-container container-fluid">
	        <div class="page-container">
	            <div class="page-sidebar" id="sidebar">
	                <ul class="nav sidebar-menu">
	                	<li class="open">
	                		<a href="#">
	                			<i class="menu-icon glyphicon glyphicon-home"></i>
	                			<span class="menu-text">主页面</span>
	                		</a>
	                	</li>
						 <li>
	                        <a href="#" class="menu-dropdown">
	                            <i class="menu-icon fa fa-desktop"></i>
	                            <span class="menu-text">订单管理</span>
	                            <i class="menu-expand"></i>
	                        </a>
							<ul class="submenu">
                               	<li>
                               		<a class="tab" href="admin/order-manage/order-list" target="order-list" title="订单查看">
                               			<span class="menu-text">订单查看</span>
                               		</a>
                               	</li>
                               	<li>
                               		<a class="tab" href="admin/order-manage/order-statistics" target="orer-statistics" title="订单统计">
                               			<span class="menu-text">订单统计</span>
                               		</a>
                               	</li>
							</ul>
	                    </li>
	                    <li>
	                        <a href="admin/production/main" class="menu-dropdown tab" target="product-main" title="生产管理">
	                        	<i class="menu-icon fa fa-desktop"></i>
	                            <span class="menu-text">生产管理</span>
	                        </a>
	                    </li>
	                    <li>
	                        <a href="admin/production/product-list" class="menu-dropdown tab" target="product-list" title="生产管理">
	                        	<i class="menu-icon fa fa-desktop"></i>
	                            <span class="menu-text">生产管理1</span>
	                        </a>
	                    </li>
	                     <li>
	                        <a href="admin/delivery/delivery_list" class="menu-dropdown tab" target="delivery-list" title="配送管理">
	                        	<i class="menu-icon fa fa-desktop"></i>
	                            <span class="menu-text">配送管理</span>
	                        </a>
	                    </li>
	                    <li>
	                        <a href="#" class="menu-dropdown">
	                            <i class="menu-icon fa fa-desktop"></i>
	                            <span class="menu-text">配送配置</span>
	                            <i class="menu-expand"></i>
	                        </a>
							<ul class="submenu">
                               	<li>
                               		<a class="tab" href="admin/config/plan/plan-list" target="delivery-plan-list" title="配送计划">
                               			<span class="menu-text">配送计划</span>
                               		</a>
                               	</li>
                               	<li>
                               		<a class="tab" href="admin/config/info/list?from=index" target="delivery-info-list" title="配送列表">
                               			<span class="menu-text">配送列表</span>
                               		</a>
                               	</li>
                               	<li>
                               		<a class="tab" href="admin/config/location/list" target="delivery-location-list" title="配送地点配置">
                               			<span class="menu-text">配送地点配置</span>
                               		</a>
                               	</li>
							</ul>
	                    </li>
					</ul>
				</div>
				<div class="page-content">
					<div class="tabbable">	
						<div class="tab-warp">
							<a href="javascript:;" class="move left">◀</a>	
								<div>	
									<ul class="nav nav-tabs" id="main-tab-title-container">
										<li class="active main-tab-title">
										    <a data-toggle="tab" href="#cpf-home-tab">
												主页
										    </a>
										</li>
									</ul>
								</div>
							<a href="javascript:;" class="move right">▶</a>
						</div>				
						<div class="tab-content" id="main-tab-content-container">
							<div id="cpf-home-tab" class="tab-pane active main-tab-content">
								<jsp:include page="home.jsp"></jsp:include>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	    <script src="media/admin/plugins/beyond/js/bootstrap.js"></script>
	    <script src="media/admin/plugins/beyond/js/toastr/toastr.js"></script>
	    <script src="media/admin/plugins/beyond/js/beyond.min.js"></script>
	    
	    <script type="text/javascript">
	    	$(function(){
	    		seajs.config({
				base	: '${basePath}media/admin/',
				paths	: {
					  COMMON	: '${basePath}media/common/',
					  MAIN		: '${basePath}media/admin/main/js/'
				},
				alias	: {
					'$CPF'		: 'COMMON/cpf/cpf-core.js',
					'utils'		: 'COMMON/cpf/cpf-utils.js',
					'page'		: 'COMMON/cpf/cpf-page.js',
					'dialog'	: 'COMMON/cpf/cpf-dialog.js',
					'paging'	: 'COMMON/cpf/cpf-paging.js',
					'tree'		: 'COMMON/cpf/cpf-tree.js',
					'form'		: 'COMMON/cpf/cpf-form.js',
					'tab' 		: 'COMMON/cpf/cpf-tab.js',
					'ajax'		: 'COMMON/cpf/cpf-ajax.js',
					'css'		: 'COMMON/cpf/cpf-css.js',
					'timer'		: 'COMMON/cpf/cpf-timer.js',
					'console'	: 'COMMON/cpf/cpf-console.js',
					'controll'	: 'COMMON/cpf/cpf-controll.js'
				}
	    		});
	    		seajs.use('COMMON/cpf/cpf-main.js');
	    	});
	    </script>
	</body>
</html>