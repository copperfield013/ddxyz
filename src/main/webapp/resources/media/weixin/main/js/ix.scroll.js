(function($) {
IX.extend({
    unscroll : function(element) {
        element.addEventListener('touchmove', function(evt) {
            if (!evt._isScroller) {
                /*evt.preventDefault();*/
            }
        });
        return this;
    },
    scroll : function(element) {
        if(!element) return this;
        element.addEventListener('touchstart', function() {
            var top = element.scrollTop, totalScroll = element.scrollHeight, currentScroll = top
                    + element.offsetHeight;
            if (top === 0) {
                element.scrollTop = 1;
            } else if (currentScroll === totalScroll) {
                element.scrollTop = top - 1;
            }
        }),
        element.addEventListener('touchmove', function(evt) {
            if (element.offsetHeight < element.scrollHeight)
                evt._isScroller = true;
        });
        return this;
    }
});
})(jQuery);