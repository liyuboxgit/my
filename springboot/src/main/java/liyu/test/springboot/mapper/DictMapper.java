package liyu.test.springboot.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import liyu.test.springboot.model.Dict;
@Repository
public interface DictMapper {
	public List<Dict> findList();
}
