/**
 * 
 */
define(function(require, exports, module){
	//绑定页面事件
	function bindPageEvent(){
		console.log('cls');
		$(document).on('click', '.operate-button', function(e){
			var $this = $(this);
			var 
				//订单id
				orderId = $this.closest('[data-oid]').attr('data-oid'),
				//操作类型
				operateType = $this.attr('data-opr');
			if(orderId && operateType){
				//执行操作订单
				operateOrder(orderId, operateType);
			}
			return false;
		});
	}
	
	/**
	 * 执行操作
	 */
	function operateOrder(orderId, operateType){
		var Ajax = require('ajax');
		if(operateType === 'pay'){
			//从后台获得订单的预付款订单号
			
		}
		
		
		Ajax.ajax('weixin/ydd/operateOrder', {
			orderId		: orderId,
			operateType	: operateType
		}, function(data){
			if(data.status === 'suc'){
				alert('操作成功');
				location.reload();
			}else{
				alert('操作失败');
			}
		});
	}
	
	function payOrder(orderId){
		var Ajax = require('ajax');
		//从后台获得订单的预付款订单号
		Ajax.ajax('weixin/order/getPrepayCode', {
			orderId		: orderId
		}, function(data){
			if(data.payParam){
				var WxPay = require('wxpay');
				WxPay.pay(data.payParam, function(res){
					if(res['errMsg'] == "chooseWXPay:ok" ) {
						alert(json.orderId);
						//支付成功，发送请求到后台，更改订单状态
						sendOrderPaiedReq(json.orderId, 0);
					}else{
						alert('没有支付');
						location.href = 'weixin/ydd/orderList';
					}
				});
			}
		});
	}
	
	function sendOrderPaiedReq(orderId, counter){
		if(counter < 3){
			alert('支付成功1');
			require('ajax').ajax('weixin/ydd/order-paied', {
				orderId		: orderId
			}, function(setOrderPaiedRes){
				//订单状态更改情况
				if(setOrderPaiedRes.status === 'suc'){
					//后台支付成功
					alert('支付成功');
					location.href = 'weixin/ydd/orderList';
				}else{
					//后台状态更新失败时，重新提交
					sendOrderPaiedReq(orderId, ++counter);
				}
			});
		}else{
			location.href = 'weixin/ydd/orderList';
		}
	}
	
	bindPageEvent();
});