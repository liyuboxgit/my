package tinychxu;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rthd.framework.mybatis.EnhanceMapper;
import com.rthd.tinychxu.MainConfigure;
import com.rthd.tinychxu.domain.Demo;
import com.rthd.tinychxu.mapper.BaseMapper;


@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=MainConfigure.class)
public class MyTest {
	@Autowired
	BaseMapper bm;
	@Autowired
	JdbcTemplate jt;
	@Test
	public void test() {
//		List<Demo> list = bm.findList(EnhanceMapper.findlist, null, Demo.class);
//		System.out.println(list.size());
		
//		Demo demo = new Demo();
//		demo.setName("test2");
//		demo.setAge(10);
//		demo.setMoney(BigDecimal.valueOf(100.12));
//		
//		int exccute = bm.exccute(Demo.class, EnhanceMapper.insert, demo);
		
		List<String> forList = jt.queryForList("select name from demo where name=?", new Object[] {"test"}, String.class);
		System.out.println(forList);
		
//		String demo = jt.queryForObject("select name from demo", String.class);
//		System.out.println(demo);
		
		List<Map<String,Object>> list = jt.queryForList("select * from demo where name=?",new Object[] {"test"});
		System.out.println(list);
		
//		Map<String, Object> map = jt.queryForMap("select * from demo");
//		System.out.println(map);
	}
}
