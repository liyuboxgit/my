package org.apache.dubbo.demo;

public interface UserService {
	public User getUser(Long id);
	public User registUser(User user);
}
