package liyu.test.springboot.service.person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import liyu.test.springboot.dao.PersionDao;
import liyu.test.springboot.model.Person;
import liyu.test.springboot.service.BaseService;
@Service
public class PersonService extends BaseService{
	@Autowired
	private PersionDao persionDao;
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	public List<Person> findList() {
		return persionDao.findAll();
	}
	

	public void create(Person person) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				persionDao.save(person);
			}
		});
	}
}
