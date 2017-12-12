<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>选择地址</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style>
		.page-title {
		    position: absolute;
		    top: 0;
		    height: 4em;
		    left: 0;
		    right: 0;
		}
		.page-title>div {
		    float: left;
		}
		.keyword-box {
		    width: 90%;
		}
		.search-button {
		    width: 10%;
		}
		.search-result {
		    position: absolute;
		    top: 4em;
		    left: 0;
		    right: 0;
		    bottom: 0;
		}
	</style>
	<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.4.2&key=b4aff5c63afca34942209cc2837a8f98&plugin=AMap.Autocomplete"></script>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
</head>
<body>
	<div class="page-title">
		<div class="keyword-box">
			<input type="search" id="keyword" />
		</div>
		<div class="search-button">
			<span id="doSearch">搜索</span>
		</div>
	</div>
	<div class="search-result">
		<div class="address-row">
			<div class="adress-top">
				<span class="address-short">西溪公园</span>
			</div>
			<div class="address-bottom">
				<span class="address-desc">浙江省杭州市西湖区文二西路</span>
			</div>
		</div>
	</div>
	<div id="myPageTop">
	    <table>
	        <tr>
	            <td>
	                <label>请输入关键字：</label>
	            </td>
	        </tr>
	        <tr>
	            <td>
	                <input id="tipinput"/>
	            </td>
	        </tr>
	    </table>
	</div>
	<script type="text/javascript">
	    //输入提示
	    /* var auto = new AMap.Autocomplete({
	        input: "tipinput"
	    }); */
	</script>
</body>
</html>