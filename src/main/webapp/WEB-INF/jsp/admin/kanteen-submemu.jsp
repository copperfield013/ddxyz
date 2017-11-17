<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<sec:authorize access="hasRole('ROLE_KANTEEN')">
	<li>
		<a href="#" class="menu-dropdown"> 
			<i class="menu-icon fa fa-rocket"></i> 
			<span class="menu-text">商家管理</span>
			<i class="menu-expand"></i>
		</a>
		<ul class="submenu">
			<li>
				<a href="#" class="tab" target="" title="微信配置"> 
					<span class="menu-text">微信配置</span>
				</a>
			</li>
		</ul>
	</li>
	<li>
		<a href="#" class="menu-dropdown"> 
			<i class="menu-icon fa fa-book"></i> 
			<span class="menu-text">菜单管理</span>
			<i class="menu-expand"></i>
		</a>
		<ul class="submenu">
			<li>
				<a href="admin/kanteen/wares" class="tab" target="wares_list" title="商品"> 
					<span class="menu-text">商品</span>
				</a>
			</li>
			<li>
				<a href="admin/kanteen/waresgroup" class="tab" target="waresgroup_list" title="商品组"> 
					<span class="menu-text">商品组</span>
				</a>
			</li>
			<li>
				<a href="#" class="tab" target="" title="菜单"> 
					<span class="menu-text">菜单</span>
				</a>
			</li>
		</ul>
	</li>
	<li>
		<a href="#" class="menu-dropdown"> 
			<i class="menu-icon fa fa-sign-in"></i> 
			<span class="menu-text">发布管理</span>
			<i class="menu-expand"></i>
		</a>
		<ul class="submenu">
			<li>
				<a href="admin/kanteen/distribution" class="tab" target="distribution_list" title="配销"> 
					<span class="menu-text">配销</span>
				</a>
			</li>
			<li>
				<a href="#" class="tab" target="" title="配送"> 
					<span class="menu-text">配送</span>
				</a>
			</li>
		</ul>
	</li>
	<li>
		<a href="#" > 
			<i class="menu-icon fa fa-file-text-o"></i> 
			<span class="menu-text">数据统计</span>
		</a>
	</li>
	
</sec:authorize>