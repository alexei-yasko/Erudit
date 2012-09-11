package eyaiis.lab1.erudit.graphics;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * Component that represent game field. Contains list of elementary cell.
 *
 * @author Q-YAA
 */
public class GameFieldComponent extends JPanel {

    private static final int FIELD_WIDTH = 750;
    private static final int FIELD_HEIGHT = 750;

    private static final int CELL_QUANTITY_WIDTH = 15;
    private static final int CELL_QUANTITY_HEIGHT = 15;

    private List<LetterCellComponent> letterCellComponentList = new ArrayList<LetterCellComponent>();

    public GameFieldComponent() {
        setSize(FIELD_WIDTH, FIELD_HEIGHT);
        setLayout(new GridLayout(CELL_QUANTITY_WIDTH, CELL_QUANTITY_HEIGHT));

        for (int i = 0; i < CELL_QUANTITY_HEIGHT * CELL_QUANTITY_WIDTH; i++) {

            int sellWidth = getCellSideSize(FIELD_WIDTH, CELL_QUANTITY_WIDTH);
            int sellHeight = getCellSideSize(FIELD_HEIGHT, CELL_QUANTITY_HEIGHT);

            LetterCellComponent letterComponent = new LetterCellComponent(sellWidth, sellHeight, i);
            letterCellComponentList.add(letterComponent);

            letterComponent.addActionListener(new LetterCellComponentActionListener(this));
            add(letterComponent);
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
