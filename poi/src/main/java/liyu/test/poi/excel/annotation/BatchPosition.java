package liyu.test.poi.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BatchPosition {
	/**
	 * 
	 * @Title: x 
	 * @Description: 横坐标，起始为A
	 * @return
	 * @return: String
	 */
	public String x();
	/**
	 * 
	 * @Title: nullable 
	 * @Description: 是否可以为空
	 * @return
	 * @return: boolean
	 */
	public boolean nullable() default true;
}
