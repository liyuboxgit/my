  157  whereis python
  158  ll /usr/bin/python*	centos预装python2.7，故有python->python2,python2->python2.7
  159  yum install zlib-devel bzip2-devel openssl-devel ncurses-devel sqlite-devel readline-devel tk-devel gcc make
  160  yum -y install epel-release
  169  wget https://www.python.org/ftp/python/3.6.4/Python-3.6.4.tar.xz
  172  tar -xf Python-3.6.4.tar.xz 
  173  cd Python-3.6.4
  174  ./configure prefix=/usr/local/python3
  175  make 
  176  make install
  189  rm /usr/bin/python	将原来的python快捷方式删除
  190  ln -s /usr/local/python3/bin/python3.6 /usr/bin/python	重新建立快捷方式并指向python3
  191  python -V
  192  vi /usr/bin/yum 	修改yum的配置将第一行/usr/bin/python改为/usr/bin/python2
  193  vi /usr/libexec/urlgrabber-ext-down	配置将第一行/usr/bin/python改为/usr/bin/python2
  194  python2
  195  python
  197  pip -V
  199  python2 -m pip uninstall pip	移除原有基于python2的pip
  201  wget https://bootstrap.pypa.io/get-pip.py
  202  python get-pip.py
  204  /usr/local/python3/bin/pip -V
  207  ln -s /usr/local/python3/bin/pip /usr/bin/pip 建立快捷方式并指向python3的pip
  
----------------------------------------------------------------------------------------------------------------

python
	python #进入python交互窗口，python注释已#开头
	print('hello,world!')
	exit()
	
	#!/usr/bin/env python3 #在linux，文件头加入这一行，就成为可执行文件
	print('The quick brown fox', 'jumps over', 'the lazy dog') 
	
	#python字符串可以用单引号，也可以用双引号。单引号和双引号是等同的。反斜杠\可以转义，r''单引号中的内容会屏蔽转义
	#布尔值True和False，布尔值计算 and or not(注意和java不一样)
	#空值None
	#运算，python中独有的板除//：8//3=2
	
	#编码
	print('中文'.encode('utf-8')) result is: b'\xe4\xb8\xad\xe6\x96\x87' #python3中，同理 print(b'\xe4\xb8\xad\xe6\x96\x87'.decode('UTF-8'))#中文  
	
	#函数,默认参数 可变参数 递归
	1.def my_abs(x):
    if x>=0:
	    return x
    else:
        return -x
	
	2.def fact(n):
    if n==1:
        return 1
    return n * fact(n - 1)
	
	#包、模块
	目录下面置_init_.py文件，就声明这个目录是个包。包下面有xy.py，就声明xy是该包下面的一个模块
	
	#安装和使用第三方模块 mysql
	pip install mysql-connector-python #安装mysql connector
	import mysql.connector
	#mysql connection config item
	'''dict Found at: mysql.connector.constants
	DEFAULT_CONFIGURATION = {
    'database':None, 
    'user':'', 
    'password':'', 
    'host':'127.0.0.1', 
    'port':3306, 
    'unix_socket':None, 
    'use_unicode':True, 
    'charset':'utf8mb4'...}'''
	conn = mysql.connector.connect(user='root', password='liyuff', database='mysql')
	cursor = conn.cursor()
	cursor.execute('select * from user where user = %s', ('root',))
	values = cursor.fetchall() #value list
	conn.close()
	
	#类
	获取对象的信息
	type() 
	#h = Dog()
	isinstance(h, Animal)
	dir()
	getattr() setattr()以及hasattr()
	使用__slots__限制类的属性
	使用@property将方法设置为属性，供外部访问
	定制类
	元类  
  
