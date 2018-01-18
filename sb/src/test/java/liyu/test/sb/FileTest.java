package liyu.test.sb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileTest {

	public static void main(String[] args) {
		String classPath = java.lang.Object.class.getClass().getResource("/").getFile();
		String src = classPath+"../../src/test/java/";
		String filePath = FileTest.class.getName().replaceAll("\\.", "/")+".java";
		
		try (
			InputStream is = new FileInputStream(new File(src+filePath));
			InputStreamReader r = new InputStreamReader(is,"UTF-8");
			BufferedReader br = new BufferedReader(r);
		){
			String line = null;
			while((line = br.readLine())!=null) {				
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Path path = Paths.get(src, filePath);
		try {
			for(String line:Files.readAllLines(path)) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try (
			Stream<String> lines = Files.lines(path, Charset.defaultCharset());
		){			
			lines.forEach(p->System.out.println(p));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
