package liyu.test.mybatis.util;

public enum ENUM {
	/**
	 * 字典
	 */
	DICT("en","English");
	
	private String name;
	private String code;

	private ENUM(String name, String code) {
		this.setName(name);
		this.setCode(code);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 
	 * @Title: getName 
	 * @Description: 通过code查询name，例如：ENUM.getName(ENUM.DICT.code)
	 * @param code
	 * @return
	 * @return: String
	 */
	public static String getName(String code){
		ENUM[] values = ENUM.values();
		for (ENUM el : values) {
			if(el.code.equals(code)){
				return el.name;
			}
		}
		return null;
	}
}
