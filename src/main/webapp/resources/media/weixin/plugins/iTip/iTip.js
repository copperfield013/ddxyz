/**
 * LBS iTips (仿iPhone界面版) 
 * Date: 2015-10-25 
 * ====================================================================
 * 1. 宽高自适应(maxWidth:300 minWidth:250)  引入iTips.css iTips.js （建议js css 放在同一目录）
 * ====================================================================
 * 2. 调用方式1: 
    Tips.alert(option) //显示(确定)按钮 
    Tips.confirm(option) //显示(确定 取消)按钮  option.after(boolean) boolean布尔值 确定true 取消false
    Tips.open(option) //无显示按钮 可设置定时关闭 默认不自动关闭需手动关闭
    Tips.close() //手动调用关闭 (方式1/方式2都可以调用)
    * Tips.show(text) // 显示加载提示框 text为弹出文本 默认加载中 
     * Tips.hide() // 隐藏加载提示框
 * ====================================================================
 * 3. 调用方式2:
     Tips.alert(content,fn) //content内容 fn弹出框关闭后执行函数 相当于option.after
     Tips.confirm(content,fn) //fn(boolean) boolean布尔值 确定true 取消false
     Tips.open(content, time) //time自动关闭时间(单位秒) 默认不自动关闭需手动关闭 
 * ====================================================================
 * 4. option选项：
     content：内容(可带html标签自定义样式)
     before: 点击确定按钮 关闭弹出框前 执行函数  (Tips.alert Tips.confirm中有效)
             如果函数返回false 则不会执行(关闭弹出框)和(after) 一般用于做一些检测
     after: 点击确定按钮 关闭弹出框后 执行函数 (Tips.alert Tips.confirm中有效)
     time: 自动关闭时间(单位秒) time 秒后关闭 (Tips.open中有效) 
     define: 定义确定按钮的文本 (Tips.alert Tips.confirm中有效)
     cancel: 定义取消按钮的文本 (Tips.confirm中有效)
     
     width: 小于1的小数，表示弹出框相对于屏幕的比例
     lockScroll: 
 * ====================================================================
 * Tips.BG //遮罩层
 * Tips.Box //弹出框
 * Tips.define //确定按钮
 * Tips.cancel //取消按钮 
 * ====================================================================
**/
;(function() {
    window.Tips = {
        _create: function() {
            if (!this.Box) {
                var body = document.getElementsByTagName('body')[0],
                    html = '<div id="tips_content"></div><div id="tips_foot"><a href="javascript:;" id="tips_cancel">取消</a><a href="javascript:;" id="tips_define">确定</a></div>';
                this.BG = document.createElement('div');
                this.BG.id = 'tips_mask';
                this.Box = document.createElement('div');
                this.Box.id = 'tips_box';
                this.Box.innerHTML = html;
                body.appendChild(this.BG);
                body.appendChild(this.Box);
                this.content = this.$('#tips_content');
                this.foot = this.$('#tips_foot');
                this.define = this.$('#tips_define');
                this.cancel = this.$('#tips_cancel');
            }
        },
        minWidth: 250,
        maxWidth: 300,
        _show: function(option) {
            this._fix = true;
            this.BG.style.display = 'block';
            this.Box.style.display = 'block';
            this._css(option);
            this._bind(option);
            (option.afterShow || $.noop).apply(this, []);
            this._showOption = option; 
        },
        _hide: function() {
            this._fix = false;
            this.BG.style.display = 'none';
            this.Box.style.display = 'none';
            this._unbind(this._showOption);
            (this._showOption.afterHide || $.noop).apply(this, []);
        },
        _pos: function() {
            var d = document,
                doc = d.documentElement,
                body = d.body;
            this.pH = doc.scrollHeight || body.scrollHeight;
            this.sY = doc.scrollTop || body.scrollTop;
            this.wW = doc.clientWidth;
            this.wH = doc.clientHeight;
            if (document.compatMode != "CSS1Compat") {
                this.pH = body.scrollHeight;
                this.sY = body.scrollTop;
                this.wW = body.clientWidth;
                this.wH = body.clientHeight;
            }
        },
        _css: function(option) {
            this._pos();
            this.BG.style.height = Math.max(this.pH, this.wH) + 'px';
            this.Box.style.width = 'auto';
            this.content.style.cssText = 'float:left';
            this.content.style.cssText = '';
            // width max:300 min:200
            if(typeof option.width === 'number' && option.width < 1){
            	cW = parseInt(option.width * document.body.clientWidth);
            }else{
            	var cW = this.content.offsetWidth;
            	if (cW < this.minWidth) cW = this.minWidth;
            	if (cW > this.maxWidth) {
            		cW = this.maxWidth;
            		// this.content.style.whiteSpace = '';
            		this.content.style.whiteSpace = 'normal';
            	}
            }
            this.Box.style.width = cW + 'px';
            // absolute
            // this.Box.style.left = (this.wW - cW) / 2 + 'px';
            // this.Box.style.top = this.sY + (this.wH - this.Box.offsetHeight) / 2 + 'px';
            // fixed 1
            // this.Box.style.marginLeft = -(cW / 2) + 'px';
            // this.Box.style.marginTop = -(this.Box.offsetHeight / 2) + 'px';
            // fixed 2
            this.Box.style.marginLeft = -(cW / 2) + 'px';
            this.Box.style.top = (this.wH - this.Box.offsetHeight) / 2 + 'px';
        },
        _fixSize: function() {
            var _this = this,
                time = +new Date();
            this._timeid && clearInterval(this._timeid);
            this._timeid = setInterval(function() {
                if (+new Date() - time > 1000) {
                    clearInterval(_this._timeid);
                    _this._timeid = null;
                    return false;
                }
                _this._css();
            }, 250);
        },
        _define: function(option) {
            var _this = this;
            this.define.onclick = function(e) {
                e.stopPropagation();
                if (typeof option === 'function') {
                    _this._hide();
                    _this.Bool = true;
                    option && option(_this.Bool);
                    return;
                }
                var before = option.before && option.before();
                var bool = false;
                before === false && (bool = true);
                if (bool) {
                    e.stopPropagation();
                    return false;
                }
                _this._hide();
                _this.Bool = true;
                option.after && option.after(_this.Bool);
            };
        },
        _cancel: function(option) {
            var _this = this;
            this.cancel.onclick = function(e) {
                e.stopPropagation();
                _this._hide();
                _this.Bool = false;
                if (typeof option === 'function') {
                    option && option(_this.Bool);
                    return;
                }
                option.after && option.after(_this.Bool);
            };
        },
        _bind: function(option) {
            this.Box.focus();
            this._setEvent(option);
        },
        _unbind: function(option) {
            this.Box.blur();
            this.define.onclick = null;
            this.cancel.onclick = null;
            this.define.innerText = '确定';
            this.cancel.innerText = '取消';
            this._timer && clearTimeout(this._timer);
            this._timer = null;
            this._timeid && clearInterval(this._timeid);
            this._timeid = null;
        },
        _setEvent: function(option) {
            var _this = this;
            this.on(this.BG, 'touchmove', function(e) {
                e.preventDefault();
				e.stopPropagation();
                return false;
            });
            
            this.on(this.Box, 'touchmove', function(e) {
            	e.stopPropagation();
            	/*if(!$(e.target).is(option.ignoredScroll)){
            		e.preventDefault();
            	}*/
            });
            this.on(this.define, 'touchstart', function(e) {
                _this.define.className.indexOf('tips_hover') < 0 && (_this.define.className += ' tips_hover');
            });
            this.on(this.define, 'touchend', function(e) {
                _this.define.className = _this.define.className.replace('tips_hover', '').trim();
            });
            this.on(this.cancel, 'touchstart', function(e) {
                _this.cancel.className.indexOf('tips_hover') < 0 && (_this.cancel.className += ' tips_hover');
            });
            this.on(this.cancel, 'touchend', function(e) {
                _this.cancel.className = _this.cancel.className.replace('tips_hover', '').trim();
            });
            this.on(window, 'resize', function(e) {
                if (!_this._fix) return;
                _this._fixSize();
            });
        },
        _setBtn: function(n, option) {
            this.foot.style.display = 'block';
            this.define.style.display = '';
            this.cancel.style.display = '';
            switch (parseInt(n)) {
                case 1:
                    this.define.className = 'tips_define';
                    this.cancel.style.display = 'none';
                    if (typeof option === 'function') {
                        this.define.innerText = '确定';
                        this._define(function() {
                            option && option();
                        });
                    } else {
                        this.define.innerText = option.define || '确定';
                        this._define(option);
                    }
                    break;
                case 2:
                    this.define.className = '';
                    if (typeof option === 'function') {
                        this.define.innerText = '确定';
                        this.cancel.innerText = '取消';
                        this._define(function(b) {
                            option && option(b);
                        });
                        this._cancel(function(b) {
                            option && option(b);
                        });
                    } else {
                        this.define.innerText = option.define || '确定';
                        this.cancel.innerText = option.cancel || '取消';
                        this._define(option);
                        this._cancel(option);
                    }
                    break;
                case 0:
                    this.foot.style.display = 'none';
                    this.define.style.display = 'none';
                    this.cancel.style.display = 'none';
                    break;
            }
        },
        _setContent: function(html) {
            this.content.innerHTML = html+'';
        },
        _setOption: function(option, n, fn) {
            var content = '';
            this._create();
            if (typeof option === 'string' || typeof option === 'number') {
                content = option || '';
                this._setBtn(n, function(b) {
                    fn && fn(b);
                });
            } else {
                option = option || {},
                    content = option.content || '';
                this._setBtn(n, option);
            }
            this._setContent(content);
            this._show(option);
        },
        _setTime: function(option, t) {
            var time = 0,
                _this = this;
            time = (typeof option === 'string' ? t : option.time);
            if (parseInt(time) > 0) {
                this._timer = setTimeout(function() {
                    _this._hide();
                }, time * 1000);
            }
        },
        on: function(el, type, handler) {
            el.addEventListener(type, handler, false);
        },
        off: function(el, type, handler) {
            el.removeEventListener(type, handler, false);
        },
        $: function(s) {
            return document.querySelector(s);
        },
        alert: function(option, fn) {
            this._setOption(option, 1, fn);
        },
        confirm: function(option, fn) {
            this._setOption(option, 2, function(b) {
                fn && fn(b);
            });
        },
        open: function(option, t) {
            this._setOption(option, 0);
            this._setTime(option, t);
        },
        close: function() {
            this._hide();
        }
    };
}());
