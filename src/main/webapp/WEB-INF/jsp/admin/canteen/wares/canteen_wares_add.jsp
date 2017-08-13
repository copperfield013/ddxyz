<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="canteen-batch-delivery">
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="bv-form form-horizontal validate-form" action="admin/canteen/wares/do_add">
					<div class="form-group">
						<label class="col-lg-2 control-label" for="waresName">商品名称</label>
						<div class="col-lg-3">
							<input type="text" class="form-control" name="waresName" id="waresName" data-bv-notempty="true"
								data-bv-notempty-message="商品名称不能为空" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="unitPrice">单价</label>
						<div class="col-lg-3">
							<input type="text" class="form-control" 
								name="unitPrice" id="unitPrice"
								data-bv-notempty="true"
								data-bv-notempty-message="单价不能为空"
								pattern="^(\d+)(\.\d{1,2})?$"
								data-bv-regexp-message="单价是一个数字（最多两位小数）"
							 />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="priceUnit">价格单位</label>
						<div class="col-lg-3">
							<input type="text" class="form-control" name="priceUnit" id="priceUnit" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="thumb">图片</label>
						<div class="col-lg-3">
							<input type="file" class="form-control" name="thumb" id="thumb" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-1 col-lg-2">
							<input type="submit" class="btn" value="提交" />
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
		});
	});
</script>