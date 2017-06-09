<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div>
	<style>
		#update-comment{
			cursor: pointer;
			font-size: 1.3em;
		}
		#update-comment:HOVER{
			color: #000 !important;
		}
		div#file-view {
		    border: 1px solid #ccc;
		}
	</style>
	<form class="bv-form form-horizontal">
		<div class="page-body">
			<div class="form-group">
				<label class="col-lg-2" style="text-align: right;">
					文件名
				</label>
				<label class="col-lg-6" id="file-name">
					1.jpg
				</label>
			</div>
			<div class="form-group">
				<label class="col-lg-2 control-label">
					备注
				</label>
				<div class="col-lg-6">
					<span class="input-icon icon-right">
						<textarea id="file-comment" class="form-control" rows="4" style="width: 100%;resize:none;"></textarea>
						<i class="fa-">
							<i id="update-comment" class="glyphicon glyphicon-ok success"></i>
						</i>
					</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-lg-2 control-label">
					文件预览
				</label>
				<div class="col-lg-6" id="file-view">
					
				</div>
			</div>
		</div>
		
	</form>
</div>