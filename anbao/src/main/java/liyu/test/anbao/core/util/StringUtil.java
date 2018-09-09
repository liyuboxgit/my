package liyu.test.anbao.core.util;

public class StringUtil {
	public static boolean isBlank(String str) {
		return str == null||"".equals(str);
	}
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
}
