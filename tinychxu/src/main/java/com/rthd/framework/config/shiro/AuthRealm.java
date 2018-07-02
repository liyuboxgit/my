package com.rthd.framework.config.shiro;

import java.util.ArrayList;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.rthd.framework.mybatis.EnhanceMapper;

public class AuthRealm extends AuthorizingRealm{
	@Autowired private EnhanceMapper em;
	@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken utoken=(UsernamePasswordToken) token;
        String username = utoken.getUsername();
        String usersql = "select username as username,password as password,'' as roles from user where username='"+username+"'";
        Object one = em.getSqlSession().selectOne("com.rthd.framework.Admin.sql_select", usersql);
        
        if(one == null) {
        	return null;
        }else {
        	@SuppressWarnings("unchecked")
			Map<String,Object> dbuser = (Map<String, Object>) one;
        	ShiroUser user = new ShiroUser((String)dbuser.get("username"),(String)dbuser.get("password"),(String)dbuser.get("roles"),(Integer)dbuser.get("version"));
        	return new SimpleAuthenticationInfo(user, user.getPassword(),this.getClass().getName());
        }
    }
	
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		@SuppressWarnings("unused")
		ShiroUser user=(ShiroUser) principal.fromRealm(this.getClass().getName()).iterator().next();
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addRole(null);
        info.addStringPermissions(new ArrayList<>());
        return info;
    }

}