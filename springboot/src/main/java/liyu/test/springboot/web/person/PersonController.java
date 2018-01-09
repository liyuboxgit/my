package liyu.test.springboot.web.person;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import liyu.test.springboot.model.Person;
import liyu.test.springboot.service.person.PersonService;

@Controller
@RequestMapping("/person")
public class PersonController {
	@Autowired
	private PersonService personService;
	
	@RequestMapping("/findList")
	@ResponseBody
	public List<Person> findList(Model model){
		List<Person> list = personService.findList();
		return list;
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public String add(Model model){
		Person person = new Person();
		person.setName("en中文");
		person.setTelephone("123456");
		person.setCreateTime(new Date());
		person.setEmail("123456@qq.com");
		
		this.personService.create(person);
		return "success";
	}
}
