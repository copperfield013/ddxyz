<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>订单列表</title>
	<jsp:include page="/WEB-INF/jsp/weixin/common/weixin-include-kanteen.jsp"></jsp:include>
    <link rel="stylesheet" href="media/weixin/kanteen/css/kanteen-order-list.css?${GLOBAL_VERSION }">
    <script src="media/weixin/kanteen/js/kanteen-order-list.js?${GLOBAL_VERSION }"></script>
    <script src="${basePath }media/weixin/main/js/ix.js?${GLOBAL_VERSION }"></script>
    <script type="text/javascript">
    	$(function(){
    		var serverTime = new Date(parseInt('${now.time + 10000}'));
    		var subTime = serverTime.getTime() - (new Date()).getTime();
    		window.SERVER_TIME = function(){
    			return new Date((new Date()).getTime() + subTime);
    		}
    	});
    </script>
</head>

<body>
    <header>
        <!--搜索框-->
        <form class="canteen-search-bar_form">
            <div class="canteen-search-bar_box">
                <i class="canteen-icon canteen-search-icon"></i>
                <input id="canteenSearch" type="text" name="canteenSearch" placeholder="搜索商品">
                <i class="canteen-search-bar_clear canteen-icon canteen-search-cancel-icon"></i>
            </div>
            <label for="canteenSearch" class="canteen-search-bar_label">
                <i class="canteen-icon canteen-search-icon"></i>
                <span>搜索商品</span>
            </label>
        </form>

        <!--订单nav-->
        <div class="order-nav ${range == 'all'? 'order-nav-total': '' }">
            <a class="order-week" href="weixin/kanteen/order_list/week">本周订单</a>
            <a class="order-total" href="weixin/kanteen/order_list/all">全部订单</a>
            <i class="order-nav-border"></i>
        </div>
    </header>
    <div class="canteen-order-list-wrap ix-wrapper">
    	<div class="ui-body">
	    	<div class="ix-content">
		    	<div id="order-list" class="page-list initing"></div>
	    	</div>
    	</div>
    </div>


    <script type="text/javascript">
    	$(function(){
    		seajs.use(['utils', 'ajax'], function(Utils, Ajax){
    			ix.list('order-list', {
    				url	: 'weixin/kanteen/order_list_data',
    				data: {
    					range	: '${range}'
    				},
    				pageNo : 'pageNo', // 当前页码的key
    				callback : function(boxs){
    					 // 回调 this是当前容器，boxs是加载的数据项
    				}
    			});
    			var countdownTimer = setInterval(function(){
    				$('.pay-countdown[pay-expired-time]').each(function(){
    					var $this = $(this);
    					var expiredTime = new Date(parseInt($this.attr('pay-expired-time')));
    					var remain = expiredTime.getTime() - SERVER_TIME().getTime();
    					if(remain > 0){
	    					var time = new Date(remain - 28800000);
    						$('span', $this).text(Utils.formatDate(time, 'mm:ss'));
    					}else{
    						var $section = $this.closest('section.canteen-order-list');
    						$section.find('.canteen-order-list_status').text($this.attr('pay-expired-status'));
    						$section.find('.canteen-order-pay').remove();
    						$this.remove();
    					}
    				});
    			}, 300);
    			
    			function bindEvent(event, selector, callback){
    				$(document).on(event, selector, function(e){
    					e.preventDefault();
    					if($(e.target).is(selector)){
    						callback.apply(this, arguments);
    					}
    					return false;
    				});
    			}
    			bindEvent('click', 'a.canteen-order-pay', function(e){
    				var $section = $(e.target).closest('section');
    				var orderId = $section.attr('data-id');
					if(orderId){
						Tips.confirm({
							define: '确认',
							cancel: '取消',
							content: '确认继续支付？',
							after: function(b){
								if(b){
									Ajax.ajax('weixin/kanteen/check_order_for_pay', {
			    						orderId	: orderId
			    					}, function(data){
			    						if(data.status === 'suc' && data.payParameter){
			    							seajs.use(['order/order-pay.js?${RES_STAMP}'], function(OrderPay){
			    								try{
				    								OrderPay.doPay('weixin/kanteen/order_paied', data.payParameter, orderId, function(){
				    									//后台支付成功
				    									Tips.alert({
				    										content	: '支付成功',
				    										after	: function(){
				    											$section.find('.canteen-order-list_status')
				    												.text('已支付')
				    												.removeClass('order-status-default')
				    												.addClass('order-status-paied')
				    												;
				    											$(e.target).remove();
				    										}
				    									});
				    								}, 
				    								function(){Tips.alert('没有支付');}, 
				    								function(){Tips.alert('无法支付该订单');});
			    								}catch(e){
			    									console.error(e);
			    									Tips.alert('调用支付接口失败');
			    								}
			    							});
			    						}else if(data.msg){
			    							Tips.alert(data.msg);
			    						}
			    					});
								}
							}
						});
    					
					}
    			});
    			
    			
    			bindEvent('click', 'a.canteen-order-delete', function(e){
    				var $section = $(e.target).closest('section');
    				var orderId = $section.attr('data-id');
    				if(orderId){
    					Ajax.ajax('weixin/kanteen/delete_order', {
    						orderId	: orderId
    					}, function(data){
    						if(data.status === 'suc'){
    							Tips.alert('删除成功', function(){
    								$section.remove();
    							});
    						}else{
    							Tips.alert('操作失败');
    						}
    					})
    				}
    			});
    			bindEvent('click', 'a.canteen-order-cancel', function(e){
    				var $section = $(e.target).closest('section');
    				var orderId = $section.attr('data-id');
					if(orderId){
    					Ajax.ajax('weixin/kanteen/cancel_order', {
    						orderId	: orderId
    					}, function(data){
    						if(data.status === 'suc'){
								$section.find('.canteen-order-list_status')
									.text('已取消')
									.removeClass('order-status-paied')
									.addClass('order-status-canceled')
									;
    						}
    					});
					}
    			});
    			bindEvent('click', '.canteen-order-list_header', function(e){
    				var href = $(e.target).attr('href');
    				if(href){
	    				window.location.href = href;
    				}
    			});
    		});
    	});
    </script>
</body>

</html>