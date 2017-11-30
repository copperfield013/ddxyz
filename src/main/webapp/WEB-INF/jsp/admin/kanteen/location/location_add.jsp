<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="location-add">
	<div class="page-header">
		<div class="header-title">
			<h1>添加配送地点</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form id="location-add-form" class="bv-form form-horizontal validate-form" action="admin/kanteen/location/doAdd">
					<div class="form-group">
						<label class="col-lg-1 control-label" for="code">编码</label>
						<div class="col-lg-3">
							<input id="location-code" class="form-control" type="text" name="code" placeholder="长度为4的数字"
								data-bv-notempty="true"
								data-bv-notempty-message="编码不能为空"
								pattern="^[0-9]{4}$"
								data-bv-regexp-message="必须是长度为4的数字"
								data-bv-remote="true"
								data-bv-remote-url="admin/kanteen/location/checkCode"
								data-bv-remote-message="编码已经存在"
							/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-1 control-label" for="name">名称</label>
						<div class="col-lg-3">
							<input id="location-name" class="form-control" type="text" name="name" placeholder="配送地点名称"
								data-bv-notempty="true"
								data-bv-notempty-message="配送地点不能为空"
							/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-1 control-label" for="address">详细地址</label>
						<div class="col-lg-3">
							<input id="location-address" class="form-control" type="text" name="address"
								data-bv-notempty="true"
								data-bv-notempty-message="详细地址不能为空"
							/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-1 control-label" for="map">地标</label>
						<!--百度地图容器-->
						<div class="col-lg-3">
							<input id="coordinate" type="hidden" name="coordinate"/>
					    	<div style="width:700px;height:550px;border:#ccc solid 1px;font-size:12px" id="map"></div>
					    </div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-1 col-lg-2">
							<input type="submit" class="btn" id="location-add-submit-btn" value="提交" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<script>
	$(function(){
		seajs.use(['ajax'], function(Ajax){
			var location_add_page = $("#location-add")
			//创建和初始化地图函数：
			function initMap(){
			  	createMap();//创建地图
			  	setMapEvent();//设置地图事件
			  	addMapControl();//向地图添加控件
			  	//addMapOverlay();//向地图添加覆盖物
			  	clickMap();
			}
			function createMap(){
				map = new BMap.Map("map");
				Ajax.ajax('admin/kanteen/location/getLocationPoint',null,function(json){
					map.centerAndZoom(new BMap.Point(json.lng, json.lat),13);
				});
			}
			function setMapEvent(){
			  map.enableScrollWheelZoom();
			  map.enableKeyboard();
			  map.enableDragging();
			  map.enableDoubleClickZoom()
			}
			function addClickHandler(target,window){
			  target.addEventListener("click",function(){
			    target.openInfoWindow(window);
			  });
			}
			//向地图添加控件
			function addMapControl(){
			  var scaleControl = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT}); // 比例尺控件
			  scaleControl.setUnit(BMAP_UNIT_IMPERIAL);
			  map.addControl(scaleControl);
			  var navControl = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:0}); //地图的平移缩放控件
			  map.addControl(navControl);
			  var overviewControl = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:true}); //缩略地图控件
			  map.addControl(overviewControl);
			}
			//左键单击 击地图
			function clickMap(){
				map.addEventListener("click", function(e){
					map.clearOverlays();
					var point = new BMap.Point(e.point.lng, e.point.lat);
					var marker = new BMap.Marker(point);
					map.addOverlay(marker);
					$("#coordinate", location_add_page).val(e.point.lng + "," + e.point.lat);
				});
			}
			var map;
			initMap();
		});
	});
</script>