<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div class="detail" id="location-${location.id }">
	<input id="locationIdHidden" type="hidden" name="id" value="${location.id }">
	<dl>
		<dt>编码</dt>
		<dd>${location.code }</dd>
	</dl>
	<dl>
		<dt>配送地点</dt>
		<dd>${location.name }</dd>
	</dl>
	<dl>
		<dt>详细地址</dt>
		<dd>${location.address }</dd>
	</dl>
	<dl>
		<dt>创建时间</dt>
		<dd><fmt:formatDate value="${location.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></dd>
	</dl>
	
</div>