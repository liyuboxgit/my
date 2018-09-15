package tinychxu;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.rthd.framework.mybatis.EnhanceMapper;
import com.rthd.framework.mybatis.EnhanceMapper.UC;
import com.rthd.tinychxu.MainConfigure;
import com.rthd.tinychxu.domain.Demo;
import com.rthd.tinychxu.mapper.BaseMapper;
import com.rthd.tinychxu.util.BeanUtil;
import com.rthd.tinychxu.util.MapUtil.StringMap;
import com.rthd.tinychxu.util.TransactionUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=MainConfigure.class)
public class TestForLiyu {
	@Autowired
	private BaseMapper bm;
	@Autowired private TransactionTemplate transactionTemplate;
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
		
		/*List<StringMap> list = bm.jdbcFindListMap("select * from demo where id=?", new Object[] {1});
		try {
			
			System.out.println(JSON.toJSONString(list));
		} catch (Exception e) {
			// TODO: handle exception///stringMap没有实现全部的方法
			e.printStackTrace();
		}*/
		
		/*List<String> list = bm.jdbcFindSingelColumn("select name from demo where id=1", String.class, false);
		System.out.println(list.get(0));*/
		
		List<StringMap> list = bm.jdbcFindListMap("select * from demo", new Object[0]);
		try {
			System.out.println(JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*Demo demo = bm.findOne(EnhanceMapper.findone, 1, Demo.class);
		System.out.println(demo.getAge());
		try {
			UC uc = BeanUtil.ucGenerate("demo", "age", 9, demo);
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
		
		TransactionUtil tc = new TransactionUtil() {
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
		System.out.println(exception);
		
	}
}
