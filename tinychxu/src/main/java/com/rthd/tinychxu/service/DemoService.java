package com.rthd.tinychxu.service;

import java.util.List;

import com.rthd.tinychxu.domain.entity.Demo;
import com.rthd.tinychxu.domain.param.DemoParam;

public interface DemoService {

	List<Demo> findList(DemoParam parameter);
	
}
