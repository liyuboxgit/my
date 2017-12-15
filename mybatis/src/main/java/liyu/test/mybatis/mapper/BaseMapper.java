package liyu.test.mybatis.mapper;

import java.util.List;

import liyu.test.mybatis.model.UpdateColumnWapper;
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
	public void deleteBatch(List<T> list);
	
	public void merge(T t);
	
	public T findOne(T t);
	public List<T> findList(T t);
	public int findCount(T t);
	
	public boolean updateColumn(UpdateColumnWapper updateColumnWapper);
}
