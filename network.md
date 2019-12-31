yum install telnet 安装客户端，就有了telnet探测远程服务器端口是否打开的功能
yum install telnet-server 安装服务端
systemctl start telnet.socket 启动telnet服务
systemclt status telnet.socket 查看telnet服务
telnet host(ip) port 如果No route to host说明防火墙开启或者对方telnet.socket没有启动，如果Connection refused说明远程服务器没有打开，如果出现Escape character is '^]'说明远程端口已经开启。

yum install net-tools 安装netstat工具

hostname 查主机名称
hostname -i 查主机名称对应的ip，对dubbo，rmi等改成127.0.0.1

hostnamectl set-hostname xxx
reboot

systemctl disable firewalld
systemctl stop firewalld