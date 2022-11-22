from ipaddress import IPv6Network
import random
import ipaddress
import time 
import hashlib
from Savoir import Savoir
import os
import json
import time 

# Start from the ula address block
ula = IPv6Network("fd00::/8")
print('Please Enter the IoT node Devices Number')



i=1

start_ip = ipaddress.IPv6Address('fd00::')
end_ip = ipaddress.IPv6Address('fd00::38')
for ip_int in range(int(start_ip), int(end_ip)):
       
    f1= open(str(i), "x")
    i=i+1
    f = open('Node'+str(ipaddress.IPv6Address(ip_int)), "x")
    time.sleep(1)
    str2hash = str(ipaddress.IPv6Address(ip_int))
    result = hashlib.md5(str2hash.encode())
    f1.write(ipaddress.IPv6Address(ip_int))  
    f.write(result.hexdigest())  
    s=result.hexdigest()          
    f.close()
    print(ipaddress.IPv6Address(ip_int))
    f='{"name":"test","name1":"Shahbaz32","name2":"Shahbaz31","name5":"Shahbaz5"}'
    a=1
    KEY=f[0]
    Data = str(f).encode("utf-8").hex()
    #print(KEY)
 
print('Service Registration UUID')
str2hash = 'WhtherAPI'
result = hashlib.md5(str2hash.encode())
print(result)
      


# Get a random bitstring the size of the number of bits we can randomise.
# This is the number of bits reserved for the network (64) minus the number of bits
# already used in the address block we start from (8).
random_bits = random.getrandbits(64 - ula.prefixlen)

# Bitshift those bits 64 times to the left, so the last 64 bits are zero.
random_address_suffix = random_bits << 64

# Add those bits to the network address of the block we start from
# and create a new IPv6 network with the modified address and prefix 64
random_network = IPv6Network((
    ula.network_address + random_address_suffix,
    64))
print('Network Address')    
print(random_network)

rpcuser='multichainrpc'
rpcpassword='67WGBPc6dMkG2UjqcvNnxkGxFpFN9EHq1FP9nPXq7AZg'
rpcport = '4278'
rpchost = 'localhost'
chainname = 'chain4'
x=1
api = Savoir(rpcuser,rpcpassword,rpchost,rpcport,chainname)
#st = str('{"text":"hello world"}').encode("utf-8").hex()
 #os.chdir(r"")
f='{"name":"John","name1":"Shahbaz32","name2":"Shahbaz31","name5":"Shahbaz5"}'
a=1
KEY=f[0]
Data = str(f).encode("utf-8").hex()
#print(KEY)
 
os.system("multichain-cli chain4 publish Registration " + str(a) + " "+ Data)
 
x=0
print("Shabaz")
