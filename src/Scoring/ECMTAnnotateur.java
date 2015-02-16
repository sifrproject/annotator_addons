
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ECMTAnnotateur {
/**
 * Open a url and read text data, for example html file
 * @param _url url
 * @return String file content
 */
    public static String getTextFile(String _url) 
    {
        BufferedReader reader = null;
        try {
            URL url = new URL(_url);
            URLConnection urlConnection = url.openConnection();
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            reader.close();
            return sb.toString();
        } 
        catch (Exception e) {
            e.printStackTrace();
            return"";
        }
    }
    public static void AnnotateTextesFR(String directoryPath) throws Exception{
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
        String text=sb.toString();
		text = URLEncoder.encode(text, "ISO-8859-1");
		//String res=ECMTAnnotateur.getTextFile("http://ecmt.chu-rouen.fr/servlets/Interpreteur?Mot="+text);
		 try { 
	          File file = new File("F:\\MasterBCD\\Stage\\corpus2\\ECMT\\EN\\"+nomArticle.substring(0, nomArticle.length()-3)+"xml");
	          BufferedWriter output = new BufferedWriter(new FileWriter(file));
	          output.write(ECMTAnnotateur.getTextFile("http://ecmt.chu-rouen.fr/servlets/Interpreteur?Mot="+text));
	           output.close();
	        } catch ( IOException e ) 
	        {
	           e.printStackTrace();
	        } 
		//WriteTexte("F:\\MasterBCD\\Stage\\corpus2\\ECMT\\FR\\"+nomArticle.substring(0, nomArticle.length()-3)+"xml",res);
		//WriteTexte("F:\\MasterBCD\\Stage\\corpus2\\ECMT\\EN\\"+nomArticle.substring(0, nomArticle.length()-3)+"xml",res);
			}
		}
    }
    public static void WriteTexte(String Path,String Text) 
	 { 
		
	 }
}
