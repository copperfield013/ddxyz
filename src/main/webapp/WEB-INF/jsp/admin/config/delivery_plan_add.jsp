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
            margin-left: 15%;
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
        .plan-location, .plan-max-count, .plan-limit-minutes, .plan-start-date, .plan-end-date{
        	position:relative;
        }
        .plan-location #location {
        	margin-left:15%;
        }
        .plan-max-count input[type="text"]{
        	margin-left:15%;
        }
        .input-group{
        	margin-left:15%;
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
   	<div id="plan-add" class="delivery-add-warp">
	    <form id="add-plan-form" action="admin/config/plan/plan-doAdd">
	        <div class="plan-year delivery-plan">
	            <p class="plan-title">计划年份</p>
	            <div class="plan-year-box">
	                <span class="plan-year-detail active" data-year="${year }">${year }</span>
	                <input type="text" autofocus="autofocus" class="inputYear"/>
	            </div>
	            <button type="button" class="plan-year-add">+</button>
	            <button type="button" class="plan-year-remove">-</button>
	        </div>
	        <div class="plan-month delivery-plan">
	            <p class="plan-title">计划月份</p>
	            <ul class="plan-month-choose">
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
	            <ul class="plan-date-choose">
	                <li class="plan-date-detail" data-value="1">一</li>
	                <li class="plan-date-detail" data-value="2">二</li>
	                <li class="plan-date-detail" data-value="3">三</li>
	                <li class="plan-date-detail" data-value="4">四</li>
	                <li class="plan-date-detail" data-value="5">五</li>
	                <li class="plan-date-detail" data-value="6">六</li>
	                <li class="plan-date-detail" data-value="7">七</li>
	            </ul>
	        </div>
	        <div class="plan-time delivery-plan">
	            <p class="plan-title">配送时间点</p>
	            <ul class="plan-time-choose">
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
	        <input type="hidden" id="period" name="period">
	        <div class="plan-location delivery-plan">
	        	<p class="plan-title">配送地点</p>
	        	<select id="location" name="locationId">
	        		<c:forEach items="${locationList }" var="location">
	        			<option value="${location.id }">${location.name }</option>
	        		</c:forEach>
				</select>
	        </div>
	        <div class="plan-max-count delivery-plan">
	        	<p class="plan-title">配送最大数</p>
	        	<input type="text" id="max-count" name="maxCount"/>&nbsp;杯
	        </div>
	        <div class="plan-limit-minutes delivery-plan">
	        	<p class="plan-title">提前结束时间</p>
	        	<div class="input-group">
	               <input type="text" id="lead-minutes" name="leadMinutes"/>&nbsp;分钟
	           </div>
	        </div>
	        <div class="plan-start-date delivery-plan">
	        	<p class="plan-title">周期开始时间</p>
	            <div class="input-group">
	               <input type="text" class="form-control" id="start-date" name="startDate" readonly="readonly" 
	               	css-width="25em"  css-cursor="text" data-date-format="yyyy-mm-dd"/>
	           </div>
	        </div>
	        <div class="plan-end-date delivery-plan">
	        	<p class="plan-title">周期结束时间</p>
	        	<div class="input-group">
	               <input type="text" class="form-control" id="end-date" name="endDate" readonly="readonly" 
	               	css-width="25em"  css-cursor="text" data-date-format="yyyy-mm-dd"/>
	           </div>
	        </div>
	        <div class="delivery-info-submit">
	            <button id="plan-add-submit" type="button">提交</button>
	        </div>
	    </form>
    </div>
	
<script>
$(function(){
    seajs.use(['ajax', 'dialog'], function(Ajax, Dialog){
    	
    	var planAdd = $("#plan-add");
        addYear();
        $("#start-date", planAdd).datepicker();
        $("#end-date", planAdd).datepicker();
        
    	$("#plan-add-submit", planAdd).click(function(){
        	var dateYear = '';
        	var dateMonth = '';
        	var dateWeek = '';
        	var dateTime = '';
        	$(".plan-year-detail", planAdd).each(function(){
        		var year = $(this).data("year");
        		dateYear= dateYear + year +",";
        	});
        	$(".plan-month-detail.active", planAdd).each(function(){
        		var month = $(this).text();
        		dateMonth += month + ","
        	});
        	$(".plan-date-detail.active", planAdd).each(function(){
        		var week = $(this).data("value");
        		dateWeek += week + ",";
        	});
        	$(".plan-time-detail.active", planAdd).each(function(){
        		var time = $(this).text();
        		dateTime += time + ","; 
        	});
        	if(dateYear.length > 0){
    	    	dateYear = "Y" + dateYear.substring(0, dateYear.length-1) + "。";
    	    }else{
    	    	Dialog.notice("请填写年份！","warning");
    	    	return;
    	    }
        	if(dateMonth.length > 0){
	        	dateMonth = "M" + dateMonth.substring(0, dateMonth.length-1) + "。";
        	}else{
        		Dialog.notice("请选择月份！","warning");
        		return;
        	}
        	if(dateWeek.length > 0){
    	    	dateWeek = "W" + dateWeek.substring(0, dateWeek.length-1) + "。";
        	}
        	if(dateTime.length > 0){
    	    	dateTime = "T" + dateTime.substring(0, dateTime.length-1) + "。";
        	}
        	period = dateYear + dateMonth + dateWeek + dateTime;
        	$("#period", planAdd).val(period);
        	console.log(period);
        	$("#add-plan-form", planAdd).submit();
        });
    	
    	$('.plan-year-remove', planAdd).on("click",function(){
//          选中的年份删除, 选中状态赋给后一个，如果没有后一个，则赋给前一个，都没有则不用管
	          var self =$('.plan-year-detail.active', planAdd);
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
      	});
    	
    	$('.delivery-plan>ul', planAdd).on("click",">li",function(){
            if($(this).hasClass("active")){
                $(this).removeClass("active")
            }else {
                $(this).addClass('active');
            }
        });
    
	    $('.plan-year-box', planAdd).on("click",".plan-year-detail",function(){
	        // 选中的增加背景色，其他的去掉背景色
	        $('.plan-year-detail.active', planAdd).removeClass("active");
	        $(this).addClass("active");
	    });
    
	 // 判断是否是数字 返回true 和false
	    function isNumber(value){
	        return /^[(-?\d+\.\d+)|(-?\d+)|(-?\.\d+)]+$/.test(value + '');
	    }

		//判断是否是重复年份，是的话返回true 否的话返回false
	    function yearRepeat(num){
	        var length = $(".plan-year-detail", planAdd).length;
	        for(var i=0; i<length; i++){
	            if( num == parseFloat($('.plan-year-detail', planAdd)[i].innerText)){
	                return true;
	            }
	    	}
        	return false;
    	}

		//    点击加号
	    function plusClick(){
	        $('.plan-year-add', planAdd).on("click",function(){
	            $('.inputYear', planAdd).addClass("active").focus();
	        })
	    }
		// input 失去焦点(enter) 添加年份
	    function addYear(){
	        var input = $('.inputYear', planAdd);
	        function add(){
	            input.removeClass("active");
	            var year = parseFloat(input.val());
	            var html = '';
	            var isNum=isNumber(year);
				// 如果不是数字，无法输入 ，如果是重复的数字，提示不能重复
	            if(!isNum){
	                input.val('');
	            }else if(yearRepeat(year)){
	                alert("年份重复")
	                input.val('');
	            }else {
	                html = "<span class='plan-year-detail active' data-year='"+year+"'>"+year+"</span>";
	                $('.plan-year-detail.active', planAdd).removeClass("active");
	                input.before(html);
	                input.val('');
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

    });
    
})

</script>