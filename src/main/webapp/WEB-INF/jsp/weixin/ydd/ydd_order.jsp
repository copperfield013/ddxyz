<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <title>下单</title>
    <jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
    <link rel="stylesheet" href="${basePath }media/weixin/main/css/templete.css">
    <link rel="stylesheet" href="${basePath }media/weixin/main/css/common.css">
    <link rel="stylesheet" href="${basePath }media/weixin/main/css/buy.css">
    <style type="text/css">
    	.delivery-location{
    		display: none;
    	}
    	.cview{
    		display: inherit !important;
    	}
    	.tit-desc{
    		font-size: 0.5em;
		    color: #f00;
		    margin-left: 5%;
    	}
    	.tea-addition-type-wrapper{
    		display: none;
    	}
    	.addition-type-wrapper{
    		display: none;
    	}
    </style>
    <%-- <script src="${basePath }media/weixin/main/js/jquery-3.1.1.min.js"></script> --%>
    <script src="${basePath }media/weixin/main/js/ix.js"></script>
    <script src="${basePath }media/weixin/plugins/xcheck/xcheck.js"></script>
    <script src="${basePath }media/weixin/plugins/xnumber/xnumber.js"></script>
    <script>
    $(function() {
        ix.init();
    });
    </script>
</head>
<body>
<form class="validate" action="index.html" method="post">
<main class="form">
    <!-- 配送信息 -->
    <div class="send-info">
        <h4>配送信息</h4>
        <dl class="select">
            <dt>时间档</dt>
            <dd><select id="timePoint">
            	<option value="">请选择</option>
            	<c:forEach items="${deliveryMap }" var="item">
            		<option data-key="${item.key.key }" value="${item.key.hour }">${item.key.hour }点</option>
            	</c:forEach>
            </select></dd>
        </dl>
        <dl class="select">
            <dt>配送地址</dt>
            <dd>
            	<c:forEach items="${deliveryMap }" var="item">
            		<select class="delivery-location" data-key="${item.key.key }" >
            			<option value="">请选择</option>
            			<c:forEach items="${item.value }" var="delivery">
            				<option value="${delivery.id }">${delivery.locationName }</option>
            			</c:forEach>
	            	</select>
            	</c:forEach>
            </dd>
        </dl>
        <dl class="text">
            <dt>联系号码</dt>
            <dd>
                <input id="telphone" type="text" name="telphone" placeholder="请输入">
            </dd>
        </dl>
    </div>
    <!-- 奶茶详情 -->
    <div class="good-info">
        <h4>奶茶详情<span class="tit-desc">当前可供余量：<span id="cup-remain">50</span>杯</span></h4>
        <dl class="select">
            <dt>奶茶种类</dt>
            <dd><select id="drink-type">
                <option value="">请选择</option>
                <c:forEach items="${drinkTypes }" var="dType">
	                <option data-price="${dType.basePrice }" value="${dType.id }">${dType.view }</option>
                </c:forEach>
            </select></dd>
        </dl>
        <dl class="number">
            <dt>数量</dt>
            <dd>
                <input id="cupCount" type="number" value="1">
            </dd>
        </dl>
        <div class="box">
            <h5>奶茶详情<pre>每行单选</pre></h5>
            <c:forEach items="${teaAdditionMap }" var="teaAdditionTypeEntry">
	            <p data-key="${teaAdditionTypeEntry.key }" class="tea-addition-type-wrapper">
	            	<c:forEach items="${teaAdditionTypeEntry.value }" var="teaAdditionType">
		                <input type="radio" name="teaAdditionType" id="teaAdditionType_${teaAdditionType.id }" value="${teaAdditionType.id }">
		                <label for="teaAdditionType_${teaAdditionType.id }">${teaAdditionType.name }</label>
	            	</c:forEach>
	            </p>
            </c:forEach>
            <p>
            	<c:forEach items="${cupSizeMap }" var="cupSize">
	                <input type="radio" id="size_${cupSize.key }" name="cupSize" value="${cupSize.key }" >
	                <label for="size_${cupSize.key }">${cupSize.value }</label>
            	</c:forEach>
            </p>
            <p>
            	<c:forEach items="${sweetnessMap }" var="sweetness">
	                <input type="radio" id="sweetness_${sweetness.key }" name="sweetness" value="${sweetness.key }" >
	                <label for="sweetness_${sweetness.key }">${sweetness.value }</label>
            	</c:forEach>
            </p>
            <p>
            	<c:forEach items="${heatMap }" var="heat">
	                <input type="radio" id="heat_${heat.key }" name="heat" value="${heat.key }" >
	                <label for="heat_${heat.key }">${heat.value }</label>
            	</c:forEach>
            </p>
        </div>
        <div class="box">
            <h5>我要加料<pre>可多选哦</pre></h5>
            <c:forEach items="${additionMap }" var="additionTypeEntry">
	            <p data-key="${additionTypeEntry.key }" class="addition-type-wrapper">
	            	<c:forEach items="${additionTypeEntry.value }" var="additionType">
		                <input type="checkbox" class="addition-type" id="addition_type_${additionType.id }" value="${additionType.id }">
		                <label for="addition_type_${additionType.id }">${additionType.name }</label>
	            	</c:forEach>
	            </p>
            </c:forEach>
        </div>
        <div class="box">
            <h5>其他备注</h5>
            <p class="blob">
                <textarea id="remarks" name="remarks" placeholder="输入备注详情"></textarea>
            </p>
        </div>
        <div class="box">
            <a id="addDrink" href="javascript:;" class="btn-add">添加</a>
            <div class="table-detail">
                <ul class="title-row">
                    <li>名称</li>
                    <li>详情</li>
                    <li>杯数</li>
                    <li>单价</li>
                    <li>操作</li>
                </ul>
                <!-- <ul class="data-row">
                    <li>金吉柠檬</li>
                    <li>
                        <p>绿茶|中杯|3分甜|常温</p>
                        <p>加料：珍珠、波霸、烧仙草、椰果、红豆绿茶</p>
                    </li>
                    <li>1</li>
                    <li>￥12.0</li>
                    <li><a href="javascript:;">删除</a></li>
                </ul>
                <ul>
                    <li>金吉柠檬</li>
                    <li>
                        <p>绿茶|中杯|3分甜|常温</p>
                        <p>加料：珍珠、波霸、烧仙草、椰果、红豆绿茶</p>
                    </li>
                    <li>1</li>
                    <li>￥12.0</li>
                    <li><a href="javascript:;">删除</a></li>
                </ul> -->
            </div>
        </div>
    </div>
    <div class="total-price">
        <span class="count">总杯数：4</span>
        <span class="price">总价：<b>￥40.0</b></span>
    </div>
</main>
<footer>
    <a href="weixin/ydd/orderList" class="order">订单</a>
    <a id="payment" href="javascript:;" class="main">结账付款</a>
</footer>
</form>
<script>
$(function(){
	$('input[type="radio"],input[type="checkbox"]').xcheck();
	$('input[type="number"]').xnumber();
	seajs.use('ydd/ydd-order.js');
	/* seajs.use(['ajax'], function(Ajax){
		var cupRemain = -1;
		$('#timePoint').change(function(){
			var key = $('option[value="' + $(this).val() + '"]', this).attr('data-key');
			if(key){
				var $dLocation = $('.delivery-location');
				var $targetLocation = $dLocation.filter('[data-key="' + key + '"]');
				$dLocation.removeClass('cview').val('');
				$targetLocation.addClass('cview');
			}
		}).trigger('change');
		
		$('.delivery-location').change(function(){
			var deliveryId = $(this).val();
			if(deliveryId){
				Ajax.ajax('weixin/ydd/getDeliveryRemain', {
					deliveryId	: deliveryId
				}, function(json){
					if(!json.error){
						if(json.remain == 'unlimited'){
							cupRemain = Number.MAX_VALUE;
							$('#cup-remain').text('不限');
						}else{
							cupRemain = json.remain;
							$('#cup-remain').text(json.remain);
						}
					}
				});
			}
		});
		
		
		$('#drink-type').change(function(){
			var drinkTypeId = $(this).val();
			$('.tea-addition-type-wrapper')
				.removeClass('cview')
				.filter('[data-key="' + drinkTypeId + '"]')
				.addClass('cview');
			$('.addition-type-wrapper')
				.removeClass('cview')
				.filter('[data-key="' + drinkTypeId + '"]')
				.addClass('cview');
		});
		
		var drinks=[];
		function addDrink(){
			var type = $('select#type option:selected').val(); //饮料类型
			var name = $('input[name="type"]:checked').val(); //奶茶名称
			var count = $("#count").val(); //数量
			var size = $('input[name="size"]:checked').val(); //奶茶规格
			var sweet = $('input[name="sweet"]:checked').val(); //奶茶甜度
			var temperature = $('input[name="temperature"]:checked').val(); //奶茶冰度
			var additions = [];
			$('input[name="other"]:checked').each(function(){
				additions.push({
					additionId : $(this).val(),
					additionName : $(this).next().text()
				})
			});
			var data ={
		            	type : type,
		            	name : name,
		            	count : count,
		            	size : size,
		            	sweet : sweet,
		            	temperature : temperature,
		            	additions : additions
			            };
			drinks.push(data);
			console.log(drinks);
			
		}
		function payment(){
			var time = $('select#time option:selected').val(); //时间档
			var address = $('select#address option:selected').val(); //配送地点
			var telphone = $("#telphone").val(); //时间档
			var remarks = $("#remarks").val();//备注
			var data = {
					time: time,
					address:address,
					telphone:telphone,
					remarks:remarks,
					drinks:drinks
					};
			console.log(data);
			Ajax.postJson('weixinBuy/testJson',data,function(json){
				console.log(json);
			});
		}
		$("#addDrink").click(addDrink);
		$("#payment").click(payment);
	}) */
});
</script>
</body>
</html>