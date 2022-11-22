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
import json
import os
import time
from datetime import datetime
from cryptography.fernet import Fernet

# Set up web3 connection with Ganache
val=0
path ="C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/01-GetStarted/Registration/"
list3b=[]
list2b=[]
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
    recieve_time = now2.strftime("%H:%M:%S")  
    
    recieve_time_seconds = time.time()/10000#now.strftime("%H:%M:%S")  
    tuple_ind2 = (str(current_time), str(recieve_time), str(((recieve_time_seconds - current_time_seconds)*1000)-0.5))
    str_file='C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/Service1/TestBed1Code-Registration/Decryption/128/ServiceExecution100.txt'
    f=open(str_file,"a")
    f.write(str(tuple_ind2[2])+'\n')
   

 






def FetchingRegistrationPolicy(Serivcename,str1digest12,Singnedcert,SDNKey2,key):
# Creating Policy Administration Point
 global list2b
 now = datetime.now()
 current_time = now.strftime("%H:%M:%S")  
 current_time_seconds = time.time()#now.strftime("%H:%M:%S")      
 mylist = ["Service1"] 
 a=str(random.choice(mylist))   
 pap = PAP(deny_unless_permit)
 time.sleep(0.6)# for 600ms  100
 #
 #time.sleep(0.12)# for 120ms 500 
 #time.sleep(0.06)# for 60ms  1000 
 #time.sleep(0.03)# for 30ms  2000 
 #time.sleep(0.015)# for 15ms  3000 
#Getting input from file as JSON
##############################This is for Blockchain keys verfication ############################
 with open("registration_rule.txt", "r") as json_file:
     data = json.load(json_file)
     
     
     file = 'C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/01-GetStarted/Keys/Keys128/private-key1.pem'
         # Location of the file (can be set a different way)

     BLOCK_SIZE = 158 # The size of each read from the file second usecase important factor 
     with open(file, 'rb') as f1: # Open the file to read it's bytes
        fb = f1.read(BLOCK_SIZE) # Read from the file. Take in the amount declared above
        print(fb)
        while len(fb) > 0: # While there is still data being read from the file
          time.sleep(0.018)
          fb = f1.read(BLOCK_SIZE) # Read the next block from the file





    
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

#

 



###############################This below code reflect the rule of Registration in Public Chain #########################################
# Adding policy to PAP
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

#/home/ubuntu/Desktop/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/01-GetStarted/Keys  THis is for 128bit key link


#print(item)

 pdp = PDP(pap_instance=pap)

# Creating Policy Enforcement Point
 pep = DenyBiasedPEP(pdp)
 now21 = datetime.now()
 recieve_time = now21.strftime("%H:%M:%S")  
 recieve_time_seconds = time.time() 
 list2b.append(((recieve_time_seconds - current_time_seconds)*1000))
 print(list2b) 
 Average=sum(list2b)/len(list2b)
 tuple_ind2 = (str(current_time), str(recieve_time), str(((recieve_time_seconds - current_time_seconds)*1000)),str(Average))
 str_file='C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/'+str(a)+'/TestBed1Code-Registration/Decryption/128/100/decrytion.txt'
 f=open(str_file,"a")
 f.write(str(tuple_ind2)+'\n')


 
 AuthorizationFind(path,a)

#Context Execution is actually sensed the Execution of Smart Contract So before that we used solidity code #
 context = {
    'resource.type': 'user',
    'action': 'Authentication',
    'SDNController': "4b3f3be75c6f95244604638f7d6918df",
    'AirQualitySeverity': 'Low'
 }


# Evaluating policy
 result = pep.evaluate(context)





 
if __name__ == "__main__":
#########################################Send Encrypt message ############################################################

 key = Fernet.generate_key()
 #print(key)
 cipher_suite = Fernet(key)
#####################################################################################
 ##1. For register payload 
 str1='Register'
 file_hash1 = hashlib.sha256()
 file_hash1.update(str1.encode('utf-8'))
 str1digest1=file_hash1.hexdigest()
 str1digest12=bytes(str1digest1,'utf-8')
 cipher_text1 = cipher_suite.encrypt(str1digest12)
 #print("Shahbaz1",cipher_text1)
 plain_text2 = cipher_suite.decrypt(cipher_text1)
 #print("Decyrption22222",plain_text2)
  
##########################################################################################
 ##2. For certificate payload 
 file = 'C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/01-GetStarted/Keys/hash1.txt' # Location of the file (can be set a different way)
 BLOCK_SIZE = 65536 # The size of each read from the file

 file_hash = hashlib.sha256() # Create the hash object, can use something other than `.sha256()` if you wish
 with open(file, 'rb') as f: # Open the file to read it's bytes
    fb = f.read(BLOCK_SIZE) # Read from the file. Take in the amount declared above
    #print(fb)
    while len(fb) > 0: # While there is still data being read from the file
        file_hash.update(fb) # Update the hash
        fb = f.read(BLOCK_SIZE) # Read the next block from the file
 certidigest1=file_hash.hexdigest()
 Singnedcert=bytes(certidigest1,'utf-8')
 cipher_text2 = cipher_suite.encrypt(Singnedcert)
######################################################################################################
 ##2. For SDN key payload 
 file = 'C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/01-GetStarted/Keys/hash2.txt' # Location of the file (can be set a different way)
 BLOCK_SIZE = 65536 # The size of each read from the file

 file_hash = hashlib.sha256() # Create the hash object, can use something other than `.sha256()` if you wish
 with open(file, 'rb') as f: # Open the file to read it's bytes
    fb = f.read(BLOCK_SIZE) # Read from the file. Take in the amount declared above
    #print(fb)
    while len(fb) > 0: # While there is still data being read from the file
        file_hash.update(fb) # Update the hash
        fb = f.read(BLOCK_SIZE) # Read the next block from the file
 SDNKey=file_hash.hexdigest()
 SDNKey2=bytes(SDNKey,'utf-8')
 cipher_text3 = cipher_suite.encrypt(SDNKey2)

for i in range(500):
 FetchingRegistrationPolicy("Shahbaz",str1digest12,Singnedcert,SDNKey2,key)



 


