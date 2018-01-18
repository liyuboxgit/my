package liyu.test.sb;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import liyu.test.sb.jpa.SBRepostory;
import liyu.test.sb.mybatis.EnhanceMapper;
import liyu.test.sb.mybatis.EnhanceMapper.Page;
import liyu.test.sb.mybatis.EnhanceMapper.PageParam;
import liyu.test.sb.mybatis.entity.SB;
import liyu.test.sb.service.SBService;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=MainConfigure.class)
public class JpaRepostoryTest {
	@Autowired
	private SBService service;
	@Test
	public void test() throws Exception{
		SB sb = new SB();
		PageParam pageParam = new PageParam(1,10);
		Page<SB> page = service.findPage(sb, pageParam);
		
		System.out.println(page.getTotalCount());
	}
}




