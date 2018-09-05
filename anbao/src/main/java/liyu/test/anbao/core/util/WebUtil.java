package liyu.test.anbao.core.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import liyu.test.anbao.core.SecurityInterceptor;

public class WebUtil {
	public static void write(HttpServletResponse response,String msg) {
		try {
			response.setContentType("text/json; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			pw.write(msg);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Title: getServletRequest 
	 * @Description: 可以从这里获得，也可在定义在方法参数中不强制
	 * @return
	 * @return: HttpServletRequest
	 */
	public static HttpServletRequest getServletRequest(){
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();  
        return attr.getRequest();
	}
	/**
	 * 
	 * @Title: getServletResponse 
	 * @Description: 可以从这里获得，也可在定义在方法参数中不强制
	 * @return
	 * @return: HttpServletResponse
	 */
	public static HttpServletResponse getServletResponse(){
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		return response;
	}
	
	public static JsonRet errorAndReture(Logger logger_,String msg,Object cause) {
		LogUtil.error(logger_, msg, cause);
		return fail(msg,cause);
	}
	
	public static String getBasePath() {
		HttpServletRequest request = getServletRequest();
		return request.getScheme()+"://"+request.getServerName()+(request.getServerPort()==80?"":":"+request.getServerPort())+request.getContextPath();
	}
	/**
	 * 
	 * @Title: success 
	 * @Description: 返回json内部类
	 * @param null
	 * @return: void
	 */
	public static JsonRet success() {
		JsonRet jsonRet = newjsonRet();
		jsonRet.setSuccess(true);
		return jsonRet;
	}
	
	public static JsonRet success(String msg) {
		JsonRet jsonRet = success();
		jsonRet.setMsg(msg);
		return jsonRet;
	}
	
	public static JsonRet setResult(Object result) {
		JsonRet jsonRet = success();
		jsonRet.setData(result);
		return jsonRet;
	}
	
	public static JsonRet setResult(String msg, Object result) {
		JsonRet jsonRet = success();
		jsonRet.setMsg(msg);
		jsonRet.setData(result);
		return jsonRet;
	}
	
	public static JsonRet fail(String msg) {
		JsonRet jsonRet = newjsonRet();
		jsonRet.setSuccess(false);
		jsonRet.setMsg(msg);
		return jsonRet;
	}
	
	public static JsonRet fail(String msg,Object data) {
		JsonRet jsonRet = fail(msg);
		jsonRet.setData(data);
		return jsonRet;
	}
	
	private static JsonRet newjsonRet() {
		return new JsonRet(getServletRequest().getAttribute(SecurityInterceptor.ckey));
	}
}
