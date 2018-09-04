package liyu.test.anbao.core.util;

public class JsonRet {
	public boolean success;
	public String msg;
	public Object data;
	
	public JsonRet(Object attribute) {
		
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
	
}
