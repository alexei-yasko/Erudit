package eyaiis.lab1.erudit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yaskoam
 */
public class Game {

    //necessarily odd, because game field must have central cell
    private static final int FIELD_WIDTH = 15;
    private static final int FIELD_HEIGHT = 15;

    private static final int CARD_ON_HANDS_QUANTITY = 7;

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

    public void nextStep() {
        List<Letter> letterList = letterBox.getRandomLetterList(CARD_ON_HANDS_QUANTITY - currentUser.getLatterList().size());
        currentUser.addLater(letterList);

        currentUser = determineNextUser(currentUser);
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
            gameFieldModel.addLetter(startWord.get(letterWordIndex), letterFieldIndex);
        }

        //set central letter and letters after the central letter
        for (int i = 0; i < startWord.size() - indexOfCentralLetterInWord; i++) {
            int letterFieldIndex = gameFieldModel.getRightCellIndex(indexOfCentralElementOnField - 1 + i);
            int letterWordIndex = indexOfCentralLetterInWord + i;
            gameFieldModel.addLetter(startWord.get(letterWordIndex), letterFieldIndex);
        }
    }
}
