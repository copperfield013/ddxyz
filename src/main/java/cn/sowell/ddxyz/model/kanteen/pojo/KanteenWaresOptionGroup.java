package cn.sowell.ddxyz.model.kanteen.pojo;

import java.util.ArrayList;
import java.util.List;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class KanteenWaresOptionGroup {
	private PlainKanteenWaresOptionGroup plainGroup;
	private List<PlainKanteenWaresOption> optionList = new ArrayList<PlainKanteenWaresOption>();
	public PlainKanteenWaresOptionGroup getPlainGroup() {
		return plainGroup;
	}
	public void setPlainGroup(PlainKanteenWaresOptionGroup plainGroup) {
		this.plainGroup = plainGroup;
	}
	public List<PlainKanteenWaresOption> getOptionList() {
		return optionList;
	}
	public void setOptionList(List<PlainKanteenWaresOption> optionList) {
		this.optionList = optionList;
	}
	
	public Long getWaresId(){
		if(plainGroup != null){
			return plainGroup.getWaresId();
		}
		return null;
	}
	public Long getId(){
		if(plainGroup != null){
			return plainGroup.getId();
		}
		return null;
	}
	
	
	
	public JSONObject toJson(){
		if(plainGroup != null){
			JSONObject json = (JSONObject) JSON.toJSON(plainGroup);
			JSONArray options = new JSONArray();
			optionList.forEach(option->{
				options.add(JSON.toJSON(option));
			});
			json.put("options", options);
			return json;
		}
		return null;
	}
	
	
}
