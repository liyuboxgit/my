package liyu.test.sb;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import liyu.test.sb.mybatis.entity.SB;
import liyu.test.sb.service.SBService;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=MainConfigure.class)
public class MybtatisServiceTest {
	@Autowired
	private SBService service;
	
	@Test
	public void testSB() throws Exception{
		/*SB sb = new SB();
		
		sb.setName("tlangp");
		sb.setPrice(new BigDecimal("2.5"));
		sb.setSex_type('2');
		sb.setBirth(new Date());
		service.create(sb);*/
		
		SB sb = new SB();
		sb.setId(1);
		
		SB ret = service.get(sb);
		System.out.println(JSON.toJSONString(ret));
	}
}
