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
	
	git branch -a 查看本地/远程分支
	git branch -d list 删除list分支
	git push origin --delete branch_wangzhan 删除远程分支branch_wangzhan

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
		
	2solr mysql数据同步
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


	
	
linux
	定时任务：(echo 'good morning'，console会没有输出，可以重定向到文件且不需要创建文件)
	service crond start （service crond start）
	echo */1 * * * * echo 'good morning' > crontest.cron
	crontab crontest.cron 
	crontab -l
	crontab -r
	rm crontest.cron
	




	