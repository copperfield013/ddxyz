<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="wares-add">
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="bv-form form-horizontal validate-form" action="admin/kanteen/wares/do_add">
					<div class="form-group">
						<label class="col-lg-2 control-label" for="waresName">商品名称</label>
						<div class="col-lg-3">
							<input type="text" class="form-control" name="waresName" id="waresName" data-bv-notempty="true"
								data-bv-notempty-message="商品名称不能为空" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="basePrice">单价</label>
						<div class="col-lg-3">
							<input type="text" class="form-control" 
								name="basePrice" id="basePrice"
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
						<div class="col-lg-offset-2 col-lg-8">
							<span class="cpf-checkbox" id="has-detail" >需要详情</span>
						</div>
					</div>
					<div class="form-group" id="detail-row" style="display: none;">
						<label class="col-lg-2 control-label" >详情</label>
						<div class="col-lg-8">
							<textarea id="kanteen-wares-add-editor" name="detail"></textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-2 col-lg-2">
							<input type="button" class="btn btn-block" value="详情预览" id="detail-preview" />
						</div>
						<div class="col-lg-2">
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
			$('#thumb', $page).change(function(){
				var files = $(this)[0].files;
				for(var i in files){
					if(files[i].size > 1024000 ){
						$(this).val('');
						Dialog.notice('上传文件不允许超过1M', 'error');
					}
				}
			});
			
			$('#basePrice,#priceUnit', $page).keypress(preview).change(preview);
			function preview(){
				var basePrice = $('#basePrice', $page).val(),
					priceUnit = $('#priceUnit', $page).val();
				if(basePrice != '' && priceUnit != ''){
					$('#preview', $page).text(basePrice + '元/' + priceUnit);
				}
			}
			var editor = CKEDITOR.replace('kanteen-wares-add-editor', {
				 toolbar :
		             [
		              	['Source', 'PasteText', 'RemoveFormat'],
		                //加粗     斜体，     下划线      穿过线      下标字        上标字
		                ['Bold','Italic','Underline'],
		                // 数字列表          实体列表            减小缩进    增大缩进
		                ['NumberedList','BulletedList','-','Outdent','Indent'],
		                //左对 齐             居中对齐          右对齐          两端对齐
		                ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
		                //图片    flash    表格       水平线            表情       特殊字符        分页符
		                ['Image','Table','HorizontalRule', 'Smiley'],
		                //文本颜色     背景颜色
		                ['TextColor','BGColor'],
		                ['Styles','Format','Font','FontSize']
		             ]
			});
			$('#detail-preview', $page).click(function(){
				var formData = new FormData();
				editor.updateElement();
				formData.append('detail', $('#kanteen-wares-add-editor').val());
				formData.append('waresKey', '0');
				Ajax.ajax('admin/kanteen/wares/preview_detail', formData, function(data){
					if(data.qrCodeUrl){
						var $content = $('<div><div class="preview-qrcode-dialog"><h3>打开手机微信扫一扫</h3><p><img width="200px" height="200px" /></p></div></div>');
						$content.find('img').attr('src', data.qrCodeUrl);
						Dialog.openDialog($content, 
								'详情预览', 
								'kanteen-wares-detail-preview-0', 
								{
									width	: '250px',
									height	: '400px'
								});
					}
				})
			});
			
			$('#has-detail', $page).on('cpf-checked-change', function(e, checked){
				$('#detail-row', $page).toggle(checked);
			});
			$('form', $page).on('cpf-submit', function(e, formData){
				if(!$('#has-detail', $page).prop('checked')){
					formData['delete']('detail');
				}
			});
		});
	});
</script>