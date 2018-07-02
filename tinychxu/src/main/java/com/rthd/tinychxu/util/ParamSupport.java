package com.rthd.tinychxu.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class ParamSupport {
	/**
	 * 
	 * 此方法描述的是：生成BaseParam工具类
	 * @author: liyu@rthdtax.com
	 * @version: 2018年7月2日 下午2:42:53
	 */
	public static <T> T construct(String name,Object value,Class<T> type) {
		try {
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(name, type);
			Method method = propertyDescriptor.getWriteMethod();
			T ret = type.newInstance();
			method.invoke(ret, value);
			return ret;
		} catch (Exception e) {
			
		}
		return null; 
	}
}
