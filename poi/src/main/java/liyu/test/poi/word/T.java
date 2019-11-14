package liyu.test.poi.word;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.POIXMLProperties.CoreProperties;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class T {
	private void printCoreProperties(CoreProperties coreProps) {  
	      System.out.println(coreProps.getCategory());   //分类  
	      System.out.println(coreProps.getCreator()); //创建者  
	      System.out.println(coreProps.getCreated()); //创建时间  
	      System.out.println(coreProps.getTitle());   //标题  
	   } 
	
	public void testReadByExtractor() throws Exception {  
		InputStream is = new FileInputStream("f:\\t.docx");  
	      XWPFDocument doc = new XWPFDocument(is);  
	      XWPFWordExtractor extractor = new XWPFWordExtractor(doc);  
	      String text = extractor.getText();  
	      System.out.println(text);  
	      CoreProperties coreProps = extractor.getCoreProperties();  
	      this.printCoreProperties(coreProps);
	      is.close();
	}
	public static void main(String[] args) throws Exception {
		 new T().testReadByExtractor();
	}

}
