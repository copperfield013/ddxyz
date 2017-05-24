<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="location-${location.id }" class="detail">
	<div class="page-header">
		<div class="header-title">
			<h1>${location.name }-详情</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="col-12">
			<input id="locationIdHidden" type="hidden" name="id" value="${location.id }">
			<div class="row">
				<div class="col-lg-6">
					<label class="col-lg-4">编码</label>
					<div class="col-lg-8">${location.code }</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6">
					<label class="col-lg-4">配送地点</label>
					<div class="col-lg-8">${location.name }</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6">
					<label class="col-lg-4">详细地址</label>
					<div class="col-lg-8">${location.address }</div>
				</div>	
			</div>
			<div class="row">
				<div class="col-lg-6">
					<label class="col-lg-4">创建时间</label>
					<div class="col-lg-8"><fmt:formatDate value="${location.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></div>
				</div>
			</div>
		</div>
	</div>
</div>