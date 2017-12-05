package liyu.test.framework.shiro;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.filter.GenericFilterBean;
/**
 * 无用
 * @author Administrator
 *
 */
public class MyNeedSessionFilter extends GenericFilterBean{

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		Subject subject = SecurityUtils.getSubject();
		Object principal = subject.getPrincipal();
		if(principal==null){
			if(this.isNotFilter(req.getServletPath()))
				chain.doFilter(request, response);
			else
				resp.sendRedirect(req.getContextPath()+"/login.jsp");
		}else{
			chain.doFilter(request, response);
		}
	}
	
	private boolean isNotFilter(String url){
		if(url.endsWith("login.jsp") || url.endsWith("noPermission.jsp") || url.startsWith("/static") || "/sec/login".equals(url)){
			return true;
		}else{
			return false;
		}
	}

}
