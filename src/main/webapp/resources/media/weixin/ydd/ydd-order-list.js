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
			var OrderPay = require('order/order-pay');
			//从后台获得订单的预付款订单号
			OrderPay.pay(orderId, function(){
				alert('支付成功');
				location.reload();
			});
		}else if(operateType === 'complete'){
			if(confirm('确认收货？')){
				Ajax.ajax('weixin/order/complete', {
					orderId		: orderId,
				}, function(res){
					if(res.status === 'suc'){
						alert('操作成功');
						location.reload();
					}else{
						alert('操作失败');
					}
				});
				
			}
		}else if(operateType === 'refund'){
			//申请退款
			Ajax.ajax('weixin/order/checkRefund', {
				orderId		: orderId
			}, function(res){
				if(res.untreated === true){
					if(confirm('检测到当前订单的产品还没有开始制作，将直接进行退款。继续？')){
						Ajax.ajax('weixin/order/applyRefund', {
							orderId		: orderId
						}, function(refundRes){
							if(refundRes.result === true){
								alert('退款成功');
							}
						});
					}
				}
			});
			
			if(confirm('确认申请退款？')){
				Ajax.ajax('weixin/order/applyRefund', {
					orderId		: orderId
				}, function(res){
					if(res.refundSuc === true){
						//退款成功
						alert('检测到当前订单的产品还没有开始制作，将直接进行退款');
						location.reload();
					}else if(res.goRefundPage === true){
						//跳转到申请退款的界面
						
					}
				});
			}
		}
	}
	
	bindPageEvent();
});