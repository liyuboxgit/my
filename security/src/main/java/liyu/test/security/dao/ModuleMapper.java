package liyu.test.security.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import liyu.test.security.entity.Module;
import liyu.test.security.entity.po.ModulePo;

@Repository
public interface ModuleMapper {
	public void add(ModulePo module);

	public List<Module> query();

	public Module get(Integer id);
}
