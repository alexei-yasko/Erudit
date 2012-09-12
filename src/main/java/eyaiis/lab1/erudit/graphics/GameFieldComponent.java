package eyaiis.lab1.erudit.graphics;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import eyaiis.lab1.erudit.model.Game;
import eyaiis.lab1.erudit.model.Letter;

/**
 * Component that represent game field. Contains list of elementary cell.
 *
 * @author Q-YAA
 */
public class GameFieldComponent extends JPanel {

    private static final int FIELD_WIDTH = 750;
    private static final int FIELD_HEIGHT = 750;

    private Game game;

    private List<LetterCellComponent> letterCellComponentList = new ArrayList<LetterCellComponent>();

    public GameFieldComponent(Game game) {
        this.game = game;

        setSize(FIELD_WIDTH, FIELD_HEIGHT);
        setLayout(new GridLayout(Game.CELL_QUANTITY_WIDTH, Game.CELL_QUANTITY_HEIGHT));

        for (int i = 0; i < Game.CELL_QUANTITY_HEIGHT * Game.CELL_QUANTITY_WIDTH; i++) {

            int sellWidth = getCellSideSize(FIELD_WIDTH, Game.CELL_QUANTITY_WIDTH);
            int sellHeight = getCellSideSize(FIELD_HEIGHT, Game.CELL_QUANTITY_HEIGHT);

            LetterCellComponent letterComponent = new LetterCellComponent(sellWidth, sellHeight, i);
            letterCellComponentList.add(letterComponent);

            letterComponent.addActionListener(new LetterCellComponentActionListener(game.getCurrentUser(), this));
            add(letterComponent);
        }
    }

    /**
     * Refresh presentation of game field.
     *
     * @param lettersOnTheField Map<Integer, Letter> map of latter and it position (new state of game)
     */
    public void refreshGameField(Map<Integer, Letter> lettersOnTheField) {
        for (LetterCellComponent letterCellComponent : letterCellComponentList) {
            Letter letter = lettersOnTheField.get(letterCellComponent.getCellNumber());

            if (letter != null) {
                letterCellComponent.setLetter(letter);
            }
        }
    }

    /**
     * Return cells that were chosen during step.
     *
     * @retur List<LetterCellComponent> list of cells
     */
    public List<LetterCellComponent> getChosenLetterCell() {
        List<LetterCellComponent> chosenLetterCell = new ArrayList<LetterCellComponent>();

        for (LetterCellComponent letterCell : letterCellComponentList) {
            if (letterCell.isChoose()) {
                chosenLetterCell.add(letterCell);
            }
        }

        return chosenLetterCell;
    }

    private int getCellSideSize(int fieldSideSize, int cellQuantity) {
        return fieldSideSize / cellQuantity;
    }
}
