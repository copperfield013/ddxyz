<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<title>添加配送（配销${distribution.code }）</title>
<div id="delivery-add">
	<div class="page-header">
		<div class="header-title">
			<h1>添加配送（配销${distribution.code }）</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="bv-form form-horizontal validate-form" confirm="确认创建配送？" action="admin/kanteen/delivery/do_add">
					<input type="hidden" name="distributionId" value="${distribution.id }" />
					<div class="form-group">
						<label class="col-lg-2 control-label">配送地点</label>
						<div class="col-lg-4">
							<input type="text" readonly="readonly" name="locationName" id="locationName"
							 	class="form-control" 
								placeholder="点击选择配送地点" css-cursor="pointer"
								data-bv-notempty="true"
								data-bv-notempty-message="必须选择一个菜单"
								 />
							<input type="hidden" name="locationId" id="locationId" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">配送时间范围</label>
						<div class="col-lg-4">
							<input class="form-control dtrangepicker" type="text" 
							data-bv-notempty="true"
							data-bv-notempty-message="请选择一个配送时间范围"
							readonly="readonly" name="timeRange" id="timeRange" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">可支付方式</label>
						<div class="col-lg-4">
							<select class="form-control" name="payWay">
								<c:forEach items="${paywayMap }" var="payway">
									<option value="${payway.key }">${payway.value }</option>
								</c:forEach>
							</select>
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
	seajs.use(['dialog'], function(Dialog){
		var $page = $('#delivery-add');
		$('#locationName', $page).trigger('change').click(function(){
			var $locationName = $(this);
			Dialog.openDialog('admin/kanteen/location/choose_location', '选择配送点', 'choose_location', {
				onSubmit: function(data){
					$locationName.val(data[0].locationName);
					$('#locationId', $page).val(data[0].locationId);
				}
			});
		});		
	});
</script>

