package liyu.test.mybatis.page;

import org.apache.ibatis.session.RowBounds;

public class MyRowBounds extends RowBounds{
	private int offset = RowBounds.NO_ROW_OFFSET;
	private int limit = RowBounds.NO_ROW_LIMIT;
	private int count;
	public MyRowBounds(int offset,int limit){
		this.offset = offset;
		this.limit = limit;
	}
	
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
