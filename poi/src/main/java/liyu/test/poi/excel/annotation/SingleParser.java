package liyu.test.poi.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SingleParser {
	/**
	 * 
	 * @Title: templateCode 
	 * @Description: Ä£°å±àºÅ
	 * @return
	 * @return: String
	 */
	public String templateCode();
}
