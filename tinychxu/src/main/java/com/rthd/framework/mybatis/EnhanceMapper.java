package com.rthd.framework.mybatis;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.rthd.tinychxu.util.MapUtil;
import com.rthd.tinychxu.util.MapUtil.StringMap;

@Service
public class EnhanceMapper extends SqlSessionDaoSupport implements ApplicationContextAware {

	static Logger logger = LoggerFactory.getLogger(EnhanceMapper.class);

	public static final String findlist = "findlist";
	public static final String findone = "findone";
	public static final String findcount = "findcount";
	public static final String insert = "insert";
	public static final String merge = "merge";
	public static final String delete = "delete";
	public static final String dynamicUpdate = "dynamicUpdate";
	@Value("${show_mapper}")
	private String showMapper = "false";
	private JdbcTemplate jt = null;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SqlSessionFactory factory = applicationContext.getBean(SqlSessionFactory.class);
		if (Boolean.parseBoolean(showMapper)) {
			Configuration configuration = factory.getConfiguration();
			Collection<?> mappedStatements = configuration.getMappedStatements();
			Set<String> methods = new TreeSet<String>(new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});
			for (Object object : mappedStatements) {
				if (object instanceof MappedStatement) {
					MappedStatement m = ((MappedStatement) object);
					methods.add("\n\t"+m.getId());
				}
			}

			logger.info("ALL MAPPER METHOD ARE:=======>{}", methods);
		}

		super.setSqlSessionFactory(factory);
		this.jt = new JdbcTemplate(applicationContext.getBean(DataSource.class));
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

	public <T> Page<T> findPage(String method, String countMethod, PageParam page, Object parameter, Class<T> type) {
		return new Page<T>(page.getPageNo(), page.getPageSize(), this.findCount(countMethod, parameter, type).intValue(),
				this.findList(method, parameter, type));
	}
	
	public <T> List<Map<String,Object>> findListMap(String method, String parameter, Class<T> type) {
		return this.getSqlSession().selectList(_all(method, type), parameter);
	}
	
	public <T> List<T> jdbcFindSingelColumn(String sql, Object[] args, Class<T> columnType, boolean checkOne){
		List<T> list = this.jt.queryForList(sql, args, columnType);
		Assert.isTrue(list.size()<=1, "too many results!");
		return list;
	}
	
	public <T> List<T> jdbcFindSingelColumn(String sql, Class<T> columnType, boolean checkOne){
		return this.jdbcFindSingelColumn(sql, null, columnType, checkOne);
	}
	
	public List<StringMap> jdbcFindListMap(String sql, Object[] args){
		List<Map<String, Object>> list = this.jt.queryForList(sql, args);
		return MapUtil.convert(list);
	}
	
	public <T> int exccute(Class<T> type, String method, Object parameter) {
		if (parameter instanceof UC[]) {
			int i = 0;
			for (UC el : (UC[]) parameter) {
				i += this.getSqlSession().update(_all(method, type), el);
			}
			return i;
		} else {
			return this.getSqlSession().update(_all(method, type), parameter);
		}
	}

	public static class PageParam {
		private int pageNo;
		private int pageSize;

		public PageParam() {
		}

		public PageParam(int pageNo, int pageSize) {
			this.pageNo = pageNo;
			this.pageSize = pageSize;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

		public int getPageNo() {
			return pageNo;
		}

		public void setPageNo(int pageNo) {
			this.pageNo = pageNo;
		}
	}

	public static class UC {
		private String tableName;
		private String columnName;
		private Object value;
		private Integer primaryKey;
		private Integer version;

		public UC(String tableName, String columnName, Object value, Integer primaryKey, Integer version) {
			this.tableName = tableName;
			this.columnName = columnName;
			this.value = value;
			this.primaryKey = primaryKey;
			this.version = version;
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
