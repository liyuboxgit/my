package liyu.test.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class DeleteFile {
	public static void del(File file) {
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			for(File f:files) {
				del(f);
			}
			file.delete();
			System.out.println("delete dictionary:"+file.getPath());
		}else {
			file.delete();
			System.out.println("delete file:"+file.getPath());
		}
	}
	
	public static void createFile(File file) throws IOException {
		System.out.println(file.getPath());
		File parentFile = file.getParentFile();
		if(!parentFile.exists()) {
			boolean mkdirs = parentFile.mkdirs();
			if(mkdirs==true) {
				file.createNewFile();
			}
		}
	}
	public static void main(String[] args) {
		try {
			createFile(new File("C:\\\\Users\\\\Administrator\\\\Downloads\\a\\b\\c\\d"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		del(Paths.get("C:\\Users\\Administrator\\Downloads\\a").toFile());
	}
}
