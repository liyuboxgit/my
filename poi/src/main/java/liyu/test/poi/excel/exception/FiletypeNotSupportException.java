package liyu.test.poi.excel.exception;

public class FiletypeNotSupportException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public FiletypeNotSupportException(String msg){
		super(msg);
	}
}
