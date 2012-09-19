package eyaiis.lab1.erudit.model;

import java.util.ArrayList;
import java.util.List;

import eyaiis.lab1.erudit.dictionary.Dictionary;

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

    private Dictionary dictionary;

    private GameFieldModel gameFieldModel;

    //all free letters
    private LetterBox letterBox;

    private List<User> userList = new ArrayList<User>();

    private User currentUser;

    public Game(List<User> userList) {
        this.userList = userList;

        dictionary = new Dictionary();
        letterBox = new LetterBox(GameUtils.loadLetterListFromResource());
        gameFieldModel = new GameFieldModel(FIELD_WIDTH, FIELD_HEIGHT);
    }

    public void startGame() {
        //set first(start) word on the game field
        List<Letter> startWord = GameUtils.getRandomWord(dictionary, letterBox);
        setStartWordOnGameFieldCenter(startWord);

        //deal letters for every user
        for (User user : userList) {
            user.setLetterList(letterBox.getRandomLetterList(CARD_ON_HANDS_QUANTITY));
        }

        currentUser = userList.get(0);
    }

    public void nextStep(List<Letter> word) {
        //check for letter list, and if it empty increase user points
        if (currentUser.getLetterList().isEmpty()) {
            currentUser.increasePoints(POINTS_FOR_EMPTY_LETTER_LIST);
        }

        //increase user points
        for (Letter letter : word) {
            currentUser.increasePoints(letter.getLetterPoints());
        }

        System.out.println("User points: '" + currentUser.getPoints() + "'");

        //get missing letters for user
        List<Letter> letterList = letterBox.getRandomLetterList(CARD_ON_HANDS_QUANTITY - currentUser.getLetterList().size());
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

    public Dictionary getDictionary() {
        return dictionary;
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
        int rowOfCentralElement = FIELD_HEIGHT / 2;
        int columnOfCentralElement = FIELD_WIDTH / 2;

        int indexOfCentralLetter = startWord.size() / 2;

        //set letters before central letter
        for (int i = 0; i < indexOfCentralLetter; i++) {
            Position position =
                gameFieldModel.getLeftCellPosition(new Position(rowOfCentralElement, columnOfCentralElement - i));
            int letterIndex = indexOfCentralLetter - i - 1;
            gameFieldModel.setLetter(startWord.get(letterIndex), position);
        }

        //set central letter and letters after the central letter
        for (int i = 0; i < startWord.size() - indexOfCentralLetter; i++) {
            Position position =
                gameFieldModel.getRightCellPosition(new Position(rowOfCentralElement, columnOfCentralElement - 1 + i));
            int letterIndex = indexOfCentralLetter + i;
            gameFieldModel.setLetter(startWord.get(letterIndex), position);
        }
    }
}
