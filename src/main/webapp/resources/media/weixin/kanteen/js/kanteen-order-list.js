'use strict';

var canteen_home_interaction = {
    init: function init() {
        this.searchBox();
        this.showMore();
        //this.orderNav();
    },


    /**
     * 事件绑定 ,均为冒泡
     * @param  {dom,event,fn}
     */
    eventBind: function eventBind(dom, event, fn) {
        try {
            dom.addEventListener(event, fn, false);
        } catch (error) {
            console.warn(error + "  maybe  param dom is underfind or wrong please check up your dom");
        }
    },


    /**
    * 搜索框交互
    */
    searchBox: function searchBox() {
        var label = document.getElementsByClassName('canteen-search-bar_label')[0];
        var box = document.getElementsByClassName('canteen-search-bar_box')[0];
        var input = document.getElementById('canteenSearch');
        var clear = document.getElementsByClassName('canteen-search-bar_clear')[0];

        // label 隐藏  box出现
        this.eventBind(label, "touchend", function () {
            label.style.display = "none";
            box.style.display = "block";
        });

        //box隐藏 label 出现
        this.eventBind(input, "blur", function () {
            label.style.display = "block";
            box.style.display = "none";
        });

        //清空input内容
        this.eventBind(clear, "touchend", function () {
            input.value = "";
        });
    },


    /**
     * 查看更多
     */
    showMore: function showMore() {
        var showClick = document.getElementsByClassName('canteen-order-list-wrap')[0];
        var information = document.getElementsByClassName('canteen-order-list_list_information_box')[0];
        var icon = document.getElementsByClassName('canteen-order-list_more_icon')[0];
        this.eventBind(showClick, "click", function (event) {
            var target = event.target;
            var isMore = target.classList.contains("canteen-order-list_more");
            var isMoreIcon = target.classList.contains("canteen-order-list_more_icon");
            if (isMore) {
                target.querySelector(".canteen-order-list_more_icon").classList.toggle("active");
                target.parentNode.previousElementSibling.classList.toggle("active");
            } else if (isMoreIcon) {
                target.classList.toggle("active");
                target.parentNode.parentNode.previousElementSibling.classList.toggle("active");
            }
        });
    },


    /**
     * 本周订单，历史订单
     */
    orderNav: function orderNav() {
        var order = document.getElementsByClassName('order-nav')[0];
        this.eventBind(order, "touchend", function (event) {
            var target = event.target;
            if (target.classList.contains("order-week")) {
                order.classList.remove("order-nav-total");
            } else {
                order.classList.add("order-nav-total");
            }
        });
    }
};

document.onreadystatechange = function () {
    if (document.readyState === "interactive") {
        canteen_home_interaction.init();
    }
};