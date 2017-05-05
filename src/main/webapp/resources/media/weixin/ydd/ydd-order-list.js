/**
 * 
 */
define(function(require, exports, module){
	//绑定页面事件
	function bindPageEvent(){
		console.log('cls');
		$(document).on('click', '.operate-button', function(e){
			var $this = $(this),
				$target = $(this).closest('.order');
			var 
				//订单id
				orderId = $this.closest('[data-oid]').attr('data-oid'),
				//操作类型
				operateType = $this.attr('data-opr');
			if(orderId && operateType){
				//执行操作订单
				operateOrder(orderId, operateType, $target);
			}
			return false;
		});
	}
	
	/**
	 * 执行操作
	 */
	function operateOrder(orderId, operateType, $target){
		var Ajax = require('ajax');
		if(operateType === 'pay'){
			var OrderPay = require('order/order-pay');
			//从后台获得订单的预付款订单号
			OrderPay.pay(orderId, function(){
				alert('支付成功');
				reloadOrder(orderId, $target);
			});
		}else if(operateType === 'complete'){
			if(confirm('确认收货？')){
				Ajax.ajax('weixin/order/complete', {
					orderId		: orderId,
				}, function(res){
					if(res.status === 'suc'){
						alert('操作成功');
						reloadOrder(orderId, $target);
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
				if(res.canRefund === true){
					if(confirm('检测到当前订单可以直接进行退款。继续？')){
						Ajax.ajax('weixin/order/applyRefund', {
							orderId		: orderId
						}, function(refundRes){
							if(refundRes.status === 'suc'){
								alert('退款成功');
								reloadOrder(orderId, $target);
							}else{
								alert('退款失败');
							}
						});
					}
				}else{
					alert(res.errorReason);
				}
			});
		}else if(operateType === 'buy-again'){
			//再次购买
			//传递订单号到下单页面
			//从后台获取再次购买该订单的产品的所有当天可用配送
			//弹出页面显示所有可用配送
			//选择配送后跳转到下单页面
			showDeliverySelection(orderId);
			
			
			//location.href = 'weixin/ydd/order?deliveryId=50&orderId=205';
		}
	}
	
	
	var $dialog = null;
	function showDeliverySelection(orderId){
		var Ajax = require('ajax');
		Ajax.ajax('weixin/ydd/deliverySelection', {
			orderId		: orderId
		}, function(html){
			if($dialog instanceof $){
				$dialog.remove();
			}
			$dialog = $(html);
			$('body').append($dialog);
			bindDialogEvent();
		});
	}
	
	/**
	 * 异步重新从后台加载订单
	 */
	function reloadOrder(orderId, $target){
		var Ajax = require('ajax');
		Ajax.ajax('weixin/ydd/loadOrderItem', {
			orderId	: orderId
		}, function(res, resType){
			if(resType === 'html'){
				var $replace = $(res);
				$target.replaceWith($replace);
			}
		});
	}
	
	function bindDialogEvent(){
		IX.unscroll($('main').get(0));
		IX.scroll($('.dialog-content-wrapper').get(0));
		//设置第一个地址栏展开    	
    	$(".distribution-address-warp:first", $dialog).css("display","block");
		//绑定地址栏点击事件    	
        $(".dialog-distribution-time", $dialog).on("click",function(){
			var address = $(this).next();
			address.slideToggle("fast");
			$('.distribution-address-warp', $dialog).not(address).slideUp('fast');
        })
		//点击选择地址
        $(".dialog-content", $dialog).on("click", ".dialog-distribution-address", function(){
            $(".dialog-distribution-address").removeClass("active");
            $(this).addClass("active");
        });
		//确认按钮获取选择中的地址的外层div
		$('.dialog-button-sure', $dialog).on("click", function(){
			console.log($(".dialog-distribution-address").filter('.active')[0]);
		});
		//取消按钮
       	$('.dialog-button-cancel', $dialog).on("click",function(){
    	   
		})
	}
	
	
	
	bindPageEvent();
});