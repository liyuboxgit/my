package liyu.test.springbootMybatis.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
*菜单(t_menu)
**/
@Entity
@Table(name="t_menu")
public class MenuDto implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    /**菜单ID*/
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    private Integer menu_id;
    /**菜单名称*/
    private String menu_name;
    /**链接地址*/
    private String link_url;
    /**父菜单*/
    private Integer parent_id;
    /**排序*/
    private Integer sort;
    /**菜单描述*/
    private String menu_descr;
    /**创建者*/
    private String create_name;
    /**修改者*/
    private String update_name;
    /**创建时间*/
    private java.util.Date create_time;
    /**修改时间*/
    private java.util.Date update_time;
    /**版本*/
    private Integer versions;
    /**左侧图标*/
    private String left_icon;
    
    private Boolean checked;
    @Transient
    private List<MenuDto> children;

    public void setMenu_id(Integer menu_id){
        this.menu_id=menu_id;
    }
    public Integer getMenu_id(){
        return this.menu_id;
    }
    public void setMenu_name(String menu_name){
        this.menu_name=menu_name;
    }
    public String getMenu_name(){
        return this.menu_name;
    }
    public void setLink_url(String link_url){
        this.link_url=link_url;
    }
    public String getLink_url(){
        return this.link_url;
    }
    public void setParent_id(Integer parent_id){
        this.parent_id=parent_id;
    }
    public Integer getParent_id(){
        return this.parent_id;
    }
    public void setSort(Integer sort){
        this.sort=sort;
    }
    public Integer getSort(){
        return this.sort;
    }
    public void setMenu_descr(String menu_descr){
        this.menu_descr=menu_descr;
    }
    public String getMenu_descr(){
        return this.menu_descr;
    }
    public void setCreate_name(String create_name){
        this.create_name=create_name;
    }
    public String getCreate_name(){
        return this.create_name;
    }
    public void setUpdate_name(String update_name){
        this.update_name=update_name;
    }
    public String getUpdate_name(){
        return this.update_name;
    }
    public void setCreate_time(java.util.Date create_time){
        this.create_time=create_time;
    }
    public java.util.Date getCreate_time(){
        return this.create_time;
    }
    public void setUpdate_time(java.util.Date update_time){
        this.update_time=update_time;
    }
    public java.util.Date getUpdate_time(){
        return this.update_time;
    }
    public void setVersions(Integer versions){
        this.versions=versions;
    }
    public Integer getVersions(){
        return this.versions;
    }
	public List<MenuDto> getChildren() {
		return children;
	}
	public void setChildren(List<MenuDto> children) {
		this.children = children;
	}
	public String getLeft_icon() {
		return left_icon;
	}
	public void setLeft_icon(String left_icon) {
		this.left_icon = left_icon;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}