package guiClasses;

// Importing generic libraries
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

// Importing GUI libraries
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;

// Importing other packages
import launcher.Main;
import objectClasses.Word;

/*
 * This screen is the main function of the program. It is a quiz that the user can take to help strengthen their Chinese vocabulary.
 * The user can receive one of three question types, being Chinese to English, Chinese to Pinyin, or English to Chinese or Pinyin.
 * Answers that are in Pinyin can be accepted both with the accent and without the accent. User stats are also tracked in this class.
 * 
 * POSSIBLE ADDITIONS:
 * - Bonus hints for the user if they mess up the same question three times
 * 		- Also the ability to skip a question, but as of now the user can just return to the Mode Selection screen and enter again for a new word.
 * 
 * AREAS OF CONCERN (?)
 * - Synonyms or slightly different takes on the same word do not register (Ex. "make phone call" vs "to make a phone call")
 * 		- I don't know if I'd count this as an error, more so as an oversight or missed feature
 * 			- I don't know how I'd even implement this, it seems too difficult and beyond the scope of this project and my current programming level
 */
public class Quiz {

	// Initializing GUI objects
	private static JPanel quizPanel;
	private static JPanel titleForeground;
	private static JPanel titleBackground;
	private static JLabel titleText;
	private static JPanel wordBackground;
	private static JPanel wordForeground;
	private static JLabel wordText;
	private static JLabel questionText;
	private static JTextField answerField;
	private static JButton submitButton = new JButton(new ImageIcon("images/submitbutton.png"));
	private static JButton returnButton = new JButton(new ImageIcon("images/returnbutton.png"));
	private static JPanel textPanel;
	
	// Initializing other objects
	private static Word currentWord;
	private static Random rand = new Random();
	
	// Initializing data variables
	private static int questionType; // 0 = Chinese to English, 1 = Chinese to Pinyin, 2 = English to Chinese
	private static String userAnswer;
	
	// Constructor
	public Quiz() {
		
		// Setting the Look and Feel
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
	
		// Initializing the Start Panel
		quizPanel = new JPanel();
		quizPanel.setBounds(0, 0, 1040, 710);
		quizPanel.setBackground(new java.awt.Color(251, 216, 216));
		quizPanel.setOpaque(true);
		quizPanel.setBorder(BorderFactory.createLineBorder(new java.awt.Color(245, 94, 97), 50));
		quizPanel.setLayout(null);
		
		// Initializing Title Text
		titleText = new JLabel("Quiz", SwingConstants.CENTER);
		titleText.setBounds(270, 80, 500, 78);
		titleText.setFont(new Font("Century Gothic", Font.BOLD, 36));
		titleText.setForeground(Color.WHITE);
		quizPanel.add(titleText);

		// Initializing foreground of title label
		titleForeground = new JPanel();
		titleForeground.setBounds(282, 92, 476, 54);
		titleForeground.setBackground(new java.awt.Color(245, 94, 97));
		titleForeground.setBorder(BorderFactory.createLineBorder(new java.awt.Color(251, 216, 216), 3));
		titleForeground.setVisible(true);
		quizPanel.add(titleForeground);

		// Initializing background of title label
		titleBackground = new JPanel();
		titleBackground.setBounds(270, 80, 500, 78);
		titleBackground.setBackground(new java.awt.Color(245, 94, 97));
		titleBackground.setVisible(true);
		quizPanel.add(titleBackground);
		
		// Initializing the entry field for the answer
		answerField = new JTextField();
		answerField.setFont(new Font("SimSun", Font.PLAIN, 24));
		answerField.setForeground(Color.BLACK);
		answerField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		answerField.setBounds(360, 475, 300, 50);
		answerField.setVisible(true);
		quizPanel.add(answerField);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkAnswer();
			}
		});
		
		// Initializing the Submit Button
		submitButton.setBounds(437, 546, 150, 75);
		quizPanel.add(submitButton);

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
		quizPanel.add(returnButton);
	
		// Initializing the panel where the character or word will appear
		textPanel = new JPanel();
		textPanel.setBounds(50, 235, 940, 220);
		textPanel.setBackground(new java.awt.Color(251, 216, 216));
		textPanel.setLayout(new MigLayout("", "[536px]", "[64px]"));
		
		// Picking question type and picking a word
		chooseQuestion();
		chooseWord();
		
		// Refreshing the page
		MainMenu.frame.repaint();
		MainMenu.frame.revalidate();
		
		// Finalizing and adding the frame to the panel
		MainMenu.frame.setTitle("Quiz");
		MainMenu.frame.getContentPane().add(quizPanel);
		
	}
	
	// This method will choose the word that the user will be quizzed on
	private static void chooseWord() {

		// Getting a random number
		int nextWordIndex = rand.nextInt(Main.wordList.size());
		currentWord = Main.wordList.get(nextWordIndex);
		
		// Comment this out if you don't want the answers to test the program (Just used to make testing easier for you)
		System.out.println(currentWord);
		
		// Creating the background label for the character box
		wordBackground = new JPanel();
		wordBackground.setBackground(new java.awt.Color(245, 94, 97));
		wordBackground.setVisible(true);
		
		// Creating the foreground label for the character box
		wordForeground = new JPanel();
		wordForeground.setBackground(new java.awt.Color(245, 94, 97));
		wordForeground.setBorder(BorderFactory.createLineBorder(new java.awt.Color(251, 216, 216), 3));
		wordForeground.setVisible(true);
		
		// Getting the appropriate text based on the type of question
		if (questionType == 2) { // English to Chinese/Pinyin
			
			// Set up the text label
			wordText = new JLabel(currentWord.getEnglish());
			wordText.setFont(new Font("Century Gothic", Font.BOLD, 50));
			wordText.setForeground(Color.WHITE);
			
			// Getting the width of the text label
			double width = wordText.getPreferredSize().getWidth();
			
			// Setting the width of the box
			if (width < 200) { // If the box is too narrow, it just looks very awkward so set minimum to 200x200
				wordBackground.setLayout(new MigLayout("", "[200px]", "[200px]"));
				wordForeground.setLayout(new MigLayout("", "[176px]", "[176px]"));
			} else { // Otherwise adjust it to fit the text
				wordBackground.setLayout(new MigLayout("", "[" + width + "px]", "[200px]"));
				wordForeground.setLayout(new MigLayout("", "[" + (width - 24) + "px]", "[176px]"));
			}
				
		} else { // Chinese to Pinyin/English
			
			// Setting up the text label
			wordText = new JLabel(currentWord.getChinese());
			wordText.setFont(new Font("SimSun", Font.BOLD, 150));
			wordText.setForeground(Color.WHITE);
			
			// Getting the width of the text label
			double width = wordText.getPreferredSize().getWidth();
				
			// Setting the width of the box
			if (width < 200) { // If the box is too narrow, it just looks very awkward so set minimum to 200x200
				wordBackground.setLayout(new MigLayout("", "[200px]", "[200px]"));
				wordForeground.setLayout(new MigLayout("", "[176px]", "[176px]"));
			} else { // Otherwise adjust it to fit the text
				wordBackground.setLayout(new MigLayout("", "[" + width + "px]", "[200px]"));
				wordForeground.setLayout(new MigLayout("", "[" + (width - 24) + "px]", "[176px]"));
			}
				
		}

		// Adding the chracter box to the screen
		textPanel.add(wordBackground, "push, align center");
		wordBackground.add(wordForeground, "push, align center");
		wordForeground.add(wordText, "push, align center");
		quizPanel.add(textPanel);

	}

	// This method will choose a question type to ask
	private static void chooseQuestion() {
		
		// Initializing Question Text
		questionText = new JLabel();
		questionText.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		questionText.setForeground(new java.awt.Color(245, 94, 97));
		questionText.setVisible(true);
		
		// Choosing a random question type
		questionType = rand.nextInt(3);
		
		// Setting the text to match the question type
		if (questionType == 0) { // Chinese to English
			questionText.setText("Please enter the English translation for the following Chinese word.");
			questionText.setBounds(125, 190, 850, 30);
		} else if (questionType == 1) { // Chinese to Pinyin
			questionText.setText("Please enter the Pinyin of the following Chinese word.");
			questionText.setBounds(195, 190, 680, 30);
		} else if (questionType == 2) { // English to Chinese/Pinyin
			questionText.setText("<html><body style='text-align: center'>Please enter the Chinese or Pinyin<br>translation of the following English Word:");
			questionText.setBounds(260, 171, 550, 60);
		}
		
		// Adding the text to the screen
		quizPanel.add(questionText);
	}
	
	// This method will check if the user got the right answer
	private static void checkAnswer() {
		
		// Getting the user's answer
		userAnswer = answerField.getText().trim().toLowerCase();
		
		// Checking if the user got the correct answer
		if (questionType == 0 && userAnswer.equals(currentWord.getEnglish().toLowerCase())) {
			message(true);
			correctAnswer();
		} else if (questionType == 1 && (userAnswer.equals(currentWord.getPinyin()) || userAnswer.equals(currentWord.getPinyinNoAccent()))) { // Pinyin with or without accent are both accepted (since it might be awkward trying to get the accents on the letters)
			message(true);
			correctAnswer();
		} else if (questionType == 2 && (userAnswer.equals(currentWord.getChinese()) || userAnswer.equals(currentWord.getPinyin()) || userAnswer.equals(currentWord.getPinyinNoAccent()))) { // Same as questionType 1 but add Chinese character acception as well
			message(true);
			correctAnswer();
		} else { // Wrong answer
			message(false);
			wrongAnswer();
		}
		
	}
	
	// Displaying the result of the user's answer
	private static void message(boolean correct) {
		
		// Initializing frames and panels that will appear on the screen
		JFrame messageFrame = new JFrame();
		JPanel messagePanel = new JPanel();
		JLabel messageLabel = new JLabel();
		messageFrame.setBounds(500,300,400,75);
		
		// Figures out what message that will be displayed.
		if (correct) {
			messageFrame.setTitle("Correct!");
			messageLabel.setText("You got the question correct!");
		} else {
			messageFrame.setTitle("Incorrect");
			messageLabel.setText("You got the answer wrong.");
		}
		
		// Making the label look better
		messageLabel.setFont(new Font("Century Gothic",Font.BOLD,20));
		messageLabel.setBounds(50, 100, 100, 100);
		messagePanel.add(messageLabel);
		
		// Adding the frame and bringing it to the front
		messageFrame.requestFocus();
		messageFrame.getContentPane().add(messagePanel);
		messageFrame.setVisible(true);
	}
	
	// Updating stats to reflect the correct answer
	private static void correctAnswer() {
		
		currentWord.setNumTimes(currentWord.getNumTimes() + 1);
		currentWord.setNumCorrect(currentWord.getNumCorrect() + 1);
		Main.currentUser.setNumCorrect(Main.currentUser.getNumCorrect() + 1);
		Main.currentUser.setNumQuestions(Main.currentUser.getNumQuestions() + 1);
		
		// Generating a new question and refreshing the screen
		refresh();
		
	}
	
	// Updating stats to reflect the wrong answer
	private static void wrongAnswer() {
		
		currentWord.setNumTimes(currentWord.getNumTimes() + 1);
		currentWord.setNumIncorrect(currentWord.getNumIncorrect() + 1);
		Main.currentUser.setNumIncorrect(Main.currentUser.getNumIncorrect() + 1);
		Main.currentUser.setNumQuestions(Main.currentUser.getNumQuestions() + 1);
		
	}
	
	// This method will just remove all components currently on the screen that will be different for then next question (Character box and question text)
	private static void refresh() {
		
		// Removing everything
		quizPanel.remove(questionText);
		quizPanel.remove(wordBackground);
		quizPanel.remove(wordForeground);
		quizPanel.remove(wordText);
		quizPanel.remove(textPanel);
		textPanel.removeAll();
		answerField.setText("");
		
		// Refreshing Screen
		quizPanel.repaint();
		quizPanel.revalidate();
		
		// Choosing new question and word
		chooseQuestion();
		chooseWord();
		
		// Refreshing again just for the sake of it
		quizPanel.repaint();
		quizPanel.revalidate();
	}
}

