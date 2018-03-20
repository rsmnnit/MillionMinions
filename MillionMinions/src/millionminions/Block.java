/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package millionminions;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Radhe Shyam Lodhi
 */
public class Block implements Serializable{
    
    public int MAX_TXN = 100;
    public String hash;
    public String previousHash;
    private String data; //simple message.
    private long timeStamp; //as number of milliseconds since 1/1/1970.
    private int nonce;
    
    
    
    public transient ArrayList<Transaction> transactionList = new ArrayList<>();
    public int transactionCount;
    private String blockId;
    
    
    public Block(){
    }
    
	//Block Constructor.
    public Block(String data,String previousHash ) {
	this.data = data;
	this.previousHash = previousHash;
	this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
        this.blockId = hash+timeStamp;
        
        
        //TO DO
        // entry in db (blockid->prevhash)
        
        
        
    }

    public ArrayList<Transaction> getTransactionList() {
        return transactionList;
    }
    
    
    
   
    
    
    public boolean addTransaction(PublicKey from, PublicKey to, String landId,String buyerWallet, String sellerWallet){
        //process Txn
        
        
        
        
        
        Transaction txn = new Transaction(from, to, landId);
        
        
        
        
        
        try {
            if(TransactionStorage.procTxn(txn)){
                
                // store in db
                transactionList.add(txn);
                return true;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
        }
        //msg alert
        return false;
    }
    
    
    public String calculateHash() {
	String calculatedhash = StringUtility.applySha256( previousHash +Long.toString(timeStamp) +data);
	return calculatedhash;
    }
    
    
//    public void mineBlock(int difficulty) {
//		String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
//		while(!hash.substring( 0, difficulty).equals(target)) {
//			nonce ++;
//			hash = calculateHash();
//		}
//		System.out.println("Block Mined!!! : " + hash);
//	}
}
