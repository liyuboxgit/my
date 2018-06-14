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
	