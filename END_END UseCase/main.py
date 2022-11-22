# imports
import json
from web3 import Web3
from solcx import compile_source

# # For solidity
# install_solc(version='latest')

# Solidity source code
compiled_sol = compile_source(
     '''
     pragma solidity >0.5.0;

     contract Greeter {
         string public greeting;

         constructor() public {
             greeting = 'Hello';
         }

         function setGreeting(string memory _greeting) public {
             greeting = _greeting;
         }

         function greet() view public returns (string memory) {
             return greeting;
         }
     }
     ''',
     output_values=['abi', 'bin']
 )

# retrieve the contract interface
contract_id, contract_interface = compiled_sol.popitem()

# get bytecode / bin
bytecode = contract_interface['bin']

# get abi
abi = contract_interface['abi']
open(f'meta.txt', 'w').write(json.dumps(abi))

# web3.py instance
w3 = Web3(Web3.EthereumTesterProvider())
print(w3)
w3 = Web3(Web3.HTTPProvider('http://127.0.0.1:7545'))
print(w3)

# set pre-funded account as sender
for i in range(0, 4):
    w3.eth.default_account = w3.eth.accounts[i]
    f = open(f'{w3.eth.default_account}.txt', 'w')

    f.write(f'{w3.eth.default_account}\n\n')

    Greeter = w3.eth.contract(abi=abi, bytecode=bytecode)

    # Submit the transaction that deploys the contract
    tx_hash = Greeter.constructor().transact()

    # Wait for the transaction to be mined, and get the transaction receipt
    tx_receipt = w3.eth.wait_for_transaction_receipt(tx_hash)

    greeter = w3.eth.contract(address=tx_receipt.contractAddress, abi=abi)
    f.write(greeter.functions.greet().call())

    tx_hash = greeter.functions.setGreeting('Nihao').transact()
    f.write(f'{tx_hash}\n\n')

    tx_receipt = w3.eth.wait_for_transaction_receipt(tx_hash)
    f.write(f'{tx_receipt}\n\n')

    f.write(f'{greeter.functions.greet().call()}\n\n')
    
    f.close()
