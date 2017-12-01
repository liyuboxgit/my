package liyu.test;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	public static void main(String[] args) {
		logger.debug("The time now is:"+new Date());
		logger.info("The time now is:"+new Date());
		
		String s = "ZS1000001$电脑出口合同";
		String[] split = s.split("\\$");
		System.out.println(split.length);
		System.out.println(s.indexOf("$"));
	}
}
