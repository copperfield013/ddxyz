define(function(require, exports){
	var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
	var SEQUENCE_MAP = {}
	var sFocus;
	var bindOrTriggerMap = {};
	var eventFieldMap = {};

	$.extend(exports, {
		/**
		 * 判断一个是否是整数，不包含判断类型
		 */
		isInteger	: function(o){
			return (o | 0) == o;
		},
		/**
		 * 清除字符串前后的空格
		 */
		trim		: function(str){
			if(typeof str === 'string'){
				return str.trim();
			}
			return str;
		},
		/**
		 * 获得dom的位置和尺寸
		 * 如果没有传入参数，则获取body的位置和尺寸
		 */
		getPageOffset: function($page){
			var page = document.body;
			if($page instanceof $){
				page = $page.get(0);
			}
			return {
				top		: page.offsetTop,
				left	: page.offsetLeft,
				width	: page.offsetWidth,
				height	: page.offsetHeight
			};
		},
		/**
		 * 获得随机字符串
		 * @param len 随机字符串长度，默认32
		 * @param radix 字符维度。如果传入10，则生成的字符串的每个字符都是0~9，如果传入16，则为0~F。默认16，最大62
		 * @return 随机字符串
		 */
		uuid		: function(len, radix){
			var chars = CHARS, uuid = [], i;
			len = len || 32;
			radix = radix || 16;
			if(radix > chars.length){
				radix = chars.length;
			}
			for (i = 0; i < len; i++){
				uuid[i] = chars[0 | Math.random() * radix];
			}
			return uuid.join('');
		},
		/**
		 * 遍历树形结构对象
		 * @param node 要遍历的树的根节点
		 * @param childrenGetter 从父节点获得子节点的方法
		 * @param func 遍历每个节点要执行的方法
		 * @return 调用遍历方法时，第一个return false的节点会被返回
		 * 			如果始终没有调用到return false，那么会返回一个包含所有元素的数组（先序遍历）
		 */
		iterateTree	: function(node, childrenGetter, func){
			var array = [node];
			if(node && typeof func === 'function'){
				try{
					result = func(node);
					if(result === false){
						return node;
					}
				}catch(e){
					console.error(e);
				}
				if(typeof childrenGetter === 'function'){
					var children = childrenGetter(node);
					if($.isArray(children)){
						for(var i in children){
							var itrResult = this.iterateTree(children[i]);
							if(typeof result === 'object'){
								return result;
							}else if($.isArray(itrResult)){
								$.merge(array, itrResult);
							}
						}
					}
				}
			}
			return array;
		},
		/**
		 * 如果值是一个函数，那么会根据参数计算之后得到返回值
		 * 否则会直接返回对象
		 * 如果还传入了checkFn，那么会在返回之前先检验是否符合，符合的话返回值，否则返回undefined
		 * @param val {any} 要返回的值或者要计算的函数
		 * @param args {Array<any>} 如果val是函数的话，那么会传入的参数
		 * @param checkFn {Function(any)} 用于检验值的函数，如果传入了函数，那么仅返回true的时候会校验成功并返回
		 */
		applyValue	: function(val, args, checkFn){
			var checkFn = typeof checkFn === 'function'? checkFn: returnTrue;
			var value = val;
			if(typeof val === 'function'){
				value = val.apply(this, args);
			}
			return checkFn(value) == true? value: undefined;
		},
		/**
		 * 切换jquery对象的class。
		 * 如果jquery对象本来都没有class1和class2,或者都有class1和class2，则不处理
		 * @param jqObj {jQuery}要切换的JqueryDom对象
		 * @param class1 {String}
		 * @param class2 {String}
		 * @param flag {Boolean} 为true的话，jqObj一定有class1没有class2；为false的话，jqObj有class2没有class1
		 */
		switchClass	: function(jqObj, class1, class2, flag, callback){
			var hasClass1 = jqObj.is('.' + class1),
				hasClass2 = jqObj.is('.' + class2);
			if((hasClass1 ^ hasClass2) === 1){
				if(typeof flag === 'function'){
					callback = flag;
					flag = hasClass2;
				}else{
					callback = (typeof callback === 'function')? callback: $.noop;
					flag = flag || hasClass2;
				}
				jqObj.toggleClass(class1, flag);
				jqObj.toggleClass(class2, !flag);
				try{
					callback.apply(jqObj, [flag]);
				}catch(e){}
			}
			return this;
		},
		/**
		 * 切换对象的一般属性。
		 * @param attrName 属性名
		 * @param value1 优先属性，当属性不存在，或者属性值不是value1和value2中的一个，那么将会变成value1
		 * @param value2 切换属性
		 */
		toggleAttr	: function(jqObj, attrName, value1, value2){
			$(jqObj).each(function(){
				var $jqObj = $(this);
				var value = $jqObj.attr(attrName);
				if(value === value1){
					$jqObj.attr(attrName, value2);
				}else{
					$jqObj.attr(attrName, value1);
				}
			});
		},
		/**
		 * 将dom元素的内容设置为数字编辑器
		 */
		NumberEdit	: function(_param, whenEnter){
			var defaultParam = {
				$target		: null,
				scope		: document,
				whenEnter	: $.noop
			};
			var param = {};
			if(_param instanceof $){
				$.extend(param, defaultParam, {
					$target		: _param,
					whenEnter	: whenEnter
				});
			}else{
				$.extend(param, defaultParam, _param);
			}
			$(param.scope).keydown(function(e){
				if($(e.target).is(':text,textarea')){
					return;
				}
				console.log(e.keyCode);
				var $number = param.$target,
					oNumber = $number.text();
				if(e.keyCode >= 48 && e.keyCode <= 57){
					oNumber = oNumber === '0'? '': oNumber;
					oNumber += String(e.keyCode - 48);
					$number.text(oNumber);
				}else if(e.keyCode == 13){
					var _return = param.whenEnter(parseInt(oNumber));
					if(_return !== false){
						//回车时将数字置零
						$number.text(0);
					}
				}else if(e.keyCode == 8){
					if(oNumber.length > 1){
						oNumber = oNumber.substr(0, oNumber.length - 1);
					}else{
						oNumber = '0';
					}
					$number.text(oNumber);
				}
			});
		},
		/**
		 * 将滚动条滑动到指定元素的位置
		 */
		scrollTo	: function($container, position){
			var $position
			if(position instanceof $){
				$position = position;
			}else if(position === 0){
				$position = $container.children('div,span').first();
			}else{
				$position = $container.children().last()
			}
			if($container.offset() && $position.offset()){
				$container.scrollTop(
						$position.offset().top - $container.offset().top + $container.scrollTop()
				);
			}
		},
		/**
		 * 验证联系号码的格式
		 */
		testContactNumber	: function(contact){
			return /^1[34578]\d{9}$/.test(contact)
			|| /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,8}$/.test(contact)
		},
		/**
		 * 格式化日期
		 */
		formatDate			: function(date, fmt){
			if(typeof date === 'string' && fmt === undefined){
				fmt = date;
				date = new Date();
			}
			if(date instanceof Date){
				var o = { 
						"M+" : date.getMonth()+1,                 //月份 
						"d+" : date.getDate(),                    //日 
						"h+" : date.getHours(),                   //小时 
						"m+" : date.getMinutes(),                 //分 
						"s+" : date.getSeconds(),                 //秒 
						"q+" : Math.floor((date.getMonth()+3)/3), //季度 
						"S"  : date.getMilliseconds()             //毫秒 
				}; 
				if(/(y+)/.test(fmt)) {
					fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length)); 
				}
				for(var k in o) {
					if(new RegExp("("+ k +")").test(fmt)){
						fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
					}
				}
				return fmt; 
			}
		},
		/**
		 * 获得当天时间的零点Date对象
		 * @param date {Date} 传入要获取零点的那天的某个时间对象，不传入时，取当天
		 * @return {Date} 零点时的时间对象
		 */
		getDate			: function(date, incDay){
			if(!(date instanceof Date)){
				date = new Date();
			}else{
				date = new Date(date);
			}
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			date.setMilliseconds(0);
			if(typeof incDay === 'number' && incDay > 0){
				date = new Date(Date.parse(date) + incDay * 86400000);
			}
			return date;
		},
		/**
		 * 获得选择框选择的选项option
		 */
		getCheckedOption: function(select){
			var $select  = $(select);
			if($select.is('select')){
				var val = $select.val();
				return $select.find('option[value="' + val + '"]');
			}
		},
		/**
		 * 移除一个元素样式，对css样式无影响
		 */
		removeStyle		: function(dom, styleName){
			if(styleName){
				var style = $(dom).attr('style');
				if(style){
					var reg = new RegExp(styleName + '\\s*:\\s*([^;]+;)|([^;]+$)', 'gi')
					style = style.replace(reg, '');
					$(dom).attr('style', style);
				}
			}
			return this;
		},
		/**
		 * 自增序列
		 * @param key 表示序列的键
		 * @param key 序列的起始值，默认为0
		 */
		getNextSequence	: function(key, start){
			key = key || 'DEFAULT_KEY';
			var currentSeq = SEQUENCE_MAP[key];
			if(!currentSql){
				currentSeq = exports.isInteger(start)? parseInt(start): 0;
			}
			SEQUENCE_MAP[key] = currentSeq + 1;
			return currentSeq;
		},
		datepicker		: function($dom,scrollEle){
			return $($dom).datepicker({
				format		: 'yyyy-mm-dd',
				weekStart	: 1,
				daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],  
                monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',  
                        '七月', '八月', '九月', '十月', '十一月', '十二月' ]
			},scrollEle);
		},
		/**
		 * 获取焦点
		 */
		focus		: function($dom){
			return $($dom).focus(function(){
				sFocus = $(this);
			});
		},
		daterangepicker: function($dom, _param){
			var defaultParam = {
					format 				: 'YYYY-MM-DD HH:mm:ss',
					timePicker			: true,
					timePicker12Hour	: false,
					timePickerIncrement : 5,
					separator			: '~',
					locale				: {
						applyLabel	: '确定',
		                cancelLabel: '取消',
		                fromLabel: '从',
		                toLabel: '到',
						daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],  
		                monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',  
	                        '七月', '八月', '九月', '十月', '十一月', '十二月' ]
				}
			};
			var param = $.extend({}, defaultParam, _param);
			return $dom.daterangepicker(param);
		},
		triggerInField	: function(fieldName, eventName, args, target){
			if(typeof fieldName === 'string' && typeof eventName === 'string'){
				var eventMap = eventFieldMap[fieldName];
				if(eventMap){
					var callbackStack = eventMap[eventName];
					if(callbackStack){
						var event = {
								fieldName	: fieldName,
								eventName	: eventName
						};
						var eventArgs = [event];
						if($.isArray(args)){
							eventArgs = $.merge(eventArgs, args);
						}else{
							eventArgs.push(args);
						}
						if(!target){
							target = this;
						}
						for(var i = callbackStack.length - 1; i >= 0; i--){
							try{
								if(callbackStack[i].apply(target, eventArgs) === false){
									return false;
								}
							}catch(e){
								console.error(e);
							}
						}
					}
				}
			}
		},
		bindInField		: function(fieldName, eventName, callback){
			if(typeof fieldName === 'string' && typeof eventName === 'string'){
				if(typeof callback === 'function'){
					var eventMap = eventFieldMap[fieldName];
					if(!eventMap){
						eventFieldMap[fieldName] = eventMap = {};
					}
					var callbackStack = eventMap[eventName];
					if(!callbackStack){
						eventMap[eventName] = callbackStack = [];
					}
					callbackStack.push(callback);
				}
			}
		},
		trigger		: function(eventName, args){
			return this.triggerInField('defaultField', eventName, args);
		},
		bind		: function(eventName, callback){
			return this.bindInField('defaultField', eventName, callback);
		},
		/**
		 * 绑定和触发
		 * 当A使用bindOrTrigger(event, callback)方法绑定了一个事件，必须等待B用bindOrTrigger(event)触发该事件。
		 * 与普通的bind-trigger方法不同的是：用该方法绑定事件时，如果已经有方法调用了触发的方法，那么在绑定之后将会立刻触发事件。
		 * 之后，如果有其他方法再次调用触发方法，将会直接触发。如果重新绑定了事件，那么也会在绑定之后立刻触发重新绑定的回调。
		 * 如果想要重新绑定并令其重新触发，只能传入callback为false，那么将会重新初始化该事件
		 */
		bindOrTrigger	: function(event, callback){
			if(typeof event === 'string'){
				if(callback === false){
					bindOrTriggerMap['event_' + event] = undefined;
					return this;
				}
				var obj = bindOrTriggerMap['event_' + event];
				if(!obj){
					bindOrTriggerMap['event_' + event] = obj = {};
				}
				if(callback === undefined || $.isArray(callback)){
					if(typeof obj.callback === 'function'){
						obj.callback.apply(this, callback);
					}
					obj.param = callback;
					obj.flag = true;
				}else if(typeof callback === 'function'){
					obj.callback = callback;
					if(obj.flag){
						this.bindOrTrigger(event, obj.param);
					}
				}
			}
		},
		bindTap		: function(target, handler){
			$(target).on('cpf-tap', handler);
			//自定义tap
			$(target).on("touchstart", function(e) {
			    if(!$(e.target).hasClass("disable")) $(e.target).data("isMoved", 0);
			});
			$(target).on("touchmove", function(e) {
			    if(!$(e.target).hasClass("disable")) $(e.target).data("isMoved", 1);
			});
			$(target).on("touchend", function(e) {
			    if(!$(e.target).hasClass("disable") && $(e.target).data("isMoved") == 0) $(e.target).trigger("cpf-tap");
			});
		},
		/**
		 * 计算地球上两个坐标的直线距离， 单位公里
		 */
		distanceBetween	: function(posA, posB){
			var lngA = posA.lng, latA = posA.lat,
				lngB = posB.lng, latB = posB.lat;
			var R = 6371.004;//地球半径
			var c = Math.sin(latA) * Math.sin(latB) * Math.cos(lngA - lngB) + Math.cos(latA) * Math.cos(latB);
			return R * Math.acos(c) * Math.PI / 180;
		}
	});
	
	function returnTrue(){return true;}
	function returnFalse(){return false;}
});
