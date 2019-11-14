package liyu.test.poi.excel.util;

import java.io.File;
import java.util.List;
import java.util.Map;

import liyu.test.poi.excel.model.SimpleUser;
class User{
	private Integer id;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
public class Test {
	public static void main(String[] args) {
		
		User user = new User();
		user.setId(1);
		user.setName("1");

		Map<String, Object> map = BeanMapUtil.beanToMap(user);
		
		user = new User();
		BeanMapUtil.mapToBean(map, user);
		
		System.out.println(user.getName());
		
		
		
		File file = new File("files/test_0002.xlsx");
		System.out.println(file.exists());
		ExcelParser parser = new ExcelParser(file);
//		User user = parser.singleParse(User.class);
//		System.out.println(user);
		System.out.println(parser.getSheetCount());
		List<SimpleUser> list = parser.batchParse(0,SimpleUser.class);
		System.out.println(list.size());
	}
}
