package liyu.test.hbaseClient;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class MainConfigure {
	private static Configuration conf = null;
	static {
		conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "localhost");
    }
	@RequestMapping("/")
	public String sccess() throws IOException {
		Connection connection = ConnectionFactory.createConnection(conf);
	    Admin admin = connection.getAdmin();
	    TableName tableNameObj = TableName.valueOf("test");
	    if (admin.tableExists(tableNameObj)) {
	    	getDataByRowKey(connection, "test", "row1");
	    	getByColumnValue(connection,"test");
	    }else {
	    	System.out.println("Table exists!");
	    } 
	    
	    connection.close();
		return "success";
	}
	
	public static void getDataByRowKey(Connection connection, String tableName, String rowKey) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(rowKey.getBytes());
        if(!get.isCheckExistenceOnly()){
            Result result = table.get(get);
            for (Cell cell : result.rawCells()){
                String colName = Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength());
                String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                System.out.println(colName+":"+value);
            }
        }
    }
	/**
	 * use column value filter
	 * @param connection
	 * @param tableName
	 * @throws IOException
	 */
	public static void getByColumnValue(Connection connection, String tableName) throws IOException {
		Table table = connection.getTable(TableName.valueOf(tableName));
		SingleColumnValueFilter scvf = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("b"),
				CompareOp.EQUAL, "java".getBytes());
		scvf.setFilterIfMissing(true);
		Scan scan = new Scan();
		scan.setFilter(scvf);
		ResultScanner resultScanner = table.getScanner(scan);

		for (Result result : resultScanner) {
			List<Cell> cells = result.listCells();
			for (Cell cell : cells) {
				String row = Bytes.toString(result.getRow());
				String family1 = Bytes.toString(CellUtil.cloneFamily(cell));
				String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
				String value = Bytes.toString(CellUtil.cloneValue(cell));
				System.out.println("[row:" + row + "],[family:" + family1 + "],[qualifier:" + qualifier + "]"
						+ ",[value:" + value + "],[time:" + cell.getTimestamp() + "]");
			}
		}

	}
	
	public static void main(String[] args) {
		SpringApplication.run(MainConfigure.class, args);
	}
}
