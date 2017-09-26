define(function(require, exports, module){
	function AdsBlock(_param){
		var defaultParam = {
			interval	: 1000,
			filter		: function(){
				return [];
			}
		};
		var param = $.extend({}, defaultParam, _param);
		
		var timer = null;
		
		this.start = function(){
			if(timer == null){
				timer = setInterval(function(){
					$.each(param.filter(), function(i, e){
						$(e).remove();
					});
				}, param.interval);
			}
		};
		
		this.stop = function(){
			if(timer != null){
				clearInterval(timer);
				timer = null;
			}
		};
		
	}
	
	module.exports = AdsBlock;
	
});