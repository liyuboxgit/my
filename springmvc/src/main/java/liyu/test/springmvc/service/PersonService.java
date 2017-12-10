package liyu.test.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import liyu.test.springmvc.dao.PersionDao;
import liyu.test.springmvc.dao.model.Person;

@Service
public class PersonService {
	@Autowired
	private PersionDao persionDao;
	
	public List<Person> findList() {
		return persionDao.findAll();
	}

	public void create(Person person) {
		this.persionDao.save(person);
	}

}
