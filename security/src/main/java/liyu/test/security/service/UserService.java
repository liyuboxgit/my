package liyu.test.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import liyu.test.framework.mybatis.page.Pager;
import liyu.test.framework.mybatis.page.PagerHolder;
import liyu.test.security.dao.UserMapper;
import liyu.test.security.entity.User;
import liyu.test.security.entity.po.UserPo;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public void add(UserPo user) {
		userMapper.add(user);
	}
	
	public User get(UserPo user){
		return this.userMapper.get(user);
	}
	
	public User getByLoginName(String loginName){
		UserPo po = new UserPo();
		po.setLoginName(loginName);
		return this.userMapper.get(po);
	}
	
	public List<User> qeuryForList(){
		return this.userMapper.query();
	}
	
	public Pager<Object> queryForPage(){
		PagerHolder.startPager(1,10);  
		this.userMapper.query();
		return PagerHolder.endPager();  
	}
}
