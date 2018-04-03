package liyu.test.sb2_0.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import liyu.test.sb2_0.domain.SB;
import liyu.test.sb2_0.mybatis.EnhanceMapper;
import liyu.test.sb2_0.service.SBService;

@RestController
public class SimpleController {
	@Autowired private EnhanceMapper em;
	@Autowired private SBService service;
	
	@RequestMapping("/sb/findList")
	public String findList() {
		List<SB> findList = em.findList(EnhanceMapper.findlist, null, SB.class);
		return JSON.toJSONString(findList);
	}
	
	@RequestMapping("/sb/insert")
	public String insert() {
		SB sb = new SB();
		this.setProperty(sb);
		service.insert(sb);
		return "success";
	}
	
	private void setProperty(SB sb) {
		sb.setBirth(new Date());
		sb.setName("bill");
		sb.setPrice(new BigDecimal(0.2));
		sb.setSex_type('2');
	}
}
