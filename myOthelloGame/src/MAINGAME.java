import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.LineBorder;

import jaco.mp3.player.MP3Player;
@SuppressWarnings("serial")


public class MAINGAME extends JFrame implements ActionListener{  
	private JFrame frame;
	static String path = "/home/ayoub/Desktop/myOthelloGame/src/";
	public static final String PLACED = path+"placed.mp3"; //SFX
	public static final String VICTORY = path+"victory.mp3";	
	MP3Player PlacedSOUND = new MP3Player(new File(PLACED));
	MP3Player VictorySOUND = new MP3Player(new File(VICTORY));

	static boolean notvalid = false;
	int nbr_pions_a_inverser[] = new int[8]; // nous aide à determiner le nombre des pions qui changent de couleur dans une direction
	
	static int[] directionx = {0,  0, 1,  1,  1, -1, -1, -1}; //nous aide à parcourir les 8 pions adjacents
	static int[] directiony = {1, -1, 1, -1,  0,  1, -1,  0}; 
	
	ImageIcon black = new ImageIcon(path+"BlackStone.jpg"); 
	ImageIcon white = new ImageIcon(path+"WhiteStone.jpg");
	ImageIcon blackNOW = new ImageIcon(path+"BlackStoneNOW.jpg"); 
	ImageIcon whiteNOW = new ImageIcon(path+"WhiteStoneNOW.jpg"); 
	ImageIcon blank = new ImageIcon(path+"blank.jpg");
	
	public String JoueurNoir;
	public String JoueurBlanc;
	
    boolean found = false;
    int oponent = 2;
    int turn = 1;
    JButton b[][];
    int[][] grid = new int[10][10];
    int n = 8; int m = 8;    // les dimensions
    
       public MAINGAME(){
    	   JoueurBlanc = MENU.stringBlanc.getText();
    	   JoueurNoir = MENU.stringNoir.getText();
    	   for (int x = 0;x<10;x++){
    		   grid[x][0] = 3;
    		   grid[x][9] = 3;
    		   grid[0][x] = 3;
    		   grid[9][x] = 3;
    	   }
    	   frame = new JFrame();
       this.setDefaultCloseOperation(EXIT_ON_CLOSE);
               b = new JButton [n][m]; // la creation d'une matrice des boutons 8x8
               setLayout(new GridLayout(n,m));
               for (int y = 0;y<m;y++){
                       for (int x = 0;x<n;x++){

                               b[x][y] = new JButton(blank);
                               b[x][y].addActionListener(this);
                               b[x][y].setBorder(new LineBorder(Color.decode("#222222")));
                               b[x][y].setPreferredSize(new Dimension(88,88));
                               add(b[x][y]);
                               b[x][y].setEnabled(true);
                       }
               }
               pack();
               setVisible(true);
               getContentPane().setLayout(null);
               start();
       }
       
       private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
    	    Image img = icon.getImage();  
    	    Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
    	    return new ImageIcon(resizedImage);
    	}
       
       
       public void start(){
    	   grid[4][4] = 1;
    	   b[3][3].setIcon(white);
    	   grid[4][5] = 2;
    	   b[3][4].setIcon(black);
    	   grid[5][4] = 2;
    	   b[4][3].setIcon(blackNOW);
    	   grid[5][5] = 1;
    	   b[4][4].setIcon(white);
       }
       
       
       
       public void run(int H, int V){
       	   Component temporaryLostComponent = null;
    	   notvalid = false;
    	   for (int x = 0;x<8;x++){
    		   nbr_pions_a_inverser[x] = 0; }
    	   
    	   chercher_pions_a_inverser(H,V);
           inverser_les_pions(H,V);
           
           if(notvalid == false){   // inverser les tours
	           if (turn == 1){ 	turn = 2; oponent = 1; }
	           else if(turn == 2){ turn = 1; oponent = 2; }
           notvalid = false;
           
    	   if (checkend() == true){ // si on a terminé
	    	   VictorySOUND.play();
    			int whitec = 0;
    			int blackc = 0; 
	    	    for (int y = 0;y<8;y++){
		   	       for (int x = 0;x<8;x++){
			   		   if (grid[x+1][y+1] == 1){ 
			   			   whitec++;   //calculer le nombre des pions blancs
			   		   } else if (grid[x+1][y+1] == 2){
			   			   blackc++; }}}  //calculer le nombre des pions noirs  }}}
	   		   if (blackc>whitec){
	              JOptionPane.showMessageDialog(temporaryLostComponent, "Game over! "+JoueurNoir+" gagne\n"+JoueurNoir+":"+
	   		   blackc+" points\n"+JoueurBlanc+": "+whitec+" points");
	   		   } else if (blackc<whitec){
		              JOptionPane.showMessageDialog(temporaryLostComponent, "Game over! "+JoueurBlanc+" gagne\n"+JoueurNoir+":"+
	   		   blackc+" points\n"+JoueurBlanc+": "+whitec+" points");} else {
	   			   JOptionPane.showMessageDialog(temporaryLostComponent, "Game over! Both players drew!");  }
	              System.exit(0); }}}
       
       public void actionPerformed(ActionEvent e){
           int V = 0;
           int H = 0;
    	   found =  false;
               JButton current = (JButton)e.getSource();
               for (int y = 0;y<m;y++){
                       for (int x = 0;x<n;x++){ //pour chercher la case cliquée
                               JButton t = b[x][y];
                               if(t == current){
                                       H=x;V=y; found =true;
                               }
                       }
               }
               if(!found) {
                       System.out.println("didn't find the button, there was an error "); System.exit(-1);
               }
            run(H,V); // on a cliqué sur la case de coordonnées (H,V)
       }
       
       public boolean check(){
    	   int count = 0;
    	   for (int y = 0;y<8;y++){
    		   for (int x = 0;x<8;x++){
    			   if (checkvalidity(x,y) == false){
    				   count++;
    			   }
    		   }
    	   }
    	   if (count == 64) {
    		   return true; }
    	   else {return false;}
       }
       
       public boolean checkend(){
    	   if (check() == true) {
    		   int T = turn;
    		   int O = oponent;
    		   turn = O;
    		   oponent = T;
    		   if (check() == true) {
    		    	   return true;
    		   } else return false;
    	   }
    	   return false ; }
       
       public boolean checkvalidity(int x,int y){
    	   boolean cycle = true;
		   int I = 1;
    	   if (grid[x+1][y+1] == 0){
    		   for (int c = 0;c<8;c++){ //pour chaque direction
    			   cycle = true;
    			   I = 1;
    			   do{
	    			   if ((grid[x+1+(directionx[c]*I)][y+1+(directiony[c]*I)] == turn) && (I == 1)){
	    				   cycle = false;  
	        		   } else if ((grid[x+1+(directionx[c]*I)][y+1+(directiony[c]*I)] == turn) && (I > 1)){
	    				   return true;
	    			   } else if ((grid[x+1+(directionx[c]*I)][y+1+(directiony[c]*I)] == 3) || (grid[x+1+(directionx[c]*I)][y+1+(directiony[c]*I)] == 0)){
	    				   cycle = false;
	    			   }
	    			   I++;
    			   } while(cycle == true);
    	   }
       }
    	   return false;
       }
       
       
       
       
       public void chercher_pions_a_inverser(int x, int y){
    	   boolean cycle = true;
		   int I = 1;
    	   if (grid[x+1][y+1] == 0){ //si la case est vide
    		   
    		   for (int c = 0;c<8;c++){ //chaque c signifie une direction
    			   cycle = true;
    			   I = 1;
    			   do{
	    			   if ((grid[x+1+(directionx[c]*I)][y+1+(directiony[c]*I)] == oponent)){
	    				   nbr_pions_a_inverser[c]++;
	    			   } else if (grid[x+1+(directionx[c]*I)][y+1+(directiony[c]*I)] == turn){
	    				   cycle = false;
	    			   } else if ((grid[x+1+(directionx[c]*I)][y+1+(directiony[c]*I)] == 3) ||
	    					   (grid[x+1+(directionx[c]*I)][y+1+(directiony[c]*I)] == 0)){
	    				   cycle = false;
	    				   nbr_pions_a_inverser[c] = 0;
	    			   }
	    			   I++;
    			   }while(cycle == true);
    		   }
       } // tableau nbr_pions_a_inverser nous aide à calculer le nombre des pions à inverser dans chaque direction
       }
       
       
       public void inverser_les_pions(int x, int y){ 
    	   int count = 0;
		   for(int z = 0;z<8;z++){
			   if (nbr_pions_a_inverser[z] != 0){
				   count++;  }}
		   if (count != 0){ //s'il y a un changement
			   grid[x+1][y+1] = turn; //màj la case séléctionée
               PlacedSOUND.play();
		   } else { //sinon
			   notvalid = true; // on fait rien
		   }
		   
    	   for (int a = 0;a<8;a++){
    		   for (int b = 1;b<=nbr_pions_a_inverser[a];b++){
    			   grid[x+1+(directionx[a]*b)][y+1+(directiony[a]*b)] = turn; }}
    	   	  
    	   for (int y2 = 0;y2<8;y2++){ // cette boucle met à jour convenablement les pions
    		   for (int x2 = 0;x2<8;x2++){
    			   if(x2 == x && y2 == y) {
    				   if (count != 0) {
    				   if (turn == 2) {b[x][y].setIcon(blackNOW);}
    				   if (turn == 1) {b[x][y].setIcon(whiteNOW);}
    			   }} else {
    			   if (grid[x2+1][y2+1] == 2){
    				   b[x2][y2].setIcon(black);  }
    			   if (grid[x2+1][y2+1] == 1){
        			   b[x2][y2].setIcon(white);  }   			   
    		   }}}}
       
   	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MAINGAME window = new MAINGAME();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
   

       public void windowIconified(WindowEvent e){}

		public void mouseClicked(MouseEvent e) { }

}//end class