package liyu.test.springbootMybatis.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
*菜单角色关系(t_role_menu)
**/
@Entity
@Table(name="t_role_menu")
public class RoleMenuDto implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    /**关系ID*/
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    private Integer role_menu_id;
    /**角色ID*/
    private Integer role_id;
    /**菜单ID*/
    private Integer menu_id;
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

    public void setRole_menu_id(Integer role_menu_id){
        this.role_menu_id=role_menu_id;
    }
    public Integer getRole_menu_id(){
        return this.role_menu_id;
    }
    public void setRole_id(Integer role_id){
        this.role_id=role_id;
    }
    public Integer getRole_id(){
        return this.role_id;
    }
    public void setMenu_id(Integer menu_id){
        this.menu_id=menu_id;
    }
    public Integer getMenu_id(){
        return this.menu_id;
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
}