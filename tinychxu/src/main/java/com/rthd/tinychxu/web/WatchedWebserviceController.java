package com.rthd.tinychxu.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rthd.framework.config.shiro.JedisPoolManager;
import com.rthd.framework.mybatis.EnhanceMapper;
import com.rthd.framework.util.JsonRet;
import com.rthd.framework.util.WebUtil;
import com.rthd.framework.web.BaseController;
import com.rthd.tinychxu.domain.Demo;
import com.rthd.tinychxu.domain.param.DemoParam;

@RestController
@RequestMapping("/webs")
public class WatchedWebserviceController extends BaseController{
	@Autowired
	private JedisPoolManager jedisPoolManager;
	
	/**
	 * 此方法描述的是：清理redis缓存
	 * @author: liyu@rthdtax.com
	 * @version: 2018年6月25日 上午11:26:45
	 */
	@RequestMapping("/cc")
	@ResponseBody
	public JsonRet clearRedisCache() {
		jedisPoolManager.clearCache();
		return WebUtil.success();
	}
	
	@RequestMapping("/demo")
	public JsonRet demo(DemoParam parameter) {		
		List<Demo> list = em().findList(EnhanceMapper.findlist, parameter, Demo.class);
		return WebUtil.setResult(list);
	}
}
