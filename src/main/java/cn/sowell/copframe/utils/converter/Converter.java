package cn.sowell.copframe.utils.converter;


public interface Converter<R> {
	boolean check(Object source);
	R convert(Object source) throws ConverteException;
}
