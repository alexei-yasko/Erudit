package eyaiis.lab1.erudit.view;

import java.util.Comparator;

/**
 * @author Q-YAA
 */
public class LetterCellComparator implements Comparator<LetterCellComponent> {

    public int compare(LetterCellComponent o1, LetterCellComponent o2) {
        return (o1.getPosition().getRow() - o2.getPosition().getRow()) * 100 +
            o1.getPosition().getColumn() - o2.getPosition().getColumn();
    }
}
