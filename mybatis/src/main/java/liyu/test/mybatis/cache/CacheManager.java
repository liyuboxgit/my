package liyu.test.mybatis.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.cache.Cache;
/**
 * 
 * @ClassName: CacheManager 
 * @Description: Mabatis的缓存是基于mapper的，没有总体的cacheManager的概念
 * 	故本class用于清除相关联的cache
 * @author: liyu
 * @date: 2017年11月2日 上午10:05:17
 */
public class CacheManager {
	private static Map<String,Cache> pool = new HashMap<String,Cache>();
	
	public static void registe(Cache cache){
		pool.put(cache.getId(), cache);
	}
	
	public static void clear(String[] cacheIds){
		for(String cacheId : cacheIds){
			if(pool.containsKey(cacheId)){
				pool.get(cacheId).clear();
			}
		}
	}
}
