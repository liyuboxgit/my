import urllib.request
import re

page = urllib.request.urlopen('http://tieba.baidu.com/p/1753935195')
htmlcode = page.read()
html=htmlcode.decode('utf-8')

reg = r'src="(.+?\.jpg)" width'
reg_img = re.compile(reg)
imglist = reg_img.findall(html)
#print(html)
for img in imglist:
    print(img)