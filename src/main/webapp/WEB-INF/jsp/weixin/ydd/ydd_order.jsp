<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <title>下单</title>
    <jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
    <link rel="stylesheet" href="${basePath }media/weixin/main/css/buy.css?${RES_STAMP}">
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
    	.cup-remain-wrapper{
    		display: none;
    	}
    	.tea-addition-type-wrapper{
    		display: none;
    	}
    	.addition-type-wrapper{
    		display: none;
    	}
    	.screen-shade{
    	    top: 0;
		    left: 0;
		    right: 0;
		    bottom: 0;
		    position: absolute;
		    z-index: 100;
		    opacity: 0.50;
		    background-color: #777;
    	}
    	
    </style>
    <%-- <script src="${basePath }media/weixin/main/js/jquery-3.1.1.min.js"></script> --%>
    <script src="${basePath }media/weixin/main/js/ix.js?${RES_STAMP}"></script>
    <script src="${basePath }media/weixin/plugins/xcheck/xcheck.js?${RES_STAMP}"></script>
    <script src="${basePath }media/weixin/plugins/xnumber/xnumber.js?${RES_STAMP}"></script>
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
            		<c:if test="${isDebug || !item.key.closed }">
	            		<option data-key="${item.key.key }" value="${item.key.hour }">${item.key.hour }点</option>
            		</c:if>
            	</c:forEach>
            </select></dd>
        </dl>
        <dl class="select">
            <dt>配送地址</dt>
            <dd>
            	<c:forEach items="${deliveryMap }" var="item">
            		<c:if test="${isDebug || !item.key.closed }">
	            		<select class="delivery-location" data-key="${item.key.key }" >
	            			<option value="">请选择</option>
	            			<c:forEach items="${item.value }" var="delivery">
	            				<option value="${delivery.locationId }" data-did="${delivery.id }">${delivery.locationName }</option>
	            			</c:forEach>
		            	</select>
            		</c:if>
            	</c:forEach>
            </dd>
        </dl>
        <dl class="text">
            <dt>联系号码</dt>
            <dd>
                <input id="telphone" type="number" name="telphone" placeholder="请输入" value="${receiverInfo.receiverContact }">
            </dd>
        </dl>
    </div>
    <!-- 奶茶详情 -->
    <div class="good-info">
        <h4>饮料详情<span class="tit-desc cup-remain-wrapper">当前可供余量：<span id="cup-remain">50</span>杯</span></h4>
        <dl class="select">
            <dt>饮料种类</dt>
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
                <textarea id="comment" placeholder="输入备注详情"></textarea>
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
            </div>
        </div>
    </div>
    <div class="total-price">
        <span class="count">总杯数：<b>0</b></span>
        <span class="price">总价：<b>￥0</b></span>
    </div>
</main>
<footer>
    <a href="weixin/ydd/orderList" class="order-link">订单</a>
    <a id="pay-order" href="javascript:;" class="main">结账付款</a>
</footer>
</form>
<script>
$(function(){
	$('input[type="radio"],input[type="checkbox"]').xcheck();
	$('#cupCount').xnumber();
	var deliveryHour = '${deliveryHour}',
		deliveryId = '${deliveryId}',
		orderId = '${orderId}';
	seajs.use(['ydd/ydd-order.js?${RES_STAMP}'], function(o){
		if(deliveryId && orderId){
			o.initOrder(deliveryId, orderId);
		}
	});
	if(!(deliveryId && orderId) && deliveryHour){
		$('#timePoint').val(deliveryHour).trigger('change');
	}
});
</script>
</body>
</html>