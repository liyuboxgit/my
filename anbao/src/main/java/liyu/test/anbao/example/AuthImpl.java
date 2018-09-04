package liyu.test.anbao.example;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import liyu.test.anbao.core.AnbaoRedisSession;
import liyu.test.anbao.core.Auth;
import liyu.test.anbao.core.AuthInterceptor;
import liyu.test.anbao.core.AuthUser;
import liyu.test.anbao.core.JedisPoolManager;
import liyu.test.anbao.core.RedisCache;
import liyu.test.anbao.core.util.ApplicationPropertes;
import liyu.test.anbao.core.util.JsonRet;
import liyu.test.anbao.core.util.WebUtil;

public class AuthImpl implements Auth{
	private JedisPoolManager jpm;
	public void setJpm(JedisPoolManager jpm) {
		this.jpm = jpm;
	}
	
	@Override
	public void authFaild(HttpServletResponse response) {
		JsonRet ret = new JsonRet("");
		ret.setSuccess(false);
		ret.setMsg("auth faild.");
		WebUtil.write(response, JSON.toJSONString(ret));
	}

	@Override
	public boolean sessionCheck(HttpServletRequest request) {
		AnbaoRedisSession session = this.getSession(request);
		if(session!=null) {			
			new RedisCache<String,AnbaoRedisSession>(jpm).reset(session.getUuid(), Integer.parseInt(ApplicationPropertes.instance().getSession_seconds()));
			return true;
		}
		return false;
	}

	@Override
	public boolean isStatic(String uri, String ctx) {
		String[] anonSet = ApplicationPropertes.instance().getAnonSet();
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
		
		HttpServletResponse response = WebUtil.getServletResponse();
		new RedisCache<String,AnbaoRedisSession>(jpm).put(uuid, instence, Integer.parseInt(ApplicationPropertes.instance().getSession_seconds()));
		
		Cookie cookie = new Cookie(AuthInterceptor.Ckey,uuid);
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
			    if(AuthInterceptor.Ckey.equals(cookie.getName())) {
			    	RedisCache<String,AnbaoRedisSession> redisCache = new RedisCache<String,AnbaoRedisSession>(jpm);
			    	return redisCache.get(cookie.getValue());
			    }
			}
		}
		return null;
	}
	
}
