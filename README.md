# Chinese Vocabulary App

​	Chinese Vocabulary App is as the name suggests, an app to help Chinese language learners improve their vocabulary through the use of flash card quizzes.  On top of quizzes, the program also offers a look at your personalized stats and a word list to study from.

​	This program was completely developed by myself, from proposal to prototyping to coding to bug testing. I used Adobe Photoshop to design the GUI, Java to code, and Google Cloud API to build in translation and text-to-speech functionality.

---

## Screenshots

![Main Menu](https://raw.githubusercontent.com/ericlinjh/chinese-vocabulary-app/master/images/readme/mainmenu.PNG)

![Mode Selection](https://raw.githubusercontent.com/ericlinjh/chinese-vocabulary-app/master/images/readme/modeselect.PNG)

---

![User Stats](https://github.com/ericlinjh/chinese-vocabulary-app/blob/master/images/readme/userstats.PNG?raw=true)

User Stats are updated live according to the user's results with the quizzes. Clicking on the "words you have trouble with" will lead to the respective word's *Word Info* page.

---

![Word List](https://github.com/ericlinjh/chinese-vocabulary-app/blob/master/images/readme/wordlist.PNG?raw=true)

Word List for the user to study from. Clicking on each respective word will also lead to it's respective *Word Info* page. This page can also lead to the *New Word* page. The list will update as well if the user chooses to add a new word.

---

![Add Word](https://github.com/ericlinjh/chinese-vocabulary-app/blob/master/images/readme/addword_before.png?raw=true)

If the user selects to add a new word to the vocabulary bank, they will be presented with this screen. All they need to do is type in a word in English or Chinese, and press *Check.* The translation for said word will automatically appear in the box through the use of Google Translate API. 

![Add Word Completed](https://github.com/ericlinjh/chinese-vocabulary-app/blob/master/images/readme/addword_after.png?raw=true)

Clicking submit will add the new word to the vocabulary bank, allowing it to be accessed both in the *Quiz*, as well as in the *Word List*.

---

![Word Info](https://github.com/ericlinjh/chinese-vocabulary-app/blob/master/images/readme/wordinfo.PNG?raw=true)

Selecting a word from the *Word List* or *User Stats* page will lead to this screen. The user's stats with this word will be displayed, as well as the word in Chinese, Pinyin, and English. On the bottom right is a button the user can use to hear the word in Chinese through the use of Google Text-to-Speech.

---

![Quiz English-to-Chinese](https://github.com/ericlinjh/chinese-vocabulary-app/blob/master/images/readme/quiz_en-ch.PNG?raw=true)

![Quiz Chinese-to-Pinyin](https://github.com/ericlinjh/chinese-vocabulary-app/blob/master/images/readme/quiz_ch-py.PNG?raw=true)

![Quiz Chinese-to-English](https://github.com/ericlinjh/chinese-vocabulary-app/blob/master/images/readme/quiz_ch-en.PNG?raw=true)

The *Quiz* is the main functionality of the program. There are three types of questions to answer, English-to-Chinese, Chinese-to-Pinyin, and Chinese-to-English. A question type will be randomly selected, then a word from the vocabulary bank will be selected to quiz the user.

![Wrong Answer](https://github.com/ericlinjh/chinese-vocabulary-app/blob/master/images/readme/quizwrong.PNG?raw=true)

The quiz will tell you whether you were right or wrong, allowing the user to receive instant feedback. Once the *Submit* button is pressed, the users stats are instantly updated and can be accessed from the *User Stats* screen.

---

## Notable Features

- Google Translate API
- Google Text-to-Speech API
- Chinese to Pinyin Translation
  - pinyin4j library
- UTF-8 text file reading and writing

---

## Areas of Concern

- Certain words don't work in the quiz such as "cheque," probably due to the fact it is a British spelling of a word.
  - Since this is all using the Google Translate API, there is nothing I can really do about this (Unless British English is a completely different language option)
- When pressing one of the word buttons, the button covers the three labels that show Chinese character, pinyin, and English translation.
  - This wouldn't be a problem since you go to another screen right away, but if you press return it will stay covered
    - This can probably be fixed by using a different layout
- After adding a new word, it is not updated properly on the owrd list and will instead show the first word, "zero", in it's place
  - I honestly have no idea why this happens. During bug-testing I found the console printed the proper word, but the GUI didn't.
    - However, this is fixed if you restart the program.
- Synonyms or slightly different takes on the same word do not register (Ex. "make phone call" vs "to make a phone call")
  - I don't know how to implement this, it seems too difficult and beyond the scope of my current programming level
- Some words get cut off if they're too long in the WordList buttons
  - An error in the design of the WordList screen
- Google Cloud API features are rather slow
- The message for "This word is already in the list" appears more often than it should