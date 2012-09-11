package eyaiis.lab1.erudit.model;

/**
 * @author Q-YAA
 */
public class User {

    private String name = "";

    private int points;

    public User() {
    }

    public void increasePoints(int additionalPoints) {
        points += additionalPoints;
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
}
