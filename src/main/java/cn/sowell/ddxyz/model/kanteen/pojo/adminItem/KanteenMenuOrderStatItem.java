package cn.sowell.ddxyz.model.kanteen.pojo.adminItem;

import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenDistributionWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWares;
import cn.sowell.ddxyz.model.kanteen.pojo.PlainKanteenWaresGroup;

public class KanteenMenuOrderStatItem {
	private final PlainKanteenDistributionWares dWares;
	private final PlainKanteenWares wares;
	private final PlainKanteenWaresGroup group;
	public KanteenMenuOrderStatItem(PlainKanteenDistributionWares dWares,
			PlainKanteenWares wares, PlainKanteenWaresGroup group) {
		super();
		this.dWares = dWares;
		this.wares = wares;
		this.group = group;
	}
	public String getWaresName() {
		return wares.getName();
	}
	public Integer getUnitPrice() {
		return wares.getBasePrice();
	}
	public String getPriceUnit() {
		return wares.getPriceUnit();
	}
	public Integer getCurrentCount() {
		return dWares.getCurrentCount();
	}
	public Integer getMaxCount() {
		return dWares.getMaxCount();
	}
	public Integer getDisabledDistribution() {
		return dWares.getDisabled();
	}
	public Long getDistributionWaresId() {
		return dWares.getId();
	}
	public String getWaresGroupName() {
		return group.getName();
	}
	
	
}
