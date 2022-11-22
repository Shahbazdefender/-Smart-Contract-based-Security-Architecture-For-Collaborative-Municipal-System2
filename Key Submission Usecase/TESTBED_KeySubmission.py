import sys
import json
import hashlib
import os
from ipaddress import IPv6Network
import random
import ipaddress
from random import choice
from datetime import datetime
import time
import json
import requests
import threading
from cryptography.fernet import Fernet

import socket                                         
import time

import logging
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





list3b=[]
list2b=[]



def registration(message1,message2,message3,key1):
   
 global list2b   


#Here The Node are going to Registrered
 mylist = ["Service","Service2"] 
 ula = IPv6Network("fd00::/8")
 #print('Please Enter the IoT node Devices Number')
 
 i=1
 start_ip = ipaddress.IPv6Address('fd00::')
 end_ip = ipaddress.IPv6Address('fd00::32')
 arrIP = []
 for ip_int in range(int(start_ip), int(end_ip)):
       # print("Hello....................................................................................")
        i =+ 1 
        if i > 50:
         print("")
        break
        arrIP.append(ip_int)
    
 for ip_int in range(int(start_ip), int(end_ip)):
    
    a=str(random.choice(mylist))
    j=0

###########################################################################################################################################        
    #decrypt the messag efirst then call fro fecthing the polciy 
    #cipher_suite = Fernet(key1)
#####################################################################################
   ##1. For register payload 
    str1='Register'
    file_hash1 = hashlib.sha256()
    file_hash1.update(str1.encode('utf-8'))
    str1digest1=file_hash1.hexdigest()
    str1digest12=bytes(str1digest1,'utf-8')
    cipher_text1 = cipher_suite.encrypt(str1digest12)
    #print("Shahbaz2",cipher_text1)
    plain_text = cipher_suite.decrypt(cipher_text1)
    if (plain_text==message1):
       print("Message Decrypyte=Register")       
########################################################################################################################################### 
 ##2. For certificate payload 
    file = 'C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/01-GetStarted/Keys/hash1.txt'
    # Location of the file (can be set a different way)
    BLOCK_SIZE = 65536 # The size of each read from the file

    file_hash = hashlib.sha256() # Create the hash object, can use something other than `.sha256()` if you wish
    with open(file, 'rb') as f: # Open the file to read it's bytes
      fb = f.read(BLOCK_SIZE) # Read from the file. Take in the amount declared above
      print(fb)
      while len(fb) > 0: # While there is still data being read from the file
         file_hash.update(fb) # Update the hash
         fb = f.read(BLOCK_SIZE) # Read the next block from the file
    certidigest1=file_hash.hexdigest()
    Singnedcert=bytes(certidigest1,'utf-8')
    cipher_text2 = cipher_suite.encrypt(Singnedcert)
    plain_text2 = cipher_suite.decrypt(cipher_text2)
    if (plain_text2==message2):
       print("CERTIFICATE Decrypyte VERIFICATION")       
####
########################################################################################################################################## 
 ##3. For SDNKEY payload 
    file = 'C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/01-GetStarted/Keys/hash2.txt' # Location of the file (can be set a different way)
    BLOCK_SIZE = 65536 # The size of each read from the file

    file_hash = hashlib.sha256() # Create the hash object, can use something other than `.sha256()` if you wish
    with open(file, 'rb') as f: # Open the file to read it's bytes
      fb = f.read(BLOCK_SIZE) # Read from the file. Take in the amount declared above
      print(fb)
      while len(fb) > 0: # While there is still data being read from the file
         file_hash.update(fb) # Update the hash
         fb = f.read(BLOCK_SIZE) # Read the next block from the file
    certidigest1=file_hash.hexdigest()
    sdnKEY=bytes(certidigest1,'utf-8')
    cipher_text2 = cipher_suite.encrypt(sdnKEY)
    plain_text3 = cipher_suite.decrypt(cipher_text2)
    if (plain_text3==message3):
       print("SDN key Decrypyte VERIFICATION")       
#########################################Before Fetching the rule verify the Public #########################





    
######################################################################################################################
    f1= open("C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/Service1/registration/"+str(i), "w")
    i=i+1
    str2hash = str(ipaddress.IPv6Address(ip_int))
    result = hashlib.md5(str2hash.encode())
    f1.write(str(ipaddress.IPv6Address(ip_int)))  
    s=result.hexdigest()          
    AccessKey1 = '4b3f3be75c6f95244604638f7d6918df'
    AccessKey2 = '1m2v1uwcdcrf060g73eo41y8yg8g580cne1yghol3a3k54m4n4q2l39k64b1&%20symbols=PKR' 
    hash_access_keys = hash(str(i) + AccessKey1 + AccessKey2) % ((sys.maxsize + 1) * 2)
    json = {'Hash Key': hash_access_keys}
    registerKeyJSON = {'Node' : str(hash(i)),'Service' : str(random.choice(mylist) ),'IPaddress' : str(ip_int)}
    registerKeyJSONString = str(registerKeyJSON).encode("utf-8").hex()
    strJsonHash = str(json).encode("utf-8").hex()
    
    
    
    
    
    
    now = datetime.now()
    current_time = now.strftime("%H:%M:%S")  
    current_time_seconds = time.time()
    #time.sleep(0.6) ##600 milisecond delay
    #time.sleep(0.12) ##120 milisecond delay
    #time.sleep(0.06) ##60 milisecond delay
    #time.sleep(0.03) ##30 milisecond delay
    #time.sleep(0.015) ##15 milisecond delay
    os.system("multichain-cli smartmeter publish Test1 " +  str(hash(i)) + " "+ registerKeyJSONString + strJsonHash)
   # os.system("multichain-cli service2 publish Registration " +  str(hash(i)) + " "+ registerKeyJSONString + strJsonHash)
  #  os.system("multichain-cli service2 publish Registration " +  str(hash(i)) + " "+ registerKeyJSONString + strJsonHash)
 #   os.system("multichain-cli service2 publish Registration " +  str(hash(i)) + " "+ registerKeyJSONString + strJsonHash)
    
    
    file = 'C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/01-GetStarted/Keys/Keys128/private-key1.pem'
         # Location of the file (can be set a different way)

    BLOCK_SIZE = 158 # The size of each read from the file second usecase important factor 
    with open(file, 'rb') as f1: # Open the file to read it's bytes
        fb = f1.read(BLOCK_SIZE) # Read from the file. Take in the amount declared above
        print(fb)
        while len(fb) > 0: # While there is still data being read from the file
          time.sleep(0.022)
          fb = f1.read(BLOCK_SIZE) # Read the next block from the file
    now21 = datetime.now()
    recieve_time = now21.strftime("%H:%M:%S")  
    recieve_time_seconds = time.time()#now.strftime("%H:%M:%S") 
    list2b.append(((recieve_time_seconds - current_time_seconds)*1000))
    #print(list2b) 
    Average=sum(list2b)/len(list2b)
    tuple_ind2 = (str(current_time), str(recieve_time), str(((recieve_time_seconds - current_time_seconds)*1000)),str(Average))
    str_file='C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/Service1/TestBed1Code-Registration/KeySubmission/128/2000/keySubmission.txt'
    f=open(str_file,"a")
    f.write(str(tuple_ind2[3])+'\n')
    f.close()
    #contract4.FetchingRegistrationPolicy(a) #FecthingRegistration Policy issi jaga main hai contract4 ki file main 

if __name__ == "__main__":
#########################################Send Encrypt message ############################################################

 key = Fernet.generate_key()
 cipher_suite = Fernet(key)
#####################################################################################
 ##1. For register payload 
 str1='Register'
 file_hash1 = hashlib.sha256()
 file_hash1.update(str1.encode('utf-8'))
 str1digest1=file_hash1.hexdigest()
 str1digest12=bytes(str1digest1,'utf-8')
 cipher_text1 = cipher_suite.encrypt(str1digest12)
 plain_text2 = cipher_suite.decrypt(cipher_text1)
  
##########################################################################################
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


import SecurityContractKeySubmission


for i in range(100):
 current_time = now.strftime("%H:%M:%S")  
 current_time_seconds = time.time()/10000   
 registration(str1digest12,Singnedcert,SDNKey2,key)
 now = datetime.now()
 #now.strftime("%H:%M:%S")
 SecurityContractKeySubmission.FetchingRegistrationPolicy("Service1")
 now2 = datetime.now()
   #Iss testbed main haumnai delay minus bhi karna hai relay ko fixed kardain 
         #Delay should be add becasue of interreltion 
 recieve_time = now2.strftime("%H:%M:%S")  
 recieve_time_seconds = time.time()/10000#now.strftime("%H:%M:%S")  
 list3b.append(((recieve_time_seconds - current_time_seconds)*1000))
         #print(list3b)
 Average=(sum(list3b)/len(list3b))
 tuple_ind2 = (str(current_time), str(recieve_time), str(((recieve_time_seconds - current_time_seconds)/1000)),str(Average))
 str_file='C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/Service1/TestBed1Code-Registration/KeySubmission/128/100/SecurityContract.txt'
 f=open(str_file,"a")
 print("Shahbaz")
 f.write(str(tuple_ind2[3])+'\n')







