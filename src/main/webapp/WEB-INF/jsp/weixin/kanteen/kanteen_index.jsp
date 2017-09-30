<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
    
    <title>林家食堂</title>
	<jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include-kanteen.jsp"></jsp:include>
    <link rel="stylesheet" href="media/weixin/kanteen/css/kanteen-index.css">
    <script src="media/weixin/kanteen/js/kanteen-index.js"></script>
</head>

<body>
    <header class="page-header">
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

        <!--展示信息-->
        <section class="canteen-information-area">
            <div class="canteen-information_basic">
                <!--<img id="canteenLogo" src="static/image/canteen_logo.png" alt="林家食堂">-->
                <div class="canteen-information_basic_detail">
                    <p class="canteen-information_bookingtime">预订时间：2017.8.28 00:00~2017.8.31 18:00</p>
                    <p class="canteen-information_collectiontime">领取时间：2017.8.28 00:00~2017.8.31 18:00</p>
                    <p class="canteen-information_collectionplace">领取地点：林家食堂</p>
                </div>
            </div>
            <div class="canteen-information_announcement">
                <span class="canteen-information_announcement_text canteen-icon canteen-announcement-icon">公告内容：</span>
                <!--动画部分-->
                <div class="canteen-information_announcement_detail">
                    <ul class="canteen-information_announcement_carousel">
                        <li>周一台风，休息两天</li>
                        <li>周五旅游，休息七天</li>
                        <li>禽流感影响，餐厅暂停售卖禽类</li>
                        <li>禽流感影响，餐厅暂停售卖禽类禽流感影响，餐厅暂停售卖禽类</li>
                    </ul>
                </div>
            </div>
        </section>

    </header>

    <!--主体部分包裹层-->
    <div class="canteen-main-wrap">
        <!--餐品选择导航-->
        <!--data-key是标识，需要和对应的展示列表 data-uid 的值一样，可以换为后台已有标识字段 -->
        <nav class="canteen-meal-nav">
            <a data-key="canteen100" href="javascript:" class="canteen-meal-nav_button canteen-icon canteen-hot-sell-icon active">热销</a>
            <a data-key="canteen001" href="javascript:" class="canteen-meal-nav_button canteen-icon canteen-pickled-taste-icon">卤味</a>
        </nav>
        <!--餐品列表部分-->
        <!--data-uid是标识，需要和对应的nav模块菜单中的 data-key 的值一样，可以换为后台已有标识字段  :String -->
        <!--data-prouid是商品标识，可替换为后台已有字段，具有唯一性与商品一一对应 :String -->
        <div data-uid="canteen100" class="canteen-meal-list active">
            <header class="canteen-meal-list_title canteen-icon canteen-title-bar-icon">
                热销
            </header>

            <div data-prouid="10001" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">卤鸡腿</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">10</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>
            <div data-prouid="10002" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">炒三丝</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">11</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>
            <div data-prouid="10003" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">香蕉拔丝</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">12</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>
            <div data-prouid="10004" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">回锅肉</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">13</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>
            <div data-prouid="10005" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">剁椒鱼头</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">10</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>
            <div data-prouid="10006" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">农家豆腐</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">10</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>
            <div data-prouid="10007" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">爆炒螺蛳</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">10</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>
            <div data-prouid="10008" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">油爆花蛤</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">10</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>
            <div data-prouid="10009" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">百威</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">10</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>
            <div data-prouid="100010" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">干锅花菜</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">10</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>


        </div>
        <div data-uid="canteen001" class="canteen-meal-list">
            <header class="canteen-meal-list_title canteen-icon canteen-title-bar-icon">
                卤味
            </header>

            <div data-prouid="10001" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">卤鸡腿</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">10</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>
            <div data-prouid="00102" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">鱼香肉丝</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">11</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>
            <div data-prouid="00103" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">红烧肉</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">12</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>
            <div data-prouid="00104" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">外婆菜炒肉</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">13</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>
            <div data-prouid="00105" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">琵琶页</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">10</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>
            <div data-prouid="00106" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">吊龙</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">10</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>
            <div data-prouid="00107" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">哇哈哈</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">10</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>
            <div data-prouid="00108" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">水蒸蛋</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">10</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>
            <div data-prouid="00109" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">北极大虾</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">10</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>
            <div data-prouid="001010" class="canteen-meal-list_menu">
                <img class="canteen-meal-list_image" src="media/weixin/kanteen/static/image/meal_example.png" alt="卤鸭爪">
                <div class="canteen-meal-list_menu_detail">
                    <p class="canteen-meal-list_menu_name">土豆片</p>
                    <p class="canteen-meal-list_menu_sales">
                        <span>余量266</span>
                        <span>已售88</span>
                    </p>
                    <p class="canteen-meal-list_menu_price canteen-icon canteen-rmb-icon">
                        <span class="">10</span>
                    </p>
                    <div class="canteen-meal-list_menu_button">
                        <a href="javascript:" class="canteen-meal-list_menu_minus canteen-icon canteen-minus-icon"></a>
                        <span class="canteen-meal-list_menu_count"></span>
                        <a href="javascript:" class="canteen-meal-list_menu_add canteen-icon canteen-add-icon"></a>
                    </div>
                </div>
            </div>


        </div>
    </div>




    <!--底部-->
    <footer class="page-footer shopping-car_empty">
        <div class="shopping-car canteen-icon canteen-shopping-car-icon">
            <span class="shopping-car-totalprice canteen-icon canteen-rmb-icon">购物车是空的</span>
            <i class="shopping-car-totalcount"></i>
        </div>
        <a href="canteen_order.html" class="settlement">选好了</a>
    </footer>


    <!--购物车展示模块，配合遮罩层-->
    <section class="shopping-car-show">
        <div class="shopping-car-show_title">
            <span class="shopping-car-show_title_text">购物车</span>
            <span class="shopping-car-show_title_empty canteen-icon canteen-empty-icon">清空</span>
        </div>
        <div class="shopping-car-show_list_wrap">

            <!--<div data-orderuid="10001" class="shopping-car-show_list">
                <span class="shopping-car-show_list_name">卤鸡腿</span>
                <span class="shopping-car-show_list_price canteen-icon canteen-rmb-icon">
                    10
                </span>
                <div class="shopping-car-show_list_button">
                    <a href="javascript:" class="shopping-car-show_list_minus canteen-icon canteen-minus-icon"></a>
                    <span class="shopping-car-show_list_count"></span>
                    <a href="javascript:" class="shopping-car-show_list_add canteen-icon canteen-add-icon"></a>
                </div>
            </div>-->
        </div>

    </section>

    <!--信息展示模块，配合遮罩层-->
    <div class="canteen-information-alert-component">
        <section class="canteen-information-alert">
        <div class="canteen-information-alert_scroll">
            <div class="canteen-information-alert_banner">
                林家食堂
            </div>
            <div class="canteen-information-alert_basic">
                <p class="canteen-information-alert_bookingtime">预订时间：2017.8.28 00:00~2017.8.31 18:00</p>
                <p class="canteen-information-alert_collectiontime">领取时间：2017.8.28 00:00~2017.8.31 18:00</p>
                <p class="canteen-information-alert_collectionplace">领取地点：林家食堂</p>
            </div>
            <div class="canteen-information-alert_announcement">
                <span class="canteen-information-alert_announcement-text">商家公告</span>
                <p class="canteen-information-alert_announcement_list">1.杀了发了时间啊乱收费骄傲了双方均按时了附件爱上了副教授</p>
                <p class="canteen-information-alert_announcement_list">1.杀了发了时间啊乱收费骄傲了双方均按时了附件爱上了副教授</p>
                <p class="canteen-information-alert_announcement_list">1.杀了发了时间啊乱收费骄傲了双方均按时了附件爱上了副教授</p>
                <p class="canteen-information-alert_announcement_list">1.杀了发了时间啊乱收费骄傲了双方均按时了附件爱上了副教授</p>
                <p class="canteen-information-alert_announcement_list">1.杀了发了时间啊乱收费骄傲了双方均按时了附件爱上了副教授</p>
            </div>
        </div>
    </section>
    <i class="canteen-information-alert_close canteen-icon canteen-close-icon"></i>
    </div>
    


    <!--遮罩-->
    <div class="canteen-shadow"></div>
    <script type="text/javascript">
    	$(function(){
    		seajs.use(['ajax'], function(Ajax){
	    		Kanteen.bindChange(function(cartData){
	    			Ajax.ajax('weixin/kanteen/uploadCart', 
	    					$.extend(cartData, {merchantId: 1}), function(data){
	    				if(data.status != 'suc'){
	    					console.error('系统错误');
	    				}
	    			})
	    		});
    		});
    	});
    </script>
</body>

</html>