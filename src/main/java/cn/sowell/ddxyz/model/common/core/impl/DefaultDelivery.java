package cn.sowell.ddxyz.model.common.core.impl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.util.Assert;

import cn.sowell.ddxyz.model.common.core.Delivery;
import cn.sowell.ddxyz.model.common.core.DeliveryLocation;
import cn.sowell.ddxyz.model.common.core.DeliveryManager;
import cn.sowell.ddxyz.model.common.core.DeliveryTimePoint;
import cn.sowell.ddxyz.model.common.core.DispenseCode;
import cn.sowell.ddxyz.model.common.core.DispenseResourceRequest;
import cn.sowell.ddxyz.model.common.core.OrderDispenseResource;
import cn.sowell.ddxyz.model.common.pojo.PlainDelivery;

public class DefaultDelivery implements Delivery{

	private PlainDelivery pDelivery = new PlainDelivery();

	private DeliveryLocation location;
	private TreeMap<Integer, DispenseCode> usableDispenseCodeMap = new TreeMap<Integer, DispenseCode>();
	
	private DeliveryManager dManager;
	
	public DefaultDelivery(PlainDelivery pDelivery, DeliveryManager dManager) {
		this.pDelivery = pDelivery;
		this.dManager = dManager;
	}
	@Override
	public Serializable getId() {
		return pDelivery.getId();
	}
	@Override
	public DeliveryLocation getLocation() {
		if(location == null){
			Long locationId = pDelivery.getLocationId();
			location = dManager.getDeliveryLocation(locationId);
		}
		return location;
	}
	@Override
	public DeliveryTimePoint getTimePoint() {
		Date timepoint = pDelivery.getTimePoint();
		DeliveryTimePoint point = new DeliveryTimePoint(timepoint);
		return point;
	}
	@Override
	public Integer getMaxCount() {
		return pDelivery.getMaxCount();
	}
	@Override
	public int getCurrentCount() {
		return usableDispenseCodeMap.size();
	}
	@Override
	public int getStatus() {
		return pDelivery.getStatus();
	}
	
	@Override
	public boolean checkAvailable(int reqCount) {
		Integer maxCount = getMaxCount();
		//最大限制为null时，无论表示没有限制
		if(maxCount == null){
			return true;
		}else{
			return getCurrentCount() + reqCount <= maxCount;
		}
	}
	
	@Override
	public DispenseCode getDispenseCode(Serializable key) {
		return usableDispenseCodeMap.get(key);
	}
	
	@Override
	public void removeDispenseCode(DispenseCode dispenseCode) {
		Assert.notNull(dispenseCode);
		usableDispenseCodeMap.remove(dispenseCode.getKey());
	}
	
	@Override
	public OrderDispenseResource applyForDispenseResource(
			DispenseResourceRequest request) {
		int dCount = request.getDispenseCount();
		DefaultDispenseResource resource = new DefaultDispenseResource(dCount);
		synchronized (usableDispenseCodeMap) {
			if(checkAvailable(dCount)){
				//获得所有被取消的编号
				List<Integer> nos = getCanceledNos(dCount);
				int nosSize = nos.size(), lastNo = getLastNo();
				//如果被取消的编号不够用，那么继续从后面添加
				for(int i = 1; i <= dCount - nosSize; i++){
					nos.add(lastNo + i);
				}
				int i = 0;
				for (Integer no : nos) {
					if(i++ == dCount) break;
					DefaultDispenseCode code = new DefaultDispenseCode();
					code.setBelong(this);
					code.setKey(no);
					//生成分发号
					code.setCode(generateDispenseCode(no));
					resource.addDispenseCode(code);
					usableDispenseCodeMap.put(no, code);
				}
				resource.setLocked(true);
			}
		}
		return resource;
	}
	/**
	 * 根据编号生成分发号<br/>
	 * 分发号的规则为“时间点+配送点编号+编号”
	 * @param no
	 * @return
	 */
	private String generateDispenseCode(Integer no) {
		StringBuffer buffer = new StringBuffer();
		DeliveryTimePoint timePoint = getTimePoint();
		DeliveryLocation location = getLocation();
		DateFormat dft = new SimpleDateFormat("yyyyMMddHH");
		buffer.append(dft.format(timePoint.getDatetime()));
		buffer.append(location.getCode());
		NumberFormat nft = new DecimalFormat("000");
		buffer.append(nft.format(no));
		return buffer.toString();
	}
	/**
	 * 获得所有被取消的编号
	 * @maxCount 获得编号的个数
	 * @return
	 */
	private List<Integer> getCanceledNos(int maxCount) {
		List<Integer> result = new ArrayList<Integer>();
		Set<Integer> keySet = usableDispenseCodeMap.keySet();
		Integer[] keys = keySet.toArray(new Integer[keySet.size()]);
		for (int i = 0; i < keys.length - 1; i++) {
			int c = keys[i], next = keys[i + 1];
			for(int j = 1; j < next - c; i++){
				if(maxCount-- <= 0) return result;
				result.add(i + j);
			}
		}
		return result;
	}
	/**
	 * 获得最后一个编号的号码，无论这个编号对象是否取消状态
	 * @return
	 */
	private int getLastNo() {
		if(usableDispenseCodeMap.isEmpty()){
			return 0;
		}else{
			return usableDispenseCodeMap.lastKey();
		}
	}
	@Override
	public boolean isEnabled() {
		return STATUS_DISABLED != pDelivery.getStatus();
	}
	

}
