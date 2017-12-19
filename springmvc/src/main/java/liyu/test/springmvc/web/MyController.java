package liyu.test.springmvc.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/my")
public class MyController {
	@RequestMapping("/index")
	public String index(Model model,Date date){
		model.addAttribute("now", date);
		return "index";
	}
	@SuppressWarnings("unused")
	@RequestMapping("/upload")
	@ResponseBody
	public Map<String,Object> upload(HttpServletRequest request){
		if(request instanceof MultipartHttpServletRequest){
			List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("file"); 
			if(!files.isEmpty()){
				MultipartFile mf = files.get(0);
				if(mf!=null){					
					System.out.println(mf.getOriginalFilename()+",size"+mf.getSize());
				}else{
					
				}
			}
		}
		System.out.println(request.getParameter("id"));
		if(false)
		throw new RuntimeException();
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("success", false);
		return map;
	}
}
