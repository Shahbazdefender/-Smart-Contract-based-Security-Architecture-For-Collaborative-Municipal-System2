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


/*import org.contikios.cooja.util.CipherUtils;*/

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

    public class Test {
    private int Vericount=0;
    
    static void Test1(){
     
         try {
 
 String pythonScriptPath = "/home/ubuntu/Desktop/contiki/tools/cooja/examples/sdn-wise_java/java/com/github/sdnwiselab/sdnwise/cooja/Script.py";





//File Sereching ALgorithm 






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
           // System.out.println(""+in.readLine());
            //int ret = new Integer(in.readLine()).intValue();
            //System.out.println("value is : "+ret);
 String line=" ";
     /* System.out.println("Farrukh"+in.readLine());  
          while ((line=in.readLine()) != null)  {   
          System.out.println("Taluuuuuuuuuuuuuuuuu");                                                                         
             System.out.println(line+"\n");                                                                                        
          }*/            
 
 

 
}

catch (IOException e) {
         System.out.println("exception happened - here's what I know: ");
         e.printStackTrace();
         System.exit(-1);
      }
       catch (InterruptedException e) {
         System.out.println("exception happened - here's what I know: ");
         e.printStackTrace();
         System.exit(-1);
      }             
                    
                    
  }
  
  
 public void verified(){
    String fileName = "/home/ubuntu/Desktop/contiki/tools/cooja/examples/sdn-wise_java/java/com/github/sdnwiselab/sdnwise/cooja/Verified/Node"+1+".txt";
            // Try block to check for exceptions
        try {
        
        
        File RecordFile = new File("/home/ubuntu/Desktop/contiki/tools/cooja/examples/sdn-wise_java/java/com/github/sdnwiselab/sdnwise/cooja/Verified/Node"+1+".txt");
	Scanner scanner = new Scanner(RecordFile);
	String lineFromFile;
	
   while (scanner.hasNextLine()) {
    lineFromFile = scanner.nextLine();
   if(lineFromFile.contains("0")) { 
   String thing;
    thing = lineFromFile.replaceAll("[^0-9]", "");
       Vericount = Integer.parseInt(thing);
        Vericount=Vericount+1;      
       }
       
       }
       if(Vericount>2){
       System.out.println("Shahbaz1");
       
       Test1();
       
       
       }
       else{
      System.out.println("Shahbaz2");   
      Test1();
          
       }
        
        
 
            // Again operating same operations by passing
            // file as
            // parameter to read it
            BufferedWriter out = new BufferedWriter(
                new FileWriter(fileName));
System.out.println("Value of J="+Vericount);
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
                    
                    
  }




  
  
  
      
   public static void main(String[] args){

int i=1;
while (i<5)
{

Test Test1=new Test();
Test1.verified();
i++;

}
}
}
