package liyu.test.springboot_v2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import liyu.test.springboot_v2.mapper.EnhanceMapper;
import liyu.test.springboot_v2.mapper.RoleMapper;
import liyu.test.springboot_v2.model.Role;

@Service
public class RoleService {
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private EnhanceMapper enhanceMapper;
	
}
