package liyu.test.springboot.init.service;

import liyu.test.springboot.init.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User findUserByName(String username) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode("000000");
        User user = new User(username,password);
        return user;
    }
}
