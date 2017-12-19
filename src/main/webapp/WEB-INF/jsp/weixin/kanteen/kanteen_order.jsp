<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>确认订单</title>
	<jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include-kanteen.jsp"></jsp:include>
    <link rel="stylesheet" href="media/weixin/kanteen/css/kanteen-order.css?v=3">
    <link rel="stylesheet" href="media/weixin/kanteen/css/kanteen-base.css?v=2">
    <script src="media/weixin/plugins/pushbutton/pushbutton.min.js"></script>
    	
    <!-- <script src="media/weixin/kanteen/js/kanteen-order.js"></script> -->
</head>

<body>
	<c:set var="hasDelivery" value="${deliveries != null && fn:length(deliveries) > 0 }" />
    <form id="canteen-order-information">
		<section class="canteen-user-information-detail">
            <div class="canteen-user-information-basic_important">
                <div class="canteen-user-information-basic_list">
                    <span class="canteen-user-information-basic_label">配送方式</span>
                    <label class="canteen-user-infomation-basic_select">
                    	<c:if test="${allowFixedDelivery }">
    	                	<input data-text="定点领取" type="radio" name="deliveryMethod" id="fixed-delivery" value="fixed" class="ccheckbox" />
                    	</c:if>
                    	<c:if test="${allowHomeDelivery }">
	                    	<input data-text="配送上门" type="radio" name="deliveryMethod" id="home-delivery" value="home" class="ccheckbox" />
                    	</c:if>
                    </label>
                </div>
                <div class="canteen-user-information-basic_list delivery-method-fixed">
                    <span class="canteen-user-information-basic_label">领取地点</span>
                    <label id="fetchSite" class="canteen-user-infomation-basic_select">请选择领取地点</label>
                </div>
                <div class="canteen-user-information-basic_list delivery-method-home">
                    <span class="canteen-user-information-basic_label">送货地址</span>
                    <label id="homeDeliveryAddress" class="canteen-user-infomation-basic_select">小区/大厦/学校</label>
                    <input type="hidden" id="homeDeliveryLongitude" />
                    <input type="hidden" id="homeDeliveryLatitude" />
                    <input type="hidden" id="homeDeliveryAddressId" />
                    <input type="hidden" id="homeDeliveryFullAddress" />
                </div>
                <div class="canteen-user-information-basic_list delivery-method-home">
                    <span class="canteen-user-information-basic_label">详细地址</span>
                    <input type="text" id="detailedAddress" class="canteen-user-infomation-basic_select" placeholder="请输入详细地址" />
                </div>
                <div class="canteen-user-information-basic_list delivery-method-fixed">
                    <span class="canteen-user-information-basic_label">领取时间</span>
                    <label id="fetchTime" class="canteen-user-infomation-basic_select">请选择领取时间</label>
                </div>
                <div class="canteen-user-information-basic_list delivery-method-home">
                    <span class="canteen-user-information-basic_label">配送时间</span>
                    <label id="deliveryTime" class="canteen-user-infomation-basic_select">请选择配送时间</label>
                </div>
	            <div class="canteen-user-information-basic_list">
	                <span class="canteen-user-information-basic_label">支付方式</span>
	                <label id="payWay" class="canteen-user-infomation-basic_select">请选择支付方式</label>
	            </div>
	            <div class="canteen-user-information-basic_list" id="delivery-fee-row">
	            	<span>该配送需另支付配送费：<span id="deliveryFee">5.00</span></span>
	            </div>
            </div>
            <div class="canteen-user-information-basic_mark">
                <p class="canteen-user-information-basic__mark_label">买家备注</p>
                <div class="canteen-user-information-basic__mark_textarea">
                    <textarea  id="remark" placeholder="请填写备注（可不写）"></textarea>
                </div>
            </div>
        </section>
        <section class="canteen-user-information-basic">
            <div class="canteen-user-information-basic_list">
                <span class="canteen-user-information-basic_label">领取人</span>
                <input id="receiverName" type="text" value="${receiver.name }" placeholder="请输入姓名">
            </div>
            <div class="canteen-user-information-basic_list">
                <span class="canteen-user-information-basic_label">手机号码</span>
                <input id="receiverContact" tSype="text"  value="${receiver.contact }" placeholder="请输入联系方式">
            </div>
            <div class="canteen-user-information-basic_list">
                <span class="canteen-user-information-basic_label">部门</span>
                <input id="receiverDepart" tSype="text"  value="${receiver.depart }" placeholder="请输入领取人部门">
            </div>
        </section>

       

        <section class="canteen-order-information">
            <p class="canteen-order-information_title">
                <i class="canteen-order-information_logo"></i>
                <span class="canteen-order-information_logoname">林家食堂</span>
            </p>
            <div class="canteen-order-information_lists">
            	<c:forEach items="${trolley.validWares }" var="wares">
	            	<div class="canteen-order-information_list" data-dwid="${wares.distributionWaresId }" data-count="${wares.count }">
	                    <span class="canteen-order-information_list_name">
	                    	<span class="kanteen-order-wares-option-name">${wares.waresName }</span>
	                    	<span class="kanteen-order-wares-option-desc">${wares.optionDesc }</span>
	                    </span>
	                    <span class="canteen-order-information_list_count canteen-icon canteen-close-icon">${wares.count }</span>
	                    <span class="canteen-order-information_list_price canteen-icon canteen-rmb-icon"><fmt:formatNumber value="${wares.basePrice/100 * wares.count }" pattern="0.00" /> </span>
	                </div>
            	</c:forEach>
                
            </div>
            <p class="canteen-order-information_price">
                <span class="canteen-order-information_price_text">商品总价</span>
                <span class="canteen-order-information_price_totalprice canteen-icon canteen-rmb-icon"><fmt:formatNumber value="${trolley.totalValidPrice/100 }" pattern="0.00" /> </span>
            </p>
        </section>
    </form>

    <footer class="canteen-order-footer">
        <div class="canteen-order-total-price">
            <span class="canteen-order-total-price_text">合计：</span>
            <span class="canteen-order-total-price_price canteen-icon canteen-rmb-icon"><fmt:formatNumber value="${trolley.totalValidPrice/100 }" pattern="0.00" /> </span>
        </div>
        <a href="javascript:;" id="order-submit" class="order-submit">提交订单</a>
    </footer>
    <section id="sitebutton"></section>
    <section id="timebutton"></section>
    <section id="paybutton"></section>
    <section id="deliveryTimeButton"></section>
    <script type="text/javascript">
    	$(function(){
	    	seajs.use(['utils'], function(Utils){
	    		Utils.bindOrTrigger('main-inited', function(){
		    		Utils.bindOrTrigger('whenAttributeInited', [{
		    			deliveriesJson 			: $.parseJSON('${deliveriesJson}'),
		    			hasDelivery				: '${hasDelivery}' == 'true',
		    			distributionId			: '${distributionId}',
		    			trolleyTotalValidPrice	: parseInt('${trolley.totalValidPrice }'),
		    			paywayMap				: {
								'key_1'	: [{
									text	: '微信支付',
									key		: 'wxpay'
								}],
								'key_2'	: [{
									text	: '现场支付',
									key		: 'spot'
								}],
								'key_3'	: [{
									text	: '微信支付',
									key		: 'wxpay'
								},{
									text	: '现场支付',
									key		: 'spot'
								}]
						}
		    		}]);
	    		});
	    	});
    	});
    </script>
    <script type="text/javascript" src="media/weixin/kanteen/js/kanteen-order.js"></script>
</body>

</html>