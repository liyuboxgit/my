package liyu.test.mybatis.util;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.github.pagehelper.PageRowBounds;

import liyu.test.mybatis.page.MyRowBounds;

public class BaseDao<T> {
	private SqlSession session;
	private String nameSpace;
	public BaseDao(SqlSession session,String mapperNameSpace){
		this.session = session;
		this.nameSpace = mapperNameSpace;
	}
	public IGenericPage<T> findPageBy(T param, int pageNo, int pageSize, String sort, String dir, String sqlName) {
        //Map<String, Object> paramMap = bean2Map(param);
        int count = this.getSqlSession().selectOne(getSqlName(sqlName+"Count"), param);
        if (count < 1) {
            return GenericDefaultPage.emptyPage();
        }
        
        /*if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(dir)) {
            paramMap.put("sortName", sort);
            paramMap.put("dirName", dir);
        }*/
        int start = GenericDefaultPage.getStartOfPage(pageNo, pageSize) - 1;
        PageRowBounds pageRowBounds = new PageRowBounds(start, pageSize);
        MyRowBounds myRowBounds = new MyRowBounds(1, 3);
        List<T> lst = this.getSqlSession().selectList(getSqlName(sqlName), param, myRowBounds);
        return new GenericDefaultPage<T>(pageNo, pageSize, lst, pageRowBounds.getTotal().intValue());
    }
	
	public List<T> queryForPage(String sqlName,Object param){
		return this.getSqlSession().selectList(getSqlName(sqlName),param);
	}
	
	private String getSqlName(String string) {
		return this.nameSpace+"."+string;
	}

	private SqlSession getSqlSession() {
		return this.session;
	}

	/*private Map bean2Map(Object bean, String... noProName) {
        Map returnMap = new HashMap();
        if (null != bean) {
            try {
                Class type = bean.getClass();
                BeanInfo beanInfo = Introspector.getBeanInfo(type);

                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                for (int i = 0; i < propertyDescriptors.length; ++i) {
                    PropertyDescriptor descriptor = propertyDescriptors[i];
                    String propertyName = descriptor.getName();
                    if (!(propertyName.equals("class"))) {
                        if (noProName != null && noProName.length != 0) {
                            for (String pro : noProName) {
                                if (!propertyName.equals(pro)) {
                                    Method readMethod = descriptor.getReadMethod();
                                    Object result = readMethod.invoke(bean, new Object[0]);
                                        returnMap.put(propertyName, result);
                                }
                            }
                        } else {
                            Method readMethod = descriptor.getReadMethod();
                            Object result = readMethod.invoke(bean, new Object[0]);
                                returnMap.put(propertyName, result);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnMap;
    }*/
}
