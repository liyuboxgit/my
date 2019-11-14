package liyu.test.poi.word;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;

import com.fasterxml.jackson.databind.util.JSONPObject;

import net.minidev.json.JSONArray;

public class Test {
	public String[] getContent(String docPath) {
		WordExtractor extractor = null;
		try {
			extractor = new WordExtractor(new FileInputStream("买卖合同电子版文本（招标）2017719.doc"));
		String [] strArray = extractor.getParagraphText();
			return strArray;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				extractor.close();
			} catch (IOException e) {
				
			}
		}
	}
	
	public Table getTables(HWPFDocument doc) {
		Range range = doc.getRange();
		TableIterator tableIt = new TableIterator(range);
		Table table = null;
		while (tableIt.hasNext()) {
			table = (Table) tableIt.next();
			break;
		}
		
		return table;
	}
	
	public static String cellval(TableCell cell) {
		StringBuffer s = new StringBuffer();
		for (int m = 0; m < cell.numParagraphs(); m++) { 
			Paragraph para = cell.getParagraph(m);
			s.append(para.text().trim());
		}
		String str = s.toString();
		return str.length()>0?str:"NULL";
	}
	private static boolean rowIsNULL(TableRow tr) {
		for (int i = 0; i < tr.numCells(); i++) {
			TableCell cell = tr.getCell(i);
			if(!cellval(cell).equals("NULL")) {
				return false;
			}
		}
		return true;
	}
	
	static class Tbl{
		private String[] header;
		private List<String[]> cont;
		public String[] getHeader() {
			return header;
		}
		public void setHeader(String[] header) {
			this.header = header;
		}
		public List<String[]> getCont() {
			return cont;
		}
		public void setCont(List<String[]> cont) {
			this.cont = cont;
		}
	}
	
	static class Ret{
		private Map<String,Object> text;
		private Tbl table;
		public Tbl getTable() {
			return table;
		}
		public void setTable(Tbl table) {
			this.table = table;
		}
		public Map<String,Object> getText() {
			return text;
		}
		public void setText(Map<String,Object> text) {
			this.text = text;
		}
	}
	
	public static void main(String[] args) {
		Ret ret = new Ret();
		Tbl tbl = new Tbl();
		ret.setText(new HashMap<String,Object>());
		tbl.setCont(new ArrayList<String[]>());
		ret.setTable(tbl);
		/// 得到表格内容
		try {
			HWPFDocument document = new HWPFDocument(new FileInputStream("买卖合同电子版文本（招标）2017719.doc"));
			Table table = new Test().getTables(document);
			
			TableRow th = table.getRow(0);
			String[] ths = new String[th.numCells()];
			for (int i = 0; i < th.numCells(); i++) {
				ths[i] = cellval(th.getCell(i)).replaceAll(" ", "");
			}
			
			tbl.setHeader(ths);
			
			for(String t:ths) {
				System.out.println(t);
			}
			
			for (int j = 1; j < table.numRows(); j++) {
				TableRow tr = table.getRow(j);
				if(!rowIsNULL(tr) && tr.numCells()>1) {
					System.out.println("=============");
					String[] arr = new String[tr.numCells()];
					for (int i = 0; i < tr.numCells(); i++) {
						TableCell cell = tr.getCell(i);
						System.out.println(cellval(cell));
						arr[i] = cellval(cell);
					}
					tbl.getCont().add(arr);
					System.out.println("=============");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		/// 得到所有内容，只取中间带有中文冒号的
		String[] content = new Test().getContent(null);
		for(String s:content) {
			String l = s.trim().replaceAll("", "").replaceAll("：", "： ");
			if(l.contains("： ")) {
				String t = l.replaceAll("\\s{20}", "\n").replaceAll(("："), "： ");
				String[] strings = t.split("\n");
				for(String tt:strings) {
					String tm = tt.replace(" ", "");
					if(tm.endsWith("：")) {
						tm += "NULL";
					}
					String[] sp = tm.split("：");
					System.out.println(sp[0]+","+sp[1]);
					ret.getText().put(sp[0], sp[1]);
				}
			}
			
		}
		
		
		String string = com.alibaba.fastjson.JSONObject.toJSONString(ret,true);
		System.out.println(string);
	}
}
