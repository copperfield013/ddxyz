define(function(require, exports, module){
	var utils = require('utils'),
		cls = require('console');
	function WxPay(){
		
	}
	
	WxPay.pay = function(_param, callback){
		alert('pay');
		var wxConfig = require('wxconfig');
		callback = callback || $.noop;
		wxConfig.ready(function(){
			wxConfig.chooseWXPay($.extend({
				'complete'	: function(res){
					callback.apply(this, [res]);
				}
			}, _param));
		});
	}
	module.exports = WxPay;
});
