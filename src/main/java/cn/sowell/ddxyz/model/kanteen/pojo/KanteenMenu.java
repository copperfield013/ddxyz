package cn.sowell.ddxyz.model.kanteen.pojo;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class KanteenMenu {
	private PlainKanteenMenu plainMenu;
	
	
	public Map<PlainKanteenWaresGroup, List<KanteenDistributionMenuItem>> menuItemMap;
	
	private Map<Long, List<KanteenWaresOptionGroup>>  waresOptionGroupMap;
	
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
	
	/**
	 * 将选项转换成前台可以使用的json
	 * @return
	 */
	public JSONObject getOptionGroupJson(){
		JSONObject json = new JSONObject();
		String waresKeyPrefix = "wares_";
		json.put("waresKeyPrefix", waresKeyPrefix);
		if(menuItemMap != null){
			menuItemMap.values().forEach(items->{
				items.forEach(item->{
					List<KanteenWaresOptionGroup> optionGroups = waresOptionGroupMap.get(item.getWaresId());
					if(optionGroups != null){
						JSONObject data = new JSONObject();						
						json.put(waresKeyPrefix + item.getWaresId(), data);
						
						JSONArray groups = new JSONArray();
						data.put("groups", groups);
						data.put("wares", JSON.toJSON(item));
						
						optionGroups.forEach(optionGroup->groups.add(optionGroup.toJson()));
					}
					
				});
			});
		}
		return json;
	}
	
	public JSONObject getCountLimitedWares(){
		JSONObject json = new JSONObject();
		String waresKeyPrefix = "deliveryWares_";
		json.put("waresKeyPrefix", waresKeyPrefix);
		if(menuItemMap != null){
			menuItemMap.values().forEach(items->{
				items.forEach(item->{
					if(item.getMaxCount() != null){
						JSONObject data = new JSONObject();
						data.put("maxCount", item.getMaxCount());
						data.put("currentCount", item.getCurrentCount());
						json.put(waresKeyPrefix + item.getDistributionWaresId(), data);
					}
				});
			});
		}
		return json;
	}
	

	public Map<Long, List<KanteenWaresOptionGroup>> getWaresOptionGroupMap() {
		return waresOptionGroupMap;
	}

	public void setWaresOptionGroupMap(
			Map<Long, List<KanteenWaresOptionGroup>> waresOptionGroupMap) {
		this.waresOptionGroupMap = waresOptionGroupMap;
	}
	
}
