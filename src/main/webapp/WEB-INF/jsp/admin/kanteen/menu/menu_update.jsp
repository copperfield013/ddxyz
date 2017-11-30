<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
	#menu-update-${menu.id } ol.waresgroup-container {
	    margin: 1em;
	    font-size: 1.2em;
	}
	#menu-update-${menu.id } ol.waresgroup-container>li {
	    margin: 0.5em 1em;
	}
	#menu-update-${menu.id } span.waresgroup-operate{
		margin-left: 1em;
	}
	#menu-update-${menu.id }  .waresgroup-container li:HOVER span.waresgroup-operate{
		display: inline;
	}
	#menu-update-${menu.id } .waresgroup-container li:FIRST-CHILD .order-up{
		display: none;
	}
	#menu-update-${menu.id } .waresgroup-container li:Last-child .order-down{
		display: none;
	}
	#menu-update-${menu.id } span.waresgroup-operate{
		display: none;
	}
	#menu-update-${menu.id } span.waresgroup-operate .fa{
	    color: #666;
	    cursor: pointer;
	    margin-left: 0.2em;
	}
	#menu-update-${menu.id } span.waresgroup-operate .fa:HOVER{
	    color: #00f;
	}
	#menu-update-${menu.id } .waresgroupes-operate{
		text-align: center;
	    font-size: 1.9em;
	    color: #427fed;
	}
	#menu-update-${menu.id } .waresgroupes-operate>span{
		cursor: pointer;
	}
	#menu-update-${menu.id } span.fa.fa-trash-o.waresgroup-del:HOVER{
		color: #00f;
	}
</style>
<div id="menu-update-${menu.id }">
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="bv-form form-horizontal validate-form" confirm="确认提交更改？" action="admin/kanteen/menu/do_update">
					<input type="hidden" name="id" value="${menu.id }" />
					<div class="form-group">
						<label class="col-lg-2 control-label" for="name">菜单名称</label>
						<div class="col-lg-3">
							<input type="text" class="form-control" name="name" id="name" data-bv-notempty="true"
								value="${menu.name }"
								data-bv-notempty-message="商品组名称不能为空" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="desc">描述</label>
						<div class="col-lg-3">
							<input type="text" class="form-control" 
								name="description" id="description" value="${menu.description }"
							 />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">商品组</label>
						<div class="col-lg-3">
							<ol class="waresgroup-container" >
								<c:forEach items="${waresGroupList }" var="waresGroup">
									<li data-id="${waresGroup.menuGroupId }" data-groupId="${waresGroup.groupId }">
										<span class="group-name">${waresGroup.groupName }</span>
										<span class="waresgroup-operate">
											<span class="fa fa-arrow-circle-o-up order-up"></span>
											<span class="fa fa-arrow-circle-o-down order-down"></span>
											<span class="fa fa-trash-o waresgroup-del"></span>
										</span>
									</li>
								</c:forEach>
							</ol>
							<div class="waresgroupes-operate">
								<span class="fa fa-plus-circle" id="add-group"></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-2 col-lg-2">
							<input type="submit" class="btn btn-primary btn-block" value="提交更改" />
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
			var $page = $('#menu-update-${menu.id}');
			console.log($page);
			
			$('form', $page).on('cpf-submit', function(e, formData){
				var count = 0;
				$('.waresgroup-container li', $page).each(function(){
					var $this = $(this);
					formData.append('id-' + count, $this.attr('data-id') || '');
					formData.append('group-id-' + count, $this.attr('data-groupId'));
					count++;
				});
				formData.append('group-count', count);
			});
			
			$('#add-group', $page).click(function(){
				var except = [];
				var $container = $('.waresgroup-container', $page);
				$('li[data-groupId]', $container).each(function(){
					except.push($(this).attr('data-groupId'));
				});
				Dialog.openDialog('admin/kanteen/menu/choose_waresgroup', '选择商品组', 'choose_waresgroup', {
					reqParam	: {
						exceptGroupIds	: except,
						mode			: 'multi'
					},
					onSubmit: function(data){
						var template = 
							'<li>' +
								'<span class="group-name"></span>' +
								'<span class="waresgroup-operate">' + 
									'<span class="fa fa-arrow-circle-o-up order-up"></span>' +
									'<span class="fa fa-arrow-circle-o-down order-down"></span>' +
									'<span class="fa fa-trash-o waresgroup-del"></span>' +
								'</span>' +
							'</li>';
						var $temp = $(template);
						for(var key in data){
							var group = data[key];
							var $li = $temp.clone();
							$li.attr('data-groupId', group.waresGroupId);
							$('.group-name', $li).text(group.waresGroupName);
							$container.append($li);
						}
					}
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
			$page.on('click', '.waresgroup-del', function(){
				var $thisLi = $(this).closest('li');
				$thisLi.remove();
			});
			
		});
	});
</script>