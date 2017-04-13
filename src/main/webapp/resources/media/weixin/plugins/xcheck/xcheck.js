/**
 * checkbox和radio样式插件
 * 前提条件：请引入jquery插件
 * @author Xiaojie.Xu
 */
(function ($) {
    var defaults = {
    	color : "#ffffff",
    	bgcolor : "#cccccc"
    };
    var constants = {
    	CLASS_NAME : "x-check",
    	CHECKED_CLASS : "checked",
    	TYPE_CHECKBOX : "checkbox",
    	TYPE_RADIO : "radio"
    };
    $.fn.extend({
    	xcheck : function(options) {
    		if($(this).length < 1) return ;
    		options = $.extend({}, defaults, options);
    		$(this).each(function() {
    			var type = $(this).attr("type").toLowerCase();
    			var className = constants.CLASS_NAME + " " + type;
    			$(this)
    			.addClass(constants.CLASS_NAME)
    			.next("label")
//    			.css({
//    				"color" : options.color,
//    				"background-color" : options.bgcolor
//    			})
    			.addClass(className)
    			.addClass(this.checked ? constants.CHECKED_CLASS : "")
    			.click(function() {
    				var $input = $(this).prev("input#" + $(this).attr("for"));
    				if(type == constants.TYPE_RADIO) {
    					$input.siblings("input[name=\"" + $input.attr("name") + "\"]")
    					.each(function() {this.checked = false;})
    					.next("label." + constants.CHECKED_CLASS)
    					.removeClass(constants.CHECKED_CLASS);
    					$(this).addClass(constants.CHECKED_CLASS);
    					$input.each(function() {this.checked = true;}).trigger("click");
    					return false;
    				} else if(type == constants.TYPE_CHECKBOX) {
    					$(this).toggleClass(constants.CHECKED_CLASS);
    				}
    			})
    			.data("default", this.checked);
    			_bind_reset($(this));
    		});
    	}
    });
    var _bind_reset = function($check) {
        var $form = $check.closest('form'),
            $label = $check.next('label'),
            dvalue = $label.data("default");
        $form && $form.on('reset', function(){
            if(dvalue) $label.addClass(constants.CHECKED_CLASS);
            else $label.removeClass(constants.CHECKED_CLASS);
        });
    };
    var doc = document,
        jsfiles = doc.scripts,
        jsPath = jsfiles[jsfiles.length - 1].src,
        dir = jsPath.substring(0, jsPath.lastIndexOf("/") + 1);
    doc.write('<link type="text/css" rel="stylesheet" href="' + dir + 'css/xcheck.css">');
})(jQuery);