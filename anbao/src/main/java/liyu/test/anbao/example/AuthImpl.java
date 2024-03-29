package liyu.test.anbao.example;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;

import liyu.test.anbao.core.AnbaoRedisSession;
import liyu.test.anbao.core.Auth;
import liyu.test.anbao.core.AuthInterceptor;
import liyu.test.anbao.core.AuthUser;
import liyu.test.anbao.core.JedisPoolManager;
import liyu.test.anbao.core.RedisCache;
import liyu.test.anbao.core.util.Conf;
import liyu.test.anbao.core.util.JsonRet;
import liyu.test.anbao.core.util.StringUtil;
import liyu.test.anbao.core.util.WebUtil;

public class AuthImpl implements Auth{
	private JedisPoolManager jpm;
	private Map<String,Set<String>> resource;
		
	public AuthImpl(JedisPoolManager jpm) {
		Assert.notNull(jpm, "jedisPoolMananger can be null.");
		this.jpm = jpm;
	}
	
	@Override
	public void authFaild(HttpServletRequest request, HttpServletResponse response) {
		JsonRet ret = new JsonRet(request.getAttribute(AuthInterceptor.ckey));
		ret.setSuccess(false);
		ret.setMsg((String)request.getAttribute("msg"));
		WebUtil.write(response, JSON.toJSONString(ret));
	}

	@Override
	public boolean sessionCheck(HttpServletRequest request) {
		AnbaoRedisSession session = this.getSession(request);
		if(session!=null) {
			request.setAttribute(AuthInterceptor.ckey, session.getUuid());
			new RedisCache<String,AnbaoRedisSession>(jpm).reset(session.getUuid(), Integer.parseInt(Conf.get(Conf.SESSION_SECONDS)));
			return true;
		}
		request.setAttribute("originalUrl", request.getRequestURI());
		return false;
	}

	@Override
	public boolean isStatic(String uri, String ctx) {
		String[] anonSet = Conf.get(Conf.ANON).split(",");
		for(String el:anonSet) {
			if(uri.startsWith((ctx + el).replace("**", ""))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void login(AuthUser user) {
		String uuid = UUID.randomUUID().toString();
		AnbaoRedisSession instence = new AnbaoRedisSession(uuid);
		instence.setAttribute(AnbaoRedisSession.SK, user);
		
		HttpServletRequest request = WebUtil.getServletRequest();
		request.setAttribute(AuthInterceptor.ckey, uuid);
		
		HttpServletResponse response = WebUtil.getServletResponse();
		new RedisCache<String,AnbaoRedisSession>(jpm).put(uuid, instence, Integer.parseInt(Conf.get(Conf.SESSION_SECONDS)));
		
		String maxSession = Conf.get(Conf.MAX_SESSION);
		if(maxSession!=null) {
			int maxs = Integer.parseInt(Conf.get(Conf.MAX_SESSION,"1"));

			String username = user.getUsername();
			Deque<String> deque = this.getAttributeInRedis("max-session", username);
	        if (deque == null) {
	            deque = new LinkedList<String>();
	            
	        }else {
	        	while (deque.size() > maxs-1) {
		            String first = (String) deque.removeFirst();
					if(StringUtil.isNotBlank(first)) {
						new RedisCache<String,AnbaoRedisSession>(jpm).remove(first);
					}
		        }
	        }
			
	        deque.add(uuid);
            this.setAttributeInRedis("max-session", username, deque, Integer.parseInt(Conf.get(Conf.SESSION_SECONDS)));
		}
		
		Cookie cookie = new Cookie(AuthInterceptor.ckey,uuid);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	@Override
	public void logout(HttpServletRequest request) {
		AnbaoRedisSession session = this.getSession(request);
		if(session!=null) {
			new RedisCache<String,AnbaoRedisSession>(jpm).remove(session.getUuid());
		}
	}

	@Override
	public AuthUser getSessionUser(HttpServletRequest request) {
		AnbaoRedisSession session = this.getSession(request);
		if(session!=null) {
			return (AuthUser) session.getAttribute(AnbaoRedisSession.SK);
		}
		return null;
	}

	@Override
	public AnbaoRedisSession getSession(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for(Cookie cookie:cookies){
			    if(AuthInterceptor.ckey.equals(cookie.getName())) {
			    	RedisCache<String,AnbaoRedisSession> redisCache = new RedisCache<String,AnbaoRedisSession>(jpm);
			    	return redisCache.get(cookie.getValue());
			    }
			}
		}
		if(StringUtil.isNotBlank(request.getParameter(AuthInterceptor.ckey))) {
			RedisCache<String,AnbaoRedisSession> redisCache = new RedisCache<String,AnbaoRedisSession>(jpm);
	    	return redisCache.get(request.getParameter(AuthInterceptor.ckey).trim());
		}
		return null;
	}

	@Override
	public boolean hasPermission(HttpServletRequest request) {
		String uri = request.getRequestURI();
		if(this.resource == null) {
			return false;
		}
		Set<String> set = this.resource.get(uri);
		if(set==null) {			
			return false;
		}else {
			AuthUser user = this.getSessionUser(request);
			String[] roles = user.getRoles();
			boolean hasRole = false;
			if(roles != null) {
				for(String role : roles) {
					if(set.contains(role)) {
						hasRole = true;
						break;
					}
				}
			}
			return hasRole;
		}
	}
	
	@Override
	public <T> void setAttributeInRedis(String prefix, String key, T value, int exptSecond) {
		RedisCache<String,T> redisCache = new RedisCache<String,T>(jpm);
		redisCache.put(prefix+key, value, exptSecond);
	}
	
	@Override
	public <T> T getAttributeInRedis(String prefix, String key) {
		RedisCache<String,T> redisCache = new RedisCache<String,T>(jpm);
		return redisCache.get(prefix+key);
	}
	
	@Override
	public void setUrlResources(Map<String, Set<String>> res) {
		this.resource = res;
	}
	
	/*@Override
	public <T> void setAttributeInSession(String key, T value, HttpServletRequest request) {
		AnbaoRedisSession session = this.getSession(request);
		if(session!=null) {
			session.setAttribute(key, value);
			RedisCache<String,AnbaoRedisSession> redisCache = new RedisCache<String,AnbaoRedisSession>(jpm);
			redisCache.put(session.getUuid(), session, Integer.parseInt(ApplicationPropertes.instance().getSession_seconds()));
		}
	}*/

	/*@Override
	@SuppressWarnings("unchecked")
	public <T> T getAttributeInSession(String key, HttpServletRequest request) {
		AnbaoRedisSession session = this.getSession(request);
		if(session!=null) {
			return (T)session.getAttribute(key);
		}
		return null;
	}*/

}
