import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
public class api {
	public static String apiUrl 	=	 "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=";
	public static String apiImageUrl 	=	 "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=images&exintro=&explaintext=";
	public static String apiImage 	=	 "https://en.wikipedia.org/w/api.php?action=query&prop=imageinfo&iiprop=url&format=json";
	
	//mendapatkan keterangan
	public static String  sendGet(String keyword) throws Exception {
		
		String url 	=	 apiUrl + "&titles=" + keyword;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		return response.toString();

	}
	
	//mendapatkan gambar 
	public String  getimage(String keyword) throws Exception {
		
		String url 	=	 apiImageUrl + "&titles=" + keyword;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		return response.toString();

	}
	
	
	public String[] responseApi(String keyWord){
		String key = null;
		String [] x 	=	 new String[2];
		 JsonParser parser = new JsonParser();
		 JsonElement jsonElement;
		try {
			jsonElement = parser.parse(this.sendGet(keyWord));
			JsonObject jsonObject = jsonElement.getAsJsonObject();

			JsonObject qqq 		=	 jsonObject.getAsJsonObject("query");
			JsonObject xxx		=	 qqq.getAsJsonObject("pages");
			Gson gs 	=	 new Gson();
			for ( Entry<String, JsonElement> entry : xxx.entrySet()) {
				    
				   String x1 	=	 entry.getValue().toString();
				   jsonElement   =    parser.parse(x1);
				   JsonObject fix =  jsonElement.getAsJsonObject();
				  
				   String t 	=	 new String(fix.get("title").toString());
				   x[0] 		=	 t.replaceAll("\"", "");
				   
				   String e 	=	 new String(fix.get("extract").toString());
				   x[1] 		=	 e.replaceAll("\"", "");
			}
			
		} catch (JsonSyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return x;
	}
	public String responseImageApi(String keyWord){
		String key = null;
		JsonObject xxx = null;
		JsonObject fix = null;
		String fileInfo = null;
		String [] x 	=	 new String[2];
		 JsonParser parser = new JsonParser();
		 JsonElement jsonElement;
		try {
			jsonElement = parser.parse(this.getimage(keyWord));
			JsonObject jsonObject = jsonElement.getAsJsonObject();

			JsonObject qqq 		=	 jsonObject.getAsJsonObject("query");
			 xxx		=	 qqq.getAsJsonObject("pages");
			for ( Entry<String, JsonElement> entry : xxx.entrySet()) {
				    
				   String x1 	=	 entry.getValue().toString();
				   jsonElement   =    parser.parse(x1);
				    fix =  jsonElement.getAsJsonObject();
			}
			
			JsonArray xx =	 (JsonArray) parser.parse(fix.get("images").toString());
			String asd	 =	 xx.get(3).toString();
			JsonObject j = (JsonObject) parser.parse(asd);
			 fileInfo  = j.get("title").toString().replaceAll("\"", "");
		} catch (JsonSyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return fileInfo;
	}
	
	public String getImage(String keyWord) throws IOException{
		String key = null;
		JsonObject qqq = null;
		JsonObject fix = null;
		JsonObject jsonObject = null;
		JsonArray arr = null;
		JsonElement jsonElement =null;
		String fileInfo = null;
		
			String url 	= "https://en.wikipedia.org/w/api.php?action=query&prop=imageinfo&iiprop=url&format=json&titles=" + URLEncoder.encode(responseImageApi(keyWord), "UTF-8");
			URL oracle = new URL(url);
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(oracle.openStream()));

	        String inputLine;
	        String json = null;
	        while ((inputLine = in.readLine()) != null)
	           json 	= inputLine;
	        
	        JsonParser parser = new JsonParser();
			 
			try {
				jsonElement = parser.parse(json);
				jsonObject = jsonElement.getAsJsonObject();
				
				 qqq 		=	 jsonObject.getAsJsonObject("query");
				 qqq		=	 qqq.getAsJsonObject("pages");
				 qqq		=	 qqq.getAsJsonObject("-1");
				 arr		=	 qqq.getAsJsonArray("imageinfo");
				 
			     
			} catch (JsonSyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			jsonObject = arr.get(0).getAsJsonObject();
			return jsonObject.get("url").toString().replaceAll("\"", "");
			
	}
}
