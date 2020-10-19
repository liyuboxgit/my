package activity523.rebuild;

import java.util.HashMap;
import java.util.Map;

public class HashMapBuilder {
	private Map<String,Object> inner;
	public HashMapBuilder() {
		this.inner = new HashMap<String,Object>();
	}
	
	public HashMapBuilder set(String key,Object value) {
		this.inner.put(key, value);
		return this;
	}
	
	public Map<String, Object> get() {
		return this.inner;
	}
}
