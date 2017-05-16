<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
 <style>
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
             min-height:2em;
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
            margin-left: 15%;
        }
        .delivery-plan > ul.active {
            display: block;
        }
        .delivery-plan > ul > li {
            width:3.5em;
            height:3.5em;
            line-height: 3.5em;
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
            position: absolute;
            margin-top: -0.5em;
            left: 0.5em;
            top: 50%;
        }
        /*计划年份*/
        .plan-year {
            position: relative;
        }
        .plan-year-box {
            width:31em;
            margin-left:15%;
            min-height:1px;
        }
        .plan-year > .plan-year-add,
        .plan-year > .plan-year-remove {
            width:1.5em;
            text-align: center;
            cursor: pointer;
            outline: none;
            font-size: 2em;
            margin-right:0.5em;
        }
        .plan-year-box >span.plan-year-detail {
            display:block;
            border:1px solid #CCCCCC;
            width:4.7em;
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
        .inputYear {
            border: 1px solid #CCCCCC;
            width: 4.7em;
            height: 2.5em;
            line-height: 2.5em;
            text-align: center;
            margin: 0 0 0.8em 0;
            float: left;
            display:none;
        }
        .inputYear.active {
            display:block;
        }
        /*计划月份*/
        .plan-month {
            position:relative;
        }
        .plan-month-choose {
            width:26em;
        }
        /*配送日期*/
        .plan-date {
            position:relative;
        }
        /*配送时间点*/
        .plan-time {
            position:relative;
        }
        .plan-time-choose {
            width:31em;
        }
        /*提交*/
        .delivery-info-submit{
            margin-top:2em;
            margin-left:15%;
        }
        .delivery-info-submit > button {
            width:5.6em;
            height:2.8em;
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
                <input type="text" autofocus="autofocus" class="inputYear"/>
            </div>
            <button class="plan-year-add">+</button>
            <button class="plan-year-remove">-</button>
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
    </div>
<script>
$(function(){
    plusClick();
    addYear();
    chooseDate();
    clickYear();
    changeYear();
    removeYear();

// 判断是否是数字 返回true 和false
    function isNumber(value){
        return /^[(-?\d+\.\d+)|(-?\d+)|(-?\.\d+)]+$/.test(value + '');
    }

//判断是否是重复年份，是的话返回true 否的话返回false
    function yearRepeat(num){
        var length = $(".plan-year-detail").length;
        for(var i=0; i<length; i++){
            if( num == parseFloat($('.plan-year-detail')[i].innerText)){
                return true;
            }
    }
        return false;
    }

//  解除具体日期选择的绑定
    function offDateClick(){
        $('.delivery-plan>ul').off("click",">li")
    }
//    点击加号
    function plusClick(){
        $('.plan-year-add').on("click",function(){
            $('.inputYear').addClass("active").focus();
        })
    }
// input 失去焦点(enter) 添加年份
    function addYear(){
        var input = $('.inputYear');
        function add(){
            input.removeClass("active");
            var year = parseFloat(input.val());
            var html = '';
            var isNum=isNumber(year);
//            如果不是数字，无法输入 ，如果是重复的数字，提示不能重复
            if(!isNum){
                input.val('');
            }else if(yearRepeat(year)){
                alert("年份重复")
                input.val('');
            }else {
                html = "<span class='plan-year-detail active' data-year='"+year+"'>"+year+"</span>";
                $('.plan-year-detail.active').removeClass("active");
                input.before(html);
                input.val('');
                addDateDom(year);
                changeYear();
            }
        }
        input.on("blur",function(){
            add();
        });
        input.on("keypress",function(event){
            if(event.keyCode == "13"){
                add();
            }
        })
    };

// 点击计划年份
    function clickYear(){
        $('.plan-year-box').on("click",".plan-year-detail",function(){
            // 选中的增加背景色，其他的去掉背景色
            $('.plan-year-detail.active').removeClass("active");
            $(this).addClass("active");
            changeYear();
        })
    }

// 选中计划月份 配送日期  配送时间点,以及取消
    function chooseDate(){
        $('.delivery-plan>ul').on("click",">li",function(){
            console.log("chooseDate work");
            if($(this).hasClass("active")){
                $(this).removeClass("active")
            }else {
                $(this).addClass('active');
            }
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
        offDateClick();
        chooseDate();
    }

//删除 删除年份相应的日期dom
    function removeDateDom(time){
        $("ul[data-year="+time+"]").remove();
        changeYear();
    }

//  删除选中年份
    function removeYear(){
        $('.plan-year-remove').on("click",function(){
//            选中的年份删除, 选中状态赋给后一个，如果没有后一个，则赋给前一个，都没有则不用管
            var self =$('.plan-year-detail.active');
            var year = parseFloat(self.data('year'));
            var next = self.next();
            var before = self.prev();
            if(before.length == 1){
                before.addClass("active");
                self.remove();
            }else if( next.length ==1 ){
                next.addClass("active")
                self.remove();
            }else{
                self.remove();
            }
            removeDateDom(year);
        })
    }

})

</script>