package eyaiis.lab1.erudit.dictionary;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * @author Q-YAA
 */
public class DictionaryTest {

    @Test
    public void testGetPossibleWordsFromLetters() {
        List<Character> characters = Arrays.asList(
            'а',
            'б',
            'в',
            'г',
            'д',
            'е',
            'з'
        );

        Dictionary dictionary = new Dictionary();
        List<String> wordList = dictionary.composeWordsFromLetters(characters);

        for (String word : wordList) {
            System.out.println(word);
        }
    }
}
