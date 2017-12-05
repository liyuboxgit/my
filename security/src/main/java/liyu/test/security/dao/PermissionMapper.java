package liyu.test.security.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import liyu.test.security.entity.Permission;
import liyu.test.security.entity.po.PermissionPo;

@Repository
public interface PermissionMapper {
	public void add(PermissionPo permission);

	public List<Permission> query(Integer id);
}
