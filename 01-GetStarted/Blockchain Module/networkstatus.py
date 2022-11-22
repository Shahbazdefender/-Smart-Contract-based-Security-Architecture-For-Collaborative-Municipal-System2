import requests
from bs4 import BeautifulSoup as soup
import time
import shutil
import os

from ipaddress import IPv6Network
import random
import ipaddress
import time
import hashlib
from Savoir import Savoir
import json

def Shahbaz(val1,file):
 i=1
 for city in cities:
   url = "https://api.openweathermap.org/data/2.5/weather?q="+city.lower().strip()+"&appid=5f5d9d2de4cd0e1d132545fc21fea2a9"
   json = requests.get(url).json()
   
   if "city not" in str(json):
     json.update({'Trust': 0})         #Adding Trust

   else:
     min_ = float(Records[[Records.index(i) for i in Records if 'visibility' in i][0]].split(',')[1])
     max_ = float(Records[[Records.index(i) for i in Records if 'visibility' in i][0]].split(',')[2])
     trust_1 = json['visibility']/max_	
	
     min_ = float(Records[[Records.index(i) for i in Records if 'temp' in i][0]].split(',')[1])
     max_ = float(Records[[Records.index(i) for i in Records if 'temp' in i][0]].split(',')[2])
     trust_2 = json['main']['temp']/max_	

     min_ = float(Records[[Records.index(i) for i in Records if 'feels_like' in i][0]].split(',')[1])
     max_ = float(Records[[Records.index(i) for i in Records if 'feels_like' in i][0]].split(',')[2])
     trust_3 = json['main']['feels_like']/max_	
	
     min_ = float(Records[[Records.index(i) for i in Records if 'speed' in i][0]].split(',')[1])
     max_ = float(Records[[Records.index(i) for i in Records if 'speed' in i][0]].split(',')[2])
     trust_4 = json['wind']['speed']/max_	

     comulative_trust = round((trust_1+trust_2+trust_3+trust_4)/4,2)
     json.update({'Trust': val1})		 #Adding Commulative Trust
     json.update({'NodeNUmber': file})			
			
     	
     f = open("/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/01-GetStarted/Blockchain Module/Application Logs/"+file+".txt","a")
     i+=1
     f.write("\n")
     f.write(str(json))
     f1= str(json).encode("utf-8").hex()
     a=1
     os.system("multichain-cli chain3 publish Registration " + str(a) + " "+ f1)


 
headers = {'User-Agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36'}


cities =open("city.csv","r").readlines()
Records = open('weather_values.txt',"r",encoding="utf-8").readlines()

headers = {'User-Agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36'}
 
i = 1

#ip
ula = IPv6Network("fd00::/8")

start_ip = ipaddress.IPv6Address('fd00::')
end_ip = ipaddress.IPv6Address('fd00::38')

i = 0
arrIP = []
for ip_int in range(int(start_ip), int(end_ip)):
 i =+ 1
 if i > 70:
  break
 arrIP.append(ip_int)
 
 
i = 1
counterIp = 0




#control Variable when framework start and registered Public Key represent the with the help of Hash Value 

for i in range(1,50):
	registerKeyJSON = {'Public Key' : str(hash(ipaddress.IPv6Address(arrIP[i]))),'IPV6' :  str(ipaddress.IPv6Address(arrIP[i]))}
	registerKeyJSONString = str(registerKeyJSON).encode("utf-8").hex()
	os.system("multichain-cli chain3 publish  RegistrationService " + str(ipaddress.IPv6Address(arrIP[i])) + " "+ registerKeyJSONString)
	os.system("multichain-cli chain3 publish Registration " + str(registerKeyJSON).encode("utf-8").hex() + " "+ registerKeyJSONString)


#Key This for Key 

#Key Trust Values 
	print(json)
#	print(type(json))
#	print("_"*80)



src = "/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/01-GetStarted/Trust Management"
dest = "/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/01-GetStarted/Network"
files = os.listdir("/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/01-GetStarted/Trust Management")
print(files)
for file in files:
 srcPath = src+"/"+file
 print(srcPath,"srcPath")
 isDir = os.path.isdir(srcPath)
 print(isDir,"isDir")
 if not isDir:
  f = open(srcPath)
  line = f.readline()
  val = float(line)
  if val:
   print(val,"line")  
   if val <= 0.1:
    print('Node is Not Trusted')
    shutil.copy(srcPath,dest+"/No access")
   elif val > 0.1:
    print('Allow Limited Access') 
    shutil.copy(srcPath,dest+"/Temp Access")
    Shahbaz(val,file)
   elif val >= 1.0:
    print('Allow Full Access')
    shutil.copy(srcPath,dest+"/Full Acess")
      
 






		
 
		

 
#		
#		time.sleep(2)
#		
#		#Registration
#/home/01-GetStarted/Trust Management


