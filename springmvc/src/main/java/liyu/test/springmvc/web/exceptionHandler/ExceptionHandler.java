package liyu.test.springmvc.web.exceptionHandler;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
@ControllerAdvice
public class ExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler({Exception.class}) 
	@ResponseBody
    public String exception(Exception e,HttpServletResponse resp) throws IOException {   
        e.printStackTrace();   
        if(e.getMessage().endsWith("not allowed")) {
        	resp.sendRedirect("http://baidu.com");
        	return null;
        }
        if(e.getMessage().endsWith("not allowed,need return json")) {
        	return "not allowed,need return json";
        }
        else {        	
        	return e.getMessage(); 
        }
    }   
}
