package liyu.test.shiro.config;

import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import liyu.test.shiro.model.Permission;
import liyu.test.shiro.model.Role;
import liyu.test.shiro.model.User;
import liyu.test.shiro.service.UserService;

public class UserRealm extends AuthorizingRealm{
    @Autowired
	private UserService userService;

    /**
     * 提供用户信息返回权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
          
        // 根据用户名查询当前用户拥有的角色
        Set<Role> roles = userService.findRoles(username);
        for(Role role:roles) {
        	authorizationInfo.addRole(role.getName());
        	List<Permission> permissions = role.getPermissions();
        	for(Permission p:permissions) {
        		authorizationInfo.addStringPermission(role.getName()+":"+p.getName());
        	}
        }
        
        return authorizationInfo;
    }

    /**
     * 提供账户信息返回认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (user.getLocked()) {
            throw new LockedAccountException();
        }
        
        if(username.equals(user.getUsername())){
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());  
        }else{
            throw new AuthenticationException();  
        }
        
    }
}