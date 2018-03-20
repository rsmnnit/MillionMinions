/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package millionminions;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Radhe Shyam Lodhi
 */
public class Server {
    public static ServerSocket ss;
    public static Socket s;
   
    
    public static Chain chain;
    
    
    public Server(){
        
        
    }
   
    public static void main(String arg[]){
        
        try {
            ss=new ServerSocket(9898);
            chain = new Chain();
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        while(true){
        try {
            
                s=ss.accept();
                System.out.println("HUA ");
                Thread t = new Thread(new ClientHandler(s, chain));
                t.start();
        } 
        catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        
        }
        }
        
    }
    
    
}
