package eyaiis.lab1.erudit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent the word in the game.
 *
 * @author Q-YAA
 */
public class Word {

    private List<Letter> lettersOfWord = new ArrayList<Letter>();

    public Word(List<Letter> lettersOfWord) {
        this.lettersOfWord = lettersOfWord;
    }

    public static Word composeWord(String word, List<Letter> letterList) {
        List<Letter> lettersOfWord = new ArrayList<Letter>();
        letterList = new ArrayList<Letter>(letterList);

        for (Character character : word.toCharArray()) {

            Letter letter = getLetterByName(character, letterList);
            lettersOfWord.add(letter);
            letterList.remove(letter);
        }

        return new Word(lettersOfWord);
    }

    public boolean isContainsLetterList(List<Letter> letterList) {
        List<Letter> lettersOfWord = new ArrayList<Letter>(this.lettersOfWord);

        boolean isContainsLetterList = true;
        for (Letter letter : letterList) {

            if (lettersOfWord.contains(letter)) {
                lettersOfWord.remove(letter);
            }
            else {
                isContainsLetterList = false;
            }
        }

        return isContainsLetterList;
    }

    public List<Letter> getLettersOfWord() {
        return lettersOfWord;
    }

    public Letter getLetter(int letterIndex) {
        return lettersOfWord.get(letterIndex);
    }

    public Letter getLetterByName(Character name) {

        for (Letter letter : lettersOfWord) {

            if (letter.getName().equals(name)) {
                return letter;
            }
        }

        return null;
    }

    public String convertToString() {
        StringBuilder builder = new StringBuilder();

        for (Letter letter : lettersOfWord) {
            builder.append(letter.getName());
        }

        return builder.toString();
    }

    public int calculateWordPoints() {
        int points = 0;

        for (Letter letter : lettersOfWord) {
            points += letter.getLetterPoints();
        }

        return points;
    }

    public int getLength() {
        return lettersOfWord.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Word)) {
            return false;
        }

        Word word = (Word) o;

        if (lettersOfWord != null ? !lettersOfWord.equals(word.lettersOfWord) : word.lettersOfWord != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return lettersOfWord != null ? lettersOfWord.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Word{" + convertToString() + "}";
    }

    private static Letter getLetterByName(Character name, List<Letter> letterList) {

        for (Letter letter : letterList) {
            if (name.equals(letter.getName())) {
                return letter;
            }
        }

        return null;
    }
}
