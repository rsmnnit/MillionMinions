/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package millionminions;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Radhe Shyam Lodhi
 */
public class DbHandler implements Serializable{
    
    public Connection conn;
    public DbHandler() throws ClassNotFoundException{
        
        try{
        
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/millionminions", "root", "root");
        
        } catch (InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Connection getConnection(){
        return conn;
    }
}
