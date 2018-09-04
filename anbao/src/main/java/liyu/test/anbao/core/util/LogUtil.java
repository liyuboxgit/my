package liyu.test.anbao.core.util;

import org.slf4j.Logger;

public class LogUtil {
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
