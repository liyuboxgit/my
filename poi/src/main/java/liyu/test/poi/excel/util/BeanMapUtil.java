package liyu.test.poi.excel.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cglib.beans.BeanMap;

public class BeanMapUtil {
	/**
	 * 
	 * @Title: beanToMap 
	 * @Description: TODO
	 * @param bean
	 * @return
	 * @return: Map<String,Object>
	 */
	public static <T> Map<String, Object> beanToMap(T bean) {  
	    Map<String, Object> map = new HashMap<String, Object>();  
	    if (bean != null) {  
	        BeanMap beanMap = BeanMap.create(bean);  
	        for (Object key : beanMap.keySet()) {  
	            map.put(key+"", beanMap.get(key));  
	        }             
	    }  
	    return map;  
	}  
	/**
	 *   
	 * @Title: mapToBean 
	 * @Description: TODO
	 * @param map
	 * @param bean
	 * @return
	 * @return: T
	 */
	public static <T> T mapToBean(Map<String, Object> map,T bean) {  
	    BeanMap beanMap = BeanMap.create(bean);  
	    beanMap.putAll(map);  
	    return bean;  
	}  
}
