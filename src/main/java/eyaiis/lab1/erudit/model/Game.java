package eyaiis.lab1.erudit.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaskoam
 */
public class Game {

    //necessarily odd, because game field must have central cell
    public static final int CELL_QUANTITY_WIDTH = 15;
    public static final int CELL_QUANTITY_HEIGHT = 15;

    private static final int CARD_ON_HANDS_QUANTITY = 7;

    //all free letter
    private LetterBox letterBox;

    //key - position on the field (cell number form 0 to height * width), value - letter on this position
    private Map<Integer, Letter> lettersOnTheField = new HashMap<Integer, Letter>();

    private List<User> userList = new ArrayList<User>();

    private User currentUser;

    public Game(List<User> userList) {
        this.userList = userList;

        letterBox = new LetterBox(GameUtils.loadLetterList());

        List<Letter> startWord = GameUtils.getRandomWordFromLetterBox(letterBox);
        setStartWordOnGameFieldCenter(startWord);
    }

    public void startGame() {
        for (User user : userList) {
            user.setLatterList(letterBox.getRandomLetterList(CARD_ON_HANDS_QUANTITY));
        }

        currentUser = userList.get(0);
    }

    public void nextStep() {
        List<Letter> letterList = letterBox.getRandomLetterList(CARD_ON_HANDS_QUANTITY - currentUser.getLatterList().size());
        currentUser.addLater(letterList);

        currentUser = determineNextUser(currentUser);
    }

    public void addLetterOnTheField(Letter letter) {

    }

    public Map<Integer, Letter> getLettersOnTheField() {
        return lettersOnTheField;
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private User determineNextUser(User currentUser) {
        int currentIndex = userList.indexOf(currentUser);
        int nextIndex = 0;

        if (currentIndex != userList.size() - 1) {
            nextIndex = currentIndex + 1;
        }

        return userList.get(nextIndex);
    }

    private void setStartWordOnGameFieldCenter(List<Letter> startWord) {
        int numberOfCentralElementOnField = (CELL_QUANTITY_HEIGHT * CELL_QUANTITY_WIDTH - 1) / 2 + 1;

        int numberOfCentralLetterInWord = startWord.size() / 2;

        //set letters before central letter
        for (int i = numberOfCentralElementOnField - numberOfCentralLetterInWord; i < numberOfCentralElementOnField; i++) {
            lettersOnTheField.put(i, startWord.get(i - (numberOfCentralElementOnField - numberOfCentralLetterInWord)));
        }

        //set central letter
        lettersOnTheField.put(numberOfCentralElementOnField, startWord.get(numberOfCentralLetterInWord));

        //set letters after central letter
//        for (int i = numberOfCentralElementOnField; i < numberOfCentralElementOnField + numberOfCentralLetterInWord; i++) {
//            lettersOnTheField.put(i + 1, startWord.get(numberOfCentralLetterInWord + i - (numberOfCentralElementOnField - numberOfCentralLetterInWord));
//        }
    }
}
