package cn.sowell.copframe.utils.converter;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

public class ListConverter<T extends Converter<R>, R> {
	List<T> converterList;
	
	static Logger logger = Logger.getLogger(ListConverter.class);
	public ListConverter(List<T> converterList) {
		Assert.notNull(converterList);
		this.converterList = converterList;
	}
	
	public void addConverter(T converter){
		this.converterList.add(0, converter);
	}
	
	public List<T> getConverterList() {
		return converterList;
	}
	
	public R doConverter(Object source) throws ConverteException{
		for (T converter : converterList) {
			if(converter.check(source)){
				try {
					return converter.convert(source);
				} catch (ConverteException e) {
					throw e;
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
		return null;
	}
	
}
