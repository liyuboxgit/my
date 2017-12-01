package liyu.test.poi.excel.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static SimpleDateFormat DATA_FORMAT = new  SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat DATA_TIME_FORMAT = new  SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
	
	public static String date2Str(Date date,DateFormat format){
		return format.format(date);
	}
	
	public static void main(String[] args) {
		String string = date2Str(new Date(),DATA_TIME_FORMAT);
		System.out.println(string);
	}
}
