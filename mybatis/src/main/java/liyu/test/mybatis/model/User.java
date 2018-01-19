package liyu.test.mybatis.model;
/**
*(t_user)
**/
public class User implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    /***/
    private Integer id;
    /***/
    private String password;
    /***/
    private String username;
    /***/
    private Integer roleId;
    /***/
    private java.math.BigDecimal age;

    public void setId(Integer id){
        this.id=id;
    }
    public Integer getId(){
        return this.id;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public String getPassword(){
        return this.password;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getUsername(){
        return this.username;
    }
    public void setRoleId(Integer roleId){
        this.roleId=roleId;
    }
    public Integer getRoleId(){
        return this.roleId;
    }
    public void setAge(java.math.BigDecimal age){
        this.age=age;
    }
    public java.math.BigDecimal getAge(){
        return this.age;
    }
}