/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package millionminions;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import org.bouncycastle.*;

/**
 *
 * @author Radhe Shyam Lodhi
 */
public class Wallet {
        private PrivateKey privateKey;
	private PublicKey publicKey;
	
        private ArrayList<String> propertyList = new ArrayList<>();
        
        private String WalletId;
        
        
        
	public Wallet(){
		//generateKeyPair();
                
                
                WalletId = StringUtility.getStringFromKey(publicKey);
                
	}
        
        public PublicKey getPublicKey(){
            return publicKey;
        }
        
        public boolean addProperty(String landId){
            return this.propertyList.add(landId);
            
        }
        public boolean removeProperty(String landId){
            return this.propertyList.remove(landId);
            
        }
        
        
        public ArrayList<String> getProperty(){
            
            
            return this.propertyList;
        }
        
        
        
        
        
	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
	        	KeyPair keyPair = keyGen.generateKeyPair();
	        	// Set the public and private keys from the keyPair
	        	privateKey = keyPair.getPrivate();
	        	publicKey = keyPair.getPublic();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
