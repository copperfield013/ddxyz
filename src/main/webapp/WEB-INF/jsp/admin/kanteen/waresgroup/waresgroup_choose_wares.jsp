<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
	#waresgroup_choose_wares input[type=checkbox] {
	    opacity: 1;
	    left: 0;
	    position: relative;
	    width: inherit;
	    height: inherit;
	}
	#waresgroup_choose_wares tr.choose-row{
		cursor: pointer;
	}
	#waresgroup_choose_wares .table-hover tr.choose-row.selected{
		background-color: #87CEEB;
		color: #fff;
	}
	#waresgroup_choose_wares tr.choose-row.selected:HOVER td{
		color: #000;
		background-color: #BBFFFF !important;
	}
	#waresgroup_choose_wares table.table{
		margin-bottom: 10px;
	}
</style>
<div id="waresgroup_choose_wares">
	<div>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>序号</th>
					<th>商品名</th>
					<th>单价</th>
					<th>价格单位</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="wares" items="${waresList }" varStatus="i">
					<tr data-id="${wares.id }" class="choose-row">
						<td>${i.index + 1 }</td>
						<td>${wares.name }</td>
						<td><fmt:formatNumber value="${wares.basePrice / 100}" pattern="0.00" /> </td>
						<td>${wares.priceUnit }</td>
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
			var $page = $('#waresgroup_choose_wares'),
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
				waresInfo = $.parseJSON('${waresInfo}');
			}catch(e){}
			
			page.bind('footer-submit', function(data){
				var data = [];
				$('.choose-row.selected', $page).each(function(){
					var waresId = $(this).attr('data-id');
					if(waresId){
						data.push(waresInfo['wares_' + waresId]);
					}
				});
				return data;
			});
			
		});
	});
</script>

