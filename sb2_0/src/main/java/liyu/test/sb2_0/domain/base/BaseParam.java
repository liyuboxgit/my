package liyu.test.sb2_0.domain.base;

import liyu.test.sb2_0.mybatis.EnhanceMapper;

public class BaseParam {
	private Integer offset;
	private Integer limit;
	
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
	public EnhanceMapper.PageParam getPage(){
		return new EnhanceMapper.PageParam(offset,limit);
	}
}
