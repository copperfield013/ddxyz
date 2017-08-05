package cn.sowell.ddxyz.model.common2.core.impl;

import javax.annotation.Resource;

import cn.sowell.ddxyz.model.common2.core.C2Order;
import cn.sowell.ddxyz.model.common2.core.C2OrderManager;
import cn.sowell.ddxyz.model.common2.core.C2OrderRequest;
import cn.sowell.ddxyz.model.common2.core.C2OrderResourceManager;

public class C2DefaultOrderManager implements C2OrderManager {

	@Resource
	C2OrderResourceManager resourceManager;

	@Override
	public C2Order createOrder(C2OrderRequest orderRequest) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
