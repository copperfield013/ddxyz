<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="distriburion-add">
	<div class="page-header">
		<div class="header-title">
			<h1>添加配销</h1>
		</div>
	</div>
	
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="bv-form form-horizontal validate-form" confirm="确认提交更改？" action="admin/kanteen/distribution/do_add">
					<div class="form-group">
						<label class="col-lg-2 control-label" for="">指定菜单</label>
						<div class="col-lg-4">
							<input type="text" id="menu-name" class="form-control" readonly="readonly" 
								css-cursor="pointer" placeholder="点击选择菜单"
								name="menuName"
								data-bv-notempty="true"
								data-bv-notempty-message="必须选择一个菜单" />
							<input type="hidden" id="menu-id" name="menuId" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="orderTimeRange">可预订时间</label>
						<div class="col-lg-4">
							<input type="text" class="form-control dtrangepicker" id="orderTimeRange" name="dateRange"
	                			css-cursor="text"
	                			data-bv-notempty="true"
								data-bv-notempty-message="必须选择一个时间段" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="saveProduct">细化产品</label>
						<div class="col-lg-3">
							<label class="form-control-static">
							    <input name="saveProduct" class="checkbox-slider toggle colored-blue" type="checkbox" value="1">
							    <span class="text"></span>
							</label>
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
<script type="text/javascript">
	seajs.use(['utils', 'dialog'], function(Utils, Dialog){
		var $page = $('#distriburion-add');
		//Utils.daterangepicker($('.timeRange', $page));
		
		$('#menu-name', $page).trigger('change').click(function(){
			var $menuName = $(this);
			Dialog.openDialog('admin/kanteen/distribution/choose_menu', '选择菜单', 'choose_menu', {
				onSubmit: function(data){
					$menuName.val(data[0].menuName);
					$('#menu-id', $page).val(data[0].menuId);
					//重新校验表单
					$menuName.trigger('cpf-revalidate');
				}
			});
		});
	})
</script>