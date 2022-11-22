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

import contract51






def registration(message1,message2,message3,key1):
 now0 = datetime.now()
 current_time0 = now0.strftime("%H:%M:%S")  
 current_time_seconds0 = time.time()#now.strftime("%H:%M:%S")      
        
   
 

#Here The Node are going to Registrered
 mylist = ["Service1","Service2"] 
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
    #print("Shahbazbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb") 
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
    file = 'C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/01-GetStarted/Keys/hash1.txt' # Location of the file (can be set a different way)
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

    now = datetime.now()
    current_time = now.strftime("%H:%M:%S")  
    current_time_seconds = time.time()#now.strftime("%H:%M:%S")  

######################################################################################################################
    
    contract51.FetchingRule(a) 
    path ="/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/01-GetStarted/Registration/"
    Servicename='service1' 
    contract51.AuthorizationFind(path,Servicename)
   
    #FecthingRegistration Policy issi jaga main hai contract4 ki file main 
    f1= open("C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/Service1/registration/"+str(i), "w")
    i=i+1
#    f = open('C:/Users/Anabia/Downloads/Shahbaz_Final_Project-2/Shahbaz_Final_Project/Ada-Framework-Shahbaz1/Service1/registration/IP'+str(ipaddress.IPv6Address(ip_int)), "w")
    str2hash = str(ipaddress.IPv6Address(ip_int))
    result = hashlib.md5(str2hash.encode())
    f1.write(str(ipaddress.IPv6Address(ip_int)))  
 #   f.write(result.hexdigest())  
    s=result.hexdigest()          
    #print("Au Ganduu")
    AccessKey1 = '4b3f3be75c6f95244604638f7d6918df'
    AccessKey2 = '1m2v1uwcdcrf060g73eo41y8yg8g580cne1yghol3a3k54m4n4q2l39k64b1&%20symbols=PKR' 
    hash_access_keys = hash(str(i) + AccessKey1 + AccessKey2) % ((sys.maxsize + 1) * 2)
    json = {'Hash Key': hash_access_keys}
    registerKeyJSON = {'Node' : str(hash(i)),'Service' : str(random.choice(mylist) ),'IPaddress' : str(ip_int)}
    registerKeyJSONString = str(registerKeyJSON).encode("utf-8").hex()
    strJsonHash = str(json).encode("utf-8").hex()
    #print("PAPUUUUUUUUUUUUUUUUUUUUU",registerKeyJSONString)
    os.system("multichain-cli chain3 publish Registration " +  str(hash(i)) + " "+ registerKeyJSONString + strJsonHash)
    #yahan per wo code add karna hai First Decode the code 
    os.system("multichain-cli service2 publish Registration " +  str(hash(i)) + " "+ registerKeyJSONString + strJsonHash)
     #two time mean to add some time 
    os.system("multichain-cli service2 publish Registration " +  str(hash(i)) + " "+ registerKeyJSONString + strJsonHash)
    
    
    
    
    
    
    

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




for i in range(1000):

 registration(str1digest12,Singnedcert,SDNKey2,key)
 
 





