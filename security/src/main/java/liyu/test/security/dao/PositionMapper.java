package liyu.test.security.dao;

import org.springframework.stereotype.Repository;

import liyu.test.security.entity.Position;
import liyu.test.security.entity.po.PositionPo;

@Repository
public interface PositionMapper {
	public void add(PositionPo position);

	public Position get(Integer id);
}
