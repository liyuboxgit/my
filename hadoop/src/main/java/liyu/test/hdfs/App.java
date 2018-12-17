package liyu.test.hdfs;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException, IOException {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://desk:9000");
		FileSystem fs = FileSystem.get(conf);

		FileStatus[] fileStatuses = fs.listStatus(new Path("/"));
		list(fileStatuses,fs);
	}
	
	private static void list(FileStatus[] fileStatuses,FileSystem fs) throws FileNotFoundException, IllegalArgumentException, IOException {
		for (int i = 0; i < fileStatuses.length; i++) {
			FileStatus fileStatus = fileStatuses[i];
			if(fileStatus.isDirectory()) {
				list(fs.listStatus(fileStatus.getPath()),fs);
			}else {
				System.out.println(fileStatus.getPath());
			}
		}
	}
	
}
