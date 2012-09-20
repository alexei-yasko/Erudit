package eyaiis.lab1.erudit.model;

/**
 * Letter object. Contains letter name and points for this letter.
 *
 * @author Q-YAA
 */
public class Letter {

    private Character name;

    private int letterPoints;

    public Letter(Character name, int letterPoints) {
        this.name = name;
        this.letterPoints = letterPoints;
    }

    public int getLetterPoints() {
        return letterPoints;
    }

    public Character getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Letter)) {
            return false;
        }

        Letter letter = (Letter) o;

        if (letterPoints != letter.letterPoints) {
            return false;
        }
        if (name != null ? !name.equals(letter.name) : letter.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + letterPoints;
        return result;
    }

    @Override
    public String toString() {
        return "Letter{" +
            "letterPoints=" + letterPoints +
            ", name='" + name + '\'' +
            '}';
    }
}
