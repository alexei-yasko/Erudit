package eyaiis.lab1.erudit.view;

import com.google.common.base.Strings;
import eyaiis.lab1.erudit.model.Game;
import eyaiis.lab1.erudit.model.Letter;
import eyaiis.lab1.erudit.model.Position;
import eyaiis.lab1.erudit.model.User;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Component that represent game field. Contains list of elementary cell.
 *
 * @author Q-YAA
 */
public class GameFieldComponent extends JPanel {

    private static final int FIELD_COMPONENT_WIDTH = 750;
    private static final int FIELD_COMPONENT_HEIGHT = 750;

    private static final int BORDER_SIZE = 2;

    private Game game;

    private List<List<LetterCellComponent>> field = new ArrayList<List<LetterCellComponent>>();

    public GameFieldComponent(Game game) {
        this.game = game;

        setSize(FIELD_COMPONENT_WIDTH, FIELD_COMPONENT_HEIGHT);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, BORDER_SIZE));

        int cellWidthQuantity = game.getGameFieldModel().getFieldWidth();
        int cellHeightQuantity = game.getGameFieldModel().getFieldHeight();

        setLayout(new GridLayout(cellWidthQuantity, cellHeightQuantity));

        //Build game field
        for (int i = 0; i < cellHeightQuantity; i++) {
            List<LetterCellComponent> row = new ArrayList<LetterCellComponent>();

            for (int j = 0; j < cellWidthQuantity; j++) {
                int sellWidth = getCellSideSize(FIELD_COMPONENT_WIDTH, cellWidthQuantity);
                int sellHeight = getCellSideSize(FIELD_COMPONENT_HEIGHT, cellHeightQuantity);

                LetterCellComponent letterComponent =
                    new LetterCellComponent(sellWidth, sellHeight, new Position(i, j));
                row.add(letterComponent);

                letterComponent.addActionListener(new LetterCellComponentActionListener());
                add(letterComponent);
            }

            field.add(row);
        }
    }

    /**
     * Refresh presentation of game field using {@link eyaiis.lab1.erudit.model.GameFieldModel}.
     */
    public void refreshGameField() {
        for (List<LetterCellComponent> row : field) {

            for (LetterCellComponent letterCell : row) {

                Letter letter = game.getGameFieldModel().getLetter(letterCell.getPosition());

                if (letter != null) {
                    letterCell.setLetter(letter);
                    letterCell.setAvailable(false);
                }
            }
        }

        repaint();
    }

    /**
     * Return cells that were chosen during step.
     */
    public List<LetterCellComponent> getChosenLetterCell() {
        List<LetterCellComponent> chosenLetterCell = new ArrayList<LetterCellComponent>();

        for (List<LetterCellComponent> row : field) {

            for (LetterCellComponent letterCell : row) {

                if (letterCell.isChoose()) {
                    chosenLetterCell.add(letterCell);
                }
            }
        }

        return chosenLetterCell;
    }

    private int getCellSideSize(int fieldSideSize, int cellQuantity) {
        return fieldSideSize / cellQuantity;
    }

    /**
     * Process clicks on cell of game field.
     *
     * @author Q-YAA
     */
    private class LetterCellComponentActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            LetterCellComponent letterCellComponent = (LetterCellComponent) e.getSource();

            if (isAvailableButNotChoose(letterCellComponent)) {
                handleAvailableButNotChoose(letterCellComponent);
            } else if (isAvailableAndChoose(letterCellComponent)) {
                handleAvailableAndChoose(letterCellComponent);
            } else if (isUnavailableAndChoose(letterCellComponent)) {
                handleUnavailableAndChoose(letterCellComponent);
            } else if (isUnavailableAndNotChoose(letterCellComponent)) {
                handleUnavailableAndNotChoose(letterCellComponent);
            }

            letterCellComponent.repaint();
        }

        private void handleAvailableButNotChoose(LetterCellComponent letterCellComponent) {
            User currentUser = game.getCurrentUser();
            String letterName = JOptionPane.showInputDialog(GameFieldComponent.this, "Choose letterName");

            if (!Strings.isNullOrEmpty(letterName) && currentUser.isHaveLetter(letterName.charAt(0))) {
                letterCellComponent.setLetter(currentUser.getLatterByName(letterName.charAt(0)));
                currentUser.removeLatter(letterCellComponent.getLetter());

                letterCellComponent.setChoose(true);
            }
        }

        private void handleAvailableAndChoose(LetterCellComponent letterCellComponent) {
            game.getCurrentUser().addLater(letterCellComponent.getLetter());

            letterCellComponent.setLetter(null);
            letterCellComponent.setChoose(false);
        }

        private void handleUnavailableAndChoose(LetterCellComponent letterCellComponent) {
            letterCellComponent.setChoose(false);
        }

        private void handleUnavailableAndNotChoose(LetterCellComponent letterCellComponent) {
            letterCellComponent.setChoose(true);
        }

        private boolean isAvailableButNotChoose(LetterCellComponent letterCellComponent) {
            return letterCellComponent.isAvailable() && !letterCellComponent.isChoose();
        }

        private boolean isAvailableAndChoose(LetterCellComponent letterCellComponent) {
            return letterCellComponent.isAvailable() && letterCellComponent.isChoose();
        }

        private boolean isUnavailableAndNotChoose(LetterCellComponent letterCellComponent) {
            return !letterCellComponent.isAvailable() && !letterCellComponent.isChoose();
        }

        private boolean isUnavailableAndChoose(LetterCellComponent letterCellComponent) {
            return !letterCellComponent.isAvailable() && letterCellComponent.isChoose();
        }
    }
}
