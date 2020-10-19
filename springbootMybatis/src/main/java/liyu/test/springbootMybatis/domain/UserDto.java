package liyu.test.springbootMybatis.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_user")
public class UserDto implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    /**用户ID*/
    private Integer user_id;
    /**登录名*/
    private String login_name;
    /**姓名*/
    private String user_name;
    /**性别*/
    private String user_sex;
    /**密码*/
    private String user_password;
    /**年龄*/
    private Integer user_age;
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

    public void setUser_id(Integer user_id){
        this.user_id=user_id;
    }
    public Integer getUser_id(){
        return this.user_id;
    }
    public void setLogin_name(String login_name){
        this.login_name=login_name;
    }
    public String getLogin_name(){
        return this.login_name;
    }
    public void setUser_name(String user_name){
        this.user_name=user_name;
    }
    public String getUser_name(){
        return this.user_name;
    }
    public void setUser_sex(String user_sex){
        this.user_sex=user_sex;
    }
    public String getUser_sex(){
        return this.user_sex;
    }
    public void setUser_password(String user_password){
        this.user_password=user_password;
    }
    public String getUser_password(){
        return this.user_password;
    }
    public void setUser_age(Integer user_age){
        this.user_age=user_age;
    }
    public Integer getUser_age(){
        return this.user_age;
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