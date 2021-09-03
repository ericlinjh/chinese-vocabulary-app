package guiClasses;

// Importing generic libraries
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

// Importing swing libraries
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

// Importing Google Cloud Libraries (Please install the Cloud Translation library for this class)
// See this -> https://cloud.google.com/storage/docs/reference/libraries#installing_the_client_library
import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

// Importing external libraries
import net.miginfocom.swing.MigLayout;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;
import objectClasses.Word;

/*
 * This class creates the screen that allows the user to add a word to the vocabulary list.
 * It uses the Google Translate API to detect the language that the user is trying to add a word in,
 * translating that word to the opposite language, and then adding it to the vocabularylist.txt file
 * for further use with the Quiz portion of the app.
 * 
 * POTENTIAL ERRORS:
 * - Certain words don't work such as "cheque," probably due to the fact it is a British spelling of the word
 * 		- Since this is all using the Google Translate API, there is nothing I can really do about this. (Unless British English is a completely different English setting that I can select)
 * 
 * If it isn't possible to install the Google Cloud Platform Libraries, then this whole entire screen will not work.
 * If this is the case, instead of just ignoring this I'd like to just record a video of how the screen functions.
 * 
 * If it isn't possible to set the environment variables, then this class WILL work. Just comment out the current
 * Translate initialization line and uncomment the one that's already commented.
 * 
 * AREAS OF CONCERN:
 * - The message for "This word is already in the list" appears like ten times for some reason.
 * 		- I really don't know why this happens. Like I have no idea.
 */
public class AddWord {
	
	// Initializing GUI Variables
	private static JPanel addWordPanel;
	private static JPanel titleForeground;
	private static JPanel titleBackground;
	private static JLabel titleText;
	private static JPanel wordBackground;
	private static JPanel wordForeground;
	private static JLabel wordText;
	private static JTextField newWordField;
	private static JButton submitButton = new JButton(new ImageIcon("images/submitbutton.png"));
	private static JButton returnButton = new JButton(new ImageIcon("images/returnbutton.png"));
	private static JButton checkButton = new JButton(new ImageIcon("images/wordlist/checkbutton.png"));
	private static JPanel textPanel;
	
	// Initializing Google Translate API Object
	private static Translate translate = TranslateOptions.getDefaultInstance().getService(); // Use this if you can set Environmental Variables
	
	/* 
	 * Uncomment this line if you cannot set Environmental Variables, and comment out the line above.
	 * private static Translate translate = TranslateOptions.newBuilder().setApiKey("").build().getService(); --> There would normally be an API key here.
	 */
	
	// Initializing data variables
	private static boolean legitWord = false;
	private static String toBeTranslated;
	private static String pinyin;
	private static String chinese;
	private static String english;
	
	// Constructor
	public AddWord() {
		
		// Setting the Look and Feel
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
	
		// Initializing the Start Panel
		addWordPanel = new JPanel();
		addWordPanel.setBounds(0, 0, 1040, 710);
		addWordPanel.setBackground(new java.awt.Color(251, 216, 216));
		addWordPanel.setOpaque(true);
		addWordPanel.setBorder(BorderFactory.createLineBorder(new java.awt.Color(245, 94, 97), 50));
		addWordPanel.setLayout(null);
		
		// Initializing Title Text
		titleText = new JLabel("Add Word", SwingConstants.CENTER);
		titleText.setBounds(270, 80, 500, 78);
		titleText.setFont(new Font("Century Gothic", Font.BOLD, 36));
		titleText.setForeground(Color.WHITE);
		addWordPanel.add(titleText);
		
		// Initializing foreground of title label
		titleForeground = new JPanel();
		titleForeground.setBounds(282, 92, 476, 54);
		titleForeground.setBackground(new java.awt.Color(245, 94, 97));
		titleForeground.setBorder(BorderFactory.createLineBorder(new java.awt.Color(251, 216, 216), 3));
		titleForeground.setVisible(true);
		addWordPanel.add(titleForeground);
				
		// Initializing background of title label
		titleBackground = new JPanel();
		titleBackground.setBounds(270, 80, 500, 78);
		titleBackground.setBackground(new java.awt.Color(245, 94, 97));
		titleBackground.setVisible(true);
		addWordPanel.add(titleBackground);
		
		// Initializing the entry field for the answer
		newWordField = new JTextField();
		newWordField.setFont(new Font("SimSun", Font.PLAIN, 24));
		newWordField.setForeground(Color.BLACK);
		newWordField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		newWordField.setBounds(250, 460, 300, 50);
		newWordField.setVisible(true);
		addWordPanel.add(newWordField);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				submitWord();
			}
		});
		
		// Initializing the Submit Button
		submitButton.setBounds(437, 546, 150, 75);

		// Initializing the Return Button
		returnButton.setBounds(781, 546, 150, 75);
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainMenu.frame.getContentPane().removeAll();
				new WordList(launcher.Main.wordList);
				MainMenu.frame.repaint();
				MainMenu.frame.revalidate();
			}
		});
		addWordPanel.add(returnButton);
		
		// Initializing Panel where the Chinese character will be displayed
		textPanel = new JPanel();
		textPanel.setBounds(50, 180, 940, 280);
		textPanel.setBackground(new java.awt.Color(251, 216, 216));
		textPanel.setLayout(new MigLayout("", "[536px]", "[220.00px][60.00px]"));
		
		// Setting up the temporary box that will hold the Chinese Character
		wordBackground = new JPanel();
		wordBackground.setBackground(new java.awt.Color(245, 94, 97));
		wordBackground.setVisible(true);
		wordBackground.setLayout(new MigLayout("", "[200px]", "[200px]"));
		
		wordForeground = new JPanel();
		wordForeground.setBackground(new java.awt.Color(245, 94, 97));
		wordForeground.setBorder(BorderFactory.createLineBorder(new java.awt.Color(251, 216, 216), 3));
		wordForeground.setVisible(true);
		wordForeground.setLayout(new MigLayout("", "[176px]", "[176px]"));
		
		// Adding the temporary box to the screen
		textPanel.add(wordBackground, "cell 0 0,push ,alignx center");
		wordBackground.add(wordForeground, "push, align center");
		addWordPanel.add(textPanel);

		// Setting up the "Check" button
		checkButton.setBounds(587, 460, 150, 50);
		checkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkWord();
			}
		});
		addWordPanel.add(checkButton);
		
		// Refreshing the page to make sure everything is updated
		MainMenu.frame.repaint();
		MainMenu.frame.revalidate();
		
		// Finalizing window and adding panel to frame
		MainMenu.frame.setTitle("Add Word");
		MainMenu.frame.getContentPane().add(addWordPanel);
	}
	
	// Will check if the word the user wants is viable, and then translates it
	@SuppressWarnings("deprecation")
	private static void checkWord() {
		
		// Clearing the temporary box for the character
		textPanel.removeAll();
		
		// Reinitializing the character box
		wordBackground = new JPanel();
		wordBackground.setBackground(new java.awt.Color(245, 94, 97));
		wordBackground.setVisible(true);
		
		wordForeground = new JPanel();
		wordForeground.setBackground(new java.awt.Color(245, 94, 97));
		wordForeground.setBorder(BorderFactory.createLineBorder(new java.awt.Color(251, 216, 216), 3));
		wordForeground.setVisible(true);

		// Getting the word that the user wants to add, and setting up variables for translation
		toBeTranslated = newWordField.getText().trim().toLowerCase();
		Detection languageDetect = translate.detect(toBeTranslated); // Checks to see if the word the user typed is either Chinese or English
		String language = languageDetect.getLanguage(); // Getting the actual language that Google thinks it is
		
		// Translating the word to the appropriate language
		if(language.equals("en")) { // If language is detected to be English
			
			// Translating the user's word
			Translation translation = translate.translate(toBeTranslated, // Initializes the object
					Translate.TranslateOption.sourceLanguage("en"), // Translates from English
				    Translate.TranslateOption.targetLanguage("zh-CN"), // Translates to Chinese (Simplified)
				    Translate.TranslateOption.model("base")); // Base version of the translation API
			
			// Saving the words for later use
			chinese = translation.getTranslatedText();
			english = toBeTranslated;
			
			// Checking to see if the screen can fit the words the user wants translated
			if (chinese.length() > 5) {
				message("Please enter a shorter word!", "ERROR");
				return;
			} else { // Word fits, create new label to display the word for the user
				wordText = new JLabel(chinese);
				wordText.setFont(new Font("SimSun", Font.BOLD, 150));
				legitWord = true;
			}

		} else if (language.equals("zh-CN") || language.equals("zh-TW")) { // If language detected is Chinese (CN = Simplified, TW = Traditional)
			
			// Checking if the Chinese word is too long to fit on the screen (for the quiz)
			if (toBeTranslated.length() > 5) {
				message("Please enter a shorter word!", "ERROR");
				return;
			}
			
			// Translating the word from Chinese to English
			Translation translation = translate.translate(toBeTranslated, // Word to be translated
					Translate.TranslateOption.sourceLanguage("zh-CN"), // Translate from Chinese (works with Traditional or Simplified)
				    Translate.TranslateOption.targetLanguage("en"), // Translate to English
				    Translate.TranslateOption.model("base")); // Base version of the translation API

			// Saving the words for later use
			english = translation.getTranslatedText();
			chinese = toBeTranslated;
			
			// Creating new label to display the word for the user
			wordText = new JLabel("<HTML>" + english + "</HTML>"); // Using HTML since apostrophes are saved as HTML codes
			wordText.setFont(new Font("Century Gothic", Font.BOLD, 50));
			legitWord = true;
		} else { // If language was detected to not be English or Chinese
			message("Language was not detected to be English or Chinese!", "ERROR");
			legitWord = false;
			return;
		}
		
		// If the phrase the user translates has a comma, it messes up the reading of the txt file
		if (chinese.contains(",") || english.contains(",")) {
			message("Please enter a word that does not contain a comma.", "ERROR");
			legitWord = false;
			return;
		}

		// Setting font colour to white 
		wordText.setForeground(Color.WHITE);
		
		// Checking how long the box has to be
		double width = wordText.getPreferredSize().getWidth();
			
		// Changing the width of the box
		if (width < 200) { // If the word is too short the box looks kinda weird, so keep it at 200x200 minimum
			wordBackground.setLayout(new MigLayout("", "[200px]", "[200px]"));
			wordForeground.setLayout(new MigLayout("", "[176px]", "[176px]"));
		} else { // Otherwise adjust as necessary
			wordBackground.setLayout(new MigLayout("", "[" + width + "px]", "[200px]"));
			wordForeground.setLayout(new MigLayout("", "[" + (width - 24) + "px]", "[176px]"));
		}
			
		// Add the character box and character to the screen
		textPanel.add(wordBackground, "push, align center");
		wordBackground.add(wordForeground, "push, align center");
		wordForeground.add(wordText, "push, align center");
		addWordPanel.add(textPanel);
		
		// Set up format for the Chinese pinyin
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE); // Keeps the tone of the character instead of using numbers
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE); // Lowercase output
		format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK); // Keeps the tone of the character as well

		// Translating the Chinese character into pinyin
		try {
			
			for (int i = 0; i < chinese.length(); i++) {
				pinyin = PinyinHelper.toHanyuPinyinString(chinese, format, "");
			}
			
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		
		// Creating a label to display the pinyin
		JLabel pinyinLabel = new JLabel(pinyin);
		pinyinLabel.setForeground(new java.awt.Color(245, 94, 97));
		pinyinLabel.setFont(new Font("Century Gothic", Font.BOLD, 30));
		textPanel.add(pinyinLabel, "cell 0 1, push, align center");

		// Adding Submit button so user can finalize adding the word to the program
		addWordPanel.add(submitButton);
		
		// Refreshing the screen to show the new character box
		MainMenu.frame.repaint();
		MainMenu.frame.revalidate();
	}
	
	// Will add the word to the vocabularylist.txt file
	private static void submitWord() {
		
		// Checks to make sure that the word the user wants is valid
		if (!legitWord) {
			message("Please enter a valid word!", "ERROR");
			return;
		}
		
		// Checks to see if the word has already been added
		for (Word checkWord : launcher.Main.wordList) {
			if (chinese.equals(checkWord.getChinese()) || english.equals(checkWord.getEnglish())) {
				message("This word has already been added!", "ERROR");
				return;
			}
		}
		
		// Converts the pinyin to a version without accents
		String pinyinNoAccents = StringUtils.stripAccents(pinyin);
		
		// Generates the string to be put into the vocabularylist.txt file
		String stringToAdd = chinese + "," + pinyin + "," + pinyinNoAccents + "," + english + ",0,0,0,\n";
		
		// Adds the string to the text file
		try {
			Writer textWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
					"vocabularylist.txt", true), "UTF-8"));
			try {
				textWriter.write(stringToAdd);
				textWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Tells the user that the word was added successfully
		message("Word Successfully Added!", "SUCCESS");
		
		// Updates word list with new words
		launcher.Main.updateWordList();
		
		// Brings the user back to the WordList page
		MainMenu.frame.getContentPane().removeAll();
		new WordList(launcher.Main.wordList);
		MainMenu.frame.repaint();
		MainMenu.frame.revalidate();
	}
	
	// Creates a new frame with a message in it
	private static void message(String message, String title) {
		
		// Initializing frames and panels that will appear on the screen
		JFrame messageFrame = new JFrame();
		JPanel messagePanel = new JPanel();
		JLabel messageLabel = new JLabel(message);
		
		// Making the label look better
		messageLabel.setFont(new Font("Century Gothic",Font.BOLD,20));
		messageLabel.setLocation(0,0);
		messagePanel.add(messageLabel);

		// Setting up the frame
		messageFrame.setTitle(title);
		messageFrame.setBounds(500, 300, (int) messageLabel.getPreferredSize().getWidth() + 20, 75);
		
		// Adding the frame and bringing it to the front
		messageFrame.requestFocus();
		messageFrame.getContentPane().add(messagePanel);
		messageFrame.setVisible(true);
	}
}

