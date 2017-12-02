package liyu.test.springboot.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static DateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String datetime2str(Date date){
		return datetime.format(date);
	}
}
