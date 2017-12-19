package liyu.test.springboot_v2.mapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class EnhanceMapper extends SqlSessionDaoSupport implements ApplicationContextAware{
	private static Logger logger = LoggerFactory.getLogger(EnhanceMapper.class);
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SqlSessionFactory factory = applicationContext.getBean(SqlSessionFactory.class);
		Configuration configuration = factory.getConfiguration();
		@SuppressWarnings("rawtypes")Collection mappedStatements = configuration.getMappedStatements();
		Set<String> methods = new HashSet<String>();
		for (Object object : mappedStatements) {
			if(object instanceof MappedStatement){
				MappedStatement m = ((MappedStatement)object);
				methods.add(m.getId());
			}
		}
		
		for (String str : methods) {
			logger.info("||"+str);
		}
		logger.info("mybatis mappered sql method zise is :"+methods.size());
		
		super.setSqlSessionFactory(factory);
	}
	
	public List<Object> findList(String statement,Object parameter){
		List<Object> selectList = this.getSqlSession().selectList(statement,parameter);
		return selectList;
	}
	
	public Object findOne(String statement,Object parameter){
		Object ret = this.getSqlSession().selectOne(statement, parameter);
		return ret;
	}
}
