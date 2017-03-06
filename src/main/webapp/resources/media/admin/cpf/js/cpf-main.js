define(function(require){
	var $CPF = require('$CPF');
	require('ajax');
	require('page');
	require('form');
	require('paging');
	require('dialog');
	require('tree');
	require('tab');
	$CPF.init({
		loadingImg	: 'media/cpf/images/loading.gif',
		paginatorGoPage	: function(pageInfo){
			
		}
	});
	//初始化当前页面
    $CPF.initPage(document);
});
