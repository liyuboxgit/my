package liyu.test.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import liyu.test.security.dao.PermissionMapper;
import liyu.test.security.entity.Permission;
import liyu.test.security.entity.po.PermissionPo;

@Service
public class PermissionService {
	@Autowired
	private PermissionMapper permissionMapper;
	
	public void add(PermissionPo permission){
		this.permissionMapper.add(permission);
	}

	public List<Permission> query(Integer id) {
		return this.permissionMapper.query(id);
	}
}
