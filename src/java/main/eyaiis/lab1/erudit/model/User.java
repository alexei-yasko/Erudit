package eyaiis.lab1.erudit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Q-YAA
 */
public class User {

    protected String name;

    protected int points;

    protected List<Letter> letterList = new ArrayList<Letter>();

    public User(String name) {
        this.name = name;
    }

    /**
     * Method for additional game step processing.
     * <p/>
     * <p>Purpose of this method it's automatic step processing, for example by game bot.
     * You can extend User class, and override this method.
     * </p>
     *
     * @param gameFieldModel GameFieldModel game field
     */
    public void executeStep(GameFieldModel gameFieldModel) {
    }

    public void increasePoints(int additionalPoints) {
        points += additionalPoints;
    }

    public boolean isHasLetter(Character letterName) {
        return getLatterByName(letterName) != null;
    }

    public Letter getLatterByName(Character name) {
        for (Letter letter : letterList) {

            if (letter.getName().equals(Character.toLowerCase(name))) {
                return letter;
            }
        }

        return null;
    }

    public void addLater(Letter later) {
        letterList.add(later);
    }

    public void addLater(List<? extends Letter> latterList) {
        this.letterList.addAll(latterList);
    }

    public void removeLatter(Letter letter) {
        letterList.remove(letter);
    }

    public void removeLatter(List<String> latterList) {
        this.letterList.removeAll(latterList);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Letter> getLetterList() {
        return letterList;
    }

    public void setLetterList(List<Letter> letterList) {
        this.letterList = letterList;
    }
}
