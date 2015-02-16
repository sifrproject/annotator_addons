import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ClasseSearch {

    static final String REST_URL = "http://data.bioontology.org";
    static final String API_KEY = "34a88118-0517-414d-8062-2672160e7180";
    static final ObjectMapper mapper = new ObjectMapper();
    static final ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();

    public static String Searche(String Terme) throws UnsupportedEncodingException 
    {
        String res="";
        Terme = URLEncoder.encode(Terme, "ISO-8859-1");

        //+"&ontologies=MESH&apikey=34a88118-0517-414d-8062-2672160e7180&exact_match=true&include_properties=true"
        	JsonNode searchResult = jsonToNode(get(REST_URL + "/search?q=" + Terme +"&ontologies=MESH")).get("collection");
        	try {
        		if(searchResult.get(0)!=null)
        		{
				res =writer.writeValueAsString(searchResult.get(0).get("@id"));
				//System.out.println(searchResult.get(0).get("@id"));
        		}
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
       return res; 
      
    }

    private static JsonNode jsonToNode(String json) {
        JsonNode root = null;
        try {
            root = mapper.readTree(json);
            
            
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    private static String get(String urlToGet) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToGet);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "apikey token=" + API_KEY);
            conn.setRequestProperty("Accept", "application/json");
            rd = new BufferedReader( new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}