package liyu.test.hbaseClient;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class MainConfigure {
	private static Configuration conf = null;
	static {
		//System.setProperty("hadoop.home.dir", "C:\\Users\\Administrator\\Documents\\workspace-sts-3.9.5.RELEASE\\hbase");
		conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "localhost");
    }
	@RequestMapping("/")
	public String sccess() throws IOException {
		Connection connection = ConnectionFactory.createConnection(conf);
	    Admin admin = connection.getAdmin();
	    TableName tableNameObj = TableName.valueOf("test");
	    if (admin.tableExists(tableNameObj)) {
	    	System.out.println("Table exists!");
	    }else {
	    	System.out.println("Table exists!");
	    } 
		return "success";
	}
	public static void main(String[] args) {
		SpringApplication.run(MainConfigure.class, args);
	}
}
