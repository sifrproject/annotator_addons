import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Date;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.ibm.icu.text.SimpleDateFormat;


public class JsonToRdf {
	public static Model m = ModelFactory.createDefaultModel();
	//              Prefixs
	public static String aofPrefix = "http://purl.org/ao/foaf/";
	public static  String aoPrefix = "http://purl.org/ao/";
	public static  String aotrPrefix = "http://purl.org/ao/types/";
	public static String pavPrefix ="http://purl.org/pav/2.0/";
	public static  String annPrefix ="http://www.w3.org/2000/10/annotation-ns#";
	public static String aosPrefix ="http://purl.org/ao/selectors/";
	public static String aot="http://purl.org/ao/types/";
	//               URLs
	public static String hasTopicURL ="";
	public static String createdByURL =  "http://bioportal.bioontology.org/annotator" ;
	public static String  contextURL = "http://my.example.org/se/10300";
	public static String  rootURL ="http://bioportal.bioontology.org/annotator/ann/";
	public static String  root2URL = "http://bioportal.bioontology.org/annotator/sel/";     
	public static String  onDocumentURL = "http://data.bioontology.org/annotator?"; 
	public static Random generator; 
	public static int CptAnnotation=0; 
	public static ArrayList<String> termeAnnote;
	public static Dictionary<String, ArrayList<String>> Dictscoreconcept = new Hashtable<String, ArrayList<String>>();
	
	public static void FromJsonToRDF(String FilePath, String FilePath2) 
	{
		JSONParser parser = new JSONParser();
		generator = new Random();
		 int CptAnnotationR = generator.nextInt(10000);
		 
		 Random R = new Random();
		 int RondomRessource = R.nextInt(11000);
		try {
			JSONArray a = (JSONArray) parser.parse(new FileReader(FilePath));
			JSONObject annotatedClass;
			for (Object o : a) 
			{
				JSONObject annotation = (JSONObject) o;
				// Calcluer le score de l'annotation directe
				annotatedClass = (JSONObject) annotation.get("annotatedClass");
				// Récupérer l'id de concept de l'annotation directe
				hasTopicURL = (String) annotatedClass.get("@id");
				// Récupérer l'annotation directe et son matchType
				JSONArray annotations = (JSONArray) annotation.get("annotations");
			    termeAnnote = new ArrayList();
				for (Object c : annotations) 
				{ 
					JSONObject uneannotation = (JSONObject) c;
					String text = (String) uneannotation.get("text");
					Long from = (Long) uneannotation.get("from");
					Long to = (Long) uneannotation.get("to");
					Long taill =to-from+1;
					 Resource root = m.createResource( rootURL+CptAnnotationR+"/"+CptAnnotation );
					 System.out.println("Concept est  :"+CptAnnotation+ "topic "+hasTopicURL);
					 //Document provenanace
					 Property annotatesDocument = m.createProperty( aofPrefix+"annotatesDocument" );
					 Resource annotatesDocumentResource = m.createResource( onDocumentURL+CptAnnotationR );
					 //Annotation topic
					 Property context = m.createProperty( aoPrefix+"context" );
					 Property hasTopic = m.createProperty( aoPrefix+"hasTopic" );
					 Resource contextResource = m.createResource( contextURL);
					 Resource hasTopicResource = m.createResource(hasTopicURL );
					 //Annotation provenanace
					 Property createdBy = m.createProperty( pavPrefix+"createdBy" );
					 Property createdOn = m.createProperty(pavPrefix+"createdOn");
					 Resource createdByResource = m.createResource( createdByURL );
					 SimpleDateFormat formater = null;
					 Date aujourdhui1 = new Date();
					 formater = new SimpleDateFormat("dd-MM-yy");
					 System.out.println(formater.format(aujourdhui1));
					 Resource root2;
					 root2 = m.createResource( root2URL+CptAnnotationR+"/"+CptAnnotation  );
					 System.out.println("Max est :"+CptAnnotation+"  terme : "+text);
					 // //Annotation Selecteur
					 Property exact = m.createProperty( aoPrefix+"exact" );
					 Property offset = m.createProperty( aoPrefix+"offset" );
					 Property range = m.createProperty( aoPrefix+"range" );
					 //Document provenanace
					 Property onDocument = m.createProperty(aofPrefix+"onDocument");
					 Resource  r1 = m.createResource(aot+"ExactQualifier");
					 Resource  r2 = m.createResource(aot+"Qualifier");
					 Resource  r3 = m.createResource(aoPrefix+"Annotation");
					 Resource  r4 = m.createResource(annPrefix+"Annotation");
					 //
					 Resource  r5 = m.createResource(aoPrefix+"Selector");
					 Resource  r6 = m.createResource(aosPrefix+"TextSelector");
					 Resource  r7 = m.createResource(aosPrefix+"OffsetRangeSelector");
					 Resource onDocumentResource = m.createResource(onDocumentURL+CptAnnotationR);	 
					 m.add(root2, onDocument, onDocumentResource).add(root2, range, taill.toString()).add(root2, exact, text).add(root2,offset,from.toString()).add(root2, RDF.type, r7).add(root2, RDF.type, r6).add(root2, RDF.type, r5);
					 m.add( root, createdBy, createdByResource ).add(root, createdOn, formater.format(aujourdhui1)).add( root, hasTopic, hasTopicResource ).add( root, context, root2 ).add( root, annotatesDocument, annotatesDocumentResource ).add(root, RDF.type, r4).add(root, RDF.type, r3).add(root, RDF.type, r2).add(root, RDF.type, r1);
						
					 CptAnnotation++;
				}
				
				JSONArray hierarchy = (JSONArray) annotation.get("hierarchy");
				for (Object h : hierarchy) 
				{
					JSONObject unehierarchie = (JSONObject) h;
					annotatedClass = (JSONObject) unehierarchie.get("annotatedClass");
					hasTopicURL = (String) annotatedClass.get("@id");
					for (Object c : annotations) 
					{
						JSONObject uneannotation = (JSONObject) c;
						String text = (String) uneannotation.get("text");
						Long from = (Long) uneannotation.get("from");
						Long to = (Long) uneannotation.get("to");
						Long taill =to-from+1;
						 Resource root = m.createResource( rootURL+CptAnnotationR+"/"+CptAnnotation );
						 System.out.println("Concept est  :"+CptAnnotation+ "topic "+hasTopicURL);
						 //Document provenanace
						 Property annotatesDocument = m.createProperty( aofPrefix+"annotatesDocument" );
						 Resource annotatesDocumentResource = m.createResource( onDocumentURL+CptAnnotationR );
						 //Annotation topic
						 Property context = m.createProperty( aoPrefix+"context" );
						 Property hasTopic = m.createProperty( aoPrefix+"hasTopic" );
						 Resource contextResource = m.createResource( contextURL);
						 Resource hasTopicResource = m.createResource(hasTopicURL );
						 //Annotation provenanace
						 Property createdBy = m.createProperty( pavPrefix+"createdBy" );
						 Property createdOn = m.createProperty(pavPrefix+"createdOn");
						 Resource createdByResource = m.createResource( createdByURL );
						 SimpleDateFormat formater = null;
						 Date aujourdhui1 = new Date();
						 formater = new SimpleDateFormat("dd-MM-yy");
						 System.out.println(formater.format(aujourdhui1));
						 
						 Resource root2;
						 root2 = m.createResource( root2URL+CptAnnotationR+"/"+CptAnnotation  );
						 System.out.println("Max est :"+CptAnnotation+"  terme : "+text);
						 // //Annotation Selecteur
						 Property exact = m.createProperty( aoPrefix+"exact" );
						 Property offset = m.createProperty( aoPrefix+"offset" );
						 Property range = m.createProperty( aoPrefix+"range" );
						 //Document provenanace
						 Property onDocument = m.createProperty(aofPrefix+"onDocument");
						 Resource onDocumentResource = m.createResource(onDocumentURL+CptAnnotationR);	
						 Resource  r1 = m.createResource(aot+"ExactQualifier");
						 Resource  r2 = m.createResource(aot+"Qualifier");
						 Resource  r3 = m.createResource(aoPrefix+"Annotation");
						 Resource  r4 = m.createResource(annPrefix+"Annotation");
						 //
						 Resource  r5 = m.createResource(aoPrefix+"Selector");
						 Resource  r6 = m.createResource(aosPrefix+"TextSelector");
						 Resource  r7 = m.createResource(aosPrefix+"OffsetRangeSelector");	
						 m.add(root2, onDocument, onDocumentResource).add(root2, range, taill.toString()).add(root2, exact, text).add(root2,offset,from.toString()).add(root2, RDF.type, r7).add(root2, RDF.type, r6).add(root2, RDF.type, r5);;
						 m.add( root, createdBy, createdByResource ).add(root, createdOn, formater.format(aujourdhui1)).add( root, hasTopic, hasTopicResource ).add( root, context, root2 ).add( root, annotatesDocument, annotatesDocumentResource ).add(root, RDF.type, r4).add(root, RDF.type, r3).add(root, RDF.type, r2).add(root, RDF.type, r1);
						 CptAnnotation++;
					}
				}
			}
			//test(FilePath2);
			 m.setNsPrefix( "aof", aofPrefix );
			 m.setNsPrefix( "ao", aoPrefix );
			 m.setNsPrefix( "pav", pavPrefix );
			 m.setNsPrefix( "aos", aosPrefix );
			 m.write( System.out );
			 FileWriter filer;
			try {
				filer = new FileWriter(FilePath2);
				 m.write(filer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public static void FromJsonToRDF(String FilePath, String FilePath2) 
	{
		JSONParser parser = new JSONParser();
		try {
			JSONArray a = (JSONArray) parser.parse(new FileReader(FilePath));
			JSONObject annotatedClass;
			for (Object o : a) 
			{
				JSONObject annotation = (JSONObject) o;
				// Calcluer le score de l'annotation directe
				annotatedClass = (JSONObject) annotation.get("annotatedClass");
				// Récupérer l'id de concept de l'annotation directe
				hasTopicURL = (String) annotatedClass.get("@id");
				// Récupérer l'annotation directe et son matchType
				JSONArray annotations = (JSONArray) annotation.get("annotations");
			    termeAnnote = new ArrayList();
				for (Object c : annotations) 
				{
					JSONObject uneannotation = (JSONObject) c;
					String text = (String) uneannotation.get("text");
					Long from = (Long) uneannotation.get("from");
					Long to = (Long) uneannotation.get("to");
					Long taill =to-from+1;
					if (!termeAnnote.contains(text+"/"+from+"/"+taill)) {
						termeAnnote.add(text+"/"+from+"/"+taill);
					}
				}
				if (Dictscoreconcept.get(hasTopicURL) == null) {
					
					Dictscoreconcept.put(hasTopicURL, termeAnnote);
				} else {
					ArrayList<String> liste = Dictscoreconcept.get(hasTopicURL);
					for (String terme : termeAnnote) 
					{
					if(!liste.contains(terme))
							{
						liste.add(terme);
							}
					}
					Dictscoreconcept.remove(hasTopicURL);
					Dictscoreconcept.put(hasTopicURL, liste);
				}
				JSONArray hierarchy = (JSONArray) annotation.get("hierarchy");
				for (Object h : hierarchy) 
				{
					JSONObject unehierarchie = (JSONObject) h;
					annotatedClass = (JSONObject) unehierarchie.get("annotatedClass");
					hasTopicURL = (String) annotatedClass.get("@id");
					if (Dictscoreconcept.get(hasTopicURL) == null) {
						
						Dictscoreconcept.put(hasTopicURL, termeAnnote);
					} else {
						ArrayList<String> liste = Dictscoreconcept.get(hasTopicURL);
						for (String terme : termeAnnote) 
						{
						if(!liste.contains(terme))
								{
							liste.add(terme);
								}
						}
						Dictscoreconcept.remove(hasTopicURL);
						Dictscoreconcept.put(hasTopicURL, liste);
					}
				}
			}
			test(FilePath2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	/*public static void test(String FilePath)
	{	 
		Enumeration<String> key = Dictscoreconcept.keys();
		generator = new Random();
		 int CptAnnotationR = generator.nextInt(10000);
		while (key.hasMoreElements()) 
		{
			hasTopicURL = key.nextElement();
			ArrayList<String> liste = Dictscoreconcept.get(hasTopicURL);
		 Resource root = m.createResource( rootURL+CptAnnotationR+"/"+CptAnnotation );
		 System.out.println("Concept est  :"+CptAnnotation+ "topic "+hasTopicURL);
		 //Document provenanace
		 Property annotatesDocument = m.createProperty( aofPrefix+"annotatesDocument" );
		 Resource annotatesDocumentResource = m.createResource( annotatesDocumentURL );
		 //Annotation topic
		 Property context = m.createProperty( aoPrefix+"context" );
		 Property hasTopic = m.createProperty( aoPrefix+"hasTopic" );
		 Resource contextResource = m.createResource( contextURL);
		 Resource hasTopicResource = m.createResource(hasTopicURL );
		 //Annotation provenanace
		 Property createdBy = m.createProperty( pavPrefix+"createdBy" );
		 Property createdOn = m.createProperty(pavPrefix+"createdOn");
		 Resource createdByResource = m.createResource( createdByURL );
		 DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		 Date aujourdhui = new Date(); 
		 DateFormat shortDateFormatEN = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT, new Locale("EN","en")); 
		 String dates=  shortDateFormatEN.format(aujourdhui).toString();
		 m.add( root, createdBy, createdByResource ).add(root, createdOn, dates.substring(0,7)).add( root, hasTopic, hasTopicResource ).add( root, context, contextResource ).add( root, annotatesDocument, annotatesDocumentResource );
		 Resource root2;
		 for (String terme : liste) 
			{ 
		 root2 = m.createResource( root2URL+CptAnnotationR+"/"+CptAnnotation  );
		 String str[]=terme.split("/");
		 System.out.println("Max est :"+CptAnnotation+"  terme : "+terme);
		 // //Annotation Selecteur
		 Property exact = m.createProperty( aoPrefix+"exact" );
		 Property offset = m.createProperty( aoPrefix+"offset" );
		 Property range = m.createProperty( aoPrefix+"range" );
		 //Document provenanace
		 Property onDocument = m.createProperty(aofPrefix+"onDocument");
		 Resource onDocumentResource = m.createResource(onDocumentURL);	 
		 m.add(root2, onDocument, onDocumentResource).add(root2, range, str[2]).add(root2, exact, str[0]);
		// System.out.println("cccccccccccccccccccccccccccccccccccccccccccccccccc");
			}
		 CptAnnotation++;
		}
		 m.setNsPrefix( "aof", aofPrefix );
		 m.setNsPrefix( "ao", aoPrefix );
		 m.setNsPrefix( "pav", pavPrefix );
		 m.setNsPrefix( "aos", aosPrefix );
		 m.write( System.out );
		 FileWriter filer;
		try {
			filer = new FileWriter(FilePath);
			 m.write(filer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	//}
	

}
