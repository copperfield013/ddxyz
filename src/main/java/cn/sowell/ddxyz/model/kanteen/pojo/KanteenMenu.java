package cn.sowell.ddxyz.model.kanteen.pojo;

import java.util.List;
import java.util.Map;

public class KanteenMenu {
	private PlainKanteenMenu plainMenu;
	
	
	public Map<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>> menuItemMap;
	
	public PlainKanteenMenu getPlainMenu() {
		return plainMenu;
	}

	public void setPlainMenu(PlainKanteenMenu plainMenu) {
		this.plainMenu = plainMenu;
	}

	public Map<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>> getMenuItemMap() {
		return menuItemMap;
	}

	public void setMenuItemMap(
			Map<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>> menuItemMap) {
		this.menuItemMap = menuItemMap;
	}
	
}
