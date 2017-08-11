<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
	#canteen-batch-delivery div.wares-row {
		margin-top		: 0.5em;
		margin-bottom	: 0.5em;
	}
</style>
<c:set var="waresRow">
	<div class="row wares-row">
		<div class="col-lg-6">
			<select class="form-control wares" name="waresId-0">
				<option value="">--请选择--</option>
				<c:forEach items="${waresList }" var="wares">
					<option value="${wares.id }">${wares.name }</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-lg-4">
			<input type="number" min="0 " name="maxCount-0" class="form-control max-count" placeholder="限量" />
		</div>
		<div class="col-lg-2">
			<input type="button" class="btn dwares-row-del" value="删除" />
		</div>
	</div>
</c:set>
<div id="canteen-batch-delivery">
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="form-horizontal" action="admin/canteen/config/do_generate_delivery" method="post">
					<div class="form-group">
						<label class="col-lg-2 control-label" for="code">预定时间</label>
						<div class="col-lg-3">
							<input type="text" class="form-control timeRange" id="orderTimeRange" name="orderTimeRange" readonly="readonly" 
	                			css-width="25em"  css-cursor="text" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">分发时间</label>
						<div class="col-lg-3">
							<input type="text" class="form-control timeRange" id="deliveryTimeRange" name="deliveryTimeRange" readonly="readonly" 
	                			css-width="25em"  css-cursor="text"  />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">分发地点</label>
						<div class="col-lg-3">
							<select id="location" name="locationId">
								<c:forEach items="${locations }" var="location">
									<option value="${location.id }">${location.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="name">餐品</label>
						<div class="col-lg-4" id="wares-row-container">
							<input type="hidden" name="waresCount" id="waresCount" />
							${waresRow }
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-2 col-lg-3">
							<input type="button" class="btn btn-block btn-primary" id="add-wares" value="添加" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-2 col-lg-2">
							<a class="btn dialog" target="canteen-create-wares" href="admin/canteen/config/create_wares" title="创建餐品">创建餐品</a>
							<input type="submit" class="btn btn-primary" id="delivery-wares-create-button" value="提交" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script type="text/jquery-tmpl" id="waresRow">${waresRow}</script>
</div>
<script>
	$(function(){
		seajs.use(['ajax', 'dialog', 'utils'], function(Ajax, Dialog, utils){
			console.log('canteen-batch-delivery');
			var nav = $("#canteen-batch-delivery");
			
			$('form', nav).on('cpf-submit', function(e){
				var orderTimeRange = $('#orderTimeRange', nav).val(),
					deliveryTimeRange = $('#deliveryTimeRange', nav).val();
				var errMsg = '';
				if(!orderTimeRange){
					errMsg = '预定时间范围不能为空';
				}else if(!deliveryTimeRange){
					errMsg = '分发时间范围不能为空';
				}else{
					var dWaresList = getDeliveryWaresList();
					if(dWaresList.length == 0){
						errMsg = '至少选择一个餐品';
					}else{
						return true;
					}
				}
				if(errMsg){
					Dialog.notice(errMsg, 'error');
					e.doCancel();
				}
			});
			
			$('.timeRange', nav).daterangepicker({
				format 				: 'YYYY-MM-DD HH:mm:ss',
				timePicker			: true,
				timePicker12Hour	: false,
				timePickerIncrement : 5,
				separator			: '~',
				minDate				: '<fmt:formatDate value="${Monday }" pattern="yyyy-MM-dd"/>',
				maxDate				: '<fmt:formatDate value="${Sunday }" pattern="yyyy-MM-dd"/>',
				locale				: {
					applyLabel	: '确定',
	                cancelLabel: '取消',
	                fromLabel: '从',
	                toLabel: '到'
				}
			});
			$('#add-wares', nav).click(function(){
				var $row = $('#waresRow', nav).tmpl({}).change(refreshDisableWares);
				$row.appendTo($('#wares-row-container', nav));
				refreshDisableWares();
			});
			$('.wares-row select.wares', nav).change(refreshDisableWares);
			
			
			nav.on('click', '.dwares-row-del', function(){
				var $row = $(this).closest('.wares-row');
				if($row.siblings().length > 0){
					$row.remove();
				}
				refreshDisableWares();
				return false;
			});
			
			function refreshDisableWares(){
				var dWaresList = getDeliveryWaresList();
				$('.wares-row select.wares', nav).each(function(){
					var $select = $(this);
					$('option', $select).removeAttr('disabled');
					var thisVal = $(this).val();
					for(var i in dWaresList){
						var dWaresId = dWaresList[i].waresId;
						if(thisVal != dWaresId){
							$select.find('option[value="' + dWaresId + '"]').attr('disabled', 'disabled');
						}
					}
				});
			}
			
			function getDeliveryWaresList(){
				var list = [];				
				var $form = $('form', nav); 
				
				var waresCount = $('.wares-row', nav).each(function(i){
					var $row = $(this);
					var $wares = $row.find('.wares').attr('name', 'waresId-' + i),
						$maxCount = $row.find('.max-count').attr('name', 'maxCount-' + i);
					var waresId = $wares.val();
					if(waresId){
						list.push({
							waresId	: waresId,
							maxCount: parseInt($maxCount.val()) || 0
						});
					}
				}).length;
				$('#waresCount').val(waresCount);
				return list;
			}
		});
	});
</script>