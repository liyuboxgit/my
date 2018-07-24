package tinychxu;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.rthd.framework.mybatis.EnhanceMapper;
import com.rthd.tinychxu.MainConfigure;
import com.rthd.tinychxu.domain.Demo;
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
		
		Long count = bm.findCount(EnhanceMapper.findcount, null, Demo.class);
		System.out.println(count);
		
		Map<Object, Object> map = bm.getSqlSession().selectMap(Demo.class.getName()+"."+EnhanceMapper.findlist, "name");
		System.out.println(JSON.toJSONString(map));
		
		
	}
}
