//package Scoring;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.text.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.omg.CORBA.portable.InputStream;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 
import org.w3c.dom.NodeList;

import com.hp.hpl.jena.graph.Node;

public class Comparaison {
	public static Dictionary<String, Donnees> DictArticles= new Hashtable<String, Donnees>();
	public static Dictionary<String, ArrayList<String> > DictArticlesPubMed= new Hashtable<String, ArrayList<String> >();
	
	public static void TitreAbstractArticles(String directoryPath) 
	{
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
				 System.out.println("nomArticle : "+nomArticle);
		 xmlfile.ReadFileXML2("F:\\MasterBCD\\Stage\\corpus\\PubMed\\"+nomArticle.substring(0, nomArticle.length()-3)+"xml");
		 try { 
	          File file = new File("F:\\MasterBCD\\Stage\\corpus\\Resumé+titre\\"+nomArticle.substring(0, nomArticle.length()-3)+"txt");
	          BufferedWriter output = new BufferedWriter(new FileWriter(file));
	          output.write(xmlfile.AbstractText);
	          output.write(xmlfile.ArticleTitle);
	          output.close();
	        } catch ( IOException e ) {
	           e.printStackTrace();
	        }
			}
		}
	}
	
	public static void AnnotationsArticles(String directoryPath) 
	{
		int cpt=0;
		int cptegale=0;
		int cptameliore=0;
		int cptamelioreF1=0;
		int cptamelioreF2=0;
		int cptpasameliore=0;
		int nbTotaleConcepts=0;
		int nbTotaleArticles=0;
		int nbautrecas=0;
		double SommeScoreCorpus=0;
		double SommeScoreCorpusF=0;
		double SommeScoreCorpusF2=0;
		Statistique StatistiqueAmelioration= new Statistique(0,0,0,0,0,0,0,0);
		Statistique StatistiquePasAmelioration= new Statistique(0,0,0,0,0,0,0,0);
		Statistique StatistiqueEgalite= new Statistique(0,0,0,0,0,0,0,0);
		File directory = new File(directoryPath);
		if(!directory.exists()){
			System.out.println("Le fichier/répertoire '"+directoryPath+"' n'existe pas");
		}else if(!directory.isDirectory()){
			System.out.println("Le chemin '"+directoryPath+"' correspond à un fichier et non à un répertoire");
		}
		else{
			File[] subfiles = directory.listFiles();
			try { 
		          File file = new File("F:\\MasterBCD\\Stage\\corpus\\resultats.txt");
		          BufferedWriter output = new BufferedWriter(new FileWriter(file));
			for(int i=0 ; i<subfiles.length; i++)
			{
				cpt++;
				String nomArticle=subfiles[i].getName();
				//System.out.println("************ Article"+nomArticle.substring(0, nomArticle.length()-5)+"************************"+cpt);
				ScoreMethodes classeLireJSON= new ScoreMethodes();
				classeLireJSON.d= new Donnees();
				classeLireJSON.d.Dictscoreconcept= new Hashtable<String, StructScore>();
				classeLireJSON.d.ConceptTries = new ArrayList <String>();
				classeLireJSON.d.DictCvalue = new Hashtable<String, CvalueTerme>();
				classeLireJSON.ClassementMax=0;
				classeLireJSON.ClassementMaxF=0;
				classeLireJSON.ClassementMaxF2=0;
				classeLireJSON.CalculeScorewithoutcontext(subfiles[i].getPath());
				classeLireJSON.OrdonnerDict(classeLireJSON.d.Dictscoreconcept,"score");
				ArrayList <String> ConceptTries = classeLireJSON.d.ConceptTries;
				classeLireJSON.CalculeScoreCvaluescore  (subfiles[i].getPath());
				classeLireJSON.CalculeScoreF  ();
				classeLireJSON.OrdonnerDict(classeLireJSON.d.Dictscoreconcept,"scoreF");
				classeLireJSON.OrdonnerDict(classeLireJSON.d.Dictscoreconcept,"scoreF2");
				int nb=0;
				int SommeScore=0;
				int SommeScoreF=0;
				int SommeScoreF2=0;
				ArrayList<String> ListeAnnotationPubmed= xmlfile.ReadFileXML("F:\\MasterBCD\\Stage\\corpus\\ConceptMesh-AnntaPubMed\\"+nomArticle.substring(0, nomArticle.length()-4)+"xml");
				Iterator i1 = ListeAnnotationPubmed.iterator();
				while(i1.hasNext())
				{
					String K=(String) i1.next();
					K=K.replace("\"","");
					if(classeLireJSON.d.Dictscoreconcept.get(K)!=null)
					{	
						nb++;
					}
				}
				Iterator ii = ListeAnnotationPubmed.iterator();
				//if(nb>0)
				if(nb*100/ListeAnnotationPubmed.size()>30)
				{
					while(ii.hasNext())
				{
					String K=(String) ii.next();
					String KK=K;
					K=K.replace("\"","");
					if(classeLireJSON.d.Dictscoreconcept.get(K)!=null)
					{	
						nbTotaleConcepts++;
						boolean res=PresentationTermedansTexte(xmlfile.ListeAnnotationPubmedname.get(KK),"F:\\MasterBCD\\Stage\\corpus\\Resume-titre\\"+nomArticle.substring(0, nomArticle.length()-4)+"txt");
						SommeScore+=classeLireJSON.d.Dictscoreconcept.get(K).ClassementScore;
						SommeScoreF+=classeLireJSON.d.Dictscoreconcept.get(K).ClassementScoreF;
						SommeScoreF2+=classeLireJSON.d.Dictscoreconcept.get(K).ClassementScoreF2;
						double s1=(double) classeLireJSON.d.Dictscoreconcept.get(K).ClassementScore*100/classeLireJSON.ClassementMax;
						double s2=(double) classeLireJSON.d.Dictscoreconcept.get(K).ClassementScoreF*100/classeLireJSON.ClassementMaxF;
						double s3=(double) classeLireJSON.d.Dictscoreconcept.get(K).ClassementScoreF2*100/classeLireJSON.ClassementMaxF2;
						String str[]=xmlfile.ListeAnnotationPubmedname.get(KK).split(" ");
						if(s1==s2 && s1==s3)
						{
							cptegale++; 
							if(classeLireJSON.d.Dictscoreconcept.get(K).herarchie)
							{
								StatistiqueEgalite.H++;
							}
							else
							{
								StatistiqueEgalite.NonH++;
							}
							if(str.length==1)
							{
								StatistiqueEgalite.Un_mot++;
							}
							if(str.length==2)
							{
								StatistiqueEgalite.deux_mot++;
							}
							if(str.length==3)
							{
								StatistiqueEgalite.Trois_mot++;
							}
							if(str.length==4)
							{
								StatistiqueEgalite.Quatre_mot++;
							}
							if(str.length==5)
							{
								StatistiqueEgalite.Cinq_mot++;
							}
							if(res==true)
							{
								StatistiqueEgalite.nb_presentsTextes++;
							}
						}
						else
						{
							if(s1<=s2 && s1<=s3 ||(s2>s1 && s3==s1)||(s3>s1 && s2==s1))
							{
								cptpasameliore++; 
								if(classeLireJSON.d.Dictscoreconcept.get(K).herarchie)
								{
									StatistiquePasAmelioration.H++;
								}
								else
								{
									StatistiquePasAmelioration.NonH++;
								}
								if(str.length==1)
								{
									StatistiquePasAmelioration.Un_mot++;
								}
								if(str.length==2)
								{
									StatistiquePasAmelioration.deux_mot++;
								}
								if(str.length==3)
								{
									StatistiquePasAmelioration.Trois_mot++;
								}
								if(str.length==4)
								{
									StatistiquePasAmelioration.Quatre_mot++;
								}
								if(str.length==5)
								{
									StatistiquePasAmelioration.Cinq_mot++;
								}
								if(res==true)
								{
									StatistiquePasAmelioration.nb_presentsTextes++;
								}
							}
							else
							{
								if(s1>s2 && s1>s3)
								{
									cptameliore++;
									if(classeLireJSON.d.Dictscoreconcept.get(K).herarchie)
									{
									StatistiqueAmelioration.H++;
									}
									else
									{
										StatistiqueAmelioration.NonH++;
									}
									if(str.length==1)
									{
										StatistiqueAmelioration.Un_mot++;
									}
									if(str.length==2)
									{
										StatistiqueAmelioration.deux_mot++;
									}
									if(str.length==3)
									{
										StatistiqueAmelioration.Trois_mot++;
									}
									if(str.length==4)
									{
										StatistiqueAmelioration.Quatre_mot++;
									}
									if(str.length==5)
									{
										StatistiqueAmelioration.Cinq_mot++;
									}
									if(res==true)
									{
										StatistiqueAmelioration.nb_presentsTextes++;
									}
								}
								else
								{
									if(s2<s1 && (s3>s1||s3==s1))
									{
										cptamelioreF1++;
									}
									else
									{
										if(s3<s1 && (s2>s1||s2==s1))
										{
											cptamelioreF2++;
										}
										else
										{
											nbautrecas++;
											System.out.println("autre cas ssssssssssssssssssss: "+K+" ("+xmlfile.ListeAnnotationPubmedname.get(KK)+")"+" hiérarchie ="+classeLireJSON.d.Dictscoreconcept.get(K).herarchie+" : "+classeLireJSON.d.Dictscoreconcept.get(K).ClassementScore+"/"+classeLireJSON.ClassementMax+" , "+classeLireJSON.d.Dictscoreconcept.get(K).ClassementScoreF+"/"+classeLireJSON.ClassementMaxF +" , "+classeLireJSON.d.Dictscoreconcept.get(K).ClassementScoreF2+"/"+classeLireJSON.ClassementMaxF2+"|| "+s1+" , "+s2+" , "+s3);
										}
									}
								}
							}
						}
						
						System.out.println("Concept : "+K+" ("+xmlfile.ListeAnnotationPubmedname.get(KK)+")"+"present dans Texte ="+res+", hiérarchie ="+classeLireJSON.d.Dictscoreconcept.get(K).herarchie+" : "+classeLireJSON.d.Dictscoreconcept.get(K).ClassementScore+"/"+classeLireJSON.ClassementMax+" , "+classeLireJSON.d.Dictscoreconcept.get(K).ClassementScoreF+"/"+classeLireJSON.ClassementMaxF +" , "+classeLireJSON.d.Dictscoreconcept.get(K).ClassementScoreF2+"/"+classeLireJSON.ClassementMaxF2+"|| "+s1+" , "+s2+" , "+s3);
						 output.write("Concept : "+K+" ("+xmlfile.ListeAnnotationPubmedname.get(KK)+")"+" hiérarchie ="+classeLireJSON.d.Dictscoreconcept.get(K).herarchie+" : "+classeLireJSON.d.Dictscoreconcept.get(K).ClassementScore+"/"+classeLireJSON.ClassementMax+" , "+classeLireJSON.d.Dictscoreconcept.get(K).ClassementScoreF+"/"+classeLireJSON.ClassementMaxF +" , "+classeLireJSON.d.Dictscoreconcept.get(K).ClassementScoreF2+"/"+classeLireJSON.ClassementMaxF2+"|| "+s1+" , "+s2+" , "+s3+"\n");
									}
				}
				}
					//double fff= ( double) (nb*100/ListeAnnotationPubmed.size());
				//if(nb>0)
				if(nb*100/ListeAnnotationPubmed.size()>30)
				{
					nbTotaleArticles++;
					 System.out.println("************ Article"+nomArticle.substring(0, nomArticle.length()-5)+"************************"+cpt+"\n");	
			 output.write("************ Article"+nomArticle.substring(0, nomArticle.length()-5)+"************************"+cpt+"\n");
			  System.out.println("nb Annotation communes : " +  nb);
			  output.write("nb Annotation communes : " +  nb+"\n");
			   System.out.println("nb Annotation PubMed  : " + ListeAnnotationPubmed.size()+"(rappel :"+  nb+"/"+ListeAnnotationPubmed.size()+") ");
			   output.write("nb Annotation PubMed  : " + ListeAnnotationPubmed.size()+"(rappel :"+  nb+"/"+ListeAnnotationPubmed.size()+")"+"\n");
			  System.out.println("nb Annotation ncbo : " + classeLireJSON.d.Dictscoreconcept.size());
					   output.write("nb Annotation ncbo : " + classeLireJSON.d.Dictscoreconcept.size()+"\n");
					   SommeScoreCorpus+= (double)SommeScore*100/(classeLireJSON.ClassementMax*nb);
					   SommeScoreCorpusF+= (double)SommeScoreF*100/(classeLireJSON.ClassementMaxF*nb);
					   SommeScoreCorpusF2+= (double)SommeScoreF2*100/(classeLireJSON.ClassementMaxF2*nb);
			  System.out.println("Score moyen "+SommeScore*100/(classeLireJSON.ClassementMax*nb) );
			  output.write("Score moyen "+SommeScore*100/(classeLireJSON.ClassementMax*nb)+"\n" );
			  System.out.println("Score moyenF "+SommeScoreF*100/(classeLireJSON.ClassementMaxF*nb));
			  output.write("Score moyenF "+SommeScoreF*100/(classeLireJSON.ClassementMaxF*nb)+"\n");
			  System.out.println("Score moyenF2 "+SommeScoreF2*100/(classeLireJSON.ClassementMaxF2*nb));
			  output.write("Score moyenF2 "+SommeScoreF2*100/(classeLireJSON.ClassementMaxF2*nb)+"\n");
				}
			}
			System.out.println("Statistiques:");
			output.write("Statistiques:"+"\n");
			System.out.println("Nb articles traités: "+nbTotaleArticles+"/"+cpt+"("+nbTotaleArticles*100/cpt+"%)");
			output.write("Nb articles traités:"+nbTotaleArticles+"\n");
			System.out.println("Score moyen final "+SommeScoreCorpus/nbTotaleArticles );
			  output.write("Score moyen final " +SommeScoreCorpus/nbTotaleArticles +"\n");
			  System.out.println("Score moyenF final "+SommeScoreCorpusF/nbTotaleArticles );
			  output.write("Score moyenF final "+SommeScoreCorpusF/nbTotaleArticles +"\n");
			  System.out.println("Score moyenF2 final "+SommeScoreCorpusF2/nbTotaleArticles);
			  output.write("Score moyenF2 final "+SommeScoreCorpusF2/nbTotaleArticles+"\n");
			System.out.println("nb cas egalité :"+cptegale+"/"+nbTotaleConcepts+" ("+cptegale*100/nbTotaleConcepts+"%)"+"\n");
			  output.write("nb cas egalité :"+cptegale+"/"+nbTotaleConcepts+" ("+cptegale*100/nbTotaleConcepts+"%)"+"\n");
			  System.out.println("nb cas  amélioration F1 et F2 :"+cptameliore+"/"+nbTotaleConcepts+" ("+cptameliore*100/nbTotaleConcepts+"%)"+"\n");
			  output.write("nb cas  amélioration F1 et F2 :"+cptameliore+"/"+nbTotaleConcepts+" ("+cptameliore*100/nbTotaleConcepts+"%)"+"\n");
			  System.out.println("nb cas pas amélioration  :"+cptpasameliore+"/"+nbTotaleConcepts+" ("+cptpasameliore*100/nbTotaleConcepts+"%)"+"\n");
			  output.write("nb cas pas amélioration  :"+cptpasameliore+"/"+nbTotaleConcepts+" ("+cptpasameliore*100/nbTotaleConcepts+"%)"+"\n");
			  System.out.println("nb cas amélioration  F1 :"+cptamelioreF1+"/"+nbTotaleConcepts+" ("+cptamelioreF1*100/nbTotaleConcepts+"%)"+"\n");
			  output.write("nb cas amélioration  F1 :"+cptamelioreF1+"/"+nbTotaleConcepts+" ("+cptamelioreF1*100/nbTotaleConcepts+"%)"+"\n");
			  System.out.println("nb cas  amélioration  F2 :"+cptamelioreF2+"/"+nbTotaleConcepts+" ("+cptamelioreF2*100/nbTotaleConcepts+"%)"+"\n");
			  output.write("nb cas  amélioration  F2 :"+cptamelioreF2+"/"+nbTotaleConcepts+" ("+cptamelioreF2*100/nbTotaleConcepts+"%)"+"\n");
			
			  System.out.println("Statistiques sur les concept améliorés F+F2:");
			  System.out.println("NB H:"+StatistiqueAmelioration.H+"/"+cptameliore+" ("+StatistiqueAmelioration.H*100/cptameliore+"%)"+"\n");
			  System.out.println("NB Non H:"+StatistiqueAmelioration.NonH+"/"+cptameliore+" ("+StatistiqueAmelioration.NonH*100/cptameliore+"%)"+"\n");
			  System.out.println("NB Concepts presents dans le texte:"+StatistiqueAmelioration.nb_presentsTextes+"/"+cptameliore+" ("+StatistiqueAmelioration.nb_presentsTextes*100/cptameliore+"%)"+"\n");
			  System.out.println("NB Concepts non presents dans le texte:"+(cptameliore-StatistiqueAmelioration.nb_presentsTextes)+"/"+cptameliore+" ("+(cptameliore-StatistiqueAmelioration.nb_presentsTextes)*100/cptameliore+"%)"+"\n");
			  System.out.println("Un mot:"+StatistiqueAmelioration.Un_mot+"/"+cptameliore+" ("+StatistiqueAmelioration.Un_mot*100/cptameliore+"%)"+"\n");
			  System.out.println("Deux mots:"+StatistiqueAmelioration.deux_mot+"/"+cptameliore+" ("+StatistiqueAmelioration.deux_mot*100/cptameliore+"%)"+"\n");
			  System.out.println("3 mots:"+StatistiqueAmelioration.Trois_mot+"/"+cptameliore+" ("+StatistiqueAmelioration.Trois_mot*100/cptameliore+"%)"+"\n");
			  System.out.println("4 mots:"+StatistiqueAmelioration.Quatre_mot+"/"+cptameliore+" ("+StatistiqueAmelioration.Quatre_mot*100/cptameliore+"%)"+"\n");
			  System.out.println("5 mots:"+StatistiqueAmelioration.Cinq_mot+"/"+cptameliore+" ("+StatistiqueAmelioration.Cinq_mot*100/cptameliore+"%)"+"\n");
			  
			  System.out.println("Statistiques sur les concept non améliorés:");
			  System.out.println("NB H:"+StatistiquePasAmelioration.H+"/"+cptpasameliore+" ("+StatistiquePasAmelioration.H*100/cptpasameliore+"%)"+"\n");
			  System.out.println("NB Non H:"+StatistiquePasAmelioration.NonH+"/"+cptpasameliore+" ("+StatistiquePasAmelioration.NonH*100/cptpasameliore+"%)"+"\n");
			 System.out.println("NB Concepts presents dans le texte:"+StatistiquePasAmelioration.nb_presentsTextes+"/"+cptpasameliore+" ("+StatistiquePasAmelioration.nb_presentsTextes*100/cptpasameliore+"%)"+"\n");
			 System.out.println("NB Concepts non presents dans le texte:"+(cptpasameliore-StatistiquePasAmelioration.nb_presentsTextes)+"/"+cptpasameliore+" ("+(cptpasameliore-StatistiquePasAmelioration.nb_presentsTextes)*100/cptpasameliore+"%)"+"\n");
			  System.out.println("Un mot:"+StatistiquePasAmelioration.Un_mot+"/"+cptpasameliore+" ("+StatistiquePasAmelioration.Un_mot*100/cptpasameliore+"%)"+"\n");
			  System.out.println("Deux mots:"+StatistiquePasAmelioration.deux_mot+"/"+cptpasameliore+" ("+StatistiquePasAmelioration.deux_mot*100/cptpasameliore+"%)"+"\n");
			  System.out.println("3 mots:"+StatistiquePasAmelioration.Trois_mot+"/"+cptpasameliore+" ("+StatistiquePasAmelioration.Trois_mot*100/cptpasameliore+"%)"+"\n");
			  System.out.println("4 mots:"+StatistiquePasAmelioration.Quatre_mot+"/"+cptpasameliore+" ("+StatistiquePasAmelioration.Quatre_mot*100/cptpasameliore+"%)"+"\n");
			  System.out.println("5 mots:"+StatistiquePasAmelioration.Cinq_mot+"/"+cptpasameliore+" ("+StatistiquePasAmelioration.Cinq_mot*100/cptpasameliore+"%)"+"\n");
			
			  System.out.println("Statistiques sur l'égalité:");
			  System.out.println("NB H:"+StatistiqueEgalite.H+"/"+cptegale+" ("+StatistiqueEgalite.H*100/cptegale+"%)"+"\n");
			  System.out.println("NB Non H:"+StatistiqueEgalite.NonH+"/"+cptegale+" ("+StatistiqueEgalite.NonH*100/cptegale+"%)"+"\n");
			  System.out.println("NB Concepts presents dans le texte:"+StatistiqueEgalite.nb_presentsTextes+"/"+cptegale+" ("+StatistiqueEgalite.nb_presentsTextes*100/cptegale+"%)"+"\n");
			  System.out.println("NB Concepts non presents dans le texte:"+(cptegale-StatistiqueEgalite.nb_presentsTextes)+"/"+cptegale+" ("+(cptegale-StatistiqueEgalite.nb_presentsTextes)*100/cptegale+"%)"+"\n");
			  System.out.println("Un mot:"+StatistiqueEgalite.Un_mot+"/"+cptegale+" ("+StatistiqueEgalite.Un_mot*100/cptegale+"%)"+"\n");
			  System.out.println("Deux mots:"+StatistiqueEgalite.deux_mot+"/"+cptegale+" ("+StatistiqueEgalite.deux_mot*100/cptegale+"%)"+"\n");
			  System.out.println("3 mots:"+StatistiqueEgalite.Trois_mot+"/"+cptegale+" ("+StatistiqueEgalite.Trois_mot*100/cptegale+"%)"+"\n");
			  System.out.println("4 mots:"+StatistiqueEgalite.Quatre_mot+"/"+cptegale+" ("+StatistiqueEgalite.Quatre_mot*100/cptegale+"%)"+"\n");
			  System.out.println("5 mots:"+StatistiqueEgalite.Cinq_mot+"/"+cptegale+" ("+StatistiqueEgalite.Cinq_mot*100/cptegale+"%)"+"\n");
			System.out.println("Fin");
			 output.close();
	        } catch ( IOException e ) 
	        {
	           e.printStackTrace();
	        } 
		}
	}
	public static int  NbAnnotataionPub(String arcticel)
	{
		int nb=0;
		ArrayList<String> ListeAnnotationPubmed= DictArticlesPubMed.get(arcticel);
		Iterator i = ListeAnnotationPubmed.iterator();
	 	while(i.hasNext())
		{
			String K=(String) i.next();
			if(ScoreMethodes.d.Dictscoreconcept.get(K)!=null)
			{
			nb++;
			}
	}
	 	return nb;
	}
	
	public static boolean  PresentationTermedansTexte(String Terme,String arcticel)
	{
		boolean res=false;
		try{
			FileInputStream ips=new FileInputStream(arcticel); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				System.out.println(ligne);
				if(ligne.contains(Terme))
				{
					res=true;
					break;
				}
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	 return	res;
	}
	
	public static void ComparaisonAnnotation()
	{
		Enumeration<String> key = DictArticlesPubMed.keys();
  	  while(key.hasMoreElements())
  	  {
  		  String k= key.nextElement();
  		ArrayList<String> ListeArticles=DictArticlesPubMed.get(k);
  		
  	  }
	}
}
