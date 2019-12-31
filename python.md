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
