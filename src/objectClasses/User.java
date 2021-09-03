package objectClasses;

// Importing libraries
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Importing other package classes
import launcher.Main;

/*
 * This class will function as an object to store information about the user.
 */
public class User {
	
	// Initializing data variables
	private int numQuestions;
	private double successRate;
	private int numCorrect;
	private int numIncorrect;
	private String stats;
	private int userNum;
	ArrayList<Word> worstWords = new ArrayList<Word>();
	
	// Constructor
	public User(int numQuestions, double successRate, int numCorrect, int numIncorrect, int userNum) {
		this.numQuestions = numQuestions;
		this.successRate = successRate;
		this.numCorrect = numCorrect;
		this.numIncorrect = numIncorrect;
		this.userNum = userNum;
	}

	// Getters and Setters
	// Each setter will update the text file as well with the new values
	public int getNumQuestions() {
		return numQuestions;
	}

	public void setNumQuestions(int numQuestions) {
		this.numQuestions = numQuestions;
		setSuccessRate();
		stats = numQuestions + "," + successRate + "," + numCorrect + "," + numIncorrect + ",";
		try {
			updateFile(userNum, stats);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public double getSuccessRate() {
		return successRate;
	}

	public void setSuccessRate() { // Just calculates the success rate so that it's to one decimal
		successRate = (double)numCorrect / numQuestions;
		successRate = successRate * 1000;
		successRate = Math.round(successRate);
		successRate = successRate / 10;
		stats = numQuestions + "," + successRate + "," + numCorrect + "," + numIncorrect + ",";
		
		try {
			updateFile(userNum, stats);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getNumCorrect() {
		return numCorrect;
	}

	public void setNumCorrect(int numCorrect) {
		this.numCorrect = numCorrect;
		stats = numQuestions + "," + successRate + "," + numCorrect + "," + numIncorrect + ",";
		try {
			updateFile(userNum, stats);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getNumIncorrect() {
		return numIncorrect;
	}

	public void setNumIncorrect(int numIncorrect) {
		this.numIncorrect = numIncorrect;
		stats = numQuestions + "," + successRate + "," + numCorrect + "," + numIncorrect + ",";
		try {
			updateFile(userNum, stats);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void findWorst() {
		worstWords = Main.wordList;
		Collections.sort(worstWords);
	}
	
	public ArrayList<Word> getWorst() {
		return worstWords;
	}
	

	// Updating userinfo.txt
	public void updateFile(int lineNumber, String data) throws IOException {
		
		// Setting the path of the userinfo.txt file
	    Path path = Paths.get("userInfo.txt");
	    
	    // Saving the text file as a list
	    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
	    
	    // Updating the appropriate line number with the new information
	    lines.set(lineNumber, data);
	    Files.write(path, lines, StandardCharsets.UTF_8);
	}
	
	@Override
	// toString method
	public String toString() {
		return "User [numQuestions=" + numQuestions + ", successRate=" + successRate + ", numCorrect=" + numCorrect
				+ ", numIncorrect=" + numIncorrect + ", worstWords=" + worstWords + "]";
	}
	
	

}
