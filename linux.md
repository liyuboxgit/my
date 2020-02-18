linux磁盘扩容
No space left on device
[root@localhost ~]# df -h /
Filesystem               Size  Used Avail Use% Mounted on
/dev/mapper/centos-root  3.5G  3.5G   20K 100% /
以上说明磁盘满了，如果继续使用就该扩容了，使用量达到100%扩容会报错，删掉部分文件，降低使用量到90%
1.使用虚拟机编辑磁盘扩容（vmworkstation中操作）
2.fdisk -l 查看扩容后磁盘容量，然后对新加的磁盘进行分区操作：
  fdisk /dev/sda
  输入p查看已分区数量
  输入n新增一个分区
  输入p选择分区类型
  输入3作为分区号
  输入w写入磁盘
  操作完成后，再次fdisk -l查看分区
3.reboot
4.vgdisplay 查看卷组名
  pvcreate /dev/sda3 初始化刚刚的分区
  vgextend centos /dev/sda3 将初始化过的分区加入到虚拟卷组名
  vgdisplay 再次查看卷组情况
  df -h 扩展根目录，所以我记下的是 /dev/mapper/centos-root
  lvextend -L +29G /dev/mapper/centos-root 扩容已有的卷组容量
  pvdisplay 查看当前卷组
  xfs_growfs /dev/mapper/centos-root 扩展磁盘空间
  df -h 我们再次用命令查看磁盘状态，可以看到，现在已经扩容成功了！
