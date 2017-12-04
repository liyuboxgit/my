package liyu.test.mybatis;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import liyu.test.mybatis.mapper.Blog;
import liyu.test.mybatis.util.ENUM;
/**
 * 分页插件使用动态代理，不是mabatis官方支持的，第三方封装的mybatis分页插件和mybatis缓存，不兼容，所以使用mybatis缓存的时候最好不要使用分页插件
 * @ClassName: SimpleTest 
 * @Description: TODO
 * @author: liyu
 * @date: 2017年11月22日 下午5:54:41
 */
public class SimpleTest {
	private static final Logger logger = LoggerFactory.getLogger(SimpleTest.class);
	
	static ThreadLocal<SimpleTest> tl = new ThreadLocal<SimpleTest>();
	
	
	
	public static void main(String[] args) throws IOException {
		String en = ENUM.DICT.getCode();
		System.out.println(en);
		
		
		
//		SimpleTest simpleTest = new SimpleTest();
//		simpleTest.f();
		
//		SimpleTest test = new SimpleTest();
//		SimpleTest test2 = new SimpleTest();
//		System.out.println(test);
//		System.out.println(test2);
//		
//		tl.set(test);
//		tl.set(test2);
//		
//		System.out.println(tl.get());
		
//		for(int i=0;i<100;i++)
//		new Thread(new Runnable(){
//
//			@Override
//			public void run() {
//				SimpleTest simpleTest = new SimpleTest();
//				System.out.println(Thread.currentThread().getId()+":"+simpleTest.hashCode());
//				tl.set(simpleTest);
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				System.out.println(Thread.currentThread().getId()+":"+tl.get().hashCode());
//			}
//			
//		}).start();
		
		
		
//		logger.debug("开始测试。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
//		String resource = "conf/mybatis-config.xml";
//		InputStream inputStream = Resources.getResourceAsStream(resource);
//		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//		SqlSession session = sqlSessionFactory.openSession();
//		
//		try {
//			BlogMapper blogMapper = session.getMapper(BlogMapper.class);
//			
//			//blogMapper.updateColumn(new UpdateColumnWapper("blog", "name", "a10000", 100));
//			
//			List<Blog> list = blogMapper.findList(new Blog());
//			System.out.println(list.get(0).getUser());
//			
			//System.out.println(list.size());
//			PagerHolder.startPager(1, 10);
//			blogMapper.findList(new Blog());
//			Pager<Blog> pager = PagerHolder.endPager(Blog.class);
//			System.out.println(pager.getPages());
//			pager.getResult().get(0).getUser();
			
			
//			Blog blog = blogMapper.find(setId(new Blog()));
//			//blogMapper.update(blog);
//			System.out.println(blog.getName());
//			System.out.println(blog.getLoginName());
//			
//			List<Blog> list = blogMapper.findList(new Blog());
//			System.out.println(list.size());
//			
//			UserMapper userMapper = session.getMapper(UserMapper.class);
//			int count = userMapper.findCount(new User());
//			User p = new User();
//			p.setId(1);
//			User user = userMapper.find(p);
//			
//			System.out.println(count);
			
//			session.commit();
//		} finally {
//			session.close();
//		}
			
//			Blog blog = blogMapper.find(setId(new Blog()));
//			//blogMapper.update(blog);
//			System.out.println(blog.getName());
//			System.out.println(blog.getLoginName());
//			
//			List<Blog> list = blogMapper.findList(new Blog());
//			System.out.println(list.size());
//			
//			UserMapper userMapper = session.getMapper(UserMapper.class);
//			int count = userMapper.findCount(new User());
//			User p = new User();
//			p.setId(1);
//			User user = userMapper.find(p);
//			
//			System.out.println(count);
			
//			session.commit();
//		} finally {
//			session.close();
//		}
		
		/*for(int i=0;i<100;i++)
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				int r = new Random().nextInt(20);
				if(r==0) r++;
				SqlSession session = sqlSessionFactory.openSession();
				try {	
				  BaseDao<Blog> baseDao = new BaseDao<Blog>(session,"liyu.test.mybatis.mapper.BlogMapper");
				  System.out.println(Thread.currentThread().getId()+"-1:"+r);
				  Blog blog = new Blog();
				  PagerHolder.startPager(1, r);
				  baseDao.queryForPage("queryForList", blog);
				  Pager<Blog> pager = PagerHolder.endPager(Blog.class);
				  
				  System.out.println(Thread.currentThread().getId()+"-2:"+pager.getResult().size());
				  
				  session.commit();
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
				  session.close();
				}
				
			}
		}).start();*/
		
		/*Blog blog = new Blog();
		  for(int i=0;i<100;i++){
			  blog.setId(i+1);
			  blog.setName("a"+blog.getId());
			  session.insert("liyu.test.mybatis.mapper.BlogMapper.create", blog);
		  }*/
		
		
//		SqlSession session = sqlSessionFactory.openSession();
//		List<Object> list = session.selectList("liyu.test.mybatis.mapper.BlogMapper.queryForList", null);
//		System.out.println(list.size());
//		session.commit();
	}

	private static Blog setId(Blog blog) {
		blog.setId(99);
		return blog;
	}
}
