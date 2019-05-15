package liyu.test.hadoopClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class MainConfigure {
	@RequestMapping("/")
	public String sccess() throws IOException {
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
		
		return "success";
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
	
	public static void main(String[] args) {
		SpringApplication.run(MainConfigure.class, args);
	}
}
