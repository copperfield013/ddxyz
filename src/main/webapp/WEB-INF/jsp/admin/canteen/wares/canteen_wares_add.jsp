<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="canteen-wares-add">
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
							<span class="help-block">单价预览：<span id="preview"></span></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="thumb">图片</label>
						<div class="col-lg-3">
							<input type="file" class="form-control" name="thumb" id="thumb" accept="image/*" />
							<span class="help-block">上传图片不能超过1M</span>
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
			var $page = $('#canteen-wares-add');
			$('#thumb', $page).change(function(){
				var files = $(this)[0].files;
				for(var i in files){
					if(files[i].size > 1024000 ){
						$(this).val('');
						Dialog.notice('上传文件不允许超过1M', 'error');
					}
				}
			});
			
			$('#unitPrice,#priceUnit', $page).keypress(preview).change(preview);
			function preview(){
				var unitPrice = $('#unitPrice', $page).val(),
					priceUnit = $('#priceUnit', $page).val();
				if(unitPrice != '' && priceUnit != ''){
					$('#preview', $page).text(unitPrice + '元/' + priceUnit);
				}
			}
			
		});
	});
</script>