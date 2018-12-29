package liyu.test.springbootMybatis.mybatis.jdbc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import liyu.test.springbootMybatis.mybatis.jdbc.joinQuery.JdbcJoinQuery;

@Aspect
@Component
public class JdbdJoinCachedAspect {	
	public static Map<Class<?>, ArrayList<String>> link = new HashMap<Class<?>,ArrayList<String>>();
	static {
		Method[] methods = JdbcJoinQuery.class.getDeclaredMethods();
		for(Method m:methods) {
			Cached cached = m.getAnnotation(Cached.class);
			if(cached!=null) {
				for(Class<?> c:cached.tableClasses()) {
					ArrayList<String> list = link.get(c);
					if(list==null) {
						link.put(c, new ArrayList<String>());
					}
					link.get(c).add(cached.uuid());
				}
			}
		}
	}
	
	private static Logger logger = LoggerFactory.getLogger(JdbdJoinCachedAspect.class);
	@Autowired
	private RedisCache rc;
	
	@Pointcut("execution(public * liyu.test.springbootMybatis.mybatis.jdbc.joinQuery..*(..))")
    public void cache(){}

    @Around("cache()")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
    	String shortName = joinPoint.getSignature().getName();   	
    	Class<?>[] cls = new Class[] {Object[].class};
    	Method method = joinPoint.getTarget().getClass().getDeclaredMethod(shortName, cls);
    	Cached c = method.getDeclaredAnnotation(Cached.class);
    	
    	//if method has 'Cache' annotation
    	if(c!=null && (c.uuid() !=null && !"".equals(c.uuid()))) {
    		Object ret = rc.getObject(c.uuid(), joinPoint.getArgs());
    		if(ret!=null) {
    			logger.debug("====>cache hit");
    			return ret;
    		}else {
    			Object obj = joinPoint.proceed();
    			if(obj!=null) {
    				Long l = rc.putObject(c.uuid(), joinPoint.getArgs(), obj);
    				logger.debug("====>cache put:"+l);
    			}
    			return obj;
    		}
    	}else {
    		return joinPoint.proceed();
    	}
    }
}
