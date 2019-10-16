package liyu.test.springmvc.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DateConvert implements Converter<String, Date> {
	private DateFormat dateFormat,dateTimeFormat;
	
	public DateConvert(String date,String dateTime) {
		this.dateFormat = new SimpleDateFormat(date);
		this.dateTimeFormat = new SimpleDateFormat(dateTime);
	}

	public Date convert(String stringDate) {
		try {
			if(stringDate.trim().length()>10){
				return dateTimeFormat.parse(stringDate);
			}
			return dateFormat.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
