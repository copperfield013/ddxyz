define(function(require){
	var $CPF = require('$CPF'),
		utils = require('utils'),
		cnsl = require('console'),
		WX = require('wxconfig'),
		$paramMap = require('$paramMap');
	$CPF.init({
		//各个模块的参数
	});
	//初始化当前页面
	$CPF.initPage(document);
	WX.ready(function(){
		WX.onMenuShareAppMessage({
		    title: '点点新意', // 分享标题
		    desc: '奶茶送来了', // 分享描述
		    link: $paramMap.basePath + 'weixin/ydd?' + $paramMap.RES_STAMP, // 分享链接
		    imgUrl: $paramMap.basePath + 'media/common/ydd/image/ydd-icon.jpg?' + $paramMap.RES_STAMP, // 分享图标
		    success: function () {
		        alert('分享成功');
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});
		WX.onMenuShareTimeline({
		    title: '点点新意', // 分享标题
		    link: '奶茶送来了', // 分享链接
		    imgUrl: $paramMap.basePath + 'media/common/ydd/image/ydd-icon.jpg?' + $paramMap.RES_STAMP, // 分享图标
		    success: function () { 
		    	alert('分享成功');
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});
	});
	
	
	cnsl.log('-----weixin-main------');
});
