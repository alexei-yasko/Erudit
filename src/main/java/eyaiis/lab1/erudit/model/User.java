package eyaiis.lab1.erudit.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Q-YAA
 */
public class User {

    private String name;

    private int points;

    private List<Letter> latterList = new ArrayList<Letter>();

    public User(String name) {
        this.name = name;
    }

    public void executeStep(Map<Integer, Letter> letterOnTheField) {
    }

    public void increasePoints(int additionalPoints) {
        points += additionalPoints;
    }

    public boolean isHaveLetter(String letterName) {
        return getLatterByName(letterName) != null;
    }

    public Letter getLatterByName(String name) {
        for (Letter letter : latterList) {

            if (letter.getName().equals(name.toLowerCase())) {
                return letter;
            }
        }

        return null;
    }

    public void addLater(Letter later) {
        latterList.add(later);
    }

    public void addLater(List<? extends Letter> latterList) {
        this.latterList.addAll(latterList);
    }

    public void removeLatter(Letter letter) {
        latterList.remove(letter);
    }

    public void removeLatter(List<String> latterList) {
        this.latterList.removeAll(latterList);
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

    public List<Letter> getLatterList() {
        return latterList;
    }

    public void setLatterList(List<Letter> latterList) {
        this.latterList = latterList;
    }
}
