package liyu.test.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import liyu.test.security.dao.ButtonMapper;
import liyu.test.security.entity.Button;
import liyu.test.security.entity.po.ButtonPo;

@Service
public class ButtonService {
	@Autowired
	private ButtonMapper buttonMapper;

	public List<Button> query() {
		return this.buttonMapper.query();
	}
	
	/*@SuppressWarnings("unchecked")
	public MPageHelper.Page<Button> page(){
		MPageHelper.startPage(1,20);  
		this.buttonMapper.query();
		return (Page<Button>) MPageHelper.endPage();  
	}*/
	
	public void add(Button button) {
		this.buttonMapper.add(button);
	}

	public void del(Integer id) {
		this.buttonMapper.del(id);
	}

	public void update(ButtonPo button) {
		this.buttonMapper.update(button);
	}
}
