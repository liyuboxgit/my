package liyu.test.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MysqlWeek {
	public static void main(String[] args) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2019);
		calendar.set(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
		calendar.clear();
		
		calendar.set(Calendar.YEAR, 2019);
		calendar.set(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));		
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
	}
}
