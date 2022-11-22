package com.github.sdnwiselab.sdnwise.cooja;

/*
 * Copyright (c) 2010, Swedish Institute of Computer Science.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the Institute nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE INSTITUTE AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE INSTITUTE OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 *
 */
 
import static com.github.sdnwiselab.sdnwise.cooja.Constants.*;
import com.github.sdnwiselab.sdnwise.flowtable.*;
import static com.github.sdnwiselab.sdnwise.flowtable.SetAction.*;
import static com.github.sdnwiselab.sdnwise.flowtable.Stats.SDN_WISE_RL_TTL_PERMANENT;
import static com.github.sdnwiselab.sdnwise.flowtable.Window.*;
import com.github.sdnwiselab.sdnwise.function.FunctionInterface;
import com.github.sdnwiselab.sdnwise.packet.*;
import static com.github.sdnwiselab.sdnwise.packet.ConfigAcceptedIdPacket.*;
import static com.github.sdnwiselab.sdnwise.packet.ConfigFunctionPacket.*;
import static com.github.sdnwiselab.sdnwise.packet.ConfigNodePacket.*;
import static com.github.sdnwiselab.sdnwise.packet.ConfigRulePacket.*;
import static com.github.sdnwiselab.sdnwise.packet.ConfigTimerPacket.*;
import static com.github.sdnwiselab.sdnwise.packet.NetworkPacket.*;

import com.github.sdnwiselab.sdnwise.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.nio.charset.StandardCharsets;
import javax.crypto.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.io.File;
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

import org.contikios.cooja.*;
import org.contikios.cooja.interfaces.*;
import org.contikios.cooja.motes.AbstractApplicationMote;
/*import org.contikios.cooja.util.CipherUtils;*/
import org.contikios.cooja.util.MultiChainUtils;
import org.jdesktop.swingx.search.ListSearchable;

import javax.crypto.BadPaddingException;
import java.security.NoSuchAlgorithmException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.io.BufferedReader;
 import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.io.Reader;
/**
 * Example SdnWise mote.
 *
 * This mote is simulated in COOJA via the Imported App Mote Type.
 *
 * @author Sebastiano Milardo
 */
public abstract class AbstractMote extends AbstractApplicationMote {

    public ArrayList<Integer> statusRegister = new ArrayList<>();

    private Simulation simulation = null;
    private Random random = null;
private float Vericount=0;
    private int sentBytes;
    private int receivedBytes;
    private boolean receiveData = false;
    private int sentDataBytes;
    private int receivedDataBytes;
    private int distanceFromSink;
    private int rssiSink;
    private int cntBeacon = 0;
    private int cntReport = 0;
    private int cntUpdTable = 0;
    private int HeartRate = 10;
     
   
    private HashMap<Integer, List<Byte>> _hashMap = new HashMap<Integer, List<Byte>>();

    ApplicationRadio radio = null;
    ApplicationLED leds = null;

    final ArrayBlockingQueue<NetworkPacket> flowTableQueue = new ArrayBlockingQueue<>(100);
    final ArrayBlockingQueue<NetworkPacket> txQueue = new ArrayBlockingQueue<>(100);

    int port,
            semaphore,
            flow_table_free_pos,
            accepted_id_free_pos,
            neighbors_number,
            net_id,
            cnt_beacon_max,
            cnt_report_max,
            cnt_updtable_max,
            cnt_sleep_max,
            ttl_max,
            rssi_min;

    NodeAddress addr;
    DatagramSocket socket;
    Battery battery = new Battery();
    ArrayList<Neighbor> neighborTable = new ArrayList<>(100);
    ArrayList<FlowTableEntry> flowTable = new ArrayList<>(100);
    ArrayList<NodeAddress> acceptedId = new ArrayList<>(100);

    HashMap<String, Object> adcRegister = new HashMap<>();
    HashMap<Integer, LinkedList<int[]>> functionBuffer = new HashMap<>();
    HashMap<Integer, FunctionInterface> functions = new HashMap<>();
    Logger MeasureLOGGER;


    public AbstractMote() {
        super();
    }

    public AbstractMote(MoteType moteType, Simulation simulation) {
        super(moteType, simulation);
    }

    @Override
    public void execute(long time) {

        if (radio == null) {
            setup();
        }

        int delay = random.nextInt(10);

        simulation.scheduleEvent(
                new MoteTimeEvent(this, 0) {
                    @Override
                    public void execute(long t) {
                        timerTask();
                        logTask();
                    }
                },
                simulation.getSimulationTime()
                + (1000 + delay) * Simulation.MILLISECOND
        );
    }

    @Override
    public void receivedPacket(RadioPacket p) {
        byte[] packetData = p.getPacketData();
        NetworkPacket np = new NetworkPacket(packetData);
        if (np.getDst().isBroadcast()
                || np.getNxhop().equals(addr)
                || acceptedId.contains(np.getNxhop())) {
            rxHandler(new NetworkPacket(packetData), 255);
        }
    }

    @Override
    public void sentPacket(RadioPacket p) {
    }

    @Override
    public String toString() {
        return "SDN-WISE Mote " + getID();
    }

    public final void setDistanceFromSink(int num_hop_vs_sink) {
        this.distanceFromSink = num_hop_vs_sink;
    }

    public final void setRssiSink(int rssi_vs_sink) {
        this.rssiSink = rssi_vs_sink;
    }

    public final void setSemaphore(int semaforo) {
        this.semaphore = semaforo;
    }

    public final int getDistanceFromSink() {
        /* */
        return distanceFromSink;
        
    }
    
    private static void printUsage() {
  OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
  for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
    method.setAccessible(true);
    if (method.getName().startsWith("get")
        && Modifier.isPublic(method.getModifiers())) {
            Object value;
        try {
            value = method.invoke(operatingSystemMXBean);
        } catch (Exception e) {
            value = e;
        } // try
        System.out.println(method.getName() + " = " + value);
    } // if
  } // for
}

    public final int getRssiSink() {
        return rssiSink;
    }

    public void initSdnWise() {

        cnt_beacon_max = SDN_WISE_DFLT_CNT_BEACON_MAX;
        cnt_report_max = SDN_WISE_DFLT_CNT_REPORT_MAX;
        cnt_updtable_max = SDN_WISE_DFLT_CNT_UPDTABLE_MAX;
        rssi_min = SDN_WISE_DFLT_RSSI_MIN;
        ttl_max = SDN_WISE_DFLT_TTL_MAX;

        battery = new Battery();
        flow_table_free_pos = 1;
        accepted_id_free_pos = 0;
    }

    public final void radioTX(final NetworkPacket np) {
        sentBytes += np.getLen();
        if (np.getType() > 7 && !np.isRequest()) {
            sentDataBytes += np.getPayloadSize();
        }

        battery.transmitRadio(np.getLen());
        //System.out.println("Shahbaz Siddiqui "+battery.transmitRadio(np.getLen()));
        np.decrementTtl();
        RadioPacket pk = new COOJARadioPacket(np.toByteArray());
        if (radio.isTransmitting() || radio.isReceiving()) {
            simulation.scheduleEvent(
                    new MoteTimeEvent(this, 0) {
                        @Override
                        public void execute(long t) {
                            radioTX(np);
                        }
                    },
                    simulation.getSimulationTime()
                            + 1 * Simulation.MILLISECOND
            );
        } else {
            radio.startTransmittingPacket(pk, 1 * Simulation.MILLISECOND);
        }
    }

    public final NodeAddress getNextHopVsSink() {
        return ((AbstractForwardAction) (flowTable.get(0).getActions().get(0))).getNextHop();
    }

/*
    public final void rxData(DataPacket packet) {
    
        if (isAcceptedIdPacket(packet)) {
        	Json json = null;
        	int src = packet.getSrc().intValue();
    System.out.println("Abstract2");    	
        	if (this.getID() != 1) {
	        	String payload = new String(packet.getPayload());
	        	//System.out.println(this.getID()+"String payload"+"/n");
    	    		System.out.println(this.getID() + "Mote just received a packet. The Data in Packet was:");
    	    		System.out.println(payload);
	    	    			//System.out.println(src+" Certificate");
    	    		
			if (!payload.equals("EOP")) {
			if (payload.equals("Register")) {
	        	
	        	try{
	        	boolean amiallowed = true;
	        	System.out.println("Sending relevant data");
	        	Socket s=new Socket("ubunu2004",3333);  
			DataInputStream din=new DataInputStream(s.getInputStream());  
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());
			String Location = ("/home/ubuntu/Desktop/contiki/tools/cooja/build/keys/" + this.getID() + "-512.key");
        		Path path = Paths.get(Location);
        		byte[] bytes = Files.readAllBytes(path);
        		PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
        		KeyFactory kf = KeyFactory.getInstance("RSA");
        		PrivateKey pvt = kf.generatePrivate(ks);
        		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        		cipher.init(Cipher.ENCRYPT_MODE, pvt);
        		byte[] response = "Please Register Me".getBytes();
        		if(this.getID() == 5 || this.getID() == 10 || this.getID() == 16 || this.getID() == 21)
        		{
        		response = "Please do NOT Register Me".getBytes();
        		amiallowed = false;
        		}
        		
        		MessageDigest md = MessageDigest.getInstance("SHA-256");
        		md.update(response);
     			byte [] digest = md.digest();
        		cipher.update(response);
			byte[] cipherText = cipher.doFinal();
			dout.writeInt(cipherText.length);
			dout.write(cipherText);
			Location = ("/home/ubuntu/Desktop/contiki/tools/cooja/build/keys/" + this.getID() + "-512.pub");
			path = Paths.get(Location);
        		bytes = Files.readAllBytes(path);
        		X509EncodedKeySpec kspub = new X509EncodedKeySpec(bytes);
        		PublicKey pub = kf.generatePublic(kspub);
        		byte[] input = pub.getEncoded();
        		dout.writeInt(input.length);
        		dout.write(input);
        		dout.writeInt(digest.length);
        		dout.write(digest);
			dout.close();
			s.close();
			System.out.println(new String(response));
			if(amiallowed){
			String uniqueid = UUID.randomUUID().toString();
			MultiChainUtils.writeRegistrationToStream("Registration",Integer.toString(this.getID()),uniqueid);
			}
			
			}
			catch(UnsupportedEncodingException e){
        System.out.println("UnsupportedEncodingException");
               }
               catch (IllegalBlockSizeException e){
    System.out.println("Encountered IllegalBlockSizeException!");
    }
   // catch (InterruptedException e){
    //System.out.println("Encountered INTERRUPTEDEXCEPTION!;");
    //}
			catch(IOException e){
        System.out.println("IOEXCEPTION specifically with sending registration!");
               }
			catch(InvalidKeySpecException e){
        System.out.println("INVALIDKEYEXCEPTION");
        }	
        catch(InvalidKeyException e){
    System.out.println("Encountered InvalidKeyException!");
    }
    catch (BadPaddingException e){
    System.out.println("Encountered BadPaddingException!");
    }
    
        catch(NoSuchPaddingException e){
    System.out.println("Encountered NoSuchPaddingException!");
    }
			catch(NoSuchAlgorithmException e)
        {
        System.out.println("Exception for NoSuchAlgorithmException in KeyGenInitialize function");
        }
			}else{
	        		if(receiveData == true){
	        		System.out.println("Already received this data, waiting for EOP or more data.");
	        		
	        		}
	        		else{
	        		System.out.println("Still Receiving Data Packets!");
	        		List<Byte> fragmentedMessageBytes = new ArrayList<Byte>();
	        		
	        		if (_hashMap.containsKey(src)) {
	        			fragmentedMessageBytes = _hashMap.get(src);
	        			       	
	        		}
	        		
	        		for (byte b : packet.getPayload()) {
	        			fragmentedMessageBytes.add(b);
	        		}

	        		_hashMap.put(src, fragmentedMessageBytes);
	        		receiveData = true;
	        		//System.out.println(_hashMap.get(src).size()+"Kamiiiiiiii"+"/n");
	        		SDN_WISE_Callback(packet);
	        		//SHahbaz Siddiqui Code //
	        	}}}
	        	else if(receiveData == true){
	        		        		System.out.println("All Data Packets received and the size is " + _hashMap.get(src).size());
	        		Integer i = 0;
    	    		byte[] completeMessageBytes = new byte[_hashMap.get(src).size()];
    	    		
    	    		
    	    		for (Byte b : _hashMap.get(src)) {
    	    			completeMessageBytes[i++] = b.byteValue();
    	    		}
    	    		    	    			System.out.println("Finished and Decrypted Data is:");
    	    		    	    			System.out.println(new String (completeMessageBytes));
    	    		    	    			try{
    	    		    	    			KeyFactory kf = KeyFactory.getInstance("RSA");
    	    	    		String Location = ("/home/ubuntu/Desktop/contiki/tools/cooja/build/keys/1-512.pub");
        Path path = Paths.get(Location);
        byte[] bytes = Files.readAllBytes(path);
        X509EncodedKeySpec ks509 = new X509EncodedKeySpec(bytes);
        PublicKey pub = kf.generatePublic(ks509);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, pub);
         System.out.println(new String(cipher.doFinal(completeMessageBytes)));
         _hashMap.clear();
         if(amitrusted()){
         initShahbaz("ShahJUN");
         }
         else{
         initShahbaz("TRIAL_TEMP");
         }
         receiveData = false;
         }
         catch(NoSuchAlgorithmException e){
        System.out.println("Exception for NoSuchAlgorithmException in KeyGenInitialize function");
        }
    catch(IOException e){
        System.out.println("IOEXCEPTION! Means error in reading/handling the input from files!");
               }
    catch(InvalidKeySpecException e){
        System.out.println("INVALIDKEYSPECEXCEPTION! Means that your key is incorrectly specified");
        }
    catch(BadPaddingException e){
    System.out.println("Encountered BADPADDINGEXCEPTION! Issue with padding. Again check your KeyFactory types");
        }
    catch(NoSuchPaddingException e){
    System.out.println("Encountered NoSuchPaddingException! Means that you have used an invalid format for handling padding. This issue should not occur generally as it means you messed up in the KeyFactory declaration somewhere");
    }
    
    catch(InvalidKeyException e){
    System.out.println("Encountered InvalidKeyException! Means Key is either incorrect or has some sort of issue");
    }
    
    catch (IllegalBlockSizeException e){
    System.out.println("Encountered IllegalBlockSizeException! This means that the encrypted data/key is correct but there is issue in the LENGTH of the data");
    }
	    	    	
            		
	        	}
        	}
        	else {
        		SDN_WISE_Callback(packet);
        	}
        } else if (isAcceptedIdAddress(packet.getNxhop())) {
            runFlowMatch(packet);
        }
    }


  
*/

/*
static void Test() {

String pythonScriptPath = "/home/ubuntu/Desktop/contiki/tools/cooja/examples/sdn-wise_java/java/com/github/sdnwiselab/sdnwise/cooja/Script.py";

try {

           String[] cmd = { "python3", pythonScriptPath};
//System.out.println("Gandu"+cmd);
        // create runtime to execute external command
        ProcessBuilder pb = new ProcessBuilder(cmd);

        // retrieve output from python script       
      //System.out.println(pb.redirectError())  ;

        
            Process p = pb.start();
            p.waitFor();
           // System.out.println("Process Started..."+p.getInputStream());
            //InputStreamReader y= new InputStreamReader(p.getInputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
          System.out.println(""+in.readLine());
            //int ret = new Integer(in.readLine()).intValue();
            //System.out.println("value is : "+ret);
 String line=" ";
     System.out.println("Farrukh"+in.readLine());  
          while ((line=in.readLine()) != null)  {   
          System.out.println("Taluuuuuuuuuuuuuuuuu");                                                                         
             System.out.println(line+"\n");                                                                                        
          }

 
}

*/

public void Trust(){
            
            try {
        
        
        File RecordFile = new File("/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/01-GetStarted/Trust Management/Trust"+this.getID());
	Scanner scanner = new Scanner(RecordFile);
	String lineFromFile;
	
	//Behaviour Adding values 
	//Behaviour reported to the neighbour
	
 
 //Equation Setting {(1-Alpha)Trust(t-Deta(t)+Alpha dIRECT oBSERVATION(dELTA t)}`
 
  while (scanner.hasNextLine()) {
        String data = scanner.nextLine();
        System.out.println("Shahbaz Siddqiui "+data);
            float data1=Float.parseFloat(data);
            double weight=(0.75) ; 
        Vericount=(float)((data1)+(0.01));   
      }
      scanner.close();
      
        
        
        
        System.out.println("Trust"+Vericount);   
     
       
        
 
            // Again operating same operations by passing
            // file as
            // parameter to read it
            BufferedWriter out = new BufferedWriter(
                new FileWriter(RecordFile));
System.out.println("Current Trust of Node"+this.getID()+"="+Vericount);
            // Writing on. file
            out.write(String.valueOf(Vericount));
 
            // Closing file connections
            out.close();
        }
 
        // Catch block to handle exceptions
        catch (IOException e) {
 
            // Display message when error occurs
            System.out.println("Exception Occurred" + e);
        }
        
       
       try {  
                 Thread.sleep(1000);
                      //System.out.println("UP2");
 
                 }
                      catch (InterruptedException ex) {
  // Stop immediately and go home
}
       
                    
                    
  }







	public boolean DigestCheck(byte[] receivedDigest, int i){
	try{
File file = new File("/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/01-GetStarted/Keys/hash"+i+".txt");
MessageDigest shaDigest1 = MessageDigest.getInstance("SHA-256");
final String shaChecksum1 = getFileChecksum(shaDigest1,file);
System.out.println(shaChecksum1);
byte [] bytes123 = shaChecksum1.getBytes(StandardCharsets.UTF_8);
Trust();// Atleast the Everynodes should have atleast 10 

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
//System.out.println("Abey harami");
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


















      
                    
                    
  









    public final void rxData(DataPacket packet) {
          try {  
                 Thread.sleep(1000);
                //      System.out.println("Delay Created By Me ");
 
                 }
                      catch (InterruptedException ex) {
  // Stop immediately and go home
}

    
        if (isAcceptedIdPacket(packet)) {
        
        
        	Json json = null;
        	int src = packet.getSrc().intValue();
        
     //Network Registration Conform Fiorst 
     //Devic Registratiion Confirmed 
     //Service Registration
     
     try {	
     
     
     
             
       

	                                File file = new File("/home/ubuntu/Desktop/Shahbaz Final Project/Ada-Framework-Shahbaz1/01-GetStarted/Keys/hash"+1+".txt");
       final MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
String shaChecksum = getFileChecksum(shaDigest,file);
byte [] bytes12 = shaChecksum.getBytes(StandardCharsets.UTF_8);
//System.out.println(shaChecksum);

if(DigestCheck(bytes12,1)){
                    System.out.println("Node"+this.getID()+"Verifed");
                    String uniqueid = UUID.randomUUID().toString();
	
           
            
                    }
                    else{
                    System.out.println("Node"+this.getID()+"Not Verifed");

}
                    
         		}
	catch(IOException e){
               }
               catch(NoSuchAlgorithmException e)
        {
        System.out.println("Exception for NoSuchAlgorithmException in KeyGenInitialize function");
        }
        


     
        
        
        
         	    			
	    	    			
            SDN_WISE_Callback(packet);
        } else if (isAcceptedIdAddress(packet.getNxhop())) {
            runFlowMatch(packet);
        }
    }
    
    
    
    
   
    
    
    
    
    
    
    
    
    public int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
}

    public void rxBeacon(BeaconPacket bp, int rssi) {
        int index = getNeighborIndex(bp.getSrc());

        if (index != (SDN_WISE_NEIGHBORS_MAX + 1)) {
            if (index != -1) {
                neighborTable.get(index).setRssi(rssi);
                neighborTable.get(index).setBatt(bp.getBatt());
            } else {
                neighborTable.get(neighbors_number).setAddr(bp.getSrc());
                neighborTable.get(neighbors_number).setRssi(rssi);
                neighborTable.get(neighbors_number).setBatt(bp.getBatt());
                neighbors_number++;
            }
        }
    }
    public boolean amitrusted(){
    try{
	
	File RecordFile = new File("/home/ubuntu/Desktop/records/Registration/output" + this.getID() + ".txt");
	Scanner scanner = new Scanner(RecordFile);
	String lineFromFile;
	String thing = "Hello";
	int j = 0;
   while (scanner.hasNextLine()) {
    lineFromFile = scanner.nextLine();
   if(lineFromFile.contains("confirmed")) { 
    thing = lineFromFile.replaceAll("[^0-9]", "");
       j = Integer.parseInt(thing);
      
       }
       
       }
       if(j>25){
       return true;}
       else{
       return false;
       }


}
catch(FileNotFoundException e){
System.out.println("FILENOTFOUND");
return false;}
}
    
    public final void runFlowMatch(NetworkPacket packet) {
        int j, i, found = 0;
        for (j = 0; j < SDN_WISE_RLS_MAX; j++) {
            i = getActualFlowIndex(j);
            if (matchRule(flowTable.get(i), packet) == 1) {
                log("Matched Rule #" + j + " " + flowTable.get(i).toString());
                found = 1;
                //System.out.println("Shabaz Siddiqui");
                for (AbstractAction a : flowTable.get(i).getActions()) {
                    runAction(a, packet);
                }
                flowTable.get(i).getStats()
                        .setCounter(flowTable.get(i).getStats().getCounter() + 1);
                break;
            }
        }
        if (found == 0) { //!found
            // It's necessary to send a rule/request if we have done the lookup
            // I must modify the source address with myself,
            packet.setSrc(addr)
                    .setRequestFlag()
                    .setTtl(SDN_WISE_DFLT_TTL_MAX);
            controllerTX(packet);
        }
    }

    public abstract void rxConfig(ConfigPacket packet);

    public NodeAddress getActualSinkAddress() {
        return new NodeAddress(flowTable.get(0).getWindows().get(0).getRhs());
    }

    public abstract void SDN_WISE_Callback(DataPacket packet);

    public abstract void controllerTX(NetworkPacket pck);

    public int marshalPacket(ConfigPacket packet) {
        int toBeSent = 0;
        int pos;
        boolean isWrite = packet.isWrite();
        int id = packet.getConfigId();
        int value = packet.getPayloadAt(1) * 256 + packet.getPayloadAt(2);
        if (isWrite) {
            switch (id) {
                case SDN_WISE_CNF_ID_ADDR:
                    addr = new NodeAddress(value);
                    break;
                case SDN_WISE_CNF_ID_NET_ID:
                    net_id = packet.getPayloadAt(2);
                    break;
                case SDN_WISE_CNF_ID_CNT_BEACON_MAX:
                    cnt_beacon_max = value;
                    break;
                case SDN_WISE_CNF_ID_CNT_REPORT_MAX:
                    cnt_report_max = value;
                    break;
                case SDN_WISE_CNF_ID_CNT_UPDTABLE_MAX:
                    cnt_updtable_max = value;
                    break;
                case SDN_WISE_CNF_ID_CNT_SLEEP_MAX:
                    cnt_sleep_max = value;
                    break;
                case SDN_WISE_CNF_ID_TTL_MAX:
                    ttl_max = packet.getPayloadAt(2);
                    break;
                case SDN_WISE_CNF_ID_RSSI_MIN:
                    rssi_min = packet.getPayloadAt(2);
                    break;
                case SDN_WISE_CNF_ADD_ACCEPTED:
                    pos = searchAcceptedId(new NodeAddress(value));
                    if (pos == (SDN_WISE_ACCEPTED_ID_MAX + 1)) {
                        pos = searchAcceptedId(new NodeAddress(65535));
                        acceptedId.set(pos, new NodeAddress(value));
                    }
                    break;
                case SDN_WISE_CNF_REMOVE_ACCEPTED:
                    pos = searchAcceptedId(new NodeAddress(value));
                    if (pos != (SDN_WISE_ACCEPTED_ID_MAX + 1)) {
                        acceptedId.set(pos, new NodeAddress(65535));
                    }
                    break;
                case SDN_WISE_CNF_REMOVE_RULE_INDEX:
                    if (value != 0) {
                        flowTable.set(getActualFlowIndex(value), new FlowTableEntry());
                    }
                    break;
                case SDN_WISE_CNF_REMOVE_RULE:
                    //TODO
                    break;
                case SDN_WISE_CNF_ADD_FUNCTION:
                    if (functionBuffer.get(value) == null) {
                        functionBuffer.put(value, new LinkedList<int[]>());
                    }
                    functionBuffer.get(value).add(Arrays.copyOfRange(
                            packet.toIntArray(), SDN_WISE_DFLT_HDR_LEN + 5,
                            packet.getLen()));
                    if (functionBuffer.get(value).size() == packet.getPayloadAt(4)) {
                        int total = 0;
                        for (int[] n : functionBuffer.get(value)) {
                            total += (n.length);
                        }
                        int pointer = 0;
                        byte[] func = new byte[total];
                        for (int[] n : functionBuffer.get(value)) {
                            for (int j = 0; j < n.length; j++) {
                                func[pointer] = (byte) n[j];
                                pointer++;
                            }
                        }
                        functions.put(value, createServiceInterface(func));
                        log("New Function Added at position: " + value);
                        functionBuffer.remove(value);
                    }
                    break;
                case SDN_WISE_CNF_REMOVE_FUNCTION:
                    functions.remove(value);
                    break;
                default:
                    break;
            }
        } else {
            toBeSent = 1;
            switch (id) {
                case SDN_WISE_CNF_ID_ADDR:
                    packet.setPayloadAt(addr.getHigh(), 1);
                    packet.setPayloadAt(addr.getLow(), 2);
                    break;
                case SDN_WISE_CNF_ID_NET_ID:
                    packet.setPayloadAt((byte) net_id, 2);
                    break;
                case SDN_WISE_CNF_ID_CNT_BEACON_MAX:
                    packet.setPayloadAt((byte) (cnt_beacon_max >> 8), 1);
                    packet.setPayloadAt((byte) (cnt_beacon_max), 2);
                    break;
                case SDN_WISE_CNF_ID_CNT_REPORT_MAX:
                    packet.setPayloadAt((byte) (cnt_report_max >> 8), 1);
                    packet.setPayloadAt((byte) (cnt_report_max), 2);
                    break;
                case SDN_WISE_CNF_ID_CNT_UPDTABLE_MAX:
                    packet.setPayloadAt((byte) (cnt_updtable_max >> 8), 1);
                    packet.setPayloadAt((byte) (cnt_updtable_max), 2);
                    break;
                case SDN_WISE_CNF_ID_CNT_SLEEP_MAX:
                    packet.setPayloadAt((byte) (cnt_sleep_max >> 8), 1);
                    packet.setPayloadAt((byte) (cnt_sleep_max), 2);
                    break;
                case SDN_WISE_CNF_ID_TTL_MAX:
                    packet.setPayloadAt((byte) ttl_max, 2);
                    break;
                case SDN_WISE_CNF_ID_RSSI_MIN:
                    packet.setPayloadAt((byte) rssi_min, 2);
                    break;
                case SDN_WISE_CNF_LIST_ACCEPTED:
                    toBeSent = 0;
                    ConfigAcceptedIdPacket packetList
                            = new ConfigAcceptedIdPacket(
                                    net_id,
                                    packet.getDst(),
                                    packet.getSrc());
                    packetList.setReadAcceptedAddressesValue();
                    int ii = 1;

                    for (int jj = 0; jj < SDN_WISE_ACCEPTED_ID_MAX; jj++) {
                        if (!acceptedId.get(jj).equals(new NodeAddress(65535))) {
                            packetList.setPayloadAt((acceptedId.get(jj)
                                    .getHigh()), ii);
                            ii++;
                            packetList.setPayloadAt((acceptedId.get(jj)
                                    .getLow()), ii);
                            ii++;
                        }
                    }
                    controllerTX(packetList);
                    break;
                case SDN_WISE_CNF_GET_RULE_INDEX:
                    toBeSent = 0;
                    ConfigRulePacket packetRule = new ConfigRulePacket(
                            net_id,
                            packet.getDst(),
                            packet.getSrc()
                    );
                    int jj = getActualFlowIndex(value);
                    packetRule.setRule(flowTable.get(jj))
                            .setPayloadAt(SDN_WISE_CNF_GET_RULE_INDEX, 0)
                            .setPayloadAt(packet.getPayloadAt(1), 1)
                            .setPayloadAt(packet.getPayloadAt(2), 2);
                    controllerTX(packetRule);
                    break;
                default:
                    break;
            }
        }
        return toBeSent;
    }

    private void timerTask() {
        if (semaphore == 1 && battery.getBatteryLevel() > 0) {
            battery.keepAlive(1);

            cntBeacon++;
            cntReport++;
            cntUpdTable++;

            if ((cntBeacon) >= cnt_beacon_max) {
                cntBeacon = 0;
                radioTX(prepareBeacon());
            }

            if ((cntReport) >= cnt_report_max) {
                cntReport = 0;
                controllerTX(prepareReport());
            }

            if ((cntUpdTable) >= cnt_updtable_max) {
                cntUpdTable = 0;
                updateTable();
            }
        }
        requestImmediateWakeup();
    }

    private void initFlowTable() {
        FlowTableEntry toSink = new FlowTableEntry();
        toSink.addWindow(new Window()
                .setOperator(SDN_WISE_EQUAL)
                .setSize(SDN_WISE_SIZE_2)
                .setLhsLocation(SDN_WISE_PACKET)
                .setLhs(SDN_WISE_DST_H)
                .setRhsLocation(SDN_WISE_CONST)
                .setRhs(this.addr.intValue()));
        toSink.addWindow(Window.fromString("P.TYPE > 127"));
        toSink.addAction(new ForwardUnicastAction()
                .setNextHop(addr));
        toSink.getStats().setPermanent();
        flowTable.add(0, toSink);

        for (int i = 1; i < SDN_WISE_RLS_MAX; i++) {
            flowTable.add(i, new FlowTableEntry());
        }
    }

    private void rxReport(ReportPacket packet) {
    //Creating priority Based Services paper Refer to //
    
    
    
        controllerTX(packet);

    }

    private FunctionInterface createServiceInterface(byte[] classFile) {
        CustomClassLoader cl = new CustomClassLoader();
        FunctionInterface srvI = null;
        Class service = cl.defClass(classFile, classFile.length);
        try {
            srvI = (FunctionInterface) service.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            log(ex.getLocalizedMessage());
        }
        return srvI;
    }

    private void rxResponse(ResponsePacket rp) {
        if (isAcceptedIdPacket(rp)) {
            rp.getRule().setStats(new Stats());
            insertRule(rp.getRule(), searchRule(rp.getRule()));
            //System.out.println("Stolo");
        } else {
            runFlowMatch(rp);
        }
    }

    private void rxOpenPath(OpenPathPacket opp) {
        if (isAcceptedIdPacket(opp)) {
            List<NodeAddress> path = opp.getPath();
            for (int i = 0; i < path.size(); i++) {
                NodeAddress actual = path.get(i);
                if (isAcceptedIdAddress(actual)) {
                    if (i != 0) {
                        FlowTableEntry rule = new FlowTableEntry();
                        rule.addWindow(new Window()
                                .setOperator(SDN_WISE_EQUAL)
                                .setSize(SDN_WISE_SIZE_2)
                                .setLhsLocation(SDN_WISE_PACKET)
                                .setLhs(SDN_WISE_DST_H)
                                .setRhsLocation(SDN_WISE_CONST)
                                .setRhs(path.get(0).intValue()));
                        rule.getWindows().addAll(opp.getOptionalWindows());
                        rule.addAction(new ForwardUnicastAction()
                                .setNextHop(path.get(i - 1))
                        );
                        int p = searchRule(rule);
                        insertRule(rule, p);
                    }

                    if (i != (path.size() - 1)) {
                        FlowTableEntry rule = new FlowTableEntry();
                        rule.addWindow(new Window()
                                .setOperator(SDN_WISE_EQUAL)
                                .setSize(SDN_WISE_SIZE_2)
                                .setLhsLocation(SDN_WISE_PACKET)
                                .setLhs(SDN_WISE_DST_H)
                                .setRhsLocation(SDN_WISE_CONST)
                                .setRhs(path.get(path.size() - 1).intValue()));

                        rule.getWindows().addAll(opp.getOptionalWindows());
                        rule.addAction(new ForwardUnicastAction()
                                .setNextHop(path.get(i + 1))
                        );

                        int p = searchRule(rule);
                        insertRule(rule, p);
                        opp.setDst(path.get(i + 1));
                        opp.setNxhop(path.get(i + 1));

                        radioTX(opp);
                        break;
                    }
                }
            }
        } else {
            runFlowMatch(opp);
        }
    }

    private void insertRule(FlowTableEntry rule, int pos) {
        if (pos >= SDN_WISE_RLS_MAX) {
            pos = flow_table_free_pos;
            flow_table_free_pos++;
            if (flow_table_free_pos >= SDN_WISE_RLS_MAX) {
                flow_table_free_pos = 1;
            }
        }
        log("Inserting rule " + rule + " at position " + pos);
        flowTable.set(pos, rule);
    }

    private int searchRule(FlowTableEntry rule) {
        int i, j, sum, target;
        for (i = 0; i < SDN_WISE_RLS_MAX; i++) {
            sum = 0;
            target = rule.getWindows().size();
            if (flowTable.get(i).getWindows().size() == target) {
                for (j = 0; j < rule.getWindows().size(); j++) {
                    if (flowTable.get(i).getWindows().get(j).equals(rule.getWindows().get(j))) {
                        sum++;
                    }
                }
            }
            if (sum == target) {
                return i;
            }
        }
        return SDN_WISE_RLS_MAX + 1;
    }

    private boolean isAcceptedIdAddress(NodeAddress addrP) {
        return (addrP.equals(addr)
                || addrP.isBroadcast()
                || (searchAcceptedId(addrP)
                != SDN_WISE_ACCEPTED_ID_MAX + 1));
    }

    private boolean isAcceptedIdPacket(NetworkPacket packet) {
        return isAcceptedIdAddress(packet.getDst());
    }

    private void rxHandler(NetworkPacket packet, int rssi) {
        
        if (packet.getLen() > SDN_WISE_DFLT_HDR_LEN
                && packet.getNetId() == net_id
                && packet.getTtl() != 0) {

            if (packet.isRequest()) {
                controllerTX(packet);
            } else {
                switch (packet.getType()) {
                    case SDN_WISE_DATA:
//Time Stamp for Experiment //
                        rxData(new DataPacket(packet));

                        break;

                    case SDN_WISE_BEACON:
//Time Stamp for Experiment //
 
                        rxBeacon(new BeaconPacket(packet), rssi);
                        break;

                    case SDN_WISE_REPORT:
//Time Stamp for Experiment //
                              
      
                        rxReport(new ReportPacket(packet));
      
                        //Creating priority Based Services paper Refer to //
                          
  
    //System.out.println("Packet Detail");
                        
                               
                        break;

                    case SDN_WISE_RESPONSE:

                        rxResponse(new ResponsePacket(packet));
                        break;

                    case SDN_WISE_OPEN_PATH:

                        rxOpenPath(new OpenPathPacket(packet));
                        break;

                    case SDN_WISE_CONFIG:

                        rxConfig(new ConfigPacket(packet));
                        break;

                    default:

                        runFlowMatch(packet);
                        break;
                }
            }
        }
    }

    private void initNeighborTable() {
        int i;
        for (i = 0; i < SDN_WISE_NEIGHBORS_MAX; i++) {
            neighborTable.add(i, new Neighbor());
        }
        neighbors_number = 0;
    }

    private void initStatusRegister() {
        for (int i = 0; i < SDN_WISE_STATUS_LEN; i++) {
            statusRegister.add(0);
        }
    }

    private void initAcceptedId() {
        for (int i = 0; i < SDN_WISE_ACCEPTED_ID_MAX; i++) {
            acceptedId.add(i, new NodeAddress(65535));
        }
    }
//Shahbaz Define Function //

   
    private void setup() {

        addr = new NodeAddress(this.getID());
        net_id = (byte) 1;
        simulation = getSimulation();
        random = simulation.getRandomGenerator();
        radio = (ApplicationRadio) getInterfaces().getRadio();
        leds = (ApplicationLED) getInterfaces().getLED();
        MeasureLOGGER = Logger.getLogger("Measure" + addr.toString());
        MeasureLOGGER.setLevel(Level.parse("FINEST"));
        try {
            FileHandler fh;
            File dir = new File("logs");
            dir.mkdir();
            fh = new FileHandler("logs/Measures" + addr + ".log");
            fh.setFormatter(new SimplestFormatter());
            MeasureLOGGER.addHandler(fh);
            MeasureLOGGER.setUseParentHandlers(false);
        } catch (IOException | SecurityException ex) {
            log(ex.getLocalizedMessage());
        }
        neighborTable = new ArrayList<>(SDN_WISE_NEIGHBORS_MAX);
        acceptedId = new ArrayList<>(SDN_WISE_ACCEPTED_ID_MAX);
        flowTable = new ArrayList<>(50);

        initFlowTable();
        initNeighborTable();
        initAcceptedId();
        initStatusRegister();
        initSdnWise();
        
       
        
        
new Thread(new PacketManager()).start();
        new Thread(new PacketSender()).start();
    }

    private int getOperand(NetworkPacket packet, int size, int location, int value) {
        int[] intPacket = packet.toIntArray();
        switch (location) {
            case SDN_WISE_NULL:
                return 0;
            case SDN_WISE_CONST:
                return value;
            case SDN_WISE_PACKET:
                if (size == SDN_WISE_SIZE_1) {
                    if (value >= intPacket.length) {
                        return -1;
                    }
                    return intPacket[value];
                }
                if (size == SDN_WISE_SIZE_2) {
                    if (value + 1 >= intPacket.length) {
                        return -1;
                    }
                    return Utils.mergeBytes(intPacket[value], intPacket[value + 1]);
                }
            case SDN_WISE_STATUS:
                if (size == SDN_WISE_SIZE_1) {
                    if (value >= statusRegister.size()) {
                        return -1;
                    }
                    return statusRegister.get(value);
                }
                if (size == SDN_WISE_SIZE_2) {
                    if (value + 1 >= statusRegister.size()) {
                        return -1;
                    }
                    return Utils.mergeBytes(
                            statusRegister.get(value),
                            statusRegister.get(value + 1));
                }
        }
        return -1;
    }

	private int matchWindow(Window window, NetworkPacket packet) {
        int operator = window.getOperator();
        int size = window.getSize();
        int lhs = getOperand(
                packet, size, window.getLhsLocation(), window.getLhs());
        int rhs = getOperand(
                packet, size, window.getRhsLocation(), window.getRhs());
        return compare(operator, lhs, rhs);
    }

    private int matchRule(FlowTableEntry rule, NetworkPacket packet) {
        if (rule.getWindows().isEmpty()) {
            return 0;
        }

        int target = rule.getWindows().size();
        int actual = 0;

        for (Window w : rule.getWindows()) {
            actual = actual + matchWindow(w, packet);
        }
        return (actual == target ? 1 : 0);
    }

    private void runAction(AbstractAction action, NetworkPacket np) {
        try {
            int action_type = action.getType();

            switch (action_type) {
                case SDN_WISE_FORWARD_U:
                case SDN_WISE_FORWARD_B:
                    np.setNxhop(((AbstractForwardAction) action).getNextHop());
                    radioTX(np);
                    break;

                case SDN_WISE_DROP:
                    break;
                case SDN_WISE_SET:
                    SetAction ftam = (SetAction) action;
                    int operator = ftam.getOperator();
                    int lhs = getOperand(
                            np, SDN_WISE_SIZE_1, ftam.getLhsLocation(), ftam.getLhs());
                    int rhs = getOperand(
                            np, SDN_WISE_SIZE_1, ftam.getRhsLocation(), ftam.getRhs());
                    if (lhs == -1 || rhs == -1) {
                        throw new IllegalArgumentException("Operators out of bound");
                    }
                    int res = doOperation(operator, lhs, rhs);
                    if (ftam.getResLocation() == SDN_WISE_PACKET) {
                        int[] packet = np.toIntArray();
                        if (ftam.getRes() >= packet.length) {
                            throw new IllegalArgumentException("Result out of bound");
                        }
                        packet[ftam.getRes()] = res;
                        np.setArray(packet);
                    } else {
                        statusRegister.set(ftam.getRes(), res);
                        log("SET R." + ftam.getRes() + " = " + res + ". Done.");
                    }
                    break;
                case SDN_WISE_FUNCTION:
                    FunctionAction ftac = (FunctionAction) action;
                    FunctionInterface srvI = functions.get(ftac.getCallbackId());
                    if (srvI != null) {
                        log("Function called: " + addr);
                        srvI.function(adcRegister,
                                flowTable,
                                neighborTable,
                                statusRegister,
                                acceptedId,
                                flowTableQueue,
                                txQueue,
                                ftac.getArg0(),
                                ftac.getArg1(),
                                ftac.getArg2(),
                                np
                        );
                    }
                    break;
                case SDN_WISE_ASK:
                    np.setSrc(addr)
                            .setRequestFlag()
                            .setTtl(NetworkPacket.SDN_WISE_DFLT_TTL_MAX);
                    controllerTX(np);
                    break;
                case SDN_WISE_MATCH:
                    flowTableQueue.add(np);
                    break;
                case SDN_WISE_TO_UDP:
                    ToUdpAction tua = (ToUdpAction) action;
                    DatagramSocket sUDP = new DatagramSocket();
                    DatagramPacket pck = new DatagramPacket(np.toByteArray(),
                            np.getLen(), tua.getInetSocketAddress());
                    sUDP.send(pck);
                    break;
                default:
                    break;
            }//switch
        } catch (IOException ex) {
            log(ex.getLocalizedMessage());
        }
    }

    private int doOperation(int operatore, int item1, int item2) {
        switch (operatore) {
            case SDN_WISE_ADD:
                return item1 + item2;
            case SDN_WISE_SUB:
                return item1 - item2;
            case SDN_WISE_DIV:
                return item1 / item2;
            case SDN_WISE_MUL:
                return item1 * item2;
            case SDN_WISE_MOD:
                return item1 % item2;
            case SDN_WISE_AND:
                return item1 & item2;
            case SDN_WISE_OR:
                return item1 | item2;
            case SDN_WISE_XOR:
                return item1 ^ item2;
            default:
                return 0;
        }
    }

    private int compare(int operatore, int item1, int item2) {
        if (item1 == -1 || item2 == -1) {
            return 0;
        }
        switch (operatore) {
            case SDN_WISE_EQUAL:
                return item1 == item2 ? 1 : 0;
            case SDN_WISE_NOT_EQUAL:
                return item1 != item2 ? 1 : 0;
            case SDN_WISE_BIGGER:
                return item1 > item2 ? 1 : 0;
            case SDN_WISE_LESS:
                return item1 < item2 ? 1 : 0;
            case SDN_WISE_EQUAL_OR_BIGGER:
                return item1 >= item2 ? 1 : 0;
            case SDN_WISE_EQUAL_OR_LESS:
                return item1 <= item2 ? 1 : 0;
            default:
                return 0;
        }
    }

    void resetSemaphore() {
    }

    BeaconPacket prepareBeacon() {
        BeaconPacket bp = new BeaconPacket(
                net_id,
                addr,
                getActualSinkAddress(),
                distanceFromSink,
                battery.getBatteryPercent());
        return bp;
    }

    ReportPacket prepareReport() {

        ReportPacket rp = new ReportPacket(
                net_id,
                addr,
                getActualSinkAddress(),
                distanceFromSink,
                battery.getBatteryPercent());

        rp.setNeigh(neighbors_number)
                .setNxhop(getNextHopVsSink());

        for (int j = 0; j < neighbors_number; j++) {
            rp.setNeighbourAddressAt(neighborTable.get(j).getAddr(), j)
                    .setNeighbourWeightAt((byte) neighborTable.get(j).getRssi(), j);
        }
        initNeighborTable();
        return rp;
    }

    final void updateTable() {
        for (int i = 0; i < SDN_WISE_RLS_MAX; i++) {
            FlowTableEntry tmp = flowTable.get(i);
            if (tmp.getWindows().size() > 1) {
                int ttl = tmp.getStats().getTtl();
                if (ttl != SDN_WISE_RL_TTL_PERMANENT) {
                    if (ttl >= SDN_WISE_RL_TTL_DECR) {
                        tmp.getStats().decrementTtl(SDN_WISE_RL_TTL_DECR);
                    } else {
                        flowTable.set(i, new FlowTableEntry());
                        log("Removing rule at position " + i);
                        if (i == 0) {
                            resetSemaphore();
                        }
                    }
                }
            }
        }
    }

    final int getNeighborIndex(NodeAddress addr) {
        int i;
        for (i = 0; i < SDN_WISE_NEIGHBORS_MAX; i++) {
            if (neighborTable.get(i).getAddr().equals(addr)) {
                return i;
            }
            if (neighborTable.get(i).getAddr().isBroadcast()) {
                return -1;
            }
        }
        return SDN_WISE_NEIGHBORS_MAX + 1;
    }

    final int searchAcceptedId(NodeAddress addr) {
        int i;
        for (i = 0; i < SDN_WISE_ACCEPTED_ID_MAX; i++) {
            if (acceptedId.get(i).equals(addr)) {
                return i;
            }
        }
        return SDN_WISE_ACCEPTED_ID_MAX + 1;
    }

    final int getActualFlowIndex(int j) {
        //j = j % SDN_WISE_RLS_MAX;
        int i;
        if (j == 0) {
            i = 0;
        } else {
            i = flow_table_free_pos - j;
            if (i == 0) {
                i = SDN_WISE_RLS_MAX - 1;
            } else if (i < 0) {
                i = SDN_WISE_RLS_MAX - 1 + i;
            }
        }
        return i;
    }

    void logTask() {
        MeasureLOGGER.log(Level.FINEST,
                // NODE;BATTERY LVL(mC);BATTERY LVL(%);NO. RULES INSTALLED; B SENT; B RECEIVED;
                "{0},{1},{2},{3},{4},{5},{6},{7},{8}",
                new Object[]{
                    addr,
                    String.valueOf(battery.getBatteryLevel()),
                    String.valueOf(battery.getBatteryPercent() / 2.55),
                    flow_table_free_pos,
                    sentBytes,
                    receivedBytes,
                    sentDataBytes,
                    receivedDataBytes,
                    
                });
    }

    private class CustomClassLoader extends ClassLoader {

        public Class defClass(byte[] data, int len) {
            return defineClass(null, data, 0, len);
        }
    }

    private class PacketManager implements Runnable {

        @Override
        public void run() {
            try {
                while (battery.getBatteryLevel() > 0) {
                    NetworkPacket tmpPacket = flowTableQueue.take();
                    battery.receiveRadio(tmpPacket.getLen());
                    receivedBytes += tmpPacket.getLen();
                    rxHandler(tmpPacket, 255);
                }
            } catch (InterruptedException ex) {
                log(ex.getLocalizedMessage());
            }
        }
    }

    private class PacketSender implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    NetworkPacket np = txQueue.take();
                    radioTX(np);
                }
            } catch (InterruptedException ex) {
                log(ex.getLocalizedMessage());
            }
        }
    }
    
    
    
    
    private class Json {
    	private String message;
    	private String token;
    	private String signature;
    	
    	public Json(String message, String token, String signature) {
    		this.message = message;
    		this.token = token;
    		this.signature = signature;
    	}
    }

    
    
    
    
    
    
    
}
