package eyaiis.lab1.erudit.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author yaskoam
 */
public class Game {

    private static final String LATTER_POINTS_FILE = "letterPoints.properties";
    private static final String LATTER_QUANTITY_FILE = "letterQuantity.properties";

    private Map<String, Integer> latterPointsMap = new HashMap<String, Integer>();
    private Map<String, Integer> latterQuantityMap = new HashMap<String, Integer>();

    public Game() {
        latterPointsMap = loadLetterProperties(LATTER_POINTS_FILE);
        latterQuantityMap = loadLetterProperties(LATTER_QUANTITY_FILE);
    }

    public void startGame() {
        //For load test
        System.out.println(latterPointsMap.size());
        System.out.println(latterQuantityMap.size());
    }

    private Map<String, Integer> loadLetterProperties(String fileName) {
        Map<String, Integer> latterMap = new HashMap<String, Integer>();

        try {
            Properties properties = new Properties();
            properties.load(Game.class.getClassLoader().getResourceAsStream(fileName));

            for (Map.Entry entry : properties.entrySet()) {
                String latter = (String) entry.getKey();
                Integer value = Integer.parseInt((String) entry.getValue());

                latterMap.put(latter, value);
            }

        } catch (IOException e) {
            throw new IllegalStateException("Latter property file not found!", e);
        }

        return latterMap;
    }
}
