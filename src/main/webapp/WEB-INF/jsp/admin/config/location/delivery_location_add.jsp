<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
	#location-add {
		margin-top: 10px;
	}
	#location-add form input{
		margin:  5px 2px;
	}
	#location-add .location-form-submit{
		margin-top: 10px;
		margin-left: 11.5%;
	}
	#location-add .location-form-submit #location-add-submit-btn{
		width: 5em;
		height: 2em;
	}
</style>
<div id="location-add">
	<form id="location-add-form" action="admin/config/location/doAdd">
		<div class="detail">
			<dl>
				<dt>编码</dt>
				<dd><input id="location-code" type="text" name="code"/>&nbsp;(长度为4的数字)</dd>
			</dl>
			<dl>
				<dt>配送地点</dt>
				<dd><input id="location-name" type="text" name="name"/></dd>
			</dl>
			<dl>
				<dt>详细地址</dt>
				<dd><input id="location-address" type="text" name="address"/></dd>
			</dl>
		</div>
		<div class="location-form-submit">
			<button id="location-add-submit-btn" type="button">提交</button>
		</div>
	</form>
</div>
<script>
	$(function(){
		seajs.use(['ajax', 'dialog'], function(Ajax, Dialog){
			var location_add = $("#location-add");
			$("#location-add-submit-btn",location_add).click(function(){
				var code = $("#location-code", location_add).val();
				if(code == ''){
					Dialog.notice("请填写编码！", "error");
					return;
				}else{
					if(code.length != 4){
						Dialog.notice("编码长度错误！", "error");
						return;
					}else if(!isNumber(code)){
						Dialog.notice("编码格式错误！", "error");
						return;
					}
				}
				Ajax.ajax('admin/config/location/checkCode',{
					code	:	code
				},function(json){
					if(json.status == 'YES'){
						$("#location-add-form", location_add).submit();
					}else{
						Dialog.notice("编码已存在！", "error");
					}
				});
			});
			
			// 判断是否是数字 返回true 和false
		    function isNumber(value){
		        return /^[(-?\d+\.\d+)|(-?\d+)|(-?\.\d+)]+$/.test(value + '');
		    }
			
		});
	});
</script>