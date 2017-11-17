<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
	ol.wareses-container {
	    margin: 1em;
	    font-size: 1.2em;
	}
	ol.wareses-container>li {
	    margin: 0.5em 1em;
	}
	#wares-update span.wares-operate{
		margin-left: 1em;
	}
	#wares-update .wareses-container li:HOVER span.wares-operate{
		display: inline;
	}
	#wares-update .wareses-container li:FIRST-CHILD .order-up{
		display: none;
	}
	#wares-update .wareses-container li:Last-child .order-down{
		display: none;
	}
	#wares-update span.wares-operate{
		display: none;
	}
	#wares-update span.wares-operate .fa{
	    color: #666;
	    cursor: pointer;
	    margin-left: 0.2em;
	}
	#wares-update span.wares-operate .fa:HOVER{
	    color: #00f;
	}
	.wareses-operate{
		text-align: center;
	    font-size: 1.9em;
	    color: #427fed;
	}
	.wareses-operate>span{
		cursor: pointer;
	}
	span.fa.fa-trash-o.wares-del:HOVER{
		color: #00f;
	}
</style>
<div id="wares-update">
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="bv-form form-horizontal validate-form" action="admin/kanteen/waresgroup/do_update">
					<input type="hidden" name="id" value="" ${waresGroup.id }/>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="name">商品组名称</label>
						<div class="col-lg-3">
							<input type="text" class="form-control" name="name" id="name" data-bv-notempty="true"
								value="${waresGroup.name }"
								data-bv-notempty-message="商品组名称不能为空" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="desc">描述</label>
						<div class="col-lg-3">
							<input type="text" class="form-control" 
								name="desc" id="description" value="${waresGroup.description }"
							 />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">商品</label>
						<div class="col-lg-3">
							<ol class="wareses-container" >
								<c:forEach items="${waresList }" var="wares">
									<li data-id="${wares.id }" data-waresId="${wares.waresId }">
										<span>${wares.waresName }</span>
										<span class="wares-operate">
											<span class="fa fa-arrow-circle-o-up order-up"></span>
											<span class="fa fa-arrow-circle-o-down order-down"></span>
											<span class="fa fa-trash-o wares-del"></span>
										</span>
									</li>
								</c:forEach>
							</ol>
							<div class="wareses-operate">
								<span class="fa fa-plus-circle" id="add-wares"></span>
							</div>
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
			var $page = $('#wares-update');
			console.log($page);
			
			$('#add-wares', $page).click(function(){
				Dialog.openDialog('admin/kanteen/waresgroup/choose_wares', '选择商品', 'choose_wares', {
					reqParam	: {
						except	: [],
						mode		: 'multi'
					},
					
				});
			});
			$page.on('click', '.order-up', function(){
				var $thisLi = $(this).closest('li');
				$thisLi.insertBefore($thisLi.prev('li'));
			});
			$page.on('click', '.order-down', function(){
				var $thisLi = $(this).closest('li');
				$thisLi.insertAfter($thisLi.next('li'));
			});
			$page.on('click', '.wares-del', function(){
				var $thisLi = $(this).closest('li');
				$thisLi.remove();
			});
			
		});
	});
</script>