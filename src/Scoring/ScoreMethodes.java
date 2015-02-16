//package Scoring;

import java.awt.List;
import java.awt.geom.Arc2D.Double;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.io.StringWriter;
import java.net.URLEncoder;

import javax.swing.Spring;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;

public class ScoreMethodes {
	public static Donnees d;
	public static int ClassementMax = 0;
	public static int ClassementMaxF = 0;
	public static int ClassementMaxF2 = 0;

	/*public static void main(String[] args) throws UnsupportedEncodingException {
		//d = new Donnees();
		//System.out.println("Fin Classement");
		//String res= chercherUrl.getTextFile("http://data.bioontology.org/search?q=melanoma&ontologies=MESH&apikey=24e03810-54e0-11e0-9d7b-005056aa3316&exact_match=true&include_properties=true");
		Intacface2 GF= new Intacface2();
		//Intacface2 GF= new Intacface2();
		//AO.NcboObsOntology();
		//FromJsonToRDF("F:\\testwithoutcontext.json");
		// ScoreF();
		//Comparaison.ArticlesPubMed("F:\\MasterBCD\\Stage\\corpus\\PubMed");
	// Comparaison.AnnotationsArticles("F:\\MasterBCD\\Stage\\corpus\\NCBOannotationssansH");
		//EvaluationAnnotators.NCBOAnnotatorEN("F:\\MasterBCD\\Stage\\corpus2\\NCBOannotationssans0H");
		// Comparaison.ReadAndPrintXMLFile();
		// xmlfile.ReadFileXML("F:\\MasterBCD\\Stage\\PubMed.xml");
		//xmlfile.ReadFileXML2("F:\\MasterBCD\\Stage\\corpus\\PubMed.xml");
		//Comparaison.TitreAbstractArticles("F:\\MasterBCD\\Stage\\corpus\\PubMed");
		//xmlfile.Writexml();
		// Scorerx
		//ClasseSearch.Searche("Tomography, X-Ray Computed");
		/*try {
			//AnnotateText.AnnotateTexteFromeTexteHFR("F:\\MasterBCD\\Stage\\test.json","cancer",false);
			AnnotateText.AnnotateTextes("F:\\MasterBCD\\Stage\\corpus2\\titrescode","sans");
			//AnnotateText.AnnotateTexteFrome("F:\\MasterBCD\\Stage\\corpus\\res.json","Malignancy arising in seborrheic keratosis: two cases report");
			//AnnotateText.AnnotateTextesFR("F:\\MasterBCD\\Stage\\corpus2\\titresFR","sans",false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//xmlfile.ReadFileXMLKhadi("F:\\MasterBCD\\Stage\\corpus2\\PubMed.xml");
		//xmlfile.ReadFileXMLKhadi("F:\\MasterBCD\\Stage\\corpus2\\ECMT\\AnnotationPubMedfrEcmtExp.xml");
		//ArrayList<String> ListeAnnotationPubmed =xmlfile.ReadFileXMLKhadiFMTI("F:\\MasterBCD\\Stage\\corpus2\\FMTI\\PubMedEn\\Puben1.xml");
		//System.out.println (ListeAnnotationPubmed);
		//Scorewithoutcontext("F:\\testwithoutcontext.json", "F:\\annotation.json");
		//ScoreF("F:\\testScoreF.json","F:\\annotation.json");*/
		//ScoreF2("F:\\testScoreF2.json", "F:\\annotation.json");
		//JsonToRdf.FromJsonToRDF("F:\\annotation.json","F:\\annotationrdf.rdf");
		//EvaluationAnnotators.NumeroterArticle("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedFR");
		//EvaluationAnnotators.NCBOAnnotatorEN("F:\\MasterBCD\\Stage\\corpus2\\NCBOannotationssans0H");
		//EvaluationAnnotators.NCBOAnnotatorFR("F:\\MasterBCD\\Stage\\corpus2\\NCBOannotationssans0HFR");
		//EvaluationAnnotators.FMTIEN("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMed");
		//EvaluationAnnotators.FMTIFR("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedFR");
		//xmlfile.ReadFileXMLKhadiECMT("F:\\MasterBCD\\Stage\\corpus2\\ECMT\\AnnotationPubMedfrEcmtExp.xml");
		//EvaluationAnnotators.ECMTFR("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedcode");
		//EvaluationAnnotators.ECMTEN("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedcode");
		
		//EvaluationAnnotators.NCBOAnnotatorEN2("F:\\MasterBCD\\Stage\\corpus2\\NCBOannotationssans0H");
		//EvaluationAnnotators.NCBOAnnotatorFR2("F:\\MasterBCD\\Stage\\corpus2\\NCBOannotationssans0HFR");
		//EvaluationAnnotators.FMTIEN2("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMed");
		//EvaluationAnnotators.FMTIFR2("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedFR");
		//xmlfile.ReadFileXMLKhadiECMT("F:\\MasterBCD\\Stage\\corpus2\\ECMT\\AnnotationPubMedfrEcmtExp.xml");
		//EvaluationAnnotators.ECMTFR2("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedcode");
		//EvaluationAnnotators.ECMTEN2("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedcode");
		//xmlfile.CorrigerFileXML("F:\\MasterBCD\\Stage\\corpus2\\ECMT\\AnnotationPubMedenEcmtExp.xml","F:\\MasterBCD\\Stage\\corpus2\\ECMT\\AnnotationPubMedenEcmtExp1.xml");
		/*String text="Le carcinosarcome du poumon. A propos d'un cas";
		text = URLEncoder.encode(text, "ISO-8859-1");
		String res=ECMTAnnotateur.getTextFile("http://ecmt.chu-rouen.fr/servlets/Interpreteur?Mot="+text);
		System.out.print(res);*/
		/*try {
			ECMTAnnotateur.AnnotateTextesFR("F:\\MasterBCD\\Stage\\corpus2\\titrescode");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//EvaluationAnnotators.ECMTFRetEN("F:\\MasterBCD\\Stage\\corpus2\\ConceptMesh-AnntaPubMedcode");
	}*/
	// Scores aggrégés
	public static void CalculeScorewithoutcontext(String FilePath) 
	{
		d.Dictscoreconcept = new Hashtable<String, StructScore>();
		JSONParser parser = new JSONParser();
		try {
			JSONArray a = (JSONArray) parser.parse(new FileReader(FilePath));
			int score = 0;
			JSONObject annotatedClass;
			String id = "";
			for (Object o : a) 
			{
				JSONObject annotation = (JSONObject) o;
				// Calcluer le score de l'annotation directe
				annotatedClass = (JSONObject) annotation.get("annotatedClass");
				// Récupérer l'id de concept de l'annotation directe
				id = (String) annotatedClass.get("@id");
				// Récupérer l'annotation directe et son matchType
				JSONArray annotations = (JSONArray) annotation.get("annotations");
				int nbannotation = 0;
				score = 0;
				ArrayList<String> termeAnnote = new ArrayList();
				for (Object c : annotations) 
				{
					JSONObject uneannotation = (JSONObject) c;
					String matchType = (String) uneannotation.get("matchType");
					String text = (String) uneannotation.get("text");
					if (!termeAnnote.contains(text)) {
						termeAnnote.add(text);
					}

					if (matchType.equals("PREF")) {
						score += 10;
					} else {
						if (matchType.equals("SYN")) {
							score += 8;
						}
					}
					nbannotation++;
					}
				// Vérifier c'est le id de concept existe déja dans le
				// dictionnaire ou pas
				if (d.Dictscoreconcept.get(id) == null) {
					StructScore nouvellestr = new StructScore(termeAnnote,
							score, 0, 0, annotatedClass, false, false, false,
							false, 0, 0, 0);
					d.Dictscoreconcept.put(id, nouvellestr);
				} else {
					StructScore nouvellestr = d.Dictscoreconcept.get(id);
					nouvellestr.score += score;
					for (int i = 0; i < termeAnnote.size(); i++) {
						if (!nouvellestr.TermesAnnotes.contains(termeAnnote.get(i))) 
						{
							nouvellestr.TermesAnnotes.add(termeAnnote.get(i));
						}
					}
					d.Dictscoreconcept.remove(id);
					d.Dictscoreconcept.put(id, nouvellestr);
				}
				// Calcluer le score de l'annotation directe
				// Récupérer les annotation hierarchiques et leur distances dans
				// la hierarchie
				JSONArray hierarchy = (JSONArray) annotation.get("hierarchy");

				for (Object h : hierarchy) {
					JSONObject unehierarchie = (JSONObject) h;
					// Calcluer le score de l'annotation directe
					annotatedClass = (JSONObject) unehierarchie
							.get("annotatedClass");
					// Récupérer l'id de concept de l'annotation directe
					id = (String) annotatedClass.get("@id");
				Long dista = (Long) unehierarchie.get("distance");

					if (dista > 12) {
						score = 1 * nbannotation;
					} else {
						double res = 1 + 10 * Math.exp(-0.2 * dista);
						score = (int) res;
						score = score * nbannotation;
					}
					// Vérifier c'est le id de concept existe déja dans le
					// dictionnaire ou pas
					if (d.Dictscoreconcept.get(id) == null) 
					{
						StructScore nouvellestr = new StructScore(termeAnnote,
								score, 0, 0, annotatedClass, false, false,
								false, true, 0, 0, 0);
						d.Dictscoreconcept.put(id, nouvellestr);
					} 
					else 
					{
						StructScore nouvellestr = d.Dictscoreconcept.get(id);
						nouvellestr.score = nouvellestr.score + score;
						nouvellestr.annotatedClass = nouvellestr.annotatedClass;
						for (int i = 0; i < termeAnnote.size(); i++) {
							if (!nouvellestr.TermesAnnotes.contains(termeAnnote.get(i))) 
							{
								nouvellestr.TermesAnnotes.add(termeAnnote.get(i));
							}
						}
						d.Dictscoreconcept.remove(id);
						d.Dictscoreconcept.put(id, nouvellestr);
					}
				}

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

	public static void Scorewithoutcontext(String FilePath, String FilePath2) {
		CalculeScorewithoutcontext(FilePath2);
		JSONObject annotationscore = new JSONObject();
		System.out.println("Debut de calcule de score fffffffffffffffff");
		OrdonnerDict(d.Dictscoreconcept, "score");
		// Sauvgaregr le score dans un nouveau fichier
		try {
			FileWriter filer = new FileWriter(FilePath);
			Iterator i = d.ConceptTries.iterator();
			while (i.hasNext()) {
				String K = (String) i.next();
				StructScore elementdic = d.Dictscoreconcept.get(K);
				annotationscore.put("score", elementdic.score);
				annotationscore.put("@id", K);
				annotationscore.put("Termes", elementdic.TermesAnnotes);
				annotationscore.put("Classement", elementdic.ClassementScore+ "/" + ClassementMax);
				try {
					filer.write(annotationscore.toString());
					filer.flush();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			filer.close();
				System.out.println("Fin score");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Scores cvalue
		public static void CalculeScoreCvaluescore(String PathFile) {
			JSONParser parser = new JSONParser();
			try {
				JSONArray a = (JSONArray) parser.parse(new FileReader(PathFile));
				JSONObject annotatedClass;
				JSONObject annotationscore = new JSONObject();
				for (Object o : a) {
					JSONObject annotation = (JSONObject) o;
					// Calcluer le score de l'annotation directe
					annotatedClass = (JSONObject) annotation.get("annotatedClass");
					// Récupérer l'annotation directe et son matchType
					JSONArray annotations = (JSONArray) annotation.get("annotations");
					for (Object c : annotations) {
						JSONObject uneannotation = (JSONObject) c;
						String Terme = (String) uneannotation.get("text");
						Long from = (Long) uneannotation.get("from");
						Long to = (Long) uneannotation.get("to");
						// Vérifier c'est le terme existe déja dans le dictionnaire
						// ou pas
						if (d.DictCvalue.get(Terme) == null) {
							ArrayList<String> Position = new ArrayList<String>();
							Position.add(from + "-" + to);
							CvalueTerme nouvellestr = new CvalueTerme(Position, 1,0);
							d.DictCvalue.put(Terme, nouvellestr);
						} else 
						{
							CvalueTerme nouvellestr = d.DictCvalue.get(Terme);
							if (!nouvellestr.Position.contains(from + "-" + to))
							{
								nouvellestr.Freq += 1;
								nouvellestr.Position.add(from + "-" + to);
								d.DictCvalue.remove(Terme);
								d.DictCvalue.put(Terme, nouvellestr);
							}
						}
					}

				}
				// Sauvgaregr le score dans un nouveau fichier
				Enumeration<String> key = d.DictCvalue.keys();
				while (key.hasMoreElements()) 
				{
					String k = key.nextElement();
					CvalueTerme nouvellestr = d.DictCvalue.get(k);
					ArrayList<String> ListTermes = calculerTa(k, d.DictCvalue);
					String[] tab = k.split(" ");
					int taille = tab.length;
					double cvalue = 0;
					if (ListTermes.isEmpty()) 
					{
						cvalue = calculelog(taille, 2) * nouvellestr.Freq;
					} 
					else 
					{
						int sommeFreq = 0;
						for (String s : ListTermes) 
						{
							CvalueTerme nouvellestr2 = d.DictCvalue.get(s);
							sommeFreq += nouvellestr2.Freq;
						}
						cvalue = calculelog(taille, 2)* (nouvellestr.Freq - 1 / ListTermes.size()* sommeFreq);
					}
					nouvellestr.cvalue = cvalue;
					d.DictCvalue.remove(k);
					d.DictCvalue.put(k, nouvellestr);
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
		
	public static void CalculeScoreF() {
		Enumeration<String> key = d.Dictscoreconcept.keys();
		while (key.hasMoreElements()) {
			String K = key.nextElement();
			StructScore elementdic = d.Dictscoreconcept.get(K);
			double cvalue = 0;
			for (Object terme : elementdic.TermesAnnotes) {

				CvalueTerme nouvellestr2 = d.DictCvalue.get(terme);
				if (nouvellestr2 != null) {
					cvalue += nouvellestr2.cvalue;
				}
			}
			
				if (cvalue == 0) 
				{
					elementdic.scoreF = calculelog(elementdic.score, 10);
					elementdic.scoreF2 = calculelog(elementdic.score, 10);
					} 
				else 
				{
					elementdic.scoreF = calculelog(elementdic.score, 10)* cvalue;
						elementdic.scoreF2 = calculelog(elementdic.score, 10)* cvalue;
						if(elementdic.herarchie)
						{
							elementdic.scoreF2 = calculelog(elementdic.score, 10);
						}
				}
			
			d.Dictscoreconcept.remove(K);
			d.Dictscoreconcept.put(K, elementdic);
			// System.out.println(K+" aaaaaaaaa"+elementdic.score+"ccccccc"+cvalue+"ggg"+calculelog
			// (elementdic.score,10)*cvalue);
		}
	}

	// Score+Cvalue si le paramètres de CalculeScoreF est false on lisse la Fscore des hiérachie sans les
	public static void ScoreF(String FilePath, String FilePath2) 
	{
		CalculeScorewithoutcontext(FilePath2);
		CalculeScoreCvaluescore(FilePath2);
		CalculeScoreF();
		OrdonnerDict(d.Dictscoreconcept, "scoreF");
		// Sauvgaregr le score dans un nouveau fichier
		JSONObject annotationscore = new JSONObject();
		try {
			FileWriter filer;
			filer = new FileWriter(FilePath);

			Enumeration<String> key = d.Dictscoreconcept.keys();
			Iterator i = d.ConceptTriesF.iterator();
			while (i.hasNext()) {
				String K = (String) i.next();
				StructScore elementdic = d.Dictscoreconcept.get(K);
				annotationscore.put("score", elementdic.score);
				annotationscore.put("scoreF", elementdic.scoreF);
				annotationscore.put("@id", K);
				annotationscore.put("Classement", elementdic.ClassementScoreF	+ "/" + ClassementMaxF);
				annotationscore.put("Termes", elementdic.TermesAnnotes);
				System.out.println(K+" aaaaaaaaa"+d.Dictscoreconcept.get(K).TermesAnnotes);
				try {
					filer.write(annotationscore.toString());
					filer.flush();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			filer.close();
			//System.out.println("Fin scoreF");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	// Score+Cvalue si le paramètres de CalculeScoreF est true on lisse la Fscore à 1
	public static void ScoreF2(String FilePath,String FilePath2) {

		CalculeScorewithoutcontext(FilePath2);
		CalculeScoreCvaluescore(FilePath2);
		CalculeScoreF();
		OrdonnerDict(d.Dictscoreconcept, "scoreF2");
		// Sauvgaregr le score dans un nouveau fichier
		JSONObject annotationscore = new JSONObject();
		try {
			FileWriter filer;
			filer = new FileWriter(FilePath);

			Enumeration<String> key = d.Dictscoreconcept.keys();
			Iterator i = d.ConceptTriesF2.iterator();
			while (i.hasNext()) {
				String K = (String) i.next();
				StructScore elementdic = d.Dictscoreconcept.get(K);
				annotationscore.put("score", elementdic.score);
				annotationscore.put("scoreF2", elementdic.scoreF2);
				annotationscore.put("@id", K);
				annotationscore.put("Classement", elementdic.ClassementScoreF2	+ "/" + ClassementMaxF2);
				annotationscore.put("Contexte", elementdic.herarchie);
				annotationscore.put("Termes", elementdic.TermesAnnotes);
				// System.out.println(K+" aaaaaaaaa"+d.Dictscoreconcept.get(K).TermesAnnotes);
				try {
					filer.write(annotationscore.toString());
					filer.flush();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			filer.close();
			System.out.println("Fin score F2");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Score non aggrégés
	public static void CalculeScorewithcontext() {
		JSONParser parser = new JSONParser();
		try {

			JSONArray a = (JSONArray) parser.parse(new FileReader(
					"F:\\annotation.json"));
			FileWriter filer = new FileWriter("F:\\testwithcontext.json");
			int score = 0;
			JSONObject annotationscore = new JSONObject();
			for (Object o : a) {
				JSONObject annotation = (JSONObject) o;
				JSONObject annotationsav = (JSONObject) o;

				JSONObject annotatedClassdirect = (JSONObject) annotation
						.get("annotatedClass");
				annotationscore.put("annotatedClass", annotatedClassdirect);
				try {
					filer.write(annotationscore.toString());
					filer.flush();

				} catch (IOException e) {
					e.printStackTrace();
				}
				JSONArray hierarchy = (JSONArray) annotation.get("hierarchy");
				JSONArray listhierarchy = new JSONArray();
				for (Object h : hierarchy) {
					JSONObject unehierarchie = (JSONObject) h;
					Long dista = (Long) unehierarchie.get("distance");
					JSONObject annotatedClass = (JSONObject) unehierarchie
							.get("annotatedClass");

					System.out.println(dista + "");
					if (dista > 12) {
						score = 1;
					} else {
						double res = 1 + 10 * Math.exp(-0.2 * dista);
						score = (int) res;
					}

					JSONObject info = new JSONObject();
					info.put("score", score);
					info.put("distance", dista);
					info.put("annotatedClass", annotatedClass);
					listhierarchy.add(info);
				}
				annotationscore.put("hierarchy", listhierarchy);
				try {
					filer.write(annotationscore.toString());
					filer.flush();

				} catch (IOException e) {
					e.printStackTrace();
				}
				JSONArray annotations = (JSONArray) annotation
						.get("annotations");
				JSONObject annotescore = new JSONObject();
				JSONArray listannotattion = new JSONArray();
				for (Object c : annotations) {
					JSONObject uneannotation = (JSONObject) c;
					String matchType = (String) uneannotation.get("matchType");
					String text = (String) uneannotation.get("text");
					Long from = (Long) uneannotation.get("from");
					Long to = (Long) uneannotation.get("to");
					System.out.println(matchType + "");
					if (matchType.equals("PREF")) {
						System.out.println(matchType + "ok");
						score = 10;
					} else {
						if (matchType.equals("SYN")) {
							score = 8;
						}
					}
					JSONObject info = new JSONObject();
					info.put("from", from);
					info.put("to", to);
					info.put("matchType", matchType);
					info.put("text", text);
					info.put("score", score);
					listannotattion.add(info);

				}
				annotationscore = new JSONObject();
				annotationscore.put("annotations", listannotattion);
				try {
					filer.write(annotescore.toString());
					filer.flush();

				} catch (IOException e) {
					e.printStackTrace();
				}

				JSONArray mappings = (JSONArray) annotation.get("mappings");
				annotationscore.put("mappings", mappings);
				try {
					filer.write(annotationscore.toString());
					filer.flush();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			filer.close();

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

	
	// Calculer le Ta
	public static ArrayList<String> calculerTa(String terme,
			Dictionary<String, CvalueTerme> Dict) {
		ArrayList<String> ListTermes = new ArrayList<String>();
		Enumeration<String> key = Dict.keys();
		while (key.hasMoreElements()) {
			String k = key.nextElement();
			if (k.contains(terme) && !k.equals(terme)) {
				ListTermes.add(k);
			}
		}
		return ListTermes;
	}

	public static double calculelog(int x, int base) {
		return (double) (Math.log(x) / Math.log(base));
	}

	public static void OrdonnerDict(Dictionary<String, StructScore> dic,	String TypeScore) {
		d.ConceptTries = new ArrayList<String>();
		d.ConceptTriesF = new ArrayList<String>();
		d.ConceptTriesF2 = new ArrayList<String>();
		int classement = 0;
		int classementegale = 1;
		ArrayList<Integer> Listeclassement = new ArrayList<Integer>();
		Dictionary<String, Integer> DictClasssement = new Hashtable<String, Integer>();
		ArrayList ListeclassementF = new ArrayList();
		// System.out.println("cooooo");
		Dictionary<String, StructScore> Dictordonne = new Hashtable<String, StructScore>();
		Dictordonne = dic;
		int i = 0;
		while (i < Dictordonne.size()) {
			String max = MaxDic(Dictordonne, TypeScore);
			StructScore nouvellestr = Dictordonne.get(max);
			if (nouvellestr != null) {
				if (TypeScore == "score") {
					nouvellestr.trie = true;
					if (!Listeclassement.contains(nouvellestr.score)) {
						classement=classement+classementegale;
						Listeclassement.add(nouvellestr.score);
						classementegale=1;
					}
					else
					{
						classementegale++;
					}
					nouvellestr.ClassementScore = classement;
					d.ConceptTries.add(max);
				} else {
					if (TypeScore == "scoreF") {
						nouvellestr.trieF = true;
						if (!ListeclassementF.contains(nouvellestr.scoreF)) {
							classement=classement+classementegale;
							ListeclassementF.add(nouvellestr.scoreF);
							classementegale=1;
						}
						else
						{
							classementegale++;
						}
						nouvellestr.ClassementScoreF = classement;
						d.ConceptTriesF.add(max);
					} else {
						nouvellestr.trieF2 = true;
						if (!ListeclassementF.contains(nouvellestr.scoreF2)) {
							classement=classement+classementegale;
							ListeclassementF.add(nouvellestr.scoreF2);
							classementegale=1;
						}
						else
						{
							classementegale++;
						}
						nouvellestr.ClassementScoreF2 = classement;
					}
					d.ConceptTriesF2.add(max);
				}
				Dictordonne.remove(max);
				Dictordonne.put(max, nouvellestr);
			//	System.out.println("Max est :"+max);

			}
			i++;
		}
		if (TypeScore == "score") {
			ClassementMax = classement;
		} else {
			if (TypeScore == "scoreF") {
				ClassementMaxF = classement;
			} else {
				ClassementMaxF2 = classement;
			}
		}
		 //System.out.println("Fin Classement"+TypeScore);

	}

	public static void OrdonnerDictsav(Dictionary<String, StructScore> dic,	String TypeScore) {
		d.ConceptTries = new ArrayList<String>();
		d.ConceptTriesF = new ArrayList<String>();
		d.ConceptTriesF2 = new ArrayList<String>();
		int classement = 0;
		ArrayList<Integer> Listeclassement = new ArrayList<Integer>();
		Dictionary<String, Integer> DictClasssement = new Hashtable<String, Integer>();
		ArrayList ListeclassementF = new ArrayList();
		// System.out.println("cooooo");
		Dictionary<String, StructScore> Dictordonne = new Hashtable<String, StructScore>();
		Dictordonne = dic;
		int i = 0;
		while (i < Dictordonne.size()) {
			String max = MaxDic(Dictordonne, TypeScore);
			StructScore nouvellestr = Dictordonne.get(max);
			if (nouvellestr != null) {
				if (TypeScore == "score") {
					nouvellestr.trie = true;
					if (!Listeclassement.contains(nouvellestr.score)) {
						classement++;
						Listeclassement.add(nouvellestr.score);
					}
					nouvellestr.ClassementScore = classement;
					d.ConceptTries.add(max);
				} else {
					if (TypeScore == "scoreF") {
						nouvellestr.trieF = true;
						if (!ListeclassementF.contains(nouvellestr.scoreF)) {
							classement++;
							ListeclassementF.add(nouvellestr.scoreF);
						}
						nouvellestr.ClassementScoreF = classement;
						d.ConceptTriesF.add(max);
					} else {
						nouvellestr.trieF2 = true;
						if (!ListeclassementF.contains(nouvellestr.scoreF2)) {
							classement++;
							ListeclassementF.add(nouvellestr.scoreF2);
						}
						nouvellestr.ClassementScoreF2 = classement;
					}
					d.ConceptTriesF2.add(max);
				}
				Dictordonne.remove(max);
				Dictordonne.put(max, nouvellestr);
			//	System.out.println("Max est :"+max);

			}
			i++;
		}
		if (TypeScore == "score") {
			ClassementMax = classement;
		} else {
			if (TypeScore == "scoreF") {
				ClassementMaxF = classement;
			} else {
				ClassementMaxF2 = classement;
			}
		}
		 //System.out.println("Fin Classement"+TypeScore);

	}
	public static String MaxDic(Dictionary<String, StructScore> dic,
			String TypeScore) {
		Enumeration<String> key = dic.keys();
		String Conceptsauv = "";
		double Scoresauv = 0;
		StructScore elementdic;

		while (key.hasMoreElements()) {
			String k = key.nextElement();
			elementdic = dic.get(k);
			double Score = 0;
			boolean trier = false;
			if (TypeScore == "score") {
				Score = elementdic.score;
				trier = elementdic.trie;
			} else {
				if (TypeScore == "scoreF") {
					Score = elementdic.scoreF;
					trier = elementdic.trieF;
				} else {
					Score = elementdic.scoreF2;
					trier = elementdic.trieF2;
				}
			}
			if (Conceptsauv == "" && Scoresauv == 0 && trier == false) {
				Conceptsauv = k;
				Scoresauv = Score;
			}
			if (Score > Scoresauv && trier == false) {
				Conceptsauv = k;
				Scoresauv = Score;
			}
		}
		return Conceptsauv;
	}
}