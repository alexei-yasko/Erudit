package eyaiis.lab1.erudit.model;

/**
 * Letter object. Contains letter name and points for this letter.
 *
 * @author Q-YAA
 */
public class Letter {

    private String name;

    private int letterPoints;

    public Letter(String name, int letterPoints) {
        this.name = name;
        this.letterPoints = letterPoints;
    }

    public int getLetterPoints() {
        return letterPoints;
    }

    public void setLetterPoints(int letterPoints) {
        this.letterPoints = letterPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.substring(0, 1);
    }
}
