package liyu.test.anbao.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Auth {
	void authFaild(HttpServletRequest request, HttpServletResponse response);
	boolean sessionCheck(HttpServletRequest request);
	boolean isStatic(String uri, String ctx);
	void login(AuthUser authUser);
	void logout(HttpServletRequest request);
	AuthUser getSessionUser(HttpServletRequest request);
	AnbaoRedisSession getSession(HttpServletRequest request);
	<T> void setAttributeInSession(String key,T value,HttpServletRequest request);
	<T> T getAttributeInSession(String key, HttpServletRequest request);
	<T> void setAttributeInRedis(String key,T value,int exptSecond);
	<T> T getAttributeInRedis(String key);
}
