package liyu.test.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import liyu.test.springboot.model.Person;

@Repository
public interface  PersionDao extends JpaRepository<Person, Long> {

}
