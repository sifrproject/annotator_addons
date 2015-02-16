

	import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;

import org.w3c.dom.*;

	import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

	public class xmlfile{
	    public static Dictionary<String, String> ListeAnnotationPubmedname = new Hashtable<String, String>();
	    public static NodeList listOfPersons;
	    public static String AbstractText=""; 
	    public static ArrayList<String> ListeAnnotation = new ArrayList<String>();
 	    public static String ArticleTitle="";
 	   public static String ArticleTitleFr="";
 	   public static String PMID="";
 	   
 	   
	    public static ArrayList<String> ReadFileXML (String FilePath){
	    	ArrayList<String> ListeAnnotationPubmed= new ArrayList<String>();
	      try {

	            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	            Document doc = docBuilder.parse (new File(FilePath));

	            // normalizetext representation
	            doc.getDocumentElement ().normalize ();
	            /*System.out.println ("Root element of the doc is " + 
	                 doc.getDocumentElement().getNodeName());*/


	            NodeList listOfannotation = doc.getElementsByTagName("MeshHeading");
	            int totalAnnotation = listOfannotation.getLength();
	           // System.out.println("Nombre d'annotation : " + totalAnnotation);

	            for(int s=0; s<listOfannotation.getLength() ; s++){


	                Node firstannotation = listOfannotation.item(s);
	                if(firstannotation.getNodeType() == Node.ELEMENT_NODE)
	                {
	                    Element firstPersonElement = (Element)firstannotation;


	                    NodeList firstNameList = firstPersonElement.getElementsByTagName("AnnotationConcept");
	                    Element firstNameElement = (Element)firstNameList.item(0);

	                    NodeList textFNList = firstNameElement.getChildNodes();
	                    String anno= ((Node)textFNList.item(0)).getNodeValue().trim();
	                   
	                   // System.out.println("Annotation : " +  anno);

	                    ListeAnnotationPubmed.add(anno);

	                    NodeList NameList = firstPersonElement.getElementsByTagName("Annotation");
	                    Element NameListElement = (Element)NameList.item(0);

	                    NodeList textNameList = NameListElement.getChildNodes();
	                    String nameconcept= ((Node)textNameList.item(0)).getNodeValue().trim();
	                  // System.out.println("nameconcept : " +  nameconcept);

	                    ListeAnnotationPubmedname.put(anno,nameconcept);

	                }//end of if clause


	            }//end of for loop with s var


	        }catch (SAXParseException err) {
	        System.out.println ("** Parsing error" + ", line " 
	             + err.getLineNumber () + ", uri " + err.getSystemId ());
	        System.out.println(" " + err.getMessage ());

	        }catch (SAXException e) {
	        Exception x = e.getException ();
	        ((x == null) ? e : x).printStackTrace ();

	        }catch (Throwable t) {
	        t.printStackTrace ();
	        }
	        
    	 	return ListeAnnotationPubmed;
	    }//end of main
		 public static void ReadFileXML2 (String FilePath)
		    {
		    	try {

		            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		            Document doc = docBuilder.parse (FilePath);
		            doc.getDocumentElement ().normalize ();
		            System.out.println ("Root element of the doc is " +  doc.getDocumentElement().getNodeName());
		            NodeList listOfArticles = doc.getElementsByTagName("PubmedArticle");
		            int totalArticle = listOfArticles.getLength();
		            System.out.println("Total no of PubmedArticle : " + totalArticle);
		            for(int s=0; s<listOfArticles.getLength() ; s++)
		            {
		                Node firstArticleNode = listOfArticles.item(s);
		                if(firstArticleNode.getNodeType() == Node.ELEMENT_NODE)
		                {
		                    Element firstArticle = (Element)firstArticleNode;
		                    NodeList firstArticleList = firstArticle.getElementsByTagName("MedlineCitation");
		                    Element firstArticleElement = (Element)firstArticleList.item(0);
		                    
		                    PMID = "";
		                    PMID =getTextValue(PMID, firstArticleElement, "PMID"); 
		                     ArticleTitle = "";
		                    ArticleTitle =getTextValue(ArticleTitle, firstArticleElement, "ArticleTitle");
		                    
		                     AbstractText = "";
		                    AbstractText =getTextValue(AbstractText, firstArticleElement, "AbstractText");
		                      if(ArticleTitle != "" && AbstractText != "")
		                      {
		                     NodeList firstannotationList = firstArticle.getElementsByTagName("MeshHeading");
		                     ListeAnnotation= new ArrayList<String>();
		                     for(int ss=0; ss<firstannotationList.getLength() ; ss++)
			    	            {
		                    	 Node firstannotation2 = firstannotationList.item(ss);
		                    	 Element firstannotation1 = (Element)firstannotation2;
		    	                    NodeList firstNameList1 = firstannotation1.getElementsByTagName("Annotation");
		    	                    Element firstNameElement1 = (Element)firstNameList1.item(0);

		    	                    NodeList textFNList = firstNameElement1.getChildNodes();
		    	                    String anno= ((Node)textFNList.item(0)).getNodeValue().trim();
		                    ListeAnnotation.add(anno);
		                    String Concept="";
		                    try {
		                    	 Concept =ClasseSearch.Searche(anno);
		            		} catch (UnsupportedEncodingException e) {
		            			e.printStackTrace();
		            		}
		                    ListeAnnotationPubmedname.put(anno,Concept);
			    	            }
		                    Writexml("F:\\MasterBCD\\Stage\\corpus\\ConceptMesh-AnntaPubMed\\"+PMID+".xml");
		                     WriteTexte("F:\\MasterBCD\\Stage\\corpus\\Resume-titre\\"+PMID+".txt");
		                    }  
		                      else
		                      {
		                    	  System.out.println(" Pas de texte ni du résumé" + PMID);
		                      }
		                }
		            }
		            
		        }
		    	catch (SAXParseException err) {
		        System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
		        System.out.println(" " + err.getMessage ());

		        }catch (SAXException e) {
		        Exception x = e.getException ();
		        ((x == null) ? e : x).printStackTrace ();

		        }catch (Throwable t) {
		        t.printStackTrace ();
		        }
		       System.out.println("Fiiiiiiiiiiiiiin");
		    }
		 public static void Filevide(String Path) 
		 {
		 try { 
		          File file = new File(Path);
		          BufferedWriter output = new BufferedWriter(new FileWriter(file));
		          output.close();
		        } catch ( IOException e ) 
		        {
		           e.printStackTrace();
		        } 
		 }
		
		 public static void WriteTexte(String Path) 
		 { 
			 try { 
		          File file = new File(Path);
		          BufferedWriter output = new BufferedWriter(new FileWriter(file));
		          //output.write(xmlfile.AbstractText);
		          output.write(xmlfile.ArticleTitle.replace("[", "").replace("]", ""));
		           output.close();
		        } catch ( IOException e ) 
		        {
		           e.printStackTrace();
		        } 
		 }
		 public static void CorrigerFileXML(String fichier,String Path) 
		 { 
			 String chaine="";
			 try { 
		          File file = new File(Path);
		          BufferedWriter output = new BufferedWriter(new FileWriter(file));
		          
			 try{
					InputStream ips=new FileInputStream(fichier); 
					InputStreamReader ipsr=new InputStreamReader(ips);
					BufferedReader br=new BufferedReader(ipsr);
					String ligne;
					while ((ligne=br.readLine())!=null){
						if(ligne.trim().length()>4)
						{
						if(!ligne.trim().substring(0, 5).equals("<des "))
						{
						output.write(ligne+"\n");
						System.out.println(ligne.trim().substring(0, 5));
						}
						}
					}
					br.close(); 
				}		
				catch (Exception e){
					System.out.println(e.toString());
				}
			 
			 
		           output.close();
		        } catch ( IOException e ) 
		        {
		           e.printStackTrace();
		        } 
		 }
		 public static void Writexml(String Path) 
		 { 
			 try
			 {
				
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		        DocumentBuilder db = dbf.newDocumentBuilder();
		        // create instance of DOM
		        Document dom = db.newDocument();

		        // create the root element
		        Element rootEle = dom.createElement("MeshHeadingList");
		        Iterator i = ListeAnnotation.iterator();
				while (i.hasNext()) 
				{
					String K = (String) i.next();
					Element MeshHeading = dom.createElement("MeshHeading");
					rootEle.appendChild(MeshHeading);
					
			        Element e1 = dom.createElement("Annotation");
			        e1.appendChild(dom.createTextNode(K));
			        MeshHeading.appendChild(e1);
			        //Chercher l'information AnnotationConcept en intéragant ncbo bioportal
			         e1 = dom.createElement("AnnotationConcept");
			         e1.appendChild(dom.createTextNode(ListeAnnotationPubmedname.get(K)));
			        MeshHeading.appendChild(e1);
				}
		        dom.appendChild(rootEle);
				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(dom);
				StreamResult result = new StreamResult(new File(Path));
						transformer.transform(source, result);
			  } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			  } catch (TransformerException tfe) {
				tfe.printStackTrace();
			  }
			}
		 private static String getTextValue(String def, Element doc, String tag) {
			    String value = def;
			    NodeList nl;
			    nl = doc.getElementsByTagName(tag);
			    if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
			        value = nl.item(0).getFirstChild().getNodeValue();
			    }
			    return value;
			}
		 public static void ReadFileXMLKhadi (String FilePath)
		    {
		    	try {

		            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		            Document doc = docBuilder.parse (FilePath);
		            doc.getDocumentElement ().normalize ();
		            System.out.println ("Root element of the doc is " +  doc.getDocumentElement().getNodeName());
		            NodeList listOfArticles = doc.getElementsByTagName("PubmedArticle");
		            int totalArticle = listOfArticles.getLength();
		            System.out.println("Total no of PubmedArticle : " + totalArticle);
		            for(int s=0; s<listOfArticles.getLength() ; s++)
		            {
		                Node firstArticleNode = listOfArticles.item(s);
		                if(firstArticleNode.getNodeType() == Node.ELEMENT_NODE)
		                {
		                    Element firstArticle = (Element)firstArticleNode;
		                    NodeList firstArticleList = firstArticle.getElementsByTagName("MedlineCitation");
		                    Element firstArticleElement = (Element)firstArticleList.item(0);
		                    
		                    PMID = "";
		                    PMID =getTextValue(PMID, firstArticleElement, "PMID");
		                    
		                     ArticleTitle = "";
		                    ArticleTitle =getTextValue(ArticleTitle, firstArticleElement, "ArticleTitle");
		                    
		                    /*ArticleTitleFr = "";
		                    ArticleTitleFr =getTextValue(ArticleTitleFr, firstArticleElement, "VernacularTitle");*/
		                      if(ArticleTitle != "")
		                      {
		                     WriteTexte("F:\\MasterBCD\\Stage\\corpus2\\titrescode\\"+PMID+".txt");
		                     System.out.println(ArticleTitle);
		                    }  
		                      else
		                      {
		                    	  System.out.println(" Pas de texte" + PMID);
		                      }
		                }
		            }
		            
		        }
		    	catch (SAXParseException err) {
		        System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
		        System.out.println(" " + err.getMessage ());

		        }catch (SAXException e) {
		        Exception x = e.getException ();
		        ((x == null) ? e : x).printStackTrace ();

		        }catch (Throwable t) {
		        t.printStackTrace ();
		        }
		       System.out.println("Fiiiiiiiiiiiiiin");
		    }
		 public static ArrayList<String> ReadFileXMLKhadiECMT (String FilePath)
		    {
			 ArrayList<String> ListeAnnotationPubmed= new ArrayList<String>();
		    	try {

		    		 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			            Document doc = docBuilder.parse (new File(FilePath));

			            NodeList listOfArticles = doc.getElementsByTagName("interpretation");
			            int totalArticle = listOfArticles.getLength();
			            //System.out.println("Total no of PubmedArticle : " + totalArticle);
			            // normalizetext representation
			            doc.getDocumentElement ().normalize ();

			            NodeList listOfannotation = doc.getElementsByTagName("des");
			            int totalAnnotation = listOfannotation.getLength();

			            for(int s=0; s<listOfannotation.getLength() ; s++)
			            {
			                Node firstannotation = listOfannotation.item(s);
			                if(firstannotation.getNodeType() == Node.ELEMENT_NODE)
			                {
			                    Element firstPersonElement = (Element)firstannotation;
			                   String  anno=firstPersonElement.getAttribute("code");
			                   String  terme=firstPersonElement.getAttribute("ter");
			                   if(terme.equals("MSH"))
			                   {
			                	 //System.out.println("aaaaaaaaaaaaaaaaaaaa"+anno);
			                	 ListeAnnotationPubmed.add(anno);
			                   }
			                   // ListeAnnotationPubmed.add(anno);
			                }
			            }
			            
		    	}
		    	catch (SAXParseException err) {
		        System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
		        System.out.println(" " + err.getMessage ());

		        }catch (SAXException e) {
		        Exception x = e.getException ();
		        ((x == null) ? e : x).printStackTrace ();

		        }catch (Throwable t) {
		        t.printStackTrace ();
		        }
		        //System.out.println(ListeAnnotationPubmed);
		        //System.out.println(ListeAnnotationPubmed.size());
		       //System.out.println("Fiiiiiiiiiiiiiin");
		       return ListeAnnotationPubmed;
		    }
		 
		 
		 public static ArrayList<String> ReadFileXMLKhadiECMTEN (String FilePath)
		    {
			 ArrayList<String> ListeAnnotationPubmed= new ArrayList<String>();
		    	try {

		    		 DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			            Document doc = docBuilder.parse (new File(FilePath));

			            NodeList listOfArticles = doc.getElementsByTagName("interpretation");
			            int totalArticle = listOfArticles.getLength();
			            System.out.println("Total no of PubmedArticle : " + totalArticle);
			            // normalizetext representation
			            doc.getDocumentElement ().normalize ();

			            NodeList listOfannotation = doc.getElementsByTagName("des");
			            int totalAnnotation = listOfannotation.getLength();

			         // System.out.println("aaaaaaaaaaaaaaaaaaaa");
			            
			            for(int s=0; s<listOfannotation.getLength() ; s++)
			            {
			                Node firstannotation = listOfannotation.item(s);
			                if(firstannotation.getNodeType() == Node.ELEMENT_NODE)
			                {
			                	Element firstArticle = (Element)firstannotation;
			                    NodeList firstArticleList = firstArticle.getElementsByTagName("ConECMT");
			                    
			                 
			                   String ConECMT = "";
			                    ConECMT =getTextValue(ConECMT, firstArticle, "ConECMT");
			                    ListeAnnotationPubmed.add(ConECMT);
			                    System.out.println (ConECMT);
			                }
			            }
			            
		    	}
		    	catch (SAXParseException err) {
		        System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
		        System.out.println(" " + err.getMessage ());

		        }catch (SAXException e) {
		        Exception x = e.getException ();
		        ((x == null) ? e : x).printStackTrace ();

		        }catch (Throwable t) {
		        t.printStackTrace ();
		        }
		        //System.out.println(ListeAnnotationPubmed);
		        //System.out.println(ListeAnnotationPubmed.size());
		       //System.out.println("Fiiiiiiiiiiiiiin");
		       return ListeAnnotationPubmed;
		    }
		 
		 
		 public static ArrayList<String> ReadFileXMLKhadiFMTI (String FilePath){
		    	ArrayList<String> ListeAnnotationPubmed= new ArrayList<String>();
		      try {

		            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		            Document doc = docBuilder.parse (new File(FilePath));
		            // normalizetext representation
		            doc.getDocumentElement ().normalize ();
		            /*System.out.println ("Root element of the doc is " + 
		                 doc.getDocumentElement().getNodeName());*/


		            NodeList listOfannotation = doc.getElementsByTagName("TERM");
		            int totalAnnotation = listOfannotation.getLength();
		           // System.out.println("Nombre d'annotation : " + totalAnnotation);

		            for(int s=0; s<listOfannotation.getLength() ; s++)
		            {
		                Node firstannotation = listOfannotation.item(s);
		                if(firstannotation.getNodeType() == Node.ELEMENT_NODE)
		                {
		                    Element firstPersonElement = (Element)firstannotation;
		                    //System.out.println("CODE : " + firstPersonElement.getAttribute("CODE"));
		                   String  anno=firstPersonElement.getAttribute("CODE");
		                    ListeAnnotationPubmed.add(anno);

		                }
		            }


		        }catch (SAXParseException err) {
		        System.out.println ("** Parsing error" + ", line " 
		             + err.getLineNumber () + ", uri " + err.getSystemId ());
		        System.out.println(" " + err.getMessage ());

		        }catch (SAXException e) {
		        Exception x = e.getException ();
		        ((x == null) ? e : x).printStackTrace ();

		        }catch (Throwable t) {
		        t.printStackTrace ();
		        }
		        
	    	 	return ListeAnnotationPubmed;
		    }//end of main
	}