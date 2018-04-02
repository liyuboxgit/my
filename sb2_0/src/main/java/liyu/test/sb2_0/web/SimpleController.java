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
	@RequestMapping("/")
	public String sccess() {
		return "success";
	}
	@Autowired
	private EnhanceMapper em;
	@RequestMapping("/sb/findList")
	public String findList() {
		List<SB> findList = em.findList(EnhanceMapper.findlist, null, SB.class);
		return JSON.toJSONString(findList);
	}
	@Autowired
	private SBService service;
	@RequestMapping("/sb/insert")
	public String insert() {
		SB sb = new SB();
		sb.setBirth(new Date());
		sb.setName("bot");
		sb.setPrice(new BigDecimal(0.2));
		sb.setSex_type('2');

		service.insert(sb);
		return "success";
	}
	
}
