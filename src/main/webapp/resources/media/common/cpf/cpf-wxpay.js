define(function(require, exports, module){
	var utils = require('utils'),
		cls = require('console');
	function WxPay(){
		
	}
	
	WxPay.pay = function(_param, success){
		var wxConfig = require('wxconfig');
		wxConfig.ready(function(){
			wxConfig.chooseWXPay($.extend({
				'success'	: function(res){
					// 支付成功后的回调函数
					if(typeof success === 'function'){
						success(res);
					}
				}
			}, _param));
		});
	}
	module.exports = WxPay;
});
