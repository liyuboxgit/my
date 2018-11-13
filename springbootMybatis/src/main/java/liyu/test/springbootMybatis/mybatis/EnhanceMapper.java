package liyu.test.springbootMybatis.mybatis;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Table;
import javax.sql.DataSource;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class EnhanceMapper extends SqlSessionDaoSupport implements ApplicationContextAware {

	static Logger logger = LoggerFactory.getLogger(EnhanceMapper.class);

	public final String findlist = "findlist";
	public final String findone = "findone";
	public final String findcount = "findcount";
	public final String insert = "insert";
	public final String merge = "merge";
	public final String delete = "delete";
	public final String dynamicUpdate = "dynamicUpdate";

	private JdbcTemplate jdbc;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SqlSessionFactory sqlSessionFactory = applicationContext.getBean(SqlSessionFactory.class);
		super.setSqlSessionFactory(sqlSessionFactory);
		this.jdbc = new JdbcTemplate(applicationContext.getBean(DataSource.class));
		
		Configuration configuration = sqlSessionFactory.getConfiguration();
		Collection<MappedStatement> mappedStatements = configuration.getMappedStatements();
		Set<String> methods = new TreeSet<String>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		for (MappedStatement el : mappedStatements) {
			methods.add(el.getId());
		}

		logger.info("Mybatis mepper methods =======>{}", methods);
		
	}

	private String _all(String method, Class<?> type) {
		return type.getName() + "." + method;
	}

	public <T> List<T> findList(String method, Object parameter, Class<T> type) {
		return this.getSqlSession().selectList(_all(method, type), parameter);
	}

	public <T> T findOne(String method, Object parameter, Class<T> type) {
		return this.getSqlSession().selectOne(_all(method, type), parameter);
	}

	public <T> Long findCount(String method, Object parameter, Class<T> type) {
		return this.getSqlSession().selectOne(_all(method, type), parameter);
	}
	
	private void checkOne(List<?> list, boolean checkOne) {
		if(checkOne) {
			Assert.isTrue(list.size()<=1, "too many results!");
		}
	}
	
	public <T> List<Map<String,Object>> findFindMapList(String method, Object parameter, Class<T> type, boolean checkOne) {
		List<Map<String,Object>> list = this.getSqlSession().selectList(_all(method, type), parameter);
		this.checkOne(list, checkOne);
		return list;
	}
	
	public <T> List<T> jdbcFindSingleColumnObject(String sql,Object[] args,Class<T> columnType, boolean checkOne) {
		List<T> queryForList = jdbc.queryForList(sql, columnType, args);
		this.checkOne(queryForList, checkOne);
		return queryForList;
	}

	public List<Map<String,Object>> jdbcFindMapList(String sql,Object[] args, boolean checkOne) {
		List<Map<String,Object>> queryForList = jdbc.queryForList(sql, args);
		this.checkOne(queryForList, checkOne);
		return queryForList;
	}
	
	public <T> Page<T> findPage(String method, String countMethod, BaseParam param, Class<T> type) {
		return new Page<T>(param.getPageNo(), param.getPageSize(), this.findCount(countMethod, param, type).intValue(),
				this.findList(method, param, type));
	}

	public <T> int exccute(Class<T> type, String method, Object parameter) {
		if (parameter instanceof UC[] ) {
			int i = 0;
			for (UC el : (UC[]) parameter) {
				i += this.getSqlSession().update(_all(method, type), el);
			}
			return i;
		}
		
		else if(parameter instanceof Collection<?>) {
			int i = 0;
			for (Object el : (Collection<?>) parameter) {
				i += this.getSqlSession().update(_all(method, type), el);
			}
			return i;
		}
		
		else {
			return this.getSqlSession().update(_all(method, type), parameter);
		}
	}
	/**
	 * 
	 * @Description: 此方法描述的是：实体和列名，生成uc（执行动态sql的输入参数）
	 * @author: you@rthdtax.com
	 * @version: 2018年11月9日 下午3:22:07
	 */
	public UC uc(BaseEntity baseEntity,String col) {
		Table table = baseEntity.getClass().getAnnotation(Table.class);
		if(table !=null && table.name()!=null) {
			try {
				Method method = baseEntity.getClass().getDeclaredMethod("get"+col.substring(0, 1).toUpperCase()+col.substring(1));
				Object val = method.invoke(baseEntity);
				return new UC(table.name(), col, val, baseEntity.getId(), baseEntity.getVersion());
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return null;
		
	}
	/**
	 * 
	 * @Description 此类描述的是：执行动态sql的输入参数
	 * @author: you@rthdtax.com
	 * @version: 2018年11月9日 下午3:23:28
	 */
	public static class UC{
		//表名
		private String tableName;
		//列名
		private String columnName;
		//值
		private Object value;
		//主键
		private Object primaryKey;
		//版本号
		private Object version;

		public UC(String tal, String col, Object val, Object pk, Object ver) {
			this.tableName = tal;
			this.columnName = col;
			this.value = val;
			this.primaryKey = pk;
			this.version = ver;
		}

		public String getTableName() {
			return tableName;
		}

		public String getColumnName() {
			return columnName;
		}

		public Object getPrimaryKey() {
			return primaryKey;
		}

		public Object getValue() {
			return value;
		}

		public Object getVersion() {
			return version;
		}
	}

	public static class Page<T> {
		private int pageNo;
		private int pageSize;
		private int totalCount;
		private int totalPage;
		private List<T> elements;

		public Page() {
		}

		public Page(int pageNo, int pageSize, int totalCount, List<T> elements) {
			this.pageNo = pageNo;
			this.pageSize = pageSize;
			this.totalCount = totalCount;
			this.elements = elements;
			this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
			
		}

		public int getPageNo() {
			return pageNo;
		}

		public void setPageNo(int pageNo) {
			this.pageNo = pageNo;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

		public int getTotalCount() {
			return totalCount;
		}

		public void setTotalCount(int totalCount) {
			this.totalCount = totalCount;
		}

		public int getTotalPage() {
			return totalPage;
		}

		public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;
		}

		public List<T> getElements() {
			return elements;
		}

		public void setElements(List<T> elements) {
			this.elements = elements;
		}
	}
}
