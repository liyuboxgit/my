package liyu.test.spring;

import org.springframework.stereotype.Component;

@Component
public class ComponentBean {
	private int count = 0;
	
	public int getCount(){
		return count++;
	}
}
