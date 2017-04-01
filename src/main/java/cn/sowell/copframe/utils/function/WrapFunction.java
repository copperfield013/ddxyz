package cn.sowell.copframe.utils.function;

import java.util.function.Supplier;

public interface WrapFunction<R> {
	void invoke(Supplier<R> main);
}
