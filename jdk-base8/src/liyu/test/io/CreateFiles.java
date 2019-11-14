package liyu.test.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/**
 * 解析一个文件，内容如
 * =========>d:\\a\\b\\file
 * line.....
 * <=========         
 * @author Administrator
 *
 */
public class CreateFiles {
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
	public static void main(String[] args) throws IOException {
		String pre = "=========>",end="<========";
		
		Path path = Paths.get("t.md");
		List<String> list = Files.readAllLines(path);
		
		ArrayList<Integer> index = new ArrayList<Integer>();
		
		for(int i=0;i<list.size();i++) {
			String l = list.get(i);
			if(l.startsWith(pre)) {
				index.add(i);
			}
		}
		
		for(int i=0;i<index.size()-1;i++) {
			String l = list.get(index.get(i));
			
			File file = new File(l.substring(pre.length()));
			createFile(file);
			
			PrintStream ps = new PrintStream(file);
			for(int j=index.get(i);j<index.get(i+1);j++) {
				String cont = list.get(j);
				if(!cont.startsWith(pre) && !cont.startsWith(end))
					ps.println(list.get(j));
			}
			ps.close();
		}
	
	}
}
