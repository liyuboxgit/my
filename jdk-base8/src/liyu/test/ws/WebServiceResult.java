package liyu.test.ws;

import java.io.Serializable;



/**
 * 
 * @author luwei
 *
 */
public class WebServiceResult implements Serializable {

	private static final long serialVersionUID = -7674036243945857811L;

	private int code = 0; // 0 if success , else -1
	
	private String msg;
	
	private Object data;
	
	//need for webservice
	public WebServiceResult() {
	}
	
	public WebServiceResult(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public WebServiceResult(String msg, Object data) {
		this.msg = msg;
		this.data = data;
	}
	
	public WebServiceResult(Object data) {
		this.data = data;
	}
	
	public static final WebServiceResult success(Object data) {
		return new WebServiceResult(data);
	}
	
	public static final WebServiceResult fail(String msg) {
		return new WebServiceResult(-1, msg, null);
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getData() {
		return this.data;
	}
	
	@Override
	public String toString() {
		return "WebServiceResult [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
}
