package liyu.test.sb.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReflectUtil {
	//liyu test,map2bean
	public static <T> T map2bean(Map<String,Object> map,Class<T> type){
		try {			
			T instance = type.newInstance();
			Field[] fields = type.getDeclaredFields();
			for (Field field : fields) {
				String name = field.getName();
				Object value = map.get(name);
				if(value!=null){
					PropertyDescriptor propertyDescriptor = new java.beans.PropertyDescriptor(name, type);
					Method writeMethod = propertyDescriptor.getWriteMethod();
					writeMethod.invoke(instance, value);
				}
			}		
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return null;
	}
	//liyu test,bean2map
	public static Map<String, Object> bean2map (Object bean){
        Map<String, Object> map = new HashMap<String, Object>();   
        try {
        	BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());    
        	PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
        	for (PropertyDescriptor property : propertyDescriptors) {    
        		String key = property.getName();    
        		if (key.compareToIgnoreCase("class") == 0) {   
        			continue;  
        		}  
        		Method getter = property.getReadMethod();  
        		Object value = getter!=null ? getter.invoke(bean) : null;  
        		map.put(key, value);  
        	}    
			
		} catch (Exception e) {
			e.printStackTrace();
		}
  
        return map;  
    }    
	//liyu test,replace bean enum to real value
	public static void EntityHandler(Object entity, Map<String, Object> dict) {
		Field[] fields = entity.getClass().getDeclaredFields();
		for (Field f : fields) {
			if (f.getName().endsWith("_dict")) {
				String name = f.getName();
				try {
					PropertyDescriptor desc = new PropertyDescriptor(name, entity.getClass());
					desc.getWriteMethod().invoke(entity, dict.get(desc.getReadMethod().invoke(entity)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	protected static class User{
		private int id;
		private String name;
		private Date birth;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Date getBirth() {
			return birth;
		}
		public void setBirth(Date birth) {
			this.birth = birth;
		}
	}
}
