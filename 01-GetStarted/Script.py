from Savoir import Savoir
import os
import json

rpcuser='multichainrpc'
rpcpassword='5x9LTKCDatzQWdtTG1VwWQgBQhFZ5Ks4FiRQ5TdGit1p'
rpcport = '6446'
rpchost = 'localhost'
chainname = 'chain1'

api = Savoir(rpcuser,rpcpassword,rpchost,rpcport,chainname)
#st = str('{"text":"hello world"}').encode("utf-8").hex()
#os.chdir(r"")


for filename in os.listdir('D:\\FYP\\API Check\\VinDataFiles\\'):
    if filename.endswith('.json'):
        with open(os.path.join('D:\\FYP\\API Check\\VinDataFiles\\', filename)) as f:
            data = json.load(f)
            KEY  = data['VIN']
            Data = str(data).encode("utf-8").hex()
            print(KEY)
            os.system("D:\\BlockchainMultichain\\multichain-cli chain1 publish vinData " + KEY + " "+ Data)

