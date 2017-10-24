define(function(require, exports, module){
	function payOrder(orderId, whenSuc, whenCancel, whenFail){
		//从后台获得订单的预付款订单号
		require('ajax').ajax('weixin/order/getPrepayCode', {
			orderId		: orderId
		}, function(data){
			if(data.payParam){
				doPay('weixin/ydd/order-paied', data.payParam, orderId, whenSuc, whenFail, whenCancel);
			}
		});
	}
	
	function sendOrderPaiedReq(paiedURI, orderId, counter, whenSuc, whenFail){
		whenSuc = whenSuc || $.noop;
		whenFail = whenFail || $.noop;
		if(counter < 3){
			require('ajax').ajax(paiedURI, {
				orderId		: orderId
			}, function(setOrderPaiedRes){
				//订单状态更改情况
				if(setOrderPaiedRes.status === 'suc'){
					whenSuc();
				}else{
					//后台状态更新失败时，重新提交
					sendOrderPaiedReq(paiedURI, orderId, ++counter, whenSuc, whenFail);
				}
			});
		}else{
			whenFail();
		}
	}
	
	function doPay(paiedURI, prepayParameter, orderId, whenSuc, whenFail, whenCancel){
		var WxPay = require('wxpay');
		WxPay.pay(prepayParameter, function(res){
			if(res['errMsg'] == "chooseWXPay:ok" ) {
				//支付成功，发送请求到后台，更改订单状态
				sendOrderPaiedReq(paiedURI, orderId, 0, whenSuc, whenFail);
			}else{
				whenCancel();
			}
		});
	}
	
	exports.pay = payOrder;
	exports.doPay = doPay;
});