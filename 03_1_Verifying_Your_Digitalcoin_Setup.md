# 3.1: Verifying Your Digitalcoin Setup

Before you start playing with Digitalcoin, you should ensure that everything is setup correctly.

## Create Your Aliases

We suggest creating some aliases to make it easier to use Digitalcoin.

You can do so by putting them in your `.bash_profile`, `.bashrc` or `.profile`.
```
cat >> ~/.bash_profile <<EOF
alias dgcdir="cd ~/.digitalcoin/" #linux default digitalcoind path
alias dc="digitalcoin-cli"
alias dd="digitalcoind"
alias dgcinfo='digitalcoin-cli getwalletinfo | egrep "\"balance\""; digitalcoin-cli getnetworkinfo | egrep "\"version\"|connections"; digitalcoin-cli getmininginfo | egrep "\"blocks\"|errors"'
EOF
```
After you enter these aliases you can either `source .bash_profile` to input them or just log out and back in.

Note that these aliases includes shortcuts for running `digitalcoin-cli`, for running `digitalcoind`, and for going to the Digitalcoin directory. These aliases are mainly meant to make your life easier. We suggest you create other aliases to ease your use of frequent commands (and arguments) and to minimize errors. Aliases of this sort can be even more useful if you have a complex setup where you regularly run commands associated with Mainnet, with Testnet, _and_ with Regtest, as explained further below.

With that said, use of these aliases in _this_ document might accidentally obscure the core lessons being taught about Digitalcoin, so the only alias directly used here is `dgcinfo` because it encapsulatea  much longer and more complex command. Otherwise, we show the full commands; adjust for your own use as appropriate.

## Run Digitalcoind

You'll begin your exploration of the Digitalcoin network with the `digitalcoin-cli` command. However, digitalcoind _must_ be running to use digitalcoin-cli, as digitalcoin-cli sends JSON-RPC commands to the digitalcoind. If you used our standard setup, digitalcoind should already be up and running. You can double check by looking at the process table.
```
$ ps auxww | grep digitalcoind
standup    455  1.3 34.4 3387536 1392904 ?     SLsl Jun16  59:30 /usr/local/bin/digitalcoind -conf=/home/standup/.digitalcoin/digitalcoin.conf
```
If it's not running, you'll want to run `/usr/local/bin/digitalcoind -daemon` by hand and also place it in your crontab.

## Verify Your Blocks

You should have the whole blockchain downloaded before you start playing. Just run the `digitalcoin-cli getblockcount` alias to see if it's all loaded. 
```
$ digitalcoin-cli getblockcount
5068307
```
That tells you what's loaded; you'll then need to check that against an online service that tells you the current block height.

> :book: ***What is Block Height?*** Block height is the the distance that a particular block is removed from the genesis block. The current block height is the block height of the newest block added to a blockchain.

You can do this by looking at a blocknet explorer, such as [the Blockcypher Testnet explorer](https://live.blockcypher.com/btc-testnet/). Does its most recent number match your `getblockcount`? If so, you're up to date.

If you'd like an alias to look at everything at once, the following currently works for Testnet, but may disappear at some time in the future:
```
$ cat >> ~/.bash_profile << EOF
alias dgcblock="echo \\\`digitalcoin-cli getblockcount 2>&1\\\`/\\\`wget -O - https://blockstream.info/testnet/api/blocks/tip/height 2> /dev/null | cut -d : -f2 | rev | cut -c 1- | rev\\\`"
EOF
$ source .bash_profile 
$ dgcblock
1804372/1804372
```

> :link: **TESTNET vs MAINNET:** Remember that this tutorial generally assumes that you are using testnet. If you're using the mainnet instead, you can retrieve the current block height with: `wget -O - http://blockchain.info/q/getblockcount 2>/dev/null`. You can replace the latter half of the `btblock` alias (after `/`) with that.

If you're not up-to-date, but your `getblockcount` is increasing, no problem. Total download time can take from an hour to several hours, depending on your setup.

## Optional: Know Your Server Types

> **TESTNET vs MAINNET:** When you set up your node, you choose to create it as either a Mainnet, Testnet, or Regtest node. Though this document presumes a testnet setup, it's worth understanding how you might access and use the other setup types — even all on the same machine! But, if you're a first-time user, skip on past this, as it's not necessary for a basic setup.

The type of setup is mainly controlled through the ~/.digitalcoin/digitalcoin.conf file. If you're running testnet, it probably contains this line:
```
testnet=1
```
If you're running regtest, it probably contains this line:
```
regtest=1
```
However, if you want to run several different sorts of nodes simultaneously, you should leave the testnet (or regtest) flag out of your configuration file. You can then choose whether you're using the mainnet, the testnet, or your regtest every time you run digitalcoind or digitalcoin-cli.

Here's a set of aliases that would make that easier by creating a specific alias for starting and stopping the digitalcoind, for going to the digitalcoin directory, and for running digitalcoin-cli, for each of the mainnet (which has no extra flags), the testnet (which is -testnet), or your regtest (which is -regtest).
```
cat >> ~/.bash_profile <<EOF
alias dcstart="digitalcoind -daemon"
alias dtstart="digitalcoind -testnet -daemon"
alias drstart="digitalcoind -regtest -daemon"

alias dcstop="digitalcoin-cli stop"
alias dtstop="digitalcoin-cli -testnet stop"
alias drstop="digitalcoin-cli -regtest stop"

alias dcdir="cd ~/.digitalcoin/" #linux default digitalcoin path
alias dtdir="cd ~/.digitalcoin/testnet" #linux default digitalcoin testnet path
alias drdir="cd ~/.digitalcoin/regtest" #linux default digitalcoin regtest path

alias dc="digitalcoin-cli"
alias dt="digitalcoin-cli -testnet"
alias dr="digitalcoin-cli -regtest"
EOF
```
For even more complexity, you could have each of your 'start' aliases use the -conf flag to load configuration from a different file. This goes far beyond the scope of this tutorial, but we offer it as a starting point for when your explorations of Digitalcoin reaches the next level.

## Summary: Verifying Your Digitalcoin Setup

Before you start playing with digitalcoin, you should make sure that your aliases are set up, your digitalcoind is running, and your blocks are downloaded. You may also want to set up some access to alternative Digitalcoin setups, if you're an advanced user.

## What's Next?

Continue "Understanding Your Digitalcoin Setup" with [§3.2: Knowing Your Digitalcoin Setup](03_2_Knowing_Your_Digitalcoin_Setup.md).
