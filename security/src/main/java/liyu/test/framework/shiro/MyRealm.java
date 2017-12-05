package liyu.test.framework.shiro;

import java.util.List;

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
import org.springframework.util.StringUtils;

import liyu.test.security.entity.Module;
import liyu.test.security.entity.Permission;
import liyu.test.security.entity.Position;
import liyu.test.security.entity.User;
import liyu.test.security.service.ModuleService;
import liyu.test.security.service.PermissionService;
import liyu.test.security.service.PositionServie;
import liyu.test.security.service.UserService;

/**
 * 
 * @author Administrator
 *
 */
public class MyRealm extends AuthorizingRealm{
	@Autowired
	private UserService userService;
	@Autowired
	private PositionServie positionServie;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private PermissionService permissionService;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String currentUsername = (String)super.getAvailablePrincipal(principals);
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		
		User user = userService.getByLoginName(currentUsername);
		if(user!=null && user.getPositionId()!=null){
			Position position = positionServie.get(user.getPositionId());
			List<Permission> ret =  permissionService.query(position.getId());
			for(Permission permis : ret){
				Module module = moduleService.get(permis.getModuleId());
				simpleAuthorInfo.addRole(module.getUrl());
				String bts = permis.getButtons();
				String[] att = this.getButton(bts);
				for(String s:att){
					simpleAuthorInfo.addStringPermission(module.getUrl()+":"+s);
				}
			}
			
			return simpleAuthorInfo;
		}

		return null;
	}

	private String[] getButton(String bts) {
		if(!StringUtils.isEmpty(bts)){
			return bts.split(",");
		}
		return new String[]{};
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken uptoken = (UsernamePasswordToken)token;
		User user = userService.getByLoginName(uptoken.getUsername());
		if(user!=null){			
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(uptoken.getUsername(), user.getPassword(), this.getName());
			return authcInfo;
		}

		return null;
	}

}
