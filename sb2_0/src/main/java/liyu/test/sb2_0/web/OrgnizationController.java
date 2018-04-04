package liyu.test.sb2_0.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import liyu.test.sb2_0.domain.Orgnization;
import liyu.test.sb2_0.mybatis.EnhanceMapper;
import liyu.test.sb2_0.service.OrgnizationService;
import liyu.test.sb2_0.web.util.WebUtil;

@RestController
public class OrgnizationController {
	@Autowired private EnhanceMapper em;
	@Autowired private OrgnizationService service;
	
	@RequestMapping("/org/insert")
	public WebUtil.JsonRet insert(Orgnization org){
		int ret = service.insert(org);
		return ret==1?WebUtil.success():WebUtil.fail("0");
	}
}
