package liyu.test.sb2_0.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import liyu.test.sb2_0.domain.User;
import liyu.test.sb2_0.mybatis.EnhanceMapper;
import liyu.test.sb2_0.util.BeanUtil;

@Service
public class UserService {
	@Autowired
	private EnhanceMapper em;
	@Transactional
	public int insert(User user) {
		return em.exccute(User.class, EnhanceMapper.insert, user);
	}
	@Transactional
	public int delete(User user) {
		return em.exccute(User.class, EnhanceMapper.delete, user);
	}
	@Transactional
	public int merge(User user) {
		User dbuser = em.findOne(EnhanceMapper.findone, user, User.class);
		BeanUtil.copyFiledsExcludeNullProperty(user, dbuser);
		return em.exccute(User.class, EnhanceMapper.merge, dbuser);
	}
}
