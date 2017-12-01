package liyu.test.poi.excel.util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import liyu.test.poi.excel.annotation.BatchParser;
import liyu.test.poi.excel.annotation.BatchPosition;
import liyu.test.poi.excel.annotation.SingleParser;
import liyu.test.poi.excel.annotation.SinglePosition;
import liyu.test.poi.excel.exception.FileParseException;
import liyu.test.poi.excel.exception.FiletypeNotSupportException;
/**
 * 
 * @ClassName: ExcelParser 
 * @Description: 解析文件，�?�过反射生成实体�?
 * @author: liyu
 * @date: 2017�?10�?23�? 下午1:45:02
 */
public class ExcelParser {
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件后缀，反应文件类型
	 */
	private String fileSuffix;
	/**
	 * poi workbook
	 */
	private Workbook workbook;
	
	public String getFileSuffix(){
		return this.fileSuffix;
	}
	
	public ExcelParser(File file){
		this(file,file.getName());
	}
	public ExcelParser(File file,String fileName){
		this.fileName = fileName;
		check();
		workbook = ExcelUtils.loadWorkBook(file, fileSuffix);
		if(workbook==null){
			throw new RuntimeException("解析文件错误");
		}
	}
	/**
	 * 
	 * @Title: getSheetCount 
	 * @Description: 返回sheet总数�?
	 * @return
	 * @return: int
	 */
	public int getSheetCount(){
		return this.workbook.getNumberOfSheets();
	}
	/**
	 * 
	 * @Title: check 
	 * @Description: check
	 * @return: void
	 */
	private void check(){
		if(this.fileName == null){
			throw new FiletypeNotSupportException("文件名不能为空！");
		}
		if(this.fileName.endsWith(".xls") || this.fileName.endsWith(".xlsx")){
			if(this.fileName.endsWith(".xls")){
				this.fileSuffix = "xls";
			}else{
				this.fileSuffix = "xlsx";
			}
		}else{
			throw new FiletypeNotSupportException("文件类型错误，只能是excel文件，后�?是xls或�?�xlsx�?");
		}
	}
	/**
	 * 
	 * @Title: singleParse 
	 * @Description: 单项数据导入
	 * @param type
	 * @return
	 * @return: T
	 */
	public <T> T singleParse(int sheetIndex,Class<T> type){
		SingleParser parser = type.getAnnotation(SingleParser.class);
		if(parser == null){
			throw new FiletypeNotSupportException("实体类上没加SingleParser注解�?");
		}
		if(!parser.templateCode().equals(ExcelUtils.getTemplateCode(this.fileName,this.fileSuffix))){
			throw new FiletypeNotSupportException("实体类和模板编码不一致，数据无法解析�?");
		}
		
		try {			
			Sheet sheet = this.workbook.getSheetAt(sheetIndex);
			Map<String,Object> ret = new HashMap<String,Object>();
			Field[] fields = type.getDeclaredFields();
			for (Field field : fields) {
				SinglePosition pos = field.getAnnotation(SinglePosition.class);
				if(pos!=null){
					String cellvalue = ExcelUtils.getCellValue(toX(pos.x())-1, pos.y()-1, sheet);
					if(cellvalue == null && pos.nullable() == false){
						throw new FileParseException("数据�?"+ pos.x() +":"+pos.y()+"）为�?!");
					}else{
						try {							
							Object value = ExcelUtils.setValue(field, cellvalue);
							ret.put(field.getName(), value);
						} catch (Exception e) {
							e.printStackTrace();
							throw new FileParseException("数据�?"+ pos.x() +":"+pos.y()+"）类型错�?!�?要的正确类型�?,"+field.getType().getName());
						}
					}
				}
			}
			
		
			return ExcelUtils.map2bean(ret, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 
	 * @Title: batchParse 
	 * @Description: 批量数据导入
	 * @param type
	 * @return
	 * @return: List<T>
	 */
	public <T> List<T> batchParse(int sheetIndex,Class<T> type){
		BatchParser parser = type.getAnnotation(BatchParser.class);
		if(parser == null){
			throw new FiletypeNotSupportException("实体类上没加BatchParser注解�?");
		}
		if(!parser.templateCode().equals(ExcelUtils.getTemplateCode(this.fileName,this.fileSuffix))){
			throw new FiletypeNotSupportException("实体类和模板编码不一致，数据无法解析�?");
		}
		
		try {
			Sheet sheet = this.workbook.getSheetAt(sheetIndex);
			Field[] fields = type.getDeclaredFields();
			int rows = sheet.getPhysicalNumberOfRows();
			System.out.println("解析文件�?"+this.fileName+"）共�?"+rows+"�?");
			List<T> list = new ArrayList<T>();
			for(int i=parser.startRow()-1;i<rows;i++){
				Map<String,Object> ret = new HashMap<String,Object>();
				
				for (Field field : fields) {
					BatchPosition pos = field.getAnnotation(BatchPosition.class);
					if(pos!=null){
						String cellvalue = ExcelUtils.getCellValue(toX(pos.x())-1, i, sheet);
						if(cellvalue == null && pos.nullable() == false){
							throw new FileParseException("数据项（"+pos.x()+":"+(i+1)+"）为�?!");
						}else{
							try {								
								Object value = ExcelUtils.setValue(field, cellvalue);
								ret.put(field.getName(), value);
							}catch(Exception e){
								e.printStackTrace();
								throw new FileParseException("数据�?"+ pos.x() +":"+(i+1)+"）类型错�?!�?要的正确类型�?,"+field.getType().getName());
							}
						}
					}
				}
				list.add(ExcelUtils.map2bean(ret, type));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	
	private int toX(String x){
		int n = 0;
		for (int i = x.length() - 1, j = 1; i >= 0; i--, j *= 26){
	        char c = x.charAt(i);
	        if (c < 'A' || c > 'Z') return 0;
	        n += ((int)c - 64) * j;
	    }
	    return n;
	}
}
