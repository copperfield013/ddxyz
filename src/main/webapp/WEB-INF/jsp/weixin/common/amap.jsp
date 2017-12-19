<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<base href="${basePath }" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
	
	
	<!-- fromXu Start -->
	<meta charset="utf-8"/>
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Expires" content="-1">
	<meta name="format-detection" content="telephone=no"><!-- 禁止iphone修改数字样式 -->
	<meta name="viewport" content="minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,width=device-width,user-scalable=no">
	<meta name="renderer" content="webkit">
    <title>选择地址</title>
	<link rel="stylesheet" href="${basePath }media/weixin/main/css/addresschoose.css?${GLOBAL_VERSION}">
	<script src="${basePath }media/weixin/main/js/jquery-3.1.1.min.js"></script>
	<script src="${basePath }media/common/plugins/jquery.tmpl.js"></script>
	<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.4.2&key=b4aff5c63afca34942209cc2837a8f98&plugin=AMap.Autocomplete"></script>
</head>
<body>
	<div class="page-title">
		<div class="keyword-box">
			<input type="search" id="keyword" placeholder="请输入关键字" />
		</div>
		<div class="search-button">
			<span id="doSearch">搜索</span>
		</div>
		<div class="cancel-button">
			<span id="doCancel">取消</span>
		</div>
	</div>
	<div id="search-result">
	</div>
	<div id="map-wrapper">
		<div id="map-cover"></div>
		<div id="map-container"></div>
		<span id="map-close"></span>
	</div>
	<script id="address-row-tmpl" type="jquery/tmpl">
		<div class="address-row" data-id="\${id}">
			<div class="adress-top">
				<span class="address-short">\${name}</span>
				<i class="address-location"></i>
			</div>
			<div class="address-bottom">
				<span class="address-desc">\${district}-\${address}</span>
			</div>
		</div>
	</script>
	<script type="text/javascript">
		$(function(){
			var $resultContainer = $('#search-result');
			AMap.plugin(['AMap.Autocomplete'],function(){
			    var autocomplete= new AMap.Autocomplete({
			        city: '杭州', //城市，默认全国
			    });
			    function search(){
			    	var keyword = $('#keyword').val();
					autocomplete.search(keyword,function(status, result){
						$resultContainer.empty();
						if(result.info === 'OK' && result.count > 0){
							for(var i in result.tips){
								var tip = result.tips[i];
								if(tip.district 
										&& tip.address 
										&& tip.location 
										&& typeof tip.location.lng === 'number' 
										&& typeof tip.location.lat === 'number'){
									var $row = $('#address-row-tmpl').tmpl(tip);
									$resultContainer.append($row.data('tip', tip));
								}
							}
						}
					});
			    }
			    var disableCloseMap = false;
			    function showMap(location, label){
			    	var map = new AMap.Map('map-container',{
					   resizeEnable: true,
		    		   zoom: 20,
		    		   center: [location.lng, location.lat]
		    		});
			    	var marker = new AMap.Marker({
			            position: map.getCenter()
			        });
			        marker.setMap(map);
			        // 设置鼠标划过点标记显示的文字提示
			        marker.setTitle(label);

			        // 设置label标签
			        marker.setLabel({//label默认蓝框白底左上角显示，样式className为：amap-marker-label
			            offset: new AMap.Pixel(20, 20),//修改label相对于maker的位置
			            content: label
			        });
			        disableCloseMap = true;
			        setTimeout(function(){disableCloseMap = false}, 100);
			    }
			    function toggleMapWrapper(toShow){
			    	if(!disableCloseMap || toShow){
				    	$('#map-wrapper').toggle(toShow);
			    	}
			    }
			    function toShowMap(){
			    	var $row = $(this).closest('.address-row');
					var tip = $row.data('tip');
					if(tip && tip.location){
						toggleMapWrapper(true);
						showMap(tip.location, $row.find('.address-short').text());
					}
					return false;
			    }
				$('#keyword').keydown(search)[0].addEventListener('input', search)
				$('#doSearch').click(search).on('touchend', search);
				$(document).on('click touchend', '.address-location', toShowMap);
				$('#map-close').click(function(){
					toggleMapWrapper(false);
				});
				$('#doCancel').click(function(){
					if(typeof window.doCanceled === 'function'){
						window.doCanceled();
					}
				});
			});
			$(document).on("touchstart", function(e) {
			    if(!$(e.target).hasClass("disable")) $(e.target).data("isMoved", 0);
			});
			$(document).on("touchmove", function(e) {
			    if(!$(e.target).hasClass("disable")) $(e.target).data("isMoved", 1);
			});
			$(document).on("touchend", function(e) {
			    if(!$(e.target).hasClass("disable") && $(e.target).data("isMoved") == 0) {
			    	var r = {result: true};
			    	$(e.target).trigger("tap", [r]);
			    	return r.result;
			    }
			});
			$(document).on('tap', '.address-row', function(e, r){
				if(typeof addressSelected === 'function'){
					addressSelected($(this).data('tip'));
					r.result = false;
				}
			});
		});
	</script>
</body>
</html>