package liyu.test.anbao.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SecurityInterceptor implements HandlerInterceptor, AuthInterceptor{
	@Autowired
	private Auth auth;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		boolean isStatic = auth.isStatic(uri,request.getContextPath());
		if(isStatic)
			return true;
		else {
			if(!auth.sessionCheck(request)) {
				request.setAttribute("msg", "auth faild! the original url is '"+request.getAttribute("originalUrl")+"'");
				auth.authFaild(request, response);
				return false;
			}else {
				if(handler instanceof HandlerMethod) {			
					HandlerMethod hm = (HandlerMethod) handler;
					Protection pt = hm.getMethodAnnotation(Protection.class);
					if(pt!=null) {
						if(!auth.hasPermission(request)) {
							request.setAttribute("msg", "permission ckeck faild! the protected url is '"+request.getRequestURI()+"'");
							auth.authFaild(request, response);
						}
					}else {
						return true;
					}
				}
				return true;
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv)
		throws Exception {	
	}


	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
		throws Exception {
	}
}
