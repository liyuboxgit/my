package tinychxu;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.rthd.tinychxu.MainConfigure;
import com.rthd.tinychxu.mapper.BaseMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=MainConfigure.class)
public class TestForLiyu {
	@Autowired
	private BaseMapper bm;
	@Test
	public void test() {
		/*Demo demo = new Demo();
		demo.setName("name");
		demo.setAge(10);
		bm.exccute(Demo.class, EnhanceMapper.insert, demo);*/
		
		/*Long count = bm.findCount(EnhanceMapper.findcount, null, Demo.class);
		System.out.println(count);
		
		Map<Object, Object> map = bm.getSqlSession().selectMap(Demo.class.getName()+"."+EnhanceMapper.findlist, "name");
		System.out.println(JSON.toJSONString(map));*/
		
		/*try {
			FileInputStream stream = new FileInputStream(new File("D:\\gitrepostary\\my\\tinychxu\\src\\test\\java\\tinychxu\\TestForLiyu.java"));
			List<String> list = IOUtils.readLines(stream, Charset.forName("UTF-8"));
			for (String string : list) {
				System.out.println(string);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*List<String> list = bm.jdbcFindSingelColumn("select name from demo where id=1", String.class, false);
		System.out.println(list.get(0));*/
		
		List<Map<String, Object>> list = bm.jdbcFindListMap("select * from demo where id=?", new Object[] {1});
		System.out.println(JSON.toJSONString(list));
	}
}
