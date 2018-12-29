package liyu.test.springbootMybatis.mybatis.jdbc;

public interface RedisCache {
	public Long putObject(String uuid, Object key, Object value);
	public Object getObject(String uuid, Object key);
	public void clear(Class<?> type);
}
