package eyaiis.lab1.erudit.dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Q-YAA
 */
public class Dictionary {

    private static final String DICTIONARY_FILE_NAME = "dictionary/dictionary.txt";

    //key - the first two letters of the word (prefix), value - corresponding word
    private Map<String, List<String>> dictionary = new HashMap<String, List<String>>();

    private static final int DICTIONARY_KEY_LENGTH = 2;

    public Dictionary() {
        loadDictionary();
    }

    public boolean isWordExist(String word) {
        List<String> wordList = dictionary.get(word.substring(0, DICTIONARY_KEY_LENGTH));

        return wordList != null && wordList.contains(word);
    }

    public String getRandomWord() {
        Random random = new Random();
        int index = random.nextInt(dictionary.size());

        List<String> wordList = (List<String>) dictionary.values().toArray()[index];

        index = random.nextInt(wordList.size());

        return wordList.get(index);
    }

    public List<String> composeWordsFromLetters(List<Character> letterList) {
        List<String> wordWithPossiblePrefixes = new ArrayList<String>();

        for (String prefix : getPossibleWordPrefixesFromLetters(letterList)) {
            wordWithPossiblePrefixes.addAll(dictionary.get(prefix));
        }

        List<String> possibleWords = new ArrayList<String>();

        for (String word : wordWithPossiblePrefixes) {
            if (isStringPossibleFromLetters(letterList, word)) {
                possibleWords.add(word);
            }
        }

        return possibleWords;
    }

    private List<String> getPossibleWordPrefixesFromLetters(List<Character> letterList) {

        List<String> possiblePrefixes = new ArrayList<String>();

        for (String prefix : dictionary.keySet()) {

            if(isStringPossibleFromLetters(letterList, prefix)) {
                possiblePrefixes.add(prefix);
            }
        }

        return possiblePrefixes;
    }

    private boolean isStringPossibleFromLetters(List<Character> letterList, String string) {
        letterList = new ArrayList<Character>(letterList);
        boolean isPrefixPossible = true;

        for (Character character : string.toCharArray()) {
            if (!letterList.contains(character)) {
                isPrefixPossible = false;
            }
            else {
                letterList.remove(letterList.indexOf(character));
            }
        }

        return isPrefixPossible;
    }

    private void loadDictionary() {
        try {
            InputStream inputStream = new FileInputStream(DICTIONARY_FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String readLine = null;
            while ((readLine = bufferedReader.readLine()) != null) {
                List<String> wordList = dictionary.get(readLine.substring(0, DICTIONARY_KEY_LENGTH));

                if (wordList == null) {
                    wordList = new ArrayList<String>();
                    dictionary.put(readLine.substring(0, DICTIONARY_KEY_LENGTH), wordList);
                }

                wordList.add(readLine);
            }
        }
        catch (FileNotFoundException e) {
            throw new IllegalStateException("Dictionary file not found!", e);
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
