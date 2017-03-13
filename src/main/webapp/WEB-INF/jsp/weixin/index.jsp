<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>点点新意</title>
		<jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include.jsp"></jsp:include>
</head>
<body>
	<div data-role="page">
		<div data-role="content">
			微信主页
			<div class="order-dest">
			</div>
			<div class="order-product">
				
			</div>
			<div class="order-list">
				
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			seajs.use(['wxconfig', 'wxpay', 'console'], function(wx, WxPay, cls){
				var pay = new WxPay({
					strategy	: {
						createOrder	: function(Order){
							return new Order({
								intro	: '商品介绍',
								totalFee: 2,
								detail	: {
									
								}
							});
						}
					},
					ajaxSetting	: {
						url		: 'weixin/prepay'
					}
				});
				var order = pay.createOrder();
				
				order.commit();
				
				/* wx.chooseWXPay({
				    timestamp: 0, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
				    nonceStr: '', // 支付签名随机串，不长于 32 位
				    package: '', // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
				    signType: '', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
				    paySign: '', // 支付签名
				    success: function (res) {
				        // 支付成功后的回调函数
				    }
				}); */
			});
		});
	</script>
</body>
</html>