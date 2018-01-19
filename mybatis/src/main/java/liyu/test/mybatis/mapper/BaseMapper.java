package liyu.test.mybatis.mapper;

import java.util.List;

import liyu.test.mybatis.model.UpdateColumnWapper;

public interface BaseMapper <T>{
	public void create(T t);
	public void merge(T t);
	public boolean updateColumn(UpdateColumnWapper updateColumnWapper);
	public void delete(T t);
	public void deleteBatch(List<T> list);
	public T findOne(T t);	
	public List<T> findList(T t);
	public Integer findCount(T t);
}