package liyu.test.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import liyu.test.security.dao.ModuleMapper;
import liyu.test.security.entity.Module;
import liyu.test.security.entity.po.ModulePo;

@Service
public class ModuleService {
	@Autowired
	private ModuleMapper moduleMapper;

	public void add(ModulePo module) {
		moduleMapper.add(module);
	}
	
	public List<Module> query(){
		return moduleMapper.query();
	}

	public Module get(Integer id) {
		return this.moduleMapper.get(id);
	}
}
