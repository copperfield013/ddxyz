<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
	<title>点点新意</title>
	<jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
	
    <link rel="stylesheet" href="${basePath }media/weixin/main/css/index.css">
    <script src="${basePath }media/weixin/main/js/jquery-3.1.1.min.js"></script>
    <script src="${basePath }media/weixin/main/js/ix.js"></script>
    <script>
    $(function() {
        ix.init();
    });
    </script>
</head>
<body>
    <main>
        <!-- 头部banner -->
        <header>
            <img src="${basePath }media/weixin/main/image/banner-index.jpg" alt="点点心意">
        </header>
        <!-- 时间档 -->
        <div class="block timeline">
            <h4>时间档<pre>高亮表示该时段有奶茶供应</pre></h4>
            <p>
                <a href="javascript:;" class="no">11点</a>
                <a href="javascript:;" class="yes">12点</a>
                <a href="javascript:;" class="yes">13点</a>
            </p>
            <p>
                <a href="javascript:;" class="yes">14点</a>
                <a href="javascript:;" class="yes">15点</a>
                <a href="javascript:;" class="yes">16点</a>
            </p>
        </div>
        <!-- 奶茶种类 -->
        <div class="block milktea">
            <h4>奶茶种类</h4>
            <p>
                <img src="${basePath }media/weixin/main/image/thumb-tea1.jpg" alt="奶茶1">
                <span class="name">奶茶</span>
                <span class="price">
                    <b>￥10.0/杯</b><i>（大杯+￥3）</i>
                </span>
            </p>
            <p>
                <img src="${basePath }media/weixin/main/image/thumb-tea2.jpg" alt="奶茶2">
                <span class="name">焦糖玛奇朵</span>
                <span class="price">
                    <b>￥12.0/杯</b><i>（大杯+￥3）</i>
                </span>
            </p>
            <p>
                <img src="${basePath }media/weixin/main/image/thumb-tea3.jpg" alt="奶茶3">
                <span class="name">金桔柠檬</span>
                <span class="price">
                    <b>￥12.0/杯</b><i>（大杯+￥3）</i>
                </span>
            </p>
        </div>
        <!-- 奶茶领取地址 -->
        <div class="block address">
            <h4>奶茶领取地址<pre>请您在所选时间到所选地址领取奶茶</pre></h4>
            <p>
                <img src="${basePath }media/weixin/main/image/thumb-shop1.jpg" alt="地址1">
                <span class="name">天虹商场</span>
                <span class="addr">地址：天虹红商场A座南大门内</span>
            </p>
            <p>
                <img src="${basePath }media/weixin/main/image/thumb-shop2.jpg" alt="地址2">
                <span class="name">杭钻大厦</span>
                <span class="addr">地址：杭钻大厦5A楼食堂内</span>
            </p>
        </div>
    </main>
    <footer>
        <a href="weixin/ydd/orderList" class="order">订单</a>
        <a href="weixin/ydd/order" class="main">立即下单</a>
    </footer>
</body>
</html>