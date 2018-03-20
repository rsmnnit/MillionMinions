/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package millionminions;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Radhe Shyam Lodhi
 */
public class ClientHandler implements Runnable{

    public Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;
    
    public ObjectOutputStream oos;
    public ObjectInputStream ois;
    public Chain chain;
    public ObjectMapper mapper;
    public transient ArrayList<Block> blockchain = new ArrayList<>();
    
    public ClientHandler(Socket s, Chain chain) {
        
        try {
            
            this.s = s;
            this.chain = chain;
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            oos = new ObjectOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());
            
            
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

    @Override
    public void run() {
        
        while(true){
            
            try {
                Data data = (Data) ois.readObject();
                System.out.println("Data "+ data.msgKey);
                if(data.msgKey == 1){
                    
                    
                    Connection con = (new DbHandler()).getConnection();
                    String query = "select * from list_of_land where walletid=?";
                    try {
                        PreparedStatement ps = con.prepareStatement(query);
                        ps.setString(1, data.sellerWalletId);
                        
                        ResultSet rs = ps.executeQuery();
                        
                        String result="";
                        
                        while(rs.next()){
                            
                            result+=rs.getString(2);
                            result+="\n";
                            
                        }
                        
                        oos.writeObject(result);
                        
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                    
                    
                }
                else if(data.msgKey == 2){
                    
                    if(chain.blockchain.size()>0){
                        Block block = chain.blockchain.get(chain.blockchain.size() - 1);
                        
                        if(block.transactionCount < block.MAX_TXN){
                            Transaction txn = new Transaction(data.sellerWalletId, data.buyerWalletId, data.landId);
                            
                                                        try {
                                        if(TransactionStorage.procTxn(txn)){

                                            // store in db
                                            block.transactionList.add(txn);
                                            block.transactionCount++;
                                        }
                                    } catch (ClassNotFoundException ex) {
                                        Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                        }
                        else{
                            
                           Block b = new Block("gsdf", chain.blockchain.get(chain.blockchain.size() - 1).hash);
                           
                           chain.blockchain.add(b);
                           
                            Transaction txn = new Transaction(data.from, data.to, data.landId);
                            
                                                        try {
                                        if(TransactionStorage.procTxn(txn)){

                                            // store in db
                                            b.transactionList.add(txn);
                                            b.transactionCount++;
                                        }
                                    } catch (ClassNotFoundException ex) {
                                        Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                        }
                           
                        
                        
                        
                        
                    }
                    else{
                    
                        
                        Block b = new Block("gsdf", chain.blockchain.get(chain.blockchain.size() - 1).hash);
                           
                           chain.blockchain.add(b);
                           
                            Transaction txn = new Transaction(data.from, data.to, data.landId);
                            
                                                        try {
                                        if(TransactionStorage.procTxn(txn)){

                                            // store in db
                                            b.transactionList.add(txn);
                                            b.transactionCount++;
                                        }
                                    } catch (ClassNotFoundException ex) {
                                        Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                    }
                    
                    
                    
                }
                else if(data.msgKey == 3){
                    
                   mapper = new ObjectMapper();
                   
                   String jsonInString = mapper.writeValueAsString(chain.blockchain);
                   oos.writeObject(jsonInString);
                   //System.out.println(jsonInString);
                    
                }
                
                
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
    }
    
    public  Wallet getWallet(){
        
        return null;
    }
    
    public boolean processTxn(){
        return true;
    } 
    
    
    
}
