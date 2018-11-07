package liyu.test.anbao.core;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Auth {
	/**
	 * 
	 * @Description: 此方法描述的是：
	 * 		1.如果用户没有登录，返回auth faild消息
	 * 		2.如果用户访问受保护的资源，而且没有资源所对应的角色，返回permission ckeck faild消息 
	 * @author: you@rthdtax.com
	 * @version: 2018年11月7日 下午3:49:38
	 */
	void authFaild(HttpServletRequest request, HttpServletResponse response);
	/**
	 * 
	 * @Description: 此方法描述的是：用户是否登录
	 * @author: you@rthdtax.com
	 * @version: 2018年11月7日 下午3:53:24
	 */
	boolean sessionCheck(HttpServletRequest request);
	/**
	 * 
	 * @Description: 此方法描述的是：所访问的资源是否可以匿名访问
	 * @author: you@rthdtax.com
	 * @version: 2018年11月7日 下午3:53:53
	 */
	boolean isStatic(String uri, String ctx);
	/**
	 * 
	 * @Description: 此方法描述的是：处理用户登录请求
	 * @author: you@rthdtax.com
	 * @version: 2018年11月7日 下午3:54:27
	 */
	void login(AuthUser authUser);
	/**
	 * 
	 * @Description: 此方法描述的是：处理用户退出登录请求
	 * @author: you@rthdtax.com
	 * @version: 2018年11月7日 下午3:54:44
	 */
	void logout(HttpServletRequest request);
	/**
	 * 
	 * @Description: 此方法描述的是：获取登录用户
	 * @author: you@rthdtax.com
	 * @version: 2018年11月7日 下午3:55:23
	 */
	AuthUser getSessionUser(HttpServletRequest request);
	/**
	 * 
	 * @Description: 此方法描述的是：获取会话Session
	 * @author: you@rthdtax.com
	 * @version: 2018年11月7日 下午3:55:58
	 */
	AnbaoRedisSession getSession(HttpServletRequest request);
	/**
	 * 
	 * @Description: 此方法描述的是：redis中保存数据，和sesion没有关系，注意设置prefix和过期时间
	 * @author: you@rthdtax.com
	 * @version: 2018年11月7日 下午3:56:27
	 */
	<T> void setAttributeInRedis(String prefix,String key,T value,int exptSecond);
	/**
	 * 
	 * @Description: 此方法描述的是：redis中获取数据
	 * @author: you@rthdtax.com
	 * @version: 2018年11月7日 下午3:56:27
	 */
	<T> T getAttributeInRedis(String prefix,String key);
	/**
	 * 
	 * @Description: 此方法描述的是：设置资源对应的角色集，一般从数据库获取
	 * @author: you@rthdtax.com
	 * @version: 2018年11月7日 下午3:58:17
	 */
	void setUrlResources(Map<String,Set<String>> res);
	/**
	 * 
	 * @Description: 此方法描述的是：资源对应的角色集，和用户对应的角色集是否有交集
	 * @author: you@rthdtax.com
	 * @version: 2018年11月7日 下午3:59:49
	 */
	boolean hasPermission(HttpServletRequest request);
	
	//<T> void setAttributeInSession(String key,T value,HttpServletRequest request);
	//<T> T getAttributeInSession(String key, HttpServletRequest request);
}
