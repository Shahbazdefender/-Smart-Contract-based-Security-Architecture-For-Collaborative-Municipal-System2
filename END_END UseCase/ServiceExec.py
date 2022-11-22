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

import contract5


def service_program():
    now0 = datetime.now()
    current_time0 = now0.strftime("%H:%M:%S")  
    current_time_seconds0 = time.time()#now.strftime("%H:%M:%S")      
 
    mylist = ["Service1","Service2"] 
    a=str(random.choice(mylist))
    contract5.FetchingRule(str(random.choice(mylist) ))
    now2 = datetime.now()
    recieve_time = now2.strftime("%H:%M:%S")  
    recieve_time_seconds = time.time()#now.strftime("%H:%M:%S")  
 #Log for Final Reistration time of Service having Number of Nodes#
    tuple_ind2 = (str(current_time0), str(recieve_time), str(recieve_time_seconds - current_time_seconds0))  
    str_file='/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/'+a+'/registration/Message.txt'
    f=open(str_file,"a")
    f.write(str(tuple_ind2)+'\n')
       
       


if __name__ == '__main__':
    service_program()
