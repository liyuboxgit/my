package com.rthd.framework.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.rthd.framework.mybatis.EnhanceMapper;
import com.rthd.tinychxu.util.BeanUtil;

public class BaseService {
	@Autowired EnhanceMapper em;
	public EnhanceMapper em() {
		return this.em;
	}
	
	public <T> int merge(Object param,Class<T> type) {
		T target = em.findOne(EnhanceMapper.findone, param, type);
		try {
			Object value = BeanUtil.readValue(param, "version", Integer.class);
			if(value==null) {
				throw new RuntimeException("versions不能为空！");
			}
			BeanUtil.copyFiledsExcludeNullProperty(param, target);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return em.exccute(type, EnhanceMapper.merge, target);
	}

}
