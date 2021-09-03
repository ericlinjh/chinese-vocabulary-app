package guiClasses;

// Importing generic libraries
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Importing GUI libraries
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/*
 * This is essentially the Main Menu for my program, except it's named the Mode Selection screen and not Main Menu.
 * This screen allows you to access the other three main screens of the program. That's really all it does.
 * There should not be any bugs found in this class.
 */
public class ModeSelection {
	
	// Initializing GUI objects
	private static JPanel modePanel;
	private static JButton userStatsButton;
	private static JButton wordListButton;
	private static JButton quizButton;
	private static JPanel titleForeground;
	private static JPanel titleBackground;
	private static JLabel titleText;
	
	// Constructor
	public ModeSelection() {
		
		// Setting the Look and Feel
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
	
	
		// Initializing the Start Panel
		modePanel = new JPanel();
		modePanel.setBounds(0, 0, 1040, 710);
		modePanel.setBackground(new java.awt.Color(251, 216, 216));
		modePanel.setOpaque(true);
		modePanel.setBorder(BorderFactory.createLineBorder(new java.awt.Color(245, 94, 97), 50));
		modePanel.setLayout(null);
		
		// Initializing foreground of title label
		titleForeground = new JPanel();
		titleForeground.setBounds(282, 92, 476, 54);
		titleForeground.setBackground(new java.awt.Color(245, 94, 97));
		titleForeground.setBorder(BorderFactory.createLineBorder(new java.awt.Color(251, 216, 216), 3));
		titleForeground.setVisible(true);
		
		// Initializing Title Text
		titleText = new JLabel("Mode Selection", SwingConstants.CENTER);
		titleText.setBounds(270, 80, 500, 78);
		modePanel.add(titleText);
		titleText.setFont(new Font("Century Gothic", Font.BOLD, 36));
		titleText.setForeground(Color.WHITE);
		modePanel.add(titleForeground);
				
		// Initializing background of title label
		titleBackground = new JPanel();
		titleBackground.setBounds(270, 80, 500, 78);
		titleBackground.setBackground(new java.awt.Color(245, 94, 97));
		titleBackground.setVisible(true);
		modePanel.add(titleBackground);
		
		// Initializing the User Stats button
		userStatsButton = new JButton(new ImageIcon("images/modeselection/userstats.png"));
		userStatsButton.setBounds(152, 270, 200, 200);
		userStatsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MainMenu.frame.getContentPane().removeAll();
				new UserStats();
				MainMenu.frame.repaint();
				MainMenu.frame.revalidate();
				
			}
		});
		modePanel.add(userStatsButton);
		
		// Initializing the Word List button
		wordListButton = new JButton(new ImageIcon("images/modeselection/wordlist.png"));
		wordListButton.setBounds(412, 270, 200, 200);
		wordListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MainMenu.frame.getContentPane().removeAll();
				new WordList(launcher.Main.wordList);
				MainMenu.frame.repaint();
				MainMenu.frame.revalidate();
				
			}
		});
		modePanel.add(wordListButton);
		
		// Initializing the Quiz Button
		quizButton = new JButton(new ImageIcon("images/modeselection/quiz.png"));
		quizButton.setBounds(672, 270, 200, 200);
		quizButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MainMenu.frame.getContentPane().removeAll();
				new Quiz();
				MainMenu.frame.repaint();
				MainMenu.frame.revalidate();
			}
		});
		modePanel.add(quizButton);
		
		// Final touches and adding the panel to the frame
		MainMenu.frame.setTitle("Mode Selection");
		MainMenu.frame.add(modePanel);
	}

}

