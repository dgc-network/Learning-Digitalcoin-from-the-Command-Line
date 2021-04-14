# 3.4: Receiving a Transaction

You're now ready to receive some money at the new address you set up.

## Get Some Money

To do anything more, you need to get some money. On testnet this is done through faucets. Since the money is all pretend, you just go to a faucet, request some money, and it will be sent over to you. We suggest using the faucet at https://testnet-faucet.mempool.co/, https://bitcoinfaucet.uo1.net/, or https://testnet.coinfaucet.eu/en/. If they're not available for some reason, search for "digitalcoin testnet faucet", and you should find others.

To use a faucet, you'll usually need to go to a URL and copy and paste in your address. Note that this is one of those cases where you won't be able to use command-line variables, alas. Afterward, a transaction will be created that sends money from the faucet to you.

> :book: ***What is a transaction?*** A transaction is a digitalcoin exchange. The owner of some digitalcoins uses his private key to access those coins, then locks the transaction using the recipient's public key.

> :link: **TESTNET vs MAINNET:** Sadly, there are no faucets in real life. If you were playing on the mainnet, you'd need to go and actually buy digitalcoins at a digitalcoin exchange or ATM, or you'd need to get someone to send them to you. Testnet life is much easier.

## Verify Your Money

After you've requested your money, you should be able to verify it with the 'digitalcoin-cli getbalance' command:
```
$ digitalcoin-cli getbalance
0.00000000
```
But wait, there's no balance yet!?

Welcome to the world of Digitalcoin latency. The problem is that your transaction hasn't yet been recorded in a block!

> :book: ***What is a block?*** Transactions are transmitted across the network and gathered into blocks by miners. These blocks are secured with a mathematical proof-of-work, which proves that computing power has been expended as part of the block creation. It's that proof-of-work (multiplied over many blocks, each built atop the last) that ultimately keeps Digitalcoin secure.

> :book: ***What is a miner?*** A miner is a participant of the Digitalcoin network who works to create blocks. It's a paying job: when a miner successfully creates a block, he is paid a one-time reward plus the fees for the transactions in his block. Mining is big business. Miners tend to run on special hardware, accelerated in ways that make it more likely that they'll be able to create blocks. They also tend to be part of mining pools, where the miners all agree to share out the rewards when one of them successfully creates a block.

Fortunately, `digitalcoin-cli getunconfirmedbalance` should still show your updated balance as long as the initial transaction has been created:
```
$ digitalcoin-cli getunconfirmedbalance
0.01010000
```
If that's still showing a zero too, you're probably moving through this tutorial too fast. Wait a second. The coins should show up unconfirmed, then rapidly move to confirmed. Do note that a coin can move from unconfirmedbalance to confirmedbalance almost immediately, so make sure you check both. However, if your `getbalance` and your `getunconfirmedbalance` both still show zero in ten minutes, then there's probably something wrong with the faucet, and you'll need to pick another.

### Gain Confidence in Your Money

You can use `digitalcoin-cli getbalance "*" [n]`, where you replace `[n]` with an integer, to see if a confirmed balance is 'n' blocks deep.

> :book: ***What is block depth?*** After a block is built and confirmed, another block is built on top of it, and another ... Because this is a stochastic process, there's some chance for reversal when a block is still new. Thus, a block has to be buried several blocks deep in a chain before you can feel totally confident in your funds. Each of those blocks tends to be built in an average of 10 minutes ... so it usually takes about an hour for a confirmed transaction to receive six blocks deep, which is the measure for full confidence in Digitalcoin.

The following shows that our transactions have been confirmed one time, but not twice:
```
$  digitalcoin-cli getbalance "*" 1
0.01010000
$  digitalcoin-cli getbalance "*" 2
0.00000000
```
Obviously, every ten minutes or so this depth will increase.

Of course, on the testnet, no one is that worried about how reliable your funds are. You'll be able to spend your money as soon as it's confirmed.

## Verify Your Wallet

The `digitalcoin-cli getwalletinfo` command gives you more information on the balance of your wallet:
```
$ digitalcoin-cli getwalletinfo
{
  "walletversion": 61000,
  "balance": 0.00000000,
  "unconfirmed_balance": 0.00000000,
  "immature_balance": 0.00000000,
  "txcount": 1,
  "keypoololdest": 1616746949,
  "keypoolsize": 999,
  "keys_left": 970,
  "unlocked_until": 0,
  "paytxfee": 0.00000000
}
```

## Discover Your Transaction ID

Your money came into your wallet via a transaction. You can discover that transactionid (txid) with the `digitalcoin-cli listtransactions` command:
```
$ digitalcoin-cli listtransactions
[
  {
    "address": "mi25UrzHnvn3bpEfFCNqJhPWJn5b77a5NE",
    "category": "receive",
    "amount": 0.01000000,
    "label": "",
    "vout": 1,
    "confirmations": 1,
    "blockhash": "00000000000001753b24411d0e4726212f6a53aeda481ceff058ffb49e1cd969",
    "blockheight": 1772396,
    "blockindex": 73,
    "blocktime": 1592600085,
    "txid": "8e2ab10cabe9ec04ed438086a80b1ac72558cc05bb206e48fc9a18b01b9282e9",
    "walletconflicts": [
    ],
    "time": 1592599884,
    "timereceived": 1592599884,
    "bip125-replaceable": "no"
  },
  {
    "address": "mi25UrzHnvn3bpEfFCNqJhPWJn5b77a5NE",
    "category": "receive",
    "amount": 0.00010000,
    "label": "",
    "vout": 0,
    "confirmations": 1,
    "blockhash": "00000000000001753b24411d0e4726212f6a53aeda481ceff058ffb49e1cd969",
    "blockheight": 1772396,
    "blockindex": 72,
    "blocktime": 1592600085,
    "txid": "ca4898d8f950df03d6bfaa00578bd0305d041d24788b630d0c4a32debcac9f36",
    "walletconflicts": [
    ],
    "time": 1592599938,
    "timereceived": 1592599938,
    "bip125-replaceable": "no"
  }
]

```
This shows two transactions (`8e2ab10cabe9ec04ed438086a80b1ac72558cc05bb206e48fc9a18b01b9282e9`) and (`ca4898d8f950df03d6bfaa00578bd0305d041d24788b630d0c4a32debcac9f36`) for a specific amount (`0.01000000` and `0.00010000`), which were both received (`receive`) by the same address in our wallet (`mi25UrzHnvn3bpEfFCNqJhPWJn5b77a5NE`). That's bad key hygeine, by the way: you should use a new address for every single Digitalcoin you ever receive. In this case, we got impatient because the first faucet didn't seem to be working.

You can access similar information with the `digitalcoin-cli listunspent` command, but it only shows the transactions for the money that you haven't spent. These are called UTXOs, and will be vitally important when you're sending money back out into the Digitalcoin world:
```
$ digitalcoin-cli listunspent
[
  {
    "txid": "3a3fd685d7655ed7510025d85a6c7dfa9ce6723a8d27a0a066b4a777c55c6e0b",
    "vout": 0,
    "address": "D9txbZZTvyfHDMXn7yq2eK4sprZMJAUdWB",
    "account": "",
    "scriptPubKey": "76a914342c2cc8cd3c58bdfb905189068b3538a2fad8ab88ac",
    "amount": 1958.95336375,
    "confirmations": 2078314,
    "ps_rounds": -2,
    "spendable": false,
    "solvable": false
  }
]
```
Note that digitalcoins are not just a homogeneous mess of cash jammed into your pocket. Each individual transaction that you receive or that you send is placed into the immutable blockchain ledger, in a block. You can see these individual transactions when you look at your unspent money. This means that digitalcoin spending isn't quite as anonymous as you'd think. Though the addresses are fairly private, transactions can be examined as they go in and out of addresses. This makes privacy vulnerable to statistical analysis. It also introduces some potential non-fungibility to digitalcoins, as you can track back through series of transactions, even if you can't track a specific "digitalcoin".

> :book: ***Why are all of these digitalcoin amounts in fractions?*** Digitalcoins are produced slowly, and so there are relatively few in circulation. As a result, each digitalcoin over on the mainnet is worth quite a bit (~ $9,000 at the time of this writing). This means that people usually work in fractions. In fact, the .0101 in Testnet coins would be worth about $100 if they were on the mainnet. For this reason, names have appeared for smaller amounts of digitalcoins, including millidigitalcoins or mDGCs (one-thousandth of a digitalcoin), microdigitalcoins or bits or μDGCs (one-millionth of a digitalcoin), and satoshis (one hundred millionth of a digitalcoin).

## Examine Your Transaction

You can get more information on a transaction with the `digitalcoin-cli gettransaction` command:
```
$ digitalcoin-cli gettransaction "3a3fd685d7655ed7510025d85a6c7dfa9ce6723a8d27a0a066b4a777c55c6e0b"
{
  "amount": 0.00000000,
  "confirmations": 2078373,
  "instantlock": false,
  "blockhash": "00000000000001a219e43df3dd0bfb6d62f0a5eb91134870d66342a486957585",
  "blockindex": 1,
  "blocktime": 1515011214,
  "txid": "3a3fd685d7655ed7510025d85a6c7dfa9ce6723a8d27a0a066b4a777c55c6e0b",
  "walletconflicts": [
  ],
  "time": 1515011214,
  "timereceived": 1616750224,
  "bip125-replaceable": "no",
  "details": [
  ],
  "hex": "010000003d7120f693d2b993a5311abfc25167a9aab4e6bb959c58b70d2204d80eb16e1f0e010000006b48304502210090cbe40c73423339040f523870abe42118d77c8b931f45053c3812d08c5a781a02203591f06b1fa7875515176272b54741765d95cae8646f56ad30a7c3f497f024d80121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffffacdd6683568e1eb3173d829aec559b672ed93c5bcd9a8f7efb561cda11c65525000000006b483045022100efefc15c759fe1eb64e71d08b68aae5bb6e047f369b436c5cf376955a83b6a2b0220332aca115dc9a4ed36f2c8bb3e9987409154800844d3d637d94df1154faf28980121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffffff1ceaca4a8514d52c55f1f125ba1172b0c06803aafa91abbb046550b8b864f7000000006b483045022100bd430f70fda8c1c8004f648acdeaecc05c80af51c5c207054a6a366928a31eb5022078dd941054bc8379abf79aac8448f66d36ebcaafcc564a3c0d158950fb3c019f0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff2c8df888ef63d5e3b60f09be14b6ba676752ada3562899d6302b349dc63d81f0010000006b483045022100c93ec1758f1add78a6c2a1a4ad83ae9557a3fbcb1a6b831ebe69f84863e4cdbf022015750f45aabfca93e65e5f6fc10d7fe14b7542a799bf0143fbdf2c5292cfb01f0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff4433e9aeeb6666d3c7417b5007cae91340d375d50a89ac0b79926ae8ecfd654c010000006a473044022059232dc7fd4d9671f5e5b8ef1b1682e01d2bd8d76487e71773fea16db5775c12022051e7b34a6694a72b56ba4feb21bc095ac4975d31af776798c3868482f12c91400121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8fffffffff7f497af33a6cbb686cde9562d5d56e3a6971228a3b934d40cc441fda7c92d17010000006b483045022100ce60a7640e1fd3d3eab0cbe61ff8e7acba7555340d89df0d5a3a823573b3864d0220353f329d2223d4bdc2a3b86dd05ccd5b1eca3b7a65d969e2f2a17e61731dc4880121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffffd5d047af7e555f6d3643e24464a3b03b04b0550ce2c06c7713af7c8d145d5fca000000006b48304502210086b3052c77d25d15866365a74ec2d6a72bcf23b15076177bef2d029d8ac6d21b02200aec609ec05b5b7daa798aad6af6d0d0d522d9d05a66e65a3c36caf52f3ea0910121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff46c4d45cd2f6e66102c87e78de2745d4459b3f567a6e2869eb23391009d1c0c7010000006a473044022075c6da32b50c2571d24b67c9c2c1f11dcf85f0d2b8cdff14b68983a89501def202202ce9fb6d1ad011fbf37c04ad1961182f06ad03ea57b5212aef6bf71c48a6b97d0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8fffffffff40fe3ef940b332bc8d86d51501b169220b4e4f02c0671671f77792a162bb4d0000000006a47304402202c3fc69055fd189fc01d4b85e4a5d6acf4e4869ad2411aebd97ae377877ebae802205ddb132cf7bd9a0ae49b59ea903cbb2946f3296785ffd6ef77103129b23190850121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffffa3bbe4024a841e4313c1e245842857e1bdb1c276a77aabfec3677d071a8111fa010000006a4730440220171dd159b2ebd60f8bf8c278896b5d4c0ffc81bce444973fb7168fb90b86960202205b380ca254ccf60b48b9cfb4daf9f16db93f1766cc73a5df3f8016226ba542f801210221cbb57963c7d4a55e62bedc15a5e1eb9ccaa4760454b69c162f9c3ee928f795ffffffff12bc63bc74283264e5720733d52f3527d5284ab3f33f2454630aff15deb15561010000006b483045022100c62268e1f4087681cb9a46a6c4595fa2e46d61a39002512e33ec82e7a736e1670220781360364925ebbb53df715e57777514d4fdd5f7dcfef6a91c06d508fcd74fa30121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff64acdb22bfb0f6900d493af7432927c9e28eb40e9dc9734d4c465267b4812c58010000006a473044022012233b24a7797b82a47bed4295cfc1edf52ae44935528513ec814ba52cb39fc50220442894482b3adf35ca94010b8383bd1d1589006838cafcab74d5c98b86da128f0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff60fb0c4860bb1c5b723db50b693cd1ae2c677f6a3cfffa1e6728c2d97578f0b6010000006a47304402203bc1b29f617c00dae37d853ab666aa72bc4986d0b73605afd5eb2d794d242a8402205b5c260c3ce68b0b1163cdaf7453d4d5aac21899dc5636cd628e282730516cd00121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffffb5640700acf7de7133e73d6053e92ccbdd410d6d3ad1879b36a7ccf5aad93170000000006b483045022100b9b30c7ce84c58a2adb877b67e341110defc1d9f52aca2f8781a56a1d097d3b902201e5e9e2a82e6d08a63d51333b3d2498ebaf8cbc65699baa722a48c4439c9e3540121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffffc0db8fa70cf31c7a2b66b5e13d0495c2c8c7a44074df2e3fa497b41c7cceb3dd000000006b483045022100f9769fdeae6c36afbf88fe9397766d44b9ca7411d20eed44dc3783b788d00f4e022053363c21ee1d5331a32eee60429716d4163d03519f859615d62184dedeec4c850121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff4424f2e055c83a6dacae775850d52776bd0fb4500d0945869e7a0b7136f5b36f000000006a47304402206f9017c5225d9eefa645022bb6b7b4a31f27341cecfc721aca981e47271da03602201dabb4ba8df505cfcce54595d2d3877294bc84d16c7d9941508c732472442c220121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff2ea8c5392ffcdc8a2a2ed3b06c0053e8f232afc530d2f47170ede97b415b1e60000000006b483045022100e5711a51cbade9d9076755647e7b251a098fd8fda1a435b945f74adab1444fde02204f179c3f7a07f45a0fa7936a3f7f13d9fd1b5e72944dbc7b30e7714b21de4a660121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff900c3ec10b867f2ed3deb54fe63cbd1b97c76a884e4635f5e2a7e831c56d119c000000006b483045022100f38de17c08bfadd7bb39baec5f24b1db8c9eebf76fa80905034687e817c1c41002207cbeacc6b02f41b061b03e73b41311ac7e3ce87c15c641b8bfb59bb9678a9ec80121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff7a628842eccbf82d83a1e92017296d6caacede7105ccc005405940bf8502cf94000000006a47304402204a5cd5096f2ef5afb7514d6f2a5be6f889c9a54d7dbf3d6e9ed171f01ea2199302207eec8fba1b8a4403bcd74c5570a8f6f82da001bb90b2534efe3d5f467df67bc20121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff6aa639ffed0e0a60fd6c65522bc2df881f2e6daa4666e4738a6a231b3a7cfd1b000000006b483045022100ec15bb232e2b05706dc1bf71fbc41bc94c729b354752a26a0ecbfcb4c138e87902207f997985b19eb6899796354a1429a5e4d6c897c659e4a140a111a916bb5b452f0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffffe4e2b3c29079f274aa759b124176e40f85400e31e634aba05d3e9a041f94c754010000006b483045022100fa0d0c6daa98cb1073e26006c3070654b146424a3b5957ee931c5e6be33c1c3c02204f2b4480d147594d33411a9e7dd1db6c4884eb0328d1ed8e771a42d83914d1460121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff83cbdcce2cef81cc3ae59c869dc409467599a2212eb86668ae4c989bb9500ca2010000006a473044022001b23dd2479eb0a3afde0cc85f81c75025c468b0241537cc1eb4720d886c58f30220444a954c439d35cd04a5a61c1477509b9b32b12d4dbf65c962f7930c1525712f0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffffc95b07b3365b46400211f57bf97af1ba7906c9f1160bcb5d406cd6646a010039010000006b483045022100887afc38c10ddcc8c4585c541a7a1690f27b9526f435068f61eb3dc6e46d6a7c022040aa1b35cb862e08f4999cc7f26bb9950d998446433b9129245a73d97dd618440121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8fffffffff3043c0fd03da13eec0fd105088e7f3914b8c3c94c73c2956af5ce4ddb0a3bb0000000006a4730440220043d98d03341fb3f35fb7d5ecce46db166b490e44a34bfed847a39435b5477ca02200b809051e96f1167431519f82fa2cbbd7a11978c77726c39baf620fbfcc55b090121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff6cf3d6dcca0a5a263aa9dc2e58be04db83d513ed11c2d36708e96607a5eeb2c5000000006a47304402203b6394cc7e7f31b9128035912bda7f7048a2a13a8c5b053b5905477b8696a5f602207f50a654346e5bc722fdfdcc2e647e1f111932c19d9370e97503ff8c7604090d0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8fffffffff73c9a1eed517eff60232ddeb9d6720d41391a092bd9bb8f94781b878f2eac24010000006a47304402206c3624beb97a87120c9c10e1c56d87ba2b779e80d35ff1c5ef25061d73ca4d0e022059aec4e62f1112e8723f708a3d48f6a236ff78b94eebb008c409f7a23f1063680121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff42f2d498a2e8b8bc8c0b3334dfd496e811b4d9815273189421fa35cb12b4f6e9000000006a47304402205d3b1f292e320b44c3036fe1b38b0d486b57ec7bac5cd43e9f74c3258b8c41b9022025d13e2dc7d353b01913bff758568421544d01938add0974cedc9ccfad1201270121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff9c64a6209f677d7d175615f54ae7b9a91bccaef0ac1605f9f301521df960fb1d010000006a4730440220793e784a72dccb3ac8ba5cf4de93711ab08fe8df868be2c9ff369f876d5b998f02203091141dd648324ecf77b7a2802207f6b54668ed88477931f88af3be7bc48d990121031fb23e78a54973802138bdf9d800c395577b829d3facd39e6d9b6589dd95e036ffffffff4a3611a7b7a882719f1a57b96535bd49d3f29f9472c7b8a1eb22ba2a26764d17000000006a473044022048dfb4d1c1185ed56e02d2713ddee83098fcd1c05b52e37e3bff16b8d08fbc2f022043cf80fa020ae72ccf341b79117166615620593bab52161173cdf09c16c3effc0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff07da1ae4c543d2edffe2256b71ab9c2bc97918df2538c1c987e471d34bd127b5000000006b483045022100d1202e989027e4dd8bae92d2ed3766f81db2061cfd973fce3fa3bd7659a3950c02200e9672456e4cead0f17c54851e4df634b6fb20a3156705f87d3b8959113358ef0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffffeaf2b81c3fa0a70ad24a2376020eb0f7ee540f2e5c7e78cfe03bae041ea5d3f4010000006b4830450221009efeb9640f3a6e02228f75ca384e721df1ce23f4c1804ec7a7049676ec60fe69022052fd8d99a06ee735dd6f02f3b1290df2621fb12c8c9b2613c7afa3258fb149b80121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff395dc814c18af5a63e4ff30d20da136d57f7a068bd81c84c5ff398e98fe26ceb010000006b483045022100a5f8e983ec18a9faec47ddc100dc8d21b856fe6ff128394e9f576415ef4a7b020220312ddeb4e2d33c0e5c607760658c92a9daa5d9f4b1c047855aa217e4e653794d0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff9e97a8a0ce376e5be76411d5636ead24ad5e48027712ef3faa5c688ad6cb6110000000006a47304402204e641ac7ebd7f97536290171a8737714220a3e1bc89714bf4a0b2210c62beda502203de647c236ff38675ae214a1a833a68a1052f358c9f9ae82863b5ea4ec6929b80121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffffc8c0cb018bf0629d9df656f49098fef69c8bdfe948e52bd13a6509338d96d76f000000006b4830450221009d4481b5ab0d84fccbde343b70ad8a8c9eb364f03a27fea2fcec8b9b9197ad0c02202ccfab192fda4c9ba647ce292e0e75ae4dafed0fa26bc37cc887c0f1da489f2d0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffffac4e64f201ec91688b22af0d5a54046b811e0aadeac08bd28e5c59bb96a6b5b6010000006b483045022100e11370c3f1ea26fc47bfc7e6f7e17e1d91d892d2797747f2f2989cf0b5c6c8ad0220419abe19163bbf3314052e293faea6b8f01116e9047ed879ff84f6740c5044370121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff1c0db772aa2d8c978c76f5217bbf484eda40270e7a85b419aac7100ce2f422b5010000006b483045022100f91e7268fe333a333e67bebf108c95a403f43e40f2d905a97cae8dfb343c7ecf02201302dc6438f0a8414b82f9344ebc8f08eefe3934702565723f3ded53c08e4c3f01210221cbb57963c7d4a55e62bedc15a5e1eb9ccaa4760454b69c162f9c3ee928f795ffffffff06888022c344124e9b5a5b3668a45de6adfabacffbf56e105f98ef832d48f666010000006a4730440220116c9b1a64479d0862c57dd36f58c0fc7f7f1727856279bb9aa343febd2bbae80220799e0cfe8917469d4aa553313eaab5952238a7b629690f2dbd004fcb1bedd78c0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffffa412b14fe577f899d8f06fa9af0f7821ca3f4ea932e75f9752ee22c10ef9a039010000006a47304402206fee11f078989e556999ec6bcac09a490d7f6a098c57fb921a5f10b326024a89022075157d0e2151b741ce14cc921bd03c0f16e3eae8c70def7a0f184d8706096bd70121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff5574e95db3e29eacb8678fef82ae312b1d65ba9b9b6111822493f50c065f6b69000000006b483045022100f422a11f522a05b221714b2d9a1d88392271ae27ee088f854ff01a284981bb1602207f9518fabb887f17f96ab03aa56e1dd7a5b5db7696bb9f37842f09be56ae9b4c0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff177c86efcc68f97774883229de689b76ca61b7d97851e1655add8e6a03e5a491010000006a473044022041036c7753c7546ec6e26c17291d24397000ca6a43f2f2f637469377df98f46b02200546b642aceda66cebf3f395b3197c5d0adaea78b8129af86690e64476159ebd0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff30d9dcbf5fcdf83375a2926d6b5ded240d99a36c31555c1a272dc4d835a4507c000000006a47304402205152a8f5d5ddf44cd67f203923f863a51673b20590a75e84fd9de16ad45f0b9c02203f816401cb0c7cc821aa2320c31c53225d333ced54a8845e3389fa0ea6547be10121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff9b1c3a3b08b6c3e8cc35bb1f674446031b0bec21de1c89c20b8aea05a54f530d010000006b483045022100b29ab2a510241d12a6848e5621f9bb755e085b23a737d7d8ec2091109696acd602204b0fce3dd1839f3793fdf03847912ac9a9c7008d61b1f89278ff77c2ee474c0a0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff5cd2bc028e7d16b03ea6cac4953982f86fa5f1d687d3e9639b1854c428568cec000000006b483045022100d21834f7f5eaae60504910ea9d89d9b303de5273dbadb458b5096a372e16ac290220037d687149f60961a27978440a8bd9d24ced4b857b9b91d53546c5b54b758d250121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff7c2b9ea2729500056cb78e7ba66134d0b95abea9d6edfd2a2193782900fbc390010000006a47304402207926a9aaa80fab99c7563f9197782709349f676580904e54a24f833f4e3007eb02205fc4b1d25fa62dc969dd31b863c674cefa2d600e69c61e6c35d6158c876e97870121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff60c85a9e5c08c26e738bfe913ea02bb84dd1b342eb2b0400050f79707623208e010000006a473044022056e6941e0f43f7b6d3f100f6246a7bacae90f5a8f9408974355dbf93e6dca31e02202a54b5cedf817960ae114eeb45f330a8a4c7c8f77df2b3ff002a90bd260e0ab90121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffffb1361038834b2bae3d3b63084f1e977f610638b598990c56ff92eee6eaee8f62010000006a473044022054f539dccb3ee0d252409917ae398e8b7894bfcb15b9d5f4c4b67777a860153b02204351f31f2ba562c9a09488eaab93848a4112167ec2aab594d7f4f3a7ec39d3330121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffffb08a39fa63b146754c0a088a02d1a621801fa240bfd0a2596dfa6024fb119e90010000006b483045022100f3df8009046a560a1619a1072ea1f3b12866ae95471f3714600885484866c0af02204d685dd4dc88662838de336a3bd2fbbd57c6816d40bf53bf9ed959c95d2344c20121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffffe4733b4429b0e57e8d9f40bf26c6f3d646e366cadc57ae44bbfc7c065f8a2b7f010000006b483045022100997f287ae41ad6f3bbc9b33804ed6731b44989edc586da883e7791f392982cab022015a2ac998667b8979289ff387fbf3c5e99d7dc4d4305bfa80cd6ad32cb53890c0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff7804d1ed80733cccc66adf652a326e6fd78836bf192aa3721cf2841226aa5eaa010000006a47304402207862c45986ba64a81a96513ce45506c1a2341be18ac33a50f6986583e209620d022024c20305c6867c2b714c0b138850ef157aeed1ad3aed65dc2a9d1f734318d93a0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff96e7d50c764b95b880f5661104fbc60be3d6b50cefb424ced4469cf565f8057b000000006b483045022100d1ee2c168f251d6f48f1cc633a845a34fe6495568d212536faea5a2fc6287d3b02207904f6842fda2fb0537e2acee622b3a602dbccdb0d21d90ac4e14938b76897af0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff005f038100c03c98f73303cf54ca1bda76cda69e803495662a2b04d19bb2d49d000000006a47304402207762225ee520dc0df1d1a189d51937521aee834efd27dece891162f11efbe8b50220741114a083790ecdd93c8cd917beee3c5ba1b8fe81afd0cb6a5fb4bf28d5b41601210221cbb57963c7d4a55e62bedc15a5e1eb9ccaa4760454b69c162f9c3ee928f795ffffffffa50cde5e139ea4ff882b9760e3f64675e61233361d45211b8ceaeb070642e7cf000000006b483045022100ffb88e29f90a9d519dff659ebedf9e37741ca4a62bf60f0f72a895c8c2d9adb602207c9b4ddbb693582f6fb9198fca49855365390435a437909a95cf9eb8ce4428cf0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff62015abaf271d0a387ecbe84fbc4de4cc3421ec066f5bec7789868002888cb25010000006b483045022100f32261e93e446885de7be5f11d20a47913175f6c2f5566cae5c2d63d14c8969002202ee5f101f89d0fe8bb40f72490ecdd2e7b232668c45a2f95a792593ad28e8d1a012102a59ac2d93496982c0a4168ae7aec384ecbb8c7b31f84548a01b55bb2beee21bbffffffff7dfda48b4e88c8cf2bd222ea226ccb25221b40cd1dc8df820c84806f61c10e51010000006b4830450221008ac38362ef2e889a8e6ecfb8e4037a29792616597ffaeaff73800be76636903a022013980735c88125f0ece85e77fdf5ba1ec2faf2add7b91eebc522c3dc7dc96ad20121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff055476930c3ba44a97bb3f2223170277f6183c57633c8a803693697f7ee06bbf010000006b483045022100b92f7f76ed93828a4b83b62070c1ce0667eed189a421732786ab8ad9636f621d02203dd3ba7da91f852952af97efbf1dfbd416743e65d9950a2f889fdbb88f457a010121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff1bde4e3d3a1b192d7f97aa74e10e566c454e1e18f5185fcdae9a38c56864d3cf000000006b483045022100e7925276ecc4681a7bcced3f1fccc72b11f7df0376cb04577fa3f6daa3a41e84022022811a7771bd207d80b1baadfbf6e2d6e194958d0008e7f692cfab85271a0f490121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8fffffffff31d800a6a46b7a9534a99e202e56a6bad2a74bfa5d310c9cfe2e82ed5d4dfae000000006a473044022070c8eb275db1573be01bf8fedfacea466e8c4a73bf6801d05d78f34722801b9b0220549789d63ad3c3e8470eccb1059b46eea58599a1a83a1389afe7ffec35f383bd0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffffb08f6a35053890109addd99b20069c3c3d2c20c226439393d06a0eb16b5ba1d4000000006a4730440220737aae9f1b6d85c37fe180810adad12b6f8d4b8301cb6352b1181bc22528faf4022024998a33db99a2f03fe288f40231bc205a21787b1a3b425633daa556bd48c4c20121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff9dfbc02b09df97f9c77e62b24dffd8136273994c7949aaec7d1bfb92cf9fa47c000000006b483045022100b840c04ab83de326c0da6e12b3373fde97a698b31e1af4b763d7bf8e04d46492022044759767a1b03ec1b3cc11117277c84ca88091988b58c135147818daab2bc56c0121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff8b8e3fece36d27adb598f628251b8873144f1d6d96b5346d1c5801909c59f93b000000006a47304402203f9293581ed43f9f1cd28d573341da507ebf8b6777ce13bc7eb50db594763c4202205707d93e654a2a60a9188ab40aa03edbd3d20dd9b02fedb93f2ffdf7e0ab9fc50121021602a4c8edd4bff7341b1a34b7f48f50813439ad7db5364fb83fa3790950bca8ffffffff7d744d2577b27ce3012d06a867c655187b2ca6ea190df461754e4c25f543bae2000000006a473044022062e59d1b6cc0e5aafea79c901f26e2e64b21df5ef5884cedf28fdc7211be9c0c0220558485329ee73a263aa69349eb6d5074e76bf43809091faecfb0f6349720ed84012103b981c52352aa4c987e51859243e5bf42b8b229db9550084cadeac8b28e573696ffffffff02b79d459c2d0000001976a914342c2cc8cd3c58bdfb905189068b3538a2fad8ab88acce460f00000000001976a914a99e9cc3e6078708f9f78b8c0b3a39ab4ad8a5df88ac00000000"
}
```
The `gettransaction` command will detail transactions that are in your wallet, such as this one, that was sent to us.

Note that `gettransaction` has two optional arguments:
```
$ digitalcoin-cli help gettransaction
gettransaction "txid" ( include_watchonly verbose )

Get detailed information about in-wallet transaction <txid>

Arguments:
1. txid                 (string, required) The transaction id
2. include_watchonly    (boolean, optional, default=true for watch-only wallets, otherwise false) Whether to include watch-only addresses in balance calculation and details[]
3. verbose              (boolean, optional, default=false) Whether to include a `decoded` field containing the decoded transaction (equivalent to RPC decoderawtransaction)
```
By setting these two true or false, we can choose to include watch-only addresses in the output (which we don't care about) or look at more verbose output (which we do).

Here's what this data instead looks at when we set `include_watchonly` to `false` and `verbose` to `true`.
```
$ digitalcoin-cli gettransaction "8e2ab10cabe9ec04ed438086a80b1ac72558cc05bb206e48fc9a18b01b9282e9" false true
{
  "amount": 0.01000000,
  "confirmations": 3,
  "blockhash": "00000000000001753b24411d0e4726212f6a53aeda481ceff058ffb49e1cd969",
  "blockheight": 1772396,
  "blockindex": 73,
  "blocktime": 1592600085,
  "txid": "8e2ab10cabe9ec04ed438086a80b1ac72558cc05bb206e48fc9a18b01b9282e9",
  "walletconflicts": [
  ],
  "time": 1592599884,
  "timereceived": 1592599884,
  "bip125-replaceable": "no",
  "details": [
    {
      "address": "mi25UrzHnvn3bpEfFCNqJhPWJn5b77a5NE",
      "category": "receive",
      "amount": 0.01000000,
      "label": "",
      "vout": 1
    }
  ],
  "hex": "0200000000010114d04977d1b0137adbf51dd5d79944b9465a2619f3fa7287eb69a779977bf5800100000017160014e85ba02862dbadabd6d204fcc8bb5d54658c7d4ffeffffff02df690f000000000017a9145c3bfb36b03f279967977ca9d1e35185e39917788740420f00000000001976a9141b72503639a13f190bf79acf6d76255d772360b788ac0247304402201e74bdfc330fc2e093a8eabe95b6c5633c8d6767249fa25baf62541a129359c202204d462bd932ee5c15c7f082ad7a6b5a41c68addc473786a0a9a232093fde8e1330121022897dfbf085ecc6ad7e22fc91593414a845659429a7bbb44e2e536258d2cbc0c270b1b00",
  "decoded": {
    "txid": "8e2ab10cabe9ec04ed438086a80b1ac72558cc05bb206e48fc9a18b01b9282e9",
    "hash": "d4ae2b009c43bfe9eba96dcd16e136ceba2842df3d76a67d689fae5975ce49cb",
    "version": 2,
    "size": 249,
    "vsize": 168,
    "weight": 669,
    "locktime": 1772327,
    "vin": [
      {
        "txid": "80f57b9779a769eb8772faf319265a46b94499d7d51df5db7a13b0d17749d014",
        "vout": 1,
        "scriptSig": {
          "asm": "0014e85ba02862dbadabd6d204fcc8bb5d54658c7d4f",
          "hex": "160014e85ba02862dbadabd6d204fcc8bb5d54658c7d4f"
        },
        "txinwitness": [
          "304402201e74bdfc330fc2e093a8eabe95b6c5633c8d6767249fa25baf62541a129359c202204d462bd932ee5c15c7f082ad7a6b5a41c68addc473786a0a9a232093fde8e13301",
          "022897dfbf085ecc6ad7e22fc91593414a845659429a7bbb44e2e536258d2cbc0c"
        ],
        "sequence": 4294967294
      }
    ],
    "vout": [
      {
        "value": 0.01010143,
        "n": 0,
        "scriptPubKey": {
          "asm": "OP_HASH160 5c3bfb36b03f279967977ca9d1e35185e3991778 OP_EQUAL",
          "hex": "a9145c3bfb36b03f279967977ca9d1e35185e399177887",
          "reqSigs": 1,
          "type": "scripthash",
          "addresses": [
            "2N1ev1WKevSsdmAvRqZf7JjvDg223tPrVCm"
          ]
        }
      },
      {
        "value": 0.01000000,
        "n": 1,
        "scriptPubKey": {
          "asm": "OP_DUP OP_HASH160 1b72503639a13f190bf79acf6d76255d772360b7 OP_EQUALVERIFY OP_CHECKSIG",
          "hex": "76a9141b72503639a13f190bf79acf6d76255d772360b788ac",
          "reqSigs": 1,
          "type": "pubkeyhash",
          "addresses": [
            "mi25UrzHnvn3bpEfFCNqJhPWJn5b77a5NE"
          ]
        }
      }
    ]
  }
}
```
Now you can see the full information on the transaction, including all of the inputs ("vin") and all the outputs ("vout). One of the interesting things to note is that although we received .01 DGC in the transaction, another .01010143 was sent to another address. That was probably a change address, a concept that is explored in the next section. It is quite typical for a transaction to have multiple inputs and/or multiple outputs.

There is another command, `getrawtransaction`, which allows you to look at transactions that are not in your wallet. However, it requires you to have unpruned node and `txindex=1` in your `digitalcoin.conf` file. Unless you have a serious need for information not in your wallet, it's probably just better to use a Digitalcoin explorer for this sort of thing ...

## Optional: Use a Block Explorer

Even looking at the verbose information for a transaction can be a little intimidating. The main goal of this tutorial is to teach how to deal with raw transactions from the command line, but we're happy to talk about other tools when they're applicable. One of those tools is a block explorer, which you can use to look at transactions from a web browser in a much friendlier format.

Currently, our preferred block explorer is [https://live.blockcypher.com/](https://live.blockcypher.com/).

You can use it to look up transactions for an address:

[https://live.blockcypher.com/btc-testnet/address/mi25UrzHnvn3bpEfFCNqJhPWJn5b77a5NE/](https://live.blockcypher.com/btc-testnet/address/mi25UrzHnvn3bpEfFCNqJhPWJn5b77a5NE/)

You can also use it to look at individual transactions:

[https://live.blockcypher.com/btc-testnet/tx/8e2ab10cabe9ec04ed438086a80b1ac72558cc05bb206e48fc9a18b01b9282e9/](https://live.blockcypher.com/btc-testnet/tx/8e2ab10cabe9ec04ed438086a80b1ac72558cc05bb206e48fc9a18b01b9282e9/)

A block explorer doesn't generally provide any more information than a command line look at a raw transaction; it just does a good job of highlighting the important information and putting together the puzzle pieces, including the transaction fees behind a transaction — another concept that we'll be covering in future sections.

## Summary: Receiving a Transaction

Faucets will give you money on the testnet. They come in as raw transactions, which can be examined with `gettransaction` or a block explorer. Once you've receive a transaction, you can see it in your balance and your wallet.

## What's Next?

For a deep dive into how addresses are described, so that they can be transferred or made into parts of a multi-signature, see [§3.5: Understanding the Descriptor](03_5_Understanding_the_Descriptor.md).

But if that's too in-depth, continue on to [Chapter Four: Sending Digitalcoin Transactions](04_0_Sending_Digitalcoin_Transactions.md).

