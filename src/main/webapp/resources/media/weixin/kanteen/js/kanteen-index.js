(function(){
	var kanteen = {
		eventsMap			: {},
		bindChange		: function(callback){
			return this.bind('change', callback);
		},
		triggerChange	: function(data, updateTrolleyWaresFn){
			return this.trigger('change', [data, updateTrolleyWaresFn]);
		},
		bind		: function(eventName, callback){
			if(typeof callback === 'function'){
				var events = this.eventsMap[eventName];
				if(!events){
					this.eventsMap[eventName] = events = [];
				}
				events.push(callback);
			}
			return this;
		},
		trigger	: function(eventName, args){
			var events = this.eventsMap[eventName];
			if(events){
				for(var j = events.length - 1; j >=0 ; j--){
					try{
						if(events[j].apply(this, args) === false){
							break ;
						}
					}catch(e){
						console.error(e);
					}
				}
			}
		},
		afterInit	: function(fn){
			if(typeof fn === 'function'){
				this.bind('afterInit', fn);
			}else{
				this.trigger('afterInit', [fn]);
			}
			return this;
		}
	};
	window.Kanteen = kanteen;
	/*
	 * 林家食堂微信首页页面交互命名空间
	 * 不考虑传统ie浏览器
	 * 不考虑低版本安卓
	 * 如有报错请先F12查看console
	 * ajax内容请写在shoppingData 模块中 
	 */
	let canteen_home_interaction = {
			
			init() {
				this.domInit();    //初始化需要操作的dom,需放在第一个调用
				this.searchBox();  //初始化搜索框点击事件
				this.mealNav();    //初始化菜单导航点击事件
				this.noticeAnimate(); //初始化公告动画内容
				this.addMeal();
				this.shoppingBasketMealadd();
				this.toggleShoppingBasket();
				this.shadowClick();
				this.shoppingEmpty();
				this.informationShow();
				this.informationClose();
			},
			
			domBox: {
				// 搜索框部分的dom
				search_label: null,
				search_box: null,
				search_input: null,
				search_clear: null,
				
				//头部总体信息部分dom
				canteen_information: null,
				
				//公告部分dom
				notice_box: null,
				
				// 左侧餐品导航部分dom
				nav_box: null,
				nav_buttons: [],
				
				//右侧餐品列表部分dom
				meal_lists: [],
				
				//右侧餐品具体点单部分dom
				order_box: null,
				
				//底部购物车部分dom
				shoppingcar_box: null,
				
				//判断是否有餐品选中以及总价计算部分dom
				shoppingcar_meal_lists: [],
				total_price: 0,
				total_count: 0,
				
				//购物车展示部分dom
				shopping_basket: null,
				
				//购物车展示部分出现隐藏部分dom
				shoppingcar_toggle: null,
				shopping_basket_wrap: null,
				
				//购物车清空部分dom
				shoppingcar_empty: null,
				
				//遮罩层部分dom
				shadow: null,
				
				//信息弹出展示部分dom
				information_alert: null,
				information_alert_close: null,
				
				
			},
			
			domInit() {
				
				// 初始化搜索框部分dom
				this.domBox.search_label = document.getElementsByClassName('canteen-search-bar_label')[0];
				this.domBox.search_box = document.getElementsByClassName('canteen-search-bar_box')[0];
				this.domBox.search_input = document.getElementById('canteenSearch');
				this.domBox.search_clear = document.getElementsByClassName('canteen-search-bar_clear')[0];
				
				//初始化头部信息部分dom
				this.domBox.canteen_information = document.getElementsByClassName('canteen-information-area')[0];
				
				// 初始化公告部分dom
				this.domBox.notice_box = document.getElementsByClassName('canteen-information_announcement_carousel')[0];
				
				// 初始化左侧餐品导航部分dom
				this.domBox.nav_box = document.getElementsByClassName('canteen-meal-nav')[0];
				this.domBox.nav_buttons = document.getElementsByClassName('canteen-meal-nav_button');
				
				//初始化右侧餐品列表部分dom
				this.domBox.meal_lists = document.getElementsByClassName('canteen-meal-list');
				
				//初始化右侧餐品具体点单部分dom
				this.domBox.order_box = document.getElementsByClassName('canteen-main-wrap')[0];
				
				//初始化 底部购物车部分dom
				this.domBox.shoppingcar_box = document.getElementsByClassName('page-footer')[0];
				
				//初始化 判断是否有餐品选中以及总价计算部分dom
				this.domBox.shoppingcar_meal_lists = document.getElementsByClassName('canteen-meal-list_menu_button');
				this.domBox.total_price = document.getElementsByClassName('shopping-car-totalprice')[0];
				this.domBox.total_count = document.getElementsByClassName('shopping-car-totalcount')[0];
				
				//初始化购物篮部分dom
				this.domBox.shopping_basket = document.getElementsByClassName('shopping-car-show_list_wrap')[0];
				
				//购物车展示部分出现隐藏部分dom
				this.domBox.shoppingcar_toggle = document.getElementsByClassName('shopping-car')[0];
				this.domBox.shopping_basket_wrap = document.getElementsByClassName('shopping-car-show')[0];
				
				//初始化购物车清空部分dom
				this.domBox.shoppingcar_empty = document.getElementsByClassName('shopping-car-show_title_empty')[0];
				
				//初始化遮罩层部分dom
				this.domBox.shadow = document.getElementsByClassName('canteen-shadow')[0];
				
				//初始化信息弹出展示部分dom
				this.domBox.information_alert = document.getElementsByClassName('canteen-information-alert-component')[0];
				this.domBox.information_alert_close = document.getElementsByClassName('canteen-information-alert_close')[0];
			},
			
			/**
			 * 事件绑定 ,均为冒泡
			 * @param  {dom,event,fn}
			 */
			eventBind(dom, event, fn) {
				try {
					dom.addEventListener(event, fn, false);
				} catch (error) {
					console.warn(error + "  maybe  param dom is underfind or wrong please check up your dom")
				}
			},
			
			/**
			 * 搜索框交互
			 */
			searchBox() {
				let label = this.domBox.search_label;
				let box = this.domBox.search_box;
				let input = this.domBox.search_input;
				let clear = this.domBox.search_clear;
				
				// label 隐藏  box出现
				this.eventBind(label, "touchend", function () {
					label.style.display = "none";
					box.style.display = "block";
				});
				
				//box隐藏 label 出现
				this.eventBind(input, "blur", function () {
					label.style.display = "block";
					box.style.display = "none";
				})
				
				//清空input内容
				this.eventBind(clear, "touchend", function () {
					input.value = "";
				})
				
			},
			
			/**
			 * 头部信息点击弹出框展示
			 */
			informationShow(){
				let me = this;
				let target = me.domBox.canteen_information;
				let alertBox = me.domBox.information_alert;
				me.eventBind(target,"click",function(){
					me.shadow(10);   //遮盖底部
					alertBox.classList.add("active");
				})
			},
			
			/**
			 * 信息弹出框close
			 */
			informationClose() {
				let me = this;
				let close = me.domBox.information_alert_close;
				let alertBox = me.domBox.information_alert;
				me.eventBind(close,"click",function(){
					alertBox.classList.remove("active");
					me.shadow();
				})
			},
			
			/**
			 * 公告部分动画
			 */
			noticeAnimate() {
				let notice = this.domBox.notice_box;
				let length = notice.querySelectorAll('li').length;
				let firstChild = notice.querySelector('li');
				let distance = parseFloat(window.getComputedStyle(firstChild).height);
				let move = distance;
				let maxMove = distance * length;
				let state = "up";    //定义滑动状态
				
				// 公告内容 多于一条 则执行动画
				if (length <= 1) {
					return;
				} else {
					setInterval(function () {
						notice.style.transform = `translateY(-${move}px)`;
						if (state === "up") {
							move += distance;
						} else if (state === "down") {
							move -= distance;
						}
						if (move >= maxMove) {
							move -= 2 * distance;
							state = "down";
						} else if (move <= -distance) {
							move += 2 * distance;
							state = "up";
						}
					}, 3000)
				}
			},
			
			/**
			 * 左侧餐品导航交互
			 */
			mealNav() {
				
				var me = this;
				let nav = this.domBox.nav_box;
				let buttons = this.domBox.nav_buttons;
				
				this.eventBind(nav, "touchend", function (event) {
					
					let target = event.target;
					if (target.classList.contains("canteen-meal-nav_button")) {
						for (let i = 0; i < buttons.length; i++) {
							buttons[i].classList.remove('active');
						}
						try {
							
							let uid = target.getAttribute("data-key");
							target.classList.add("active");
							//执行右侧餐品列表的整体切换
							me.mealListSwitch(uid);
							
						} catch (error) {
							console.warn(error);
						}
					} else {
						return;
					}
					
					
				})
			},
			
			/**
			 * 右侧餐品列表整体切换
			 */
			mealListSwitch(uid) {
				let lists = this.domBox.meal_lists;
				let showList = document.querySelector(`[data-uid="${uid}"]`);
				
				for (let i = 0; i < lists.length; i++) {
					lists[i].classList.remove('active');
				}
				showList.classList.add('active');
				showList.scrollTop = 0;
			},
			
			/**
			 * 具体餐品的点单处增加、减少按钮交互
			 */
			addMeal() {
				let me = this;
				let chooseBox = this.domBox.order_box;
				this.eventBind(chooseBox, "click", function (event) {
					target = event.target;
					let isAdd = target.classList.contains("canteen-meal-list_menu_add");
					let isReduce = target.classList.contains("canteen-meal-list_menu_minus");
					let unitPrice = 0;
					let prouid = null;
					var item = target.parentNode.parentNode.parentNode;
					unitPrice = parseFloat(item.getAttribute('data-base-price'));
					if (isAdd) {
						prouid = item.getAttribute("data-prouid");
						me.mealCount(target, "add");
						me.shoppingCar(unitPrice, "add");
						me.shoppingBasket(prouid, "add");
					} else if (isReduce) {
						prouid = item.getAttribute("data-prouid");
						me.mealCount(target, "reduce");
						me.shoppingCar(unitPrice, "reduce");
						me.shoppingBasket(prouid, "reduce");
					} else {
						return;
					}
				})
			},
			triggerAddTrolley(prouid, addition, trolleyWaresId){
				var target = document.querySelector('.canteen-meal-list_menu[data-prouid="' + prouid +'"]');
				if(target){
					var unitPrice = parseFloat(target.getAttribute('data-base-price'));
					var operate = null; 
					if(addition > 0){
						target = target.querySelector('.canteen-meal-list_menu_add');
						operate = 'add';
					}else if(addition < 0){
						target = target.querySelector('.canteen-meal-list_menu_minus');
						operate = 'reduce';
					}else{
						return;
					}
					this.mealCount(target, operate, addition);
					this.shoppingCar(unitPrice, operate, addition);
					this.shoppingBasket(prouid, operate, trolleyWaresId);
				}
			},
			/**
			 * 具体餐品的点单处数量部分增加减少方法
			 * @param {target,kind} (target:右侧餐品列表处对应餐品的增加以及减少按钮,kind:增加 or 减少)
			 */
			mealCount(target, kind, addition) {
				let me = this;
				let countDom = null;
				let orderChooseWrap = null;
				let count = 0;
				let uid = "";
				if(addition === undefined) addition = 1;
				let allMeal = []; //存储页面上所有该商品
				if (kind === "add") {
					uid = target.parentNode.parentNode.parentNode.getAttribute("data-prouid");
					countDom = target.previousElementSibling;
					count = parseFloat(countDom.textContent);
					isNaN(count) ? count = addition : count += addition;
					allMeal = document.querySelectorAll(`[data-prouid="${uid}"]`);
					for (let i = 0; i < allMeal.length; i++) {
						allMeal[i].querySelector(".canteen-meal-list_menu_count").textContent = count;
						allMeal[i].querySelector(".canteen-meal-list_menu_button ").classList.add("active");
					}
				} else if (kind === "reduce") {
					uid = target.parentNode.parentNode.parentNode.getAttribute("data-prouid");
					countDom = target.nextElementSibling;
					count = parseFloat(countDom.textContent);
					allMeal = document.querySelectorAll(`[data-prouid="${uid}"]`);
					if (isNaN(count)) {
						count = addition;
						for (let i = 0; i < allMeal.length; i++) {
							allMeal[i].querySelector(".canteen-meal-list_menu_count").textContent = count;
						}
					} else if (count <= 1) {
						count -= addition;
						for (let i = 0; i < allMeal.length; i++) {
							allMeal[i].querySelector(".canteen-meal-list_menu_count").textContent = count;
							allMeal[i].querySelector(".canteen-meal-list_menu_button ").classList.remove("active");
						}
					} else {
						count -= addition;
						for (let i = 0; i < allMeal.length; i++) {
							allMeal[i].querySelector(".canteen-meal-list_menu_count").textContent = count;
						}
					}
				}
			},
			
			/**
			 * 底部购物车栏交互效果
			 * @param { unitPrice, kind } ( unitPrice: 单价 kind: 增加 or 减少 ) 
			 */
			shoppingCar(unitPrice, kind, addition) {
				let footer = this.domBox.shoppingcar_box;
				let priceDom = this.domBox.total_price;
				let price = parseFloat(priceDom.textContent);
				let countDom = this.domBox.total_count;
				let count = parseFloat(countDom.textContent);
				if(addition == undefined) addition = 1;
				isNaN(price) ? price = 0 : price;
				isNaN(count) ? count = 0 : count;
				if (kind === "add") {
					priceDom.textContent = parseFloat(price + unitPrice * addition).toFixed(2);
					countDom.textContent = count + addition;
					footer.classList.remove("shopping-car_empty");
				} else if (kind === "reduce") {
					priceDom.textContent = parseFloat(price - unitPrice * addition).toFixed(2);
					countDom.textContent = count - addition;
					if (parseInt(countDom.textContent) === 0) {
						footer.classList.add("shopping-car_empty");
						priceDom.textContent = "购物车是空的";
					}
				}
			},
			
			/**
			 *  购物车展示模块 
			 * @param { uid, kind } ( uid:商品id唯一标识 data-prouid)
			 */
			shoppingBasket(uid, kind, trolleyWaresId) {
				let me = this;
				let wrap = this.domBox.shopping_basket;
				let lists = wrap.children;
				let productWrap = document.querySelector(`[data-prouid="${uid}"]`);
				let footerPage = this.domBox.shoppingcar_box;
				let count = parseFloat(productWrap.querySelector(".canteen-meal-list_menu_count").textContent);
				let name = productWrap.querySelector(".canteen-meal-list_menu_name").textContent;
				let unitPrice = parseFloat(productWrap.querySelector(".canteen-meal-list_menu_price").querySelector("span").textContent);
				let totalPrice = (parseFloat(count * unitPrice)).toFixed(2);
				let listHtml = ``;
				let listUid = -1;
				for (let i = 0; i < lists.length; i++) {
					if (lists[i].getAttribute("data-orderuid") === uid) {
						listUid = i;
					}
				};
				if (kind === "add") {
					if (listUid === -1) {
						let listDiv = document.createElement("div");
						let tempId = trolleyWaresId || me.getTrolleyWaresTempId();
						listHtml = `<div data-orderuid="${uid}" data-twid="${tempId}" class="shopping-car-show_list" data-base-price="${unitPrice}" >
						<span class="shopping-car-show_list_name"><span class="trolley-wares-name">${name}</span></span>
						<div class="shopping-car-show_list_button">
						<a href="javascript:" class="shopping-car-show_list_minus canteen-icon canteen-minus-icon"></a>
						<span class="shopping-car-show_list_count">${count}</span>
						<a href="javascript:" class="shopping-car-show_list_add canteen-icon canteen-add-icon"></a>
						</div>
						<span class="shopping-car-show_list_price canteen-icon canteen-rmb-icon">${totalPrice}</span>
						</div>`;
						listDiv.innerHTML = listHtml;
						wrap.appendChild(listDiv.children[0].cloneNode(true));
					} else {
						lists[listUid].querySelector(".shopping-car-show_list_count").textContent = count;
						lists[listUid].querySelector(".shopping-car-show_list_price").textContent = totalPrice;
					}
				} else if (kind === "reduce") {
					let shoppingBasket = me.domBox.shopping_basket_wrap;
					let showBasket = shoppingBasket.classList.contains("active");
					if (count === 0) {
						let removeList = lists[listUid];
						removeList.parentNode.removeChild(removeList);
					} else {
						lists[listUid].querySelector(".shopping-car-show_list_count").textContent = count;
						lists[listUid].querySelector(".shopping-car-show_list_price").textContent = totalPrice;
					}
					if (lists.length === 0 && showBasket) {
						shoppingBasket.classList.toggle("active");
						footerPage.classList.toggle("active");
						me.shadow();
					}
				} else {
					return;
				}
				this.shoppingData();
			},
			/**
			 * 
			 */
			trolleyWaresTempId	: 0,
			/**
			 * 
			 */
			getTrolleyWaresTempId() {
				var uuid = 0 | Math.random() * 100000;
				return 'temp_' + uuid;
			},
			/**
			 *  购物车展示模块 
			 * @param { uid, kind } ( uid:商品id唯一标识 data-prouid)
			 */
			addOptionWares(dwid, count, unitPrice, description, optionIds, trolleyWaresId) {
				if(typeof dwid === 'object'){
					let $trolleyItem = dwid;
					let currentCount = parseInt($trolleyItem.querySelector(".shopping-car-show_list_count").textContent);
					let toCount = currentCount + count;
					let basePrice = parseFloat($trolleyItem.getAttribute('data-base-price'));
					if(toCount > 0){
						$trolleyItem.querySelector(".shopping-car-show_list_count").textContent = toCount;
						$trolleyItem.querySelector(".shopping-car-show_list_price").textContent = (basePrice * toCount).toFixed(2);
					}else{
						let shoppingBasket = this.domBox.shopping_basket_wrap;
						let showBasket = shoppingBasket.classList.contains("active");
						let wrap = this.domBox.shopping_basket;
						let lists = wrap.children;
						$trolleyItem.parentNode.removeChild($trolleyItem);
						if (lists.length === 0 && showBasket) {
							shoppingBasket.classList.toggle("active");
							this.domBox.shoppingcar_box.classList.toggle("active");
							this.shadow();
						}
						
					}
						
				}else{
					let me = this;
					let wrap = this.domBox.shopping_basket;
					let lists = wrap.children;
					let productWrap = document.querySelector(`[data-prouid="${dwid}"]`);
					let footerPage = this.domBox.shoppingcar_box;
					let name = productWrap.querySelector(".canteen-meal-list_menu_name").textContent;
					let totalPrice = (parseFloat(count * unitPrice)).toFixed(2);
					let tempId = trolleyWaresId || me.getTrolleyWaresTempId();
					let listDiv = document.createElement("div");
					let options = optionIds.join();
					let listHtml = `<div data-orderuid="${dwid}" data-twid="${tempId}" data-options="${options}" class="shopping-car-show_list" data-base-price="${unitPrice}">
						<span class="shopping-car-show_list_name"><span class="trolley-wares-name">${name}</span><span class="trolley-wares-desc">${description}</span></span>
						<div class="shopping-car-show_list_button">
						<a href="javascript:" class="shopping-car-show_list_minus canteen-icon canteen-minus-icon"></a>
						<span class="shopping-car-show_list_count">${count}</span>
						<a href="javascript:" class="shopping-car-show_list_add canteen-icon canteen-add-icon"></a>
						</div>
						<span class="shopping-car-show_list_price canteen-icon canteen-rmb-icon">${totalPrice}</span>
						</div>`;
					listDiv.innerHTML = listHtml;
					wrap.appendChild(listDiv.children[0].cloneNode(true));
				}
				this.shoppingData();
			},
			/**
			 * 购物车展示栏 添加减少 具体方法调用展示模块
			 */
			shoppingBasketMealadd() {
				let me = this;
				let basket = this.domBox.shopping_basket;
				let uid = "";
				let meal = null;   //界面中所有存在该商品的第一个dom
				let mealTarget = null;
				let count = 0;
				let unitPrice = 0;
				
				this.eventBind(basket, "touchend", function (event) {
					target = event.target;
					let isAdd = target.classList.contains("shopping-car-show_list_add");
					let isReduce = target.classList.contains("shopping-car-show_list_minus");
					if (isAdd) {
						
						uid = target.parentNode.parentNode.getAttribute("data-orderuid");
						meal = document.querySelector(`[data-prouid="${uid}"]`);
						if(meal.classList.contains('wares-with-option')){
							var $trolleyItem =  target.closest('.shopping-car-show_list');
							var price = parseFloat($trolleyItem.getAttribute('data-base-price'));
							me.shoppingCar(price, "add");
    						me.addOptionWares($trolleyItem, 1);
						}else{
							mealTarget = meal.querySelector(".canteen-meal-list_menu_add");
							unitPrice = parseFloat(meal.querySelector(".canteen-meal-list_menu_price").querySelector("span").textContent);
							//调用顺序不可乱
							me.mealCount(mealTarget, "add");
							me.shoppingBasket(uid, "add");
							me.shoppingCar(unitPrice, "add");
						}
						
					} else if (isReduce) {
						uid = target.parentNode.parentNode.getAttribute("data-orderuid");
						meal = document.querySelector(`[data-prouid="${uid}"]`);
						if(meal.classList.contains('wares-with-option')){
							var $trolleyItem =  target.closest('.shopping-car-show_list');
							var price = parseFloat($trolleyItem.getAttribute('data-base-price'));
							me.shoppingCar(price, "reduce");
    						me.addOptionWares($trolleyItem, -1);
						}else{
							mealTarget = meal.querySelector(".canteen-meal-list_menu_minus");
							unitPrice = parseFloat(meal.querySelector(".canteen-meal-list_menu_price").querySelector("span").textContent);
							//调用顺序不可乱
							me.mealCount(mealTarget, "reduce");
							me.shoppingBasket(uid, "reduce");
							me.shoppingCar(unitPrice, "reduce");
						}
					} else {
						return;
					}
					
				});
			},
			
			/**
			 * 购物车展示 的出现和隐藏
			 */
			toggleShoppingBasket() {
				let me = this;
				let shoppingCar = this.domBox.shoppingcar_toggle;
				let footerPage = this.domBox.shoppingcar_box;
				this.eventBind(shoppingCar, "touchend", function () {
					let status = footerPage.classList.contains("shopping-car_empty"); 
					if (!status) {
						let shoppingBasket = me.domBox.shopping_basket_wrap;
						shoppingBasket.classList.toggle("active");
						footerPage.classList.toggle("active");
						me.shadow();
					} else {
						return;
					}
				});
			},
			
			/**
			 * 购物车清空
			 */
			shoppingEmpty() {
				let me = this;
				let target = me.domBox.shoppingcar_empty;
				let footer = this.domBox.shoppingcar_box;
				let footerCount = this.domBox.total_count;
				let footerPrice = this.domBox.total_price;
				let shoppingCarWrap = this.domBox.shopping_basket_wrap;
				let shoppingCar = this.domBox.shopping_basket;
				let mealbuttons = this.domBox.shoppingcar_meal_lists;
				let shadow = this.domBox.shadow;
				me.eventBind(target, "touchend", function (event) {
					event.stopPropagation();
					event.preventDefault();
					//购物车底部栏初始化
					footer.classList.add("shopping-car_empty");
					footer.classList.remove("active");
					footerCount.textContent = 0;
					footerPrice.textContent = "购物车是空的";
					
					//购物车展示栏初始化
					shoppingCarWrap.classList.remove("active");
					shoppingCar.innerHTML = "";
					
					//右侧餐品展示栏初始化
					for( let i=0; i<mealbuttons.length; i++){
						// console.log(i);
						if(mealbuttons[i].classList.contains("active")){
							mealbuttons[i].classList.remove("active");
							mealbuttons[i].children[1].textContent = 0;
						} 
					}
					
					//遮罩层初始化
					shadow.classList.remove("active");
					me.shoppingData();
				});
			},
			
			/**
			 * 遮罩层显示隐藏方法
			 * @param { zindex? } ( zindex: 期望的遮罩层的zindex 可选 )
			 */
			shadow(zindex) {
				let shadow = this.domBox.shadow;
				if( zindex ){
					shadow.style.zIndex = zindex;
				}else{
					shadow.style.zIndex = 0;
				}       
				shadow.classList.toggle('active');
			},
			
			/**
			 * 遮罩层点击事件绑定
			 */
			shadowClick() {
				let me = this;
				let shadow = this.domBox.shadow;
				let footerPage = this.domBox.shoppingcar_box;
				me.eventBind(shadow, "touchend", function (event) {
					event.preventDefault();
					event.stopPropagation();
					let shoppingBasket = me.domBox.shopping_basket_wrap;
					let informationBox = me.domBox.information_alert;
					shoppingBasket.classList.remove("active");
					informationBox.classList.remove("active");
					footerPage.classList.remove("active");
					me.shadow();
				})
			},
			
			/**
			 * 数据传输模块
			 * orderDate 为 用户每次操作购物车后，购物车内的内容
			 */
			shoppingData() {
				let me = this;
				let basket = this.domBox.shopping_basket;
				let meals = basket.children;
				let orderData = {};
				for( let i=0; i<meals.length; i++){
					orderData['id_' + meals[i].getAttribute("data-twid")]  = {
						dwId	: meals[i].getAttribute("data-orderuid"),
						count	: meals[i].querySelector(".shopping-car-show_list_count").textContent,
						options	: meals[i].getAttribute('data-options')
					}
				}
				kanteen.triggerChange(orderData, function(tempTrolleyWaresData){
					for(var tempId in tempTrolleyWaresData){
						var $trolleyDom = me.domBox.shopping_basket.querySelector('[data-twid="' + tempId + '"]');
						if($trolleyDom){
							$trolleyDom.setAttribute('data-twid', tempTrolleyWaresData[tempId]);
						}
					}
				});
				console.log(orderData);
			},
			
	};
	
	
	
	document.onreadystatechange = function () {
		if (document.readyState === "interactive") {
			canteen_home_interaction.init();
			kanteen.ca = canteen_home_interaction;
			kanteen.afterInit(canteen_home_interaction);
		}
	}
	
	if(typeof define === 'function'){
		define(kanteen);
	}
	
	
})();