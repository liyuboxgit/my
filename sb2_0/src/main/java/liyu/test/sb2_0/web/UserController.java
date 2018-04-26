package liyu.test.sb2_0.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import liyu.test.sb2_0.domain.User;
import liyu.test.sb2_0.domain.param.UserParam;
import liyu.test.sb2_0.mybatis.EnhanceMapper;
import liyu.test.sb2_0.mybatis.EnhanceMapper.Page;
import liyu.test.sb2_0.service.EnhanceService;
import liyu.test.sb2_0.web.util.WebUtil;

@RestController
public class UserController {
	@Autowired private EnhanceMapper em;
	@Autowired private EnhanceService es;
	
	@RequestMapping("/user/findList")
	public WebUtil.JsonRet findList() {
		List<User> ret = em.findList(EnhanceMapper.findlist, null, User.class);
		return WebUtil.setResult(ret);
	}
	
	@RequestMapping("/user/insert")
	public WebUtil.JsonRet insert() {
		User user = new User();
		int ret = es.insert(user,User.class);
		return ret==1?WebUtil.success():WebUtil.fail("0");
	}
	
	@RequestMapping("/user/update")
	public WebUtil.JsonRet update(User user) {
		int ret = es.merge(user, User.class);
		return ret==1?WebUtil.success():WebUtil.fail("0");
	}
	
	@RequestMapping("/user/delete")
	public WebUtil.JsonRet delete(User user) {
		int ret = es.delete(user, User.class);
		return ret==1?WebUtil.success():WebUtil.fail("0");
	}	
	
	@RequestMapping("/user/findPage")
	public WebUtil.JsonRet findPage(UserParam user) {
		Page<User> page = em.findPage(EnhanceMapper.findlist, EnhanceMapper.findcount, user.getPage(), user, User.class);
		return WebUtil.setResult(page);
	}	
}
