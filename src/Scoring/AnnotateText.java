

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.simple.JSONObject;

public class AnnotateText {

	static final String REST_URL = "http://data.bioontology.org";
	static final String REST_URL_FR = "http://bioportal.lirmm.fr:8082";
    static final String API_KEY = "34a88118-0517-414d-8062-2672160e7180";
    static final String API_KEY_FR = "b7df5dec-f5ca-4abe-a11b-0a564938dfe9";
    static final ObjectMapper mapper = new ObjectMapper();
    public static void AnnotateTextes(String directoryPath,String H) throws Exception{
    	File directory = new File(directoryPath);
		if(!directory.exists()){
			System.out.println("Le fichier/répertoire '"+directoryPath+"' n'existe pas");
		}else if(!directory.isDirectory()){
			System.out.println("Le chemin '"+directoryPath+"' correspond à un fichier et non à un répertoire");
		}
		else{
			File[] subfiles = directory.listFiles();
			for(int i=0 ; i<subfiles.length; i++)
			{
				String nomArticle=subfiles[i].getName();
    	BufferedReader br = new BufferedReader(new FileReader(subfiles[i].getPath()));
    	StringBuilder sb;
        try {
             sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        } finally {
            br.close();
        }
        if(H=="avec")
        {
        		AnnotateTexteFromeTexteH("F:\\MasterBCD\\Stage\\corpus2\\NCBOannotationavec3H\\"+nomArticle.substring(0, nomArticle.length()-3)+"json", sb.toString());	
        }
        else
        {
        		AnnotateTexteFrome("F:\\MasterBCD\\Stage\\corpus2\\NCBOannotationssans0H\\"+nomArticle.substring(0, nomArticle.length()-3)+"json", sb.toString());	
        }
			}
		}
    }
   public static void AnnotateTexteFrome(String Path, String textToAnnotate) throws Exception{
    	 String urlParameters;
         JsonNode annotations;
         textToAnnotate = URLEncoder.encode(textToAnnotate, "ISO-8859-1");
         // Get just annotations
         urlParameters = "max_level=0&text=" + textToAnnotate.replace("%", "").replace(":","");
         
        	 annotations = jsonToNode(get(REST_URL + "/annotator?" + urlParameters+"&ontologies=MESH&longest_only=false"));
        	 if(annotations.size()==0)
        	 {
        		 System.out.println(Path);
        	 }
        	 printJSONAnnotations(annotations,Path);
    }
   public static void AnnotateTexteFromeTexteH(String Path, String textToAnnotate) throws Exception
   {
  	   String urlParameters;
       JsonNode annotations;
       textToAnnotate = URLEncoder.encode(textToAnnotate, "ISO-8859-1");
       // Annotations with hierarchy
       urlParameters = "max_level=3&text=" + textToAnnotate.replace("%", "");
       annotations = jsonToNode(get(REST_URL + "/annotator?" + urlParameters+"&ontologies=MESH"+"&longest_only=false"));
       System.out.println(annotations.size());
       printJSONAnnotations(annotations,Path);
  }
   
   
   
   //Annotation en utilisanr l'annotateur franchphone
   public static void AnnotateTextesFR(String directoryPath,String H, boolean longestonly) throws Exception{
   	File directory = new File(directoryPath);
		if(!directory.exists()){
			System.out.println("Le fichier/répertoire '"+directoryPath+"' n'existe pas");
		}else if(!directory.isDirectory()){
			System.out.println("Le chemin '"+directoryPath+"' correspond à un fichier et non à un répertoire");
		}
		else{
			File[] subfiles = directory.listFiles();
			for(int i=0 ; i<subfiles.length; i++)
			{
				String nomArticle=subfiles[i].getName();
   	BufferedReader br = new BufferedReader(new FileReader(subfiles[i].getPath()));
   	StringBuilder sb;
       try {
            sb = new StringBuilder();
           String line = br.readLine();
           while (line != null) {
               sb.append(line);
               sb.append(System.lineSeparator());
               line = br.readLine();
           }
       } finally {
           br.close();
       }
       if(!sb.toString().equals(""))
       {
       if(H=="avec")
       {
       	AnnotateTexteFromeTexteHFR( "F:\\MasterBCD\\Stage\\corpus2\\NCBOannotationavec3HFR\\"+nomArticle.substring(0, nomArticle.length()-3)+"json", sb.toString(),longestonly);
       }
       else
       {
       	
       	AnnotateTexteFromeTexteHFR( "F:\\MasterBCD\\Stage\\corpus2\\NCBOannotationssans0HFR\\"+nomArticle.substring(0, nomArticle.length()-3)+"json", sb.toString(),longestonly);	
       	}
       }
       System.out.println("FFFFFFFFFFFFFFFFFFFFin: "+nomArticle);
			}
		}
   }
   public static void AnnotateTexteFromeTexteHFR(String Path, String textToAnnotate, boolean longest_only) throws Exception
   {
  	 String urlParameters;
       JsonNode annotations;
       textToAnnotate = URLEncoder.encode(textToAnnotate, "ISO-8859-1");
       // Annotations with hierarchy
       urlParameters = "text=" +textToAnnotate.replace("%", "");
       if(longest_only)
       {
       annotations = jsonToNode(get_FR(REST_URL_FR + "/annotator?" + urlParameters+"&ontologies=MSHFRE"));
       }
       else
       {
    	   annotations = jsonToNode(get_FR(REST_URL_FR + "/annotator?" + urlParameters+"&ontologies=MSHFRE"));
       }
       printJSONAnnotations(annotations,Path);
       System.out.println("ok");
  }
   
   
  
    private static void printAnnotations(JsonNode annotations) {
        for (JsonNode annotation : annotations) {
            // Get the details for the class that was found in the annotation and print
            JsonNode classDetails = jsonToNode(get(annotation.get("annotatedClass").get("links").get("self").asText()));
            System.out.println("Class details"+ classDetails);
           /* System.out.println("\tid: " + classDetails.get("@id").asText());
            //System.out.println("\tannotations: " + classDetails.get("@annotations").asText());
            System.out.println("\tprefLabel: " + classDetails.get("prefLabel").asText());
            System.out.println("\tontology: " + classDetails.get("links").get("ontology").asText());
            System.out.println("\n");*/
            JSONObject annotationscore = new JSONObject();
            FileWriter filer;
			try {
            JsonNode annotations1 = annotation.get("annotations");
            if (annotations1.isArray() && annotations1.elements().hasNext()) {
                 System.out.println("\tHierarchy annotations "+annotations1.size());
                 for (JsonNode Annot : annotations1) {
                     System.out.println("\t\tAnnotations"+Annot);
                    // classDetails = jsonToNode(get(Annot.get("from").asText()));
                     
                    //System.out.println("\t\t\text: " + Annot.get("@from").asText());
                    // System.out.println("\t\t\tprefLabel: " + classDetails.get("prefLabel").asText());
                   //  System.out.println("\t\t\tontology: " + classDetails.get("links").get("ontology").asText());
                 }
            }
            
				filer = new FileWriter("F:\\test.json");
				annotationscore.put("@id", annotation);
				
				try {
					filer.write(annotationscore.toString());
					filer.flush();

				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
            
            
            JsonNode hierarchy = annotation.get("hierarchy");
            // If we have hierarchy annotations, print the related class information as well
            if (hierarchy.isArray() && hierarchy.elements().hasNext()) {
                System.out.println("\tHierarchy annotations");
                for (JsonNode hierarchyAnnotation : hierarchy) {
                    classDetails = jsonToNode(get(hierarchyAnnotation.get("annotatedClass").get("links").get("self").asText()));
                    System.out.println("\t\tClass details");
                    System.out.println("\t\t\tid: " + classDetails.get("@id").asText());
                    System.out.println("\t\t\tprefLabel: " + classDetails.get("prefLabel").asText());
                    System.out.println("\t\t\tontology: " + classDetails.get("links").get("ontology").asText());
                }
            }
        }
    }
    private static void printJSONAnnotations(JsonNode annotations, String Path) throws IOException 
    {
    	 FileWriter filer;
        filer = new FileWriter(Path);
				try {
					filer.write(annotations.toString());
					filer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
    	filer.close(); 
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
            rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            System.out.println(url);
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    private static String get_FR(String urlToGet) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToGet);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "apikey token=" + API_KEY_FR);
            conn.setRequestProperty("Accept", "application/json");
            rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
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