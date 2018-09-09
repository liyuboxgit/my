package liyu.test.anbao.core.util;

import com.alibaba.fastjson.JSON;

public class JsonRet {
	public boolean success;
	public String msg;
	public Object data;
	public String ckey;
	
	public JsonRet() {
		
	}
	
	public JsonRet(Object attribute) {
		this.ckey = (String) attribute;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public String getCkey() {
		return ckey;
	}

	public void setCkey(String ckey) {
		this.ckey = ckey;
	}

	public String toString() {
		return JSON.toJSONString(this);
	}
}
