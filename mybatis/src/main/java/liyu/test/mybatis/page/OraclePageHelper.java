package liyu.test.mybatis.page;

import java.sql.Connection;
import java.sql.Statement;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;


@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class}),
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class OraclePageHelper extends AbstractPageHelper {
	@Override
	protected <T> String buildPageSql(String sql, Pager<T> page) {
		StringBuilder pageSql = new StringBuilder(200);
		pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
		pageSql.append(sql);
		pageSql.append(" ) temp where rownum <= ").append(page.getEndRow());
		pageSql.append(") where row_id > ").append(page.getStartRow());
		return pageSql.toString();
	}

}
