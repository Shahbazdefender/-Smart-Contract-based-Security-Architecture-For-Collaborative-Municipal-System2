from sabac import PDP, PAP, DenyBiasedPEP, deny_unless_permit
import sys
import json
import hashlib
import os
from ipaddress import IPv6Network
import random
import ipaddress

from sabac import PDP, PAP, DenyBiasedPEP, deny_unless_permit
import sys
import ast
import random 
#Connection Established 
import json
import os
import time
#from web3 import Web3
from datetime import datetime

# Set up web3 connection with Ganache


val=0
def AuthorizationFind(path,Serivcename):
    global list2b
    mylist = ["Service1"] 
    a=str(random.choice(mylist))   
  
    now = datetime.now()
    current_time = now.strftime("%H:%M:%S")  
    current_time_seconds = time.time()/10000#now.strftime("%H:%M:%S")      
   
    src = "C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/01-GetStarted/Trust Management"
    files = os.listdir("C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/01-GetStarted/Trust Management")
    i=0
    for file in files:
      
      srcPath = src+"/"+file
      print(srcPath,"srcPath")
      isDir = os.path.isdir(srcPath)
      #print(isDir,"isDir")
      if not isDir:
        f = open(srcPath)
        line = f.readline()
        global val
        val = float(line)
      if val:
        print(val,"line") 
        i=i+1 
        #/Keys Change karna har experiment main
        file = 'C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/01-GetStarted/Keys/Keys128/private-key'+str(i)+'.pem'
         # Location of the file (can be set a different way)

        BLOCK_SIZE = 158 # The size of each read from the file second usecase important factor 
        with open(file, 'rb') as f1: # Open the file to read it's bytes
         fb = f1.read(BLOCK_SIZE) # Read from the file. Take in the amount declared above
         print(fb)
         k=0
         while len(fb) > 0: # While there is still data being read from the file
 
          relay=0.15 # for 600ms  100
          #relay=0.06 # for 120ms 500
          #relay=0.002 #for 60ms  1000
          #relay=0.00015 #for 60ms  2000
          #relay=0.000 #for 60ms  3000
          time.sleep(relay)
          fb = f1.read(BLOCK_SIZE) # Read the next block from the file
          registerKeyJSON = {'NodeID' : str(hash(i)),'TrustValue' :  str(val)}  	
          registerKeyJSONString = str(registerKeyJSON).encode("utf-8").hex()
         # print("SHHAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",k)
          k=k+1      
          os.system("multichain-cli chain3 publish  Service1nodeTrust " + str(registerKeyJSON).encode("utf-8").hex() + " "+ registerKeyJSONString)
          os.system("multichain-cli service2 publish  Service2nodeTrust " + "" + " "+ registerKeyJSONString)    
          os.system("multichain-cli service2 publish  Service2nodeTrust " + str(registerKeyJSON).encode("utf-8").hex() + " "+ registerKeyJSONString)    
 
    now2 = datetime.now()
   #Iss testbed main haumnai delay minus bhi karna hai relay ko fixed kardain 
         #Delay should be add becasue of interreltion 
    recieve_time = now2.strftime("%H:%M:%S")  
    #Question why sec /10000=1 milisecond
    
    recieve_time_seconds = time.time()/10000#now.strftime("%H:%M:%S")  
    tuple_ind2 = (str(current_time), str(recieve_time), str(((recieve_time_seconds - current_time_seconds)*1000)-0.5))
    str_file='C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/Service1/TestBed1Code-Registration/END_END/128/2000/999.txt'
    f=open(str_file,"a")
    f.write(str(tuple_ind2[2])+'\n')
































    
def CollaborativeRule(Servicename):
# Adding policy to PAP
 now = datetime.now()
 current_time = now.strftime("%H:%M:%S")  
 current_time_seconds = time.time()#now.strftime("%H:%M:%S")      
   
 pap = PAP(deny_unless_permit)
 with open("Service1API.txt", "r") as json_file:
     print("Shahbaz")
     data = json.load(json_file)
     print(data)
 item = {
    "description": "Admin permissions",
    "target": data,
    "algorithm": "DENY_UNLESS_PERMIT",
    'rules': [
        {
            "effect": "PERMIT",
            "description": "Authentication of SDN Controller",
            "target": {
                'resource.type': 'user',
                'action': {'@in': ['COLLABORATIVE', 'Trust', 'Authorization']},
            },
        }
    ]
 } 

 pap.add_item(item)
 pdp = PDP(pap_instance=pap)

# Creating Policy Enforcement Point
 pep = DenyBiasedPEP(pdp)




 AuthorizationFind(path,Servicename)

#Context Execution is actually sensed the Execution of Smart Contract So before that we used solidity code #
 context = {
    'resource.type': 'user',
    'action': 'COLLABORATIVE',
    'SDNController': "4b3f3be75c6f95244604638f7d6918df",
    'AirQualitySeverity': 'Low'
 }


# Evaluating policy
 result = pep.evaluate(context)

 print(result)
 now2 = datetime.now()
 recieve_time = now2.strftime("%H:%M:%S")  
 recieve_time_seconds = time.time()#now.strftime("%H:%M:%S")  
 
 tuple_ind2 = (str(current_time), str(recieve_time), str(recieve_time_seconds - current_time_seconds))  
 str_file='C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/'+str(Servicename)+'/registration/CollaborativeContract.txt'
 f=open(str_file,"a")
 f.write(str(tuple_ind2[3])+'\n')








list3b=[]
list2b=[]


def FetchingRule(Servicename):
# Creating Policy Administration Point
 global list2b   

 pap = PAP(deny_unless_permit)
 with open("Service1API.txt", "r") as json_file:
     print("Shahbaz")
     data = json.load(json_file)
     print(data)
    
#Getting input from file as JSON
 for j in range (50):
   print("Shahbaz2")
   y=str(random.choice(data["AirIndex"]))
   print(data["organisations"])
   print("latitude: " + str(random.choice(data["longitude"])))
   print("longitude: " + str(random.choice(data["latitude"])))
   print("AirIndex:"  + y)
   #print("Write on file *******************************************************")    
   now = datetime.now()
   current_time = now.strftime("%H:%M:%S")  
   current_time_seconds = time.time()#now.strftime("%H:%M:%S")      
   time.sleep(0.6) ##600 milisecond delay
   #time.sleep(0.12) ##120 milisecond delay
   #time.sleep(0.06) ##60 milisecond delay
   #time.sleep(0.03) ##30 milisecond delay
   #time.sleep(0.01) ##15 milisecond delay
   
   if (int(y)>100):
      print("Alert")       
      #CollaborativeRule(Servicename)
      

      new_data1 = {
            "organisations": "Blockcchain to NADRA",
            "latitude": str(random.choice(data["longitude"])),
            "longitude": str(random.choice(data["longitude"])),
            "message": "ALERT!",
            }
      registerKeyJSON = {'Service2Data' : str(new_data1)}
      registerKeyJSONString = str(registerKeyJSON).encode("utf-8").hex()
      os.system("multichain-cli chain3 publish  datawritestream  " + str(j) + " "+ registerKeyJSONString)
      os.system("multichain-cli service2 publish  datawritestream " + str(j) + " "+ registerKeyJSONString)
      #os.system("multichain-cli service2 publish  datawritestream " + str(j) + " "+ registerKeyJSONString)
   
   
   file = 'C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/01-GetStarted/Keys/Keys128/private-key1.pem'
         # Location of the file (can be set a different way)

   BLOCK_SIZE = 158 # The size of each read from the file second usecase important factor 
   with open(file, 'rb') as f1: # Open the file to read it's bytes
      fb = f1.read(BLOCK_SIZE) # Read from the file. Take in the amount declared above
      print(fb)
      while len(fb) > 0: # While there is still data being read from the file
          time.sleep(0.009)
          fb = f1.read(BLOCK_SIZE) # Read the next block from the file
   relay=60 #yeh writing ka minus hai 
   now21 = datetime.now()
   recieve_time = now21.strftime("%H:%M:%S")  
   recieve_time_seconds = time.time()#now.strftime("%H:%M:%S") 
   list2b.append(((recieve_time_seconds - current_time_seconds)*1000))
    
   Average=sum(list2b)/len(list2b)
   tuple_ind2 = (str(current_time), str(recieve_time), str(((recieve_time_seconds - current_time_seconds)*1000)),str(Average))
   str_file='C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/Service1/TestBed1Code-Registration/END_END/128/3000/END_END'
   f=open(str_file,"a")
   f.write(str(tuple_ind2)+'\n')
   f.close()

   
   
   
      
# APPLICATION policy 
   item = {
    "description": "Admin permissions",
    "target": data,
    "algorithm": "DENY_UNLESS_PERMIT",
    'rules': [
        {
            "effect": "PERMIT",
            "description": "Authentication of SDN Controller",
            "target": {
                'resource.type': 'user',
                'action': {'@in': ['Authentication', 'Trust', 'Authorization']},
            },
        }
    ]
 } 

   pap.add_item(item)
   pdp = PDP(pap_instance=pap)

# Creating Policy Enforcement Point
   pep = DenyBiasedPEP(pdp)


 

