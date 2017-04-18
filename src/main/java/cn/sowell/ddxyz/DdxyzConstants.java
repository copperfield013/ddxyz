package cn.sowell.ddxyz;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("serial")
public interface DdxyzConstants {
	Integer VALUE_TRUE = 1;
	String COMMON_SPLITER = ";";
	Map<Integer, String> CUP_SIZE_MAP = new LinkedHashMap<Integer, String>(){
		{
			put(2, "中杯");
			put(3, "大杯");
		}
	};
	
	Map<Integer, String> SWEETNESS_MAP = new LinkedHashMap<Integer, String>(){
		{
			put(3, "3分甜");
			put(5, "5分甜");
			put(7, "7分甜");
		}
	};
	
	Map<Integer, String> HEAT_MAP = new LinkedHashMap<Integer, String>(){
		{
			put(1, "冰");
			put(2, "常温");
			put(3, "热");
		}
	};
}
