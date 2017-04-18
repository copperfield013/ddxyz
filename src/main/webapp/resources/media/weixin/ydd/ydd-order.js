define(function(require, exports, module){
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
			var value = $(this).val();
			var key = $('option[value="' + value + '"]', this).attr('data-key');
			if(key){
				//根据配送时间点，显示对应的配送地点
				var $dLocation = $('.delivery-location');
				var $targetLocation = $dLocation.filter('[data-key="' + key + '"]');
				$dLocation.removeClass('cview').val('');
				$targetLocation.addClass('cview');
				//设置对象的信息
				date.setHours(Number(value));
				order.getDelivery().setTimePoint(date);
			}
			console.log(order.toObject());
		}).trigger('change');
		
		//选择配送地点
		$('.delivery-location').change(function(){
			var deliveryId = $(this).val();
			if(deliveryId){
				//构造配送地址选项对象
				var locationOpt = new OrderOption({
					key		: deliveryId,
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
					}
				});
			}
			console.log(order.toObject());
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
		/*//选择加茶
		$(':radio[name="teaAdditionType"]').click(function(){
			
		});
		//选择规格
		$(':radio[name="cupSize"]').click(function(){
			var cupSizeKey = $(this).val();
			currentOrderItem.setCupSize(cupSizeKey);
			console.log(order.toObject());
		});
		
		$(':radio[name="sweetness"]').click(function(){
			var sweetnessKey = $(this).val();
			currentOrderItem.setSweetness(sweetnessKey);
			console.log(order.toObject());
		});
		
		$(':radio[name="heat"]').click(function(){
			var heatKey = $(this).val();
			currentOrderItem.setHeat(heatKey);
			console.log(order.toObject());
		});
		
		$(':checkbox.addition-type').click(function(){
			var toCheck = $(this).prop('checked');
			var additionKey = $(this).val();
			if(toCheck){
				var additionName = $(this).next('label').text();
				currentOrderItem.addAddition(new OrderOption({
					key		: additionKey,
					name	: additionName,
					type	: 'addition'
				}));
			}else{
				currentOrderItem.removeAddition(additionKey);
			}
			console.log(order.toObject());
		});*/
		
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
				$.error('没有选择饮料');
			}
			//加茶
			var $teaAdditionType = $(':radio[name="teaAdditionType"]:checked');
			var teaAdditionTypeId = $teaAdditionType.val();
			if(teaAdditionTypeId){
				orderItem.setTeaAdditionType(new OrderOption({
					key		:$teaAdditionType.val(),
					name	: $teaAdditionType.next('label').text(),
					type	: 'teaAdditionType'
				}));
			}else{
				$.error('没有选择加茶');
			}
			
			//饮料规格
			var $cupSize = $(':radio[name="cupSize"]:checked'),
				cupSizeKey = $cupSize.val();
			if(cupSizeKey){
				orderItem.setCupSize(cupSizeKey);
			}else{
				$.error('没有选择饮料规格')
			}
			
			//甜度
			var $sweetness = $(':radio[name="sweetness"]:checked'),
				sweetnessKey = $sweetness.val();
			if(sweetnessKey){
				orderItem.setSweetness(sweetnessKey);
			}else{
				$.error('没有选择甜度');
			}
			
			//热度
			var $heat = $(':radio[name="heat"]:checked'),
				heatKey = $heat.val();
			if(heatKey){
				orderItem.setHeat(heatKey);
			}else{
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
			
			order.addOrderItem(orderItem);
			refreshOrderItems(order);
			console.log(order.toObject());
		});
		
	};
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
			$descCell.append('<p>' 
					+ item.getTeaAdditionName() 
					+ '|' + item.getCupSizeName() 
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
			//杯数
			$row.append('<li>' + item.getCupCount() + '</li>');
			//单价
			$row.append('<li>' + item.getPerPrice() + '</li>')
			//操作
			$row.append('<li><a href="javascript:;">删除</a></li>');
			$rows.push($row);
		}
		$('.table-detail .data-row').remove();
		for(var i in $rows){
			$('.table-detail').append($rows[i]);
		}
	}
	
	
	
	/**
	 * 配送信息
	 */
	function Delivery(_param){
		var defaultParam = {
			location	: new OrderOption(),
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
		/**
		 * 设置配送地点
		 */
		this.setLocation = function(location){
			if(location instanceof OrderOption && location.getType() === 'location'){
				param.location = location;
			}
		};
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
			orderItems.splice(index, 1);
		}
		
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
			
		};
		
		this.toObject = function(){
			var obj = this.getDelivery().toObject();
			obj.receiver = this.getReceiver().toObject();
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
		/**
		 * 设置饮料类型
		 */
		this.setDrinkType = function(drinkType){
			if(drinkType instanceof OrderOption && drinkType.getType() === 'drinkType' || drinkType == null){
				param.drinkType = drinkType; 
			}
		};
		/**
		 * 设置加茶信息
		 */
		this.setTeaAdditionType = function(teaAdditionType){
			if(teaAdditionType instanceof OrderOption && teaAdditionType.getType() === 'teaAdditionType' || teaAdditionType == null){
				param.teaAdditionType = teaAdditionType; 
			}
		};
		/**
		 * 设置订单条目的杯数
		 */
		this.setCupCount = function(cupCount){
			param.cupCount = Number(cupCount);
		};
		/**
		 * 设置甜度
		 */
		this.setSweetness = function(sweetnessKey){
			param.sweetness = Number(sweetnessKey); 
		};
		/**
		 * 
		 */
		this.setHeat = function(heatKey){
			param.heat = Number(heatKey);
		};
		/**
		 * 
		 */
		this.setCupSize = function(sizeKey){
			param.cupSize = Number(sizeKey);
		};
		/**
		 * 
		 */
		this.getPerPrice = function(perPrice){
			return param.perPrice;
		};
		
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