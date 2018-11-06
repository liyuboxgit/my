package test.shiro.matcher;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class SimpleMatcher extends SimpleCredentialsMatcher{
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		UsernamePasswordToken utoken = (UsernamePasswordToken) token;
		String inPassword = new String(utoken.getPassword());
		String dbPassword = (String) info.getCredentials();
		return this.equals(inPassword, dbPassword);
	}
}
