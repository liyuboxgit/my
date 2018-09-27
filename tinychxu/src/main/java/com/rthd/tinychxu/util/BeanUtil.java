package com.rthd.tinychxu.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import com.rthd.framework.mybatis.BaseEntity;
import com.rthd.framework.mybatis.EnhanceMapper.UC;

public class BeanUtil {
	public static UC ucGenerate(String tableName,String columnName, BaseEntity entity) throws Exception {
		Integer primaryKey = readValue(entity, "id", Integer.class);
		Integer version = readValue(entity, "version", Integer.class);
		Object value = readValue(entity, columnName, Object.class);
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
	
	public static void copyFiledsExcludeNullProperty(Object source,Object target,String...ignoreProperties) throws Exception {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null) {
					Method readMethod = sourcePd.getReadMethod();
					if (readMethod != null &&
							ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
						
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						if(value!=null)
							writeMethod.invoke(target, value);
					}
				}
			}
		}
	}
}
