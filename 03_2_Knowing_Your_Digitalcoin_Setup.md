# 3.2: Knowing Your Digitalcoin Setup

Before you start playing with Digitalcoin, you may always want to come to a better understanding of your setup.

## Know Your Digitalcoin Directory

To start with, you should understand where everything is kept: the `~/.digitalcoin` directory.

The main directory just contains your config file and the testnet directory:
```
$ ls ~/.digitalcoin
digitalcoin.conf  testnet3
```
The setup guides in [Chapter Two: Creating a Digitalcoin-Core VPS](02_0_Setting_Up_a_Digitalcoin-Core_VPS.md) laid out a standardized config file. [§3.1: Verifying Your Digitalcoin Setup](03_1_Verifying_Your_Digitalcoin_Setup.md) suggested how to change it to support more advanced setups. If you're interested in learning even more about the config file, you may wish to consult [Jameson Lopp's Digitalcoin Core Config Generator](https://jlopp.github.io/digitalcoin-core-config-generator/).

Moving back to your ~/.digitalcoin directory, you'll find that the testnet3 directory contains all of the guts:
```
$ ls ~/.digitalcoin/testnet3
banlist.dat   blocks	  debug.log	     mempool.dat	peers.dat
digitalcoind.pid  chainstate  fee_estimates.dat  onion_private_key	wallets
```
You shouldn't mess with most of these files and directories — particularly not the `blocks` and `chainstate` directories, which contain all of the blockchain data, and the information in your `wallets` directory, which contains your personal wallet. However, do take careful note of the `debug.log` file, which you should refer to if you ever have problems with your setup.

> :link: **TESTNET vs MAINNET:** If you're using mainnet, then _everything_ will instead be placed in the main `~/.digitalcoin` directory. These various setups _do_ elegantly stack, so if you are using mainnet, testnet, and regtest, you'll find that `~/.digitalcoin` contains your config file and your mainnet data, the `~/.digitalcoin/testnet3` directory contains your testnet data, and the `~/.digitalcoin/regtest` directory contains your regtest data.

## Know Your Digitalcoin-cli Commands

Most of your early work will be done with the `digitalcoin-cli` command, which offers an easy interface to `digitalcoind`. If you ever want more information on its usage, just run it with the `help` argument. Without any other arguments, it shows you every possible command:
```
$ digitalcoin-cli help
== Blockchain ==
getbestblockhash
getblock "blockhash" ( verbosity )
getblockchaininfo
getblockcount
getblockfilter "blockhash" ( "filtertype" )
getblockhash height
getblockheader "blockhash" ( verbose )
getblockstats hash_or_height ( stats )
getchaintips
getchaintxstats ( nblocks "blockhash" )
getdifficulty
getmempoolancestors "txid" ( verbose )
getmempooldescendants "txid" ( verbose )
getmempoolentry "txid"
getmempoolinfo
getrawmempool ( verbose )
gettxout "txid" n ( include_mempool )
gettxoutproof ["txid",...] ( "blockhash" )
gettxoutsetinfo
preciousblock "blockhash"
pruneblockchain height
savemempool
scantxoutset "action" ( [scanobjects,...] )
verifychain ( checklevel nblocks )
verifytxoutproof "proof"

== Control ==
getmemoryinfo ( "mode" )
getrpcinfo
help ( "command" )
logging ( ["include_category",...] ["exclude_category",...] )
stop
uptime

== Generating ==
generatetoaddress nblocks "address" ( maxtries )
generatetodescriptor num_blocks "descriptor" ( maxtries )

== Mining ==
getblocktemplate ( "template_request" )
getmininginfo
getnetworkhashps ( nblocks height )
prioritisetransaction "txid" ( dummy ) fee_delta
submitblock "hexdata" ( "dummy" )
submitheader "hexdata"

== Network ==
addnode "node" "command"
clearbanned
disconnectnode ( "address" nodeid )
getaddednodeinfo ( "node" )
getconnectioncount
getnettotals
getnetworkinfo
getnodeaddresses ( count )
getpeerinfo
listbanned
ping
setban "subnet" "command" ( bantime absolute )
setnetworkactive state

== Rawtransactions ==
analyzepsbt "psbt"
combinepsbt ["psbt",...]
combinerawtransaction ["hexstring",...]
converttopsbt "hexstring" ( permitsigdata iswitness )
createpsbt [{"txid":"hex","vout":n,"sequence":n},...] [{"address":amount},{"data":"hex"},...] ( locktime replaceable )
createrawtransaction [{"txid":"hex","vout":n,"sequence":n},...] [{"address":amount},{"data":"hex"},...] ( locktime replaceable )
decodepsbt "psbt"
decoderawtransaction "hexstring" ( iswitness )
decodescript "hexstring"
finalizepsbt "psbt" ( extract )
fundrawtransaction "hexstring" ( options iswitness )
getrawtransaction "txid" ( verbose "blockhash" )
joinpsbts ["psbt",...]
sendrawtransaction "hexstring" ( maxfeerate )
signrawtransactionwithkey "hexstring" ["privatekey",...] ( [{"txid":"hex","vout":n,"scriptPubKey":"hex","redeemScript":"hex","witnessScript":"hex","amount":amount},...] "sighashtype" )
testmempoolaccept ["rawtx",...] ( maxfeerate )
utxoupdatepsbt "psbt" ( ["",{"desc":"str","range":n or [n,n]},...] )

== Util ==
createmultisig nrequired ["key",...] ( "address_type" )
deriveaddresses "descriptor" ( range )
estimatesmartfee conf_target ( "estimate_mode" )
getdescriptorinfo "descriptor"
signmessagewithprivkey "privkey" "message"
validateaddress "address"
verifymessage "address" "signature" "message"

== Wallet ==
abandontransaction "txid"
abortrescan
addmultisigaddress nrequired ["key",...] ( "label" "address_type" )
backupwallet "destination"
bumpfee "txid" ( options )
createwallet "wallet_name" ( disable_private_keys blank "passphrase" avoid_reuse )
dumpprivkey "address"
dumpwallet "filename"
encryptwallet "passphrase"
getaddressesbylabel "label"
getaddressinfo "address"
getbalance ( "dummy" minconf include_watchonly avoid_reuse )
getbalances
getnewaddress ( "label" "address_type" )
getrawchangeaddress ( "address_type" )
getreceivedbyaddress "address" ( minconf )
getreceivedbylabel "label" ( minconf )
gettransaction "txid" ( include_watchonly verbose )
getunconfirmedbalance
getwalletinfo
importaddress "address" ( "label" rescan p2sh )
importmulti "requests" ( "options" )
importprivkey "privkey" ( "label" rescan )
importprunedfunds "rawtransaction" "txoutproof"
importpubkey "pubkey" ( "label" rescan )
importwallet "filename"
keypoolrefill ( newsize )
listaddressgroupings
listlabels ( "purpose" )
listlockunspent
listreceivedbyaddress ( minconf include_empty include_watchonly "address_filter" )
listreceivedbylabel ( minconf include_empty include_watchonly )
listsinceblock ( "blockhash" target_confirmations include_watchonly include_removed )
listtransactions ( "label" count skip include_watchonly )
listunspent ( minconf maxconf ["address",...] include_unsafe query_options )
listwalletdir
listwallets
loadwallet "filename"
lockunspent unlock ( [{"txid":"hex","vout":n},...] )
removeprunedfunds "txid"
rescanblockchain ( start_height stop_height )
sendmany "" {"address":amount} ( minconf "comment" ["address",...] replaceable conf_target "estimate_mode" )
sendtoaddress "address" amount ( "comment" "comment_to" subtractfeefromamount replaceable conf_target "estimate_mode" avoid_reuse )
sethdseed ( newkeypool "seed" )
setlabel "address" "label"
settxfee amount
setwalletflag "flag" ( value )
signmessage "address" "message"
signrawtransactionwithwallet "hexstring" ( [{"txid":"hex","vout":n,"scriptPubKey":"hex","redeemScript":"hex","witnessScript":"hex","amount":amount},...] "sighashtype" )
unloadwallet ( "wallet_name" )
walletcreatefundedpsbt [{"txid":"hex","vout":n,"sequence":n},...] [{"address":amount},{"data":"hex"},...] ( locktime options bip32derivs )
walletlock
walletpassphrase "passphrase" timeout
walletpassphrasechange "oldpassphrase" "newpassphrase"
walletprocesspsbt "psbt" ( sign "sighashtype" bip32derivs )

== Zmq ==
getzmqnotifications
```
You can also type `digitalcoin-cli help [command]` to get even more extensive info on that command. For example:
```
$ digitalcoin-cli help getmininginfo
...
Returns a json object containing mining-related information.
Result:
{
  "blocks": nnn,             (numeric) The current block
  "currentblocksize": nnn,   (numeric) The last block size
  "currentblocktx": nnn,     (numeric) The last block transaction
  "difficulty": xxx.xxxxx    (numeric) The current difficulty
  "errors": "..."          (string) Current errors
  "genproclimit": n          (numeric) The processor limit for generation. -1 if no generation. (see getgenerate or setgenerate calls)
  "networkhashps": n         (numeric) An estimate of the number of hashes per second the network is generating to maintain the current difficulty
  "pooledtx": n              (numeric) The size of the mem pool
  "testnet": true|false      (boolean) If using testnet or not
  "chain": "xxxx",         (string) current network name as defined in BIP70 (main, test, regtest)
  "generate": true|false     (boolean) If the generation is on or off (see getgenerate or setgenerate calls)
}

Examples:
> digitalcoin-cli getmininginfo 
> curl --user myusername --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getmininginfo", "params": [] }' -H 'content-type: text/plain;' http://127.0.0.1:9998/
```
> :book: ***What is RPC?*** `digitalcoin-cli` is just a handy interface that lets you send commands to the `digitalcoind`. More specifically, it's an interface that lets you send RPC (or Remote Procedure Protocol) commands to the `digitalcoind`. Often, the `digitalcoin-cli` command and the RPC command have identical names and interfaces, but some `digitalcoin-cli` commands instead provide shortcuts for more complex RPC requests. Generally, the `digitalcoin-cli` interface is much cleaner and simpler than trying to send RPC commands by hand, using `curl` or some other method. However, it also has limitations as to what you can ultimately do.

## Optional: Know Your Digitalcoin Info

A variety of digitalcoin-cli commands can give you additional information on your digitalcoin data. The most general ones are:
```
$ digitalcoin-cli getblockchaininfo
$ digitalcoin-cli getmininginfo
$ digitalcoin-cli getnetworkinfo
$ digitalcoin-cli getnettotals
$ digitalcoin-cli getwalletinfo
```
For example `digitalcoin-cli getnetworkinfo` gives you a variety of information on your setup and its access to various networks:
```
$ digitalcoin-cli getnetworkinfo
{
  "version": 5000300,
  "subversion": "/Digitalcoin Core:5.0.3/",
  "protocolversion": 70208,
  "localservices": "0000000000000005",
  "localrelay": true,
  "timeoffset": 0,
  "networkactive": true,
  "connections": 8,
  "networks": [
    {
      "name": "ipv4",
      "limited": false,
      "reachable": true,
      "proxy": "",
      "proxy_randomize_credentials": false
    }, 
    {
      "name": "ipv6",
      "limited": false,
      "reachable": true,
      "proxy": "",
      "proxy_randomize_credentials": false
    }, 
    {
      "name": "onion",
      "limited": true,
      "reachable": false,
      "proxy": "",
      "proxy_randomize_credentials": false
    }
  ],
  "relayfee": 0.00001000,
  "localaddresses": [
  ],
  "warnings": ""
}
```
Feel free to reference any of these and to use "digitalcoin-cli help" if you want more information on what any of them do.

## Summary: Knowing Your Digitalcoin Setup

The `~/.digitalcoin` directory contains all of your files, while `digitalcoin-cli help` and a variety of info commands can be used to get more information on how your setup and Digitalcoin work.

## What's Next?

Continue "Understanding Your Digitalcoin Setup" with [§3.3: Setting Up Your Wallet](03_3_Setting_Up_Your_Wallet.md).
