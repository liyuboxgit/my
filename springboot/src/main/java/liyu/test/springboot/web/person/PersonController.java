package liyu.test.springboot.web.person;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	public List<Person> findList(){
		return personService.findList();
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public String create(){
		Person person = new Person();
		person.setName("john");
		person.setCreateTime(new Date());
		person.setTelephone("13288888888");
		
		this.personService.create(person);
		return "create success";
	}
}
