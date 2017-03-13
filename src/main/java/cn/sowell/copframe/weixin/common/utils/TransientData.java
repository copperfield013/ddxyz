package cn.sowell.copframe.weixin.common.utils;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import cn.sowell.copframe.utils.HttpRequestUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * <p>Title: TransientData</p>
 * <p>Description: </p><p>
 * 线程同步地获取并保存从服务器获取的数据
 * </p>
 * @author Copperfield Zhang
 * @date 2017年3月12日 下午11:07:17
 * @param <T>
 */
public class TransientData<T> {

	/**
	 * 请求数据的地址
	 */
	private String reqURL;
	/**
	 * 是否已经初始化
	 */
	private Boolean inited = false;
	/**
	 * 数据值对象
	 */
	private T value;
	
	
	private String returnDataType = "json";
	private String valueKey;
	private String expiredInKey = "expires_in";
	
	
	/**
	 * 最长超时时间
	 */
	private long expiresIn;
	/**
	 * 最后一次获取数据的时间
	 */
	private long lastGetDataTime;
	
	
	Logger logger = Logger.getLogger(TransientData.class);
	
	public boolean hasInit(){
		synchronized (inited) {
			return inited;
		}
	}
	
	public void init(String reqURL, String valueKey) {
		Assert.hasText(reqURL);
		synchronized (inited) {
			this.reqURL = reqURL;
			this.valueKey = valueKey;
			this.inited = true;
		}
	}

	@SuppressWarnings("unchecked")
	public T getValue() {
		if(inited){
			synchronized (this) {
				if(this.value == null || (System.currentTimeMillis() - this.lastGetDataTime) > this.expiresIn){
					if("json".equals(returnDataType)){
						JSONObject json = HttpRequestUtils.postAndReturnJson(reqURL, null);
						String val = json.getString(valueKey);
						if(val != null){
							this.value = (T) val;
							this.expiresIn = json.getLong(expiredInKey) * 1000;
							this.lastGetDataTime = System.currentTimeMillis();
						}
					}
				}
			}
		}
		return this.value;
	}

}
