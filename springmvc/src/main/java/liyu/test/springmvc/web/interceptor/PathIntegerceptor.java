package liyu.test.springmvc.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class PathIntegerceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(request.getRequestURI().endsWith("/toError")) {
			response.getWriter().write("url toError is not allowed");
			return false;
		}
		if(request.getRequestURI().endsWith("/toErrorEx")) {
			throw new RuntimeException("url toErrorEx is not allowed");
		}
		if(request.getRequestURI().endsWith("/toErrorExAndReturn")) {
			throw new RuntimeException("url toErrorExAndReturn is not allowed,need return json");
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
