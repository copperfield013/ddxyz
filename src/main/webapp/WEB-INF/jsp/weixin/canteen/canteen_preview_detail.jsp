<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
	<title>${WXAPP.cname }</title>
	<base>
	<jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
	<link href="media/weixin/canteen/css/canteen-home.css?111" type="text/css" rel="stylesheet" />
</head>
<body>
	<main>
        <header>
            <img src="${basePath }media/weixin/canteen/image/banner-canteen.png" alt="林业厅之家">
        </header>
        <div class="block weekMenu">
            <h4>本周菜单</h4>
        </div>
        <div class="block time-range">
        	<h4>下单和领取时间</h4>
        	<p>
        	</p>
        	<p>
        	</p>
        </div>
        <!-- 领取地址 -->
        <div class="block address">
            <h4>领取地址</h4>         
        </div>
    </main>
    <div class="shade dialog-shade" >
			<div style="height: 70%; top:15%;">
				<div class="shade-operation">
					<span class="shade-close">×</span>
				</div>
				<div id="detail-content" style="overflow-y: scroll;overflow-x: hidden;height: 80%;font-size: 14px;margin: 0px 10px 10px 10px;padding-top: 10px">
					${detail }
				</div>
			</div>
    </div>
    <footer>
        <a href="weixin/canteen/order_list" class="order-link">订单</a>
        <a href="weixin/canteen/order" class="main">立即下单</a>
    </footer>
    <script type="text/javascript">
    	$(function(){
    	});
    </script>
</body>
</html>