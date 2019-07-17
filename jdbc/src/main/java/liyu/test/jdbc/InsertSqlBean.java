package liyu.test.jdbc;

import java.util.Date;

public class InsertSqlBean {
	private int size;
	private String name;
	private String[] labels;
	private Object[] values;
	public String[] getLabels() {
		return labels;
	}
	public void setLabels(String[] labels) {
		this.labels = labels;
	}
	public Object[] getValues() {
		return values;
	}
	public void setValues(Object[] values) {
		this.values = values;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String sql() {
		StringBuffer sb = new StringBuffer("insert into ");
		sb.append(name);
		sb.append("(");
		for(int i=0;i<this.size;i++) {
			sb.append(this.labels[i]);
			if(i<size-1)
				sb.append(",");
		}
		sb.append(")");
		
		sb.append(" values(");
		for(int i=0;i<this.size;i++) {
			Object v = this.values[i];
			sb.append(v==null?"null":"'"+v.toString()+"'");
			if(i<size-1)
				sb.append(",");
		}
		sb.append(");");
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
		InsertSqlBean insertSqlBean = new InsertSqlBean();
		insertSqlBean.setName("warn");
		insertSqlBean.setSize(3);
		insertSqlBean.setLabels(new String[] {"a","b","c"});
		insertSqlBean.setValues(new Object[] {"1",null,new Date()});
		System.out.println(insertSqlBean.sql());
	}
}
