package liyu.test.springboot_v2.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import liyu.test.springboot_v2.model.Role;

@Repository
public interface RoleMapper {
	public Role get(int id);
	public List<Role> findList(Role user);
	public Integer findCount(Role user);
}
