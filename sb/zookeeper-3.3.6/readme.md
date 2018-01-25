wget http://mirror.bit.edu.cn/apache/zookeeper/zookeeper-3.3.6/zookeeper-3.3.6.tar.gz
tar -xf zookeeper-3.3.6.tar.gz 
cp zookeeper-3.3.6/conf/zoo_sample.cfg zookeeper-3.3.6/conf/zoo.cfg 
mkdir zookeeper-3.3.6/my
mkdir zookeeper-3.3.6/my/log
vi zookeeper-3.3.6/conf/zoo.cfg  
--set 
--dataDir=/home/liyu/zookeeper-3.3.6/my
--dataLogDir=/home/liyu/zookeeper-3.3.6/my/log
--vi save
zookeeper-3.3.6/bin/zkServer.sh start
--startup
zookeeper-3.3.6/bin/zkCli.sh
--connect
quit
--quit from zookeeper client
