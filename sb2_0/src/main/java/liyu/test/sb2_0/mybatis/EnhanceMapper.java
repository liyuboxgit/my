package liyu.test.sb2_0.mybatis;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
public final class EnhanceMapper extends SqlSessionDaoSupport implements ApplicationContextAware {

	static Logger logger = LoggerFactory.getLogger(EnhanceMapper.class);

	public static final String findlist = "findlist";
	public static final String findone = "findone";
	public static final String findcount = "findcount";
	public static final String insert = "insert";
	public static final String merge = "merge";
	public static final String deleteBatch = "deleteBatch";
	public static final String dynamicUpdate = "dynamicUpdate";

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SqlSessionFactory factory = applicationContext.getBean(SqlSessionFactory.class);
		if (logger.isInfoEnabled()) {
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
					methods.add(m.getId());
				}
			}

			logger.info("ALL MAPPER METHOD ARE:{}", methods);
		}

		super.setSqlSessionFactory(factory);
	}

	public static final String PAGENO = "pageNo";
	public static final String PAGESIZE = "pageSize";

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
		Map<String, Object> map = bean2mapWithPageParam(parameter, page);

		return new Page<T>(page.getPageNo(), page.getPageSize(), this.findCount(countMethod, map, type).intValue(),
				this.findList(method, map, type));
	}

	public <T> int exccute(Class<T> type, String method, Object parameter) {
		if (parameter instanceof UpdateColumnWapper[]) {
			int i = 0;
			for (UpdateColumnWapper el : (UpdateColumnWapper[]) parameter) {
				i += this.getSqlSession().update(_all(method, type), el);
			}
			return i;
		} else {
			return this.getSqlSession().update(_all(method, type), parameter);
		}
	}

	public static Map<String, Object> bean2map(Object bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (key.compareToIgnoreCase("class") == 0) {
					continue;
				}
				Method getter = property.getReadMethod();
				Object value = getter != null ? getter.invoke(bean) : null;
				map.put(key, value);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	private static Map<String, Object> bean2mapWithPageParam(Object bean, PageParam page) {
		Map<String, Object> map = bean2map(bean);
		map.put(PAGENO, page.getPageNo());
		map.put(PAGESIZE, page.getPageSize());
		return map;
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

	public static class UpdateColumnWapper {
		private String tableName;
		private String columnName;
		private Object value;
		private Object primaryKey;

		public UpdateColumnWapper(String tableName, String columnName, Object value, Object primaryKey) {
			this.tableName = tableName;
			this.columnName = columnName;
			this.primaryKey = primaryKey;
			this.value = value;
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
			this.totalPage = totalCount / pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
			
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
