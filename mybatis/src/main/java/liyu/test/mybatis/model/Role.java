package liyu.test.mybatis.model;
/**
*(t_role)
**/
public class Role implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    /***/
    private Integer id;
    /***/
    private String rolename;

    public void setId(Integer id){
        this.id=id;
    }
    public Integer getId(){
        return this.id;
    }
    public void setRolename(String rolename){
        this.rolename=rolename;
    }
    public String getRolename(){
        return this.rolename;
    }
}