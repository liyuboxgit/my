package com.rthd.tinychxu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rthd.framework.mybatis.EnhanceMapper;

@Service
public class EnhanceService {
	@Autowired EnhanceMapper em;
	public EnhanceMapper em() {
		return this.em;
	}
}
