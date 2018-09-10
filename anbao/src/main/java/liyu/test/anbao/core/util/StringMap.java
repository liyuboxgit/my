package liyu.test.anbao.core.util;

import java.util.HashMap;

import org.springframework.util.Assert;

public class StringMap extends HashMap<String,Object>{
	private static final long serialVersionUID = 1L;
	
	public StringMap() {
		
	}
	
	public StringMap(String[] keys, Object[] values) {
		Assert.isTrue(keys.length == values.length, "key-value array lenth not equels.");
		for(int i=0;i<keys.length;i++) {
			this.put(keys[i], values[i]);
		}
	}
	
	public StringMap(String keys, Object values) {
		this.put(keys, values);
	}
}
