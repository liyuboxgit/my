package liyu.test.ireport.servlet;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

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
		URL resource = this.getClass().getResource("/liyu/test/ireport/new_baoguandan.jasper");
		//String path = resource.getPath();
		String path = "C:\\iReport\\new_baoguandan.jasper";
		
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("YLRBH", "100000000000Z");
		parameters.put("HGBH", "1220989344444");
		parameters.put("JNFHR", "珠海小米");
		parameters.put("CJGB", "海关");
		parameters.put("CKRQ", "20180404");
		
		
		parameters.put("SBRQ", "10180203");
		parameters.put("BAH", "12200000000009");
		parameters.put("JWSHR", "印度");
		parameters.put("YSFS", "航空");
		parameters.put("YSGJMCJHCH", "航空102");
		parameters.put("TYDH", "222229");
		parameters.put("SCXSDW", "北京电子");
		
		
		parameters.put("JGFS", "外包");
		parameters.put("ZMXZ", "按税收");
		parameters.put("XKZH", "22222220");
		parameters.put("HTXYH", "X33333");
		parameters.put("MYG", "印度");
		parameters.put("YDG", "印度海关");
		parameters.put("ZYG", "印度海峡");
		parameters.put("LJKA", "北京");
		parameters.put("BZZL", "箱车");
		parameters.put("JS", "4");
		parameters.put("MZ", "100");
		parameters.put("JZ", "120");
		parameters.put("CJFS", "FCI");
		
		
		
		parameters.put("YF", "20");
		parameters.put("BF", "10");
		parameters.put("ZF", "5");
		parameters.put("SFDZJBH", "");
		parameters.put("BJMMJBZ", "");
		
		List<Map<String,Object>> set = new ArrayList<Map<String,Object>>();
		
		HashMap<String, Object> map = null;
		
		map = new HashMap<String,Object>();
		
		map.put("XH", "1");
		map.put("SPBH", "002");
		map.put("SPMCJGG", "衣服XLL");
		map.put("SLJDW", "千克");
		map.put("DJZJBZ", "1.0/2.0/300");
		map.put("YCG", "中国");
		map.put("ZZMDG", "美国");
		map.put("JNHYD", "北京");
		map.put("ZM", "是");
		
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		set.add(map);
		
		if(set.size()%8==0) {
			
		}else {
			int n = 8 - set.size()%8;
			HashMap<String, Object> hashMap = new HashMap<String,Object>();
			hashMap.put("XH", "");
			hashMap.put("SPBH", "");
			hashMap.put("SPMCJGG", "");
			hashMap.put("SLJDW", "");
			hashMap.put("DJZJBZ", "");
			hashMap.put("YCG", "");
			hashMap.put("ZZMDG", "");
			hashMap.put("JNHYD", "");
			hashMap.put("ZM", "");
			for(int i=0;i<n;i++) {
				set.add(hashMap);
			}
		}
		
		try {
			if(set.size()%8==0) {
				parameters.put("YMYS", "/"+set.size()/8);
			}else {
				parameters.put("YMYS", "/"+(set.size()/8+1));
			}
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
