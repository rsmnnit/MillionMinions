/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package millionminions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Radhe Shyam Lodhi
 */
public class TransactionStorage {
    
    
    public static boolean procTxn(Transaction t) throws ClassNotFoundException{
        
        return addProperty(t.recipientId, t.landId) && removeProperty(t.senderId, t.landId);
    }
    
    
        
    public static boolean addProperty(String buyerWallet, String landId ) throws ClassNotFoundException{
            try {
                // add directly to db
                // waiting for dbhandler class
                
                
                Connection conn = (new DbHandler()).getConnection();
                PreparedStatement ps ;
                
                String query = "INSERT INTO list_of_land Values (?,?)";
                
                
                ps  = conn.prepareStatement(query);
                ps.setString(1, buyerWallet);
                ps.setString(2, landId);
                
                
                ps.executeUpdate();
                
                return true;
                
               
                        
                        
            } catch (SQLException ex) {
                Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
        
        public static boolean removeProperty(String sellerWallet, String landId) throws ClassNotFoundException{
            
            try {
                Connection conn = (new DbHandler()).getConnection();
                PreparedStatement ps ;
                
                String query = "DELETE FROM list_of_land WHERE walletid = ? AND landid = ?";
                
                
                ps  = conn.prepareStatement(query);
                ps.setString(1, sellerWallet);
                ps.setString(2, landId);
                
                
                ps.executeUpdate();
                
                    return true;
            } catch (SQLException ex) {
                Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }

   
        
        
    
    
    
}
