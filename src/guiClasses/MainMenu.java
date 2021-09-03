package guiClasses;

// Importing GUI libraries
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * This screen is the start screen, I don't really know why I named it the MainMenu. But it is the Main Menu
 * in the way it allows you to either quit or start. That's really all this screen does.
 */
public class MainMenu {

	// Initializing GUI objects
	public static JFrame frame;
	private static JPanel startPanel;
	private static JPanel titleBackground;
	private static JPanel titleForeground;
	private static JLabel titleText;
	private static JButton startButton = new JButton("Start");
	private static JButton quitButton = new JButton("Quit");

	// Initializes the frame
	public static void initializeFrame() {
		// Creates new frame
		frame = new JFrame();
		frame.setBounds(0, 0, 1056, 749);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Setting Look and Feel
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		// Makes the frame appear
		frame.setVisible(true);
		frame.setLayout(null);
		
		// Initializes Main Menu panel
		initializePanel();
	}
	
	// This method initializes the start panel
	private static void initializePanel() {
		
		// Initializing the Start Panel
		startPanel = new JPanel();
		startPanel.setBounds(0, 0, 1040, 710);
		startPanel.setBackground(new java.awt.Color(251, 216, 216));
		startPanel.setOpaque(true);
		startPanel.setBorder(BorderFactory.createLineBorder(new java.awt.Color(245, 94, 97), 50));
		startPanel.setLayout(null);

		// Initializing title text
		titleText = new JLabel("<html><body style='text-align: center'>Chinese Vocabulary<br>App");
		titleText.setFont(new Font("Century Gothic", Font.BOLD, 36));
		titleText.setBounds(330, 120, 400, 162);
		titleText.setForeground(Color.WHITE);
		startPanel.add(titleText);
		
		// Initializing Start Button
		startButton.setFont(new Font("Century Gothic", Font.BOLD, 36));
		startButton.setBounds(369, 315, 290, 77);
		startButton.setForeground(Color.WHITE);
		startButton.setBackground(new java.awt.Color(245, 94, 97));
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MainMenu.frame.getContentPane().removeAll();
				new ModeSelection();
				MainMenu.frame.repaint();
				MainMenu.frame.revalidate();
				
			}
		});
		startPanel.add(startButton);
		
		// Initializing Quit Button
		quitButton.setFont(new Font("Century Gothic", Font.BOLD, 36));
		quitButton.setBounds(369, 431, 290, 77);
		quitButton.setForeground(Color.WHITE);
		quitButton.setBackground(new java.awt.Color(245, 94, 97));
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.setVisible(false);
				frame.dispose();
				
			}
		});
		startPanel.add(quitButton);
		
		// Initializing foreground of title label
		titleForeground = new JPanel();
		titleForeground.setBounds(290, 148, 441, 114);
		titleForeground.setBackground(new java.awt.Color(245, 94, 97));
		titleForeground.setBorder(BorderFactory.createLineBorder(new java.awt.Color(251, 216, 216), 3));
		titleForeground.setVisible(true);
		startPanel.add(titleForeground);
		
		// Initializing background of title label
		titleBackground = new JPanel();
		titleBackground.setBounds(280, 136, 464, 137);
		titleBackground.setBackground(new java.awt.Color(245, 94, 97));
		titleBackground.setVisible(true);
		startPanel.add(titleBackground);
		
		// Finalizing and adding the panel to the frame.
		frame.setTitle("Main Menu");
		frame.add(startPanel);
	}

}
