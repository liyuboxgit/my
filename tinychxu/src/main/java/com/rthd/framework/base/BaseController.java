package com.rthd.framework.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rthd.framework.mybatis.BaseParam;
import com.rthd.framework.mybatis.EnhanceMapper.PageParam;
import com.rthd.framework.util.JsonRet;
import com.rthd.framework.util.WebUtil;

public class BaseController {
	private static Logger logger = LoggerFactory.getLogger(BaseController.class);
	/**
	 * 异常处理器
	 * @param request
	 * @param response
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	@ResponseBody
	public JsonRet exception(Exception e) {
		logger.error(e.getMessage(),e);
		return WebUtil.fail("system exception", e);
	}
	
	public PageParam getPage(BaseParam param){
		return new PageParam(param.getPageNo(), param.getPageSize());
	}
}
