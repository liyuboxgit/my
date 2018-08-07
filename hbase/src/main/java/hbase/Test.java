package hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
/**
 * 运行本测试类：
 * 1：修改System.setProperty("hadoop.home.dir", "C:\\Users\\Administrator\\Documents\\workspace-sts-3.9.5.RELEASE\\hbase");
 * 2：配置hosts文件，添加192.168.126.128 single
 * @author Administrator
 *
 */
public class Test {
	private static Configuration conf = null;
	static {
		System.setProperty("hadoop.home.dir", "C:\\Users\\Administrator\\Documents\\workspace-sts-3.9.5.RELEASE\\hbase");
		conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.126.128");
    }
	// 创建表
    public static void createTable(String tablename, String columnFamily) throws Exception {
       Connection connection =ConnectionFactory.createConnection(conf);
       Admin admin = connection.getAdmin();
       TableName tableNameObj = TableName.valueOf(tablename);

       if (admin.tableExists(tableNameObj)) {
           System.out.println("Table exists!");
           System.exit(0);
       } else {
           HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tablename));
           tableDesc.addFamily(new HColumnDescriptor(columnFamily));
           admin.createTable(tableDesc);
           System.out.println("createtable success!");

       }

       admin.close();
       connection.close();
    }

    // 删除表
    public static void deleteTable(String tableName) {
       try {
           Connection connection =ConnectionFactory.createConnection(conf);
           Admin admin = connection.getAdmin();
           TableName table = TableName.valueOf(tableName);
           admin.disableTable(table);
           admin.deleteTable(table);
           System.out.println("deletetable " + tableName + "ok.");
       } catch (IOException e) {
           e.printStackTrace();
       }

    }

   // 插入一行记录
   public static void addRecord(String tableName, String rowKey,String family, String qualifier,String value){
       try {
           Connection connection =ConnectionFactory.createConnection(conf);
           Table table = connection.getTable(TableName.valueOf(tableName));
           Put put = new Put(Bytes.toBytes(rowKey));
           put.addColumn(Bytes.toBytes(family),Bytes.toBytes(qualifier), Bytes.toBytes(value));
           put.addColumn(Bytes.toBytes(family),Bytes.toBytes(qualifier), Bytes.toBytes(value));
           table.put(put);
           table.close();
           connection.close();
           System.out.println("insertrecored " + rowKey + " totable " + tableName + "ok.");

       } catch (IOException e) {
           e.printStackTrace();
       }

    }

    public static void main(String[] args) throws Exception {
    	Test.createTable("testTb", "info");
    	Test.addRecord("testTb", "001", "info", "name", "zhangsan");
    	Test.addRecord("testTb", "001", "info", "age", "20");
    	Test.deleteTable("testTb");
    }
}
