/**
 * 分页插件模块
 */
define(function(require, exports, module){
	var $CPF = require('$CPF'),
		Utils = require('utils')
		;
	/**
	 * 添加默认方法
	 */
	$CPF.addDefaultParam({
		//分页器的class
		paginatorClass	: 'cpf-paginator',
		//分页器jQuery对象找到对应pageInfo的data key
		pageInfoKey		: 'PAGE_INFO_KEY',
		//从分页器的元素获得分页信息的方法
		pageInfoGetter	: function($target){
			return {
				pageNo	: Number($target.attr('pageNo')),
				pageSize: Number($target.attr('pageSize')),
				count	: Number($target.attr('count'))
			};
		},
		//根据分页信息对象构造分页的jQuery对象
		paginatorBuilder: buildPaginator,
		//WARNING: maxPageCount-lastPageCount必须为偶数
		//如果不是偶数，那么在超过maxPageCount的时候，将显示maxPageCount-1页
		//分页最多显示的页数，包含省略号前面的页号和后面的页号，至少为5
		maxPageCount	: 5,
		//分页超过最多显示的页数时，省略号后面的页号，至少为1
		lastPageCount	: 1,
		//分页器显示的每页条数
		pageSizeOptions	: [5, 10, 15, 20],
		paginatorGoPage	: $.noop
	});
	/**
	 * 添加页面初始化
	 */
	$CPF.putPageInitSequeue(2, initPaging);
	
	
	/**
	 * 初始化页面中的分页插件
	 */
	function initPaging($page){
		var paginatorClass = $CPF.getParam('paginatorClass');
		$('.' + paginatorClass, $page).each(function(){
			var pageInfo = $CPF.getParam('pageInfoGetter')($(this));
			if(Utils.isInteger(pageInfo.pageNo) && Utils.isInteger(pageInfo.pageSize)&& Utils.isInteger(pageInfo.count)){
				//构造分页
				var paginator = $CPF.getParam('paginatorBuilder')(pageInfo);
				if(paginator){
					paginator
						.data($CPF.getParam('pageInfoKey'), paginator)
						.appendTo($(this).empty());
						;
				}
			}else{
				$.error('pageNo, pageSize, count must be integer');
			}
		});
	}
	/**
	 * 根据分页信息对象构造分页的jQuery对象
	 */
	function buildPaginator(pageInfo){
		if(!pageInfo || pageInfo.count == 0){
			return null;
		}
		var pageCount = Math.ceil(pageInfo.count / pageInfo.pageSize);
		//
		var frontShowBegin = 1, frontShowEnd = pageCount, endShowBegin = undefined;
		//最多显示的页码个数
		var maxPageCount = $CPF.getParam('maxPageCount');
		//构造页数跳转器
		var paginatorJumpLi = $('<li class="cpf-paginator-jump">');
		
		
		//构造分页器的每页条数选择器
		var pageSizeSelect = $('<select>');
		
		if(pageCount > maxPageCount){
			//超过最大显示页数时，只显示部分页数
			var lastPageCount = $CPF.getParam('lastPageCount');
			//前面显示的页数必须是奇数，如果不是奇数，将会自动减1
			var frontPageCount = maxPageCount - lastPageCount - 1;
			if(frontPageCount%2 == 0){
				frontPageCount--;
			}
			//扣除最后页数和省略符号，剩余的是当前页号旁边显示的页号
			var halfFrontShowCount = (frontPageCount + 1) / 2;
			var pageNo = pageInfo.pageNo;
			if(pageNo <= halfFrontShowCount){
				//在前半段，能直接显示第1个页号
				frontShowEnd = frontPageCount;
			}else if(pageNo >= pageCount - halfFrontShowCount){
				//在后半段，能直接显示到最后一个页号
				frontShowBegin = pageCount - maxPageCount + 1;
				frontShowEnd = pageCount;
			}else{
				//在中间，只显示当前页号旁边的几个页号和最后几个页号
				frontShowBegin = pageNo - halfFrontShowCount + 1;
				frontShowEnd = pageNo + halfFrontShowCount - 1;
			}
			if(frontShowEnd < pageCount - lastPageCount){
				endShowBegin = pageCount - lastPageCount + 1;
			}
		}
		var ul = $('<ul>').addClass('pagination pagination-sm');
		if(pageInfo.pageNo > 1){
			ul.append($('<li><a href="#">«</a></li>'));
		}
		for(var i= frontShowBegin; i <= frontShowEnd; i++){
			buildPagingLi(i).appendTo(ul).addClass(i == pageInfo.pageNo? 'active': undefined);
		}
		if(endShowBegin){
			ul.append($('<li><a href="#">...</a></li>'));
			for(var i = endShowBegin; i <= pageCount; i++){
				buildPagingLi(i).appendTo(ul);
			}
		}
		if(pageInfo.pageNo < pageCount){
			ul.append($('<li><a href="#">»</a></li>'));
		}
		//将分页器的页面跳转控件放到ul中
		ul.append(paginatorJumpLi
				.append($('<input type="text">')
							.addClass('cpf-paginator-jump-text')
							.val(pageInfo.pageNo)
						)
				.append($('<button type="button" >')
							.addClass('cpf-paginator-jump-button btn-default btn')
							.text('go')
							.click(function(){
								//点击按钮时跳转页面
								var pageNo = $(this).val();
								goPage(pageNo, pageSizeSelect.val());
								return false;
							})
						)
			);
		//构造分页器的每页条数选项
		var  pageSizeOptions = $CPF.getParam('pageSizeOptions')
		if(!$.inArray(pageInfo.pageSize, pageSizeOptions)){
			pageSizeSelect.append('<option value="' + pageInfo.pageSize + '">' + pageInfo.pageSize + '</option>');
		}
		for(var i in pageSizeOptions){
			pageSizeSelect.append('<option value="' + pageSizeOptions[i] + '">' + pageSizeOptions[i] + '</option>');
		}
		pageSizeSelect.val(pageInfo.pageSize);
		ul.prepend($('<li class="cpf-paginator-pagesize">')
				.append(pageSizeSelect)
			);
		//修改每页条数时，触发跳转
		pageSizeSelect.change(function(){
			var pageSize = $(this).val();
			var pageNo = Number(ul.find('li.active a').text());
			goPage(pageNo, pageSize);
		});
		return ul
	}
	
	function buildPagingLi(pageNo){
		var a = $('<a href="#">' + pageNo + '</a>');
		a.on('click', function(){
			var goPageNo = Number($(this).text());
			goPage(goPageNo);
		});
		var li = $('<li>').append(a);
		return li;
	}
	
	
	/**
	 * 页面跳转
	 */
	function goPage(pageNo, pageSize){
		$CPF.getParam('paginatorGoPage')({
			pageNo	: pageNo,
			pageSize: pageSize
		});
	}
});