<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>确认订单</title>
	<jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
    <link rel="stylesheet" href="media/weixin/kanteen/css/reset.css?v=1.1">
    <link rel="stylesheet" href="media/weixin/kanteen/css/kanteen-order.css?v=1.11">
    <script src="media/weixin/kanteen/js/mobile-adaptation.js"></script>
    <script src="media/weixin/kanteen/js/kanteen-order.js"></script>
</head>

<body>

    <form id="canteen-order-information">
        <section class="canteen-user-information-basic">
            <div class="canteen-user-information-basic_list">
                <span class="canteen-user-information-basic_label">领取人</span>
                <input id="userName" type="text" name="userName" value="" placeholder="请输入您的姓名">
            </div>
            <div class="canteen-user-information-basic_list">
                <span class="canteen-user-information-basic_label">手机号码</span>
                <input id="userTel" tSype="text" name="userTel" value="" placeholder="请输入您的联系方式">
            </div>
        </section>

        <section class="canteen-user-information-detail">
            <div class="canteen-user-information-basic_important">
                <div class="canteen-user-information-basic_list">
                    <span class="canteen-user-information-basic_label">领取时间</span>
                    <span>2017.9.01 14:00~2017.9.01 14:00</span>
                </div>
                <div class="canteen-user-information-basic_list">
                    <span class="canteen-user-information-basic_label">领取地点</span>
                    <span>林家食堂</span>
                </div>
            </div>
            <div class="canteen-user-information-basic_list">
                <span class="canteen-user-information-basic_label">支付方式</span>
                <span class="canteen-user-pay-way">当面支付</span>
            </div>
            <div class="canteen-user-information-basic_list">
                <span class="canteen-user-information-basic_label">买家备注</span>
                <input id="userMark" tSype="text" name="userTel" value="" placeholder="请填写备注">
            </div>
        </section>

        <section class="canteen-order-information">
            <p class="canteen-order-information_title">
                <i class="canteen-order-information_logo"></i>
                <span class="canteen-order-information_logoname">林家食堂</span>
            </p>
            <div class="canteen-order-information_lists">
                <div class="canteen-order-information_list">
                    <span class="canteen-order-information_list_name">卤鸡腿</span>
                    <span class="canteen-order-information_list_count canteen-icon canteen-close-icon">1</span>
                    <span class="canteen-order-information_list_price canteen-icon canteen-rmb-icon">10</span>
                </div>
                <div class="canteen-order-information_list">
                    <span class="canteen-order-information_list_name">卤鸡腿</span>
                    <span class="canteen-order-information_list_count canteen-icon canteen-close-icon">1</span>
                    <span class="canteen-order-information_list_price canteen-icon canteen-rmb-icon">10</span>
                </div>
                <div class="canteen-order-information_list">
                    <span class="canteen-order-information_list_name">卤鸡腿</span>
                    <span class="canteen-order-information_list_count canteen-icon canteen-close-icon">1</span>
                    <span class="canteen-order-information_list_price canteen-icon canteen-rmb-icon">10</span>
                </div>
                <div class="canteen-order-information_list">
                    <span class="canteen-order-information_list_name">卤鸡腿</span>
                    <span class="canteen-order-information_list_count canteen-icon canteen-close-icon">1</span>
                    <span class="canteen-order-information_list_price canteen-icon canteen-rmb-icon">10</span>
                </div>
                <div class="canteen-order-information_list">
                    <span class="canteen-order-information_list_name">卤鸡腿</span>
                    <span class="canteen-order-information_list_count canteen-icon canteen-close-icon">1</span>
                    <span class="canteen-order-information_list_price canteen-icon canteen-rmb-icon">10</span>
                </div>
                <div class="canteen-order-information_list">
                    <span class="canteen-order-information_list_name">卤鸡腿</span>
                    <span class="canteen-order-information_list_count canteen-icon canteen-close-icon">1</span>
                    <span class="canteen-order-information_list_price canteen-icon canteen-rmb-icon">10</span>
                </div>
                <div class="canteen-order-information_list">
                    <span class="canteen-order-information_list_name">卤鸡腿</span>
                    <span class="canteen-order-information_list_count canteen-icon canteen-close-icon">1</span>
                    <span class="canteen-order-information_list_price canteen-icon canteen-rmb-icon">10</span>
                </div>
                <div class="canteen-order-information_list">
                    <span class="canteen-order-information_list_name">卤鸡腿</span>
                    <span class="canteen-order-information_list_count canteen-icon canteen-close-icon">1</span>
                    <span class="canteen-order-information_list_price canteen-icon canteen-rmb-icon">10</span>
                </div>
                <div class="canteen-order-information_list">
                    <span class="canteen-order-information_list_name">卤鸡腿</span>
                    <span class="canteen-order-information_list_count canteen-icon canteen-close-icon">1</span>
                    <span class="canteen-order-information_list_price canteen-icon canteen-rmb-icon">10</span>
                </div>
                <div class="canteen-order-information_list">
                    <span class="canteen-order-information_list_name">卤鸡腿</span>
                    <span class="canteen-order-information_list_count canteen-icon canteen-close-icon">1</span>
                    <span class="canteen-order-information_list_price canteen-icon canteen-rmb-icon">10</span>
                </div>
                <div class="canteen-order-information_list">
                    <span class="canteen-order-information_list_name">卤鸡腿</span>
                    <span class="canteen-order-information_list_count canteen-icon canteen-close-icon">1</span>
                    <span class="canteen-order-information_list_price canteen-icon canteen-rmb-icon">10</span>
                </div>
                <div class="canteen-order-information_list">
                    <span class="canteen-order-information_list_name">卤鸡腿</span>
                    <span class="canteen-order-information_list_count canteen-icon canteen-close-icon">1</span>
                    <span class="canteen-order-information_list_price canteen-icon canteen-rmb-icon">10</span>
                </div>
                <div class="canteen-order-information_list">
                    <span class="canteen-order-information_list_name">卤鸡腿</span>
                    <span class="canteen-order-information_list_count canteen-icon canteen-close-icon">1</span>
                    <span class="canteen-order-information_list_price canteen-icon canteen-rmb-icon">10</span>
                </div>
                
            </div>
            <p class="canteen-order-information_price">
                <span class="canteen-order-information_price_text">商品总价</span>
                <span class="canteen-order-information_price_totalprice canteen-icon canteen-rmb-icon">70</span>
            </p>
        </section>
    </form>

    <footer class="canteen-order-footer">
        <div class="canteen-order-total-price">
            <span class="canteen-order-total-price_text">合计：</span>
            <span class="canteen-order-total-price_price canteen-icon canteen-rmb-icon">70</span>
        </div>
        <a href="canteen_order_list.html" class="order-submit">提交订单</a>
    </footer>

</body>

</html>