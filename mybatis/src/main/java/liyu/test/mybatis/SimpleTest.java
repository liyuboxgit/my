package liyu.test.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import liyu.test.mybatis.mapper.BaoGuanDanMapper;
import liyu.test.mybatis.model.BaoGuanDan;

/**
 * 分页插件使用动态代理，不是mabatis官方支持的，第三方封装的mybatis分页插件和mybatis缓存，不兼容，所以使用mybatis缓存的时候最好不要使用分页插件
 * @ClassName: SimpleTest 
 * @Description: TODO
 * @author: liyu
 * @date: 2017年11月22日 下午5:54:41
 */
public class SimpleTest {
	public static void main(String[] args) throws IOException, ParseException {
        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = Resources.getResourceAsStream("conf/mybatis-config.xml");
        //构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession session = sessionFactory.openSession();
        BaoGuanDanMapper mapper = session.getMapper(BaoGuanDanMapper.class);
        mapper.findCount(new BaoGuanDan());
        session.commit();		
	}
}
