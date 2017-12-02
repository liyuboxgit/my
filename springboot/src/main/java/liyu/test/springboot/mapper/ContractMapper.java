package liyu.test.springboot.mapper;

import org.springframework.stereotype.Repository;

import liyu.test.springboot.model.Contract;
@Repository
public interface ContractMapper {
	public Contract get(int id);
}
