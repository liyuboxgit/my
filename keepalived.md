下载keepalived-2.0.10.tar.gz，文档url https://www.jianshu.com/p/fae5e3252fc8
解压到/usr/local/keepalived-2.0.10/
cd keepalived-2.0.10/
./configure --prefix=/usr/local/keepalived	如果失败yum -y install libnl libnl-devel
make && make install
cp /usr/local/keepalived/sbin/keepalived /usr/sbin/
cp /usr/local/keepalived-2.0.10/keepalived/etc/init.d/keepalived /etc/init.d/
cp /usr/local/keepalived-2.0.10/keepalived/etc/sysconfig/keepalived /etc/sysconfig/
mkdir /etc/keepalived/
cp /usr/local/keepalived-2.0.10/keepalived/etc/keepalived/keepalived.conf /etc/keepalived/
chmod +x /etc/init.d/keepalived
chkconfig --add keepalived
chkconfig keepalived on
service keepalived start
ps -aux |grep keepalived

vi /etc/keepalived/keepalived.conf
	vrrp_instance VI_1 {
		state MASTER                //MASTER主节点，备用节点上设置为state BACKUP
		interface ens33             //绑定虚拟机IP的网卡  两个节点设置一样 根据 ipaddr换成对应的网卡地址
		virtual_router_id 51        //VRRP组名，主副节点设置必须一样
		priority 100                //优先级(1~254之间)，备用节点必须比主节点优先级低
		advert_int 1                //组播信息发送间隔，两个节点设置必须一样
		authentication {            //设置验证信息， 两个节点设置必须一样，用于节点间信息转发时的加密
			auth_type PASS
			auth_pass 1111
		}
		virtual_ipaddress {
			192.168.16.199/24
		}
	}
service keepalived restart
ping 192.168.16.199
tail -f  /var/log/messages 查看日志
在priority低的主机上：
Dec 25 12:24:02 dockerys Keepalived_vrrp[61257]: (VI_1) Receive advertisement timeout
Dec 25 12:24:02 dockerys Keepalived_vrrp[61257]: (VI_1) Entering MASTER STATE


