<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
	.item-operate{
		float: right;
	}
	.item-operate i{
		font-size: 2em;
		padding: 0 3px;
		cursor: pointer;
	}
	.item-operate i.fa{
		display: none;
	}
	.dd2-content:HOVER .item-operate i.fa{
		display: inline;
	}
	.item-operate i.fa:HOVER{
		font-weight: bold;
	}
	.option-price-positive:BEFORE{
		content: '+￥';
	}
	.option-price-negative:BEFORE{
		content: '-￥';
	}
	input#optiongroup-name, input.optionName, input.additionPrice,
	input#optiongroup-name:FOCUS, input.optionName:FOCUS, input.additionPrice:FOCUS {
	    height: 100%;
	    border-color: #efefef;
	    background-color: inherit;
	    width: 15em;
	}
	#cancel-edit{
		display: none;
	}
	.group-item.item-disabled .fa-hand-o-right:BEFORE {
		content: "\f05e";
	}
	
}
</style>
<title>商品选项-${wares.name }</title>
<div id="wares-options-${wares.id }">
	<div class="page-header">
		<div class="header-title">
			<h1>商品选项-${wares.name }</h1>
		</div>
	</div>
	<div class="page-body">
		<form confirm="确认保存商品的选项？" class="bv-form form-horizontal validate-form">
			<input type="hidden" name="waresId" value="${wares.id }" />
			<div class="form-group">
				<label class="col-lg-2 control-label">商品名</label>
				<div class="col-lg-5">
					<input class="form-control" type="text" readonly="readonly" value="${wares.name }" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-lg-2 control-label">选项</label>
				<div class="col-lg-5">
					<div class="option-tree dd dd-draghandle" style="background-color: #efefef;padding: 5px;">
						<ol class="dd-list group-list">
							<c:forEach items="${groupList }" var="group">
								<c:set var="options" value="${optionMap[group.id] }" />
								<c:if test="${options != null && !empty options }">
									<li class="dd-item dd2-item group-item ${group.disabled == 1? 'item-disabled':'' }" data-id="${group.id }">
										<div class="dd-handle dd2-handle">
											<i class="normal-icon fa fa-hand-o-right"></i>
											<i class="drag-icon fa fa-arrows-alt"></i>
										</div>
										<div class="dd2-content">
											<span class="content-title group-name">${group.title }</span>
											<span class="item-operate">
												<i class="fa fa-plus-circle add-option"></i>
												<i class="fa fa-trash-o del-optiongroup"></i>
												<i title="${group.required == 1? '选项组必选': '选项组非必选' }" class="iconfont toggle-required ${group.required == 1? 'icon-flash_on': 'icon-flash_off' }"></i>
												<i title="${group.multiple == 1? '多选': '单选' }" class="iconfont toggle-multiple ${group.multiple == 1? 'icon-leiappmulti': 'icon-leiappsingle' }"></i>
											</span>
										</div>
										<ol class="dd-list option-list">
											<c:forEach items="${options }" var="option">
												<li class="dd-item dd2-item option-item ${option.disabled == 1? 'item-disabled':'' }" data-id="${option.id }">
													<div class="dd-handle dd2-handle">
														<i class="normal-icon fa fa-smile-o"></i>
														<i class="drag-icon fa fa-arrows-alt"></i>
													</div>
													<div class="dd2-content">
														<span class="content-title option-name">${option.name }</span>
														<span class="item-operate">
															<c:if test="${option.additionPrice != 0 }">
																<span class="${option.additionPrice > 0? 'option-price-positive': 'option-price-negative'}"><fmt:formatNumber value="${(option.additionPrice > 0? option.additionPrice: -option.additionPrice )/ 100 }" pattern="0.00" /> </span>
															</c:if>
															<i class="fa fa-trash-o del-option"></i>
														</span>
													</div>
												</li>
											</c:forEach>
										</ol>
									</li>
								</c:if>
							</c:forEach>
						</ol>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-lg-offset-2 col-lg-2">
					<input type="button" id="add-optiongroup" data-role="add" class="btn btn-primary btn-block" value="添加商品组" />
				</div>
				<div class="col-lg-2" id="cancel-edit">
					<input type="button" class="btn btn-default  btn-block" value="取消" />
				</div>
				<div class="col-lg-2">
					<input type="submit" id="submit" class="btn btn-default btn-block" value="提交" />
				</div>
			</div>
		</form>
	</div>
	<c:set var="optionItem">
		<li class="dd-item dd2-item option-item">
			<div class="dd-handle dd2-handle">
				<i class="normal-icon fa fa-smile-o"></i>
				<i class="drag-icon fa fa-arrows-alt"></i>
			</div>
			<div class="dd2-content option-content">
				<span class="content-title option-name"><input type="text" class="optionName" /></span>
			</div>
		</li>
	</c:set>
	<script id="option-model" type="jquery-tmpl">${optionItem}</script>
	<script id="groupoption-model" type="jquery-tmpl">
		<li class="dd-item dd2-item group-item" id="to-create">
			<div class="dd-handle dd2-handle">
				<i class="normal-icon fa fa-hand-o-right"></i>
				<i class="drag-icon fa fa-arrows-alt"></i>
			</div>
			<div class="dd2-content optiongroup-content">
				<span class="content-title group-name"><input type="text" id="optiongroup-name" /></span>
			</div>
			<ol class="dd-list option-list">
				${optionItem}
			</ol>
		</li>
	</script>
</div>
<script type="text/javascript">
	seajs.use(['dialog', 'ajax', 'utils'], function(Dialog, Ajax, Utils){
		var $page = $('#wares-options-${wares.id}');
		console.log($page);
		var $optionTree = $('.option-tree', $page);
			$root = $('ol:first', $optionTree);
		var backupOrder = [];
		$('form', $page).on('cpf-submit', function(e){
			submitOptionTree();
			e.doCancel();
		});
		$page.on('click', '.toggle-multiple', function(){
			Utils.switchClass($(this), 'icon-leiappmulti', 'icon-leiappsingle');
			Utils.toggleAttr(this, 'title', '多选', '单选')
		});
		$page.on('click', '.toggle-required', function(){
			Utils.switchClass($(this), 'icon-flash_on', 'icon-flash_off');
			Utils.toggleAttr(this, 'title', '选项组必选', '选项组非必填')
		});
		$page.on('dblclick', 'li.option-item', function(){
			if($('.additionPrice', this).length == 0){
				var $span = $('.option-price-positive,.option-price-negative', this);
				var $text = $('<input type="text" class="additionPrice" placeholder="选择选项时会添加的价格" />');
				if($span.length > 0){
					var additionPrice = ($span.is('.option-price-negative')? '-':'') + $span.text();
					$span.replaceWith($text.val(additionPrice));
					$text.focus().select();
				}else{
					$text.prependTo($('.item-operate', this)).focus();
				}
				return false;
			}
		});
		$page.on('click', function(e){
			if(!$(e.target).is(':text.additionPrice')){
				$(':text.additionPrice', $page).each(function(){
					var $this = $(this);
					var price = parseFloat($this.val());
					if(!isNaN(price) && price != 0){
						var $span = $('<span class="option-price-positive">').text(Math.abs(price).toFixed(2)).toggleClass('option-price-negative', price < 0);
						$this.replaceWith($span);
					}else{
						$this.remove();
					}
				});
			}
		});
		//在每次拖动之前调用方法记录当前的节点顺序
		$page.on('nestable-dragstart', '.dd-handle', function(){
			backupOrder = []
			$root.children('li.group-item').each(function(i){
				var $li = $(this);
				var options = [];
				$li.data('group-item-id', i);
				$li.find('li.option-item').each(function(j){
					$(this).data('parent-item-id', i);
					options.push(this);
				});
				backupOrder.push({
					li		: this,
					options	: options
				});
			});
			console.log(backupOrder);
			return false;
		});
		$('.option-tree', $page).nestable({maxDepth:2}).nestable('expandAll').change(function(e){
			console.log(e);
			var flag = true;
			//不允许越级
			$root.find('li.group-item').each(function(){
				if($(this).parent().is('.option-list')){
					flag = false;
					return false;
				}
			});
			if(flag){
				$root.find('li.option-item').each(function(){
					var $optionLi = $(this);
					var $parent = $optionLi.parent(); 
					if($parent.is('.group-list')){
						flag = false;
						return false;
					}else{
						var pId = $optionLi.data('parent-item-id');
						var $optionGroup = $optionLi.closest('li.group-item');
						var groupItemId = $optionGroup.data('group-item-id');
						if(pId !== groupItemId){
							flag = false;
							return false;
						}
					}
					
				});
			}
			if(!flag){
				resetOption();
			}
		});
		//重置选项树
		function resetOption(){
			$root.empty();
			for(var i in backupOrder){
				var groupLi =  backupOrder[i].li,
					options = backupOrder[i].options;
				$root.append(groupLi);
				var $optionList = $('ol.option-list', groupLi).empty();
				if($optionList.length == 0){
					$optionList = $('<ol class="dd-list option-list">').appendTo(groupLi);
				}
				for(var j in options){
					$optionList.append(options[j]);
				}
			}
		}
		//创建选项组
		$('#add-optiongroup', $page).click(function(){
			var dataRole = $(this).attr('data-role');
			if(dataRole === 'add'){
				addOptionGroup();
				toggleEditMode(true);
			}else if(dataRole === 'confirmed'){
				if(saveOptionGroup() || saveOption()){
					toggleEditMode();
				}
			}
		});
		//文本框回车时保存
		$page.on('keypress', '#optiongroup-name,:text.optionName,:text.additionPrice', function(e){
			if(e.keyCode === 13){
				if($(e.target).is('.additionPrice')){
					$(e.target).parent().trigger('click');
				}else{
					$('#add-optiongroup', $page).trigger('click');
				}
			}else if(e.keyCode === 9 && $(e.target).is('.optionName')){
				$(this).closest('li.group-item').find('.add-option').trigger('click');
			}
		});
		//取消创建选项组
		$('#cancel-edit :button', $page).click(function(){
			var $toCreate = $('#to-create', $page);
			$toCreate.remove();
			toggleEditMode();
		});
		//删除商品选组或选项
		$page.on('click', '.del-optiongroup, .del-option', function(){
			var $this = $(this);
			Dialog.confirm('确认删除？', function(yes){
				if(yes){
					$this.closest('li').remove();
				}
			});
		});
		//添加选项
		$page.on('click', '.add-option', function(){
			var $li = $(this).closest('li');
			var $ol = $li.find('ol:first');
			var $option = $('#option-model', $page).tmpl();
			$ol.append($option);
			toggleEditMode(true);
			$option.find(':text').trigger('focus');
		});
		//切换编辑选项状态
		function toggleEditMode(edit){
			var $addoptiongroup = $('#add-optiongroup', $page);
			if(edit){
				$addoptiongroup.attr('data-role', 'confirmed').attr('value', '保存');
				$('#cancel-edit', $page).show();
				$('#submit', $page).hide().attr('disabled', 'disabled');
			}else{
				$addoptiongroup.attr('data-role', 'add').attr('value', '添加商品组');
				$('#cancel-edit', $page).hide();
				$('#submit', $page).show().removeAttr('disabled');
			}
		}
		function addOptionGroup(){
			var $optionGroup = $('#groupoption-model', $page).tmpl();
			$root.append($optionGroup);
		}
		function saveOptionGroup(){
			var $toCreate = $('#to-create', $page);
			var $optiongroupContent = $('.optiongroup-content'),
				$optionContent = $('.option-content');
			var $groupName = $(':text', $optiongroupContent),
				$optionName = $(':text', $optionContent);
			var groupName = $groupName.val(),
				optionName = $optionName.val();
			if(groupName && optionName){
				$groupName.replaceWith(groupName);
				$optionName.replaceWith(optionName);
				$optiongroupContent.append(
							'<span class="item-operate">' +
							'<i class="fa fa-plus-circle add-option"></i>' +
							'<i class="fa fa-trash-o del-optiongroup"></i>' + 
							'<i title="选项组非必选" class="iconfont toggle-required icon-flash_off"></i>' +
							'<i title="单选" class="iconfont toggle-multiple icon-leiappsingle"></i>' +
							'</span>'); 
				$optionContent.after('<span class="item-operate"><i class="fa fa-trash-o del-option"></i></span>');
				$toCreate.removeAttr('id');
				return true;
			}
		}
		function saveOption(){
			var flag = true;
			var texts = [];
			$(':text.optionName', $page).each(function(){
				var $optionName = $(this);
				var optionName = $optionName.val();
				if(optionName){
					texts.push($optionName);
				}else{
					return flag = false;
				}
			});
			if(flag){
				for(var i in texts){
					var $optionName = texts[i];
					var $optionContent = $optionName.closest('.content-title');
					var optionName = $optionName.val();
					$optionName.replaceWith(optionName);
					$optionContent.after('<span class="item-operate"><i class="fa fa-trash-o del-option"></i></span>');
				}
			}
			return flag;
		}
		
		function submitOptionTree(){
			var data = [];
			$('li.group-item', $page).each(function(){
				var $groupItem = $(this),
					options = [];
				var groupItem = {
					id			: $groupItem.attr('data-id'),
					name		: $groupItem.find('.group-name').text(),
					isMulti		: $('.toggle-multiple', this).is('.icon-leiappmulti'),
					isRequired	: $('.toggle-required', this).is('.icon-flash_on'),
					options		: options
				};
				$('li.option-item', $groupItem).each(function(){
					var $optionItem = $(this);
					var $additionPrice = $('.option-price-positive,.option-price-negative', this);
					var additionPrice = 0;
					if($additionPrice.length > 0){
						var price = parseFloat($additionPrice.text());
						if(price && !isNaN(price)){
							additionPrice = parseInt(price * ($additionPrice.is('.option-price-negative')? -100: 100));
						}
					}
					options.push({
						id				: $optionItem.attr('data-id'),
						name			: $optionItem.find('.option-name').text(),
						rule			: '',
						additionPrice	: additionPrice
					});
				});
				data.push(groupItem);
			});
			
			Ajax.postJson('admin/kanteen/wares/option_update',{
				waresId		: parseInt('${wares.id}'),
				optionData	: data
			}, function(data){
				try{
					var res = new Ajax.AjaxPageResponse(data);
					res.doAction($page.getLocatePage());
				}catch(e){
					console.error(e);
				}
			});
		}
		
	});
</script>