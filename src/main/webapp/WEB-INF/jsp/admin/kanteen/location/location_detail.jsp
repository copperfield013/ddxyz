<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="location-${location.id }" class="detail">
	<div class="page-header">
		<div class="header-title">
			<h1>${location.name }-详情</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="col-12">
			<input id="locationIdHidden" type="hidden" name="id" value="${location.id }">
			<div class="row">
				<div class="col-lg-6">
					<label class="col-lg-4">编码</label>
					<div class="col-lg-8">${location.code }</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6">
					<label class="col-lg-4">配送地点</label>
					<div class="col-lg-8">${location.name }</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6">
					<label class="col-lg-4">详细地址</label>
					<div class="col-lg-8">${location.address }</div>
				</div>	
			</div>
			<div class="row">
				<div class="col-lg-6">
					<label class="col-lg-4">创建时间</label>
					<div class="col-lg-8"><fmt:formatDate value="${location.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6">
					<label class="col-lg-4">地标</label>
					<div class="col-lg-8">
						<div style="width:700px;height:550px;border:#ccc solid 1px;font-size:12px" id="detail-map-${location.id }"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	$(function(){
		var location_detail_page = $("#location_" + '${location.id }');
		//创建和初始化地图函数：
		function initMap(){
		  	createMap();//创建地图
		  	setMapEvent();//设置地图事件
		  	addMapControl();//向地图添加控件
		}
		function createMap(){
			map = new BMap.Map("detail-map-" + '${location.id }');
			var location = '${location.coordinate}';
			var lng = location.split(",")[0];
			var lat = location.split(",")[1];
			var point = new BMap.Point(lng, lat);
			var marker = new BMap.Marker(point);
			map.centerAndZoom(new BMap.Point(lng, lat),17);
			map.addOverlay(marker);
			markerClick(marker, point);
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
		
		function markerClick(marker, point){
			var opts = {
			  width : 150,     // 信息窗口宽度
			  height: 80,     // 信息窗口高度
			  title : '${location.name}' , // 信息窗口标题
			}
			var infoWindow = new BMap.InfoWindow("地址：" + '${location.address}', opts);  // 创建信息窗口对象 
			marker.addEventListener("click", function(){          
				map.openInfoWindow(infoWindow,point); //开启信息窗口
			});
		}
		var map;
		initMap();
	})
</script>