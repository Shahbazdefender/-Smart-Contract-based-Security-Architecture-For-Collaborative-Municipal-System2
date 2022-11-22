/* 
 * Copyright (C) 2015 SDN-WISE
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.sdnwiselab.sdnwise.loader;
/* Shahbaz Description

According to hindawi.com/journals/misy/2015/398637
The Sky Mote Maximum Data Packet Size is 127 bytes
*/
import com.github.sdnwiselab.sdnwise.configuration.Configurator;
import com.github.sdnwiselab.sdnwise.controller.Controller;
import com.github.sdnwiselab.sdnwise.controller.ControllerFactory;
import com.github.sdnwiselab.sdnwise.packet.DataPacket;
import com.github.sdnwiselab.sdnwise.util.NodeAddress;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.MessageDigest;
import java.util.Base64;
import java.io.*;
import java.lang.NullPointerException;
import java.net.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.*;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.nio.charset.StandardCharsets;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.io.File;
import java.nio.file.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Base64;
import java.io.*;
import java.lang.NullPointerException;
import java.net.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.*;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.lang.*;
import java.lang.Float;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

/**
 * SdnWise class of the SDN-WISE project. It loads the configuration file and
 * starts the the Controller.
 *
 * @author Sebastiano Milardo
 * @version 0.1
 */
 
 
public class SdnWise {

    /**
     * Starts the components of the SDN-WISE Controller.
     *
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        new SdnWise().startExample();
    }

    private Controller controller;
    /**
     * Starts the Controller layer of the SDN-WISE network. The path to the
     * configurations are specified in the configFilePath String. The options to
     * be specified in this file are: a "lower" Adapter, in order to communicate
     * with the flowVisor (See the Adapter javadoc for more info), an
     * "algorithm" for calculating the shortest path in the network. The only
     * supported at the moment is "DIJKSTRA". A "map" which contains
     * informations regarding the "TIMEOUT" in order to remove a non responding
     * node from the topology, a "RSSI_RESOLUTION" value that triggers an event
     * when a link rssi value changes more than the set threshold.
     *
     * @param configFilePath a String that specifies the path to the
     * configuration file.
     * @return the Controller layer of the current SDN-WISE network.
     */
    

/* Before start the Controller you have to Call the Required Function From Python Located Script in 

/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/contiki/tools/cooja/examples/sdn-wise_java/java/com/github/sdnwiselab/sdnwise/cooja/NetworkConfigration

/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/01-GetStarted/Keys/hash


/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/01-GetStarted/Registration/
*/
    
	public boolean DigestCheck(byte[] receivedDigest, int i){
	try{
File file = new File("/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/01-GetStarted/Keys/hash"+i+".txt");
MessageDigest shaDigest1 = MessageDigest.getInstance("SHA-256");
final String shaChecksum1 = getFileChecksum(shaDigest1,file);
//System.out.println(shaChecksum1);
byte [] bytes123 = shaChecksum1.getBytes(StandardCharsets.UTF_8);
return Arrays.equals(bytes123,receivedDigest);
//return Arrays.equals(receivedDigest,shaChecksum1);

}
catch(NoSuchAlgorithmException e){
        System.out.println("Exception for NoSuchAlgorithmException in KeyGenInitialize function");
        return false;
        }
 catch(IOException e){
        System.out.println("IOEXCEPTION!");
        e.printStackTrace();
               }

return true;
}


public boolean NDigestCheck(byte[] receivedDigest, int i){
	try{
File file = new File("/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/contiki/tools/cooja/examples/sdn-wise_java/java/com/github/sdnwiselab/sdnwise/cooja/NetworkConfigration/"+i);
MessageDigest shaDigest1 = MessageDigest.getInstance("SHA-256");
final String shaChecksum1 = getFileChecksum(shaDigest1,file);
//System.out.println(shaChecksum1);
byte [] bytes123 = shaChecksum1.getBytes(StandardCharsets.UTF_8);
return Arrays.equals(bytes123,receivedDigest);
//return Arrays.equals(receivedDigest,shaChecksum1);

}
catch(NoSuchAlgorithmException e){
        System.out.println("Exception for NoSuchAlgorithmException in KeyGenInitialize function");
        return false;
        }
 catch(IOException e){
        System.out.println("IOEXCEPTION!");
        e.printStackTrace();
               }

return true;
}

private static String getFileChecksum(MessageDigest digest, File file) throws IOException
{
System.out.println("");
    //Get file input stream for reading the file content
    FileInputStream fis = new FileInputStream(file);
     
    //Create byte array to read data in chunks
    byte[] byteArray = new byte[1024];
    int bytesCount = 0; 
      
    //Read file data and update in message digest
    while ((bytesCount = fis.read(byteArray)) != -1) {
        digest.update(byteArray, 0, bytesCount);
    };
     
    //close the stream; We don't need it now.
    fis.close();
     
    //Get the hash's bytes
    byte[] bytes = digest.digest();
     
    //This bytes[] has bytes in decimal format;
    //Convert it to hexadecimal format
    StringBuilder sb = new StringBuilder();
    for(int i=0; i< bytes.length ;i++)
    {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
    }
     
    //return complete hash
   return sb.toString();
}





public int NetworkVeri(int i){
try {	
System.out.println("Network Verification Process start ");
	                                File file = new File("/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/01-GetStarted/Registration/"+i);
	                              //  System.out.println(file);
       final MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
String shaChecksum = getFileChecksum(shaDigest,file);
byte [] bytes12 = shaChecksum.getBytes(StandardCharsets.UTF_8);
//System.out.println(shaChecksum);
if(NDigestCheck(bytes12,i)){
                    System.out.println("Network Verified");
                    //New Point should be added 
                    }
                    else{
                    System.out.println("Network Not Verified");
return 0;
}
                    
         		}
	catch(IOException e){
               }
               catch(NoSuchAlgorithmException e)
        {
        System.out.println("Exception for NoSuchAlgorithmException in KeyGenInitialize function");
        }
        
                    return 1;


	}
	






	public int devicever(int i){
try {	
	                                File file = new File("/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/01-GetStarted/Keys/hash"+i+".txt");
       final MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
String shaChecksum = getFileChecksum(shaDigest,file);
byte [] bytes12 = shaChecksum.getBytes(StandardCharsets.UTF_8);
//System.out.println(shaChecksum);
if(DigestCheck(bytes12,i)){
                    System.out.println("Sensor Registration verified");
                    //New Point should be added 
                    }
                    else{
                    System.out.println("Sensor Registration denied");
return 0;
}
                    
         		}
	catch(IOException e){
               }
               catch(NoSuchAlgorithmException e)
        {
        System.out.println("Exception for NoSuchAlgorithmException in KeyGenInitialize function");
        }
        
                    return 1;


	}
	
	


   public Controller startController(String configFilePath) {
        InputStream configFileURI = null;
        if (configFilePath == null || configFilePath.isEmpty()) {
            configFileURI = this.getClass().getResourceAsStream("/config.ini");
        } else {
            try {
                configFileURI = new FileInputStream(configFilePath);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SdnWise.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Configurator conf = Configurator.load(configFileURI);
        controller = new ControllerFactory().getController(conf.getController());
        new Thread(controller).start();
        return controller;
    }
    
    


    


    public void startExample() {
        controller = startController("");

        System.out.println("SDN-WISE Controller running....");
        
        // We wait for the network to start 
        try {
            Thread.sleep(100);
            // Then we query the nodes
            while (true){    
                for (int i = 1; i < 50; i++){
                int k1=0;
                int k=0;
                //Every Iot Node have a make a System of Sensing Device That help to excute the Application or Services  
                System.out.println("Controller Checking Network Registration");
                k1=NetworkVeri(i);
                k=devicever(i);
             if (k1!=0)    //check the Network first and the trust value of network check Threshould ranges of trust is 0.5 to 1 
             //The Network Trust value depend on the Network Units if all the network units work properly then the Trust value of the network is 1 otherwise network trust is down
             // Experimental Setup will be create to customize the Trustscript in the Trust folder of the controller .The trust folder have trust policy that 0 and 1 (0 for Network unit not 
              //working properly the evaluation parameter is the Unknown network file)
                    { 
                    System.out.println("Controller ID"+"1");
                          if (k!=0) //check the device registration first and device trust on the controller 
                          {
                          
                          

try{
	
	File RecordFile = new File("/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/01-GetStarted/Registration/" + i);
	Scanner scanner = new Scanner(RecordFile);
	String lineFromFile;
	String thing = "Hello";
	int j = 0;
   while (scanner.hasNextLine()) {
    lineFromFile = scanner.nextLine();
   // System.out.println("Shahbaz Ka Baap ");
   if(lineFromFile.contains("")) { 
  // System.out.println("Shahbaz Ka Baap "+lineFromFile);
             System.out.println("Controller Checking Device Registration");
       System.out.println("- quering node " + i+"IP Address="+lineFromFile);
                
       }
       
       }

}
catch(FileNotFoundException e){
System.out.println("FILENOTFOUND");
}

        
                          
//                          System.out.println("Controller Checking Device Registration");
              System.out.println("- quering node " + i);
                    int netId = 1;
                    NodeAddress dst = new NodeAddress(i);
                    NodeAddress src = new NodeAddress(1);
    // Test();   
                    
                    DataPacket p = new DataPacket(netId,src,dst);
                    p.setNxhop(src);
                    System.out.println("Hello Ka baaps");
                    p.setPayload("Hello World!".getBytes(Charset.forName("UTF-8")));
                    controller.sendNetworkPacket(p);
                    Thread.sleep(2000);
                    p.setPayload("Shahbaz!".getBytes(Charset.forName("UTF-8")));
                    controller.sendNetworkPacket(p);
                    
                    
                       }
}                   }
                }            
                }
        
         catch (InterruptedException ex) {
            Logger.getLogger(SdnWise.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
}
	









	












	



