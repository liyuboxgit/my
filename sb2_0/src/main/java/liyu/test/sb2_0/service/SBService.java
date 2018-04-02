package liyu.test.sb2_0.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import liyu.test.sb2_0.domain.SB;
import liyu.test.sb2_0.mybatis.EnhanceMapper;

@Service
public class SBService {
	@Autowired
	private EnhanceMapper em;
	@Transactional
	public String insert(SB sb) {
		em.exccute(SB.class, EnhanceMapper.insert, sb);
		return "success";
	}
}
