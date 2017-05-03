package cn.sowell.ddxyz.model.main;

import java.util.LinkedHashMap;
import java.util.Map;

public interface AdminMainConstants {
	@SuppressWarnings("serial")
	final Map<String, String> ERROR_CODE_MAP = new LinkedHashMap<String, String>(){
		{
			put("1", "用户名或密码错误");
			put("2", "");
		}
	};
}
