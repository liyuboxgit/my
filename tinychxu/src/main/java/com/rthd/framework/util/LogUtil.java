package com.rthd.framework.util;

import org.slf4j.Logger;

/**
 * 项目中不能再使用System.out.print,e.printExceptionStack
 * 全部使用logUtil进行日志输出
 * @author Administrator
 *
 */
public class LogUtil {
	/**
	 * 
	 * @Title: getServletResponse 
	 * @Description: 处理日志
	 * @return
	 * @return: HttpServletResponse
	 */
	public static void error(Logger logger,String msg,Object cause) {
		if(cause == null) {			
			logger.error(msg);
		}else {
			logger.error(msg,cause);	
			if(cause instanceof Exception) {
				((Exception) cause).printStackTrace();
			}
		}
	}
}
