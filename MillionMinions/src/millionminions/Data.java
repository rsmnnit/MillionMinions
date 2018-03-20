/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package millionminions;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;

/**
 *
 * @author Radhe Shyam Lodhi
 */
public class Data implements Serializable{
    
    public int msgKey;
    
    // 1 -> view wallet
    // 2 -> selling property
    // 3 -> view chain
    
    
    public PublicKey from;
    public PublicKey to;
    public String landId;
    public String buyerWalletId;
    public String sellerWalletId;
    
    public transient ArrayList<Block> blockchain;
    
    
    
    public Data(int msgKey ,PublicKey from ,PublicKey to, String landId,String buyerWalletId,String sellerWalletId){
        this.msgKey = msgKey;
        this.from = from;
        this.to = to;
        this.landId = landId;
        this.buyerWalletId = buyerWalletId;
        this.sellerWalletId = sellerWalletId;
    }

    public Data() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
