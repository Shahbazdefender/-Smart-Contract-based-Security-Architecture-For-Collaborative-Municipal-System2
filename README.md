# Smart-Contract-based-Security-Architecture-For-Collaborative-Municipal-System

Please Read the help file that will help you to undertand the Usecase and Installation of the core technolgies 

# Contract-1 

**import sys

import json


with open("inputJSON.txt", "r") as json_file:

data = json.load(json_file)

   print(data)
firstKeyDataInput = list(data.keys())[0]

secondKeyDataInput = list(data.keys())[1]


class Shahbaz(KnowledgeEngine):

    Rule(Light(AND(firstKeyData == 'AirQualityIndex', secondKeyData == 'AirQualitySeverity')))
        
        def Context(self):
        
           print("Second Contract Passed")
        
engine = Execute()


# Contract-2

import json


with open("inputJSON.txt", "r") as json_file:

data = json.load(json_file)

totalNumberOfKeysFetched = len(data.keys())

print(totalNumberOfKeysFetched)
engine.declare(Light(numberOfKeysExpected=str(totalNumberOfKeysFetched)))
        
engine.run()

engine.declare(Light(firstKeyData=str(firstKeyDataInput), secondKeyData=str(secondKeyDataInput)))
        
engine.run()



# Contract-3


import json


with open("inputJSON.txt", "r") as json_file:

data = json.load(json_file)

totalNumberOfKeysFetched = len(data.keys())

print(totalNumberOfKeysFetched)

engine.declare(Light(numberOfKeysExpected=str(totalNumberOfKeysFetched)))
