/**
 * cpf标签页控件
 * 拦截a标签和form标签的跳转动作，转换为在标签页中打开新的页面
 */
define(function(require, exports, module){
	var 
		$CPF = require('$CPF'),
		Page = require('page'),
		Ajax = require('ajax'),
		utils= require('utils')
	;
	//id-tab的map
	var tabMap = {},
		nextTab = 1
		;
	/**
	 * 设置参数
	 */
	$CPF.addDefaultParam({
		tabIdGenerate	: function(){
			return 'tab_' + nextTab++;
		},
		//默认打开的标签页的名称
		defautTabTitle	: 'new Tab',
		tabTitleClass	: 'main-tab-title',
		//标签页的class
		tabClass		: 'main-tab-content',
		tabWrapperClass	: 'main-tab-content-wrapper',
		//用于将tab对象绑定到标签页的标题dom中
		tabTitleDataKey	: 'tab-title-data-key',
		//从标签页content的data中获取标签页对象的key
		//tabDataKey		: 'CPF_TAB_KEY',
		defaultTab		: new $CPF.DeferParam(function(){
			var tab = new Tab({
				id		: 'cpf-home-tab',
				$title	: $('a[href="#cpf-home-tab"]').closest('li.main-tab-title'),
				$content: $('#cpf-home-tab')
			});
			tabMap[tab.getId()] = tab;
			return tab;
		})
	});
	
	/**
	 * CPF插件调用init后会加载该函数
	 */
	$CPF.putInitSequeue(1, function(){
		/**
		 * init时就为左右移动标签的a绑定事件
		 */	
		moveTab();
	});
	
	/**
	 * CPF在加载页面的时候会第10个执行该函数
	 */
	$CPF.putPageInitSequeue(10, function($page){
		require('utils').scrollTo($($page), 0);
		bindPageTabEvent($page);
	});
	
	function Tab(_param){
		var defaultParam = {
			id				: utils.uuid(5, 32),
			title			: $CPF.getParam('defautTabTitle'),
			content			: '',
			onPageLoad		: $.noop,
			onClose			: $.noop,
			$title			: undefined,
			$content		: undefined
		};
		var param = $.extend({}, defaultParam, _param);
		var id = param.id,
			title = param.title,
			_this = this,
			url, formData;
		var tabDomObj;
		if(param.$title && param.$content){
			tabDomObj = {
				$title 	: param.$title,
				$content: param.$content
			};
			title = $('a', param.$title).text();
		}else{
			tabDomObj = buildTabDomObj(id, title);
		}
		
		var page = new Page({
			type		: 'tab',
			id			: id,
			pageObj		: this,
			$content	: tabDomObj.$content,
			$container	: tabDomObj
		});
		
		tabDomObj.$title.data($CPF.getParam('tabTitleDataKey'), this);
		this.getId = function(){
			return id;
		};
		this.getPage = function(){
			return page;
		}
		
		this.getTitleDom = function(){
			return tabDomObj.$title;
		};
		this.getContent = function(){
			return tabDomObj.$content;
		}
		/**
		 * 判断当前标签页是否是游离状态的（没有插入到标签组中）
		 */
		this.isFree = function(){
			return !tabMap[this.getId()];
		};
		this.getTitle = function(){
			return title;
		};
		this.setTitle = function(_title){
			var $title = $('a', this.getTitleDom()).text(_title);
			if($title.length > 0){
				title = _title;
			}
			return this;
		};
		/**
		 * 异步加载内容到当前标签页中
		 */
		this.loadContent = function(content, _title, _formData){
			var free = this.isFree();
			if(typeof content === 'string'){
				url = content;
				formData = _formData;
				if(!free && this.isActive()){
					$CPF.showLoading();
				}
				Ajax.ajax(url, formData, {
					page		: page,
					whenSuc		: function(data, dataType){
						if(dataType === 'html'){
							_this.loadContent($('<div>').html(data));
						}
					},
					afterLoad	: function(){
						$CPF.closeLoading();
					}
				});
			}else if(content instanceof $){
				if(!free && this.isActive()){
					$CPF.showLoading();
				}
				var $title = content.children('title').first();
				if($title.length === 1){
					$title.remove();
					var __title = $title.text();
					if(__title){
						_title = title;
					}
				}
				this.getContent().html(content.html());
				$CPF.initPage(_this.getContent());
				$CPF.closeLoading();
			}
			if(_title){
				this.setTitle(_title);
			}
			return this;
		};
		/**
		 * 刷新页面，必须从url获得内容
		 */
		this.refresh = function(){
			this.loadContent(url, title, formData);
		};
		/**
		 * 将当前标签插入到页面当中
		 * @param tab 已经在页面中的标签对象，将会把当前标签页插入到这个标签页后面
		 * 				如果不传入tab，那么将标签页插入到最后一个标签页中
		 */
		this.insert = function(tab, toActivate){
			if(typeof tab === 'boolean'){
				toActivate = tab;
				tab = undefined;
			}else if(toActivate == undefined){
				toActivate = true;
			}
			tab = tab || Tab.getLastTab();
			if(tabMap[tab.getId()]){
				var warpWidth = parseFloat(tab.getTitleDom().parent().parent().css("width"));
				var nextWidth = parseFloat(this.getTitleDom().text().length-1)*13+42;
				var ulWidth = parseFloat(tab.getTitleDom().parent().css("width"));
				var totalWidth = nextWidth+ulWidth-1;
				var moveWidth = totalWidth -warpWidth +34;
				tab.getTitleDom().parent().css("width",totalWidth);
				if(totalWidth >= warpWidth){
					$('a.move').css("display","block");
					tab.getTitleDom().parent().css("left",-moveWidth);
				}
				//前面的标签已经显示
				this.getTitleDom().insertAfter(tab.getTitleDom());	
				this.getContent().insertAfter(tab.getContent());
				var origin = tabMap[this.getId()];
				if(origin){
					origin.close();
				}
				//放到容器中
				tabMap[this.getId()] = this;
				Page.putPage(this.getId(), page);
				if(toActivate){
					this.activate();
				}
			}
			return this;
		};
		/**
		 * 激活当前标签
		 */
		this.activate = function(){
			if($(".move").css("display") === "block"){
				var ulDom = $("#main-tab-title-container")
				var ulWidth = parseFloat(ulDom.css("width"));
				var warpWidth =parseFloat(ulDom.parent().css("width"));
				var thisWidth =parseFloat(this.getTitleDom().css("width"));
				var index = this.getTitleDom().index();
				var excursion = 0;
				var leftLimit = parseFloat(ulDom.css("left"));
				for(var i=0 ;i<=index-1;i++){
					excursion += ulDom.children()[i].offsetWidth;
				}
				if(excursion <-leftLimit){
					ulDom.css('left',34-excursion);
					console.log("iam in left");
				}else if( excursion+thisWidth+leftLimit > warpWidth){
					ulDom.css('left',-(excursion+2+thisWidth-warpWidth+34));
					console.log("i am in right");
				}	
			}
			var $title = this.getTitleDom();
			$('a', $title).trigger('click');
			return this;
		};
		/**
		 * 判断当前标签页是否有被激活
		 */
		this.isActive = function(){
			return this.getTitleDom().is('.active');
		};
		/**
		 * 关闭标签
		 */
		this.close = function(activateTabId){
			var ulDom = $("#main-tab-title-container")
			var warpWidth = parseFloat(ulDom.parent().css("width"));
			var result = param.onClose(this);
			var closeWidth = param.title.length*13+42;
			var ulWidth = parseFloat(ulDom.css("width"));
			var ulLeft = parseFloat(ulDom.css("left"));
			var finalWidth = ulWidth - closeWidth+1;
			console.log(warpWidth);
			console.log(closeWidth);
			console.log(ulWidth);
			console.log(ulLeft);
			console.log(finalWidth);
			if(result === false){
				return this;
			}
			var activateTab;
			if(activateTabId){
				activateTab = tabMap[activateTabId];
			}
			if(activateTab){
				//存在关闭后要激活的标签
				activateTab.activate();
			}else{
				//不存在关闭后要激活的标签，那么根据策略来激活前后的标签
				var nextTab = this.getNextTab();
				if(nextTab){
					nextTab.activate();
				}else{
					var prevTab = this.getPrevTab();
					if(!(prevTab instanceof Tab)){
						$CPF.getParam('defaultTab').activate();
					}else{
						prevTab.activate();
					}
				}
			}
			
			//关闭并且移除当前标签对象
			tabMap[this.getId()] = undefined;
			this.getTitleDom().remove();
			this.getContent().remove();
			this.destruct();
			ulDom.css("width",finalWidth);
			if(finalWidth >= warpWidth){
				ulDom.css("left",ulLeft-closeWidth)
			}
			if(finalWidth < warpWidth){
				ulDom.css("left",0);
				$('a.move').css("display","none");
				ulDom.css("marginLeft",0);
			}
			return this;
		};
		/**
		 * 获得前一个标签页
		 */
		this.getPrevTab = function(){
			var $prevTitle = this.getTitleDom().prev('.' + $CPF.getParam('tabTitleClass'));
			if($prevTitle.length == 1){
				return $prevTitle.data($CPF.getParam('tabTitleDataKey'));
			}
		};
		/**
		 * 获得后一个标签页
		 */
		this.getNextTab = function(){
			var $nextTitle = this.getTitleDom().next('.' + $CPF.getParam('tabTitleClass'));
			if($nextTitle.length == 1){
				return $nextTitle.data($CPF.getParam('tabTitleDataKey'));
			}
		};
		this.destruct = function(){
			Page.remove(id);
		};
	
	}
	
	
	/**
	 * 左右移动标签页绑定事件
	 */
	function moveTab(){
		$('.move').on("click",function(e){
			var ulDom = $("#main-tab-title-container");
			var ulWidth = parseFloat(ulDom.css("width"));
			var leftWidth = parseFloat(ulDom.css("left"));
			var warpWidth = parseFloat(ulDom.parent().css("width"));
			var moveDistance = parseFloat(ulDom.children().last().css("width"));
			if($(this).hasClass("left")){
				if(-leftWidth<moveDistance){
					ulDom.css("left",34)
				}else{
					ulDom.css("left",leftWidth+moveDistance)
				}
			}else if($(this).hasClass("right")){
				if(ulWidth+leftWidth-34-warpWidth < moveDistance){
					ulDom.css("left",-(ulWidth+34-warpWidth))	
				}else{
					ulDom.css("left",leftWidth-moveDistance);	
				}	
			}
		})
	}
	
	/**
	 * 构造标签页对象
	 * 如果传入的参数中tabId为空，那么将根据配置的tabId生成一个
	 * 如果根据tabId找到标签已经存在，那么将覆盖原来的title后返回原来构造的标签对象
	 */
	function buildTabDomObj(id, tabTitle){
		if(id){
			var tabId = $CPF.getParam('tabIdGenerate')(id);
			return {
				$title : 
					$('<li>').addClass($CPF.getParam('tabTitleClass'))
					.append(
							$('<a>').attr('data-toggle', 'tab')
							.attr('href', '#' + tabId)
							.text(tabTitle)
					).append(
							$('<span>×</span>').click(function(){
								Tab.closeTab(id);
							})
					),
				$content :
					$('<div>').addClass('tab-pane ' + $CPF.getParam('tabClass'))
					.attr('id', tabId)
					.append('<div class="' + $CPF.getParam('tabWrapperClass') + '"></div>')
			};
		}
	}
	
	
	/**
	 * 绑定标签页面事件
	 */
	function bindPageTabEvent($page){
		//阻止跳转
		$('a[href]', $page).click(function(e){
			  e.preventDefault();
		});
		/**
		 * 将a标签天跳转页面修改为在标签页中打开
		 */
		$('a.tab[href]', $page).click(function(){
			var _this = $(this);
			var uri = _this.attr('href'),
				tabId = _this.attr('target'),
				title = _this.attr('title')
				;
			var li = $(_this).closest('.sidebar-menu li');
			if(li.length > 0){
				$('li.active', li.closest('.sidebar-menu')).removeClass('active');
				li.addClass('active');
			}
			if(uri.startsWith('admin/')){
				try{
					Tab.openInTab(uri, tabId, title);
				}catch(e){
					console.error(e);
				}finally{
					return false;
				}
			}else if(uri.startsWith('#')){
				return false;
			}
		});
	};
	
	
	$.extend(Tab, {
		openInTab	: function(url, tabId, title){
			var tab = Tab.getTab(tabId);
			if(!tab){
				tab = new Tab({
					id		: tabId,
					title	: title
				});
				tab.insert();
			}
			tab.loadContent(url, title)
				.activate();
			return tab;
		},
		closeTab	: function(tabId){
			var tab = Tab.getTab(tabId);
			if(tab){
				tab.close();
			}
		},
		getLastTab	: function(){
			return $('.' + $CPF.getParam('tabTitleClass'), $('#main-tab-title-container')).last().data($CPF.getParam('tabTitleDataKey'));
		},
		getTab 		: function(tabId){
			return tabMap[tabId];
		}
	});
	
	
	
	/**
	 * 返回模块对象
	 */
	module.exports = Tab;
});