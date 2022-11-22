package org.contikios.cooja.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import org.apache.log4j.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import multichain.command.MultiChainCommand;
import multichain.command.MultichainException;
import multichain.object.StreamKeyItem;
import multichain.object.queryobjects.RawParam;

public class MultiChainUtils {
	private static Logger logger = Logger.getLogger(MultiChainUtils.class);
	
	private static MultiChainCommand createMultiChainCommand() {
		return new MultiChainCommand(
				"localhost",
				"4278",
				"multichainrpc",
				"67WGBPc6dMkG2UjqcvNnxkGxFpFN9EHq1FP9nPXq7AZg");
	}

	private static String getAddressFrom(MultiChainCommand multiChainCommand) throws MultichainException {
		List<String> addressFromList = multiChainCommand.getAddressCommand().getAddresses();
		return addressFromList.get(addressFromList.size() - 1);
	}
	
	private static String getPrivateKey(MultiChainCommand multiChainCommand, String addressFrom) throws MultichainException {
		return multiChainCommand.getKeyCommand().getPrivkey(addressFrom).toString();
	}
		
	public static void writeCertificateToStream(String streamName, String id, String Pvtkey, String Pubkey, LocalDateTime validTo) {
		try {
			MultiChainCommand multiChainCommand = createMultiChainCommand();
			String addressFrom = getAddressFrom(multiChainCommand);
			String privateKey = getPrivateKey(multiChainCommand, addressFrom);
			JsonCertificate json = new JsonCertificate(id, Pvtkey, Pubkey, validTo);

			Gson gson = new Gson();
			String signedMsg = multiChainCommand.getMessagingCommand().signMessage(privateKey, gson.toJson(json));
			String keys = id + "\n" + signedMsg;
			
			MultipartUtils multipart = new MultipartUtils("http://localhost/multichain-web-demo/?chain=default&page=publish", "UTF-8");
            multipart.addFormField("from", addressFrom);
            multipart.addFormField("name", streamName);
            multipart.addFormField("key", keys);
            multipart.addFormField("json", gson.toJson(json));
            multipart.addFormField("publish", "Publish Item");
 
            List<String> response = multipart.finish();
             
            //System.out.println("SERVER REPLIED:");
             
            //for (String line : response) {
            //    System.out.println(line);
            //}
		} catch (MultichainException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeRegistrationToStream(String streamName, String id, String uuid) {
		try {
			MultiChainCommand multiChainCommand = createMultiChainCommand();
			String addressFrom = getAddressFrom(multiChainCommand);
			String privateKey = getPrivateKey(multiChainCommand, addressFrom);
			JsonRegistration json = new JsonRegistration(id, uuid);

			Gson gson = new Gson();
			String signedMsg = multiChainCommand.getMessagingCommand().signMessage(privateKey, gson.toJson(json));
			String keys = id + "\n" + signedMsg;
			
			MultipartUtils multipart = new MultipartUtils("http://127.0.0.1/Shahbaz/?chain=default&page=publish", "UTF-8");
            multipart.addFormField("from", addressFrom);
            
            multipart.addFormField("name", streamName);
            multipart.addFormField("key", keys);
            multipart.addFormField("json", gson.toJson(json));
            multipart.addFormField("publish", "Publish Item");
 
            List<String> response = multipart.finish();
             
            System.out.println("SERVER REPLIED:");
             
            for (String line : response) {
                System.out.println(line+"Abey Kuttey ki nasal");
            }
		} catch (MultichainException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void writeExperienceToStream(String streamName, String id, int value, String token, String signature) {
		try {
			MultiChainCommand multiChainCommand = createMultiChainCommand();
			String addressFrom = getAddressFrom(multiChainCommand);
			String privateKey = getPrivateKey(multiChainCommand, addressFrom);
			
			JsonExperience json = new JsonExperience(id, value, token, signature);
//*Shahbaz Code JsonExperience json = new JsonExperience(id, value, privateKey, signature);*//
			Gson gson = new Gson();
			String signedMsg = multiChainCommand.getMessagingCommand().signMessage(privateKey, gson.toJson(json));
			String keys = id + "\n" + signedMsg;
			
			MultipartUtils multipart = new MultipartUtils("http://localhost/multichain-web-demo/?chain=default&page=publish", "UTF-8");
            multipart.addFormField("from", addressFrom);
            multipart.addFormField("name", streamName);
            multipart.addFormField("key", keys);
            multipart.addFormField("json", gson.toJson(json));
            multipart.addFormField("publish", "Publish Item");
 
            List<String> response = multipart.finish();
             
            //System.out.println("SERVER REPLIED:");
             
            //for (String line : response) {
            //    System.out.println(line);
            //}
		} catch (MultichainException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getCertificateFromStream(String streamName, String key) {
		try {
			String url = "http://localhost/multichain-web-demo/streamkeycertificate.php?chain=default&stream=" + streamName + "&key=" + key;
			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
	
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			
			in.close();
	
			//print result
			//System.out.println(response.toString());
			return response.toString();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static float getTrustIndexFromStream(String streamName, String key) {
		try {
			String url = "http://localhost/streamkeytrustindex.php?chain=default&stream=" + streamName + "&key=" + key;
			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
	
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			
			in.close();
	
			//print result
			//System.out.println(response.toString());
			return Float.valueOf(response.toString());
		} catch(Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	private static class JsonCertificate {
		private String id;
		private String Pvtkey;
		private String Pubkey;
		private String validTo;
		private String timestamp;
		
		public JsonCertificate(String id, String Pvtkey, String Pubkey, LocalDateTime validTo) {
			this.id = id;
			this.Pvtkey = Pvtkey;
			this.Pubkey = Pubkey;
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			this.validTo = dateTimeFormatter.format(validTo);
			LocalDateTime now = LocalDateTime.now();
			this.timestamp = dateTimeFormatter.format(now);
		}
	}
	private static class JsonRegistration{
	
	private String id;
	private String uuid;
	private String timestamp;
	
		public JsonRegistration(String id, String uuid){
		
		this.id= id;
		this.uuid = uuid;
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		this.timestamp = dateTimeFormatter.format(now);
		}
	}
	
	private static class JsonExperience {
		private String id;
		private int value;
		private String token;
		private String signature;
		private String timestamp;
		
		public JsonExperience(String id, int value, String token, String signature) {
			this.id = id;
			this.value = value;
			this.token = token;
			this.signature = signature;
			//DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			//LocalDateTime now = LocalDateTime.now();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			this.timestamp = timestamp.toString();
		}
	}
}
