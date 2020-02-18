package com.example.accessingdatamysql.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FormTemplateItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@ManyToOne
	private FormTemplate formTemplate;
	
	private String lable;
	@Enumerated(EnumType.STRING)
	private ItemType type;
	private int posTop;
	private int posLeft;
	private int styleWidth;
	private int styleHeight;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public FormTemplate getFormTemplate() {
		return formTemplate;
	}

	public void setFormTemplate(FormTemplate formTemplate) {
		this.formTemplate = formTemplate;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

	public int getPosTop() {
		return posTop;
	}

	public void setPosTop(int posTop) {
		this.posTop = posTop;
	}

	public int getPosLeft() {
		return posLeft;
	}

	public void setPosLeft(int posLeft) {
		this.posLeft = posLeft;
	}

	public int getStyleWidth() {
		return styleWidth;
	}

	public void setStyleWidth(int styleWidth) {
		this.styleWidth = styleWidth;
	}

	public int getStyleHeight() {
		return styleHeight;
	}

	public void setStyleHeight(int styleHeight) {
		this.styleHeight = styleHeight;
	}

	public static enum ItemType {
        //input,textarea,select,input with datepicker,file
		ShortText,LongText,Select,DatePicker,File
    }
}
