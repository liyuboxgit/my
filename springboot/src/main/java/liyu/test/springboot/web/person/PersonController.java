package liyu.test.springboot.web.person;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import liyu.test.springboot.model.Person;

@Controller
@RequestMapping("/person")
public class PersonController {
	
	@RequestMapping("/findList")
	@ResponseBody
	public List<Person> findList(Model model){
		List<Person> list = Arrays.asList(new Person[] {});
		return list;
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public String add(Model model){
		return "success";
	}
}
