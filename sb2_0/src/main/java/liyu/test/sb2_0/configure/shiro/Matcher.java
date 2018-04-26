package liyu.test.sb2_0.configure.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Matcher extends SimpleCredentialsMatcher {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		UsernamePasswordToken utoken = (UsernamePasswordToken) token;
		String inPassword = new String(utoken.getPassword());
	
		String dbPassword = (String) info.getCredentials();

		try {
			return this.equals(inPassword, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	/*private static class CryptUtils {
		private static final String KEY_MD5 = "MD5";
		private static final String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c",
				"d", "e", "f" };

		private String byteToArrayString(byte bByte) {
			int iRet = bByte;
			if (iRet < 0) {
				iRet += 256;
			}
			int iD1 = iRet / 16;
			int iD2 = iRet % 16;
			return strDigits[iD1] + strDigits[iD2];
		}

		private String byteToString(byte[] bByte) {
			StringBuffer sBuffer = new StringBuffer();
			for (int i = 0; i < bByte.length; i++) {
				sBuffer.append(byteToArrayString(bByte[i]));
			}
			return sBuffer.toString();
		}

		public String getMD5Code(String strObj) throws Exception {
			MessageDigest md = MessageDigest.getInstance(KEY_MD5);
			return byteToString(md.digest(strObj.getBytes()));
		}

	}*/
}
