package liyu.test.poi.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SinglePosition {
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
	 * @Title: y 
	 * @Description: 纵坐标，起始为1
	 * @return
	 * @return: int
	 */
	public int y() default 1;
	/**
	 * 
	 * @Title: nullable 
	 * @Description: 是否可以为空
	 * @return
	 * @return: boolean
	 */
	public boolean nullable() default true;
}
