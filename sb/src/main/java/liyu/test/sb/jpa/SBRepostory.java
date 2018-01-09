package liyu.test.sb.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import liyu.test.sb.mybatis.entity.SB;
@Repository
public interface SBRepostory extends JpaRepository<SB,Integer>{

}
