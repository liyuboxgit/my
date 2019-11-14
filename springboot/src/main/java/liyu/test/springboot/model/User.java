package liyu.test.springboot.model;

import java.io.Serializable;

//@Entity
//@Table(name = "t_user")
public class User implements Serializable{
	private static final long serialVersionUID = 8768982937738871210L;
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String password = "123456";
	//@ManyToOne
	//@JoinColumn(name = "role_id")
	private Role role;
	

	public User(Integer id, String username) {
		this.id = id;
		this.username = username;
	}
	
	public User() {}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
