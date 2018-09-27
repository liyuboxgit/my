package com.rthd.tinychxu.util;

import java.util.function.Function;

public interface TrycatchFunctionUtil extends Function<Object,Object>{
	public static void execute(TrycatchFunctionUtil tfu, Object result) {
		try {
			result = tfu.apply(null);
		} catch (Exception e) {
			result = e;
		}
	}
}
