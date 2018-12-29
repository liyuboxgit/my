package springbootMybatisTest;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import liyu.test.springbootMybatis.MainConfigure;
import liyu.test.springbootMybatis.domain.Demo;
import liyu.test.springbootMybatis.domain.param.DemoParam;
import liyu.test.springbootMybatis.mybatis.EnhanceMapper;
import liyu.test.springbootMybatis.mybatis.jdbc.joinQuery.JdbcJoinQuery;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=MainConfigure.class)
public class BaseTest {
	@Autowired
	private EnhanceMapper em;
	@Autowired
	private JdbcJoinQuery jjq;
    @Test
	public void test() {
    	//1，创建数据库
    	//2，创建表（demo），HibernateCreateTable
    	//3，批量插入
    	/*for(int i=1;i<54;i++) {
    		Demo demo = new Demo();
    		demo.setName("d"+i);
    		demo.setAge(i);
    		demo.setMoney(BigDecimal.valueOf(i*0.5));
    		demo.setBirth(Calendar.getInstance().getTime());
    		em.exccute(Demo.class, em.insert, demo);
    	}*/
    	/*//3，查询数量
    	Long count = em.findCount(em.findcount, null, Demo.class);
    	Assert.isTrue(count==53, "");
    	//4，分页查询
    	DemoParam demoParam = new DemoParam();
    	demoParam.setPageSize(10);
    	
    	Page<Demo> page = em.findPage(em.findlist, em.findcount, demoParam, Demo.class);
    	Assert.isTrue(page.getTotalPage()==6, "");
    	//5，动态修改
    	Demo demo = em.findOne(em.findone, 3, Demo.class);
    	demo.setName("test");
    	em.exccute(Demo.class, em.dynamicUpdate, em.uc(demo, "name"));
    	DemoParam param = new DemoParam();
    	param.setName("test");
    	Long findCount = em.findCount(em.findcount, param, Demo.class);
    	Assert.isTrue(findCount==1, "");
    	//5，使用缓存
    	Long ret1 = em.findCount(em.findcount, null, Demo.class);
    	Assert.isTrue(ret1==53, "");
    	Long ret2 = em.findCount(em.findcount, null, Demo.class);
    	Assert.isTrue(ret2==53, "");*/
    	
    	List<Map<String, Object>> find1 = jjq.find(new Object[] {});
    	System.out.println(find1);
    	DemoParam param = new DemoParam();
    	param.setId(50);
    	param.setVersion(0);
    	em.exccute(Demo.class, em.delete, param);
    	List<Map<String, Object>> find2 = jjq.find(new Object[] {});
    	System.out.println(find2);
    }
	
	public static void main(String[] args) {
        RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        String string = rt.getForObject("http://localhost:8080", String.class);
        Assert.isTrue("success".equals(string),"");
   }
};