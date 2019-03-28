git
	git clone 下载master分支
	git clone -b <branch> 下载特定分支
	git rm <filename> 删除文件
	git reset --hard <commitid>回退到某个版本
	git log 查看commit
	git reflog 查看详细轨迹

	git branch 查看当前分支
	git branch <name> 创建分支
	git checkout <name> 切换分支
	git merge <name> 分支name，合并到当前分支上
	git branch -d <name> 删除分支
	git push origin :<name> 删除远程分支
	git push origin --delete <name> 删除远程分支
	
	git push origin branch_website:branch_website 本地分支发送到远程
	git fetch origin branch_website 拉取远程分支
	git checkout -b branch_website origin/branch_website 创建并切换到本地分支，和远程分支相对应
	
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

gradle
	
	
	
nginx
	1.安装nginx
		cd /usr/local/src
		wget ....tar.gz
		tar -xf ....tar.gz
		cd nginx-x-x-x
		./configure --prefix=/usr/local --with-http_stub_status_module --with-http_ssl_module --with-pcre
			Configuration summary
			  + using system PCRE library
			  + using system OpenSSL library
			  + using system zlib library

			  nginx path prefix: "/usr/local/nginx"
			  nginx binary file: "/usr/local/nginx/sbin/nginx"
			  nginx modules path: "/usr/local/nginx/modules"
			  nginx configuration prefix: "/usr/local/nginx/conf"
			  nginx configuration file: "/usr/local/nginx/conf/nginx.conf"
			  nginx pid file: "/usr/local/nginx/logs/nginx.pid"
			  nginx error log file: "/usr/local/nginx/logs/error.log"
			  nginx http access log file: "/usr/local/nginx/logs/access.log"
			  nginx http client request body temporary files: "client_body_temp"
			  nginx http proxy temporary files: "proxy_temp"
			  nginx http fastcgi temporary files: "fastcgi_temp"
			  nginx http uwsgi temporary files: "uwsgi_temp"
			  nginx http scgi temporary files: "scgi_temp"
		make & make install
		openssl version -a //查询openssl之版本，-a表示全部信息，关键看编译时间
		cd /usr/local/nginx/conf
		openssl genrsa -des3 -out server.key 1024
		openssl req -new -key server.key -out server.csr
		cp server.key server.key.org
		openssl rsa -in server.key.org -out server.key
		openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt
		vi nginx.conf
		########################start
		user  root;
		worker_processes  2;

		events {
			worker_connections  1024;
		}


		http {
			include       mime.types;
			default_type  application/octet-stream;
			sendfile        on;
			keepalive_timeout  65;
			server {
				listen       443;
				server_name  localhost;
			ssl  on;
			ssl_certificate /usr/local/nginx/conf/server.crt;
			ssl_certificate_key /usr/local/nginx/conf/server.key;
				
			location / {
					root   html;
					index  index.html index.htm;
				}


				error_page   500 502 503 504  /50x.html;
				location = /50x.html {
					root   html;
				}
			}
		}
		########################end
	2.启动：sbin/nginx -c /path/nginx.conf
	  停止：sbin/nginx -s quit
			sbin/nginx -s stop
	  重置：sbin/nginx -s reload -c /path/nginx.conf
	  检查配置：sbin/nginx -t
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
	4.nginx rewrite
	rewrite ^(.*)/index-([0-9]+).html$ $1/index.html?offset=$2 redirect;
	
	spring boot
	java -jar target/smart.jar --server.port=8080

	firewalld（防火墙）
	#查看防火墙状态
	firewall-cmd --state
	#停止防火墙
	systemctl stop firewalld.service
	#禁止防火墙开机启动
	systemctl disable firewalld.service
	#查看指定区域所有打开的端口
	firewall-cmd --zone=public --list-ports
	#在指定区域打开端口（记得重启防火墙）
	firewall-cmd --zone=public --add-port=80/tcp --permanent
mysql：
	下载：mysql-5.6.40-linux-glibc2.12-x86_64.tar.gz至/usr/local
	rpm -qa|grep mariadb 	//查看是否安装maradb
	rpm -e --nodeps 文件名 	//如安装，则卸载
	rm /etc/my.cnf 			//删除遗留配置文件
	groupadd mysql			//create group
	useradd -g mysql mysql	//create user 
	yum -y install autoconf	//
	tar -xvf mysql-5.6.34-linux-glibc2.5-x86_64.tar
	mv mysql-5.6.40-linux-glibc2.12-x86_64 mysql
	vi /etc/my.cnf			//创建mysql配置文件在目录/etc下
		[mysql]
		default-character-set=utf8 
		socket=/var/lib/mysql/mysql.sock

		[mysqld]
		skip-name-resolve
		port = 3306 
		socket=/var/lib/mysql/mysql.sock
		basedir=/usr/local/mysql
		datadir=/usr/local/mysql/data
		max_connections=200
		character-set-server=utf8
		default-storage-engine=INNODB
		lower_case_table_name=1
		max_allowed_packet=16M
	cd /usr/local/mysql
	chown -R mysql:mysql ./	//修改当前目录拥有着为mysql用户，下面的命令是安装数据库
	./scripts/mysql_install_db --user=mysql --basedir=/usr/local/mysql/ --datadir=/usr/local/mysql/data/
	chown -R mysql:mysql data
	
	chmod 777 /etc/my.cnf 	//配置mysql，开放my权限，下面的命令配置开机启动
	cp ./support-files/mysql.server /etc/rc.d/init.d/mysqld
	chmod +x /etc/rc.d/init.d/mysqld
	chkconfig --add mysqld
	chkconfig --list mysqld	//检查是否生效 在2、3、4、5运行级别随系统启动而自动启动，以后可以使用service命令控制mysql的启动和停止
	service mysqld start	//启动mysql，如启动报错：(注意看启动日志)
								1.mkdir /var/lib/mysql 
								2.打开/etc/selinux/config，把SELINUX=enforcing改为SELINUX=disabled后存盘退出重启机器
								3.chown -R mysql:mysql /var/lib/mysql/
	
	/usr/local/mysql/bin/mysql -uroot -p(没用配置环境变量)
	mysql>use mysql;		//设置mysql，root用户的密码
	mysql>update user set password=password('root') where user='root' and host='localhost';
	mysql>flush privileges;
	
	create database liyu;	//创建数据库
	CREATE USER 'liyu'@'%' IDENTIFIED BY 'liyu';(创建用户，%代表可以在任何客户端登录)
	GRANT ALL PRIVILEGES ON liyu.* TO 'liyu'@'%';(授权)
	
	REVOKE ALL PRIVILEGES ON liyu.* FROM 'liyu'@'%';(撤销权限)
	DROP USER 'liyu'@'%';
	drop database liyu;
	
	mysqldump工具基本用法
	1. 备份所有数据库: mysqldump -u root -p --all-databases > all_database_sql
	2. 备份mysql数据库：mysqldump -u root -p --databases mysql > mysql_database_sql
	3. 备份指定的多个数据库：mysqldump -u root -p --databases db1 db2 db3 > bak.sql
	4. 备份mysql数据库下的user表：mysqldump -u root -p mysql user > user_table
	
	#mysql binlog
	编辑mysql配置文件my.cnf,在[mysqld]下加log-bin=mysql-bin  确认是打开状态(值 mysql-bin 是日志的基本名或前缀名)；
	重启mysql服务
	show variables like 'log_bin';
	show master logs;
	show master status;
	flush logs;
	reset master;--清空所有binlog日志
	mysqlbinlog -d ceshi mysql-bin.000003 -r my.sql --解析binlog为sql文件
	mysqlbinlog mysql-bin.000003 --start-position=100  --stop-position=200 -r my.sql --解析binlog的区间为sql文件

	修改mysql编码使微信表情可以入库 mysql支持utf8mb4的版本是5.5.3+
	
	改配置文件/etc/my.cnf
	[client]
	default-character-set = utf8mb4

	[mysql]
	default-character-set = utf8mb4

	[mysqld]
	character-set-client-handshake = FALSE
	character-set-server = utf8mb4
	collation-server = utf8mb4_unicode_ci
	init_connect='SET NAMES utf8mb4'
	重启，确保mysql connection版本高于5.1.13

	SHOW VARIABLES WHERE Variable_name LIKE ‘version%’;
	show variables like 'character%' 
	show variables like 'collation%' 

	character_set_client    utf8mb4
	character_set_connection    utf8mb4
	character_set_database    utf8mb4
	character_set_filesystem    binary
	character_set_results    utf8mb4
	character_set_server    utf8mb4
	character_set_system    utf8
	character_sets_dir    /usr/share/mysql/charsets/
	collation_connection    utf8mb4_general_ci
	collation_database    utf8mb4_unicode_ci
	collation_server    utf8mb4_unicode_ci

	ALTER DATABASE t_yown_userdb CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
	ALTER TABLE t_yown_user CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
	alter table t_yown_user modify `NICKNAME` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '昵称';
	重启mysql

	主从复制
	配置Master主服务器：
	mysql>create user repl; //创建新用户
	mysql>GRANT REPLICATION SLAVE ON *.* TO 'repl'@'192.168.126.%' IDENTIFIED BY 'mysql';
	在my.cnf中[mysqld]下面增加下面几行代码：
	server-id=1   
	log-bin=master-bin
	log-bin-index=master-bin.index
	mysql> SHOW MASTER STATUS;
	+-------------------+----------+--------------+------------------+
	| File | Position | Binlog_Do_DB | Binlog_Ignore_DB |
	+-------------------+----------+--------------+------------------+
	| master-bin.000001 | 1285 | | |
	+-------------------+----------+--------------+------------------+
	重启mysql主服务器
	配置slave从服务器
	在my.cnf中[mysqld]下面增加下面几行代码：
	server-id=2
	relay-log-index=slave-relay-bin.index
	relay-log=slave-relay-bin 
	重启MySQL服务
	登录从mysql，连接Master：
	change master to master_host='192.168.126.128',
	master_port=3306,
	master_user='repl',
	master_password='mysql', 
	master_log_file='master-bin.000001',
	master_log_pos=0;
	启动Slave
	start slave;
	
	配置mysql-proxy
	tar -xf mysql-proxy-0.8.4-linux-glibc2.3-x86-64bit.tar.gz 
	mv mysql-proxy-0.8.4-linux-glibc2.3-x86-64bit/ mysql-proxy-0.8.4 
	cd mysql-proxy-0.8.4/
	mkdir lua
	mkdir logs
	cp share/doc/mysql-proxy/rw-splitting.lua ./lua
	cp share/doc/mysql-proxy/admin-sql.lua ./lua
	vi /etc/mysql-proxy.cnf
	[mysql-proxy]
	user=root
	admin-username=proxy
	admin-password=123456
	proxy-address=192.168.126.128:4040
	proxy-read-only-backend-addresses=192.168.126.129
	proxy-backend-addresses=192.168.126.128
	proxy-lua-script=/usr/local/mysql-proxy-0.8.4/lua/rw-splitting.lua
	admin-lua-script=/usr/local/mysql-proxy-0.8.4/lua/admin-sql.lua
	log-file=/usr/local/mysql-proxy-0.8.4/logs/mysql-proxy.log
	log-level=info
	daemon=true
	keepalive=true
	保存mysql-proxy.cnf
	chmod 660 /etc/mysql-porxy.cnf
	vim /usr/local/mysql-proxy-0.8.4/lua/rw-splitting.lua
	if not proxy.global.config.rwsplit then
        proxy.global.config.rwsplit = {
        min_idle_connections = 1,
        max_idle_connections = 1,
        is_debug = false
	}
	end
	保存rw-splitting.lua
	启动
	./bin/mysql-proxy --defaults-file=/etc/mysql-proxy.cnf
	连接
	/usr/local/mysql/bin/mysql -uproxy -P4040 -p
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
	java bigdecimal divide use new BigDecimal(2).divide(new BigDecimal(3),2,RoundingMode.HALF_UP);
	
	java spring restTemplate：
	public static RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }
	//接受前端request payload，ajax中：
	//	contentType: 'application/json',
	//	data: JSON.stringify({
	//		'fgk': 'e49cbdd3e13948dea1c0aa1790e652cf'
	//	}), 
	StringBuffer stb = new StringBuffer();
	ServletInputStream inputStream = WebUtil.getServletRequest().getInputStream(); 
	List<String> lines = IOUtils.readLines(inputStream, Charset.forName("UTF-8"));
	if(lines!=null) {			
		for(String str: lines) {
			stb.append(str);
		}
	}
	String stream = stb.toString();
	
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
	HttpEntity<String> strEntity = new HttpEntity<String>(stb.toString(),headers);
	ResultData resultData = restTemplate().postForObject(req,strEntity,ResultData.class);
	
	//如果前端非request payload传递，param是hashmap<string,object>
	ResultData resultData = restTemplate().postForObject(req, param, ResultData.class);
	
	//springboot编程是事务控制：方式一
	@Autowired private TransactionTemplate transactionTemplate;
	public JsonRet m() {
		Exception ret = transactionTemplate.execute(new TransactionCallback<Exception>() {
		    public Exception doInTransaction(TransactionStatus status) {
		    	Exception result = null;
		        try {
		        	
		        } catch (Exception ex) {
		            status.setRollbackOnly();
		            result = ex;
		        }
		        return result;
		    }
		});
		
		return WebUtil.success();
	}
	//方式二
	<bean id="transactionManager"
		class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource" />
	</bean>
	private PlatformTransactionManager ptm;

	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	TransactionStatus status = ptm.getTransaction(def);
	try{
		
			
	} catch (Exception e) {
		ptm.rollback(status);
		e.printStackTrace();
	}
	ptm.commit(status);
	
	阿里FASTJSON:
	@JSONField(serialize=true)  
	private Integer version;
	JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	JSON.toJSONString(demo,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);

	阿里云maven库
 	<mirror>
      		<id>alimaven</id>
      		<name>aliyun maven</name>
      		<url>http://maven.aliyun.com/nexus/content/groups/public/</url>
      		<mirrorOf>central</mirrorOf>        
    	</mirror>
	spring-web的架包下面有文件javax.servlet.ServletContainerInitallizer(SCI)文件，文件中有spring-web的实现
	public class SpringServletContainerInitializer implements ServletContainerInitializer
	
	spring jdbcTemplate
	insert GeneratedKeyHolder
	find BeanPropertyRowMapper<T>(Class<T> type)
	
	<parent>
		<groupId>org.apache.httpcomponents</groupId>
		<artifactId>httpcomponents-client</artifactId>
		<version>4.5.3</version>
	</parent>
	<artifactId>httpclient</artifactId>
	import java.io.BufferedOutputStream;
	import java.io.BufferedReader;
	import java.io.DataOutputStream;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.io.OutputStream;
	import java.io.PrintStream;
	import java.net.ConnectException;
	import java.net.HttpURLConnection;
	import java.net.InetAddress;
	import java.net.URL;
	import java.net.UnknownHostException;
	import java.security.cert.CertificateException;
	import java.security.cert.X509Certificate;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.Map;

	import javax.net.ssl.HttpsURLConnection;
	import javax.net.ssl.SSLContext;
	import javax.net.ssl.SSLSocketFactory;
	import javax.net.ssl.TrustManager;
	import javax.net.ssl.X509TrustManager;
	import javax.servlet.http.HttpServletRequest;

	import org.apache.http.Consts;
	import org.apache.http.HttpResponse;
	import org.apache.http.HttpStatus;
	import org.apache.http.NameValuePair;
	import org.apache.http.client.config.RequestConfig;
	import org.apache.http.client.entity.UrlEncodedFormEntity;
	import org.apache.http.client.methods.CloseableHttpResponse;
	import org.apache.http.client.methods.HttpGet;
	import org.apache.http.client.methods.HttpPost;
	import org.apache.http.impl.client.CloseableHttpClient;
	import org.apache.http.impl.client.HttpClients;
	import org.apache.http.message.BasicNameValuePair;
	import org.apache.http.util.EntityUtils;
	import org.apache.log4j.Logger;

	import com.alibaba.fastjson.JSON;
	import com.alibaba.fastjson.JSONObject;
	import com.rthd.framework.util.StringUtil;
	import com.rthd.framework.util.WebUtil;

	public class HttpUtil {
		private static Logger log= Logger.getLogger(HttpUtil.class);
		private final static int CONNECT_TIMEOUT = 5000; 
		private final static String DEFAULT_ENCODING = "UTF-8";

		private static TrustManager myX509TrustManager = new X509TrustManager(){
			@Override
			public X509Certificate[] getAcceptedIssuers() {	
				return null;		
			}
			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}	
		};
		/**
		 * 
		 * 此方法描述的是：发送远程请求post
		 * @author: zhupengfei@rthdtax.com
		 * @version: 2018年7月17日 下午4:31:14
		 */
		public static String postData(String urlStr, String param){
			DataOutputStream dos = null;
			InputStream input = null;
			try {
				URL url = new URL(urlStr);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setConnectTimeout(CONNECT_TIMEOUT); 
				conn.setReadTimeout(CONNECT_TIMEOUT);
				conn.setRequestMethod("POST");
				conn.setUseCaches(false);
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("Charset", DEFAULT_ENCODING);
				conn.setRequestProperty("contentType", DEFAULT_ENCODING);
				dos = new DataOutputStream(conn.getOutputStream());
				if(StringUtil.isNotEmpty(param)) {
					dos.writeBytes(param);
					dos.flush();
					dos.close();
				}
				input = conn.getInputStream();
				int resLen =0;
				byte[] res = new byte[1024];
				StringBuffer sb = new StringBuffer();
				while((resLen=input.read(res))!=-1){
					sb.append(new String(res, 0, resLen));
				}
				return sb.toString();
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				try { 
					if(input != null) input.close();  
					if(dos != null) dos.close();
				}catch (IOException e) { 
					e.printStackTrace();
				}
			}
			return null;
		}
	   
		public static String postJsonData(String urlStr, String param){
			DataOutputStream dos = null;
			InputStream input = null;
			try {
				URL url = new URL(urlStr);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setConnectTimeout(CONNECT_TIMEOUT); 
				conn.setReadTimeout(CONNECT_TIMEOUT);
				conn.setRequestMethod("POST");
				conn.setUseCaches(false);
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("Charset", DEFAULT_ENCODING);
				dos = new DataOutputStream(conn.getOutputStream());
				if(StringUtil.isNotEmpty(param)) {
					dos.write(param.getBytes("UTF-8"));
					dos.flush();
					dos.close();
				}
				input = conn.getInputStream();
				int resLen =0;
				byte[] res = new byte[1024];
				StringBuffer sb = new StringBuffer();
				while((resLen=input.read(res))!=-1){
					sb.append(new String(res, 0, resLen));
				}
				return sb.toString();
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				try { 
					if(input != null) input.close();  
					if(dos != null) dos.close();
				}catch (IOException e) { 
					e.printStackTrace();
				}
			}
			return null;
		}
		
		public static JSONObject httpGet(String url) {
			JSONObject jsonResult = null;
			try {
				CloseableHttpClient client = HttpClients.createDefault();
				HttpGet request = new HttpGet(url);
				HttpResponse response = client.execute(request);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String strResult = EntityUtils.toString(response.getEntity(),DEFAULT_ENCODING);
					jsonResult = JSON.parseObject(strResult);
					request.releaseConnection();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return jsonResult;
		}
		/**
		 * 
		 * 此方法描述的是：后台调用苹果接口
		 * @author: zhupengfei@rthdtax.com
		 * @version: 2018年10月16日 下午12:55:54
		 */
		public static JSONObject verify1(String url, String receipt) {
			try {
			HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setAllowUserInteraction(false); 
			PrintStream ps = new PrintStream(connection.getOutputStream());
			ps.print("{\"receipt-data\": \"" + receipt + "\"}");
			ps.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
			sb.append(str);
			}
			br.close();
			String resultStr = sb.toString();
			JSONObject result = JSONObject.parseObject(resultStr);
			if (result != null && result.getInteger("status") == 21007) { //递归，以防漏单
			return verify1(url, receipt);
			}
			return result;
			} catch (Exception e) {
			e.printStackTrace();
			}
			return null;
			} 
	  
		public static String doPost(String url, Map<String, String> params) {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			try {
				HttpPost httppost = new HttpPost(url);

				RequestConfig config = RequestConfig.custom()
						.setConnectTimeout(CONNECT_TIMEOUT)
						.setConnectionRequestTimeout(CONNECT_TIMEOUT)
						.setSocketTimeout(CONNECT_TIMEOUT).build();

				httppost.setConfig(config);

				List<NameValuePair> formparams = new ArrayList<NameValuePair>();
				if (params != null) {
					for (Map.Entry<String, String> entry : params.entrySet()) {
						formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue() + ""));
					}
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
				httppost.setEntity(entity);

				log.debug("Executing request: " + httppost.getRequestLine());
				CloseableHttpResponse response = httpclient.execute(httppost);
				try {
					log.debug(response.getStatusLine().toString());
					return EntityUtils.toString(response.getEntity());
				} finally {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					httpclient.close();
				} catch (IOException e) {
					log.error("error when closing httpClient.", e);
				}
			}
			return null;
		}
		
		public static String sendHttpsCoon(String url, String code){
			if(StringUtil.isEmpty(url)||StringUtil.isEmpty(code)) return "";
			BufferedOutputStream buffOutStr = null;
			BufferedReader reader = null;
			HttpsURLConnection conn = null;
			try {
				SSLContext ssl = SSLContext.getInstance("SSL");//设置SSLContext
				ssl.init(null, new TrustManager[]{myX509TrustManager}, null);
				
				conn = (HttpsURLConnection) new URL(url).openConnection();// 打开连接
				conn.setSSLSocketFactory(ssl.getSocketFactory());// 设置套接工厂
				conn.setRequestMethod("POST");// 加入数据
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-type", "application/json");
				conn.setRequestProperty("Proxy-Connection", "Keep-Alive");	
				JSONObject obj = new JSONObject();
				obj.put("receipt-data", code);
				
				buffOutStr = new BufferedOutputStream(conn.getOutputStream());
				buffOutStr.write(obj.toString().getBytes());
				buffOutStr.flush();
				buffOutStr.close();
				
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line = null;
				StringBuffer sb = new StringBuffer();
				while ((line = reader.readLine()) != null){
					sb.append(line);
				}
				String appStoreRet = sb.toString();
				conn.getInputStream().close();
				conn.getOutputStream().close();
				return appStoreRet;
			}catch(Exception e) {
				e.printStackTrace();
				return "";
			}finally {
				try {
					if(buffOutStr!=null)buffOutStr.close();
					if(reader!=null)reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		/**
		 * 发送https请求
		 * @param requestUrl 请求地址
		 * @param requestMethod 请求方式（GET、POST）
		 * @param outputStr 提交的数据
		 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
		 */
		public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
			JSONObject jsonObject = null;
			try {
				// 创建SSLContext对象，并使用我们指定的信任管理器初始化
				TrustManager[] tm = { myX509TrustManager };
				SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
				sslContext.init(null, tm, new java.security.SecureRandom());
				// 从上述SSLContext对象中得到SSLSocketFactory对象
				SSLSocketFactory ssf = sslContext.getSocketFactory();
				URL url = new URL(requestUrl);
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				conn.setSSLSocketFactory(ssf);
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setUseCaches(false);
				// 设置请求方式（GET/POST）
				conn.setRequestMethod(requestMethod);
				// 当outputStr不为null时向输出流写数据
				if (null != outputStr) {
					OutputStream outputStream = conn.getOutputStream();
					// 注意编码格式
					outputStream.write(outputStr.getBytes("UTF-8"));
					outputStream.close();
				}
				// 从输入流读取返回内容
				InputStream inputStream = conn.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String str = null;
				StringBuffer buffer = new StringBuffer();
				while ((str = bufferedReader.readLine()) != null) {
					buffer.append(str);
				}
				// 释放资源
				bufferedReader.close();
				inputStreamReader.close();
				inputStream.close();
				inputStream = null;
				conn.disconnect();
				jsonObject = JSONObject.parseObject(buffer.toString());
			} catch (ConnectException ce) {
				log.error("连接超时：{}", ce);
			} catch (Exception e) {
				log.error("https请求异常：{}", e);
			}
			return jsonObject;
		}
	}
oracle分区;
	--建立测试表分区
	CREATE TABLE FPFX_T_QYGX_TEST (
		NSRSBH VARCHAR2(100 BYTE), 
		RELATION_SBH VARCHAR2(100 BYTE)
	)
	PARTITION BY HASH(NSRSBH,RELATION_SBH)(
	PARTITION p1 TABLESPACE DB_ZGXT,
	PARTITION p2 TABLESPACE DB_ZGXT_INDEX
	) NOLOGGING;

	--查询分区
	select t.partition_name,t.num_rows from all_tab_partitions t where table_name='FPFX_T_QYGX_TEST';

	insert /*+ parallel(6) */ /*+ append nologging */
		into FPFX_T_QYGX_TEST select * from FPFX_T_QYGX;

	select count(1) from FPFX_T_QYGX_TEST;
	select count(1) from FPFX_T_QYGX_TEST PARTITION(P1);
	select count(1) from FPFX_T_QYGX_TEST PARTITION(P2);
	--创建本地分区索引,和分区数据不在同一个表空间，意为互相交叉。
	CREATE /*+ parallel(6) */ INDEX FPFX_T_QYGX_TEST_index_NSRSBH ON FPFX_T_QYGX_TEST (NSRSBH) 
	LOCAL (
		PARTITION p1 TABLESPACE DB_ZGXT_INDEX,
		PARTITION p2 TABLESPACE DB_ZGXT
	);
	--查询索引 
	select * from user_extents where segment_name=upper('FPFX_T_QYGX_TEST_index_NSRSBH');
	select * from USER_SEGMENTS where segment_name=upper('FPFX_T_QYGX_TEST_index_NSRSBH'); 
oracle install contos_7_64脚本安装oracle11
	1. 修改主机名
	#sed -i "s/HOSTNAME=localhost.localdomain/HOSTNAME=oracledb.01/" /etc/sysconfig/network

	2.添加主机名与IP对应记录
	# vim /etc/hosts 
	192.168.0.71 oracledb.01

	3.关闭Selinux
	#sed -i "s/SELINUX=enforcing/SELINUX=disabled/" /etc/selinux/config 
	
	4.安装依赖包
	yum -y install binutils compat-libcap1 compat-libstdc++-33 compat-libstdc++-33*i686 compat-libstdc++-33*.devel compat-libstdc++-33 compat-libstdc++-33*.devel gcc gcc-c++ glibc glibc*.i686 glibc-devel glibc-devel*.i686 ksh libaio libaio*.i686 libaio-devel libaio-devel*.devel libgcc libgcc*.i686 libstdc++ libstdc++*.i686 libstdc++-devel libstdc++-devel*.devel libXi libXi*.i686 libXtst libXtst*.i686 make sysstat unixODBC unixODBC*.i686 unixODBC-devel unixODBC-devel*.i686
	
	5.检测是否31个包都有安装
	rpm -q binutils compat-libcap1 compat-libstdc++-33 gcc gcc-c++ glibc glibc-devel ksh libaio libaio-devel libgcc libstdc++ libstdc++-devel libXi libXtst  make sysstat  unixODBC unixODBC-devel
	
	6.创建oracle组和用户
	/usr/sbin/groupadd oinstall
	/usr/sbin/groupadd dba

	/usr/sbin/useradd -g oinstall -G dba oracle
	passwd oracle
	id oracle #验证用户是否创建正确
	
	7.配置内核参数：
	[root@docker ~]# vim /etc/sysctl.conf 
	# System default settings live in /usr/lib/sysctl.d/00-system.conf.
	# To override those settings, enter new settings here, or in an /etc/sysctl.d/<name>.conf file
	#
	# For more information, see sysctl.conf(5) and sysctl.d(5).
	fs.aio-max-nr = 1048576
	fs.file-max = 6815744
	kernel.shmall = 2097152
	kernel.shmmax = 536870912  
	kernel.shmmni = 4096
	kernel.sem = 250 32000 100 128
	net.ipv4.ip_local_port_range = 9000 65500
	net.core.rmem_default = 262144
	net.core.rmem_max = 4194304
	net.core.wmem_default = 262144
	net.core.wmem_max = 1048576
	修改后使之生效
	/sbin/sysctl -p
	
	8.修改用户限制及其它
	vim  /etc/security/limits.conf
	oracle soft nproc 2047
	oracle hard nproc 16384
	oracle soft nofile 1024
	oracle hard nofile 65536
	oracle soft stack 10240
	oracle hard stack 10240
	在/etc/pam.d/login 文件中：
	session required /lib/security/pam_limits.so
	session required pam_limits.so
	在/etc/profile 文件：
	if [ $USER = "oracle" ]; then
	   if [ $SHELL = "/bin/ksh" ]; then
		   ulimit -p 16384
		   ulimit -n 65536
		else
		   ulimit -u 16384 -n 65536
	   fi
	fi
	修改使之生效：source /etc/profile
	
	9.创建安装目录：
	mkdir -p /u01/app/
	chown -R oracle:oinstall /u01/app/
	chmod -R 775 /u01/app/
	
	10.解压oracle软件：
	[root@docker src]# unzip linux.x64_11gR2_database_1of2.zip
	[root@docker src]# unzip linux.x64_11gR2_database_2of2.zip
	
	11.复制响应文件模板
	[oracle@docker ~]$ mkdir etc
	[oracle@docker ~]$ cp  /usr/local/src/database/response/* /home/oracle/etc/
	[oracle@docker ~]$ ls etc
	dbca.rsp  db_install.rsp  netca.rsp
	
	12.设置响应文件权限
	[oracle@docker ~]$ su - root
	[root@docker ~]# chmod 700 /home/oracle/etc/*.rsp
	
	13.su - oracle修改安装Oracle软件的响应文件/home/oracle/etc/db_install.rsp
	ORACLE_HOSTNAME=oracledb.01        // 主机名称（hostname查询）
	UNIX_GROUP_NAME=oinstall     // 安装组
	INVENTORY_LOCATION=/u01/app/oraInventory   //INVENTORY目录（不填就是默认值）
	SELECTED_LANGUAGES=en,zh_CN,zh_TW // 选择语言
	ORACLE_HOME=/u01/app/oracle/product/11.2.0/db_1    //oracle_home
	ORACLE_BASE=/u01/app/oracle     //oracle_base
	oracle.install.option=INSTALL_DB_SWONLY
	oracle.install.db.InstallEdition=EE 　　　　// oracle版本
	oracle.install.db.isCustomInstall=false 　　//自定义安装，否，使用默认组件
	oracle.install.db.DBA_GROUP=dba /　　/ dba用户组
	oracle.install.db.OPER_GROUP=oinstall // oper用户组
	oracle.install.db.config.starterdb.type=GENERAL_PURPOSE //数据库类型
	oracle.install.db.config.starterdb.globalDBName=orcl //globalDBName
	oracle.install.db.config.starterdb.SID=dbsrv2      //SID
	oracle.install.db.config.starterdb.memoryLimit=2048 //自动管理内存的内存(M)
	oracle.install.db.config.starterdb.password.ALL=oracle //设定所有数据库用户使用同一个密码
	SECURITY_UPDATES_VIA_MYORACLESUPPORT=false         //（手动写了false）
	DECLINE_SECURITY_UPDATES=true 　　//设置安全更新（貌似是有bug，这个一定要选true，否则会无限提醒邮件地址有问题，终止安装。PS：不管地址对不对）
	
	14.开始静默安装：
	[oracle@docker database]$ ./runInstaller -silent -responseFile /home/oracle/etc/db_install.rsp
	出现类似如下提示表示安装完成：
	The following configuration scripts need to be executed as the "root" user. 
	#!/bin/sh 
	#Root scripts to run

	/u01/app/oraInventory/orainstRoot.sh
	/u01/app/oracle/product/11.2.0/db_1/root.sh
	To execute the configuration scripts:
	1. Open a terminal window 
	2. Log in as "root" 
	3. Run the scripts 
	4. Return to this window and hit "Enter" key to continue

	Successfully Setup Software.

	使用root用户执行脚本：

	su - root
	/u01/app/oraInventory/orainstRoot.sh
	/u01/app/oracle/product/11.2.0/db_1/root.sh
	
	15.增加或修改oracle的环境变量：
	su  - oracle
	vim ~/.bash_profile
	#for oracle
	export ORACLE_BASE=/u01/app/oracle
	export ORACLE_SID=dbsrv2
	export ROACLE_PID=ora11g
	export LD_LIBRARY_PATH=$ORACLE_HOME/lib:/usr/lib
	export ORACLE_HOME=/u01/app/oracle/product/11.2.0/db_1
	export PATH=$PATH:$ORACLE_HOME/bin
	export LANG="zh_CN.UTF-8"
	export NLS_LANG="SIMPLIFIED CHINESE_CHINA.AL32UTF8"
	export NLS_DATE_FORMAT='yyyy-mm-dd hh24:mi:ss'
	
	16.配置监听程序（貌似不需要）：
	[oracle@docker ~]$ netca /silent /responsefile /home/oracle/etc/netca.rsp
	Parsing command line arguments:
		Parameter "silent" = true
		Parameter "responsefile" = /home/oracle/etc/netca.rsp
	Done parsing command line arguments.
	Oracle Net Services Configuration:
	Profile configuration complete.
	Oracle Net Listener Startup:
		Running Listener Control: 
		  /u01/app/oracle/product/11.2.0/db_1/bin/lsnrctl start LISTENER
		Listener Control complete.
		Listener started successfully.
	Listener configuration complete.
	Oracle Net Services configuration successful. The exit code is 0
	
	17.启动监控程序：
	lsnrctl start
	
	18.静默dbca建库：
	[oracle@docker ~]$ vi etc/dbca.rsp
	[GENERAL]
	RESPONSEFILE_VERSION = "11.2.0"
	OPERATION_TYPE = "createDatabase"
	[CREATEDATABASE]
	GDBNAME = "dbsrv2"
	SID = "dbsrv2"
	TEMPLATENAME = "General_Purpose.dbc"
	CHARACTERSET = "AL32UTF8"
	
	19.建库
	dbca -silent -responseFile etc/dbca.rsp
	
	20.至此，oracle数据库安装完成，外部连接注意关闭防火墙
solr
	1安装solrCloud
	1.1：安装zookeeper集群并启动
	1.2：下载solr，解压，修改bin/solr.in.sh:SOLR_HOST和SOLR_TIMEZOON(UTC+8)
             将solr scp到各台机器并修改solr.in.sh
	1.3:启动solrCloud：/usr/local/solr-6.6.0/bin/solr start -cloud -z master:2181,slave1:2181,slave2:2181 -p 8993 -force
	    访问地址是ip:8993/solr/index.html
	1.4:创建core和分片：/bin/solr(根据命令提示help)
	1.5:将配置文件上传zk：
		./server/scripts/cloud-scripts/zkcli.sh -zkhost 127.0.0.1:9983 -cmd upconfig -confname my_new_config -confdir server/solr/configsets/basic_configs/con
		
	2solr mysql数据同步单机版
	2.1创建core：$slor_home/bin/solr create_core -c user
	2.2创建数据库表和数据，将$solr_home/dist下dataimport相关的两个jar包，和mysql的驱动jar包复制到$solr_home/server/server-web/WEB-INF/lib.
	2.3配置solr core
	2.3.1在solrconfig.xml中append
	<requestHandler name="/mysqldataimport" class="solr.DataImportHandler"> 
      <lst name="defaults"> 
        <str name="config">mysql-data-config.xml</str> 
      </lst> 
    </requestHandler>
	mysql-data-config.xml内容：
	<?xml version="1.0" encoding="UTF-8" ?>  
	<dataConfig>
	<dataSource type="JdbcDataSource" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost:3306/liyu" user="liyu" password="liyuff"/>
	<document name="testmysqladdDoc">
		<entity name="tb_solr_add"
				pk="id"
				query="select id,name,update_time,del from user where del='0'"
				deltaImportQuery="select id,name,update_time,del from user where id='${dih.delta.id}'"
				deltaQuery="select id from user where update_time> '${dataimporter.last_index_time}' and del='0'"
				deletedPkQuery="select id from user where del='1'">
		<field column="id" name="id"/>
		<field column="name" name="name"/>
		<field column="update_time" name="update_time"/>
		<field column="del" name="del"/>
	   </entity>
	</document>
	</dataConfig>
	managed-schema append如下，注意注释掉前面的filed[name="id"]默认配置。
	<field name="id" type="int" indexed="true" stored="true" required="true" multiValued="false" />
    <field name="name" type="string" indexed="true" stored="true" />
    <field name="update_time" type="date" indexed="true" stored="true" />
    <field name="del" type="boolean" indexed="true" stored="true" />
	2.4启动solr，页面操作mysqldataimport，全量导入。
	2.5后台执行定时器：
	cat mysql-solr-data-delta-import.cron 
	*/1 * * * * curl "http://single:8983/solr/user/mysqldataimport?command=delta-import&clean=false&commit=true" >> logs.log
	
	3solr mysql数据同步集群版
	3.1修改/usr/local/solr-6.6.0/server/solr/configsets/data_driven_schema_configs/conf下相关配置文件，和2中单机版一样
	3.2命令将配置发布到zk：
	#pwd /usr/local/solr-6.6.0/server
	./scripts/cloud-scripts/zkcli.sh -zkhost master:2181 -cmd upconfig -confdir solr/configsets/data_driven_schema_configs/conf  -confname data_driven_schema_configs
	3.3创建collection
	/usr/local/solr-6.6.0/bin/solr create_collection -c user -d data_driven_schema_configs -n data_driven_schema_configs -shards 3 -replicationFactor 3 -force

	solr 7.4创建core 定义schema http上传数据
	./bin/solr create_core -c name -force
	页面上core/document 定义schama
	上传数据：(使用java，spring之restTemplate)
	String url = "http://192.168.126.128:8983/solr/liyu/update?_=1536563699949&commitWithin=1000&overwrite=true&wt=json";
	StringMap map = new StringMap(new String[] {"id","visit","comp"},new Object[] {"1","130","test"});
	Object[] param = new Object[] {map};
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
	HttpEntity<String> strEntity = new HttpEntity<String>(JSON.toJSONString(param),headers);
	String resultData = restTemplate().postForObject(url,strEntity,String.class);
	System.out.println(resultData);
	查询文档：
	String resultData = restTemplate().postForObject("http://192.168.126.128:8983/solr/liyu/select?hl=on&hl.fl=desc&q=desc:第一个数",strEntity,String.class);
	返回{
		"responseHeader":{
			"status":0,
			"QTime":2,
			"params":{
				"q":"desc:第一个数",
				"hl":"on",
				"json":"{}",
				"hl.fl":"desc"}},
			"response":{"numFound":3,"start":0,"docs":[
			{
				"id":"1",
				"id_i":1,
				"desc":"第一个数",
				"_version_":1611478501195513856},
			{
				"id":"2",
				"id_i":1,
				"desc":"第二个数",
				"_version_":1611478511193686016},
			{
				"id":"3",
				"id_i":1,
				"desc":"第三个数",
				"_version_":1611478520807030784}]
			},
		"highlighting":{
		"1":{
		  "desc":["<em>第一个数</em>"]},
		"2":{
		  "desc":["第二<em>个数</em>"]},
		"3":{
		  "desc":["第三<em>个数</em>"]}}}
	删除文档：
	1)建立xml文件，内容：
	<delete>   
		<id>1</id>   
		<id>2</id> 
	</delete>
	use commond:./post -c liyu del.xml
	IK中文分词
	在managed-schema中添加：
    <fieldType name="text_cn_ik" class="solr.TextField">
        <analyzer type="index" useSmart="false" class="org.wltea.analyzer.lucene.IKAnalyzer"/>
        <analyzer type="query" useSmart="true" class="org.wltea.analyzer.lucene.IKAnalyzer"/>
    </fieldType>
	在solr server lib中加入jar包 https://github.com/liyuboxgit/my
	将managed-schema文件同步到zk
	./scripts/cloud-scripts/zkcli.sh -zkhost master:2181,slave1:2182,slave2:2181 -cmd putfile /configs/basic_configs/managed-schema  solr/configsets/basic_configs/conf/managed-schema
	如果没有效果，执行下面
	./scripts/cloud-scripts/zkcli.sh -zkhost master:2181,slave1:2182,slave2:2181 -cmd putfile /configs/uvpv(collection_name)/managed-schema  solr/configsets/basic_configs/conf/managed-schema
linux
	定时任务：(echo 'good morning'，console会没有输出，可以重定向到文件且不需要创建文件)
	service crond start （service crond start）
	echo */1 * * * * echo 'good morning' > crontest.cron
	crontab crontest.cron 
	crontab -l
	crontab -r
	rm crontest.cron
	
	修改主机名称
	hostnamectl set-hostname xxx 
	reboot	
	
	脚本Shell
	#!/bin/bash
	# ------------------------------------------------------------------hello world
	hello=HelloWorld
	echo "$hello ${#hello} !"
	echo ${hello:5}
	echo `expr index "$hello" o`
	# ------------------------------------------------------------------拼接字符串、专义
	echo "the PATH is: \""$PATH"\""
	# 遍历文件
	for file in $(ls ../)
	do 
		echo $file 
	done
	# ------------------------------------------------------------------定义数组、获取数组长度、遍历数组和while遍历数组
	arr=("a" "b" "c" "d" "e" "f")
	echo ${arr[2]}
	echo "arr length："${#arr[*]}
	for loop in ${arr[@]};do 
		echo $loop 
	done

	i=0
	while [[ i -lt ${#arr[@]} ]]; do
		echo ${arr[i]}
		let i++
	done
	# ------------------------------------------------------------------shell传递参数
	echo "shell script name：$0";
	echo "first param：$1";
	echo "second param：$2";
	echo "third param：$3";
	echo "param count: $#";
	echo "param in one string: $*"
	# ------------------------------------------------------------------运算符及条件判断
	echo `expr 2 + 2`
	echo `expr 2 - 1`
	echo `expr 2 \* 3`
	echo `expr 6 / 2`
	if [ 1==1 ]
	then
		echo '1=1'
	fi
	if [ 1 -lt 2 ]
	then
		echo '1 low then 1'
	fi
	if [[ 1==1 && 1 -lt 2 ]]
	then
		echo '1=1 1<2'
	fi
	if [ -e ./a.sh ]
	then
		echo 'file a.sh exist'
	fi
	# -----------------------------------------------------------------执行命令
	echo `date`
	
	yum groupinstall "X Window System"
	yum groupinstall "GNOME Desktop" "Graphical Administration Tools"
	ln -sf /lib/systemd/system/runlevel5.target /etc/systemd/system/default.target 图形桌面启动（runlevel3是命令界面）
	
elasticsearch
	基础教程
	https://www.elastic.co/guide/cn/elasticsearch/guide/current/getting-started.html
	对下面这个curl命令的理解：添加索引blogs，3个分片（就是把数据分成3部分），1个副本（就是为每个分片设置一个副本）
	PUT /blogs
	{
		"settings" : {
			"number_of_shards" : 3,
			"number_of_replicas" : 1
		}
	}
	改变索引blogs的副本数量
	PUT /blogs/_settings
	{
		"number_of_replicas" : 2
	}
	
	单机
	useradd es
	chown -R es:es /usr/local/elasticsearch6.4.1
	/usr/local/elasticsearch6.4.1/bin/elasticsearch -d
	curl -XGET "http://localhost:9200/_cat/health?v"
	集群
	.添加用户和授权同单机
	.修改配置文件/usr/local/elasticsearch6.4.1/config/elasticsearch.yml,修改完查看grep '^[a-z]' /usr/local/elk/elasticsearch/config/elasticsearch.yml
	cluster.name: es6.2
	node.name: node-1
	masternode.master: true
	node.data: true
	path.data: /usr/local/elk/elasticsearch/data
	path.logs: /usr/local/elk/elasticsearch/logs
	swapbootstrap.memory_lock: true
	network.host: 0.0.0.0
	http.port: 9200
	transport.tcp.port: 9300
	discovery.zen.ping.unicast.hosts: ["192.168.8.101:9300", "192.168.8.103:9300", "192.168.8.104:9300"]
	discovery.zen.minimum_master_nodes: 2
	三台机器分别启动，注意看日志
	vi /etc/security/limits.conf
	* soft nofile 65536
	* hard nofile 65536
	* soft nproc 2048
	* hard nproc 4096

	es soft memlock unlimited
	es hard memlock unlimited
	
	vi /etc/sysctl.conf 
	vm.max_map_count=655360
	fs.file-max=655360

	安装head
	.安装node 下载node 解压 设置连接到usr/bin
	node -v 
	mnp -v
	npm install -g grunt-cli
	ln -s /usr/local/node-v9.4.0-linux-x64/bin/grunt /usr/bin/grunt
	grunt -v
	wget  https://github.com/mobz/elasticsearch-head/archive/master.zip
	修改Gruntfile.js 添加hostname
	启动 grunt server
hadoop_2.7,6
	启动hadoop集群：/usr/local/hadoop2.7.6/sbin/start-dfs.sh /usr/local/hadoop2.7.6/sbin/start-yarn.sh
	创建hdfs目录：/usr/local/hadoop2.7.6/bin/hdfs dfs -mkdir input
	上传文件：/usr/local/hadoop2.7.6/bin/hdfs dfs -put /usr/local/hadoop2.7.6/etc/hadoop/* input
	运行wordcount之mapreduce程序： /usr/local/hadoop2.7.6/bin/hadoop jar /usr/local/hadoop2.7.6/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.6.jar grep input output 'dfs[a-z.]+'
	（也可以自己的mapreduce程序：/usr/local/hadoop2.7.6/bin/hadoop jar xx.jar org.apche.hadoop.example.WordCount input output）
	查看运行结果：/usr/local/hadoop2.7.6/sbin/hdfs dfs -cat output/*
	
phantomjs
	wget https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-2.1.1-linux-x86_64.tar.bz2
	yum -y install bzip2
	tar -xf phantomjs-2.1.1-linux-x86_64.tar.bz2
	yum install fontconfig freetype libfreetype.so.6 libfontconfig.so.1 libstdc++.so.6
	ln -s /usr/local/phantomjs-2.1.1-linux-x86_64/bin/phantomjs /usr/bin/phantomjs
	yum install bitmap-fonts bitmap-fonts-cjk
	
	vi in test.js
	var page = require('webpage').create();
	page.open('http://baidu.com', function(status) {
	  console.log("Status: " + status);
	  if(status === "success") {
		page.render('example.png');
	  }
	  phantom.exit();
	});
	
	phantomjs test.js


	
	
