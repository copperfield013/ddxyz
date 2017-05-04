define(function(require, exports, module){
	var OPTION_NAME_MAP = {
			'cupSize_2'		: '中杯',
			'cupSize_3'		: '大杯',
			'sweetness_3'	: '3分甜',
			'sweetness_5'	: '5分甜',
			'sweetness_7'	: '7分甜',
			'heat_1'		: '冰',
			'heat_2'		: '常温',
			'heat_3'		: '热'
		};
	function mapName(id, type){
		var name = OPTION_NAME_MAP[type + '_' + id];
		return name || '';
	}
	function initPage(){
		var order = new Order();
		//全局的用于存放当前选择的配送剩余的可用杯数
		var cupRemain = -1;
		//用于获取当前日期
		var date = new Date();
		//选择配送时间点
		$('#timePoint').change(function(){
			if((new Date()).getDate() != date.getDate()){
				alert('页面已过期，请重新刷新');
				return false;
			}
			if(order.getItems().length > 0){
				if(!confirm('更改配送时间点，将会移除所有已选的饮料，是否继续？')){
					//还原
					$(this).val(order.getDelivery().getTimePoint().getHours());
					return false;
				}
			}
			var value = $(this).val();
			var key = $('option[value="' + value + '"]', this).attr('data-key');
			var $dLocation = $('.delivery-location');
			$dLocation.removeClass('cview').val('').trigger('change', [true]);
			$('.cup-remain-wrapper').hide();
			cupRemain = -1;
			if(key){
				//根据配送时间点，显示对应的配送地点
				var $targetLocation = $dLocation.filter('[data-key="' + key + '"]');
				$targetLocation.addClass('cview');
				//设置对象的信息
				date.setHours(Number(value));
				order.getDelivery().setTimePoint(date);
			}
		}).trigger('change');
		
		//选择配送地点
		$('.delivery-location').change(function(e, ignoreConfirm){
			if(!ignoreConfirm && order.getItems().length > 0){
				if(!confirm('更改配送地点，将会移除所有已选的饮料，是否继续？')){
					//还原
					$(this).val(order.getDelivery().getLocation().getKey());
					return false;
				}
			}
			//重置饮料表单和订单
			reInitForm();
			$('.table-detail .data-row').remove();
			order.removeAllOrderItem();
			refreshOrderItems(order);
			$('.cup-remain-wrapper').hide();
			
			cupRemain = -1;
			var locationId = $(this).val();
			var deliveryId = $('option[value="' + locationId + '"]', this).attr('data-did')
			if(locationId && deliveryId){
				//构造配送地址选项对象
				var locationOpt = new OrderOption({
					key		: locationId,
					name	: $(this).text(),
					type	: 'location'
				});
				order.getDelivery().setLocation(locationOpt);
				//发起ajax查询该配送下的剩余配送数量
				var Ajax = require('ajax');
				Ajax.ajax('weixin/ydd/getDeliveryRemain', {
					deliveryId	: deliveryId
				}, function(json){
					if(!json.error){
						if(json.remain == 'unlimited'){
							cupRemain = Number.MAX_VALUE;
							$('#cup-remain').text('不限');
						}else{
							cupRemain = json.remain;
							$('#cup-remain').text(json.remain);
						}
						$('.cup-remain-wrapper').show();
					}
				});
			}
		});
		
		//选择饮料类型
		$('#drink-type').change(function(){
			var drinkTypeId = $(this).val(),
				$drinkTypeOption = $('option[value="' + drinkTypeId + '"]', this)
				;
			$('.tea-addition-type-wrapper')
				.removeClass('cview')
				.filter('[data-key="' + drinkTypeId + '"]')
				.addClass('cview');
			$('.addition-type-wrapper')
				.removeClass('cview')
				.filter('[data-key="' + drinkTypeId + '"]')
				.addClass('cview');
			
		});
		
		$('#addDrink').click(function(){
			var orderItem = new OrderItem();
			var $drinkType = $('#drink-type');
			var drinkTypeId = $drinkType.val(),
				$drinkTypeOption = $('option[value="' + drinkTypeId + '"]', $drinkType)
				;
			if(drinkTypeId){
				//设置饮料类型
				orderItem.setDrinkType(new OrderOption({
					key		: drinkTypeId,
					name	: $drinkTypeOption.text(),
					type	: 'drinkType',
					price	: Number($drinkTypeOption.attr('data-price'))
				}));
			}else{
				alertMsg('请选择饮料类型');
				$.error('没有选择饮料');
			}
			if($('p.tea-addition-type-wrapper[data-key="' + drinkTypeId + '"]').length > 0){
				//加茶
				var $teaAdditionType = $(':radio[name="teaAdditionType"]:checked');
				var teaAdditionTypeId = $teaAdditionType.val();
				if(teaAdditionTypeId){
					orderItem.setTeaAdditionType(new OrderOption({
						key		: $teaAdditionType.val(),
						name	: $teaAdditionType.next('label').text(),
						type	: 'teaAdditionType'
					}));
				}else{
					alertMsg('请选择添加茶类');
					$.error('没有选择加茶');
				}
			}
			
			//饮料规格
			var $cupSize = $(':radio[name="cupSize"]:checked'),
				cupSizeKey = $cupSize.val();
			if(cupSizeKey){
				orderItem.setCupSize(cupSizeKey);
			}else{
				alertMsg('请选择饮料是中杯还是大杯');
				$.error('没有选择饮料规格')
			}
			
			//甜度
			var $sweetness = $(':radio[name="sweetness"]:checked'),
				sweetnessKey = $sweetness.val();
			if(sweetnessKey){
				orderItem.setSweetness(sweetnessKey);
			}else{
				alertMsg('请选择饮料甜度');
				$.error('没有选择甜度');
			}
			
			//热度
			var $heat = $(':radio[name="heat"]:checked'),
				heatKey = $heat.val();
			if(heatKey){
				orderItem.setHeat(heatKey);
			}else{
				alertMsg('请选择饮料温度');
				$.error('没有选择热度');
			}
			
			//加料
			var $additionTypes = $(':checkbox.addition-type:checked');
			$additionTypes.each(function(){
				var additionKey = $(this).val(),
					additionName = $(this).next('label').text();
				orderItem.addAddition(new OrderOption({
					key		: additionKey,
					name	: additionName,
					type	: 'addition'
				}));
			});
			
			//杯数
			var cupCount = $('#cupCount').val();
			orderItem.setCupCount(cupCount);
			
			//添加条目
			order.addOrderItem(orderItem);
			//刷新显示的订单条目
			refreshOrderItems(order);
			//重置条目的表单
			reInitForm();
			var utils = require('utils');
			utils.scrollTo($('main'));
		});
		
		//提交并支付订单
		$('#pay-order').click(function(){
			//检查订单配送信息
			if(!order.getDelivery().getTimePoint()){
				return alertMsg('请选择配送时间档');
			}
			if(!order.getDelivery().getLocation()){
				return alertMsg('请选择配送地点');
			}
			var contactNum = $('#telphone').val();
			//检查订单收货人信息
			if(!contactNum){
				return alertMsg('请输入联系号码');
			}else{
				order.getReceiver().setContact(contactNum);
			}
			//检查订单内条目信息
			if(order.getItems().length == 0){
				return alertMsg('请至少添加一杯饮料后再提交订单');
			}
			if(order.getTotalCupCount() > cupRemain){
				return alertMsg('选择饮料总杯数大于该配送的当前可供余量');
			}
			showShade();
			//生成提交后台的JSON
			var reqJSON = order.toObject();
			var Ajax = require('ajax');
			//提交订单到后台
			Ajax.postJson('weixin/ydd/submitOrder', reqJSON, function(json){
				if(json != null && json.orderId){
					var OrderPay = require('order/order-pay');
					OrderPay.pay(json.orderId, function(){
						//后台支付成功
						alert('支付成功');
						location.href = 'weixin/ydd/orderList';
					}, function(){
						alert('没有支付');
						location.href = 'weixin/ydd/orderList';
					}, function(){
						location.href = 'weixin/ydd/orderList';
					});
				}else{
					//后台创建订单失败，显示错误信息
					alertMsg('创建订单失败');
					showShade(false);
				}
			});
		});
		
		/**
		 * 显示订单内的所有订单条目
		 */
		function refreshOrderItems(order){
			var items = order.getItems();
			var $rows = [];
			for(var i in items){
				var item = items[i];
				var $row = $('<ul class="data-row">');
				//饮料名
				$row.append('<li>' + item.getDrinkTypeName() + '</li>');
				var $descCell = $('<li>');
				//普通选项
				var teaAdditionTypeName = item.getTeaAdditionTypeName();
				$descCell.append('<p>'  
						+ (teaAdditionTypeName? (teaAdditionTypeName + '|'): '')
						+ item.getCupSizeName() 
						+ '|' + item.getSweetnessName()
						+ '|' + item.getHeatName() + '</p>');
				//加料
				var additionText = '';
				var additions = item.getAdditions();
				for(var j in additions){
					var addition = additions[j];
					additionText += '、' + addition.getName();
				}
				if(additionText !== ''){
					$descCell.append($('<p>').text('加料：' + additionText.substr(1)));
				}
				$row.append($descCell);
				//杯数
				$row.append('<li>' + item.getCupCount() + '</li>');
				//单价
				$row.append('<li>￥' + (item.getPerPrice() / 100).toFixed(2) + '</li>')
				//操作
				var $removeBtn = $('<a href="javascript:;" class="remove-item-btn">删除</a>');
				$removeBtn.click(function(){
					order.removeOrderItem(item);
					refreshOrderItems(order);
				});
				$row.append($('<li>').append($removeBtn));
				$rows.push($row);
			}
			$('.table-detail .data-row').remove();
			for(var i in $rows){
				$('.table-detail').append($rows[i]);
			}
			
			$('.total-price')
				.find('.count b').text(order.getTotalCupCount())
				.end()
				.find('.price b').text((order.getTotalPrice() / 100).toFixed(2))
				;
		}
		
		function reInitForm(){
			$('#drink-type').val('').trigger('change');
			$('#cupCount').val(1);
			$(':radio[name="teaAdditionType"]').each(removeCheck);
			$(':radio[name="cupSize"]').each(removeCheck);
			$(':radio[name="sweetness"]').each(removeCheck);
			$(':radio[name="heat"]').each(removeCheck);
			$(':checkbox.addition-type').each(removeCheck);
		}
		function removeCheck(){
			$(this).prop('checked', false)
					.next('label').removeClass('checked')
					;
			
		}
		
		function alertMsg(msg){
			alert(msg);
		}
		
		var $shade = $('<div class="screen-shade">');
		function showShade(show){
			if(show === false){
				$shade.remove();
			}else{
				$shade.appendTo($('body'));
			}
		}
		
		/**
		 * 根据数据初始化订单，并且显示在页面上
		 */
		function initOrder(_param){
			var defaultParam = {
				//配送时间点
				deliveryTimePoint	: 13,
				//配送地点
				deliveryLocationId	: 1,
				//订单条目
				items				: [
				     {
				    	 //饮料类型id
				    	 drinkTypeId		: 1,
				    	 //加茶类型id
				    	 teaAdditionTypeId	: 1,
				    	 //规格
				    	 cupSizeKey			: 2,
				    	 //温度
				    	 heatKey			: 1,
				    	 //加料的id数组
				    	 additionIds		: [1, 2, 3]
				     },
				     {
				    	 //饮料类型id
				    	 drinkTypeId		: 2,
				    	 //加茶类型id
				    	 teaAdditionTypeId	: 1,
				    	 //规格
				    	 cupSizeKey			: 2,
				    	 //温度
				    	 heatKey			: 1,
				    	 //加料的id数组
				    	 additionIds		: [1, 2, 3]
				     }
				]
			};
			var param = _param
			var timePointKey = 
				$('#timePoint').val(param.deliveryTimePoint).trigger('change')
					.find('option[value="' + param.deliveryTimePoint + '"]').attr('data-key');
			if(timePointKey){
				$('.delivery-location[data-key="' + timePointKey + '"]').val(param.deliveryLocationId).trigger('change');
				
				
			}
			
			
		}
		
		
		
	};
	
	
	/**
	 * 配送信息
	 */
	function Delivery(_param){
		var defaultParam = {
			location	: null,
			timePoint	: null
		};
		
		
		var param = $.extend({}, defaultParam, _param);
		/**
		 * 设置配送时间点
		 * @param dateTime ${Date}
		 */
		this.setTimePoint = function(dateTime){
			if(dateTime instanceof Date){
				param.timePoint = dateTime;
			}
		};
		
		this.getTimePoint = function(){
			return param.timePoint;
		};
		
		/**
		 * 设置配送地点
		 */
		this.setLocation = function(location){
			if(location instanceof OrderOption && location.getType() === 'location'){
				param.location = location;
			}
		};
		
		this.getLocation = function(){
			return param.location;
		}
		/**
		 * 将当前配送对象转换成简单对象
		 */
		this.toObject = function(){
			var timePoint = param.timePoint;
			return {
				deliveryLocationId 	: param.location.getKey(),
				timePoint	: {
					year 	: timePoint && timePoint.getFullYear(),
					month 	: timePoint && timePoint.getMonth(),
					date 	: timePoint && timePoint.getDate(),
					hour 	: timePoint && timePoint.getHours()
				}
			};
		};
	}
	/**
	 * 收货人信息
	 */
	function Receiver(_param){
		var defaultParam = {
			contact		: '',
			name		: '',
			address		: ''
		};
		
		var param = $.extend({}, defaultParam, _param);
		
		this.setContact = function(contact){
			param.contact = contact;
		};
		
		this.setName = function(name){
			param.name = name;
		};
		
		this.setAddress = function(address){
			param.address = address;
		}
		
		this.toObject = function(){
			return $.extend({}, param);
		}
	}
	
	
	
	function Order(_param){
		var defaultParam = {
			delivery	: new Delivery(),
			receiver	: new Receiver(),
			comment		: ''
		};
		
		
		var param = $.extend({}, defaultParam, _param);
		var orderItems = [];
		/**
		 * 获得所有订单条目
		 */
		this.getItems = function(){
			return orderItems;
		}
		/**
		 * 添加订单条目
		 * @param orderItem {OrderItem}
		 */
		this.addOrderItem = function(orderItem){
			if(orderItem instanceof OrderItem){
				orderItems.push(orderItem);
			}
		};
		/**
		 * 移除订单条目
		 * @param index {int} 订单条目索引， 0-base
		 */
		this.removeOrderItem = function(index){
			if(index instanceof OrderItem){
				for(var i = 0; i < orderItems.length; i++){
					if(orderItems[i] === index){
						orderItems.splice(i, 1);
						return;
					}
				}
			}else{
				orderItems.splice(index, 1);
			}
		}
		/**
		 * 移除订单所有条目
		 */
		this.removeAllOrderItem = function(){
			orderItems.splice(0, orderItems.length);
		};
		
		/**
		 * 获得配送对象
		 */
		this.getDelivery = function(){
			return param.delivery;
		};
		
		this.getReceiver = function(){
			return param.receiver;
		}
		/**
		 * 获得备注
		 */
		this.getComment = function(){
			return param.comment;
		};
		/**
		 * 设置备注
		 */
		this.setComment = function(comment){
			param.comment = comment;
		}
		/**
		 * 计算订单总价
		 */
		this.getTotalPrice = function(){
			var totalPrice = 0;
			for(var i in orderItems){
				var item = orderItems[i];
				totalPrice += item.getPerPrice() * item.getCupCount();
			}
			return totalPrice;
		};
		/**
		 * 计算订单总杯数
		 */
		this.getTotalCupCount = function(){
			var totalCupCount = 0;
			for(var i in orderItems){
				var item = orderItems[i];
				totalCupCount += item.getCupCount();
			}
			return totalCupCount;
		}
		
		this.toObject = function(){
			var obj = this.getDelivery().toObject();
			obj.receiver = this.getReceiver().toObject();
			obj.totalPrice = this.getTotalPrice();
			obj.items = [];
			for(var i in orderItems){
				obj.items.push(orderItems[i].toObject());
			}
			obj.comment = this.getComment();
			return obj;
		}
		
		
	}
	
	function OrderItem(_param){
		var defaultParam = {
			wares			: new OrderOption({
				key		: 1,
				name	: '饮料',
				type	: 'wares'
			}),
			drinkType		: null,
			cupCount		: 0,
			teaAdditionType	: null,
			cupSize			: null,
			sweetness		: null,
			heat			: null,
			perPrice		: 0,
			additionMap		: {}
		};
		
		
		
		var param = $.extend({}, defaultParam, _param);
		/**
		 * 设置商品信息
		 */
		this.setWares = function(wares){
			if(wares instanceof OrderOption && wares.getType() === 'wares' || wares == null){
				param.wares = wares; 
			}
		};
		
		this.getWaresName = function(){
			return param.wares && param.wares.etName();
		};
		/**
		 * 设置饮料类型
		 */
		this.setDrinkType = function(drinkType){
			if(drinkType instanceof OrderOption && drinkType.getType() === 'drinkType' || drinkType == null){
				param.drinkType = drinkType; 
			}
		};
		this.getDrinkTypeName = function(){
			return param.drinkType && param.drinkType.getName();
		}
		/**
		 * 设置加茶信息
		 */
		this.setTeaAdditionType = function(teaAdditionType){
			if(teaAdditionType instanceof OrderOption && teaAdditionType.getType() === 'teaAdditionType' || teaAdditionType == null){
				param.teaAdditionType = teaAdditionType; 
			}
		};
		this.getTeaAdditionTypeName = function(){
			return param.teaAdditionType && param.teaAdditionType.getName();
		}
		
		/**
		 * 设置订单条目的杯数
		 */
		this.setCupCount = function(cupCount){
			param.cupCount = Number(cupCount);
		};
		/**
		 * 获得订单条目的杯数
		 */
		this.getCupCount = function(){
			return param.cupCount;
		}
		/**
		 * 设置甜度
		 */
		this.setSweetness = function(sweetnessKey){
			param.sweetness = Number(sweetnessKey); 
		};
		
		this.getSweetnessName = function(){
			return mapName(param.sweetness, 'sweetness');
		} 
		/**
		 * 
		 */
		this.setHeat = function(heatKey){
			param.heat = Number(heatKey);
		};
		/**
		 * 
		 */
		this.getHeatName = function(){
			return mapName(param.heat, 'heat');
		}
		
		/**
		 * 
		 */
		this.setCupSize = function(sizeKey){
			param.cupSize = Number(sizeKey);
		};
		/**
		 * 
		 */
		this.getCupSizeName = function(){
			return mapName(param.cupSize, 'cupSize');
		}
		/**
		 * 
		 */
		this.getPerPrice = function(){
			var price = 0;
			if(param.drinkType){
				price += param.drinkType.get('price');
			}
			//大杯加3元
			if(param.cupSize == 3){
				price += 300;
			}
			
			
			return price;
		};
		this.getAdditions = function(){
			var additions = [];
			for(var key in param.additionMap){
				additions.push(param.additionMap[key]);
			}
			return additions;
		}
		/**
		 * 添加一个加料
		 */
		this.addAddition = function(addition){
			if(addition instanceof OrderOption && addition.getType() === 'addition'){
				param.additionMap['addition_' + addition.getKey()] = addition;
			}
		}
		/**
		 * 移除一个加料
		 */
		this.removeAddition = function(additionKey){
			param.additionMap['addition_' + additionKey] = undefined;
		}
		
		
		this.toObject = function(){
			var obj = {
				waresId			: param.wares && param.wares.getKey(),
				waresName		: param.wares && param.wares.getName(),
				drinkTypeId		: param.drinkType && param.drinkType.getKey(),
				drinkTypeName	: param.drinkType && param.drinkType.getName(),
				teaAdditionId	: param.teaAdditionType && param.teaAdditionType.getKey(),
				teaAdditionName	: param.teaAdditionType && param.teaAdditionType.getName(),
				cupCount		: param.cupCount,
				sweetness		: param.sweetness,
				heat			: param.heat,
				cupSize			: param.cupSize,
				perPrice		: this.getPerPrice(),
				additions		: []
			};
			for(var key in param.additionMap){
				var additionType = param.additionMap[key];
				if(additionType){
					obj.additions.push({
						typeId	: additionType.getKey(),
						name	: additionType.getName()
					});
				}
			}
			return obj;
		}
	}
	/**
	 * 订单条目选项
	 */
	function OrderOption(_param){
		var defaultParam = {
			key		: null,
			name	: null,
			type	: null
		};
		
		var param = $.extend(param, defaultParam, _param);
		this.getKey = function(){
			return param.key;
		};
		
		this.getName = function(){
			return param.name;
		};
		/**
		 * 订单条目选项类型，当前有以下几项
		 * 1.饮料类型 drinkType
		 * 2.加茶 teaAdditionType
		 * 3.规格 cupSize
		 * 4.甜度 sweetness
		 * 5.热度 heat
		 */
		this.getType = function(){
			return param.type;
		};
		/**
		 * 
		 */
		this.get = function(key){
			return param[key];
		}
		
	}
	
	initPage();
	
});