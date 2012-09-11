package eyaiis.lab1.erudit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Q-YAA
 */
public class User {

    private String name = "";

    private int points;

    private List<String> latterList = new ArrayList<String>();

    public User() {
    }

    public void increasePoints(int additionalPoints) {
        points += additionalPoints;
    }

    public void addLater(String later) {
        latterList.add(later);
    }

    public void addLater(List<String> latterList) {
        this.latterList.addAll(latterList);
    }

    public void removeLatter(String latter) {
        latterList.remove(latter);
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

    public List<String> getLatterList() {
        return latterList;
    }

    public void setLatterList(List<String> latterList) {
        this.latterList = latterList;
    }
}
