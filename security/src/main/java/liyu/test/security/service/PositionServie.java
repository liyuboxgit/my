package liyu.test.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import liyu.test.security.dao.PositionMapper;
import liyu.test.security.entity.Position;
import liyu.test.security.entity.po.PositionPo;

@Service
public class PositionServie {
	@Autowired
	private PositionMapper positionMapper;
	
	public void add(PositionPo position){
		this.positionMapper.add(position);
	}
	
	public Position get(Integer id){
		return this.positionMapper.get(id);
	}
}
