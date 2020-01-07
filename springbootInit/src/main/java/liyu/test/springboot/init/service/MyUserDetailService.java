package liyu.test.springboot.init.service;

import liyu.test.springboot.init.entity.Resources;
import liyu.test.springboot.init.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MyUserDetailService implements UserDetailsService {
    private static Logger log = LoggerFactory.getLogger(MyUserDetailService.class);
    @Resource
    private UserService userService;

    @Resource
    private ResourcesService resourcesService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userService.findUserByName(username);
        if (user == null)
            throw new UsernameNotFoundException(username + " not exist!");
        log.info("load user " + user.getUsername());
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();

        List<Resources> list = resourcesService.loadMenu(username);
        for (Resources r : list) {
            authSet.add(new SimpleGrantedAuthority("ROLE_" + r.getResKey()));
        }
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.getEnable() == 1 ? true : false,
                true,
                true,
                true,
            authSet);
    }
}

