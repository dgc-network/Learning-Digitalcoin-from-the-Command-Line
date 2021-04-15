package com.blockchaincommons.lbtc;

import wf.digitalcoin.javabitcoindrpcclient.BitcoinAcceptor;
import wf.digitalcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;
import wf.digitalcoin.javabitcoindrpcclient.BitcoinPaymentListener;
import wf.digitalcoin.javabitcoindrpcclient.BitcoindRpcClient;
import wf.digitalcoin.javabitcoindrpcclient.BitcoindRpcClient.*;

public class App
{

    public static void main( String[] args ) throws Exception
    {

	BitcoindRpcClient rpcClient = new BitcoinJSONRPCClient("http://StandUp:6
305f1b2dbb3bc5a16cd0f4aac7e1eba@localhost:18332");

	String blockHash = rpcClient.getBestBlockHash();
	
	BitcoinAcceptor acceptor = new BitcoinAcceptor(rpcClient, blockHash, 6, 
new BitcoinPaymentListener() {

		@Override
		public void transaction(Transaction tx) {
		    System.out.println("Transaction: " + tx);
		}

		@Override
		public void block(String block) {
		    System.out.println("Block: " + block);

		}
	    });
	acceptor.run();
    }
}
