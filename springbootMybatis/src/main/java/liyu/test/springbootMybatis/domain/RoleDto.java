package liyu.test.springbootMybatis.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
*角色(t_role)
**/
@Entity
@Table(name="t_role")
public class RoleDto implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    /**角色ID*/
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    private Integer role_id;
    /**角色名称*/
    private String role_name;
    /**父角色*/
    private String parent_id;
    /**角色描述*/
    private String role_descr;
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
    
    private Boolean checked;

    public void setRole_id(Integer role_id){
        this.role_id=role_id;
    }
    public Integer getRole_id(){
        return this.role_id;
    }
    public void setRole_name(String role_name){
        this.role_name=role_name;
    }
    public String getRole_name(){
        return this.role_name;
    }
    public void setParent_id(String parent_id){
        this.parent_id=parent_id;
    }
    public String getParent_id(){
        return this.parent_id;
    }
    public void setRole_descr(String role_descr){
        this.role_descr=role_descr;
    }
    public String getRole_descr(){
        return this.role_descr;
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
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}