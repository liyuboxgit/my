package com.rthd.framework.util;

public class JsonRet implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private boolean suc;
	private String msg;
	private Object data;

	public boolean getSuc() {
		return suc;
	}

	public void setSuc(boolean suc) {
		this.suc = suc;
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