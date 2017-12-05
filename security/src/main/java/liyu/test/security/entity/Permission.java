package liyu.test.security.entity;

import java.io.Serializable;

import liyu.test.framework.mvc.BaseEntity;

public class Permission extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer positionId;
	private Integer moduleId;
	private String buttons;
	
	public Integer getPositionId() {
		return positionId;
	}
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	public String getButtons() {
		return buttons;
	}
	public void setButtons(String buttons) {
		this.buttons = buttons;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
