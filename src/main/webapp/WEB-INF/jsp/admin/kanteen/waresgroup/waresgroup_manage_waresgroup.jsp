<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="waresgroup_manage_waresgroup">
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="bv-form form-horizontal validate-form" action="admin/kanteen/waresgroup/commit_waresgroup_wares">
					<div class="form-group">
						<label class="col-lg-2 control-label">商品组名称</label>
						<div class="col-lg-3">
							${waresGroup.name }
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