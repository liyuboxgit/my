package liyu.test.hadoopClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;

public class S {
	public static void main(String[] args) {
		Configuration conf = new Configuration();
		FileSystem fs = new DistributedFileSystem();
		try {
			fs.initialize(new URI("hdfs://localhost:9000/"), conf);
			for (FileStatus f : fs.listStatus(new Path("/"))) {
				System.out.println(f.getPath().getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
