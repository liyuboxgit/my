package liyu.test.poi.word;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class Test2 {
	public void testReadByDoc() throws Exception {
		InputStream is = new FileInputStream("承揽电子合同.docx");
		XWPFDocument doc = new XWPFDocument(is);
		/*List<XWPFParagraph> paras = doc.getParagraphs();
		for (XWPFParagraph para : paras) {
			String text = para.getText();
			if(text.contains("：")) {
				String t = text.trim().replaceAll("：", "： ").replaceAll("\\s{20}", "\n");
				String[] strings = t.split("\n");
				for(String tt:strings) {
					String tm = tt.replace(" ", "");
					if(tm.endsWith("：")) {
						tm += "NULL";
					}
					String[] sp = tm.split("：");
					if(sp.length>1)
					System.out.println(sp[0]+","+sp[1]);
				}
			}
		}*/

		List<XWPFTable> tables = doc.getTables();
		List<XWPFTableRow> rows;
		List<XWPFTableCell> cells;
	
		XWPFTableRow th = tables.get(0).getRow(0);
		for (XWPFTableCell cell : th.getTableCells()) {
			System.out.println(cell.getText().trim());
		}
		System.out.println("-------------");
		
		List<XWPFTableRow> list = tables.get(0).getRows();
		for(int i=1;i<list.size()-1;i++) {
			XWPFTableRow row = list.get(i);
			List<XWPFTableCell> listc = row.getTableCells();
			for(XWPFTableCell cell:listc) {
				System.out.println(cell.getText().trim());
			}
		}
		

		this.close(is);
	}

	private void close(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		new Test2().testReadByDoc();
	}

}
