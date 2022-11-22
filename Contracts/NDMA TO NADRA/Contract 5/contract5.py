from sabac import PDP, PAP, DenyBiasedPEP, deny_unless_permit
import sys
import json
import hashlib
import os
from ipaddress import IPv6Network
import random
import ipaddress

first = sys.argv[0]
second = int(sys.argv[1])

# Creating Policy Administration Point
pap = PAP(deny_unless_permit)



#Getting input from file as JSON


with open("inputJSON.txt", "r") as json_file:
    data = json.load(json_file)
    # Let's say you want to add the string "Hello, World!" to the "password" key
#    data["account"]["password"] += "Hello, World!"
    # Or you can use this way to overwrite anything already written inside the key
#    data["account"]["password"] = "Hello, World!"
    
print(data)

airQualityIndexValue = list(data.values())[0]
airQualitySeverity = list(data.values())[1]

if airQualityIndexValue > 300 and airQualitySeverity == "High":
	print('Contract 5 Success')
else : 
	print('Contract 5 Failed')
	
	
# Adding policy to PAP


item = {
    "description": "Admin permissions",
    "target": data,
    "algorithm": "DENY_UNLESS_PERMIT",
    'rules': [
        {
            "effect": "PERMIT",
            "description": "Allow to manage users",
            "target": {
                'resource.type': 'user',
                'action': {'@in': ['create', 'view', 'update', 'erase_personal_data', 'delete']},
            },
        }
    ]
}

pap.add_item(item)


print(item)

pdp = PDP(pap_instance=pap)

# Creating Policy Enforcement Point
pep = DenyBiasedPEP(pdp)





# Describing Policy Enforcement Point context
context = {
    'resource.type': 'user',
    'action': 'create',
    'AirQualityIndex': 200,
    'AirQualitySeverity': 'Low'
}

# Evaluating policy
result = pep.evaluate(context)

print(context)




start_ip = ipaddress.IPv6Address('fd00::')
end_ip = ipaddress.IPv6Address('fd00::38') 

i = 0 
arrIP = []
for ip_int in range(int(start_ip), int(end_ip)):
	i =+ 1 
	if i > 70:
		break
	arrIP.append(ip_int)
 

AccessKey1 = '4b3f3be75c6f95244604638f7d6918df'
AccessKey2 = '1m2v1uwcdcrf060g73eo41y8yg8g580cne1yghol3a3k54m4n4q2l39k64b1&%20symbols=PKR' 

#for i in range(5):
#	hash_access_keys = hash(str(i) + AccessKey1 + AccessKey2) % ((sys.maxsize + 1) * 2)


#	json = {'Data' : data,'Hash': hash_access_keys}
#	registerKeyJSONString = str(json).encode("utf-8").hex()


#	if (result):
#		print("Permission granted, Data shared with 	government agency.")
#		os.system("multichain-cli chain45 publish AirQualityDetails " +  "NDMAToNADRA" + " "+ registerKeyJSONString)
#	else:
#		print("Permission Denied, Data can't be shared with 	government agency.")
#
