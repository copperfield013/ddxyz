(function($) {
var
    defaults = {
        url : undefined,
        type : 'post',
        data : {},
        dataType : 'text',
        callback : function(data) {}
    };
IX.extend({
    ajax : function(options) {
        options = $.extend({}, defaults, options);
        $.ajax({
            url : options.url,
            type : options.type,
            data : options.data,
            dataType : options.dataType,
            cache : false,
            beforeSend : function() {
            },
            success : function(data) {
                if(options.callback && $.isFunction(options.callback)) {
                    options.callback(data);
                }
            },
            error: function(xhr, ajaxOptions, thrownError) {
                //ix.alert('error : ' + xhr.responseText);
                console.error("Http status: " + xhr.status + " " + xhr.statusText + "\n" +
                        "server response : " + xhr.responseText);
            }
        });
        return this;
    },
    get : function(options) {
        return this.ajax($.extend({}, options, {type : 'get'}));
    },
    post : function(options) {
        return this.ajax(options);
    }
});
})(jQuery);