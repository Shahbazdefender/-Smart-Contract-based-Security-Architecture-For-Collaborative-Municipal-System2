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




# Adding policy to PAP


item = {
    "description": "Admin permissions",
    "target": {'datum': data},
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
    'datum':[{"name":"Abdul Karim Khan","cnic":"43302-1231231-1","dateOfExpiry":"28/12/2026"},{"name":"Abdul Sami Khan","cnic":"32321-1231231-2","dateOfExpiry":"28/12/2026"},{"name":"Ahmed Aqeel","cnic":"43012-7231331-3","dateOfExpiry":"28/12/2026"},{"name":"Ahmed Abdullah","cnic":"43012-3232193-3","dateOfExpiry":"12/12/2026"}]
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
 
i = 1

AccessKey1 = '4b3f3be75c6f95244604638f7d6918df'
AccessKey2 = '1m2v1uwcdcrf060g73eo41y8yg8g580cne1yghol3a3k54m4n4q2l39k64b1&%20symbols=PKR' 

for i in range(5):
	hash_access_keys = hash(str(i) + AccessKey1 + AccessKey2) % ((sys.maxsize + 1) * 2)

	json = {'Hash Key': hash_access_keys}
	registerKeyJSONString = str(data).encode("utf-8").hex()
	strJsonHash = str(json).encode("utf-8").hex()

	if (result):
		print("Permission granted, Data shared with government agency.")
		os.system("multichain-cli chain45 publish CNICDetails " +  "NADRAToPTA" + " "+ registerKeyJSONString + strJsonHash)
	else:
		print("Permission Denied, Data can't be shared with 	government agency.")

