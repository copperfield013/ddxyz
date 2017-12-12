define(function(require, exports, module){
	var 
		$CPF = require('$CPF'),
		//获得默认参数
		$paramMap = require('$paramMap'),
		//加载微信jsSDK
		wx = require('http://res.wx.qq.com/open/js/jweixin-1.1.0.js'),
		cls = require('console');
	
	cls.log($paramMap);
	cls.log(wx);
	if($CPF.data('wxConfigFlag') !== true){
		if(wx && wx.config){
			wx.config({
				debug: $paramMap.wxDebug, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				appId: $paramMap.appid, // 必填，公众号的唯一标识
				timestamp: $paramMap.timestamp, // 必填，生成签名的时间戳
				nonceStr: $paramMap.nonceStr, // 必填，生成签名的随机串
				signature: $paramMap.signature,// 必填，签名，见附录1
				jsApiList: [
				            'onMenuShareTimeline',
				            'onMenuShareAppMessage',
				            'chooseWXPay',
				            'scanQRCode',
				            'openAddress',
				            'openLocation'
				            ] 
			});
			
			var Api = {
					scanQrCode		: function(_option, callback){
						if(typeof _option === 'function'){
							callback = _option;
							_option = {};
						}
						callback = callback || _option.callback || $.noop;
						var option = {};
						
						var defaultOption  = {
								//调试模式
								debug		: false,
								// 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
								needResult	: 1, 
								//扫描成功后的回调
								success		: function(res){
									if(option.debug){
										alert(JSON.stringify(res));
									}
									var result = res.resultStr;
									callback.apply(wx, [result, res]);
								}
						};
						
						$.extend(option, defaultOption, _option);
						wx.scanQRCode(option);
					}	
			};
			
			wx.LocalApi = Api;
			$CPF.data('wxConfigFlag', true);
		}
	}else{
		$.error('已经初始化过wx.config，不能再次初始化');
	}
	
	module.exports = wx;
	
});