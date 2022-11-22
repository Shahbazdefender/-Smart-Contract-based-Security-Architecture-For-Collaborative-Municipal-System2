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


path ="'C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/01-GetStarted/01-GetStarted/Registration/"
list3b=[]
list2b=[]
val=0.0
def AuthorizationFind(path,Serivcename):
    global list2b
    mylist = ["Service1"] 
    a=str(random.choice(mylist))   
    
    
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
        #/Keys Change karna har experiment main 128 key only
        file = 'C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/01-GetStarted/Keys/Keys128/private-key'+str(i)+'.pem'

 
        BLOCK_SIZE = 128 # The size of each read from the file second usecase important factor 
        with open(file, 'rb') as f1: # Open the file to read it's bytes
          #relay=0.00042 # for 600ms  100
    #      relay=0.0002 # for 120ms 500
          #relay=0.00008 #for 60ms  1000
          #relay=0.00006 #for 60ms  2000
          #relay=0.00000 #for 60ms  3000
          fb = f1.read(BLOCK_SIZE) # Read the next block from the file
          registerKeyJSON = {'NodeID' : str(hash(i)),'TrustValue' :  str(val)}  	
          registerKeyJSONString = str(registerKeyJSON).encode("utf-8").hex()
          from Savoir import Savoir
          rpcuser = 'multichainrpc'
          rpcpasswd = 'FAfHxZhj2c6D9b45X5SsGeDUZ9Sj8Lc1qiSSAYXP2Urw'
          rpchost = '127.0.0.1'
          rpcport = '5746'
          chainname = 'smartmeter'
          import hashlib
          api = Savoir(rpcuser, rpcpasswd, rpchost, rpcport, chainname)
          print(api.liststreamitems("Test1"))
       
    
        
    
          
   

 





def FetchingRegistrationPolicy(Serivcename):
# Creating Policy Administration Point
 now = datetime.now()
 current_time = now.strftime("%H:%M:%S")  
 current_time_seconds = time.time()/10000#now.strftime("%H:%M:%S")      
   

##############################This is for Blockchain keys verfication ############################
 with open("registration_rule.txt", "r") as json_file:
     data = json.load(json_file)
    
 print(data)
 address = list(data.values())[2]
 pubkey = list(data.values())[3]
#Aithentication Rule#
 if address == "1W6PKULPuyjx7h4wxDgi9JpVyrcs5RS116qDWY" and pubkey == "02ac829057fe50a82f5c808741fa05d99955f9e9f9b5fd1cd73237cacfb33def47":
    	print('Contract 5 Success')
 else : 
        print('Contract 5 Failed')
        
 ##After this upper code actually service chain code run but I write the code in Registration.py have same impact 
         
#########################################################################
 
 airQualityIndexValue = list(data.values())[0]
 airQualitySeverity = list(data.values())[1]
#Aithentication Rule#
 if airQualityIndexValue == "4b3f3be75c6f95244604638f7d6918df" and airQualitySeverity == "Low":
 	print('Contract 4 Success')
 else : 
        print('Contract 4 Failed')

################################This below code reflect the rule of Registration in Public Chain #########################################
 
 AuthorizationFind(path,Serivcename)

 



#Context Execution is actually sensed the Execution of Smart Contract So before that we used solidity code #

#########################This is the rule of Registration mechanism #################################################
 
##########################################################################################################################
# Evaluating policy
 

 
if __name__ == "__main__":
 for i in range (2000):
  FetchingRegistrationPolicy("Service1")
  



###






