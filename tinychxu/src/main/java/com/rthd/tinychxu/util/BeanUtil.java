package com.rthd.tinychxu.util;

import java.lang.reflect.Method;

import com.rthd.framework.mybatis.BaseEntity;
import com.rthd.framework.mybatis.EnhanceMapper.UC;

public class BeanUtil {
	public static UC ucGenerate(String tableName,String columnName,Object value, BaseEntity entity) throws Exception {
		Integer primaryKey = readValue(entity, "getId", Integer.class);
		Integer version = readValue(entity, "getVersion", Integer.class);
		return new UC(tableName, columnName, value, primaryKey, version);
	}
	
	public static <T> T readValue(Object instence,String methodName,Class<T> returnType) throws Exception {
		Class<? extends Object> cl = instence.getClass();
        Method met = cl.getMethod(methodName);
        @SuppressWarnings("unchecked")
		T result = (T) met.invoke(instence);
		return result;
	}
}
