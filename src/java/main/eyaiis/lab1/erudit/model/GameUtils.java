package eyaiis.lab1.erudit.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import eyaiis.lab1.erudit.dictionary.Dictionary;


/**
 * @author Q-YAA
 */
public class GameUtils {

    private static final String LETTER_POINTS_FILE = "letterPoints.properties";
    private static final String LETTER_QUANTITY_FILE = "letterQuantity.properties";

    /**
     * Create random word from dictionary, and take letters for it from given letter box.
     *
     * @param dictionary loaded dictionary
     * @param letterBox letter box
     * @return List<Letter> random word
     */
    public static Word getRandomWord(Dictionary dictionary, LetterBox letterBox) {
        List<Letter> wordLetters = new ArrayList<Letter>();

        String randomWord = dictionary.getRandomWord();

        for (Character name : randomWord.toCharArray()) {
            Letter letter = letterBox.getLetterByName(name);
            if (letter == null) {
                letter = wordLetters.get(wordLetters.indexOf(letter));
                wordLetters.add(new Letter(letter.getName(), letter.getLetterPoints()));
            }
            else {
                wordLetters.add(letter);
            }
        }

        return new Word(wordLetters);
    }

    public static List<Character> convertFromLetterListToCharacterList(List<Letter> letterList) {
        List<Character> characterList = new ArrayList<Character>();

        for (Letter letter : letterList) {
            characterList.add(letter.getName());
        }

        return characterList;
    }

    /**
     * Load letters from resource file. Load letter name, points of letter and letter quantity.
     *
     * @return List<Letter> loaded letters
     */
    public static List<Letter> loadLetterListFromResource() {

        List<Letter> letterList = new ArrayList<Letter>();

        Map<Character, Integer> letterPointMap = loadLetterProperties(LETTER_POINTS_FILE);
        Map<Character, Integer> letterQuantityMap = loadLetterProperties(LETTER_QUANTITY_FILE);

        for (Map.Entry<Character, Integer> letterPoint : letterPointMap.entrySet()) {
            Letter letter = new Letter(letterPoint.getKey(), letterPoint.getValue());

            int quantity = letterQuantityMap.get(letter.getName());
            for (int i = 0; i < quantity; i++) {
                letterList.add(letter);
            }
        }

        return letterList;
    }

    private static Map<Character, Integer> loadLetterProperties(String fileName) {
        Map<Character, Integer> latterMap = new HashMap<Character, Integer>();

        try {
            Properties properties = new Properties();
            properties.load(Game.class.getClassLoader().getResourceAsStream(fileName));

            for (Map.Entry entry : properties.entrySet()) {
                Character letter = ((String) entry.getKey()).charAt(0);
                Integer value = Integer.parseInt((String) entry.getValue());

                latterMap.put(Character.toLowerCase(letter), value);
            }

        }
        catch (IOException e) {
            throw new IllegalStateException("Latter property file not found!", e);
        }

        return latterMap;
    }
}
