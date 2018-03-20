/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package millionminions;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Radhe Shyam Lodhi
 */
public class User {
    
    private Wallet wallet;
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String userWalletId;
    
    
    public int msgKey;
    // 1 -> view wallet
    // 2 -> selling property
    // 3 -> view chain
    public PublicKey from;
    public PublicKey to;
    public String landId;
    public String buyerWalletId;
    
    public ObjectOutputStream oos;
    public ObjectInputStream ois;
    
    
    
    public User() {
        
        try {
            s = new Socket("localhost", 9898);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            
            oos = new ObjectOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());
            
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void main(String args[]) throws IOException, ClassNotFoundException{
        
        
        
        User user = new User();
        
        int getout=0;
        
        while(true){
            
            System.out.println("1.View wallet\n2.Sell property\n3.View View Blockcahin\n4.Exit\n");
            Scanner sc= new Scanner(System.in);
            getout = sc.nextInt();
            
            switch(getout){
                
                case 1: 
                System.out.println("Enter your wallet id.");
                user.userWalletId = sc.next();
                user.getWallet();
                break;
                
                case 2:
                
                System.out.println("Enter your wallet id.");
                user.userWalletId = sc.next();
                
                
                System.out.println("Enter buyer wallet id.");
                user.buyerWalletId = sc.next();
                
                
                System.out.println("Enter land id.");
                user.landId = sc.next();
                
                
                user.sellProperty();
                
                break;
                
                case 3: user.viewChain();
                break;
                
                case 4: 
                    return ;
                    
                
            }
                        
        }
    }
    
    public Wallet getWallet(){
        try {
            // get wallet form walletId using socket call
            Data data = new Data(1,null,null,null, null,userWalletId);
            oos.writeObject(data);
            
            String result = (String) ois.readObject();
            System.out.println(result);
            
            return null;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean sellProperty(){
        
        
        
        
        try {
            Data data = new Data(2,from,to,landId, buyerWalletId,userWalletId);
            oos.writeObject(data);
            
            
            
            
            return true;
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    
    
    public void viewChain() throws IOException, ClassNotFoundException{
        //System.out.println("aaya viewchain me");
        Data data = new Data(3,from,to,landId, buyerWalletId,userWalletId);
            oos.writeObject(data);
            
            
            //Block a = (Block)ois.readObject();
            
            
            //data = (Data) ois.readObject();
            
            String json = (String) ois.readObject();
            System.out.println(json);
            ObjectMapper mapper = new ObjectMapper();
            
            
            ArrayList<Block> blockchain = mapper.readValue(json, 
                    mapper.getTypeFactory().constructCollectionType(
                            ArrayList.class, Block.class));
            
            
            //System.out.println(data.blockchain.size());
            //System.out.println("yaha bhi");
            for( Block b : blockchain){
                
                for(Transaction t : b.transactionList){
                    
                    System.out.println("TransactionId : "+t.transactionId+",\nLandID : "+t.landId+" ,\nSenderId : "+t.senderId+",\nBuyerId : "+t.recipientId+"\n");
                    
                }
                
                
            }
//            System.out.println("last");
       
    }
    
    
}
