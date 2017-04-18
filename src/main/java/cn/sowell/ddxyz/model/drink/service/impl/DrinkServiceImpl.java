package cn.sowell.ddxyz.model.drink.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sowell.copframe.utils.CollectionUtils;
import cn.sowell.ddxyz.model.drink.dao.DrinkDao;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkAdditionType;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkTeaAdditionType;
import cn.sowell.ddxyz.model.drink.pojo.PlainDrinkType;
import cn.sowell.ddxyz.model.drink.service.DrinkService;

@Service
public class DrinkServiceImpl implements DrinkService{

	@Resource
	DrinkDao drinkDao;
	
	@Override
	public List<PlainDrinkType> getAllDrinkTypes(long waresId) {
		return drinkDao.getAllDrinkTypes(waresId);
	}

	@Override
	public Map<Long, List<PlainDrinkTeaAdditionType>> getTeaAdditionMap(
			long waresId) {
		List<PlainDrinkTeaAdditionType> teaAdditions = drinkDao.getAllTeaAdditionTypes(waresId);
		return CollectionUtils.toListMap(teaAdditions, addition -> {
			return addition.getDrinkTypeId();
		});
	}

	@Override
	public Map<Long, List<PlainDrinkAdditionType>> getAdditionMap(
			long waresId) {
		List<PlainDrinkAdditionType> additions = drinkDao.getAllAdditionTypes(waresId);
		return CollectionUtils.toListMap(additions, addition -> {
			return addition.getDrinkTypeId();
		});
	}

}
