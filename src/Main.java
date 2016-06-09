import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {
		public static void main(String[] args) throws Exception{
			api apix 	=	 new api();
			System.out.println(apix.getImage("pindad"));
			
		}
}
