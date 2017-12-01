package liyu.test.ireport.servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.FileBufferedOutputStream;
import net.sf.jasperreports.engine.util.JRLoader;

public class JasperUtil {
	public static void printPDF(String jasperFilePath, Map<String, Object> parameters, Collection<?> set,
			HttpServletResponse response) throws JRException, IOException {

		JasperReport personJasperReport = (JasperReport) JRLoader.loadObjectFromFile(jasperFilePath);
		JasperPrint print = JasperFillManager.fillReport(personJasperReport, parameters,
				new JRBeanCollectionDataSource(set));

		if (print != null) {
			FileBufferedOutputStream fbos = new FileBufferedOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fbos);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);

			exporter.exportReport();
			fbos.close();
			if (fbos.size() > 0) {
				response.setContentType("application/pdf");
				response.setContentLength(fbos.size());
				ServletOutputStream ouputStream = response.getOutputStream();

				fbos.writeData(ouputStream);
				fbos.dispose();
				ouputStream.flush();
				ouputStream.close();
			}
			
			fbos.close();
		}
	}
	
	public static void main(String[] args) {
		String jasperFilePath = "C:\\liyu\\report3.jasper";
		Map<String,Object> parameters = new HashMap<String,Object>();
		List<Map<String,Object>> set = new ArrayList<Map<String,Object>>();
		
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("field1", "中文");
		set.add(map);
		
		map = new HashMap<String,Object>();
		map.put("field1", "韩文");
		set.add(map);
		map = new HashMap<String,Object>();
		map.put("field1", "韩文");
		set.add(map);
		map = new HashMap<String,Object>();
		map.put("field1", "韩文");
		set.add(map);
		map = new HashMap<String,Object>();
		map.put("field1", "韩文");
		set.add(map);
		map = new HashMap<String,Object>();
		map.put("field1", "韩文");
		set.add(map);
		map = new HashMap<String,Object>();
		map.put("field1", "韩文");
		set.add(map);
		map = new HashMap<String,Object>();
		map.put("field1", "韩文");
		set.add(map);
		
		try {
			JasperReport personJasperReport = (JasperReport) JRLoader.loadObjectFromFile(jasperFilePath);
			JasperPrint print = JasperFillManager.fillReport(personJasperReport, parameters,
					new JRBeanCollectionDataSource(set));
	
			if (print != null) {
				JRGraphics2DExporter exporter = new JRGraphics2DExporter();
				
				BufferedImage bufferedImage = new BufferedImage(print.getPageWidth() * 4, print.getPageHeight() * 4, BufferedImage.TYPE_INT_RGB);  
				 
				Graphics2D g = (Graphics2D) bufferedImage.getGraphics();  
				
				exporter.setParameter(JRGraphics2DExporterParameter.GRAPHICS_2D, g);  
				exporter.setParameter(JRGraphics2DExporterParameter.ZOOM_RATIO, Float.valueOf(4));  
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);  
				exporter.exportReport();  
				g.dispose();
				
				
				FileOutputStream outputStream = new FileOutputStream(new File("c:\\liyu\\temp.jpeg"));
				ImageIO.write(bufferedImage, "JPEG", outputStream);
				outputStream.flush();
				outputStream.close();
			}
		} catch (Exception e) {
			throw new RuntimeException("图片生成错误", e);
		}
	}
}
