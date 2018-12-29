package liyu.test.springbootMybatis.mybatis.jdbc.joinQuery;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import liyu.test.springbootMybatis.domain.Demo;
import liyu.test.springbootMybatis.domain.User;
import liyu.test.springbootMybatis.mybatis.EnhanceMapper;
import liyu.test.springbootMybatis.mybatis.jdbc.Cached;
@Service
public class JdbcJoinQuery {
	@Autowired
	private EnhanceMapper em;
	
	@Cached(tableClasses= {Demo.class,User.class},uuid="78314623-6BEC-4761-B8B8-D5D9E24F36FE")
	public List<Map<String,Object>> find(Object[] args){
		List<Map<String, Object>> mapList = em.jdbcFindMapList("select count(*) as c from t_demo", null, true);
		return mapList;
	}
}
