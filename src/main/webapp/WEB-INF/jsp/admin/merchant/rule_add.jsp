<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
* {
	box-sizing: border-box;
}

ul, li, p, a {
	padding: 0;
	margin: 0;
	list-style: none;
	text-decoration: none;
}

.merchant-rule:after {
	content: '';
	display: block;
	clear: both;
}

.merchant-rule>ul, .merchant-rule>ul>li {
	float: left;
}

.merchant-rule>ul>li {
	width: 3.5em;
	height: 3.5em;
	line-height: 3.5em;
	border: 1px solid #CCCCCC;
	text-align: center;
	margin: 0 0.8em 0.8em 0;
	cursor: pointer;
}

.merchant-rule>ul>li.active {
	background-color: #044d22;
	color: #ffffff;
}

/*配送日期*/
.rule-date {
	position: relative;
}
/*配送时间点*/
.rule-time {
	position: relative;
}

.rule-time-choose {
	width: 31em;
}

/*提交*/
.merchant-rule-submit {
	margin-top: 2em;
}

.merchant-rule-submit>button {
	width: 5.6em;
	height: 2.8em;
	text-align: center;
	cursor: pointer;
	outline: none;
}
</style>
<div id="mechant-disabled-rule-add">
	<div class="page-header">
		<div class="header-title">
			<h1>添加规则配置</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form id="merchant-rule-add-form" class="bv-form form-horizontal validate-form" action="admin/merchant/rule/saveRule">
					<div class="form-group">
						<input id="rule-cron" type="hidden" name="rule" />
						<label class="col-lg-2 control-label">星期</label>
						<div class="col-lg-6 merchant-rule rule-date">
							<ul class="rule-date-choose">
				                <li class="rule-date-detail" data-value="2">一</li>
				                <li class="rule-date-detail" data-value="3">二</li>
				                <li class="rule-date-detail" data-value="4">三</li>
				                <li class="rule-date-detail" data-value="5">四</li>
				                <li class="rule-date-detail" data-value="6">五</li>
				                <li class="rule-date-detail" data-value="7">六</li>
				                <li class="rule-date-detail" data-value="1">七</li>
				            </ul>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">时间点</label>
						<div class="col-lg-5 rule-time merchant-rule">
				            <ul class="rule-time-choose">
					            <li class="rule-time-detail">8</li>
					            <li class="rule-time-detail">10</li>
					            <li class="rule-time-detail">11</li>
					            <li class="rule-time-detail">12</li>
					            <li class="rule-time-detail">13</li>
					            <li class="rule-time-detail">14</li>
					            <li class="rule-time-detail">15</li>
					            <li class="rule-time-detail">16</li>
					            <li class="rule-time-detail">17</li>
					            <li class="rule-time-detail">18</li>
					            <li class="rule-time-detail">19</li>
					            <li class="rule-time-detail">20</li>
					            <li class="rule-time-detail">21</li>
					            <li class="rule-time-detail">22</li>
				        	</ul> 
				        </div>
					</div>
					<div class="form-group">
			        	<div class="col-lg-offset-4 col-lg-3 merchant-rule-submit">
			        		<input class="btn btn-block btn-darkorange" id="rule-add-submit" type="button" value="提交"  />
				        </div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<script>
$(function(){
    seajs.use(['ajax', 'dialog', 'utils'], function(Ajax, Dialog, utils){
    	var ruleAdd = $("#mechant-disabled-rule-add");
    	
    	$('.merchant-rule>ul', ruleAdd).on("click",">li",function(){
            if($(this).hasClass("active")){
                $(this).removeClass("active")
            }else {
                $(this).addClass('active');
            }
        });
    
    
	    $("#rule-add-submit", ruleAdd).click(function(){
    		var $years = $('#years', ruleAdd);
   			var years = $years.tagsinput('items');
    		console.log(years);
    		
    		
        	var dateWeek = '';
        	var dateTime = '';
        	$(".rule-date-detail.active", ruleAdd).each(function(){
        		var week = $(this).data("value");
        		dateWeek += week + ",";
        	});
        	$(".rule-time-detail.active", ruleAdd).each(function(){
        		var time = $(this).text();
        		dateTime += time + ","; 
        	});
        	if(dateWeek.length <= 0){
    	    	dateWeek = "*";
        	}
        	if(dateTime.length <= 0){
    	    	dateTime = "*";
        	}
        	var ruleCron = "* * " + dateTime + " * * " + dateWeek + " *";
        	$("#rule-cron", ruleAdd).val(ruleCron);
        	console.log(ruleCron);
        	$("#merchant-rule-add-form", ruleAdd).submit();
        });
    });
    
})

</script>