package guiClasses;

// Importing generic libraries
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Importing GUI Libraries
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

// Importing other packages' classes
import objectClasses.Word;

/*
 * This class is just a button that also shows the Chinese, Pinyin, and English translation.
 * I would have liked to avoid making another class for a button (tsk tsk Post Secondary App) but
 * this was the only way to set up the ActionListener to actually make the button link to the right
 * WordInfo screen. This is because I initialize these buttons in a loop, and it doesn't let you assign
 * an ActionListener based on the i value of a for-loop.
 */
public class WordButton extends JPanel {
	
	// Initializing GUI objects
	private JButton wordButton;
	private JLabel chineseLabel;
	private JLabel pinyinLabel;
	private JLabel englishLabel;
	
	// Initializing other objects
	private Word currentWord;

	// Constructor
	public WordButton(Word currentWord) {
		
		// Setting the Look and Feel
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		// Setting size of the panel
		this.setPreferredSize(new Dimension(216,54));
		this.setLayout(null);
		
		// Setting up the button
		wordButton = new JButton(new ImageIcon("images/wordlist/emptywordbutton.png"));
		wordButton.setBounds(0, 0, 216, 54);
		wordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenu.frame.getContentPane().removeAll();
				new WordInfo(currentWord);
				MainMenu.frame.repaint();
				MainMenu.frame.revalidate();
			}
		});
		wordButton.setRolloverEnabled(false); // Necessary so that the button doesn't cover the labels
		
		// Initializing label that displays the Chinese character
		chineseLabel = new JLabel(currentWord.getChinese(), SwingConstants.CENTER);
		chineseLabel.setBounds(0, 0, 70, 53);
		chineseLabel.setFont(new Font("SimSun", Font.PLAIN, 11));
		chineseLabel.setForeground(new java.awt.Color(251, 216, 216));
		chineseLabel.setFocusable(false);
						
		// Initializing label that displays the Pinyin
		pinyinLabel = new JLabel(currentWord.getPinyin(), SwingConstants.CENTER);
		pinyinLabel.setBounds(72, 0, 70, 53);
		pinyinLabel.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 11));
		pinyinLabel.setForeground(new java.awt.Color(251, 216, 216));
		chineseLabel.setFocusable(false);

		// Initializing label that displays the English translation
		englishLabel = new JLabel(currentWord.getEnglish(), SwingConstants.CENTER);
		englishLabel.setBounds(144, 0, 70, 53);
		englishLabel.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		englishLabel.setForeground(new java.awt.Color(251, 216, 216));
		chineseLabel.setFocusable(false);
		
		// Adds the labels and button to the panel
		this.add(englishLabel);			
		this.add(pinyinLabel);
		this.add(chineseLabel);
		this.add(wordButton);
		
	}
	
}
