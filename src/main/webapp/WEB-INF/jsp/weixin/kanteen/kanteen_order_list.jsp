<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>订单列表</title>

    <link rel="stylesheet" href="media/weixin/kanteen/css/reset.css">
    <link rel="stylesheet" href="media/weixin/kanteen/css/canteen-order-list.css">
    <script src="media/weixin/kanteen/js/mobile-adaptation.js"></script>
    <script src="media/weixin/kanteen/js/kanteen-order-list.js"></script>
</head>

<body>
    <header>
        <!--搜索框-->
        <form class="canteen-search-bar_form">
            <div class="canteen-search-bar_box">
                <i class="canteen-icon canteen-search-icon"></i>
                <input id="canteenSearch" type="text" name="canteenSearch" placeholder="搜索商品">
                <i class="canteen-search-bar_clear canteen-icon canteen-search-cancel-icon"></i>
            </div>
            <label for="canteenSearch" class="canteen-search-bar_label">
                <i class="canteen-icon canteen-search-icon"></i>
                <span>搜索商品</span>
            </label>
        </form>

        <!--订单nav-->
        <div class="order-nav">
            <a class="order-week" href="javascript:">本周订单</a>
            <a class="order-total" href="javascript:">全部订单</a>
            <i class="order-nav-border"></i>
        </div>
    </header>
    <div class="canteen-order-list-wrap">
        <section class="canteen-order-list">
            <p class="canteen-order-list_header">
                <i class="canteen-order-list_logo"></i>
                <span class="canteen-order-list_logoname">林家食堂</span>
                <span class="canteen-order-list_status">待领取</span>
            </p>
            <div class="canteen-order-list_lists">
                <div class="canteen-order-list_list">
                    <img class="canteen-order-list_list_image" src="static/image/meal_example.png">
                    <span class="canteen-order-list_list_name">卤鸡腿</span>
                    <div class="canteen-order-list_list_rightbox">
                        <span class="canteen-order-list_list_price canteen-icon canteen-rmb-icon">20.00</span>
                        <span class="canteen-order-list_list_count canteen-icon canteen-close-icon">1</span>
                    </div>
                </div>
                <div class="canteen-order-list_list">
                    <img class="canteen-order-list_list_image" src="static/image/meal_example.png">
                    <span class="canteen-order-list_list_name">卤鸡腿</span>
                    <div class="canteen-order-list_list_rightbox">
                        <span class="canteen-order-list_list_price canteen-icon canteen-rmb-icon">20.00</span>
                        <span class="canteen-order-list_list_count canteen-icon canteen-close-icon">1</span>
                    </div>
                </div>
                <div class="canteen-order-list_list">
                    <img class="canteen-order-list_list_image" src="static/image/meal_example.png">
                    <span class="canteen-order-list_list_name">卤鸡腿</span>
                    <div class="canteen-order-list_list_rightbox">
                        <span class="canteen-order-list_list_price canteen-icon canteen-rmb-icon">20.00</span>
                        <span class="canteen-order-list_list_count canteen-icon canteen-close-icon">1</span>
                    </div>
                </div>
            </div>
            <p class="canteen-order-list_list_totalprice">
                <span class="canteen-order-list_list_totalprice_text">合计：</span>
                <span class="canteen-order-list_list_totalprice_number canteen-icon canteen-rmb-icon">40.00</span>
            </p>
            <div class="canteen-order-list_list_information_box">
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">领取人</span>
                    <span class="canteen-order-list_lsit_name">王杰</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">领取时间</span>
                    <span class="canteen-order-list_list_rtime">2017-09-01 16:00:00</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">部门</span>
                    <span class="canteen-order-list_lsit_department">文化部</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">备注</span>
                    <span class="canteen-order-list_lsit_mark">切好</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">联系号码</span>
                    <span class="canteen-order-list_lsit_tel">13888888888</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">创建时间</span>
                    <span class="canteen-order-list_lsit_stime">2017-08-30 18:00:00</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">订单编号</span>
                    <span class="canteen-order-list_lsit_number">20170930012545896545</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">截止时间</span>
                    <span class="canteen-order-list_lsit_etime">2020-09-19 18:00:00</span>
                </p>
            </div>
            <div class="canteen-order-list_operation">
                <div class="canteen-order-list_more">
                    查看更多
                    <i class="canteen-order-list_more_icon canteen-icon canteen-toright-icon"></i>
                </div>
                <div class="canteen-order-list_button">
                    <!--暂时不需要-->
                    <!--<a class="canteen-order-alter" href="javascript:">修改订单</a>-->
                    <a class="canteen-order-delete" href="javascript:">删除订单</a>
                </div>
            </div>
        </section>
        <section class="canteen-order-list">
            <p class="canteen-order-list_header">
                <i class="canteen-order-list_logo"></i>
                <span class="canteen-order-list_logoname">林家食堂</span>
                <span class="canteen-order-list_status">待领取</span>
            </p>
            <div class="canteen-order-list_lists">
                <div class="canteen-order-list_list">
                    <img class="canteen-order-list_list_image" src="static/image/meal_example.png">
                    <span class="canteen-order-list_list_name">卤鸡腿</span>
                    <div class="canteen-order-list_list_rightbox">
                        <span class="canteen-order-list_list_price canteen-icon canteen-rmb-icon">20.00</span>
                        <span class="canteen-order-list_list_count canteen-icon canteen-close-icon">1</span>
                    </div>
                </div>
                <div class="canteen-order-list_list">
                    <img class="canteen-order-list_list_image" src="static/image/meal_example.png">
                    <span class="canteen-order-list_list_name">卤鸡腿</span>
                    <div class="canteen-order-list_list_rightbox">
                        <span class="canteen-order-list_list_price canteen-icon canteen-rmb-icon">20.00</span>
                        <span class="canteen-order-list_list_count canteen-icon canteen-close-icon">1</span>
                    </div>
                </div>
                <div class="canteen-order-list_list">
                    <img class="canteen-order-list_list_image" src="static/image/meal_example.png">
                    <span class="canteen-order-list_list_name">卤鸡腿</span>
                    <div class="canteen-order-list_list_rightbox">
                        <span class="canteen-order-list_list_price canteen-icon canteen-rmb-icon">20.00</span>
                        <span class="canteen-order-list_list_count canteen-icon canteen-close-icon">1</span>
                    </div>
                </div>
            </div>
            <p class="canteen-order-list_list_totalprice">
                <span class="canteen-order-list_list_totalprice_text">合计：</span>
                <span class="canteen-order-list_list_totalprice_number canteen-icon canteen-rmb-icon">40.00</span>
            </p>
            <div class="canteen-order-list_list_information_box">
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">领取人</span>
                    <span class="canteen-order-list_lsit_name">王杰</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">领取时间</span>
                    <span class="canteen-order-list_list_rtime">2017-09-01 16:00:00</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">部门</span>
                    <span class="canteen-order-list_lsit_department">文化部</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">备注</span>
                    <span class="canteen-order-list_lsit_mark">切好</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">联系号码</span>
                    <span class="canteen-order-list_lsit_tel">13888888888</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">创建时间</span>
                    <span class="canteen-order-list_lsit_stime">2017-08-30 18:00:00</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">订单编号</span>
                    <span class="canteen-order-list_lsit_number">20170930012545896545</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">截止时间</span>
                    <span class="canteen-order-list_lsit_etime">2020-09-19 18:00:00</span>
                </p>
            </div>
            <div class="canteen-order-list_operation">
                <div class="canteen-order-list_more">
                    查看更多
                    <i class="canteen-order-list_more_icon canteen-icon canteen-toright-icon"></i>
                </div>
                <div class="canteen-order-list_button">
                    <!--暂时不需要-->
                    <!--<a class="canteen-order-alter" href="javascript:">修改订单</a>-->
                    <a class="canteen-order-delete" href="javascript:">删除订单</a>
                </div>
            </div>
        </section>
        <section class="canteen-order-list">
            <p class="canteen-order-list_header">
                <i class="canteen-order-list_logo"></i>
                <span class="canteen-order-list_logoname">林家食堂</span>
                <span class="canteen-order-list_status">待领取</span>
            </p>
            <div class="canteen-order-list_lists">
                <div class="canteen-order-list_list">
                    <img class="canteen-order-list_list_image" src="static/image/meal_example.png">
                    <span class="canteen-order-list_list_name">卤鸡腿</span>
                    <div class="canteen-order-list_list_rightbox">
                        <span class="canteen-order-list_list_price canteen-icon canteen-rmb-icon">20.00</span>
                        <span class="canteen-order-list_list_count canteen-icon canteen-close-icon">1</span>
                    </div>
                </div>
                <div class="canteen-order-list_list">
                    <img class="canteen-order-list_list_image" src="static/image/meal_example.png">
                    <span class="canteen-order-list_list_name">卤鸡腿</span>
                    <div class="canteen-order-list_list_rightbox">
                        <span class="canteen-order-list_list_price canteen-icon canteen-rmb-icon">20.00</span>
                        <span class="canteen-order-list_list_count canteen-icon canteen-close-icon">1</span>
                    </div>
                </div>
                <div class="canteen-order-list_list">
                    <img class="canteen-order-list_list_image" src="static/image/meal_example.png">
                    <span class="canteen-order-list_list_name">卤鸡腿</span>
                    <div class="canteen-order-list_list_rightbox">
                        <span class="canteen-order-list_list_price canteen-icon canteen-rmb-icon">20.00</span>
                        <span class="canteen-order-list_list_count canteen-icon canteen-close-icon">1</span>
                    </div>
                </div>
            </div>
            <p class="canteen-order-list_list_totalprice">
                <span class="canteen-order-list_list_totalprice_text">合计：</span>
                <span class="canteen-order-list_list_totalprice_number canteen-icon canteen-rmb-icon">40.00</span>
            </p>
            <div class="canteen-order-list_list_information_box">
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">领取人</span>
                    <span class="canteen-order-list_lsit_name">王杰</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">领取时间</span>
                    <span class="canteen-order-list_list_rtime">2017-09-01 16:00:00</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">部门</span>
                    <span class="canteen-order-list_lsit_department">文化部</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">备注</span>
                    <span class="canteen-order-list_lsit_mark">切好</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">联系号码</span>
                    <span class="canteen-order-list_lsit_tel">13888888888</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">创建时间</span>
                    <span class="canteen-order-list_lsit_stime">2017-08-30 18:00:00</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">订单编号</span>
                    <span class="canteen-order-list_lsit_number">20170930012545896545</span>
                </p>
                <p class="canteen-order-list_list_information">
                    <span class="canteen-order-list_list_label">截止时间</span>
                    <span class="canteen-order-list_lsit_etime">2020-09-19 18:00:00</span>
                </p>
            </div>
            <div class="canteen-order-list_operation">
                <div class="canteen-order-list_more">
                    查看更多
                    <i class="canteen-order-list_more_icon canteen-icon canteen-toright-icon"></i>
                </div>
                <div class="canteen-order-list_button">
                    <!--暂时不需要-->
                    <!--<a class="canteen-order-alter" href="javascript:">修改订单</a>-->
                    <a class="canteen-order-delete" href="javascript:">删除订单</a>
                </div>
            </div>
        </section>
        
    </div>


    <footer class="canteen-order-list-footer">
        <a class="canteen-footer-goto-home" href="canteen_home.html">去下单</a>
    </footer>
</body>

</html>