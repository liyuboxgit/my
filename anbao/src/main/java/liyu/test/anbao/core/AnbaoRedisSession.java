package liyu.test.anbao.core;

import java.io.Serializable;

import liyu.test.anbao.core.util.StringMap;

public class AnbaoRedisSession implements Serializable{
	private static final long serialVersionUID = 4316903886236713382L;
	
	public static final String SK = "SK";
	
	private StringMap inner;
	private String uuid;
	public AnbaoRedisSession(String uuid) {
		this.uuid = uuid;
		this.inner = new StringMap();
	}
	
	public void setAttribute(String key,Object value) {
		this.inner.put(key, value);
	}
	
	public Object getAttribute(String key) {
		return this.inner.get(key);
	}

	public String getUuid() {
		return uuid;
	}
}
