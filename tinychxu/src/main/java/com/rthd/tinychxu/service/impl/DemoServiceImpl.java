package com.rthd.tinychxu.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rthd.framework.base.BaseService;
import com.rthd.framework.mybatis.EnhanceMapper;
import com.rthd.tinychxu.domain.entity.Demo;
import com.rthd.tinychxu.domain.param.DemoParam;
import com.rthd.tinychxu.service.DemoService;
@Service
public class DemoServiceImpl extends BaseService implements DemoService{
	@Override
	public List<Demo> findList(DemoParam parameter) {
		return em().findList(EnhanceMapper.findlist, parameter, Demo.class);
	}
}
