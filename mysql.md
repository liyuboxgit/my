centos7安装mysql前系统设置，设置完成重启系统
1.卸载mariadb
	rpm -qa|grep mariadb
	yum remove 前行结果
2.vi /etc/sysconfig/selinux，set disabled
3.systemctl status firewalld
4.dmesg | grep -i scheduler，IO调度，默认是deadline不必修改
5.vi /etc/security/limits.conf,文件后加入
	* soft nproc 65535
	* hard nproc 65535
	* soft nofile 65535
	* hard nofile 65535
	ulimit -a 查看修改结果
6.关闭numa
	vi /etc/default/grub
	在这行GRUB_CMDLINE_LINUX="crashkernel=auto rd.lvm.lv=centos/root rd.lvm.lv=centos/swap rhgb quiet numa=off"，加入numa=off
	grub2-mkconfig -o /etc/grub2.cfg，重新生成 /etc/grub2.cfg 配置文件
	cat /proc/cmdline，查看修改结果，有numa=off
--------------------------------------------------------------------------------------------------------------------------------	
安装mysql5.7
groupadd mysql
useradd -g mysql mysql -s /sbin/nologin
cd /usr/local
tar -xf mysql-5.7.28-el7-x86_64.tar.gz
ln -s mysql-5.7.28-el7-x86_64 mysql
mkdir -p /data/mysql
vi /etc/my.cnf
	[mysql]
	default-character-set=utf8mb4 
	socket=/tmp/mysql.sock

	[mysqld]
	default-time_zone='+8:00'
	port = 3306 
	socket=/tmp/mysql.sock
	basedir=/usr/local/mysql
	datadir=/data/mysql
	log-error=/data/mysql/error.log
	max_connections=200
	character-set-server=utf8mb4
	default-storage-engine=INNODB
	lower_case_table_name=1
	max_allowed_packet=16M
chown mysql:mysql -R /usr/local/mysql	
chown mysql:mysql -R /data/mysql
--初始化、启动、登录
cd /usr/local/mysql/bin
./mysqld --defaults-file=/etc/my.cnf --basedir=/usr/local/mysql --datadir=/data/mysql/ --user=mysql --initialize
cat /data/mysql/error.log
./mysqld_safe & 注意，正常启动是./mysqld --user=mysql &
./mysql -uroot -p
--修改密码
mysql> set password='liyuff';
Query OK, 0 rows affected (0.00 sec)

mysql> alter user 'root'@'localhost' password expire never;
Query OK, 0 rows affected (0.00 sec)

mysql> flush privileges;
Query OK, 0 rows affected (0.00 sec)
--关闭mysql
./mysqladmin -uroot -pliyuff shutdown
-------------------------------------------------------------------------------------------------------------------------
mysql文件
show variables like '%lower_case_table_names%';  在/etc/my.cnf中配置lower_case_table_names=1
show variables like '%log_bin%';  查看binarylog
show variables like '%slow_query%';  查看慢查询日志
pid文件默认在/data/mysql
sock文件在/tmp/
表结构文件一般以.frm结尾,表数据和索引文件一般以.ibd结尾，他们在datadir/数据库名，的目录下
中继日志relay_log,在从服务器中
redo log日志：ib_logfile0~2
系统表空间：ibdata1
临时表空间：ibtmp1
-------------------------------------------------------------------------------------------------------------------------
mysql全库冷备
就是在关闭状体下，mysql的datadir下面的所以文件，拷贝到另一台mysql服务器相应的目录即可。
主要操作就是mysql的启动关闭，和tar的三个命令：
tar -cf name.tar /data/mysql/  	mysql文件打包
tar -xf name.tar 				解压打包文件
-------------------------------------------------------------------------------------------------------------------------
异步复制
CREATE USER 'bak'@'192.168.16.*' IDENTIFIED BY 'passbak';（这里*不可以，要改成从服务器的ip）
grant replication slave on *.* to 'bak'@'192.168.16.*';
flush privileges;--主库配置，如果user ip第四部分要写，一定写从库的ip

server-id=1
log_bin=mysql-bin.log
binlog_format=row--主库配置
server-id=2 --从库配置

/usr/local/mysql/bin/mysqldump --single-transaction -uroot -pliyuff --master-data=2 -A > all.sql--主库执行
/usr/local/mysql/bin/mysql -uroot -p < all.sql--从库执行

stop slave;
change master to master_host='192.168.16.128', master_user='bak', master_password='passbak',
master_log_file='mysql-bin.000002', master_log_pos=1346; --从库执行，master_log_file和master_log_pos是show master status获取的
start slave;
show slave status\G;--从库执行，如果connection失败，注意主库机的防火墙
---------------------------------------------------------------------------------------------------------------------------
半同步复制
install plugin rpl_semi_sync_master soname 'semisync_master.so';
set global rpl_semi_sync_master_enabled=on;--主库执行
show variables like '%semi%'
==>rpl_semi_sync_master_enabled  on
   rpl_semi_sync_master_timeout  1000
show plugins;
==> rpl_semi_sync_master       | ACTIVE   | REPLICATION        | semisync_master.so | GPL --说明主库已开启半同步复制功能


install plugin rpl_semi_sync_slave soname 'semisync_slave.so';
set global rpl_semi_sync_slave_enabled=on;
show variables like '%semi%'
==> rpl_semi_sync_slave_enabled     | ON  --从库执行，说明从库已开启半同步复制功能
stop slave io_thread;
start slave io_thread;


 show global status like '%semi%';--主库执行，说明主从库半同步复制可用
+--------------------------------------------+-------+
| Variable_name                              | Value |
+--------------------------------------------+-------+
| Rpl_semi_sync_master_clients               | 1     ||
| Rpl_semi_sync_master_net_avg_wait_time     | 0     |
| Rpl_semi_sync_master_net_wait_time         | 0     |
| Rpl_semi_sync_master_net_waits             | 0     |
| Rpl_semi_sync_master_no_times              | 0     |
| Rpl_semi_sync_master_no_tx                 | 0     |
| Rpl_semi_sync_master_status                | ON    ||
| Rpl_semi_sync_master_timefunc_failures     | 0     |
| Rpl_semi_sync_master_tx_avg_wait_time      | 0     |
| Rpl_semi_sync_master_tx_wait_time          | 0     |
| Rpl_semi_sync_master_tx_waits              | 0     |
| Rpl_semi_sync_master_wait_pos_backtraverse | 0     |
| Rpl_semi_sync_master_wait_sessions         | 0     |
| Rpl_semi_sync_master_yes_tx                | 0     |
+--------------------------------------------+-------+

从库关闭的情况
从库关闭，主库执行insert操作，会等待10s，10后事务提交
 show global status like '%semi%';--主库执行，说明半同步已经关闭了
 +--------------------------------------------+-------+
| Variable_name                              | Value |
+--------------------------------------------+-------+
| Rpl_semi_sync_master_clients               | 0     ||
| Rpl_semi_sync_master_net_avg_wait_time     | 0     |
| Rpl_semi_sync_master_net_wait_time         | 0     |
| Rpl_semi_sync_master_net_waits             | 4     |
| Rpl_semi_sync_master_no_times              | 1     |
| Rpl_semi_sync_master_no_tx                 | 1     |
| Rpl_semi_sync_master_status                | OFF   ||
| Rpl_semi_sync_master_timefunc_failures     | 0     |
| Rpl_semi_sync_master_tx_avg_wait_time      | 521   |
| Rpl_semi_sync_master_tx_wait_time          | 1564  |
| Rpl_semi_sync_master_tx_waits              | 3     |
| Rpl_semi_sync_master_wait_pos_backtraverse | 0     |
| Rpl_semi_sync_master_wait_sessions         | 0     |
| Rpl_semi_sync_master_yes_tx                | 3     |
+--------------------------------------------+-------+
为了解决这种情况：
主库my.cnf
rpl_semi_sync_master_enabled=on
rpl_semi_sync_master_timeout=86400000
从库my.cnf
rpl_semi_sync_slave_enabled=on
主从库重启，ok

主库从库由于网络原因或者在从库上的误操作导致复制中断，在从库 .
处理方法就是在主库上做了什么操作，在从库上也做什么操作，保持主库从库一致。
get https://www.percona.com/downloads/percona-toolkit/3.0.12/binary/redhat/6/x86_64/percona-toolkit-3.0.12-1.el6.x86_64.rpm
yum install perl-DBI perl-DBD-MySQL perl-Digest-MD5 perl-IO-Socket-SSL perl-TermReadKey
rpm -ivh percona-toolkit-3.0.12-1.el6.x86_64.rpm
/usr/bin/pt-slave-restart -uroot -pliyuff --socket=/tmp/mysql.sock 
show slave status\G，显示Slave_IO_Running=yes,Slave_sql_Running=yes --说明复制功能回复正常，以上从库执行
-----------------------------------------------------------------------------------------------------------------
GTID复制
server-id=1
log_bin=mysql-bin.log
binlog_format=row
gtid_mode=on
enforce_gtid_consistency=on

server-id=2
gtid_mode=on
enforce_gtid_consistency=on
log_slave_updates=1
启动主库，启动从库
从库执行：
stop slave;
change master to master_auto_position=1;--在上面传统复制的基础上，主库以有备份用户的条件下。
start slave;




















