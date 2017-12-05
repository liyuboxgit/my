package liyu.test.security.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import liyu.test.security.entity.Button;
import liyu.test.security.entity.po.ButtonPo;
@Repository
public interface ButtonMapper {
	public List<Button> query();
	public void add(Button button);
	public void del(Integer id);
	public void update(ButtonPo button);
}
