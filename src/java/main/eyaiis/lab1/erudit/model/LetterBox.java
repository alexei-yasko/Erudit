package eyaiis.lab1.erudit.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Q-YAA
 */
public class LetterBox {

    private List<Letter> letterList = new ArrayList<Letter>();

    public LetterBox(List<Letter> letterList) {
        this.letterList = letterList;
    }

    /**
     * Return letter from box by name and remove them from it.
     *
     * @return Letter letter object
     */
    public Letter getLetterByName(Character name) {
        for (Letter letter : letterList) {

            if (letter.getName().equals(name)) {
                letterList.remove(letter);
                return letter;
            }
        }

        return null;
    }

    /**
     * Return random letter from box and remove them from it.
     *
     * @return Letter letter object
     */
    public Letter getRandomLetter() {
        Random random = new Random();
        int index = random.nextInt(letterList.size());

        Letter letter = letterList.get(index);
        letterList.remove(index);

        return letter;
    }

    public boolean isEmpty() {
        return letterList.isEmpty();
    }

    /**
     * Return list of random letter from box and remove them from it.
     *
     * @return List<Letter> list of letter objects
     */
    public List<Letter> getRandomLetterList(int quantity) {
        List<Letter> selectedLetters = new ArrayList<Letter>();

        for (int i = 0; i < quantity; i++) {

            if (!letterList.isEmpty()) {
                selectedLetters.add(getRandomLetter());
            }
        }

        return selectedLetters;
    }
}
