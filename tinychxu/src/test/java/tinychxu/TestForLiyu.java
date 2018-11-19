package tinychxu;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.rthd.framework.config.shiro.JedisPoolManager;
import com.rthd.framework.config.shiro.RedisCache;
import com.rthd.framework.mybatis.EnhanceMapper;
import com.rthd.framework.mybatis.EnhanceMapper.UC;
import com.rthd.tinychxu.MainConfigure;
import com.rthd.tinychxu.domain.entity.Demo;
import com.rthd.tinychxu.domain.entity.User;
import com.rthd.tinychxu.mapper.BaseMapper;
import com.rthd.tinychxu.service.DemoService;
import com.rthd.tinychxu.util.BeanUtil;
import com.rthd.tinychxu.util.MapUtil.StringMap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=MainConfigure.class)
public class TestForLiyu {
	@Autowired
	private BaseMapper bm;
	@Autowired private TransactionTemplate transactionTemplate;
	@Autowired private DemoService demoService;
	@Test
	public void test() {
		/*Demo demo = new Demo();
		demo.setName("tom");
		demo.setAge(10);
		demo.setMoney(BigDecimal.valueOf(12.56));
		bm.exccute(Demo.class, EnhanceMapper.insert, demo);*/
		
		/*Long count = bm.findCount(EnhanceMapper.findcount, null, Demo.class);
		System.out.println(count);*/
		
		/*Map<Object, Object> map = bm.getSqlSession().selectMap(Demo.class.getName()+"."+EnhanceMapper.findlist, "name");
		System.out.println(JSON.toJSONString(map));*/
		
		List<Long> list = bm.jdbcFindSingelColumn("select count(*) from demo", Long.class, true);
		System.out.println(list.get(0));
		
		/*User user = new User();
		user.setUsername("test");
		user.setPassword("test");
		bm.exccute(User.class, EnhanceMapper.insert, user);*/
		
		List<StringMap> listMap = bm.jdbcFindListMap("select username as username,password as password,'' as roles from user", null);
		
		System.out.println(listMap);
		
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
		
		/*List<StringMap> list = bm.jdbcFindListMap("select * from demo where id=?", new Object[] {1});
		try {
			
			System.out.println(JSON.toJSONString(list));
		} catch (Exception e) {
			// TODO: handle exception///stringMap没有实现全部的方法
			e.printStackTrace();
		}*/
		
		/*List<String> list = bm.jdbcFindSingelColumn("select name from demo where id=1", String.class, false);
		System.out.println(list.get(0));*/
		
		/*List<StringMap> list = bm.jdbcFindListMap("select * from demo", new Object[0]);
		try {
			System.out.println(JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		/*Demo demo = bm.findOne(EnhanceMapper.findone, 1, Demo.class);
		System.out.println(demo.getAge());
		try {
			demo.setAge(10);
			UC uc = BeanUtil.ucGenerate("demo", "age", demo);
			bm.exccute(Demo.class, EnhanceMapper.dynamicUpdate, uc);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		
		
		/*Exception ret = transactionTemplate.execute(new TransactionCallback<Exception>() {
		    public Exception doInTransaction(TransactionStatus status) {
		    	Exception result = null;
		        try {
		        	Demo demo = bm.findOne(EnhanceMapper.findone, 1, Demo.class);
		    		System.out.println(demo.getAge());
		    		try {
		    			UC uc = BeanUtil.ucGenerate("demo", "age", 29, demo);
		    			bm.exccute(Demo.class, EnhanceMapper.dynamicUpdate, uc);
		    		} catch (Exception e) {
		    			e.printStackTrace();
		    		}
		    		//throw new RuntimeException();
		        } catch (Exception ex) {
		            status.setRollbackOnly();
		            result = ex;
		        }
		        return result;
		    }
		});
			
			
		System.out.println(ret);*/
		
		/*TransactionUtil tc = new TransactionUtil() {
			public void accept(Object t) {
				Demo demo = bm.findOne(EnhanceMapper.findone, 1, Demo.class);
				System.out.println(demo.getAge());
				try {
					UC uc = BeanUtil.ucGenerate("demo", "age", 29, demo);
					bm.exccute(Demo.class, EnhanceMapper.dynamicUpdate, uc);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//throw new RuntimeException();
			}
		};
		
		Exception exception = TransactionUtil.doInOneTransaction(transactionTemplate, tc);
		System.out.println(exception);*/
		
	}
	
	public static void main(String[] args) {
		/*JedisPoolManager manager = new JedisPoolManager("localhost", "6379");
		RedisCache<String,String> cache = new RedisCache<String,String>();
		cache.setJedisPoolManager(manager);
		cache.put("k", "v", 5);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(cache.get("k"));//return v
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(cache.get("k"));//return null
*/	}
}
