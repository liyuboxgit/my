package liyu.test.springboot.util;

import java.io.Serializable;

/**
 * 
 * @ClassName: AjaxResult 
 * @Description: JSON返回对象
 * @author: liyu
 * @date: 2017年10月16日 下午6:24:39
 */
public class AjaxResult implements Serializable{
	private static final long serialVersionUID = -439344780243913845L;
	
	private boolean success;
	private String msg;
	private Object data;
	
	public static AjaxResult success(){
		AjaxResult result = new AjaxResult();
		result.setSuccess(true);
		return result;
	}
	
	public static AjaxResult fail(String msg){
		AjaxResult result = new AjaxResult();
		result.setSuccess(false);
		result.setMsg(msg);
		return result;
	}
	
	public static AjaxResult setResult(Object data){
		AjaxResult result = new AjaxResult();
		result.setSuccess(true);
		result.setData(data);
		return result;
	}
	
	public boolean getSuccess() {
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
