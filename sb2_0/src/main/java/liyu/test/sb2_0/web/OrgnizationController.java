package liyu.test.sb2_0.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import liyu.test.sb2_0.domain.Orgnization;
import liyu.test.sb2_0.domain.param.OrgnizationParam;
import liyu.test.sb2_0.mybatis.EnhanceMapper;
import liyu.test.sb2_0.mybatis.EnhanceMapper.Page;
import liyu.test.sb2_0.service.EnhanceService;
import liyu.test.sb2_0.web.util.WebUtil;

@RestController
public class OrgnizationController {
	@Autowired private EnhanceMapper em;
	@Autowired private EnhanceService es;
	
	@RequestMapping("/org/insert")
	public WebUtil.JsonRet insert(Orgnization org){
		int ret = es.insert(org, Orgnization.class);
		return ret==1?WebUtil.success():WebUtil.fail("0");
	}
	
	@RequestMapping("/org/findPage")
	public WebUtil.JsonRet findPage(OrgnizationParam org){
		Page<Orgnization> ret = em.findPage(EnhanceMapper.findlist, EnhanceMapper.findcount, org.getPage(), org, Orgnization.class);
		return WebUtil.setResult(ret);
	}
}
