package liyu.test.springbootMybatis.mybatis;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class MybatisEhcache implements Cache{
	static Logger logger = LoggerFactory.getLogger(MybatisRedisCache.class);
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();   
    
	private CacheManager cm = CacheManager.create(Object.class.getResourceAsStream("/ehcache.xml"));
	private net.sf.ehcache.Cache cache;
    private String id;
      
    public MybatisEhcache(final String id) {    
        if (id == null) {  
            throw new IllegalArgumentException("Cache instances require an ID");  
        }  
        logger.debug("MybatisEhcache="+id);  
        this.id = id; 
        this.cache = cm.getCache("local");
        this.rebuild();
    }  
    
    private void rebuild(){
    	cache.put(new Element(this.id, new HashMap<Object,Object>()));
    }
    
	public String getId() {
		return this.id;
	}
	
	@SuppressWarnings("unchecked")
	public void putObject(Object key, Object value) {
		Map<Object,Object> sub = (Map<Object, Object>) cache.get(this.id).getObjectValue();
		sub.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public Object getObject(Object key) {
		Map<Object,Object> sub = (Map<Object, Object>) cache.get(this.id).getObjectValue();
		return sub.get(key);
	}

	@SuppressWarnings("unchecked")
	public Object removeObject(Object key) {
		Map<Object,Object> sub = (Map<Object, Object>) cache.get(this.id).getObjectValue();
		return sub.remove(key);
	}

	public void clear() {
		this.rebuild();
	}

	public int getSize() {
		return 0;
	}

	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}
}
