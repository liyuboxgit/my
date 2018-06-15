git
	git rm <filename> 删除文件
	git reset --hard <commitid>回退到某个版本
	git log 查看commit
	git reflog 查看详细轨迹

	git branch 查看当前分支
	git branch <name> 创建分支
	git checkout <name> 切换分支
	git merge <name> 分支name，合并到当前分支上
	git branch -d <name> 删除分支

	---解决冲突呢没有新的命令，知道其原理就能应对

	git clone
	git checkout -b dev origin/dev 必须创建远程origin的dev分支到本地

	...
	继续修改和提交
	...

	git push origin dev 推送到远程
	有可能失败，失败需要git pull
	修改解决冲突后再继续推送


	多人协作的工作模式通常是这样：
	首先，可以试图用git push origin branch-name推送自己的修改；
	如果推送失败，则因为远程分支比你的本地更新，需要先用git pull试图合并；
	如果合并有冲突，则解决冲突，并在本地提交；
	没有冲突或者解决掉冲突后，再用git push origin branch-name推送就能成功！
	如果git pull提示“no tracking information”，则说明本地分支和远程分支的链接关系没有创建，用命令git branch --set-upstream branch-name origin/branch-name。
	这就是多人协作的工作模式，一旦熟悉了，就非常简单。

	安装完window git，设置
	$ git config --global core.autocrlf false
	在eclipse中window>references>general>workspace设置new line delimiter为unix
	项目删除，重新下载， 只此三步，基本上可以解决换行问题了，否则请深究吧
nginx
	1.安装nginx，参考网上资料
	2.启动：sbin/nginx
	  停止：sbin/nginx -s quit
			sbin/nginx -s stop
	  重置：sbin/nginx -s reload
	3.配置：
	1)uncomment user nobody and set user root;
	2)in server block add
		location / {
			root /root/nginx;
		}
	  this means url / link to file system direction /root/nginx;

	3)configure proxy server:
		location / {
			http_proxy:http://localhost:8080;
		}
	4)upstream
		http {
			...
			upstream backend {
				#可以设置访问策略，1随机2最少次数3ip_hash4url_hash
				server localhost:8080;
				server localhost:8081;
				server localhost:8082;
			}

			server {
				...
				location / {
				   proxy_pass http://backend;
				}	
			}
		}
		
	spring boot
	java -jar target/smart.jar --server.port=8080

	firewalld（防火墙）
	查看指定区域所有打开的端口
	firewall-cmd --zone=public --list-ports
	在指定区域打开端口（记得重启防火墙）
	firewall-cmd --zone=public --add-port=80/tcp --permanent

mysql
java：RSA加减密
	package smart;

	import org.apache.commons.codec.binary.Base64;
	import org.apache.commons.io.IOUtils;

	import javax.crypto.Cipher;
	import java.io.ByteArrayOutputStream;
	import java.nio.charset.Charset;
	import java.nio.file.Files;
	import java.nio.file.Path;
	import java.nio.file.Paths;
	import java.security.*;
	import java.security.interfaces.RSAPrivateKey;
	import java.security.interfaces.RSAPublicKey;
	import java.security.spec.InvalidKeySpecException;
	import java.security.spec.PKCS8EncodedKeySpec;
	import java.security.spec.X509EncodedKeySpec;
	import java.util.HashMap;
	import java.util.Iterator;
	import java.util.List;
	import java.util.Map;

	public class RSAUtils {

		public static final String CHARSET = "UTF-8";
		public static final String RSA_ALGORITHM = "RSA";


		public static Map<String, String> createKeys(int keySize){
			//为RSA算法创建一个KeyPairGenerator对象
			KeyPairGenerator kpg;
			try{
				kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
			}catch(NoSuchAlgorithmException e){
				throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
			}

			//初始化KeyPairGenerator对象,密钥长度
			kpg.initialize(keySize);
			//生成密匙对
			KeyPair keyPair = kpg.generateKeyPair();
			//得到公钥
			Key publicKey = keyPair.getPublic();
			String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
			//得到私钥
			Key privateKey = keyPair.getPrivate();
			String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
			Map<String, String> keyPairMap = new HashMap<String, String>();
			keyPairMap.put("publicKey", publicKeyStr);
			keyPairMap.put("privateKey", privateKeyStr);

			return keyPairMap;
		}

		/**
		 * 得到公钥
		 * @param publicKey 密钥字符串（经过base64编码）
		 * @throws Exception
		 */
		public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
			//通过X509编码的Key指令获得公钥对象
			KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
			RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
			return key;
		}

		/**
		 * 得到私钥
		 * @param privateKey 密钥字符串（经过base64编码）
		 * @throws Exception
		 */
		public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
			//通过PKCS#8编码的Key指令获得私钥对象
			KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
			RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
			return key;
		}

		/**
		 * 公钥加密
		 * @param data
		 * @param publicKey
		 * @return
		 */
		public static String publicEncrypt(String data, RSAPublicKey publicKey){
			try{
				Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
				cipher.init(Cipher.ENCRYPT_MODE, publicKey);
				return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
			}catch(Exception e){
				throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
			}
		}

		/**
		 * 私钥解密
		 * @param data
		 * @param privateKey
		 * @return
		 */

		public static String privateDecrypt(String data, RSAPrivateKey privateKey){
			try{
				Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
				cipher.init(Cipher.DECRYPT_MODE, privateKey);
				return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.getModulus().bitLength()), CHARSET);
			}catch(Exception e){
				throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
			}
		}

		/**
		 * 私钥加密
		 * @param data
		 * @param privateKey
		 * @return
		 */

		public static String privateEncrypt(String data, RSAPrivateKey privateKey){
			try{
				Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
				cipher.init(Cipher.ENCRYPT_MODE, privateKey);
				return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
			}catch(Exception e){
				throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
			}
		}

		/**
		 * 公钥解密
		 * @param data
		 * @param publicKey
		 * @return
		 */

		public static String publicDecrypt(String data, RSAPublicKey publicKey){
			try{
				Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
				cipher.init(Cipher.DECRYPT_MODE, publicKey);
				return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength()), CHARSET);
			}catch(Exception e){
				throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
			}
		}

		private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize){
			int maxBlock = 0;
			if(opmode == Cipher.DECRYPT_MODE){
				maxBlock = keySize / 8;
			}else{
				maxBlock = keySize / 8 - 11;
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] buff;
			int i = 0;
			try{
				while(datas.length > offSet){
					if(datas.length-offSet > maxBlock){
						buff = cipher.doFinal(datas, offSet, maxBlock);
					}else{
						buff = cipher.doFinal(datas, offSet, datas.length-offSet);
					}
					out.write(buff, 0, buff.length);
					i++;
					offSet = i * maxBlock;
				}
			}catch(Exception e){
				throw new RuntimeException("加解密阀值为["+maxBlock+"]的数据时发生异常", e);
			}
			byte[] resultDatas = out.toByteArray();
			IOUtils.closeQuietly(out);
			return resultDatas;
		}
		
		public static void main (String[] args) throws Exception {
			Map<String, String> keyMap = RSAUtils.createKeys(1024);
			String  publicKey = keyMap.get("publicKey");
			String  privateKey = keyMap.get("privateKey");
			System.out.println("公钥:" + publicKey);
			System.out.println("私钥:" + privateKey);

			Path path = Paths.get("C:\\root\\bills_helper_log\\log.2018-06-13.log");
			List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));
			StringBuffer sb = new StringBuffer();
			for (String string : lines) {
				sb.append(string);
				sb.append("\n");
			}
			
			String encodedData = RSAUtils.publicEncrypt(sb.toString(), RSAUtils.getPublicKey(publicKey));
			String decodedData = RSAUtils.privateDecrypt(encodedData, RSAUtils.getPrivateKey(privateKey));
			System.out.println(decodedData);
		}
	}



	