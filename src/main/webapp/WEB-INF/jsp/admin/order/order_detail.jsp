<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<style>
	#order-${order.id } .order-table-cell{
		display: block;
		width: 100%;
	}
	#order-${order.id } .item-detail{
		display: table;
   	 	width: 45%;
   	 	border: 1px solid #FF0000;
   	 	margin-right: 10px;
   	 	margin-top: 20px;
	}
	#order-${order.id } .left{
		float:left;
	}
	#order-${order.id } .right{
		float:left;
	}
	#order-${order.id } .item-detail>dl{
		display: table-row;
   		width: 100%;
	}
	#order-${order.id } .item-detail>dl>dt, .item-detail>dl>dd{
		border-bottom: dashed 1px #ddd;
	    height: 2em;
	    line-height: 2em;
	    vertical-align: middle;
	}
	#order-${order.id } .item-detail>dl>dt{
		display: table-cell;
	    width: 8em;
	    text-align: center;
	}
	#order-${order.id } .item-detail>dl>dd{
		display: table-cell;
		padding: 0 0.5em;
	}
</style>
<div class="detail" id="order-${order.id }">
	<input id="orderIdHidden" type="hidden" name="id" value="${order.id }">
	<dl>
		<dt>配送地点</dt>
		<dd>${plainOrder.locationName }</dd>
	</dl>
	<dl>
		<dt>联系方式</dt>
		<dd>${plainOrder.receiverContact }</dd>
	</dl>
	<dl>
		<dt>订单原价</dt>
		<dd>${plainOrder.totalPrice/100 }元</dd>
	</dl>
	<dl>
		<dt>支付金额</dt>
		<dd>${plainOrder.actualPay/100 }元</dd>
	</dl>
	<dl>
		<dt>总杯数</dt>
		<dd>${cupCountMap[plainOrder.id] }</dd>
	</dl>
	<dl>
		<dt>下单时间</dt>
		<dd><fmt:formatDate value="${plainOrder.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></dd>
	</dl>
	<dl>
		<dt>支付时间</dt>
		<dd><fmt:formatDate value="${plainOrder.payTime }" pattern="yyyy-MM-dd HH:mm:ss"/></dd>
	</dl>
	<dl>
		<dt></dt>
		<dd>
			<div class="order-table-cell">
				<c:forEach items="${orderDrinkItemMap[plainOrder] }" var="orderDrinkItem" varStatus="status">
					<div class="item-detail ${status.count%2 == 1? 'left' : 'right' }">
						<dl>
							<dt>饮料类型</dt>
							<dd>${orderDrinkItem.drinkName }</dd>
						</dl>
						<dl>
							<dt>添加茶类</dt>
							<dd>${orderDrinkItem.teaAdditionName }</dd>
						</dl>
						<dl>
							<dt>规格</dt>
							<dd>${cupSizeMap[orderDrinkItem.cupSize] }</dd>
						</dl>
						<dl>
							<dt>甜度</dt>
							<dd>${sweetnessMap[orderDrinkItem.sweetness] }</dd>
						</dl>
						<dl>
							<dt>冰度</dt>
							<dd>${heatMap[orderDrinkItem.heat] }</dd>
						</dl>
						<dl>
							<dt>加料</dt>
							<dd>
								<c:forEach items="${addtionMap[orderDrinkItem] }" var="addition">
									<label>${addition.additionTypeName }</label>
								</c:forEach>
							</dd>
						</dl>
					</div>
				</c:forEach>
			</div>
		</dd>
	</dl>
</div>