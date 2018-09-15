package com.rthd.tinychxu.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import com.rthd.framework.mybatis.BaseEntity;
import com.rthd.framework.mybatis.EnhanceMapper.UC;

public class BeanUtil {
	public static UC ucGenerate(String tableName,String columnName,Object value, BaseEntity entity) throws Exception {
		Integer primaryKey = readValue(entity, "id", Integer.class);
		Integer version = readValue(entity, "version", Integer.class);
		return new UC(tableName, columnName, value, primaryKey, version);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T readValue(Object instence,String filedName,Class<T> returnType) throws Exception {
		Class<? extends Object> cl = instence.getClass();
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(filedName, cl);
		Method method = propertyDescriptor.getReadMethod();
		T result = (T) method.invoke(instence);
		return result;
	}
}
