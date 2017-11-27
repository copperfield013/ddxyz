package cn.sowell.ddxyz.model.kanteen.pojo.adminItem;

import cn.sowell.ddxyz.model.canteen.pojo.PlainKanteenDelivery;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenMenu;

public class KanteenDeliveryItem {
	private PlainKanteenDelivery delivery;
	private PlainKanteenMenu menu;
	private Integer orderCount;
	public PlainKanteenDelivery getDelivery() {
		return delivery;
	}
	public void setDelivery(PlainKanteenDelivery delivery) {
		this.delivery = delivery;
	}
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public PlainKanteenMenu getMenu() {
		return menu;
	}
	public void setMenu(PlainKanteenMenu menu) {
		this.menu = menu;
	}

}
