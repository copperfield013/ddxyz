define(function(require, exports){
	var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
	$.extend(exports, {
		/**
		 * 判断一个是否是整数
		 */
		isInteger	: function(o){
			return (o | 0) == o;
		},
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
		 * @param radix 字符维度。如果传入10，则生成的字符串的每个字符都是0~9，如果传入16，则为0~F。默认16
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
		switchClass	: function(jqObj, class1, class2, flag){
			var hasClass1 = jqObj.is('.' + class1),
				hasClass2 = jqObj.is('.' + class2);
			if((hasClass1 ^ hasClass2) === 1){
				flag = flag || hasClass2;
				jqObj.toggleClass(class1, flag);
				jqObj.toggleClass(class2, !flag);
			}
			return this;
		}
	});
	
	function returnTrue(){return true;}
	function returnFalse(){return false;}
});