/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package millionminions;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Radhe Shyam Lodhi
 */
public class Transaction implements Serializable{
        public String transactionId; // this is also the hash of the transaction.
	public PublicKey sender; // senders address/public key.
	public PublicKey reciepient; // Recipients address/public key.
        
        public String senderId, recipientId;
        
        
        public String landId;
        public String timeStamp;
        public DbHandler dbHandler;
        
	public float value;
	public byte[] signature; // this is to prevent anybody else from spending funds in our wallet.
	
	public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
	
	private static int sequence = 0; // a rough count of how many transactions have been generated. 
	
	// Constructor: 
        
        
        public Transaction(){}
        
        public Transaction(PublicKey from, PublicKey to, String landId){
            this.sender = from;
            this.reciepient = to;
            this.landId = landId;
            timeStamp = String.valueOf(new Date().getTime());
            this.transactionId = StringUtility.getStringFromKey(from)+StringUtility.getStringFromKey(to)+String.valueOf(timeStamp);
            //dbHandler = new DbHandler();
        }
        
        
        public Transaction(String from, String to, String landId){
            this.senderId = from;
            this.recipientId = to;
            this.landId = landId;
            timeStamp = String.valueOf(new Date().getTime());
            this.transactionId = from+to+String.valueOf(timeStamp);
            //dbHandler = new DbHandler();
        }
        
        
      
        
        
	public Transaction(PublicKey from, PublicKey to, float value,  ArrayList<TransactionInput> inputs) {
		this.sender = from;
		this.reciepient = to;
		this.value = value;
		this.inputs = inputs;
	}
	
	// This Calculates the transaction hash (which will be used as its Id)
	private String calulateHash() {
		sequence++; //increase the sequence to avoid 2 identical transactions having the same hash
		return StringUtility.applySha256(
				StringUtility.getStringFromKey(sender) +
				StringUtility.getStringFromKey(reciepient) +
				Float.toString(value) + sequence
				);
	}
        
        
        public void generateSignature(PrivateKey privateKey) {
            String data = StringUtility.getStringFromKey(sender) + StringUtility.getStringFromKey(reciepient) + Float.toString(value)	;
            signature = StringUtility.applyECDSASig(privateKey,data);		
        }
//Verifies the data we signed hasnt been tampered with
        public boolean verifiySignature() {
            String data = StringUtility.getStringFromKey(sender) + StringUtility.getStringFromKey(reciepient) + Float.toString(value)	;
            return StringUtility.verifyECDSASig(sender, data, signature);
        }

//    public boolean processTxn(String buyerWallet, String sellerWallet) {
//        
//        
//        return this.addProperty(buyerWallet, landId) && this.removeProperty(sellerWallet, landId);
//    
//    }
//    
//    
//        
//    public boolean addProperty(String buyerWallet, String landId ){
//            try {
//                // add directly to db
//                // waiting for dbhandler class
//                
//                
//                Connection conn = dbHandler.getConnection();
//                PreparedStatement ps ;
//                
//                String query = "INSERT INTO list_of_land Values (?,?)";
//                
//                
//                ps  = conn.prepareStatement(query);
//                ps.setString(1, buyerWallet);
//                ps.setString(2, landId);
//                
//                
//                ps.executeUpdate();
//                
//                return true;
//                
//               
//                        
//                        
//            } catch (SQLException ex) {
//                Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return false;
//        }
//        
//        public boolean removeProperty(String sellerWallet, String landId){
//            
//            try {
//                Connection conn = dbHandler.getConnection();
//                PreparedStatement ps ;
//                
//                String query = "DELETE FROM list_of_land WHERE walletid = ? AND landid = ?";
//                
//                
//                ps  = conn.prepareStatement(query);
//                ps.setString(1, sellerWallet);
//                ps.setString(2, landId);
//                
//                
//                ps.executeUpdate();
//                
//                    return true;
//            } catch (SQLException ex) {
//                Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return false;
//        }
//        
//        
//        
        
}
