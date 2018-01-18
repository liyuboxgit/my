package liyu.test.sb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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
	
	
	public static void copy(String src, String dest) throws IOException {
		File srcFile = Paths.get(src).toFile();
		File destFile = Paths.get(dest).toFile();
		
		if(srcFile.exists()) {
			try (
				FileInputStream in = new FileInputStream(srcFile);
				FileOutputStream out = new FileOutputStream(destFile);
				FileChannel inChannel = in.getChannel();
				FileChannel outChannel = out.getChannel();
			){
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				while (true) {
					int eof = inChannel.read(buffer);
					if (eof == -1) {
						break;
					}
					buffer.flip();
					outChannel.write(buffer);
					buffer.clear();
				}
			} 
		}
	} 
}
