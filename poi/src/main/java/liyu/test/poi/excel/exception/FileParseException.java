package liyu.test.poi.excel.exception;

public class FileParseException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public FileParseException(String msg){
		super(msg);
	}
	public FileParseException(String msg,Throwable cause){
		super(msg, cause);
	}
}
