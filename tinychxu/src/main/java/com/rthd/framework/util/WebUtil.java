package com.rthd.framework.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.rthd.framework.config.shiro.ShiroUser;

public class WebUtil {
	/**
	 * 
	 * @Title: responseWrite 
	 * @Description: 向页面写数据
	 * @param response
	 * @param message
	 * @return: void
	 */
	public static void responseWrite(HttpServletResponse response, String message) {
		try {
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			pw.write(message);
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
	/**
	 * 返回登录用户
	 * @return
	 */
	public static ShiroUser getSessionUser() {
		Object principal = SecurityUtils.getSubject().getPrincipal();
		return principal!=null?(ShiroUser)principal:null;
	}
	
	/**
	 * 
	 * @Title: quietClose 
	 * @Description: 关闭连接
	 * @param closeable
	 * @return: void
	 */
	public static void quietClose(AutoCloseable closeable){
		try {
			if(closeable!=null) {				
				closeable.close();
			}
		} catch (Exception e) {
			
		}
	}
	/**
	 * 
	 * @Title: success 
	 * @Description: 返回json内部类
	 * @param null
	 * @return: void
	 */
	public static JsonRet success() {
		JsonRet jsonRet = new JsonRet();
		jsonRet.setSuc(true);
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
		JsonRet jsonRet = new JsonRet();
		jsonRet.setSuc(false);
		jsonRet.setMsg(msg);
		return jsonRet;
	}
	
	public static JsonRet fail(String msg,Object data) {
		JsonRet jsonRet = fail(msg);
		jsonRet.setData(data);
		return jsonRet;
	}
}
