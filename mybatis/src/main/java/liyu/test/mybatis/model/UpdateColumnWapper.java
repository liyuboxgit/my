package liyu.test.mybatis.model;

public class UpdateColumnWapper {
	private String tableName;
	private String columnName;
	private Object value;
	private Object primaryKey;
	
	public UpdateColumnWapper(String tableName, String columnName, Object value, Object primaryKey) {
		this.tableName = tableName;
		this.columnName = columnName;
		this.primaryKey = primaryKey;
		this.value = value;
	}
	public String getTableName() {
		return tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public Object getPrimaryKey() {
		return primaryKey;
	}
	public Object getValue() {
		return value;
	}
}
