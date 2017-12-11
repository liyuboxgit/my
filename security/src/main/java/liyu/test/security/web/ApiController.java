package liyu.test.security.web;

import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.druid.support.json.JSONUtils;

@Controller
@RequestMapping("/api")
public class ApiController {
	@SuppressWarnings("rawtypes")
	@RequestMapping("/ajax")
	public String json(@RequestBody Map map,HttpServletRequest request,HttpServletResponse response){
		try{
			Writer writer = response.getWriter();
			String string = JSONUtils.toJSONString(map);
			writer.write(string);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/form")
	public String form(@RequestParam Map map,HttpServletRequest request,HttpServletResponse response){
		try{
			Writer writer = response.getWriter();
			String string = JSONUtils.toJSONString(map);
			writer.write(string);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
