package launcher;

// IMPORTANT IMPORTANT IMPORTANT MESSAGE
// Please make sure that all the necessary libraries are installed
// 1. pinyin4j, 2. miglayout, 3. Google Cloud Platform Libraries
// 1 and 2 are both jar files found in the project
// However, 3 is installed through the Eclipse marketplace
// Just search up for Google Cloud Tools for Eclipse on the Eclipse marketplace and install it
// Then, add the Google Cloud Storage, Google Cloud Translation, and Text-to-Speech libraries to this project
// If these instructions were unclear, I have emailed you with more information.
// ALSO:
// It is necessary for an environment variable to be set for the Google Cloud API to function.
// The variable name is: GOOGLE_APPLICATION_CREDENTIALS, and the variable value is the directory for the
// credentials.json file I have attached to the project as well.
// For more information, see this link: https://cloud.google.com/storage/docs/reference/libraries#installing_the_client_library
// If any of the Google Cloud libraries or environment variables do not work,
// Workarounds to get rid of that functionality can be found in the AddWord and WordInfo classes.
// Thank you so much for all that work. Now, onto the program!

/*
 * Name: Eric Lin 1
 * Date: 2021-01-23
 * Course Code: ICS4U1-01
 * Instructor's Name: Mr. Fernandes
 * Title: Chinese Vocabulary App
 * 
 * Description: An incredibly helpful app for anyone trying to increase their Chinese vocabulary.
 * By consistently quizzing yourself with this program, it will become much easier to memorize
 * Chinese words, their pinyin pronounciation, and their English translation. Also found in this
 * program is a stat-tracking tool, allowing you to analyze your strengths and weaknesses. Also featured
 * is integration with Google Translate and Text-to-Speech. Add any Chinese or English word you want,
 * and it will be automatically translated and added to the vocabulary list you are quizzed on. Then,
 * continue to learn Chinese words by using Text-to-Speech to learn proper pronounciation of Chinese words!
 * 
 * Notable Features:
 * 	- UTF-8 text file reading and writing
 *  - Word List screen that allows you to browse through 100s of Chinese words
 * 	- Text-to-Speech powered by Google featured in the Word Info screen
 * 	- Automatic translation powered by Google Translate in the Add Word screen
 * 	- Automatic User Stat Tracking
 * 	- Automatic saving of stats
 * 	- Chinese Character to Pinyin functionality (surprisingly difficult!)
 * 
 * Major Skills:
 * 	- Object Oriented Programming
 * 	- Importing of Libraries
 * 	- Java swing GUI
 * 	- File reading and writing (UTF-8 too!!)
 *  - Google Cloud Platform Library
 *  	- API Keys, Credentials, and Service Accounts
 *  	- Environment Variables
 *  - Writing and Playing of Audio Files
 *  
 * Areas of Concern:
 * - Certain words don't work such as "cheque," probably due to the fact it is a British spelling of the word
 * 		- Since this is all using the Google Translate API, there is nothing I can really do about this. (Unless British English is a completely different English setting that I can select)
 * - When pressing one of the word buttons, the button covers the three labels that show Chinese character, pinyin, and English translation
 * 		- This wouldn't be a problem since you go to another screen right away, but if you press return it will stay covered
 * 			- This can probably be fixed by using a different layout, but I didn't have the time to learn another layout
 * - After adding a new word, it is not updated properly on the word list and will instead show the first word, "zero," in it's place.
 * 		- I honestly have no idea why this happens. Like when I was trying to fix it and printed out the word stored by that button it displayed
 * 		  the right word. However, the GUI says differently.
 * 			- I have no idea how to fix this, but it does fix itself if you restart the program. So it's not the biggest problem in the world.
 * - Synonyms or slightly different takes on the same word do not register (Ex. "make phone call" vs "to make a phone call")
 * 		- I don't know if I'd count this as an error, more so as an oversight or missed feature
 * 			- I don't know how I'd even implement this, it seems too difficult and beyond the scope of this project and my current programming level
 * - Some words get cut off if they're too long in the WordList buttons
 * 		- This is probably just a design feature, the buttons aren't designed in a way for this to be fixed elegantly
 * - The Google Cloud API features are kind of slow
 * 		- After pressing "Check" in the AddWord screen, it takes a second or two for the translated word to appear on screen
 * 		- After pressing on the play button, it takes many many seconds for the audio to play
 * 			- I don't really know why it takes so long or how to make it faster 
 * - The message for "This word is already in the list" appears like ten times for some reason. (AddWord screen)
 * 		- I really don't know why this happens. Like I have no idea.
 */

// Importing libraries
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Importing other packages' classes
import guiClasses.MainMenu;
import objectClasses.User;
import objectClasses.Word;

/*
 * Launcher
 */
public class Main {
	
	// Initializing objects and data variables
	public static ArrayList<Word> wordList = new ArrayList<Word>();
	public static User currentUser;

	// Main method
	public static void main(String[] args) {
		
		// Import User Info
		try {
			Scanner userFileInput = new Scanner(new File("userInfo.txt"));
			userFileInput.useDelimiter(",");

			currentUser = new User(
					userFileInput.nextInt(), // numQuestions
					userFileInput.nextDouble(), // successRate
					userFileInput.nextInt(), // numCorrect
					userFileInput.nextInt(), // numIncorrect
					0
					);

			userFileInput.close();
		} catch (FileNotFoundException error) {
			System.out.println("File error: userInfo.txt");
		}
		
		// Import word info
		updateWordList();

		// Create first screen
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainMenu.initializeFrame();
			}
		});

	}
	
	public static void updateWordList() {
		
		// Clears previous word list so it's as updated as possible
		wordList.clear();
		
		try {
			
			// Create Scanner using FileInputStream so UTF-8 is accepted
			Scanner vocabularyListInput = new Scanner(new FileInputStream("vocabularylist.txt"));
			vocabularyListInput.useDelimiter(",");

			// Skip the beginning line
			vocabularyListInput.nextLine();
			
			// Keeps track of position of word
			int index = 1;

			// Adding every word to the array
			while (vocabularyListInput.hasNext()) {

				// Creating a new Word object
				wordList.add( new Word(
						vocabularyListInput.next(), // Chinese
						vocabularyListInput.next(), // Pinyin
						vocabularyListInput.next(), // Pinyin (No Accents)
						vocabularyListInput.next(), // English
						vocabularyListInput.nextInt(), // Times Seen
						vocabularyListInput.nextInt(), // Times Correct
						vocabularyListInput.nextInt(), // Times Incorrect
						index
						) );
				
				index++;
				vocabularyListInput.nextLine();
				
			}
			vocabularyListInput.close();
		} catch (FileNotFoundException error) {
			System.out.println("File error: vocabularylist.txt");
		}
	}
	
}
