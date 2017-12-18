package liyu.test.springboot.service.user;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import liyu.test.springboot.mapper.user.UserMapper;
import liyu.test.springboot.model.User;

@Service
public class UserService extends SqlSessionDaoSupport{
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }
	
	@Autowired
	private UserMapper userMapper;
	@Transactional
	public void insert(User user){
		this.userMapper.insert(user);
	}
	
	public User findOne(){
		Collection<String> names = this.getSqlSession().getConfiguration().getMappedStatementNames();

		List<User> list = userMapper.findList(new User());
		return list.isEmpty()?null:list.get(0);
	}
}
