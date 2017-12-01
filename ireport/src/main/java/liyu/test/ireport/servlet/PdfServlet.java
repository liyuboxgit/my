package liyu.test.ireport.servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

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
		String jasperFilePath = "C:\\liyu\\report22.jasper";
		Map<String,Object> parameters = new HashMap<String,Object>();
		List<Map<String,Object>> set = new ArrayList<Map<String,Object>>();
		
		HashMap<String, Object> map = null;
		
		map = new HashMap<String,Object>();
		map.put("f1", "中文");
		//set.add(map);
		
		//map = new HashMap<String,Object>();
		map.put("f2", "韩文");
		//set.add(map);
		//map = new HashMap<String,Object>();
		map.put("f3", "韩文");
		//set.add(map);
		//map = new HashMap<String,Object>();
		map.put("f4", "韩文");
		//set.add(map);
		//map = new HashMap<String,Object>();
		map.put("f5", "韩文");
		//set.add(map);
		//map = new HashMap<String,Object>();
		map.put("f6", "韩文");
		//set.add(map);
		//map = new HashMap<String,Object>();
		map.put("f7", "韩文");
		//set.add(map);
		// = new HashMap<String,Object>();
		map.put("f8", "韩文");
		//set.add(map);
		//map = new HashMap<String,Object>();
		map.put("f9", "韩文");
		//set.add(map);
		//map = new HashMap<String,Object>();
		map.put("f10", "韩文");
		//set.add(map);
		//map = new HashMap<String,Object>();
		map.put("f11", "韩文");
		set.add(map);
		
	/*	try {
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
		}*/
		
		try {
			JasperUtil.printPDF(jasperFilePath, parameters, set, response);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//bgd(request, response);
		//test(request, response);
	}

	private List<Map<String, Object>> findBgdItemPrintService(Map<String, Object> bgdMap) throws IOException {
		String resource = "conf/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();
		
		SqlSession openSession = sqlSessionFactory.openSession();
		
		List<Map<String, Object>> ret = openSession.selectList("com.system.tmssb.drawback.mapper.BgdInfoMapper.findBgdItemPrintMapper", bgdMap);
		openSession.close();
		return ret;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void test(HttpServletRequest request, HttpServletResponse response) throws IOException{
		File reportFile = new File("C:/liyu/report1.jasper");
		Map<String, Object> parameters = new HashMap<String, Object>();
		List<Map<String, Object>> printList = new ArrayList<Map<String, Object>>();
		
		parameters.put("title", "增值税发票");
		parameters.put("kaipiaori", "2016年12月15日");
		parameters.put("gname", "北京税讯技术有限公司");
		parameters.put("gnsrsbh", "");
		parameters.put("gaddresstel", "");
		parameters.put("gaddresstel", "");
		parameters.put("gkhhzh", "");

		parameters.put("mmq", "");
		
		Map<String, Object> el = new HashMap<String, Object>();
		el.put("f1", "餐费");
		el.put("f2", "");
		el.put("f3", "");
		el.put("f4", "");
		el.put("f5", "195.145631067");
		el.put("f6", "195.15");
		el.put("f7", "3%");
		el.put("f8", "5.85");
		
		Map<String, Object> e2 = new HashMap<String, Object>();
		e2.put("f1", "");
		e2.put("f2", "");
		e2.put("f3", "");
		e2.put("f4", "");
		e2.put("f5", "");
		e2.put("f6", "");
		e2.put("f7", "");
		e2.put("f8", "");
		
		printList.add(el);
		printList.add(e2);
		printList.add(e2);
		printList.add(e2);
		
		
		parameters.put("he1", "");
		parameters.put("he2", "");
		parameters.put("he3", "");
		parameters.put("he4", "");
		parameters.put("he5", "");
		parameters.put("he6", "");
		parameters.put("he7", "");
		parameters.put("dx", "");
		parameters.put("xx", "");
		
		parameters.put("gname_1", "北京金鹏世纪万福饮有限公司");
		parameters.put("gnsrsbh_1", "110106699591587");
		parameters.put("gaddresstel_1", "刘家窑");
		parameters.put("gkhhzh_1", "民生银行");
		parameters.put("bz", "民生银行");
		
		
		parameters.put("skren", "开票员");
		parameters.put("fuhe", "开票员");
		parameters.put("kaipren", "开票员");
		parameters.put("xiaoshouf", "");
		
		try {
			JasperUtil.printPDF(reportFile.getPath(), parameters, printList, response);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	private void bgd(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String bgd_no = "010120170000010988";
		File reportFile = new File(request.getSession().getServletContext().getRealPath("/rpt/bgd.jasper"));
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			Map<String, Object> bgdMap = new HashMap<String, Object>();
			List<Map<String, Object>> bgdList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> printList = new ArrayList<Map<String, Object>>();
			String[] str = null;
			if (bgd_no!=null && !bgd_no.equals("")) {
				str = bgd_no.split("@@");
				for (String str_bgd : str) {
					bgdMap.put("bgd_no", str_bgd);
					bgdList = this.findBgdItemPrintService(bgdMap);

					Integer page = (int) Math.ceil((double)bgdList.size() / 5);
					for (int i = 0; i < page * 5; i++) {
						Map<String, Object> printMap = new HashMap<String, Object>();
						printMap.put("BGD_NO", bgdList.get(0).get("BGD_NO"));
						if (i< bgdList.size()) {
							printMap.put("YLRBH",bgdList.get(i).get("YLRBH"));
							printMap.put("HGNAME", bgdList.get(i).get("HGNAME"));
							printMap.put("BAH",bgdList.get(i).get("BAH"));
							printMap.put("LJ_DATE",bgdList.get(i).get("LJ_DATE"));
							printMap.put("SB_DATE",bgdList.get(i).get("SB_DATE"));
							
							printMap.put("DWCODE", "00000000002");
							printMap.put("DWNAME", "US");
							printMap.put("TRANS_NAME",bgdList.get(i).get("TRANS_NAME"));
							printMap.put("TRANS_TOOLS",bgdList.get(i).get("TRANS_TOOLS"));
							printMap.put("TYDH", bgdList.get(i).get("TYDH"));
							printMap.put("FHDWBH", "0000000001");
							printMap.put("FHDWMC", "US");
							String myCode = (String)bgdList.get(i).get("MYCODE");
							if(myCode == "" || "".equals(myCode)){
								myCode = null;
							}
							printMap.put("MYCODE", myCode);
							printMap.put("MYNAME", bgdList.get(i).get("MYNAME"));
							printMap.put("ZMXZMC", bgdList.get(i).get("ZMXZMC"));
							printMap.put("ZMXZ",bgdList.get(i).get("ZMXZ"));
							printMap.put("SETNAME", bgdList.get(i).get("SETNAME"));//结汇方式名称
							printMap.put("XKZH", bgdList.get(i).get("XKZH")); //许可证号
							printMap.put("GBNAME", bgdList.get(i).get("GBNAME"));
							printMap.put("ZYGNAME",bgdList.get(i).get("ZYGNAME"));
							printMap.put("HYDBM", bgdList.get(i).get("HYDBM"));
							printMap.put("PZWH", bgdList.get(i).get("PZWH"));
							printMap.put("MYFS", bgdList.get(i).get("MYFS"));
							printMap.put("T_AMT", bgdList.get(i).get("T_AMT"));
							printMap.put("BF_AMT", bgdList.get(i).get("BF_AMT"));
							printMap.put("ZF_AMT", bgdList.get(i).get("ZF_AMT"));
							printMap.put("CONTRACT_NO",bgdList.get(i).get("CONTRACT_NO"));
							printMap.put("JS", bgdList.get(i).get("JS"));
							printMap.put("BZZL",bgdList.get(i).get("BZZL"));
							printMap.put("GROSSWEIGHT",bgdList.get(i).get("GROSSWEIGHT"));
							printMap.put("NETWEIGHT",bgdList.get(i).get("NETWEIGHT"));
							printMap.put("JZX_NO", bgdList.get(i).get("JZX_NO"));
							printMap.put("FJ", bgdList.get(i).get("FJ"));
							printMap.put("SCCJ", bgdList.get(i).get("SCCJ"));//生产厂家
							printMap.put("BJMMJBZ", bgdList.get(i).get("BJMMJBZ"));
							printMap.put("INVO_NO", bgdList.get(i).get("INVO_NO"));
							printMap.put("SORTNO", bgdList.get(i).get("SORTNO"));
							printMap.put("CMCODE", bgdList.get(i).get("CMCODE"));
							printMap.put("CMNAME", bgdList.get(i).get("CMNAME"));
							printMap.put("MT_TYPE",bgdList.get(i).get("MT_TYPE"));
							printMap.put("CJ_NUM",5.5);
							printMap.put("CJDW", bgdList.get(i).get("CJDW"));
							printMap.put("CJDWMC", bgdList.get(i).get("CJDWMC"));
							printMap.put("ZZMDG", bgdList.get(i).get("ZZMDG"));//最终目的国
							printMap.put("PRICE", 0.05);
							printMap.put("CJJE", 200.5);
							printMap.put("CJBZ", bgdList.get(i).get("CJBZ"));
							printMap.put("CJBZNAME", bgdList.get(i).get("CJBZNAME"));
							printMap.put("ZMFS", bgdList.get(i).get("ZMFS"));
							printMap.put("HYDMC", bgdList.get(i).get("HYDMC"));
							printMap.put("ZMXZ", bgdList.get(i).get("ZMXZ"));
							printMap.put("HGCODE", bgdList.get(i).get("HGCODE"));
							String transType = (String)bgdList.get(i).get("TRANS_TYPE");
							if(transType == "" || "".equals(transType)){
								transType = null;
							}
							printMap.put("TRANS_TYPE", transType);
							printMap.put("GBCODE", bgdList.get(i).get("GBCODE"));
							String gbCode = (String)bgdList.get(i).get("GBCODE");
							if(gbCode == "" || "".equals(gbCode)){
								gbCode = null;
							}
							printMap.put("GBCODE", gbCode);
							printMap.put("MDD", bgdList.get(i).get("MDD"));
							printMap.put("SBDWBM", bgdList.get(i).get("SBDWBM"));
							printMap.put("SBDWMC", bgdList.get(i).get("SBDWMC"));
							parameters.put("SBDWMC", bgdList.get(i).get("SBDWMC"));
						} 
						printList.add(printMap);
					}
				}

			}
			
			JasperUtil.printPDF(reportFile.getPath(), parameters, printList, response);
			
			
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
}
