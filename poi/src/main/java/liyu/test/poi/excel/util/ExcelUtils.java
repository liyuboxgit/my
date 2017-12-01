package liyu.test.poi.excel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	/**
	 * 
	 * @Title: loadExcelSheet 
	 * @Description: 返回ExcelSheet
	 * @param file
	 * @param sheetIndex
	 * @param fileSuffix
	 * @return
	 * @return: Sheet
	 */
	@SuppressWarnings("resource")
	@Deprecated
	public static Sheet loadExcelSheet(File file, int sheetIndex, String fileSuffix) {  
        FileInputStream inStream = null;  
        try {  
            if(fileSuffix.equals("xls")){            	
            	inStream = new FileInputStream(file);
            	Workbook workBook = WorkbookFactory.create(inStream);  
            	Sheet sheet = workBook.getSheetAt(sheetIndex);
            	return sheet;
            }
            
            if(fileSuffix.equals("xlsx")){            	
            	inStream = new FileInputStream(file);
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inStream);
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetIndex);
                return xssfSheet;
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
	 * @Title: loadWorkBook 
	 * @Description: 加载excel的workbook
	 * @param file
	 * @param fileSuffix
	 * @return
	 * @return: Workbook
	 */
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
	 * @Title: getTemplateCode 
	 * @Description: 获取上传文件编码，如xxx_001.xls(�?.xlsx)中的001
	 * @param fileName
	 * @param fileSuffix
	 * @return
	 * @return: String
	 */
	public static String getTemplateCode(String fileName,String fileSuffix){
		if(fileSuffix.equals("xls"))
			fileName = fileName.substring(0, fileName.length()-4);
		if(fileSuffix.equals("xlsx"))
			fileName = fileName.substring(0, fileName.length()-5);
		
		return fileName.substring(fileName.length()-4,fileName.length());
	}
	
	/**
	 * 
	 * @Title: getCellValue 
	 * @Description: 获取单元格的�?
	 * @param x
	 * @param y
	 * @param sheet
	 * @return
	 * @return: String
	 */
	public static String getCellValue(int x,int y,Sheet sheet){
		Row row = sheet.getRow(y);
		Cell cell = row.getCell(x);
		return getCellValue(cell);
	}
	
	@SuppressWarnings("deprecation")
	private static String getCellValue(Cell cell) {  
        String cellValue = null;    
        if (cell != null) {  
            //判断单元格数据的类型，不同类型调用不同的方法  
            switch (cell.getCellType()) {  
                //数�?�类�?  
                case Cell.CELL_TYPE_NUMERIC:  
                    //进一步判�? ，单元格格式是日期格�?   
                    if (DateUtil.isCellDateFormatted(cell)) {  
                    	Date date = cell.getDateCellValue();
                        cellValue = String.valueOf(date.getTime());  
                    } else {  
                        //数�??  
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
                    //判断单元格是公式格式，需要做�?种特殊处理来得到相应的�??  
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
        
        return null;
    }  
	
	/**
	 * 
	 * @Title: stringmap2bean 
	 * @Description: 将map转成实体bean
	 * @param map
	 * @param type
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @return: T
	 */
	public static <T> T map2bean(Map<String,Object> map,Class<T> type) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		T instance = type.newInstance();
		Field[] fields = type.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			Object value = map.get(name);
			if(value!=null){
				field.setAccessible(true);
				Method method = type.getDeclaredMethod(getMethodName(field.getName()), field.getType());
				method.invoke(instance, value);
			}
		}
		
		return instance;
	}
	
	public static String getMethodName(String fieldName){
		String ret = fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
		return "set"+ret;
	}
	
	/**
	 * 
	 * @Title: setValue 
	 * @Description: 将字符串value，转成field对应的类型返�?
	 * @param field
	 * @param value
	 * @return
	 * @return: Object
	 */
	public static Object setValue(Field field,String value) {
		if(field.getType().equals(String.class)){
			return value;
		}
		if(field.getType().equals(Integer.class)){
			try {				
				return Integer.valueOf(value);
			} catch (Exception e) {
				BigDecimal decimal = new BigDecimal(value);
				return decimal.intValue();
			}
		}
		if(field.getType().equals(BigDecimal.class)){
			return  new BigDecimal(value);
		}
		if(field.getType().equals(Date.class)){
			// case 1
			try {
				return new SimpleDateFormat("yyyy/MM/dd").parse(value);
			} catch (Exception e) {}
			// case 2
			try {
				return new SimpleDateFormat("yyyy-MM-dd").parse(value);
			} catch (Exception e) {}
			
			// defaut
			return new Date(Long.valueOf(value));
		}
		return null;
	}
	/**
	 * 
	 * @Title: exportSimpleExcelFile 
	 * @Description: 导出excel文件，如果oldVersion为true，则�?2003-2007旧版
	 * @param workbook
	 * @param title
	 * @param response
	 * @param isOldVersion
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
}
