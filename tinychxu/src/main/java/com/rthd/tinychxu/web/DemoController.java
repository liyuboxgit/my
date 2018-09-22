package com.rthd.tinychxu.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rthd.framework.base.BaseController;
import com.rthd.framework.util.JsonRet;
import com.rthd.framework.util.WebUtil;
import com.rthd.tinychxu.domain.entity.Demo;
import com.rthd.tinychxu.domain.param.DemoParam;
import com.rthd.tinychxu.service.DemoService;

@RestController
public class DemoController extends BaseController{
	@Autowired
	private DemoService demoService;
	@RequestMapping("/demo")
	public JsonRet demo(DemoParam parameter) {	
		List<Demo> list = demoService.findList(parameter);
		return WebUtil.setResult(list);
	}
}
