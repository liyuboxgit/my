package liyu.test.anbao.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Auth {
	void authFaild(HttpServletResponse response);
	boolean sessionCheck(HttpServletRequest request);
	boolean isStatic(String uri, String ctx);
	void login(AuthUser authUser);
	void logout(HttpServletRequest request);
	AuthUser getSessionUser(HttpServletRequest request);
	AnbaoRedisSession getSession(HttpServletRequest request);
}
