package liyu.test.springbootCfx.service.impl;

import org.springframework.stereotype.Component;

import liyu.test.springbootCfx.entity.Student;
import liyu.test.springbootCfx.service.StudentService;

import javax.jws.WebService;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 * @date 2019/01/30
 */
@WebService(serviceName = "StudentService",
        targetNamespace = "http://service.simple.cfx.com",
        endpointInterface = "liyu.test.springbootCfx.service.StudentService")
@Component
public class StudentServiceImpl implements StudentService {
    @Override
    public List<Student> getStudentList() {
        Student stu1 = new Student("学生1",25);
        Student stu2 = new Student("学生2",30);
        return Arrays.asList(stu1,stu2);
    }
}
