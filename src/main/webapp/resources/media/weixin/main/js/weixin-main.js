define(function(require){
	var $CPF = require('$CPF'),
		utils = require('utils'),
		cnsl = require('console');
	$CPF.init({
		//各个模块的参数
	});
	//初始化当前页面
	$CPF.initPage(document);
	cnsl.log('-----weixin-main------');
});
