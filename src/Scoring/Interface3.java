//package Scoring;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.omg.CORBA.portable.InputStream;


public class Interface3<JPael> extends JFrame implements ActionListener
{
    private JPanel bg;
    public JTextArea  inputUML;
    public static String texte="";
    public  Interface3()
    {
        super();
        this.setTitle("Représentation des annotations sous format RDF");
        this.setSize(800,700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //background
        bg = new JPanel();
        bg.setBackground(new Color(200,200,200));
        this.add(bg);
        //menus
        JMenuBar menuBar= new JMenuBar();
        JMenu menu_fichier = new JMenu("Fichier");
        //Les itemes
        JMenuItem menu_fichier_open = new JMenuItem("Scoring des annotations");
        menu_fichier_open.addActionListener((ActionListener) this);
        JMenuItem menu_fichier_close = new JMenuItem("Représentation des annotation sous format RDF");
        menu_fichier_close.addActionListener((ActionListener) this);
        menu_fichier.add(menu_fichier_open);
        menu_fichier.add(menu_fichier_close);
        menuBar.add(menu_fichier);
        this.setJMenuBar(menuBar);  
        GroupLayout layout = new GroupLayout(bg);
        bg.setLayout(layout);
        
  
        JPanel box1 = new JPanel();
        JButton button = new JButton("Ouvrir le fichier à représenter sous format RDF");
        JPanel bg2 = new JPanel();
        bg2.setBackground(new Color(200,200,200));
        this.add(bg2);
        box1.add(button);
        box1.setBorder(BorderFactory.createTitledBorder("Fichier à représenter sous format RDF"));
        bg2.add(box1);
        
        //
        JPanel CreationPanel = new JPanel();
        CreationPanel.setLayout(new BorderLayout());
        JLabel  instructionlabel = new JLabel("Annotations représentées sous format RDF");
        CreationPanel.add(instructionlabel,BorderLayout.NORTH);

        inputUML = new JTextArea("",26,70);
        // very important next 2 lines
        inputUML.setLineWrap(true);
        inputUML.setWrapStyleWord(true);
        // add it to a scrollpane
        CreationPanel.add(new JScrollPane(inputUML),BorderLayout.CENTER);

        bg2.add(CreationPanel);
        button.addActionListener( new ActionListener()
    	{
    	    public void actionPerformed(ActionEvent e)
    	    {
    	    	 final JFileChooser fc = new JFileChooser();
    	            int returnVal= fc.showOpenDialog(Interface3.this);
    	            System.out.print(returnVal);
    	            if(returnVal==JFileChooser.APPROVE_OPTION)
    	            {
    	                File file= fc.getSelectedFile();
    	                JsonToRdf.FromJsonToRDF(file.getPath(),"ResRdf.rdf");
    	                LireFichier("ResRdf.rdf");
    	            }
    	    }
    	});
        JPanel box2 = new JPanel();
        JButton button2 = new JButton("Sauvgarder le résultat");
        box2.add(button2);
        box2.setBorder(BorderFactory.createTitledBorder("Sauvgarder"));
        bg2.add(box2);
        button2.addActionListener( new ActionListener()
    	{
    	    public void actionPerformed(ActionEvent e)
    	    {
    	    	JFileChooser fileChooser = new JFileChooser();
    	    	fileChooser.setDialogTitle("Specify a file to save");   
    	    	 
    	    	int userSelection = fileChooser.showSaveDialog(Interface3.this);
    	    	 
    	    	if (userSelection == JFileChooser.APPROVE_OPTION) {
    	    	    File fileToSave = fileChooser.getSelectedFile();
    	    	    SauvgardereTexte(fileToSave.getPath());
    	    	    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
    	    	}
    	    }
    	});
        this.setVisible(true);
        
    }
    public void LireFichier(String fichier)
    {
 	   try{
 			FileInputStream ips=new FileInputStream(fichier); 
 			InputStreamReader ipsr=new InputStreamReader(ips);
 			BufferedReader br=new BufferedReader(ipsr);
 			String ligne;
 			while ((ligne=br.readLine())!=null){
 				inputUML.append(ligne.replace("\\/","/"));
 				texte+=ligne;
 			}
 			br.close(); 
 		}		
 		catch (Exception e){
 			System.out.println(e.toString());
 		}
    }
    public static void SauvgardereTexte(String Path) 
	 { 
		 try { 
	          File file = new File(Path);
	          BufferedWriter output = new BufferedWriter(new FileWriter(file));
	          output.write(texte.replace("\\/","/"));
	           output.close();
	        } catch ( IOException e ) 
	        {
	           e.printStackTrace();
	        } 
	 }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
  
}


 
    
 
