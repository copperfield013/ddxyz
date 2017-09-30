<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>确认订单</title>
	<jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include-kanteen.jsp"></jsp:include>
    <link rel="stylesheet" href="media/weixin/kanteen/css/kanteen-order.css?v=1.11">
    <link rel="stylesheet" href="media/weixin/kanteen/css/kanteen-base.css?v=1.1.4">
    <script src="media/weixin/plugins/pushbutton/pushbutton.min.js"></script>
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
                    <span class="canteen-user-information-basic_label">领取地点</span>
                    <label id="fetchSite" class="canteen-user-infomation-basic_select">请选择领取地点</label>
                    <input type="hidden" id="locationId"  />
                    <!-- <input id="fetchSite" tSype="text" name="userTel" readonly value="" placeholder="请选择领取地点"> -->
                </div>
                <div class="canteen-user-information-basic_list">
                    <span class="canteen-user-information-basic_label">领取时间</span>
                    <label id="fetchTime" class="canteen-user-infomation-basic_select">请选择领取时间</label>
                    <input type="hidden" id="timeRange"  />
                </div>
            </div>
            <div class="canteen-user-information-basic_list">
                <span class="canteen-user-information-basic_label">支付方式</span>
                <label id="payWay" class="canteen-user-infomation-basic_select">请选择支付方式</label>
                <input type="hidden" id="payWayValue"  />
            </div>
            <div class="canteen-user-information-basic_mark">
                <p class="canteen-user-information-basic__mark_label">买家备注</p>
                <div class="canteen-user-information-basic__mark_textarea">
                    <textarea name="userTel" id="userMark" placeholder="请填写备注（可不写）"></textarea>
                </div>
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
    <section id="sitebutton"></section>
    <section id="timebutton"></section>
    <section id="paybutton"></section>
</body>

</html>