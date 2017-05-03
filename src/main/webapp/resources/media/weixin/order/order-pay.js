define(function(require, exports, module){
	function payOrder(orderId, whenSuc, whenCancel, whenFail){
		//从后台获得订单的预付款订单号
		require('ajax').ajax('weixin/order/getPrepayCode', {
			orderId		: orderId
		}, function(data){
			if(data.payParam){
				var WxPay = require('wxpay');
				WxPay.pay(data.payParam, function(res){
					if(res['errMsg'] == "chooseWXPay:ok" ) {
						//支付成功，发送请求到后台，更改订单状态
						sendOrderPaiedReq(orderId, 0, whenSuc, whenFail);
					}else{
						whenCancel();
					}
				});
			}
		});
	}
	
	function sendOrderPaiedReq(orderId, counter, whenSuc, whenFail){
		whenSuc = whenSuc || $.noop;
		whenFail = whenFail || $.noop;
		if(counter < 3){
			require('ajax').ajax('weixin/ydd/order-paied', {
				orderId		: orderId
			}, function(setOrderPaiedRes){
				//订单状态更改情况
				if(setOrderPaiedRes.status === 'suc'){
					whenSuc();
				}else{
					//后台状态更新失败时，重新提交
					sendOrderPaiedReq(orderId, ++counter, whenSuc, whenFail);
				}
			});
		}else{
			whenFail();
		}
	}
	
	exports.pay = payOrder;
});