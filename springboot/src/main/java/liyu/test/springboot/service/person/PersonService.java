package liyu.test.springboot.service.person;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import liyu.test.springboot.dao.PersionDao;
import liyu.test.springboot.model.Person;

@Service
public class PersonService {
	@Autowired
	private PersionDao personDao;
	
	public List<Person> findList(){
		return this.personDao.findAll();
	}

	public void create(Person person) {
		this.personDao.save(person);
	}
}
