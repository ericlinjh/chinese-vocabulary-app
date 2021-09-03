package guiClasses;

// Importing generic libraries
import java.util.ArrayList;

// Importing GUI Libraries
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Importing other packages' classes
import objectClasses.Word;

/*
 * This screen will display every single word found in the vocabularylist.txt file.
 * Each button will show the Chinese character, pinyin, and English translation.
 * There is also a button that allows the user to add a word to the screen.
 * 
 * AREAS OF CONCERN:
 * - When pressing one of the word buttons, the button covers the three labels that show Chinese character, pinyin, and English translation
 * 		- This wouldn't be a problem since you go to another screen right away, but if you press return it will stay covered
 * 			- This can probably be fixed by using a different layout, but I didn't have the time to learn another layout
 * - After adding a new word, it is not updated properly on the word list and will instead show the first word, "zero," in it's place.
 * 		- I honestly have no idea why this happens. Like when I was trying to fix it and printed out the word stored by that button it displayed
 * 		  the right word. However, the GUI says differently.
 * 			- I have no idea how to fix this, but it does fix itself if you restart the program. So it's not the biggest problem in the world.
 * - Some words get cut off if they're too long
 * 		- This is probably just a design feature, the buttons aren't designed in a way for this to be fixed elegantly
 */
public class WordList {
	
	// Initializing GUI objects
	private static JPanel wordListPanel;
	private static JPanel titleForeground;
	private static JPanel titleBackground;
	private static JLabel titleText;
	private static JButton returnButton = new JButton(new ImageIcon("images/returnButton.png"));
	private static JButton newWordButton = new JButton(new ImageIcon("images/wordlist/newwordbutton.png"));
	private static JPanel listPanel = new JPanel();
	private static JPanel wordPanel = new JPanel();
	
	// Initializing data variables
	private static ArrayList<WordButton> wordButtonArray = new ArrayList<WordButton>();
	
	// Constructor
	public WordList(ArrayList<Word> wordList) {
		
		// Setting Look and Feel
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
	
		// Initializing the Start Panel
		wordListPanel = new JPanel();
		wordListPanel.setBounds(0, 0, 1040, 710);
		wordListPanel.setBackground(new java.awt.Color(251, 216, 216));
		wordListPanel.setOpaque(true);
		wordListPanel.setBorder(BorderFactory.createLineBorder(new java.awt.Color(245, 94, 97), 50));
		wordListPanel.setLayout(null);

		// Initializing Title Text
		titleText = new JLabel("Word List", SwingConstants.CENTER);
		titleText.setBounds(270, 80, 500, 78);
		titleText.setFont(new Font("Century Gothic", Font.BOLD, 36));
		titleText.setForeground(Color.WHITE);
		wordListPanel.add(titleText);

		// Initializing foreground of title label
		titleForeground = new JPanel();
		titleForeground.setBounds(282, 92, 476, 54);
		titleForeground.setBackground(new java.awt.Color(245, 94, 97));
		titleForeground.setBorder(BorderFactory.createLineBorder(new java.awt.Color(251, 216, 216), 3));
		titleForeground.setVisible(true);
		wordListPanel.add(titleForeground);

		// Initializing background of title label
		titleBackground = new JPanel();
		titleBackground.setBounds(270, 80, 500, 78);
		titleBackground.setBackground(new java.awt.Color(245, 94, 97));
		titleBackground.setVisible(true);
		wordListPanel.add(titleBackground);
		
		// Initializing button that allows user to add a new word
		newWordButton.setBounds(399, 170, 216, 54);
		newWordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenu.frame.getContentPane().removeAll();
				new AddWord();
				MainMenu.frame.repaint();
				MainMenu.frame.revalidate();
			}
		});
		wordListPanel.add(newWordButton);
		
		// Initializing the Return Button
		returnButton.setBounds(781, 546, 150, 75);
		returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenu.frame.getContentPane().removeAll();
				new ModeSelection();
				MainMenu.frame.repaint();
				MainMenu.frame.revalidate();
			}
		});
		wordListPanel.add(returnButton);
		
		// Initializing the scrollPanel
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(150, 235, 737, 289);
		scrollPane.setBackground(new java.awt.Color(251, 216, 216));
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		// Initializing panel that will store all the buttons, and adding it to the scroll pane
		scrollPane.setViewportView(listPanel);
		listPanel.setBackground(new java.awt.Color(251, 216, 216));
		listPanel.setLayout(new GridLayout(0, 3, 36, 63));
		listPanel.setVisible(true);       
		wordListPanel.add(scrollPane);
		
		// Creating buttons for every word and adding it to the panel
		for (int i = 0; i < launcher.Main.wordList.size(); i++) {
			wordButtonArray.add(new WordButton(wordList.get(i)));
			listPanel.add(wordButtonArray.get(i));
		}

		// Finalizing and adding panel to the frame
		MainMenu.frame.setTitle("Word List");
		MainMenu.frame.getContentPane().add(wordListPanel);

	}
}
