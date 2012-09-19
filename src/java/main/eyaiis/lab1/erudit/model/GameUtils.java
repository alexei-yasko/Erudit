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
    public static List<Letter> getRandomWord(Dictionary dictionary, LetterBox letterBox) {
        List<Letter> resultWord = new ArrayList<Letter>();

        String randomWord = dictionary.getRandomWord();

        for (Character name : randomWord.toCharArray()) {
            resultWord.add(letterBox.getLetterByName(name));
        }

        return resultWord;
    }

    /**
     * Method that check if given word exist in dictionary.
     *
     * @param dictionary dictionary
     * @param word List<Letter> given word
     * @return true - word exist in dictionary, false - word doesn't exist in dictionary
     */
    public static boolean isWordExist(Dictionary dictionary, List<Letter> word) {

        StringBuffer buffer = new StringBuffer();

        for (Letter letter : word) {
            buffer.append(letter.getName());
        }

        return dictionary.isWordExist(buffer.toString());
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
