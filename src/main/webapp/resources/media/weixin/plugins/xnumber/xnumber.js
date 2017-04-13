/**
 * type="number"的input样式插件
 * 前提条件：请引入jquery插件
 * @author Xiaojie.Xu
 */
(function ($) {
    var defaults = {
    };
    var constants = {
    	CLASS_NAME : "x-number"
    };
    $.fn.extend({
    	xnumber : function(options) {
    		if($(this).length < 1) return ;
    		options = $.extend({}, defaults, options);
    		$(this).each(function() {
    			var _self = this,
                    defaultval = $(_self).val() || 1,
                    $span = $('<label></label>'),
                    $text = $('<i class="text">' + defaultval + '</i>'),
                    $minus = $('<i class="minus"></i>'),
                    $plus = $('<i class="plus"></i>');
                $(this).addClass(constants.CLASS_NAME)
                    .after($span);
                $span.addClass(constants.CLASS_NAME)
                    .append($minus)
                    .append($text)
                    .append($plus)
                    .data('default', defaultval);
                $minus.click(function() {
                    var num = $(_self).val();
                    num = num - 1 < 1 ? 1 : num - 1;
                    $(this).next().text(num);
                    $(_self).val(num);
                });
                $plus.click(function() {
                    var num = $(_self).val();
                    num ++;
                    $(this).prev().text(num);
                    $(_self).val(num);
                });
    			_bind_reset($(this));
    		});
    	}
    });
    var _bind_reset = function($number) {
        var $form = $number.closest('form'),
            $label = $number.next('label'),
            dvalue = $label.data("default");
        $form && $form.on('reset', function(){
            $label.find('i.text').html(dvalue);
            $number.val(dvalue);
        });
    };
    var doc = document,
        jsfiles = doc.scripts,
        jsPath = jsfiles[jsfiles.length - 1].src,
        dir = jsPath.substring(0, jsPath.lastIndexOf("/") + 1);
    doc.write('<link type="text/css" rel="stylesheet" href="' + dir + 'css/xnumber.css">');
})(jQuery);