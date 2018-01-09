package liyu.test.sb.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiExcelUtil {
	public static String getCellValue(Cell cell) {  
        String cellValue = null;    
        if (cell != null) {  
            switch (cell.getCellType()) {  
                case Cell.CELL_TYPE_NUMERIC:  
                    if (DateUtil.isCellDateFormatted(cell)) {  
                    	Date date = cell.getDateCellValue();
                        cellValue = String.valueOf(date.getTime());  
                    } else {  
                        double value = cell.getNumericCellValue();  
                        int intValue = (int) value;  
                        cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);  
                    }  
                    break;  
                case Cell.CELL_TYPE_STRING:  
                    cellValue = cell.getStringCellValue();  
                    break;  
                case Cell.CELL_TYPE_BOOLEAN:  
                    cellValue = String.valueOf(cell.getBooleanCellValue());  
                    break;  
                case Cell.CELL_TYPE_FORMULA:{  
                    try{  
                        cellValue = String.valueOf(cell.getNumericCellValue());  
                    }catch(IllegalStateException e){  
                        cellValue = String.valueOf(cell.getRichStringCellValue());  
                    }  
                }  
                    break;  
                case Cell.CELL_TYPE_BLANK:  
                    cellValue = "";  
                    break;  
                case Cell.CELL_TYPE_ERROR:  
                    cellValue = "";  
                    break;  
                default:  
                    cellValue = cell.toString().trim();  
                    break;  
            }  
            return cellValue.trim();  
        }  
        
        return cellValue;
    } 
	
	public static Workbook loadWorkBook(File file, String fileSuffix) {
		FileInputStream inStream = null;  
        try {  
            if(fileSuffix.equals("xls")){            	
            	inStream = new FileInputStream(file);
            	Workbook workBook = WorkbookFactory.create(inStream);  
            	return workBook;
            }
            
            if(fileSuffix.equals("xlsx")){            	
            	inStream = new FileInputStream(file);
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inStream);
                return xssfWorkbook;
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                if(inStream!=null){  
                    inStream.close();  
                }                  
            } catch (IOException e) {                  
                e.printStackTrace();  
            }  
        }
		return null;  
	}
	
	/**
	 * 
	 * @Title: workbook 
	 * @Description: 仅用于旧版excel，生成workbook
	 * @param data
	 * @param type
	 * @return
	 * @return: Workbook
	 */
	public static <T> Workbook workbook(Collection<T> data, Class<T> type) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		Field[] fields = type.getDeclaredFields();
		
		try {
			
			Row row = sheet.createRow(0); 
			for (int i=0;i<fields.length;i++) {
				ExcelThead thead = fields[i].getAnnotation(ExcelThead.class);
				Cell cell = row.createCell(i);
				cell.setCellValue(thead != null ? thead.name() : "未命名");
			}
			
			int r = 1;
			for (T e: data) {			
				setCont(sheet, e, fields, r++);
			}
			
		} catch (Exception cause) {
			throw new RuntimeException("数据写入错误", cause);
		}
		return workbook;
		
	}

	private static <T> void setCont(Sheet sheet,T data, Field[] fields, int rownum) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Row cont = sheet.createRow(rownum);
		for (int j=0;j<fields.length;j++) {		
			String fname = fields[j].getName().substring(0, 1).toUpperCase()+fields[j].getName().substring(1);
			Method method = data.getClass().getDeclaredMethod("get"+fname);
			Object ret = method.invoke(data, new Object[]{});
			Cell cell = cont.createCell(j);
			cell.setCellValue(toString(ret));
		}
	}
	
	private static String toString(Object ret){
		if(ret == null){
			return "";
		}else{			
			if(ret instanceof Date){
				return DateUitl.date2str((Date)ret);
			}else if(ret instanceof BigDecimal)
				return ((BigDecimal)ret).setScale(2, BigDecimal.ROUND_UP).toString();
			else{
				return ret.toString();
			}
		}
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface ExcelThead {
		public String name();
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface CellItem{
		public String x();
		public int y();
		public boolean nullable() default true;
	}
	
	
	private static class DateUitl{
		public static String date2str(Date date){
			return new SimpleDateFormat("yyyy-MM-dd").format(date);
		}
	}
	
	/**
	 * 
	 * @Title: batchParse 
	 * @Description: 批量数据导入
	 * @param type
	 * @return
	 * @return: List<T>
	 */
	public static <T> List<T> batchParse(Sheet sheet,Class<T> type,int startRow){
		try {
			Field[] fields = type.getDeclaredFields();
			int rows = sheet.getPhysicalNumberOfRows();
			List<T> list = new ArrayList<T>();
			for(int i=startRow; i<rows; i++){
				Map<String,Object> ret = new HashMap<String,Object>();
				
				for (Field field : fields) {
					CellItem pos = field.getAnnotation(CellItem.class);
					if(pos!=null){
						String cellvalue = getCellValue(sheet.getRow(toX(pos.x())).getCell(i));
						if(cellvalue == null && pos.nullable() == false){
							throw new RuntimeException("dataItem（"+pos.x()+":"+(i+1)+"）is null!");
						}else{
							try {								
								Object value = getFieldValue(field, cellvalue);
								ret.put(field.getName(), value);
							}catch(Exception e){
								e.printStackTrace();
								throw new RuntimeException("dataItem"+ pos.x() +":"+(i+1)+"）type error! the conrect type is, "+field.getType().getName());
							}
						}
					}
				}
				list.add(ReflectUtil.map2bean(ret, type));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Object getFieldValue(Field field, String cellvalue){
		if(cellvalue == null){
			return null;
		}
		
		if(field.getType().equals(String.class)){
			return cellvalue;
		}
		if(field.getType().equals(Integer.class)){
			try {				
				return Integer.valueOf(cellvalue);
			} catch (Exception e) {
				BigDecimal decimal = new BigDecimal(cellvalue);
				return decimal.intValue();
			}
		}
		if(field.getType().equals(BigDecimal.class)){
			return new BigDecimal(cellvalue);
		}
		if(field.getType().equals(Date.class)){
			try {
				return parseString2Date(cellvalue);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	static Pattern p1 = Pattern.compile("\\d{8}");
	static Pattern p2 = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
	static Pattern p3 = Pattern.compile("\\d{4}\\/\\d{2}\\/\\d{2}");
	static Pattern p4 = Pattern.compile("\\d{13,}");
	//liyu test,regex parse string to date
	public static Date parseString2Date(String s) throws ParseException{
		if(p1.matcher(s).matches()){
			return new SimpleDateFormat("yyyyMMdd").parse(s);
		}else if(p2.matcher(s).matches()){
			return new SimpleDateFormat("yyyy-MM-dd").parse(s);
		}else if(p3.matcher(s).matches()){
			return new SimpleDateFormat("yyyy/MM/dd").parse(s);
		}else if(p4.matcher(s).matches()){
			return new Date(Long.valueOf(s));
		}
		return null;
		
	}
	/**
	 * @Description: exportExcel
	 * @param workbook
	 * @param title
	 * @param response
	 * @param oldVersion
	 * @return: void
	 */
	public static void exportExcel(Workbook workbook, String title, HttpServletResponse response, boolean oldVersion) {
		try {
			if(oldVersion){				
				response.setHeader("Content-disposition",
						"attachment; filename=" + new String((title + ".xls").getBytes(), "iso-8859-1"));
				response.setContentType("application/msexcel");
			}else{				
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + new String((title + ".xlsx").getBytes(), "iso-8859-1"));
			}
            
			ServletOutputStream os = response.getOutputStream();
			workbook.write(response.getOutputStream());
			os.flush();
			os.close();
		} catch (Exception e) {
			throw new RuntimeException("下载错误", e);
		}
	}
	
	private static int toX(String x){
		int n = 0;
		for (int i = x.length() - 1, j = 1; i >= 0; i--, j *= 26){
	        char c = x.charAt(i);
	        if (c < 'A' || c > 'Z') return 0;
	        n += ((int)c - 64) * j;
	    }
	    return n;
	}
}
