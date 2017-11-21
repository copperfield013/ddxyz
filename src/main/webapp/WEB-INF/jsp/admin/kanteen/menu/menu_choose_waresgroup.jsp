<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
	#menu_choose_waresgroup input[type=checkbox] {
	    opacity: 1;
	    left: 0;
	    position: relative;
	    width: inherit;
	    height: inherit;
	}
	#menu_choose_waresgroup tr.choose-row{
		cursor: pointer;
	}
	#menu_choose_waresgroup .table-hover tr.choose-row.selected{
		background-color: #87CEEB;
		color: #fff;
	}
	#menu_choose_waresgroup tr.choose-row.selected:HOVER td{
		color: #000;
		background-color: #BBFFFF !important;
	}
	#menu_choose_waresgroup table.table{
		margin-bottom: 10px;
	}
</style>
<div id="menu_choose_waresgroup">
	<div>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>序号</th>
					<th>商品组名</th>
					<th>描述</th>
					<th>商品数</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="waresGroup" items="${waresGroupList }" varStatus="i">
					<tr data-id="${waresGroup.waresGroupId }" class="choose-row">
						<td>${i.index + 1 }</td>
						<td>${waresGroup.waresGroupName }</td>
						<td>${waresGroup.description }</td>
						<td>${waresGroup.waresCount }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
	</div>
</div>
<div class="modal-footer">
	<div class="row">
		<div class="col-lg-3 col-lg-offset-4">
			<input id="submit" class="btn btn-primary btn-block submit" type="button" value="确定" /> 
		</div>
	</div>
</div>
<script>
	$(function(){
		seajs.use(['utils', 'dialog'], function(Utils, Dialog){
			var $page = $('#menu_choose_waresgroup'),
				page = $page.getLocatePage();
			$('#check-all', $page).change(function(){
				var checked = $(this).prop('checked');
				var $table = $(this).closest('table');
				$(':checkbox', $table).prop('checked', checked);
			});
			$('tr.choose-row').click(function(){
				$(this).closest('tr.choose-row').toggleClass('selected');
			});
			var waresInfo = {};
			try{
				waresInfo = $.parseJSON('${waresGroupInfo}');
			}catch(e){}
			
			page.bind('footer-submit', function(data){
				var data = [];
				$('.choose-row.selected', $page).each(function(){
					var waresId = $(this).attr('data-id');
					if(waresId){
						data.push(waresInfo['group_' + waresId]);
					}
				});
				return data;
			});
			
		});
	});
</script>

