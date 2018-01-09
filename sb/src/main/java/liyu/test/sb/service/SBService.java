package liyu.test.sb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import liyu.test.sb.framework.TransactionalControl;
import liyu.test.sb.jpa.SBRepostory;
import liyu.test.sb.mybatis.EnhanceMapper;
import liyu.test.sb.mybatis.entity.SB;

@Service
public class SBService {
	@Autowired
	private EnhanceMapper em;
	@Autowired
	private SBRepostory sbRepostory;
	@TransactionalControl
	public void create(SB sb) throws Exception{
		em.exccute(SB.class, EnhanceMapper.insert, sb);
	}
	@TransactionalControl
	public void save(SB sb) throws Exception{
		sbRepostory.save(sb);
	}
	
	public SB get(SB sb){
		return em.findOne(EnhanceMapper.findone, sb, SB.class);
	}
}
