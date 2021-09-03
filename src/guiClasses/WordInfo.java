package guiClasses;

// Importing generic libraries
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

// Importing the GUI objects
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;

// Importing Audio libraries
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

// Importing Google Cloud Platform Libraries
import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;

// Importing other package's class
import objectClasses.Word;

/*
 * This class will display information regarding one certain Chinese word.
 * It will display the Chinese character itself, it's Pinyin, and English Translation.
 * This screen will also display the stats related to the character, notably number of times seen,
 * number of times answered correct, and number of times answered incorrect.
 * This screen also features a text-to-speech function, allowing you to hear how to pronounce
 * the Chinese word. This uses the Google Cloud Text-to-Speech API.
 * 
 * This class only requires the Google Cloud Text-to-Speech API for the text-to-speech portion.
 * For the API to work, it is required to set GOOGLE_APPLICATION_CREDENTIALS to the path of the 
 * credentials.json file found in this project. Without this, the program will probably not function. :(
 * 
 * If you are unable to set the environment variable, to make this screen work just remove the two non-constructor
 * functions and the playAudioButton.
 */
public class WordInfo {
	
	// Initializing GUI Objects
	private JPanel wordInfoPanel;
	private JPanel titleForeground;
	private JPanel titleBackground;
	private JLabel titleText;
	private JPanel wordBackground;
	private JPanel wordForeground;
	private JLabel wordText;
	private JButton returnButton = new JButton(new ImageIcon("images/returnbutton.png"));
	private JPanel translationPanel;
	private JLabel pinyinLabel;
	private JLabel englishLabel;
	private JPanel wordStatsPanel;
	private JLabel numTimesLabel;
	private JLabel numCorrectLabel;
	private JLabel numIncorrectLabel;
	private JPanel textPanel;
	private JButton playAudioButton = new JButton(new ImageIcon("images/playbutton.png"));
	
	// Initializing other objects
	private static Word currentWord;
	
	// Initializing data variables
	private int numCharacters;
	
	// Constructor
	public WordInfo(Word currentWord) {
		
		// Saving the word to get the info of
		this.currentWord = currentWord;
		
		// Setting up Look and Feel
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
	
		// Initializing the Start Panel
		wordInfoPanel = new JPanel();
		wordInfoPanel.setBounds(0, 0, 1040, 710);
		wordInfoPanel.setBackground(new java.awt.Color(251, 216, 216));
		wordInfoPanel.setOpaque(true);
		wordInfoPanel.setBorder(BorderFactory.createLineBorder(new java.awt.Color(245, 94, 97), 50));
		wordInfoPanel.setLayout(null);
		
		// Initializing Title Text
		titleText = new JLabel("Word Info", SwingConstants.CENTER);
		titleText.setFont(new Font("Century Gothic", Font.BOLD, 36));
		titleText.setBounds(273, 42, 469, 162);
		titleText.setForeground(Color.WHITE);
		wordInfoPanel.add(titleText);
		
		// Initializing foreground of title label
		titleForeground = new JPanel();
		titleForeground.setBounds(263, 99, 479, 54);
		titleForeground.setBackground(new java.awt.Color(245, 94, 97));
		titleForeground.setBorder(BorderFactory.createLineBorder(new java.awt.Color(251, 216, 216), 3));
		titleForeground.setVisible(true);
		wordInfoPanel.add(titleForeground);
				
		// Initializing background of title label
		titleBackground = new JPanel();
		titleBackground.setBounds(252, 90, 500, 71);
		titleBackground.setBackground(new java.awt.Color(245, 94, 97));
		titleBackground.setVisible(true);
		wordInfoPanel.add(titleBackground);

		// Initializing the Return Button
		returnButton.setBounds(790, 535, 150, 75);
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainMenu.frame.getContentPane().removeAll();
				new WordList(launcher.Main.wordList);
				MainMenu.frame.repaint();
				MainMenu.frame.revalidate();
			}
		});
		wordInfoPanel.add(returnButton);
		
		// Initializing Panel where the Chinese character will be displayed
		textPanel = new JPanel();
		textPanel.setBounds(50, 180, 940, 210);
		textPanel.setBackground(new java.awt.Color(251, 216, 216));
		textPanel.setLayout(new MigLayout("", "[938.00px]", "[220.00px]"));
		wordInfoPanel.add(textPanel);
		
		// Initializing the background box for the character
		wordBackground = new JPanel();
		wordBackground.setBackground(new java.awt.Color(245, 94, 97));
		wordBackground.setVisible(true);
		
		// Initializing the foreground box for the character
		wordForeground = new JPanel();
		wordForeground.setBackground(new java.awt.Color(245, 94, 97));
		wordForeground.setBorder(BorderFactory.createLineBorder(new java.awt.Color(251, 216, 216), 3));
		wordForeground.setVisible(true);
		
		// Initializes the label that shows the Chinese character
		wordText = new JLabel(currentWord.getChinese());
		wordText.setFont(new Font("SimSun", Font.BOLD, 150));
		wordText.setForeground(new java.awt.Color(251, 216, 216));
		
		// Adding the character box to the screen
		textPanel.add(wordBackground, "push, aligncenter");
		wordBackground.add(wordForeground, "push, aligncenter");
		wordForeground.add(wordText, "push, aligncenter");
		
		// Initializing the panel that shows the Pinyin and English translations
		translationPanel = new JPanel();
		translationPanel.setBounds(50, 395, 940, 110);
		translationPanel.setBackground(new java.awt.Color(251, 216, 216));
		wordInfoPanel.add(translationPanel);
		translationPanel.setLayout(new MigLayout("", "[940.00]", "[45.00][55.00]"));
		
		// Initializing the label that shows the pinyin of the Chinese character
		pinyinLabel = new JLabel(currentWord.getPinyin());
		pinyinLabel.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 32));
		pinyinLabel.setForeground(new java.awt.Color(245, 94, 97));
		translationPanel.add(pinyinLabel, "cell 0 0, align center");
		
		// Initializing the label that shows the English translation of the Chinese character
		englishLabel = new JLabel(currentWord.getEnglish());
		englishLabel.setFont(new Font("Century Gothic", Font.BOLD, 32));
		englishLabel.setForeground(new java.awt.Color(245, 94, 97));
		translationPanel.add(englishLabel, "cell 0 1, align center");
		
		// Initializing the panel that will show off the word's stats
		wordStatsPanel = new JPanel();
		wordStatsPanel.setBounds(400, 510, 250, 100);
		wordStatsPanel.setBackground(new java.awt.Color(245, 94, 97));
		wordInfoPanel.add(wordStatsPanel);
		wordStatsPanel.setLayout(new MigLayout("", "[536.00]", "[33.33][33.33][33.34]"));
		
		// Initializing the label that shows the amount of times the word has been seen
		numTimesLabel = new JLabel(currentWord.getNumTimes() + " times seen");
		numTimesLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));;
		numTimesLabel.setForeground(new java.awt.Color(251, 216, 216));
		wordStatsPanel.add(numTimesLabel, "cell 0 0, align center");
		
		// Initializing the label that shows the amount of times the user has answered a question with this word correctly
		numCorrectLabel = new JLabel(currentWord.getNumCorrect() + " times correct");
		numCorrectLabel.setForeground(new Color(251, 216, 216));
		numCorrectLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		wordStatsPanel.add(numCorrectLabel, "cell 0 1, align center");
		
		// Initializing the label that shows the amount of times the user has answered a question with this word incorreectly.
		numIncorrectLabel = new JLabel(currentWord.getNumCorrect() + " times incorrect");
		numIncorrectLabel.setForeground(new Color(251, 216, 216));
		numIncorrectLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		wordStatsPanel.add(numIncorrectLabel, "cell 0 2, align center");
		
		// Initializing the Play Audio Button
		playAudioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					createAudio();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		playAudioButton.setBounds(185, 535, 75, 75);
		wordInfoPanel.add(playAudioButton);
		
		// Finalizing and adding the panel into the frame
		MainMenu.frame.setTitle("Word Info");
		MainMenu.frame.getContentPane().add(wordInfoPanel);
		
	}
	
	// Taken from https://cloud.google.com/text-to-speech/docs/basics, Edited to fit this program by me.
	// This method creates a wav file
	public static void createAudio() throws IOException {
		
		// Instantiates a client
	    try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
	      
		    // Set the text input to be synthesized
		    SynthesisInput input = SynthesisInput.newBuilder().setText(currentWord.getChinese()).build();
	
		    VoiceSelectionParams voice = VoiceSelectionParams.newBuilder().setLanguageCode("zh-CN").setSsmlGender(SsmlVoiceGender.NEUTRAL).build();
	
		    // Select the type of audio file you want returned
		    AudioConfig audioConfig = AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.LINEAR16).build();
	
		    // Perform the text-to-speech request on the text input with the selected voice parameters and audio file type
		    SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
	
		    // Get the audio contents from the response
		    ByteString audioContents = response.getAudioContent();
	
		    // Write the response to the output file.
		    try (OutputStream out = new FileOutputStream("output.wav")) {
		      out.write(audioContents.toByteArray());
		    } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      
	    }
	    
	    playAudio();
	}
	
	// This method will play a wav file.
	public static void playAudio() {
		 try {
			 	// Initializing objects needed to play the audio file
			    File audioFile = new File("output.wav"); // The audio file itself
			    AudioInputStream audioStream; // Will obtain an Audio Input Stream from the Audio File
			    AudioFormat format; // Helps with the formatting of the Audio file
			    DataLine.Info info; // Helps with media-playing
			    Clip clip; // Helps load the audio file

			    // Sets up the audio file to be played
			    audioStream = AudioSystem.getAudioInputStream(audioFile);
			    format = audioStream.getFormat();
			    info = new DataLine.Info(Clip.class, format);
			    
			    // Plays the audio file
			    clip = (Clip) AudioSystem.getLine(info);
			    clip.open(audioStream);
			    clip.start();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
}

