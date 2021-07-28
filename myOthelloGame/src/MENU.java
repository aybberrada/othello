
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;

public class MENU {

	private JFrame frame;
	public static JTextField stringBlanc;
	public static JTextField stringNoir;
	static String path = "/home/ayoub/Desktop/myOthelloGame/src/";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MENU window = new MENU();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MENU() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.decode("#5caf50")); 
		frame.setBounds(100, 100, 448, 294);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnStart = new JButton("START");
		btnStart.setBounds(120, 183, 199, 51);
		frame.getContentPane().add(btnStart);
		btnStart.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MAINGAME GameWin = new MAINGAME();
				
			} });
		
		JLabel logo = new JLabel("");

		
		ImageIcon imageIcon = new ImageIcon("/home/ayoub/Desktop/OthelloGame/src/logo.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(140, 63,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back

		logo.setIcon(new ImageIcon("/home/ayoub/Desktop/myOthelloGame/src/logo.png"));
		logo.setBounds(148, -74, 550, 250);
		frame.getContentPane().add(logo);
		
		stringBlanc = new JTextField();
		stringBlanc.setBounds(42, 129, 152, 26);
		frame.getContentPane().add(stringBlanc);
		stringBlanc.setColumns(10);
		
		stringNoir = new JTextField();
		stringNoir.setColumns(10);
		stringNoir.setBounds(253, 129, 152, 26);
		frame.getContentPane().add(stringNoir);
		

		JLabel jBlanc = new JLabel("Joueur 1 ( Blanc )");
		jBlanc.setBounds(62, 102, 186, 15);
		frame.getContentPane().add(jBlanc);
		
		JLabel jNoir = new JLabel("Joueur 2 ( Noir )");
		jNoir.setBounds(273, 102, 186, 15);
		frame.getContentPane().add(jNoir);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(25, 49, 70, 15);
		frame.getContentPane().add(lblNewLabel);
	}
}
