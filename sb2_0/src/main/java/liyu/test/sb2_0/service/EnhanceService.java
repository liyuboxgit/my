package liyu.test.sb2_0.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import liyu.test.sb2_0.domain.User;
import liyu.test.sb2_0.mybatis.EnhanceMapper;
import liyu.test.sb2_0.util.BeanUtil;

@Service
public class EnhanceService {
	@Autowired
	private EnhanceMapper em;
	
	@Transactional
	public int insert(Object param,Class<?> type) {
		return em.exccute(type, EnhanceMapper.insert, param);
	}
	@Transactional
	public int delete(User param,Class<?> type) {
		return em.exccute(type, EnhanceMapper.delete, param);
	}
	@Transactional
	public <T> int merge(Object param,Class<T> type) {
		T target = em.findOne(EnhanceMapper.findone, param, type);
		BeanUtil.copyFiledsExcludeNullProperty(param, target);
		return em.exccute(type, EnhanceMapper.merge, target);
	}
}
