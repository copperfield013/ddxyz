<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <meta name="format-detection" content="telephone=no"><!-- 禁止iphone修改数字样式 -->
    <meta name="viewport" content="minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,width=device-width,user-scalable=no">
    <title>点点新意</title>
    <!--<link rel="stylesheet/less" href="less/order.less">-->
    <!--<script src="js/less.min.js"></script>-->
    <link rel="stylesheet" href="${basePath }media/weixin/main/css/templete.css">
    <link rel="stylesheet" href="${basePath }media/weixin/main/css/common.css">
    <link rel="stylesheet" href="${basePath }media/weixin/main/css/order.css">
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
    <!-- 待收货 -->
    <div class="order">
        <p class="number">订单号：M1790378T1<i class="todo">待收货</i></p>
        <p class="detail">
            <img src="${basePath }media/weixin/main/image/thumb-tea1.jpg" alt="奶茶1">
            <span class="name">奶茶<i>1</i></span>
            <span class="price">单价：￥12.0</span>
            <a href="javascript:;" class="btn">再次购买</a>
        </p>
        <p class="result">
            <span class="time">2016/06/21 9:30:25</span>
            <span class="count">总计<b>1</b>杯</span>
            <span class="money">总价：<b>￥12.0</b></span>
        </p>
    </div>
    <!-- 已取消 -->
    <div class="order">
        <p class="number">订单号：M1790378T1<i class="cancel">已取消</i></p>
        <p class="detail">
            <img src="${basePath }media/weixin/main/image/thumb-tea1.jpg" alt="奶茶1">
            <span class="name">奶茶<i>1</i></span>
            <span class="price">单价：￥12.0</span>
        </p>
        <p class="detail">
            <img src="${basePath }media/weixin/main/image/thumb-tea2.jpg" alt="奶茶2">
            <span class="name">玛奇朵<i>2</i></span>
            <span class="price">单价：￥14.0</span>
        </p>
        <p class="result">
            <span class="time">2016/06/21 9:30:25</span>
            <span class="count">总计<b>3</b>杯</span>
            <span class="money">总价：<b>￥40.0</b></span>
        </p>
        <p class="tool">
            <a href="javascript:;" class="btn">继续支付</a>
        </p>
    </div>
</main>
</body>
</html>