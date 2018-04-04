package liyu.test.sb2_0.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import liyu.test.sb2_0.domain.Orgnization;
import liyu.test.sb2_0.mybatis.EnhanceMapper;
import liyu.test.sb2_0.util.BeanUtil;

@Service
public class OrgnizationService {
	@Autowired
	private EnhanceMapper em;
	@Transactional
	public int insert(Orgnization org) {
		return em.exccute(Orgnization.class, EnhanceMapper.insert, org);
	}
	@Transactional
	public int delete(Orgnization org) {
		return em.exccute(Orgnization.class, EnhanceMapper.delete, org);
	}
	@Transactional
	public int merge(Orgnization org) {
		Orgnization dbuser = em.findOne(EnhanceMapper.findone, org, Orgnization.class);
		BeanUtil.copyFiledsExcludeNullProperty(org, dbuser);
		return em.exccute(Orgnization.class, EnhanceMapper.merge, dbuser);
	}
}
