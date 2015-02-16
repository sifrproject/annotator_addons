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

//Linterface pour le choix de traitement à effectuer 1-Scoring des annotations ou 2-Représentation des annotation sous format RDF 
public class Intacface2<JPael> extends JFrame implements ActionListener
{
    private JPanel bg;
    public  Intacface2()
    {
        super();
        this.setTitle("Amélioration du processus d’annotation de l’annotateur de NCBO ");
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
        
  
        this.setVisible(true);
        
    }
    public void actionPerformed(ActionEvent e)
    {
    	 if(e.getActionCommand().equals("Scoring des annotations"))
        {
    		 Interface GF1= new Interface();
        }
        else if(e.getActionCommand().equals("Représentation des annotation sous format RDF"))
            {
        	 Interface3 GF= new Interface3();
            }
            

    }
	/*public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}*/
  
}


 
    
 