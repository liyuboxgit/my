package liyu.test.springboot.model;

import java.io.Serializable;
import java.util.Date;


import liyu.test.springboot.util.DateUtil;
//@Entity
//@Table(name = "t_contract")
public class Contract implements Serializable{
	private static final long serialVersionUID = 1019941861212647059L;
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private Date createTime;
	private String type;
	private String typeDict;
	//@ManyToOne
	private User author;
	
	public String getCreateTimeStr(){
		return this.createTime == null?"":DateUtil.datetime2str(this.createTime);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTypeDict() {
		return typeDict;
	}
	public void setTypeDict(String typeDict) {
		this.typeDict = typeDict;
	}

}