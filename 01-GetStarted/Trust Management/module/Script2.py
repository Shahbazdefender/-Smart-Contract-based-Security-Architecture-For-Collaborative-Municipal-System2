import requests
from bs4 import BeautifulSoup as soup
import time
import os.path
import os
Records = open('nPolicy.txt',"r",encoding="utf-8").readlines()



i = 1
while i<70 :	
	u='/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/01-GetStarted/Registration/'
	u1=str(i)
	u3=u+u1
	i=i+1
	if os.path.isfile(u3):
	 f1= open('/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/01-GetStarted/Trust Management/Trust'+str(i),"x")
	 time.sleep(1)
	 f1.write(str(0.01))
  
	 print ("File exist")
	else:
    	     print (" Not exist")
