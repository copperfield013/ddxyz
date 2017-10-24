(function($) {
var defaults = {
    url : '',       // request url
    data : {},      // request params
    pageNo : 'pageNo',  // the key of 'current_page_number'
    callback : function(lis) {  // callback function, won't request again if it return false.
        return true;
    }
};
IX.extend({
    list : function(id, options) {
        var $list = $('#' + id);
        $list = $list.length > 0 ? $list.eq(0) : null;
        if(!$list) return this;
        options = $.extend({}, defaults, options);
        var $page = $list.closest('.ui-page');
        // init style
        $list.empty()
            .attr('pageNo', 0)
            .attr('url', options.url)
            .data('options', options)
            .removeClass('nomore');
        // first init
        nextpage($list, options);
        // scroll event to load more
        $list.closest('.ix-wrapper').scroll(function(e) {
            var scroll_height = $list.closest('.ix-content').outerHeight() - $(this).outerHeight();
            //console.log("this scrollTop is :" + $(this).scrollTop() + ", scroll_height is :" + scroll_height);
            if(!$list.hasClass('nomore')
                    && scroll_height > 0
                    && $(this).scrollTop() >= scroll_height) {
                nextpage($list, options);
            }
        });
        return this;
    },
});
var getpage = function($list) {
    return parseInt($list.attr('pageNo'));
};
var setpage = function($list, pageNo) {
    return $list.attr('pageNo', pageNo);
};
var load = function($list, opt, curpage, cb) {
    var data = opt.data || {};
    data[opt.pageNo] = curpage;
    if($list.hasClass('loading')) return ;
    $list.addClass('loading');
    IX.post({
        url : opt.url,
        data : data,
        dataType : 'html',
        callback : function(html) {
            $list.removeClass('loading');
            var $lis = $('<div></div>').html(html).children();
            cb($lis);
            if(opt.callback && $.isFunction(opt.callback)) {
                opt.callback.call($list, $lis);
            }
        }
    });  
};
var nextpage = function($list, options, cb) {
    var next = getpage($list) + 1;
    load($list, options, next, function(lis) {
        setpage($list, next);
        if(lis.length) {
            $list.append(lis);
        } else {
            $list.addClass('nomore');
        }
        if(cb && $.isFunction(cb)) cb();
    });
};
})(jQuery);