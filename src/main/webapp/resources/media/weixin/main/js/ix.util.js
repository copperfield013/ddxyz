(function($) {
IX.extend({
    query : function(html, selector) {
        return $('<div></div>').html(html).find(selector);
    },
    browser : function() {
        var u = navigator.userAgent, app = navigator.appVersion;
        return {
            trident : u.indexOf('Trident') > -1, // IE内核
            presto : u.indexOf('Presto') > -1, // opera内核
            webkit : u.indexOf('AppleWebKit') > -1, // 苹果、谷歌内核
            gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,// 火狐内核
            mobile : !!u.match(/AppleWebKit.*Mobile.*/), // 是否为移动终端
            ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), // ios终端
            android : u.indexOf('Android') > -1 || u.indexOf('Adr') > -1, // android终端
            iphone : u.indexOf('iPhone') > -1, // 是否为iPhone或者QQHD浏览器
            ipad : u.indexOf('iPad') > -1, // 是否iPad
            webapp : u.indexOf('Safari') == -1, // 是否web应该程序，没有头部与底部
            weixin : u.indexOf('MicroMessenger') > -1, // 是否微信
            qq : u.match(/\sQQ/i) == " qq" //是否QQ
        };
    }()
});
})(jQuery);

Array.prototype.indexOf = function(el){
     for ( var i = 0, n = this.length; i < n; i++) {
        if (this[i] === el) {
            return i;
        }
    }
    return -1;
};
Date.prototype.format = function(format) {
    var o = {
        "M+" : this.getMonth() + 1,
        "d+" : this.getDate(),
        "H+" : this.getHours(),
        "m+" : this.getMinutes(),
        "s+" : this.getSeconds(),
        "q+" : Math.floor((this.getMonth() + 3) / 3),
        "S" : this.getMilliseconds()
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
    }
    for ( var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                    : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};