package liyu.test.poi.excel.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import liyu.test.poi.excel.annotation.ExcelThead;
import liyu.test.poi.excel.exception.FileParseException;

/**
 * 
 * @ClassName: ExcelExport 
 * @Description: 将数据（实体类）导出excel
 * @author: liyu
 * @date: 2017年11月10日 下午4:14:51
 */
public class ExcelExport {
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
			throw new FileParseException("数据写入错误", cause);
		}
		return workbook;
		
	}

	private static <T> void setCont(Sheet sheet,T data, Field[] fields, int rownum) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Row cont = sheet.createRow(rownum);
		for (int j=0;j<fields.length;j++) {		
			String fname = fields[j].getName().substring(0, 1).toUpperCase()+fields[j].getName().substring(1);
			Method method = data.getClass().getDeclaredMethod("get"+fname, new Class[]{});
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
				return DateUtil.date2Str((Date)ret, DateUtil.DATA_FORMAT);
			}else if(ret instanceof BigDecimal)
				return ((BigDecimal)ret).setScale(2, BigDecimal.ROUND_UP).toString();
			else{
				return ret.toString();
			}
		}
	}

}
