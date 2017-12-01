package liyu.test.mybatis;

import java.util.List;

import liyu.test.mybatis.mapper.UpdateColumnWapper;
/**
 * 
 * @ClassName: BaseMapper 
 * @Description: CRUD
 * @author: liyu
 * @date: 2017年11月12日 下午4:42:08 
 * @param <T>
 */
public interface BaseMapper <T>{
	public void create(T t);
	
	public void delete(T t);
	public void deleteByPrimaryKey(Object key);
	
	public void update(T t);
	
	public T find(T t);
	public List<T> findList(T t);
	public int findListCount(T t);
	
	public boolean updateColumn(UpdateColumnWapper updateColumnWapper);
}
