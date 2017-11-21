<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="wares-add">
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="bv-form form-horizontal validate-form" action="admin/kanteen/menu/do_add">
					<div class="form-group">
						<label class="col-lg-2 control-label" for="name">菜单名称</label>
						<div class="col-lg-3">
							<input type="text" class="form-control" name="name" id="name" data-bv-notempty="true"
								data-bv-notempty-message="菜单名称不能为空" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="description">描述</label>
						<div class="col-lg-3">
							<input type="text" class="form-control" 
								name="description" id="description"
							 />
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-2 col-lg-2">
							<input type="submit" class="btn btn-primary btn-block" value="提交" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<script>
	$(function(){
		seajs.use(['ajax', 'dialog', 'utils'], function(Ajax, Dialog, utils){
			var $page = $('#wares-add');
			console.log($page);
		});
	});
</script>