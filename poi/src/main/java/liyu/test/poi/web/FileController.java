package liyu.test.poi.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import liyu.test.poi.excel.model.User;
import liyu.test.poi.excel.util.ExcelParser;
import liyu.test.poi.excel.util.ExcelUtils;

@Controller
@RequestMapping("/poi")
public class FileController {
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		System.out.println(request instanceof MultipartHttpServletRequest);
		return "/index";
	}
	
    @RequestMapping(value="/multifileUpload",method=RequestMethod.POST) 
    @ResponseBody
    public  String multifileUpload(HttpServletRequest request) throws Exception{
    	System.out.println(request instanceof MultipartHttpServletRequest);
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("fname"); 
        if(files.isEmpty()){
            throw new RuntimeException("�ϴ��ļ�Ϊ��");
        }

        String path = System.getProperty("user.home");
        File destFile = null;
        for(MultipartFile file:files){
        	String fileName = file.getOriginalFilename();
            if(file.isEmpty()){
                return "false";
            }else{        
            	File dest = new File(path + "/" + fileName);
             
                try {
                    file.transferTo(dest);
                    destFile = dest;
                }catch (Exception e) { 
                	throw e;
                } 
            }
            
            try {
            	ExcelParser parser = new ExcelParser(destFile);
            	parser.singleParse(0,User.class);
     
			} catch (Exception e) { 
            	e.printStackTrace();
            	throw e;
            } finally {
				destFile.delete();
			}
            
        }
        return "true";
    }
    
    @RequestMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse response) throws IOException{
    	File file = new File("test_0002.xlsx");
    	FileInputStream inStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(inStream);
        ExcelUtils.exportExcel(wb, "test_0002", response, false);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    } 
}
