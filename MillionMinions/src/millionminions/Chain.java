/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package millionminions;

import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import sun.security.provider.DSAPublicKey;

/**
 *
 * @author Radhe Shyam Lodhi
 */
public class Chain implements Serializable{
    
   
        public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static HashMap<String,TransactionOutputs> UTXOs = new HashMap<String,TransactionOutputs>(); //list of all unspent transactions. 
	public static int difficulty = 5;
	public static Wallet walletA;
	public static Wallet walletB;
        
        
        
        public Chain(){
            
            //DUMMY
            
            
            
            
            
            Block b1 = new Block("Radhe", "0");
//            
//            Wallet w = new Wallet();
//            
//            Transaction txn = new Transaction(w.getPublicKey(),w.getPublicKey() , "10");
//            b1.transactionList.add(txn);
            
            this.blockchain.add(b1);
           
            
        }
        
        
        
        
        public static void main(String[] args) {
		
	blockchain.add(new Block("Hi im the first block", "0"));		
	blockchain.add(new Block("Yo im the second block",blockchain.get(blockchain.size()-1).hash)); 
	blockchain.add(new Block("Hey im the third block",blockchain.get(blockchain.size()-1).hash));
		
	String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);		
	System.out.println(blockchainJson);	
    }
    
    
    public Block getCurrrentBlock(){
        
        return blockchain.get(blockchain.size()-1);
    }
    
    
    public ArrayList<Transaction> getTxnByProperty(String landId){
        // TO DO
        ArrayList<Transaction> temp = new ArrayList<>();
        for(Block b: blockchain){
            for(Transaction t: b.getTransactionList()){
                if(t.landId.equals(landId)){
                    temp.add(t);
                }
            }
        }
        return temp;
    }
    
//    public static Boolean isChainValid() {
//	Block currentBlock; 
//	Block previousBlock;
//	String hashTarget = new String(new char[difficulty]).replace('\0', '0');
//        
//	//loop through blockchain to check hashes:
//	for(int i=1; i < blockchain.size(); i++) {
//		currentBlock = blockchain.get(i);
//		previousBlock = blockchain.get(i-1);
//		//compare registered hash and calculated hash:
//		if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
//			System.out.println("Current Hashes not equal");			
//			return false;
//		}
//		//compare previous hash and registered previous hash
//		if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
//			System.out.println("Previous Hashes not equal");
//			return false;
//		}
//                
//                //check if hash is solved
//		if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
//			System.out.println("This block hasn't been mined");
//			return false;
//		}
//                
//	}
//	return true;
//}

    
    
}
