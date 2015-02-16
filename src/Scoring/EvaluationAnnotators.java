import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;


public class EvaluationAnnotators {
	public static Dictionary<String, Integer> ListeAnnotateur = new Hashtable<String, Integer>();
	public static Dictionary<String, Integer> ListePubMed = new Hashtable<String, Integer>();
	public static Dictionary<String, Boolean> ListeAnnotateurOrd = new Hashtable<String, Boolean>();
	public static Dictionary<String, Boolean> ListePubMedOrd = new Hashtable<String, Boolean>();
	public static ArrayList<String> ConceptOrdNCBO = new ArrayList<String>();
	public static ArrayList<String> ConceptOrdPubMed = new ArrayList<String>();
	public static void NCBOAnnotatorEN(String directoryPath) 
	{
		
		int nbAnnotationRetrouvee=0;
		int nbAnnotationTotalePubMed=0;
		int nbAnnotationTotaleNCBO=0;
		int nbArticles=0;
		double precisionTotale=0;
		double RappelTotale=0;
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
				nbArticles++;
				String nomArticle=subfiles[i].getName();
				ScoreMethodes classeLireJSON= new ScoreMethodes();
				classeLireJSON.d= new Donnees();
				classeLireJSON.d.Dictscoreconcept= new Hashtable<String, StructScore>();
				classeLireJSON.CalculeScorewithoutcontext(subfiles[i].getPath());
				ArrayList<String> ListeAnnotationPubmed= xmlfile.ReadFileXML("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedcode\\"+nomArticle.substring(0, nomArticle.length()-4)+"xml");
				Iterator i1 = ListeAnnotationPubmed.iterator();
				int nb=0;
				while(i1.hasNext())
				{
					String K=(String) i1.next();
					K=K.replace("\"","");
					if(classeLireJSON.d.Dictscoreconcept.get(K)!=null)
					{	
						nb++;
					}
				}
				if(ListeAnnotationPubmed.size()!=0)
				{
				RappelTotale=RappelTotale+(nb*100/ListeAnnotationPubmed.size());
				}
				if(classeLireJSON.d.Dictscoreconcept.size()!=0)
				{
				precisionTotale=precisionTotale+(nb*100/classeLireJSON.d.Dictscoreconcept.size());
				}
				nbAnnotationRetrouvee=nbAnnotationRetrouvee+nb;
				nbAnnotationTotalePubMed=nbAnnotationTotalePubMed+ListeAnnotationPubmed.size();
				nbAnnotationTotaleNCBO=nbAnnotationTotaleNCBO+classeLireJSON.d.Dictscoreconcept.size();
				System.out.println("Article: "+nomArticle + " nb annotation communne :"+ nb);     
		} 

		}
		System.out.println("nb articles : "+nbArticles);
		System.out.println("nb annotation NCBO totale : "+nbAnnotationTotaleNCBO);
		System.out.println("nb annotation PubMed totale : "+nbAnnotationTotalePubMed);
		System.out.println("nb  Annotation Retrouvee : "+nbAnnotationRetrouvee);
		//moyen du rappel et de précision corpus
		System.out.println("Rappel t : "+RappelTotale/nbArticles);
		System.out.println("Précision  t: "+precisionTotale/nbArticles);
		//rappel et de précision par corpus
		System.out.println("Rappel : "+nbAnnotationRetrouvee*100/nbAnnotationTotalePubMed);
		System.out.println("Précision : "+nbAnnotationRetrouvee*100/nbAnnotationTotaleNCBO);
	}
	public static void NCBOAnnotatorEN2(String directoryPath) 
	{
		ListeAnnotateur = new Hashtable<String, Integer>();
	 ListePubMed = new Hashtable<String, Integer>();
		 ListeAnnotateurOrd = new Hashtable<String, Boolean>();
		ListePubMedOrd = new Hashtable<String, Boolean>();
	 ConceptOrdNCBO = new ArrayList<String>();
	 ConceptOrdPubMed = new ArrayList<String>();
		
		int nbAnnotationRetrouvee=0;
		int nbAnnotationTotalePubMed=0;
		int nbAnnotationTotaleNCBO=0;
		int nbArticles=0;
		double precisionTotale=0;
		double RappelTotale=0;
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
				nbArticles++;
				String nomArticle=subfiles[i].getName();
				ScoreMethodes classeLireJSON= new ScoreMethodes();
				classeLireJSON.d= new Donnees();
				classeLireJSON.d.Dictscoreconcept= new Hashtable<String, StructScore>();
				classeLireJSON.CalculeScorewithoutcontext(subfiles[i].getPath());
				Enumeration<String> key = classeLireJSON.d.Dictscoreconcept.keys();
				while (key.hasMoreElements()) 
				{
					String K = key.nextElement();
					if(ListeAnnotateur.get(K)!=null)
					{	
						int freq=ListeAnnotateur.get(K);
						ListeAnnotateur.remove(K);
						ListeAnnotateur.put(K, freq+1);
					}
					else
					{
						ListeAnnotateur.put(K, 1);
						ListeAnnotateurOrd.put(K, false);
					}
				}
				//System.out.println(classeLireJSON.d.Dictscoreconcept.size());
				ArrayList<String> ListeAnnotationPubmed= xmlfile.ReadFileXML("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedcode\\"+nomArticle.substring(0, nomArticle.length()-4)+"xml");
				Iterator i1 = ListeAnnotationPubmed.iterator();
				while(i1.hasNext())
				{
					String K=(String) i1.next();
					K=K.replace("\"","");
					if(ListePubMed.get(K)!=null)
					{	
						int freq=ListePubMed.get(K);
						ListePubMed.remove(K);
						ListePubMed.put(K, freq+1);
					}
					else
					{
						ListePubMed.put(K, 1);
						ListePubMedOrd.put(K, false);
					}
				}		
		} 
		}
		System.out.println("nb articles : "+nbArticles);
		System.out.println("nb ncbo : "+ListeAnnotateur.size());
		System.out.println("nb pubmed : "+ListePubMed.size());
		Ordonnerdic(ListePubMed,ConceptOrdPubMed,ListePubMedOrd);
		Ordonnerdic(ListeAnnotateur,ConceptOrdNCBO,ListeAnnotateurOrd);
		//System.out.println(ConceptOrdPubMed);
		System.out.println("fffin");
		System.out.println("   ncbo size                "+ConceptOrdNCBO.size());
		double p=precisionpar(20);
		System.out.println("Précision 20: "+p);
		 p=precisionpar(50);
		System.out.println("Précision 50: "+p);
		 p=precisionpar(100);
			System.out.println("Précision 100: "+p);
		 p=precisionpar(500);
		System.out.println("Précision 500: "+p);
		 p=precisionpar(1000);
			System.out.println("Précision 1000: "+p);
			 p=precisionpar(1500);
				System.out.println("Précision 1500: "+p);
				 p=precisionpar(2000);
					System.out.println("Précision 2000: "+p);
					 p=precisionpar(2500);
						System.out.println("Précision 2500: "+p);
						System.out.println("fin");
	}
	public static double precisionpar(int indice)
	{
	 ArrayList<String> ConceptOrdNCBOIndice = new ArrayList<String>();
	 ArrayList<String> ConceptOrdPubMedIndice = new ArrayList<String>();
		int i = 0;
		Iterator i1 = ConceptOrdNCBO.iterator();
		while(i1.hasNext() &&i<indice)
		{
			String K=(String) i1.next();
			ConceptOrdNCBOIndice.add(K);
			//System.out.println(K +"          fred     :  "+ListeAnnotateur.get(K)+ "        "+i);
			i++;
		}
		Iterator i2 = ConceptOrdPubMed.iterator();
		i = 0;
		int cpt=0;
		while(i2.hasNext() &&i<indice)
		{
			String K=(String) i2.next();
			//System.out.println(K +"          fred     :  "+ListePubMed.get(K)+ "        "+i);
			i++;
			if(ConceptOrdNCBOIndice.contains(K))
			{
			cpt++;
			}
		}
		System.out.println("precision:"+cpt);
		return (cpt*100)/indice;
	}
	public static double precisionpar2(int indice)
	{
	 ArrayList<String> ConceptOrdNCBOIndice = new ArrayList<String>();
	 ArrayList<String> ConceptOrdPubMedIndice = new ArrayList<String>();
		int i = 0;
		Iterator i1 = ConceptOrdNCBO.iterator();
		while(i1.hasNext() &&i<indice)
		{
			String K=(String) i1.next();
			String str[]=K.split("/");
			ConceptOrdNCBOIndice.add(str[5]);
			//System.out.println(str[5] +"          fred     :  "+ListeAnnotateur.get(K)+ "        "+i);
			i++;
		}
		Iterator i2 = ConceptOrdPubMed.iterator();
		i = 0;
		int cpt=0;
		while(i2.hasNext() &&i<indice)
		{
			String K=(String) i2.next();
			String str[]=K.split("/");
			//System.out.println(str[5] +"          fred     :  "+ListePubMed.get(K)+ "        "+i);
			i++;
			if(ConceptOrdNCBOIndice.contains(str[5]))
			{
			cpt++;
			}
		}
		System.out.println("precision:"+cpt);
		return (cpt*100)/indice;
	}
	public static double precisionpar3(int indice)
	{
	 ArrayList<String> ConceptOrdNCBOIndice = new ArrayList<String>();
	 ArrayList<String> ConceptOrdPubMedIndice = new ArrayList<String>();
		int i = 0;
		Iterator i1 = ConceptOrdNCBO.iterator();
		while(i1.hasNext() &&i<indice)
		{
			String K=(String) i1.next();
			String str[]=K.split("/");
			ConceptOrdNCBOIndice.add(K);
			//System.out.println(K +"          fred     :  "+ListeAnnotateur.get(K)+ "        "+i);
			i++;
		}
		Iterator i2 = ConceptOrdPubMed.iterator();
		i = 0;
		int cpt=0;
		while(i2.hasNext() &&i<indice)
		{
			String K=(String) i2.next();
			String str[]=K.split("/");
			//System.out.println(str[5] +"          fred     :  "+ListePubMed.get(K)+ "        "+i);
			i++;
			if(ConceptOrdNCBOIndice.contains(str[5]))
			{
			cpt++;
			}
		}
		System.out.println("precision:"+cpt);
		return (cpt*100)/indice;
	}
	
	public static void NCBOAnnotatorFR2(String directoryPath) 
	{
		ListeAnnotateur = new Hashtable<String, Integer>();
		 ListePubMed = new Hashtable<String, Integer>();
			 ListeAnnotateurOrd = new Hashtable<String, Boolean>();
			ListePubMedOrd = new Hashtable<String, Boolean>();
		 ConceptOrdNCBO = new ArrayList<String>();
		 ConceptOrdPubMed = new ArrayList<String>();
		int nbAnnotationRetrouvee=0;
		int nbAnnotationTotalePubMed=0;
		int nbAnnotationTotaleNCBO=0;
		int nbArticles=0;
		double precisionTotale=0;
		double RappelTotale=0;
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
				nbArticles++;
				String nomArticle=subfiles[i].getName();
				ScoreMethodes classeLireJSON= new ScoreMethodes();
				classeLireJSON.d= new Donnees();
				classeLireJSON.d.Dictscoreconcept= new Hashtable<String, StructScore>();
				classeLireJSON.CalculeScorewithoutcontext(subfiles[i].getPath());
				Enumeration<String> key = classeLireJSON.d.Dictscoreconcept.keys();
				while (key.hasMoreElements()) 
				{
					String K = key.nextElement();
					if(ListeAnnotateur.get(K)!=null)
					{	
						int freq=ListeAnnotateur.get(K);
						ListeAnnotateur.remove(K);
						ListeAnnotateur.put(K, freq+1);
					}
					else
					{
						ListeAnnotateur.put(K, 1);
						ListeAnnotateurOrd.put(K, false);
					}
				}
				//System.out.println(classeLireJSON.d.Dictscoreconcept.size());
				ArrayList<String> ListeAnnotationPubmed= xmlfile.ReadFileXML("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedcode\\"+nomArticle.substring(0, nomArticle.length()-4)+"xml");
				Iterator i1 = ListeAnnotationPubmed.iterator();
				while(i1.hasNext())
				{
					String K=(String) i1.next();
					K=K.replace("\"","");
					if(ListePubMed.get(K)!=null)
					{	
						int freq=ListePubMed.get(K);
						ListePubMed.remove(K);
						ListePubMed.put(K, freq+1);
					}
					else
					{
						ListePubMed.put(K, 1);
						ListePubMedOrd.put(K, false);
					}
				}		
		} 
		}
		System.out.println("nb articles : "+nbArticles);
		System.out.println("nb ncbo : "+ListeAnnotateur.size());
		System.out.println("nb pubmed : "+ListePubMed.size());
		Ordonnerdic(ListePubMed,ConceptOrdPubMed,ListePubMedOrd);
		Ordonnerdic(ListeAnnotateur,ConceptOrdNCBO,ListeAnnotateurOrd);
		//System.out.println(ConceptOrdPubMed);
		System.out.println("fffin");
		System.out.println("   ncbo size                "+ConceptOrdNCBO.size());
		double p=precisionpar2(20);
		System.out.println("Précision 20: "+p);
		 p=precisionpar2(50);
		System.out.println("Précision 50: "+p);
		 p=precisionpar2(100);
			System.out.println("Précision 100: "+p);
		 p=precisionpar2(500);
		System.out.println("Précision 500: "+p);
		 p=precisionpar2(1000);
			System.out.println("Précision 1000: "+p);
			 p=precisionpar2(1500);
				System.out.println("Précision 1500: "+p);
				 p=precisionpar2(2000);
					System.out.println("Précision 2000: "+p);
					 p=precisionpar2(2500);
						System.out.println("Précision 2500: "+p);
	}
	public static void NCBOAnnotatorFR(String directoryPath) 
	{
		
		int nbAnnotationRetrouvee=0;
		int nbAnnotationTotalePubMed=0;
		int nbAnnotationTotaleNCBO=0;
		int nbArticles=0;
		double precisionTotale=0;
		double RappelTotale=0;
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
				nbArticles++;
				if(!subfiles[i].isFile()){
					System.out.println("Le chemin '"+directoryPath+"' n'existe pas ");
				}
				else
				{
				String nomArticle=subfiles[i].getName();
				ScoreMethodes classeLireJSON= new ScoreMethodes();
				classeLireJSON.d= new Donnees();
				classeLireJSON.d.Dictscoreconcept= new Hashtable<String, StructScore>();
				classeLireJSON.CalculeScorewithoutcontext(subfiles[i].getPath());
				Enumeration<String> key = classeLireJSON.d.Dictscoreconcept.keys();
				ArrayList<String> ListeAnnotationNCBO=new ArrayList<String>();
				while (key.hasMoreElements()) 
				{
					String K = key.nextElement();
					String str[]=K.split("/");
					ListeAnnotationNCBO.add(str[5]);
				}
				System.out.println(ListeAnnotationNCBO);
				
				ArrayList<String> ListeAnnotationPubmed= xmlfile.ReadFileXML("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedcode\\"+nomArticle.substring(0, nomArticle.length()-4)+"xml");
				Iterator i1 = ListeAnnotationPubmed.iterator();
				int nb=0;
				while(i1.hasNext())
				{
					String K=(String) i1.next();
					String str[]=K.split("/");
					System.out.println(str[5]);
					if(ListeAnnotationNCBO.contains(str[5].replace("\"", "")))
					{	
						nb++;
					}
				}
				if(ListeAnnotationPubmed.size()!=0)
				{
				RappelTotale=RappelTotale+(nb*100/ListeAnnotationPubmed.size());
				}
				if(classeLireJSON.d.Dictscoreconcept.size()!=0)
				{
				precisionTotale=precisionTotale+(nb*100/classeLireJSON.d.Dictscoreconcept.size());
				}
				nbAnnotationRetrouvee=nbAnnotationRetrouvee+nb;
				nbAnnotationTotalePubMed=nbAnnotationTotalePubMed+ListeAnnotationPubmed.size();
				nbAnnotationTotaleNCBO=nbAnnotationTotaleNCBO+classeLireJSON.d.Dictscoreconcept.size();
				System.out.println("Article: "+nomArticle + " nb annotation communne :"+ nb+" nb an ncbo "+classeLireJSON.d.Dictscoreconcept.size()+" nb ann pubmed "+ListeAnnotationPubmed.size()); 
				}   
		}
}
		System.out.println("nb articles : "+nbArticles);
		System.out.println("nb annotation NCBO totale : "+nbAnnotationTotaleNCBO);
		System.out.println("nb annotation PubMed totale : "+nbAnnotationTotalePubMed);
		System.out.println("nb  Annotation Retrouvee : "+nbAnnotationRetrouvee);
		//moyen du rappel et de précision corpus
		System.out.println("Rappel t : "+RappelTotale/nbArticles);
		System.out.println("Précision  t: "+precisionTotale/nbArticles);
		//rappel et de précision par corpus
		System.out.println("Rappel : "+nbAnnotationRetrouvee*100/nbAnnotationTotalePubMed);
		System.out.println("Précision : "+nbAnnotationRetrouvee*100/nbAnnotationTotaleNCBO);
		
	}
	
	public static void FMTIEN2(String directoryPath) 
	{
		ListeAnnotateur = new Hashtable<String, Integer>();
		 ListePubMed = new Hashtable<String, Integer>();
			 ListeAnnotateurOrd = new Hashtable<String, Boolean>();
			ListePubMedOrd = new Hashtable<String, Boolean>();
		 ConceptOrdNCBO = new ArrayList<String>();
		 ConceptOrdPubMed = new ArrayList<String>();
		int nbAnnotationRetrouvee=0;
		int nbAnnotationTotalePubMed=0;
		int nbAnnotationTotaleNCBO=0;
		int nbArticles=0;
		double precisionTotale=0;
		double RappelTotale=0;
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
				nbArticles++;
				String nomArticle=subfiles[i].getName();
			    ArrayList<String> ListeAnnotationPubmed= xmlfile.ReadFileXML("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMed\\"+nomArticle);
				Iterator i1 = ListeAnnotationPubmed.iterator();
				while(i1.hasNext())
				{
					String K=(String) i1.next();
					K=K.replace("\"","");
					if(ListePubMed.get(K)!=null)
					{	
						int freq=ListePubMed.get(K);
						ListePubMed.remove(K);
						ListePubMed.put(K, freq+1);
					}
					else
					{
						ListePubMed.put(K, 1);
						ListePubMedOrd.put(K, false);
					}
				}	
				ArrayList<String> ListeAnnotationFMTI= xmlfile.ReadFileXMLKhadiFMTI("F:\\MasterBCD\\Stage\\corpus2\\FMTI\\PubMedEn\\"+nomArticle);			
				Iterator i2 = ListeAnnotationFMTI.iterator();
				while(i2.hasNext())
				{
					String K=(String) i2.next();
					K=K.replace("\"","");
					if(ListeAnnotateur.get(K)!=null)
					{	
						int freq=ListeAnnotateur.get(K);
						ListeAnnotateur.remove(K);
						ListeAnnotateur.put(K, freq+1);
					}
					else
					{
						ListeAnnotateur.put(K, 1);
						ListeAnnotateurOrd.put(K, false);
					}
				}		
		} 
		}
		System.out.println("nb articles : "+nbArticles);
		System.out.println("nb ncbo : "+ListeAnnotateur.size());
		System.out.println("nb pubmed : "+ListePubMed.size());
		Ordonnerdic(ListePubMed,ConceptOrdPubMed,ListePubMedOrd);
		Ordonnerdic(ListeAnnotateur,ConceptOrdNCBO,ListeAnnotateurOrd);
		//System.out.println(ConceptOrdPubMed);
		System.out.println("fffin");
		System.out.println("   ncbo size                "+ConceptOrdNCBO.size());
		double p=precisionpar3(20);
		System.out.println("Précision 20: "+p);
		 p=precisionpar3(50);
		System.out.println("Précision 50: "+p);
		 p=precisionpar3(100);
			System.out.println("Précision 100: "+p);
		 p=precisionpar3(500);
		System.out.println("Précision 500: "+p);
		 p=precisionpar3(1000);
			System.out.println("Précision 1000: "+p);
			 p=precisionpar3(1500);
				System.out.println("Précision 1500: "+p);
				 p=precisionpar3(2000);
					System.out.println("Précision 2000: "+p);
					 p=precisionpar3(2500);
						System.out.println("Précision 2500: "+p);
	}
	
	public static void FMTIFR2(String directoryPath) 
	{
		ListeAnnotateur = new Hashtable<String, Integer>();
		 ListePubMed = new Hashtable<String, Integer>();
			 ListeAnnotateurOrd = new Hashtable<String, Boolean>();
			ListePubMedOrd = new Hashtable<String, Boolean>();
		 ConceptOrdNCBO = new ArrayList<String>();
		 ConceptOrdPubMed = new ArrayList<String>();
		int nbAnnotationRetrouvee=0;
		int nbAnnotationTotalePubMed=0;
		int nbAnnotationTotaleNCBO=0;
		int nbArticles=0;
		double precisionTotale=0;
		double RappelTotale=0;
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
				nbArticles++;
				String nomArticle=subfiles[i].getName();
				ArrayList<String> ListeAnnotationPubmed= xmlfile.ReadFileXML("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedFR\\"+nomArticle);
			    Iterator i1 = ListeAnnotationPubmed.iterator();
				while(i1.hasNext())
				{
					String K=(String) i1.next();
					K=K.replace("\"","");
					if(ListePubMed.get(K)!=null)
					{	
						int freq=ListePubMed.get(K);
						ListePubMed.remove(K);
						ListePubMed.put(K, freq+1);
					}
					else
					{
						ListePubMed.put(K, 1);
						ListePubMedOrd.put(K, false);
					}
				}	
				ArrayList<String> ListeAnnotationFMTI= xmlfile.ReadFileXMLKhadiFMTI("F:\\MasterBCD\\Stage\\corpus2\\FMTI\\PubMedFr\\"+nomArticle);
					Iterator i2 = ListeAnnotationFMTI.iterator();
				while(i2.hasNext())
				{
					String K=(String) i2.next();
					K=K.replace("\"","");
					if(ListeAnnotateur.get(K)!=null)
					{	
						int freq=ListeAnnotateur.get(K);
						ListeAnnotateur.remove(K);
						ListeAnnotateur.put(K, freq+1);
					}
					else
					{
						ListeAnnotateur.put(K, 1);
						ListeAnnotateurOrd.put(K, false);
					}
				}		
		} 
		}
		System.out.println("nb articles : "+nbArticles);
		System.out.println("nb ncbo : "+ListeAnnotateur.size());
		System.out.println("nb pubmed : "+ListePubMed.size());
		Ordonnerdic(ListePubMed,ConceptOrdPubMed,ListePubMedOrd);
		Ordonnerdic(ListeAnnotateur,ConceptOrdNCBO,ListeAnnotateurOrd);
		//System.out.println(ConceptOrdPubMed);
		System.out.println("fffin");
		System.out.println("   ncbo size                "+ConceptOrdNCBO.size());
		double p=precisionpar3(20);
		System.out.println("Précision 20: "+p);
		 p=precisionpar3(50);
		System.out.println("Précision 50: "+p);
		 p=precisionpar3(100);
			System.out.println("Précision 100: "+p);
		 p=precisionpar3(500);
		System.out.println("Précision 500: "+p);
		 p=precisionpar3(1000);
			System.out.println("Précision 1000: "+p);
			 p=precisionpar3(1500);
				System.out.println("Précision 1500: "+p);
				 p=precisionpar3(2000);
					System.out.println("Précision 2000: "+p);
					 p=precisionpar3(2500);
						System.out.println("Précision 2500: "+p);
	}
	
	public static void FMTIEN(String directoryPath) 
	{
		
		int nbAnnotationRetrouvee=0;
		int nbAnnotationTotalePubMed=0;
		int nbAnnotationTotaleFMTI=0;
		int nbArticles=0;
		double precisionTotale=0;
		double RappelTotale=0;
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
				nbArticles++;
				String nomArticle=subfiles[i].getName();
				//System.out.println("************ Article"+nomArticle.substring(0, nomArticle.length()-5)+"************************"+cpt);
				ArrayList<String> ListeAnnotationFMTI= xmlfile.ReadFileXMLKhadiFMTI("F:\\MasterBCD\\Stage\\corpus2\\FMTI\\PubMedEn\\"+nomArticle);			
				ArrayList<String> ListeAnnotationPubmed= xmlfile.ReadFileXML("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMed\\"+nomArticle);
				Iterator i1 = ListeAnnotationPubmed.iterator();
				int nb=0;
				System.out.println(ListeAnnotationFMTI.size()+"    "+ ListeAnnotationPubmed.size()+"             " + nomArticle);
				while(i1.hasNext())
				{
					String K=(String) i1.next();
					K=K.replace("\"","");
					String str[]=K.split("/");
					if(ListeAnnotationFMTI.contains(str[5]))
					{	
						nb++;
					}
				}
				System.out.println(ListeAnnotationFMTI.size()+"    "+ ListeAnnotationPubmed.size()+"             " + nomArticle+ "           " +nb+ "              "+nbAnnotationRetrouvee);
				if(ListeAnnotationPubmed.size()!=0)
				{
				RappelTotale=RappelTotale+(nb*100/ListeAnnotationPubmed.size());
				}
				if(ListeAnnotationFMTI.size()!=0)
				{
				precisionTotale=precisionTotale+(nb*100/ListeAnnotationFMTI.size());
				}
				nbAnnotationRetrouvee=nbAnnotationRetrouvee+nb;
				nbAnnotationTotalePubMed=nbAnnotationTotalePubMed+ListeAnnotationPubmed.size();
				nbAnnotationTotaleFMTI=nbAnnotationTotaleFMTI+ListeAnnotationFMTI.size();
				//System.out.println("Article: "+nomArticle + " nb annotation communne :"+ nb);     
		}  

}
		System.out.println("nb articles : "+nbArticles);
		System.out.println("nb annotation FMTI totale : "+nbAnnotationTotaleFMTI);
		System.out.println("nb annotation PubMed totale : "+nbAnnotationTotalePubMed);
		System.out.println("nb  Annotation Retrouvee : "+nbAnnotationRetrouvee);
		
		//moyen du rappel et de précision corpus
		System.out.println("Rappel t : "+RappelTotale/nbArticles);
		System.out.println("Précision  t: "+precisionTotale/nbArticles);
		//rappel et de précision par corpus
		System.out.println("Rappel : "+nbAnnotationRetrouvee*100/nbAnnotationTotalePubMed);
		System.out.println("Précision : "+nbAnnotationRetrouvee*100/nbAnnotationTotaleFMTI);

	}
	

	public static void FMTIFR(String directoryPath) 
	{
		
		int nbAnnotationRetrouvee=0;
		int nbAnnotationTotalePubMed=0;
		int nbAnnotationTotaleFMTI=0;
		int nbArticles=0;
		double precisionTotale=0;
		double RappelTotale=0;
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
				nbArticles++;
				String nomArticle=subfiles[i].getName();
				ArrayList<String> ListeAnnotationFMTI= xmlfile.ReadFileXMLKhadiFMTI("F:\\MasterBCD\\Stage\\corpus2\\FMTI\\PubMedFr\\"+nomArticle);
					ArrayList<String> ListeAnnotationPubmed= xmlfile.ReadFileXML("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedFR\\"+nomArticle);
				Iterator i1 = ListeAnnotationPubmed.iterator();
				int nb=0;
				while(i1.hasNext())
				{
					String K=(String) i1.next();
					K=K.replace("\"","");
					String str[]=K.split("/");
					if(ListeAnnotationFMTI.contains(str[5]))
					{	
						nb++;
					}
				}
				if(ListeAnnotationPubmed.size()!=0)
				{
				RappelTotale=RappelTotale+(nb*100/ListeAnnotationPubmed.size());
				}
				if(ListeAnnotationFMTI.size()!=0)
				{
				precisionTotale=precisionTotale+(nb*100/ListeAnnotationFMTI.size());
				}
				nbAnnotationRetrouvee=nbAnnotationRetrouvee+nb;
				nbAnnotationTotalePubMed=nbAnnotationTotalePubMed+ListeAnnotationPubmed.size();
				nbAnnotationTotaleFMTI=nbAnnotationTotaleFMTI+ListeAnnotationFMTI.size();
				//System.out.println("Article: "+nomArticle + " nb annotation communne :"+ nb);     
		} 
	}
		System.out.println("nb articles : "+nbArticles);
		System.out.println("nb annotation FMTI totale : "+nbAnnotationTotaleFMTI);
		System.out.println("nb annotation PubMed totale : "+nbAnnotationTotalePubMed);
		System.out.println("nb  Annotation Retrouvee : "+nbAnnotationRetrouvee);
		//moyen du rappel et de précision corpus 
		System.out.println("Rappel t : "+RappelTotale/nbArticles);
		System.out.println("Précision  t: "+precisionTotale/nbArticles);
		//rappel et  précision dans tous le corpus 
		System.out.println("Rappel : "+nbAnnotationRetrouvee*100/nbAnnotationTotalePubMed);
		System.out.println("Précision : "+nbAnnotationRetrouvee*100/nbAnnotationTotaleFMTI);
	}
	
	
	public static void ECMTFR2(String directoryPath) 
	{
		ListeAnnotateur = new Hashtable<String, Integer>();
		 ListePubMed = new Hashtable<String, Integer>();
			 ListeAnnotateurOrd = new Hashtable<String, Boolean>();
			ListePubMedOrd = new Hashtable<String, Boolean>();
		 ConceptOrdNCBO = new ArrayList<String>();
		 ConceptOrdPubMed = new ArrayList<String>();
		int nbAnnotationRetrouvee=0;
		int nbAnnotationTotalePubMed=0;
		int nbAnnotationTotaleNCBO=0;
		int nbArticles=0;
		double precisionTotale=0;
		double RappelTotale=0;
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
				nbArticles++;
				String nomArticle=subfiles[i].getName();
					ArrayList<String> ListeAnnotationPubmed= xmlfile.ReadFileXML("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedcode\\"+nomArticle);
			  Iterator i1 = ListeAnnotationPubmed.iterator();
				while(i1.hasNext())
				{
					String K=(String) i1.next();
					K=K.replace("\"","");
					if(ListePubMed.get(K)!=null)
					{	
						int freq=ListePubMed.get(K);
						ListePubMed.remove(K);
						ListePubMed.put(K, freq+1);
					}
					else
					{
						ListePubMed.put(K, 1);
						ListePubMedOrd.put(K, false);
					}
				}	
				ArrayList<String> ListeAnnotationECMT= xmlfile.ReadFileXMLKhadiECMT("F:\\MasterBCD\\Stage\\corpus2\\ECMT\\FR\\"+nomArticle);
					Iterator i2 = ListeAnnotationECMT.iterator();
				while(i2.hasNext())
				{
					String K=(String) i2.next();
					K=K.replace("\"","");
					if(ListeAnnotateur.get(K)!=null)
					{	
						int freq=ListeAnnotateur.get(K);
						ListeAnnotateur.remove(K);
						ListeAnnotateur.put(K, freq+1);
					}
					else
					{
						ListeAnnotateur.put(K, 1);
						ListeAnnotateurOrd.put(K, false);
					}
				}		
		} 
		}
		System.out.println("nb articles : "+nbArticles);
		System.out.println("nb ncbo : "+ListeAnnotateur.size());
		System.out.println("nb pubmed : "+ListePubMed.size());
		Ordonnerdic(ListePubMed,ConceptOrdPubMed,ListePubMedOrd);
		Ordonnerdic(ListeAnnotateur,ConceptOrdNCBO,ListeAnnotateurOrd);
		//System.out.println(ConceptOrdPubMed);
		System.out.println("fffin");
		System.out.println("   ncbo size                "+ConceptOrdNCBO.size());
		double p=precisionpar3(20);
		System.out.println("Précision 20: "+p);
		 p=precisionpar3(50);
		System.out.println("Précision 50: "+p);
		 p=precisionpar3(100);
			System.out.println("Précision 100: "+p);
		 p=precisionpar3(500);
		System.out.println("Précision 500: "+p);
		 p=precisionpar3(1000);
			System.out.println("Précision 1000: "+p);
			 p=precisionpar3(1500);
				System.out.println("Précision 1500: "+p);
				 p=precisionpar3(2000);
					System.out.println("Précision 2000: "+p);
					 p=precisionpar3(2500);
						System.out.println("Précision 2500: "+p);
	}
	
	public static void ECMTFR(String directoryPath) 
	{
		
		int nbAnnotationRetrouvee=0;
		int nbAnnotationTotalePubMed=0;
		int nbAnnotationTotaleECMT=0;
		int nbArticles=0;
		double precisionTotale=0;
		double RappelTotale=0;
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
				nbArticles++;
				String nomArticle=subfiles[i].getName();
				ArrayList<String> ListeAnnotationECMT= xmlfile.ReadFileXMLKhadiECMT("F:\\MasterBCD\\Stage\\corpus2\\ECMT\\FR\\"+nomArticle);
				ArrayList<String> ListeAnnotationPubmed= xmlfile.ReadFileXML("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedcode\\"+nomArticle);
				Iterator i1 = ListeAnnotationPubmed.iterator();
				int nb=0;
				while(i1.hasNext())
				{
					String K=(String) i1.next();
					K=K.replace("\"","");
					String str[]=K.split("/");
					//System.out.println("concept:"+ str[5]);
					if(ListeAnnotationECMT.contains(str[5]))
					{	
						nb++;
						//System.out.println(K);
					}
				}
				if(ListeAnnotationPubmed.size()!=0)
				{
				RappelTotale=RappelTotale+(nb*100/ListeAnnotationPubmed.size());
				}
				if(ListeAnnotationECMT.size()!=0)
				{
				precisionTotale=precisionTotale+(nb*100/ListeAnnotationECMT.size());
				}
				nbAnnotationRetrouvee=nbAnnotationRetrouvee+nb;
				nbAnnotationTotalePubMed=nbAnnotationTotalePubMed+ListeAnnotationPubmed.size();
				nbAnnotationTotaleECMT=nbAnnotationTotaleECMT+ListeAnnotationECMT.size();
				System.out.println("Article: "+nomArticle + " nb annotation communne :"+ nb);     
		} 
	}
		System.out.println("nb articles : "+nbArticles);
		System.out.println("nb annotation FMTI totale : "+nbAnnotationTotaleECMT);
		System.out.println("nb annotation PubMed totale : "+nbAnnotationTotalePubMed);
		System.out.println("nb  Annotation Retrouvee : "+nbAnnotationRetrouvee);
        //moyen du rappel et de précision corpus 
		System.out.println("Rappel t : "+RappelTotale/nbArticles);
		System.out.println("Précision  t: "+precisionTotale/nbArticles);
		// rappel et  précision t le corpus dans tou corpus 
		System.out.println("Rappel : "+nbAnnotationRetrouvee*100/nbAnnotationTotalePubMed);
		System.out.println("Précision : "+nbAnnotationRetrouvee*100/nbAnnotationTotaleECMT);
	}
	
	public static void ECMTEN2(String directoryPath) 
	{
		ListeAnnotateur = new Hashtable<String, Integer>();
		 ListePubMed = new Hashtable<String, Integer>();
			 ListeAnnotateurOrd = new Hashtable<String, Boolean>();
			ListePubMedOrd = new Hashtable<String, Boolean>();
		 ConceptOrdNCBO = new ArrayList<String>();
		 ConceptOrdPubMed = new ArrayList<String>();
		int nbAnnotationRetrouvee=0;
		int nbAnnotationTotalePubMed=0;
		int nbAnnotationTotaleNCBO=0;
		int nbArticles=0;
		double precisionTotale=0;
		double RappelTotale=0;
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
				nbArticles++;
				String nomArticle=subfiles[i].getName();
				ArrayList<String> ListeAnnotationPubmed= xmlfile.ReadFileXML("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedcode\\"+nomArticle);
				  Iterator i1 = ListeAnnotationPubmed.iterator();
				while(i1.hasNext())
				{
					String K=(String) i1.next();
					K=K.replace("\"","");
					if(ListePubMed.get(K)!=null)
					{	
						int freq=ListePubMed.get(K);
						ListePubMed.remove(K);
						ListePubMed.put(K, freq+1);
					}
					else
					{
						ListePubMed.put(K, 1);
						ListePubMedOrd.put(K, false);
					}
				}	
				ArrayList<String> ListeAnnotationECMT= xmlfile.ReadFileXMLKhadiECMT("F:\\MasterBCD\\Stage\\corpus2\\ECMT\\EN\\"+nomArticle);
				Iterator i2 = ListeAnnotationECMT.iterator();
				while(i2.hasNext())
				{
					String K=(String) i2.next();
					K=K.replace("\"","");
					if(ListeAnnotateur.get(K)!=null)
					{	
						int freq=ListeAnnotateur.get(K);
						ListeAnnotateur.remove(K);
						ListeAnnotateur.put(K, freq+1);
					}
					else
					{
						ListeAnnotateur.put(K, 1);
						ListeAnnotateurOrd.put(K, false);
					}
				}		
		} 
		}
		System.out.println("nb articles : "+nbArticles);
		System.out.println("nb ncbo : "+ListeAnnotateur.size());
		System.out.println("nb pubmed : "+ListePubMed.size());
		Ordonnerdic(ListePubMed,ConceptOrdPubMed,ListePubMedOrd);
		Ordonnerdic(ListeAnnotateur,ConceptOrdNCBO,ListeAnnotateurOrd);
		//System.out.println(ConceptOrdPubMed);
		System.out.println("fffin");
		System.out.println("   ncbo size                "+ConceptOrdNCBO.size());
		double p=precisionpar3(20);
		System.out.println("Précision 20: "+p);
		 p=precisionpar3(50);
		System.out.println("Précision 50: "+p);
		 p=precisionpar3(100);
			System.out.println("Précision 100: "+p);
		 p=precisionpar3(500);
		System.out.println("Précision 500: "+p);
		 p=precisionpar3(1000);
			System.out.println("Précision 1000: "+p);
			 p=precisionpar3(1500);
				System.out.println("Précision 1500: "+p);
				 p=precisionpar3(2000);
					System.out.println("Précision 2000: "+p);
					 p=precisionpar3(2500);
						System.out.println("Précision 2500: "+p);
	}
	public static void ECMTEN(String directoryPath) 
	{
		
		int nbAnnotationRetrouvee=0;
		int nbAnnotationTotalePubMed=0;
		int nbAnnotationTotaleECMT=0;
		int nbArticles=0;
		double precisionTotale=0;
		double RappelTotale=0;
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
				nbArticles++;
				String nomArticle=subfiles[i].getName();
				ArrayList<String> ListeAnnotationECMT= xmlfile.ReadFileXMLKhadiECMT("F:\\MasterBCD\\Stage\\corpus2\\ECMT\\EN\\"+nomArticle);
				ArrayList<String> ListeAnnotationPubmed= xmlfile.ReadFileXML("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedcode\\"+nomArticle);
				Iterator i1 = ListeAnnotationPubmed.iterator();
				int nb=0;
				while(i1.hasNext())
				{
					String K=(String) i1.next();
					K=K.replace("\"","");
					String str[]=K.split("/");
					//System.out.println("concept:"+ str[5]);
					if(ListeAnnotationECMT.contains(str[5]))
					{	
						nb++;
						//System.out.println(K);
					}
				}
				if(ListeAnnotationPubmed.size()!=0)
				{
				RappelTotale=RappelTotale+(nb*100/ListeAnnotationPubmed.size());
				}
				if(ListeAnnotationECMT.size()!=0)
				{
				precisionTotale=precisionTotale+(nb*100/ListeAnnotationECMT.size());
				}
				nbAnnotationRetrouvee=nbAnnotationRetrouvee+nb;
				nbAnnotationTotalePubMed=nbAnnotationTotalePubMed+ListeAnnotationPubmed.size();
				nbAnnotationTotaleECMT=nbAnnotationTotaleECMT+ListeAnnotationECMT.size();
				System.out.println("Article: "+nomArticle + " nb annotation communne :"+ nb);     
		} 
	}
		System.out.println("nb articles : "+nbArticles);
		System.out.println("nb annotation FMTI totale : "+nbAnnotationTotaleECMT);
		System.out.println("nb annotation PubMed totale : "+nbAnnotationTotalePubMed);
		System.out.println("nb  Annotation Retrouvee : "+nbAnnotationRetrouvee);
        //moyen du rappel et de précision corpus 
		System.out.println("Rappel t : "+RappelTotale/nbArticles);
		System.out.println("Précision  t: "+precisionTotale/nbArticles);
		// rappel et  précision t le corpus dans tou corpus 
		System.out.println("Rappel : "+nbAnnotationRetrouvee*100/nbAnnotationTotalePubMed);
		System.out.println("Précision : "+nbAnnotationRetrouvee*100/nbAnnotationTotaleECMT);
	}
	
	
	
	/*public static void ECMTFR(String directoryPath) 
	{
		
		int nbAnnotationRetrouvee=0;
		ArrayList<String> ListeAnnotationPubmedTotale=new ArrayList<String> ();
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
				ArrayList<String> ListeAnnotationPubmed= xmlfile.ReadFileXML("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedFR\\"+nomArticle);
				Iterator i1 = ListeAnnotationPubmed.iterator();
				while(i1.hasNext())
				{
					String K=(String) i1.next();
					K=K.replace("\"","");
					String str[]=K.split("/");
					ListeAnnotationPubmedTotale.add(str[5]);
				}	
		} 
	}
		ArrayList<String> ListeAnnotationECMT= xmlfile.ReadFileXMLKhadiECMT("F:\\MasterBCD\\Stage\\corpus2\\ECMT\\AnnotationPubMedfrEcmtExp.xml");
		Iterator i1 = ListeAnnotationECMT.iterator();
		while(i1.hasNext())
		{
			String K=(String) i1.next();
			if(ListeAnnotationPubmedTotale.contains(K))
			{
			nbAnnotationRetrouvee++;
			}
		}	
		//System.out.println("Article: "+nomArticle + " nb annotation communne :"+ nb);     
		System.out.println("nb annotation ECMT totale : "+ListeAnnotationECMT.size());
		System.out.println("nb annotation PubMed totale : "+ListeAnnotationPubmedTotale.size());
		System.out.println("nb  Annotation Retrouvee : "+nbAnnotationRetrouvee);
		System.out.println("Rappel : "+nbAnnotationRetrouvee*100/ListeAnnotationPubmedTotale.size());
		System.out.println("Précision : "+nbAnnotationRetrouvee*100/ListeAnnotationECMT.size());
		//Rappel=5, et Précision=3
		//Rappel=4, et Précision=11
	}
	
	
	public static void ECMTEN(String directoryPath) 
	{
		
		int nbAnnotationRetrouvee=0;
		ArrayList<String> ListeAnnotationPubmedTotale=new ArrayList<String> ();
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
				ArrayList<String> ListeAnnotationPubmed= xmlfile.ReadFileXML("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedFR\\"+nomArticle);
				Iterator i1 = ListeAnnotationPubmed.iterator();
				while(i1.hasNext())
				{
					String K=(String) i1.next();
					K=K.replace("\"","");
					String str[]=K.split("/");
					ListeAnnotationPubmedTotale.add(str[5]);
				}	
		} 
	}
		ArrayList<String> ListeAnnotationECMT= xmlfile.ReadFileXMLKhadiECMTEN("F:\\MasterBCD\\Stage\\corpus2\\ECMT\\AnnotationPubMedenEcmtExp1.xml");
		Iterator i1 = ListeAnnotationECMT.iterator();
		while(i1.hasNext())
		{
			String K=(String) i1.next();
			if(ListeAnnotationPubmedTotale.contains(K))
			{
			nbAnnotationRetrouvee++;
			}
		}	
		//System.out.println("Article: "+nomArticle + " nb annotation communne :"+ nb);     
		System.out.println("nb annotation ECMT totale : "+ListeAnnotationECMT.size());
		System.out.println("nb annotation PubMed totale : "+ListeAnnotationPubmedTotale.size());
		System.out.println("nb  Annotation Retrouvee : "+nbAnnotationRetrouvee);
		System.out.println("Rappel : "+nbAnnotationRetrouvee*100/ListeAnnotationPubmedTotale.size());
		System.out.println("Précision : "+nbAnnotationRetrouvee*100/ListeAnnotationECMT.size());
		//Rappel=5, et Précision=3
		//Rappel=4, et Précision=11
	}*/
	
	public static void NumeroterArticle(String directoryPath) 
	{
		
		File directory = new File(directoryPath);
		int cpt=2000;
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
				System.out.println(nomArticle);
				File file = new File(subfiles[i].getPath().substring(0, subfiles[i].getPath().length()-12)+"Pubfr"+cpt+".xml");
				cpt--;
				subfiles[i].renameTo(file);
			}
			}
	}
	public static void Ordonnerdic(Dictionary<String, Integer> dic, ArrayList<String> Liste,Dictionary<String, Boolean> dicord) 
	{
		//Liste=new ArrayList<String>();
		int i = 0;
		while (i < dic.size()) {
			String max = MaxDic(dic,dicord);
			dicord.remove(max);
			dicord.put(max, true);
			Liste.add(max);
			i++;
		}
	}
	public static String MaxDic(Dictionary<String, Integer> dic,Dictionary<String, Boolean> dicord) {
		Enumeration<String> key = dic.keys();
		String Conceptsauv = "";
		int freqsauv = 0;
		int freq;
		while (key.hasMoreElements()) {
			String k = key.nextElement();
			freq = dic.get(k);
			boolean ord=dicord.get(k);
			if (freq > freqsauv && ord ==false) 
			{
				Conceptsauv = k;
				freqsauv = freq;
			}
		}
		//System.out.println(Conceptsauv+"      freq:"+freqsauv);
		return Conceptsauv;
	}
}
