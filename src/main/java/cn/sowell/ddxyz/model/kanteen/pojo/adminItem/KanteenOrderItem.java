package cn.sowell.ddxyz.model.kanteen.pojo.adminItem;

import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenOrder;

public class KanteenOrderItem {
	private PlainKanteenOrder order;
	private PlainKanteenDelivery delivery;

	public PlainKanteenOrder getOrder() {
		return order;
	}

	public void setOrder(PlainKanteenOrder order) {
		this.order = order;
	}

	public PlainKanteenDelivery getDelivery() {
		return delivery;
	}

	public void setDelivery(PlainKanteenDelivery delivery) {
		this.delivery = delivery;
	}
	
}
