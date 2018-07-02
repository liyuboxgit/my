package com.rthd.framework.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rthd.framework.mybatis.BaseParam;
import com.rthd.framework.mybatis.EnhanceMapper;
import com.rthd.framework.mybatis.EnhanceMapper.PageParam;
import com.rthd.framework.util.LogUtil;
import com.rthd.framework.util.WebUtil;

public class BaseController {
	private static Logger logger = LoggerFactory.getLogger(BaseController.class);
	@Autowired private EnhanceMapper em;
	public EnhanceMapper em() {
		return this.em;
	}
	/**
	 * 异常处理器
	 * @param request
	 * @param response
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	public String exception(HttpServletRequest request, HttpServletResponse response, Exception e) {
		LogUtil.error(logger, "system error" + e.getMessage(), e);
		if (request.getHeader("accept").indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)) {
			response.setStatus(500);
			response.setContentType("application/json;charset=utf-8");
			WebUtil.responseWrite(response, "system error" + e.toString());
			return null;
		} else {
			request.setAttribute("exceptionMsg", "system error" + e.toString());
			return "error";
		}
	}
	
	public PageParam getPage(BaseParam param){
		return new PageParam(param.getPageNo(), param.getPageSize());
	}
}
