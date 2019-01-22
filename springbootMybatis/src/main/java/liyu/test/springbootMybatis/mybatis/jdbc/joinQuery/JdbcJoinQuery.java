package liyu.test.springbootMybatis.mybatis.jdbc.joinQuery;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import liyu.test.springbootMybatis.domain.Demo;
import liyu.test.springbootMybatis.domain.User;
import liyu.test.springbootMybatis.mybatis.EnhanceMapper;
import liyu.test.springbootMybatis.mybatis.jdbc.Cached;
/**
 * 使用Spring jdbcTemplate进行组合join查询，
 * 切面实现其缓存功能，
 * 此class中不能使用重载方法，且方法参数必须是object[]
 * @author Administrator
 *
 */
@Service
public class JdbcJoinQuery {
	@Autowired
	private EnhanceMapper em;
	
	@Cached(tableClasses= {Demo.class,User.class})
	public List<Map<String,Object>> find(Object[] args){
		List<Map<String, Object>> mapList = em.jdbcFindMapList("select count(*) as c from t_demo", null, true);
		return mapList;
	}
}
