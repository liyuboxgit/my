package liyu.test.mybatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import liyu.test.mybatis.entity.Blog;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws IOException{
        String resource = "mybatis.xml";
        InputStream inputStream = null;
        SqlSessionFactory sqlSessionFactory = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			inputStream.close();
		}
		
        
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	Blog blog = (Blog) session.selectOne("liyu.test.mybatis.mapper.BlogMapper.selectBlog", 1);
        	System.out.println(blog.getCreate_time());
        } finally {
        	session.close();
        }
    }
}
