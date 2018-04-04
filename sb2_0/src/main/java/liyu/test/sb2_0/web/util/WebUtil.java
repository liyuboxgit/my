package liyu.test.sb2_0.web.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class WebUtil {
	/**
	 * 
	 * @Title: responseWrite 
	 * @Description: 向页面写数据
	 * @param response
	 * @param message
	 * @return: void
	 */
	protected static void responseWrite(HttpServletResponse response, String message) {
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
	
	/**
	 * 
	 * @Title: quietClose 
	 * @Description: 关闭连接
	 * @param closeable
	 * @return: void
	 */
	public static void quietClose(Closeable closeable){
		try {
			closeable.close();
		} catch (IOException e) {
			
		}
	}
	
	public static JsonRet success() {
		JsonRet jsonRet = new JsonRet();
		jsonRet.setSuc(true);
		return jsonRet;
	}
	
	public static JsonRet success(String msg) {
		JsonRet jsonRet = new JsonRet();
		jsonRet.setSuc(true);
		jsonRet.setMsg(msg);
		return jsonRet;
	}
	
	public static JsonRet setResult(Object result) {
		JsonRet jsonRet = new JsonRet();
		jsonRet.setSuc(true);
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
		JsonRet jsonRet = new JsonRet();
		jsonRet.setSuc(false);
		jsonRet.setMsg(msg);
		jsonRet.setData(data);
		return jsonRet;
	}
	
	public static class JsonRet{
		private boolean suc;
		private String msg;
		private Object data;
		
		public boolean getSuc() {
			return suc;
		}
		public void setSuc(boolean suc) {
			this.suc = suc;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public Object getData() {
			return data;
		}
		public void setData(Object data) {
			this.data = data;
		}
	}
}
