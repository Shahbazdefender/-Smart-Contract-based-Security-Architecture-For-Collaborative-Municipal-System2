# -Smart-Contract-based-Security-Architecture-For-Collaborative-Municipal-System

Please Read the help file that will help you to undertand the Usecase and Installation of the core technolgies 

# -Contract-1 

import sys
import json
import hashlib
import os
from ipaddress import IPv6Network
import random
import ipaddress
from random import choice
from experta import *
with open("inputJSON.txt", "r") as json_file:
    data = json.load(json_file)
    print(data)
firstKeyDataInput = list(data.keys())[0]
secondKeyDataInput = list(data.keys())[1]

class Light(Fact):
    """Info about the Contract"""
    pass
class RobotCrossStreet(KnowledgeEngine):
    @Rule(Light(AND(firstKeyData == 'AirQualityIndex', secondKeyData == 'AirQualitySeverity')))
    def green_light(self):
        print("Second Contract Passed")
        
engine = RobotCrossStreet()
engine.reset()

engine.declare(Light(firstKeyData=str(firstKeyDataInput), secondKeyData=str(secondKeyDataInput)))
        
engine.run()
