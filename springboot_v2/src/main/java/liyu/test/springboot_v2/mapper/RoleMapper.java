package liyu.test.springboot_v2.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import liyu.test.springboot_v2.model.Role;

@Repository
public class RoleMapper{
	@Autowired
	private EnhanceMapper enhanceMapper;
	
	public Role findone(Role role) {
		return enhanceMapper.findOne(EnhanceMapper.findone, role, Role.class);
	}
}
