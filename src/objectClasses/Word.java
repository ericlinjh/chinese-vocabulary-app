package objectClasses;

// Importing libraries
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/*
 * This class stores information about one word
 */
public class Word implements java.lang.Comparable<Word> {
	
	// Initializing variables
	private String chinese;
	private String pinyin;
	private String english;
	private String pinyinNoAccent;
	private int numTimes;
	private int numCorrect;
	private int numIncorrect;
	private int index;
	private String newLine;
	private int successRate = 0;
	
	// Constructor
	public Word(String chinese, String pinyin, String pinyinNoAccent, String english, int numTimes, int numCorrect, int numIncorrect, int index) {
		this.chinese = chinese;
		this.pinyin = pinyin;
		this.pinyinNoAccent = pinyinNoAccent;
		this.english = english;
		this.numTimes = numTimes;
		this.numCorrect = numCorrect;
		this.numIncorrect = numIncorrect;
		this.index = index;
	}

	// Setters and Getters
	// Setting any of the three stats (numTimes, numCorrect, numIncorrect) will update the vocabularylist.txt accordingly
	public String getPinyinNoAccent() {
		return pinyinNoAccent;
	}

	public void setPinyinNoAccent(String pinyinNoAccent) {
		this.pinyinNoAccent = pinyinNoAccent;
	}

	public String getChinese() {
		return chinese;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public int getNumTimes() {
		return numTimes;
	}

	public void setNumTimes(int numTimes) {
		this.numTimes = numTimes;
		newLine = chinese + "," + pinyin + "," + pinyinNoAccent + "," + english + "," + numTimes + "," + numCorrect + "," + numIncorrect + ",";
		try {
			updateFile(index, newLine);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getNumCorrect() {
		return numCorrect;
	}

	public void setNumCorrect(int numCorrect) {
		this.numCorrect = numCorrect;
		newLine = chinese + "," + pinyin + "," + pinyinNoAccent + "," + english + "," + numTimes + "," + numCorrect + "," + numIncorrect + ",";
		try {
			updateFile(index, newLine);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getNumIncorrect() {
		return numIncorrect;
	}

	public void setNumIncorrect(int numIncorrect) {
		this.numIncorrect = numIncorrect;
		newLine = chinese + "," + pinyin + "," + pinyinNoAccent + "," + english + "," + numTimes + "," + numCorrect + "," + numIncorrect + ",";
		try {
			updateFile(index, newLine);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getSuccessRate() {
		
		if (numTimes != 0) 
			successRate = numCorrect / numTimes;
		
		return successRate;
	}
	
	// source: https://stackoverflow.com/questions/31375972/how-to-replace-a-specific-line-in-a-file-using-java
	public static void updateFile(int lineNumber, String data) throws IOException {
	    Path path = Paths.get("vocabularylist.txt");
	    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
	    lines.set(lineNumber, data);
	    Files.write(path, lines, StandardCharsets.UTF_8);
	}

	@Override
	// toString
	public String toString() {
		return "Word [chinese=" + chinese + ", pinyin=" + pinyin + ", english=" + english + ", pinyinNoAccent="
				+ pinyinNoAccent + ", numTimes=" + numTimes + ", numCorrect=" + numCorrect + ", numIncorrect="
				+ numIncorrect + "]";
	}
	
	@Override
	// Comparator
	public int compareTo(Word word2) {
		
		return word2.getNumIncorrect() - this.getNumIncorrect();
	}
	

}
