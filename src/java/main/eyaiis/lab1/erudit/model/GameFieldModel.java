package eyaiis.lab1.erudit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that present game field.
 *
 * @author Q-YAA
 */
public class GameFieldModel {

    private List<List<Letter>> field = new ArrayList<List<Letter>>();

    private int fieldWidth;
    private int fieldHeight;

    public GameFieldModel(int fieldWidth, int fieldHeight) {
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;

        initializeField(field, fieldHeight, fieldWidth);
    }

    /**
     * Set letter to the given position. Replace old latter.
     *
     * @param letter letter
     * @param position Position letter position on the field
     */
    public void setLetter(Letter letter, Position position) {
        field.get(position.getRow()).set(position.getColumn(), letter);
    }

    /**
     * Return the letter which placed in the given position.
     *
     * @param position Position letter position on the field
     * @return Letter letter
     */
    public Letter getLetter(Position position) {
        return field.get(position.getRow()).get(position.getColumn());
    }

    /**
     * Method that return row with the given index from the game field.
     *
     * @param rowIndex row index
     * @return List<Letter> row from the field
     */
    public List<Letter> getRow(int rowIndex) {
        return field.get(rowIndex);
    }

    /**
     * Method that return column with the given index from the game field.
     *
     * @param columnIndex column index
     * @return List<Letter> column from the field
     */
    public List<Letter> getColumn(int columnIndex) {
        List<Letter> column = new ArrayList<Letter>();
        for (List<Letter> row : field) {
            column.add(row.get(columnIndex));
        }

        return column;
    }

    /**
     * Return the position of the cell that is the top for the cell with the given position.
     *
     * @param currentPosition positions of current cell
     * @return Position position for the top cell.
     *         If the cell with the given position doesn't has the top cell, return null.
     */
    public Position getTopCellPosition(Position currentPosition) {
        int currentRow = currentPosition.getRow();
        int currentColumn = currentPosition.getColumn();

        return currentRow - 1 >= 0 ? new Position(currentRow - 1, currentColumn) : null;
    }

    /**
     * Return the position of the cell that is the bottom for the cell with the given position.
     *
     * @param currentPosition position of current cell
     * @return Position position for the bottom cell.
     *         If the cell with the given position doesn't has the bottom cell, return null.
     */
    public Position getBottomCellPosition(Position currentPosition) {
        int currentRow = currentPosition.getRow();
        int currentColumn = currentPosition.getColumn();

        return currentRow + 1 < fieldHeight ? new Position(currentRow + 1, currentColumn) : null;
    }

    /**
     * Return the position of the cell that is the right for the cell with the given position.
     *
     * @param currentPosition position of current cell
     * @return Position position for the bottom cell.
     *         If the cell with the given position doesn't has the right cell, return null.
     */
    public Position getRightCellPosition(Position currentPosition) {
        int currentRow = currentPosition.getRow();
        int currentColumn = currentPosition.getColumn();

        return currentColumn + 1 < fieldWidth ? new Position(currentRow, currentColumn + 1) : null;
    }

    /**
     * Return the position of the cell that is the left for the cell with the given position.
     *
     * @param currentPosition position of current cell
     * @return Position position for the bottom cell.
     *         If the cell with the given position doesn't has the left cell, return null.
     */
    public Position getLeftCellPosition(Position currentPosition) {
        int currentRow = currentPosition.getRow();
        int currentColumn = currentPosition.getColumn();

        return currentColumn - 1 >= 0 ? new Position(currentRow, currentColumn - 1) : null;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    private void initializeField(List<List<Letter>> field, int fieldHeight, int fieldWidth) {
        for (int i = 0; i < fieldHeight; i++) {
            List<Letter> fieldRow = new ArrayList<Letter>();
            for (int j = 0; j < fieldWidth; j++) {
                fieldRow.add(null);
            }

            field.add(fieldRow);
        }
    }
}
