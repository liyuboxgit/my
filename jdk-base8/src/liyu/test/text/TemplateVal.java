package liyu.test.text;

import java.text.SimpleDateFormat;
import java.util.Date;

import liyu.test.collection.StringKeyMap;
import liyu.test.date.StringOfDate;

public class TemplateVal {
	/**
	 * replace {key} in string content
	 * @param args
	 */
	public static void main(String[] args) {
		String cont = "<html><body><p>the date is {now}</p><p>the name is {name}</p></body></html>";
		StringKeyMap sk = new StringKeyMap();
		
		StringOfDate date = param -> new SimpleDateFormat("yyyy-MM-dd").format(param);
		sk.put("now", date.format(new Date()));
		sk.put("name", "java expert.");
		String[] split = cont.split("\\{");
		
		StringBuffer sb = new StringBuffer();
		for(String s:split) {
			if(s.indexOf("}")==-1) {
				sb.append(s);
				continue;
			}else {
				String zwf = s.substring(0, s.indexOf("}"));
				Object v = sk.get(zwf);
				sb.append(v);
				sb.append(s.substring(s.indexOf("}")+1));
			}
		}
		
		System.out.println(sb);
	}

}
