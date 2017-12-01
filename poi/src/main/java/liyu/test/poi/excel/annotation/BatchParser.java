package liyu.test.poi.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BatchParser{
	/**
	 * 
	 * @Title: templateCode 
	 * @Description: 模板编号
	 * @return
	 * @return: String
	 */
	public String templateCode();
	/**
	 * 
	 * @Title: startRow 
	 * @Description: 从第几行开始解析数据，一般第一行为标题，从第二行为数据
	 * @return
	 * @return: int
	 */
	public int startRow() default 2;
}
