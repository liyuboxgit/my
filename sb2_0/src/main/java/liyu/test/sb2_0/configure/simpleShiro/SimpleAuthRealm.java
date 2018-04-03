package liyu.test.sb2_0.configure.simpleShiro;

import java.util.ArrayList;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class SimpleAuthRealm extends AuthorizingRealm{
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken utoken=(UsernamePasswordToken) token;
        String username = utoken.getUsername();
        SimpleShiroUser user = new SimpleShiroUser(username,"123456");
        return new SimpleAuthenticationInfo(user, user.getPassword(),this.getClass().getName());
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        @SuppressWarnings("unused")
		SimpleShiroUser user=(SimpleShiroUser) principal.fromRealm(this.getClass().getName()).iterator().next();
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addRole(null);
        info.addStringPermissions(new ArrayList<>());
        return info;
    }

}