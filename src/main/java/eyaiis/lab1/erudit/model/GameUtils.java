package eyaiis.lab1.erudit.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Q-YAA
 */
public class GameUtils {

    private static final String LETTER_POINTS_FILE = "letterPoints.properties";
    private static final String LETTER_QUANTITY_FILE = "letterQuantity.properties";

    /**
     * Create random word from dictionary, and take letters for it from given letter box.
     *
     * @param letterBox letter box
     * @return List<Letter> random word
     */
    public static List<Letter> getRandomWordFromLetterBox(LetterBox letterBox) {
        List<Letter> resultWord = new ArrayList<Letter>();

        //TODO: take this word from dictionary
        String randomWord = "рандом";

        for (char symbol : randomWord.toCharArray()) {
            char[] chars = new char[]{symbol};

            resultWord.add(letterBox.getLetterByName(new String(chars)));
        }

        return resultWord;
    }

    /**
     * Load letters from resource file. Load letter name, points of letter and letter quantity.
     *
     * @return List<Letter> loaded letters
     */
    public static List<Letter> loadLetterListFromResource() {

        List<Letter> letterList = new ArrayList<Letter>();

        Map<String, Integer> letterPointMap = loadLetterProperties(LETTER_POINTS_FILE);
        Map<String, Integer> letterQuantityMap = loadLetterProperties(LETTER_QUANTITY_FILE);

        for (Map.Entry<String, Integer> letterPoint : letterPointMap.entrySet()) {
            Letter letter = new Letter(letterPoint.getKey(), letterPoint.getValue());

            int quantity = letterQuantityMap.get(letter.getName());
            for (int i = 0; i < quantity; i++) {
                letterList.add(letter);
            }
        }

        return letterList;
    }

    private static Map<String, Integer> loadLetterProperties(String fileName) {
        Map<String, Integer> latterMap = new HashMap<String, Integer>();

        try {
            Properties properties = new Properties();
            properties.load(Game.class.getClassLoader().getResourceAsStream(fileName));

            for (Map.Entry entry : properties.entrySet()) {
                String latter = (String) entry.getKey();
                Integer value = Integer.parseInt((String) entry.getValue());

                latterMap.put(latter.toLowerCase(), value);
            }

        }
        catch (IOException e) {
            throw new IllegalStateException("Latter property file not found!", e);
        }

        return latterMap;
    }
}
