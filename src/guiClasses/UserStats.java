package guiClasses;

import java.awt.Color;
// Importing generic libraries
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

import net.miginfocom.swing.MigLayout;

// Importing other packages' classes
import launcher.Main;

/*
 * This screen will display the user's stats. It will also display five of the user's worst words.
 * 
 * POSSIBLE ADDITIONS:
 * - I would have liked to make the labels showing the worst words buttons.
 * 		- This is probably doable and pretty easy actually, it's just that the labels covered the button and it didn't work :(
 * - I had planned on having a "See More" button that would bring up a modified WordList page, but I didn't have enough time to implement it
 * 		- It should be pretty simple to do, just generate the buttons based on the WorstWords array in the User object until the number of incorrect answers is detected to be 0
 */
public class UserStats {

	// Initializing GUI Objects
	private static JPanel userStatsPanel;
	private static JPanel titleForeground;
	private static JPanel titleBackground;
	private static JLabel titleText;
	private static JButton returnButton = new JButton(new ImageIcon("images/returnbutton.png"));
	private static JPanel statsPanel;
	private static JLabel numWordsLabel;
	private static JLabel incorrectAnswersLabel;
	private static JLabel correctAnswersLabel;
	private static JLabel successRateLabel;
	private static JPanel top5Panel;
	private static JLabel tooFewLabel = new JLabel("Please attempt more questions to see stats!");
	private static JLabel struggleLabel = new JLabel("Words you have trouble with:");
	private static JPanel worstPanel;
	private static JLabel chineseWordLabel;
	private static JLabel pinyinLabel;
	private static JLabel englishLabel;
	private static JLabel wordSuccessLabel;
	private static JLabel seeMoreLabel = new JLabel("<html><body style='text-align: center'>See<br>More");
	private static JPanel[] worstArray = new JPanel[5];
	
	// Constructor
	public UserStats() {
		
		// Setting the Look and Feel
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		// Finding the user's worst five words based on number of incorrect answers
		Main.currentUser.findWorst();
	
		// Initializing the Start Panel
		userStatsPanel = new JPanel();
		userStatsPanel.setBounds(0, 0, 1040, 710);
		userStatsPanel.setBackground(new java.awt.Color(251, 216, 216));
		userStatsPanel.setOpaque(true);
		userStatsPanel.setBorder(BorderFactory.createLineBorder(new java.awt.Color(245, 94, 97), 50));
		userStatsPanel.setLayout(null);
		
		// Initializing Title Text
		titleText = new JLabel("User Stats", SwingConstants.CENTER);
		titleText.setBounds(270, 80, 500, 78);
		titleText.setFont(new Font("Century Gothic", Font.BOLD, 36));
		titleText.setForeground(Color.WHITE);
		userStatsPanel.add(titleText);

		// Initializing foreground of title label
		titleForeground = new JPanel();
		titleForeground.setBounds(282, 92, 476, 54);
		titleForeground.setBackground(new java.awt.Color(245, 94, 97));
		titleForeground.setBorder(BorderFactory.createLineBorder(new java.awt.Color(251, 216, 216), 3));
		titleForeground.setVisible(true);
		userStatsPanel.add(titleForeground);

		// Initializing background of title label
		titleBackground = new JPanel();
		titleBackground.setBounds(270, 80, 500, 78);
		titleBackground.setBackground(new java.awt.Color(245, 94, 97));
		titleBackground.setVisible(true);
		userStatsPanel.add(titleBackground);
		
		// Initializing the Return Button
		returnButton.setBounds(781, 546, 150, 75);
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainMenu.frame.getContentPane().removeAll();
				new ModeSelection();
				MainMenu.frame.repaint();
				MainMenu.frame.revalidate();
			}
		});
		userStatsPanel.add(returnButton);
		
		// Initializing the panel that will show the user's stats
		statsPanel = new JPanel();
		statsPanel.setBackground(new java.awt.Color(245, 94, 97));
		statsPanel.setBounds(252, 194, 500, 100);
		statsPanel.setLayout(new MigLayout("", "[223.00px][9.00][260.00]", "[14px][10.00][]"));
		userStatsPanel.add(statsPanel);
		
		// Initializing the label that will display the number of questions the user has attempted
		numWordsLabel = new JLabel("Words Studied: " + Main.currentUser.getNumQuestions());
		numWordsLabel.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		numWordsLabel.setForeground(new java.awt.Color(251, 216, 216));
		statsPanel.add(numWordsLabel, "cell 0 0,alignx left,aligny top");
		
		// Calculating the user's success rate
		Main.currentUser.setSuccessRate();
		
		// Initializing the label that will display the success rate of the user
		successRateLabel = new JLabel("Success Rate: " + Main.currentUser.getSuccessRate() + "%");
		successRateLabel.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		successRateLabel.setForeground(new java.awt.Color(251, 216, 216));
		statsPanel.add(successRateLabel, "cell 2 0,alignx left,aligny top");
		
		// Initializing the label that will display the number of correct answers the user has done
		correctAnswersLabel = new JLabel("Correct Answers: " + Main.currentUser.getNumCorrect());
		correctAnswersLabel.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		correctAnswersLabel.setForeground(new java.awt.Color(251, 216, 216));
		statsPanel.add(correctAnswersLabel, "flowx,cell 0 2,alignx left,aligny top");
		
		// Initializing the label that will display the number of incorrect answers the user has done
		incorrectAnswersLabel = new JLabel("Incorrect Answers: " + Main.currentUser.getNumIncorrect());
		incorrectAnswersLabel.setFont(new Font("Century Gothic", Font.PLAIN, 22));
		incorrectAnswersLabel.setForeground(new java.awt.Color(251, 216, 216));
		statsPanel.add(incorrectAnswersLabel, "cell 2 2,alignx left,aligny top");
		
		// Creating a panel that will show the user's worst words
		top5Panel = new JPanel();
		top5Panel.setBounds(90, 325, 860, 210);
		top5Panel.setBackground(new java.awt.Color(245, 94, 97));
		userStatsPanel.add(top5Panel);
		
		// Creating a label that will tell the user that they have not done enough questions for quality stats to appear
		tooFewLabel.setFont(new Font("Century Gothic", Font.BOLD, 39));
		tooFewLabel.setForeground(new java.awt.Color(251, 216, 216));
		tooFewLabel.setBounds(20, 70, 824, 49);
		top5Panel.setLayout(null);
		
		// Setting up the label that describes what the top5Panel will do
		struggleLabel.setBounds(302, 5, 255, 23);
		struggleLabel.setFont(new Font("Century Gothic", Font.BOLD, 18));
		struggleLabel.setForeground(new java.awt.Color(251, 216, 216));
		top5Panel.add(struggleLabel);
		
		// Starting x co-ordinate of the first panel
		int x = 25;
		
		// Creating 5 panels that each show a different word
		for (int i = 0; i < 5; i++) {
			
			// Initializing the panel for all the labels to go on
			worstPanel = new JPanel();
			worstPanel.setBounds(x, 39, 150, 150);
			worstPanel.setBackground(new java.awt.Color(251, 216, 216));
			worstPanel.setLayout(new MigLayout("", "[424.00]", "[75.00][25.00][25.00][25.00]"));
			
			// Initializing the label that shows the Chinese character of the ith worst word
			chineseWordLabel = new JLabel(Main.currentUser.getWorst().get(i).getChinese());
			chineseWordLabel.setFont(new Font("SimSun", Font.BOLD, 36));
			chineseWordLabel.setForeground(new java.awt.Color(245, 94, 97));
			worstPanel.add(chineseWordLabel, "cell 0 0,alignx center");
			
			// Initializing the label that shows the Pinyin of the ith worst word
			pinyinLabel = new JLabel(Main.currentUser.getWorst().get(i).getPinyin());
			pinyinLabel.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
			pinyinLabel.setForeground(new java.awt.Color(245, 94, 97));
			worstPanel.add(pinyinLabel, "cell 0 1,alignx center");
			
			// Initializing the label that shows the English translation of the ith worst word
			englishLabel = new JLabel(Main.currentUser.getWorst().get(i).getEnglish());
			englishLabel.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
			englishLabel.setForeground(new java.awt.Color(245, 94, 97));
			worstPanel.add(englishLabel, "cell 0 2,alignx center");
			
			// Initializing the label that shows the number of times the user has gotten the question incorrect
			wordSuccessLabel = new JLabel(Main.currentUser.getWorst().get(i).getNumIncorrect() + "x Incorrect");
			wordSuccessLabel.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
			wordSuccessLabel.setForeground(new java.awt.Color(245, 94, 97));
			worstPanel.add(wordSuccessLabel, "cell 0 3,alignx center");
		
			// Adding the panel to the array
			worstArray[i] = worstPanel;
			
			// Moving the next panel 165 units to the right
			x += 165;
		}
		
		// Checking how many questions the user has done
		if (Main.currentUser.getNumQuestions() < 10) { // Less than 10 is too few
			top5Panel.add(tooFewLabel);
		} else { // Adding the panels
			top5Panel.add(worstArray[0]);
			top5Panel.add(worstArray[1]);
			top5Panel.add(worstArray[2]);
			top5Panel.add(worstArray[3]);
			top5Panel.add(worstArray[4]);
		}
		
		// Finalizing and adding the panel to the frame
		MainMenu.frame.setTitle("User Stats");
		MainMenu.frame.getContentPane().add(userStatsPanel);
		

	}
	
}
