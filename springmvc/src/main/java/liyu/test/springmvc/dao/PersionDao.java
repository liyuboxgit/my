package liyu.test.springmvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import liyu.test.springmvc.dao.model.Person;
@Repository
public interface  PersionDao extends JpaRepository<Person, Long> {

}
