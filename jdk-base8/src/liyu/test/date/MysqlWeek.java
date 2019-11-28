package liyu.test.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MysqlWeek {
	static void f01() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		System.out.println(calendar.getTime().toInstant());//今天
		
		calendar.add(Calendar.MONTH, -1);
		System.out.println(calendar.getTime().toLocaleString());//一个月前
		
		while(calendar.getTimeInMillis()<System.currentTimeMillis()) {
			String s = new SimpleDateFormat("yyyy-MM-dd EEE").format(calendar.getTime());
			if(s.endsWith("日")) System.out.println(s);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}		
	}
	
	public static void main(String[] args) throws ParseException {
		
		f01();//获取近一个月的所有星期天
		/*Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2019);
		calendar.set(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
		calendar.clear();
		
		calendar.set(Calendar.YEAR, 2019);
		calendar.set(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));		
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));*/
	}
}
