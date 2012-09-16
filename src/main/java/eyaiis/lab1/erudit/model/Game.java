package eyaiis.lab1.erudit.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The main object that process game flow.
 *
 * @author yaskoam
 */
public class Game {

    //necessarily odd, because game field must have central cell
    private static final int FIELD_WIDTH = 15;
    private static final int FIELD_HEIGHT = 15;

    private static final int CARD_ON_HANDS_QUANTITY = 7;

    private static final int POINTS_FOR_EMPTY_LETTER_LIST = 50;

    private GameFieldModel gameFieldModel;

    //all free letter
    private LetterBox letterBox;

    private List<User> userList = new ArrayList<User>();

    private User currentUser;

    public Game(List<User> userList) {
        this.userList = userList;

        letterBox = new LetterBox(GameUtils.loadLetterListFromResource());
        gameFieldModel = new GameFieldModel(FIELD_WIDTH, FIELD_HEIGHT);
    }

    public void startGame() {
        //set first(start) word on the game field
        List<Letter> startWord = GameUtils.getRandomWordFromLetterBox(letterBox);
        setStartWordOnGameFieldCenter(startWord);

        //deal letters for every user
        for (User user : userList) {
            user.setLatterList(letterBox.getRandomLetterList(CARD_ON_HANDS_QUANTITY));
        }

        currentUser = userList.get(0);
    }

    public void nextStep(Map<Integer, Letter> newWordLetters) {
        //check for letter list, and if it empty increase user points
        if (currentUser.getLatterList().isEmpty()) {
            currentUser.increasePoints(POINTS_FOR_EMPTY_LETTER_LIST);
        }

        //update field model and increase user points
        for (Map.Entry<Integer, Letter> letterEntry : newWordLetters.entrySet()) {
            gameFieldModel.setLetter(letterEntry.getValue(), letterEntry.getKey());
            currentUser.increasePoints(letterEntry.getValue().getLetterPoints());
        }

        System.out.println("User points: '" + currentUser.getPoints() + "'");

        //get missing letters for user
        List<Letter> letterList = letterBox.getRandomLetterList(CARD_ON_HANDS_QUANTITY - currentUser.getLatterList().size());
        currentUser.addLater(letterList);

        currentUser = determineNextUser(currentUser);

        //this will help to implement the game bot
        currentUser.executeStep(gameFieldModel);
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public GameFieldModel getGameFieldModel() {
        return gameFieldModel;
    }

    public List<User> getUserList() {
        return userList;
    }

    public static int getCardOnHandsQuantity() {
        return CARD_ON_HANDS_QUANTITY;
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
        int indexOfCentralElementOnField = (FIELD_HEIGHT * FIELD_WIDTH - 1) / 2 + 1;

        int indexOfCentralLetterInWord = (startWord.size() + 1) / 2;

        //set letters before central letter
        for (int i = 0; i < indexOfCentralLetterInWord; i++) {
            int letterFieldIndex = gameFieldModel.getLeftCellIndex(indexOfCentralElementOnField - i);
            int letterWordIndex = indexOfCentralLetterInWord - i - 1;
            gameFieldModel.setLetter(startWord.get(letterWordIndex), letterFieldIndex);
        }

        //set central letter and letters after the central letter
        for (int i = 0; i < startWord.size() - indexOfCentralLetterInWord; i++) {
            int letterFieldIndex = gameFieldModel.getRightCellIndex(indexOfCentralElementOnField - 1 + i);
            int letterWordIndex = indexOfCentralLetterInWord + i;
            gameFieldModel.setLetter(startWord.get(letterWordIndex), letterFieldIndex);
        }
    }
}
