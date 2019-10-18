package liyu.test.springbootCfx.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import liyu.test.springbootCfx.entity.Student;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/01/30
 */
//命名空间，写一个有意义的http地址就行，并不是网上所说的要写成包名倒序,只不过写成包名倒序易读而已
@WebService(targetNamespace = "http://service.simple.cfx.com")
public interface StudentService {

    @WebMethod
    List<Student> getStudentList();
}