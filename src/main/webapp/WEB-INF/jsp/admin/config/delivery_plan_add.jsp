<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
        body,html {
            padding:0;
            margin:0;
            width:100%;
            height:100%;
            font-size: 13px;
        }
        * {
            box-sizing: border-box;
        }
        ul,li,p,a{
            padding:0;
            margin:0;
            list-style: none;
            text-decoration: none;
        }
        .delivery-add-warp {
            width:100%;
            padding:3em 5em 0 2em;
        }
        .delivery-plan {
            padding:1em 0 0.2em 0.5em;
            border-bottom:1px dashed #cccccc;
        }
        .delivery-plan:after {
            content:'';
            display:block;
            clear:both;
        }
        .delivery-plan > .plan-title,
        .delivery-plan > .plan-year-box,
        .delivery-plan > ul,
        .delivery-plan > ul > li {
            float:left;
        }
        .delivery-plan > ul {
            display:none;
        }
        .delivery-plan > ul.active {
            display: block;
        }
        .delivery-plan > ul > li {
            width:2.5em;
            height:2.5em;
            line-height: 2.5em;
            border:1px solid #CCCCCC;
            text-align: center;
            margin:0 0.8em 0.8em 0;
            cursor: pointer;
        }
        .delivery-plan >ul >li.active {
            background-color:#044d22;
            color:#ffffff;
        }
        .plan-title {
            width:15%;
            text-align: left;
            display: inline-block;
        }
        /*计划年份*/
        .plan-year {
            position: relative;
        }
        .plan-year > .plan-title {
            position: absolute;
            margin-top:-0.5em;
            left:0.5em;
            top:50%;
        }
        .plan-year-box {
            width:36em;
            margin-left:15%;
        }
        .plan-year > .plan-year-add {
            width:2em;
            font-size: 2em;
            border-radius:0.5em;
            text-align: center;
            cursor: pointer;
            outline: none;
        }
        .plan-year-box >span.plan-year-detail {
            display:block;
            border:1px solid #CCCCCC;
            width:5em;
            height:2.5em;
            line-height: 2.5em;
            text-align: center;
            margin:0 1.5em 0.8em 0;
            cursor: pointer;
            float:left;
        }
        .plan-year-box >span.plan-year-detail.active {
            background-color:#044d22;
            color:#ffffff;
        }
        /*计划月份*/
        .plan-month {
            line-height: 6.6em;
        }
        .plan-month-choose {
            width:20em;
        }
        /*配送日期*/
        .plan-date {
            line-height: 3.2em;
        }
        /*配送时间点*/
        .plan-time {
            line-height:6.6em;
        }
        .plan-time-choose {
            width:24em;
        }
        /*提交*/
        .delivery-info-submit{
            float:right;
            margin-top:2em;
        }
        .delivery-info-submit > button {
            width: 4em;
            font-size: 1.5em;
            border-radius: 0.5em;
            text-align: center;
            cursor: pointer;
            outline: none;
        }
    </style>

<div class="delivery-add-warp">
        <div class="plan-year delivery-plan">
            <p class="plan-title">计划年份</p>
            <div class="plan-year-box">
                <span class="plan-year-detail active" data-year="2017">2017</span>
                <span class="plan-year-detail" data-year="2018">2018</span>
                <span class="plan-year-detail" data-year="2019">2019</span>
            </div>
            <button class="plan-year-add">+</button>
        </div>
        <div class="plan-month delivery-plan">
            <p class="plan-title">计划月份</p>
            <ul class="plan-month-choose" data-year="2017">
                <li class="plan-month-detail">1</li>
                <li class="plan-month-detail">2</li>
                <li class="plan-month-detail">3</li>
                <li class="plan-month-detail">4</li>
                <li class="plan-month-detail">5</li>
                <li class="plan-month-detail">6</li>
                <li class="plan-month-detail">7</li>
                <li class="plan-month-detail">8</li>
                <li class="plan-month-detail">9</li>
                <li class="plan-month-detail">10</li>
                <li class="plan-month-detail">11</li>
                <li class="plan-month-detail">12</li>
            </ul>
            <ul class="plan-month-choose" data-year="2018">
                <li class="plan-month-detail">1</li>
                <li class="plan-month-detail">2</li>
                <li class="plan-month-detail">3</li>
                <li class="plan-month-detail">4</li>
                <li class="plan-month-detail">5</li>
                <li class="plan-month-detail">6</li>
                <li class="plan-month-detail">7</li>
                <li class="plan-month-detail">8</li>
                <li class="plan-month-detail">9</li>
                <li class="plan-month-detail">10</li>
                <li class="plan-month-detail">11</li>
                <li class="plan-month-detail">12</li>
            </ul>
            <ul class="plan-month-choose" data-year="2019">
                <li class="plan-month-detail">1</li>
                <li class="plan-month-detail">2</li>
                <li class="plan-month-detail">3</li>
                <li class="plan-month-detail">4</li>
                <li class="plan-month-detail">5</li>
                <li class="plan-month-detail">6</li>
                <li class="plan-month-detail">7</li>
                <li class="plan-month-detail">8</li>
                <li class="plan-month-detail">9</li>
                <li class="plan-month-detail">10</li>
                <li class="plan-month-detail">11</li>
                <li class="plan-month-detail">12</li>
            </ul>
        </div>
        <div class="plan-date delivery-plan">
            <p class="plan-title">配送日期</p>
            <ul class="plan-date-choose" data-year="2017">
                <li class="plan-date-detail">一</li>
                <li class="plan-date-detail">二</li>
                <li class="plan-date-detail">三</li>
                <li class="plan-date-detail">四</li>
                <li class="plan-date-detail">五</li>
                <li class="plan-date-detail">六</li>
                <li class="plan-date-detail">七</li>
            </ul>
            <ul class="plan-date-choose" data-year="2018">
                <li class="plan-date-detail">一</li>
                <li class="plan-date-detail">二</li>
                <li class="plan-date-detail">三</li>
                <li class="plan-date-detail">四</li>
                <li class="plan-date-detail">五</li>
                <li class="plan-date-detail">六</li>
                <li class="plan-date-detail">七</li>
            </ul>
            <ul class="plan-date-choose" data-year="2019">
                <li class="plan-date-detail">一</li>
                <li class="plan-date-detail">二</li>
                <li class="plan-date-detail">三</li>
                <li class="plan-date-detail">四</li>
                <li class="plan-date-detail">五</li>
                <li class="plan-date-detail">六</li>
                <li class="plan-date-detail">七</li>
            </ul>
        </div>
        <div class="plan-time delivery-plan">
            <p class="plan-title">配送时间点</p>
            <ul class="plan-time-choose" data-year="2017">
            <li class="plan-time-detail">8</li>
            <li class="plan-time-detail">10</li>
            <li class="plan-time-detail">11</li>
            <li class="plan-time-detail">12</li>
            <li class="plan-time-detail">13</li>
            <li class="plan-time-detail">14</li>
            <li class="plan-time-detail">15</li>
            <li class="plan-time-detail">16</li>
            <li class="plan-time-detail">17</li>
            <li class="plan-time-detail">18</li>
            <li class="plan-time-detail">19</li>
            <li class="plan-time-detail">20</li>
            <li class="plan-time-detail">21</li>
            <li class="plan-time-detail">22</li>
        </ul>
            <ul class="plan-time-choose" data-year="2018">
                <li class="plan-time-detail">8</li>
                <li class="plan-time-detail">10</li>
                <li class="plan-time-detail">11</li>
                <li class="plan-time-detail">12</li>
                <li class="plan-time-detail">13</li>
                <li class="plan-time-detail">14</li>
                <li class="plan-time-detail">15</li>
                <li class="plan-time-detail">16</li>
                <li class="plan-time-detail">17</li>
                <li class="plan-time-detail">18</li>
                <li class="plan-time-detail">19</li>
                <li class="plan-time-detail">20</li>
                <li class="plan-time-detail">21</li>
                <li class="plan-time-detail">22</li>
            </ul>
            <ul class="plan-time-choose" data-year="2019">
                <li class="plan-time-detail">8</li>
                <li class="plan-time-detail">10</li>
                <li class="plan-time-detail">11</li>
                <li class="plan-time-detail">12</li>
                <li class="plan-time-detail">13</li>
                <li class="plan-time-detail">14</li>
                <li class="plan-time-detail">15</li>
                <li class="plan-time-detail">16</li>
                <li class="plan-time-detail">17</li>
                <li class="plan-time-detail">18</li>
                <li class="plan-time-detail">19</li>
                <li class="plan-time-detail">20</li>
                <li class="plan-time-detail">21</li>
                <li class="plan-time-detail">22</li>
            </ul>
        </div>
        <div class="delivery-info-submit">
            <button>提交</button>
        </div>
    </div><script>
$(function(){
    addYear();
    chooseDate();
    clickYear();
    changeYear();
    //    增加计划年份
    function addYear(){
        $('.plan-year-add').on("click",function(){
            var html = '';
            var year = 0;
            year = parseFloat($('.plan-year-detail:last').text())+1;
            html = "<span class='plan-year-detail active' data-year='"+year+"'>"+year+"</span>";
            $('.plan-year-detail.active').removeClass("active");
            $('.plan-year-box').append(html);
            addDateDom(year);
            changeYear();
        })
    }

// 点击计划年份
    function clickYear(){
        $('.plan-year-box').on("click",".plan-year-detail",function(){
            // 选中的增加背景色，其他的去掉背景色
            $('.plan-year-detail.active').removeClass("active");
            $(this).addClass("active");
            changeYear();
        })
    }

// 选中计划月份 配送日期  配送时间点
    function chooseDate(){
        $('.delivery-plan>ul').on("click",">li",function(){
            $(this).addClass('active');
        })
    }

// 选中的年份 关联的日期显示，未选中的 关联的日期隐藏
    function changeYear(){
        var YearKey =  $(".plan-year-detail.active").data("year");
        $(".delivery-plan>ul[data-year="+YearKey+"]")
                .addClass("active")
                .siblings('ul').removeClass("active");
    }

// 新建 新建年份对于的日期 dom
    function addDateDom(time){
        var monthHtml = '';
        var dateHtml  = '';
        var timeHtml  = '';
        var dataTime = time;
        monthHtml = "<ul class='plan-month-choose' data-year='"+dataTime+"'>"+
        "<li class='plan-month-detail'>1</li>"+
        "<li class='plan-month-detail'>2</li>"+
        "<li class='plan-month-detail'>3</li>"+
        "<li class='plan-month-detail'>4</li>"+
        "<li class='plan-month-detail'>5</li>"+
        "<li class='plan-month-detail'>6</li>"+
        "<li class='plan-month-detail'>7</li>"+
        "<li class='plan-month-detail'>8</li>"+
        "<li class='plan-month-detail'>9</li>"+
        "<li class='plan-month-detail'>10</li>"+
        "<li class='plan-month-detail'>11</li>"+
        "<li class='plan-month-detail'>12</li>"+
        "</ul>";
        dateHtml = "<ul class='plan-date-choose' data-year='"+dataTime+"'>"+
        "<li class='plan-date-detail'>一</li>"+
        "<li class='plan-date-detail'>二</li>"+
        "<li class='plan-date-detail'>三</li>"+
        "<li class='plan-date-detail'>四</li>"+
        "<li class='plan-date-detail'>五</li>"+
        "<li class='plan-date-detail'>六</li>"+
        "<li class='plan-date-detail'>七</li>"+
        "</ul>";

        timeHtml = "<ul class='plan-time-choose'' data-year='"+dataTime+"'>"+
        "<li class='plan-time-detail'>8</li>"+
        "<li class='plan-time-detail'>10</li>"+
        "<li class='plan-time-detail'>11</li>"+
        "<li class='plan-time-detail'>12</li>"+
        "<li class='plan-time-detail'>13</li>"+
        "<li class='plan-time-detail'>14</li>"+
        "<li class='plan-time-detail'>15</li>"+
        "<li class='plan-time-detail'>16</li>"+
        "<li class='plan-time-detail'>17</li>"+
        "<li class='plan-time-detail'>18</li>"+
        "<li class='plan-time-detail'>19</li>"+
        "<li class='plan-time-detail'>20</li>"+
        "<li class='plan-time-detail'>21</li>"+
        "<li class='plan-time-detail'>22</li>"+
        "</ul>";

        $(".plan-month").append(monthHtml);
        $(".plan-date").append(dateHtml);
        $(".plan-time").append(timeHtml);
        // 动态生成的  需要重新绑定事件
        chooseDate();
    }

})

</script>