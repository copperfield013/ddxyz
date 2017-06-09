<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
	.cpf-upload {
	    width: 30em;
	    height: 15em;
	    border: solid 1px #999;
	}
	.cpf-upload-drag-area {
	    width: 95%;
	    margin: 0 auto;
	    height: 40%;
	    border: dashed 1px #777;
	    margin-top: 2%;
	    position: relative;
	}
	.cpf-upload-control{
		width: 0;
		height: 0;
	}
	
	.cpf-upload-center {
	    height: 5em;
	    top: 50%;
	    position: relative;
	    margin-top: -2.5em;
	    text-align: center;
	}
	
	.cpf-upload-button {
	    margin-top: 1em;
	    display: none;
	    border-radius: 15px;
	    padding: 5px 15px;
	    color: #fff;
	    cursor: pointer;
	}
	.cpf-upload-do-addfile.cpf-upload-button {
		display: inline-block;
	}
	
	label.cpf-upload-drag-desc {
	    position: absolute;
	    color: #bfbfbf;
	    bottom: 0;
	    left: 0;
	    right: 0;
	    text-align: center;
	}
	
	.cpf-upload-files{
		height: 55%;
		position: relative;
	}
	.cpf-upload-files:HOVER>span{
		display: inline;
	}
	.cpf-upload-files>span{
		display: none;
	    position: absolute;
	    top: 50%;
	    margin-top: -0.8em;
	    font-size: 1.3em;
	    color: #4169E1;
	    cursor: pointer;
	    width: 1.3em;
	    height: 1.3em;
	    text-align: center;
	    line-height: 1.3em;
	    z-index: 100;
	}
	.cpf-upload-files>span:HOVER{
		color: #fff;
		border-radius: 1em;
    	background-color: #4169E1;
	}
	.cpf-upload-files>span:BEFORE{
		height: 1.3em;
		line-height: 1.3em;
		text-align: center;
		width: 1.3em;
	}
	
	.cpf-upload-files-container {
	    height: 100%;
	    width: 28em;
	    margin: 0 auto;
	    margin-top: 3%;
	    overflow-x: hidden;
	    overflow-y: hidden;
	    position: relative;
	}
	
	.cpf-upload-files-prev{
		left: 5px;
	}
	.cpf-upload-files-next{
		right: 5px;
	}
	
	.cpf-upload-files-wrapper {
	    height: 100%;
	    position: absolute;
	}
	
	.cpf-upload-file-item {
	    height: 90%;
	    width: 6em;
	    float: left;
	    margin: 0 0.5em;
	    cursor: pointer;
	    
	    moz-user-select: -moz-none;
		-moz-user-select: none;
		-o-user-select:none;
		-khtml-user-select:none;
		-webkit-user-select:none;
		-ms-user-select:none;
		user-select:none;
	    
	}
	
	.cpf-upload-file-icon-container {
   		width: 5em;
	    height: 5em;
	    margin: 0 auto;
	    background-color: #ddd;
	    position: relative;
	}
	.cpf-upload-do-area{
		position: absolute;
	    top: 0;
	    left: 0;
	    right: 0;
	    font-size: 1.3em;
	    background-color:rgba(72,118,255, 0.2);
	    display: none;
	}
	.cpf-upload-file-item:HOVER .cpf-upload-do-area{
		display: block;
	}
	.cpf-upload-do-upload,.cpf-upload-do-remove{
		font-weight: bold;
	    float: right;
	    display: block;
	    margin-left: 6px;
	}
	.cpf-upload-do-upload{
		color: #5db2ff;
	}
	.cpf-upload-do-upload:HOVER{
		color: #0d62af;
	}
	.cpf-upload-do-remove {
	    color: #000;
	}
	.cpf-upload-do-remove:HOVER {
	    color: #555;
	}
	
	img.cpf-upload-file-icon {
	    width: 100%;
	    height: 100%;
	}
	label.cpf-upload-file-name {
	    margin-left: 0.5em;
	    width: 5em;
	    text-align: center;
	    white-space: nowrap;
	    overflow: hidden;
	    text-overflow: ellipsis;
	}
	.cpf-upload-progressbar {
	    height: 2px;
	    background-color: #f00;
	    width: 0;
	}
</style>

<div id="test-upload">
	<div class="page-header">
		<div class="header-title">
			<h1>上传测试</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="col-lg-6">
			<div class="cpf-upload" url="admin/test/doUpload" name="file">
				<div class="cpf-upload-drag-area">
					<div class="cpf-upload-center">
						<span class="cpf-upload-button bg-darkorange cpf-upload-do-addfile">添加文件</span>
						<span class="cpf-upload-button bg-blue cpf-upload-do-uploadall">开始上传</span>
						<span class="cpf-upload-button bg-gray cpf-upload-do-removeall">移除所有</span>
						<input type="file" class="cpf-upload-control" multiple="multiple" />
					</div>
					<label class="cpf-upload-drag-desc">文件拖到此处上传</label>
				</div>
				<div class="cpf-upload-files">
					<span class="cpf-upload-files-prev fa fa-arrow-left"></span>
					<div class="cpf-upload-files-container">
						<div class="cpf-upload-files-wrapper">
						</div>
					</div>
					<span class="cpf-upload-files-next fa fa-arrow-right"></span>
				</div>
			</div>
		
			<!-- <input id="fileupload" type="file" name="file" data-url="admin/test/doUpload" multiple /> -->
		</div>
	</div>
</div>
<script>
	seajs.use(['upload'], function(Upload){
		Upload.bind($('.cpf-upload'));
		/* var $itemTmpl = $('<div class="cpf-upload-file-item">'
				+ '<div class="cpf-upload-file-icon-container"><img class="cpf-upload-file-icon" />'
				+ '</div>'
				+ '<label class="cpf-upload-file-name"></label>'
				+ '</div>');
		$('.cpf-upload-control').fileupload({
	        dataType: 'json',
	        add	: function(e, obj){
	        	console.log('add');
	        	console.log(arguments);
	        	var $wrapper = $('.cpf-upload-files-wrapper');
	        	for(var i in obj.files){
	        		var file = obj.files[i];
					var url = URL.createObjectURL(file);
					var $item = $itemTmpl.clone();
					$('.cpf-upload-file-icon', $item).attr('src', url);
					$('.cpf-upload-file-name', $item).text(file.name);
		        	$wrapper.append($item).css('width', ($wrapper.children().length * 7) + 'em');
	        	}
	        },
	        done: function (e, data) {
	        	console.log('done');
	        	console.log(data);
	        }
	    });
		$('.cpf-upload-button').click(function(){
			$('.cpf-upload-control').click();
		})  */
	});

</script>