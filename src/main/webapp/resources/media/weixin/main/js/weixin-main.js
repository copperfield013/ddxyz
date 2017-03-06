define(function(require){
	var $CPF = require('$CPF'), utils = require('utils');
	$CPF.init({
	});
	//初始化当前页面
    $CPF.initPage(document);
    console.log(utils.getPageOffset());
});
