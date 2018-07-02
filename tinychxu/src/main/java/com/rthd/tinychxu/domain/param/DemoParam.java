package com.rthd.tinychxu.domain.param;

import com.rthd.framework.mybatis.BaseParam;

public class DemoParam extends BaseParam{
	private static final long serialVersionUID = 1L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
