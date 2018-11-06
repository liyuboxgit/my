package liyu.test.shiro.web.expection;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public String processUnauthenticatedException(Exception e) {
       e.printStackTrace();
       return "exception:"+e.getMessage();
    }
}
