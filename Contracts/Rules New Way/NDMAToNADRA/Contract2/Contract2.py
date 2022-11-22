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
    # Let's say you want to add the string "Hello, World!" to the "password" key
#    data["account"]["password"] += "Hello, World!"
    # Or you can use this way to overwrite anything already written inside the key
#    data["account"]["password"] = "Hello, World!"
    
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







