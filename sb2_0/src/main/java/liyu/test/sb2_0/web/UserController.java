package liyu.test.sb2_0.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import liyu.test.sb2_0.domain.User;
import liyu.test.sb2_0.mybatis.EnhanceMapper;
import liyu.test.sb2_0.service.UserService;
import liyu.test.sb2_0.web.util.WebUtil;

@RestController
public class UserController {
	@Autowired private EnhanceMapper em;
	@Autowired private UserService service;
	
	@RequestMapping("/user/findList")
	public WebUtil.JsonRet findList() {
		List<User> ret = em.findList(EnhanceMapper.findlist, null, User.class);
		return WebUtil.setResult(ret);
	}
	
	@RequestMapping("/user/insert")
	public WebUtil.JsonRet insert() {
		User user = new User();
		int ret = service.insert(user);
		return ret==1?WebUtil.success():WebUtil.fail("0");
	}
	
	@RequestMapping("/user/update")
	public WebUtil.JsonRet update(User user) {
		int ret = service.merge(user);
		return ret==1?WebUtil.success():WebUtil.fail("0");
	}
	
	@RequestMapping("/user/delete")
	public WebUtil.JsonRet delete(User user) {
		int ret = service.delete(user);
		return ret==1?WebUtil.success():WebUtil.fail("0");
	}	
}