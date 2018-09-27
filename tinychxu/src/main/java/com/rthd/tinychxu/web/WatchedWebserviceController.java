package com.rthd.tinychxu.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rthd.framework.base.BaseController;
import com.rthd.framework.config.shiro.JedisPoolManager;
import com.rthd.framework.util.JsonRet;
import com.rthd.framework.util.WebUtil;

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

}
