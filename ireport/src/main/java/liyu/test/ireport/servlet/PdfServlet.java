package liyu.test.ireport.servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * 
 * @ClassName: PdfServlet 
 * @Description: jaserReport打印pdf
 * @author: liyu
 * @date: 2017年10月20日 下午4:20:03
 */
public class PdfServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jasperFilePath = "C:/Users/Administrator/report1.jasper";
		URL resource = this.getClass().getResource("/liyu/test/ireport/report1.jasper");
		String path = resource.getPath();
		
		Map<String,Object> parameters = new HashMap<String,Object>();
		List<Map<String,Object>> set = new ArrayList<Map<String,Object>>();
		
		HashMap<String, Object> map = null;
		
		map = new HashMap<String,Object>();
		
		
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		
	
		
		try {
			JasperUtil.printPDF(path, parameters, set, response);
		} catch (JRException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
}
