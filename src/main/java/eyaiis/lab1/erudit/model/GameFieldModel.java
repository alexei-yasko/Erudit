package eyaiis.lab1.erudit.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that present game field.
 *
 * @author Q-YAA
 */
public class GameFieldModel {

    //key - position on the field (cell number form 0 to height * width), value - letter on this position
    private Map<Integer, Letter> lettersOnTheField = new HashMap<Integer, Letter>();

    private int fieldWidth;
    private int fieldHeight;


    public GameFieldModel(int fieldWidth, int fieldHeight) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
    }

    /**
     * Add letter to the given position.
     *
     * @param letter letter
     * @param letterPosition letter position
     */
    public void addLetter(Letter letter, int letterPosition) {
        lettersOnTheField.put(letterPosition, letter);
    }

    /**
     * Return the letter which placed in the given position.
     *
     * @param letterPosition letter position
     * @return Letter letter
     */
    public Letter getLetter(int letterPosition) {
        return lettersOnTheField.get(letterPosition);
    }

    /**
     * Return the index of the cell that is the top for the cell with the given index.
     *
     * @param currentCellIndex index of current cell
     * @return int index for the bottom cell. If the cell with the given index doesn't has the top cell, return -1.
     */
    public int getTopCellIndex(int currentCellIndex) {
        int topCellIndex;

        if (currentCellIndex < fieldWidth) {
            topCellIndex = -1;
        }
        else {
            topCellIndex = currentCellIndex - fieldWidth;
        }

        return topCellIndex;
    }

    /**
     * Return the index of the cell that is the bottom for the cell with the given index.
     *
     * @param currentCellIndex index of current cell
     * @return int index for the bottom cell. If the cell with the given index doesn't has the bottom cell, return -1.
     */
    public int getBottomCellIndex(int currentCellIndex) {
        int bottomCellIndex;

        if (currentCellIndex >= fieldHeight * fieldWidth - fieldWidth) {
            bottomCellIndex = -1;
        }
        else {
            bottomCellIndex = currentCellIndex + fieldWidth;
        }

        return bottomCellIndex;
    }

    /**
     * Return the index of the cell that is the right for the cell with the given index.
     *
     * @param currentCellIndex index of current cell
     * @return int index for the bottom cell. If the cell with the given index doesn't has the right cell, return -1.
     */
    public int getRightCellIndex(int currentCellIndex) {
        int rightCellIndex;

        if ((currentCellIndex + 1) % fieldWidth == 0) {
            rightCellIndex = -1;
        }
        else {
            rightCellIndex = currentCellIndex + 1;
        }

        return rightCellIndex;
    }

    /**
     * Return the index of the cell that is the left for the cell with the given index.
     *
     * @param currentCellIndex index of current cell
     * @return int index for the bottom cell. If the cell with the given index doesn't has the left cell, return -1.
     */
    public int getLeftCellIndex(int currentCellIndex) {
        int leftCellIndex;

        if (currentCellIndex % fieldWidth == 0) {
            leftCellIndex = -1;
        }
        else {
            leftCellIndex = currentCellIndex - 1;
        }

        return leftCellIndex;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }
}
