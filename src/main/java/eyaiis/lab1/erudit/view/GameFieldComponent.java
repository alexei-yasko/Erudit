package eyaiis.lab1.erudit.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.google.common.base.Strings;

import eyaiis.lab1.erudit.model.Game;
import eyaiis.lab1.erudit.model.Letter;
import eyaiis.lab1.erudit.model.User;

/**
 * Component that represent game field. Contains list of elementary cell.
 *
 * @author Q-YAA
 */
public class GameFieldComponent extends JPanel {

    private static final int FIELD_COMPONENT_WIDTH = 750;
    private static final int FIELD_COMPONENT_HEIGHT = 750;

    private Game game;

    private List<LetterCellComponent> letterCellComponentList = new ArrayList<LetterCellComponent>();

    public GameFieldComponent(Game game) {
        this.game = game;

        setSize(FIELD_COMPONENT_WIDTH, FIELD_COMPONENT_HEIGHT);

        int cellWidthQuantity = game.getGameFieldModel().getFieldWidth();
        int cellHeightQuantity = game.getGameFieldModel().getFieldHeight();

        setLayout(new GridLayout(cellWidthQuantity, cellHeightQuantity));

        for (int i = 0; i < cellHeightQuantity * cellWidthQuantity; i++) {

            int sellWidth = getCellSideSize(FIELD_COMPONENT_WIDTH, cellWidthQuantity);
            int sellHeight = getCellSideSize(FIELD_COMPONENT_HEIGHT, cellHeightQuantity);

            LetterCellComponent letterComponent = new LetterCellComponent(sellWidth, sellHeight, i);
            letterCellComponentList.add(letterComponent);

            letterComponent.addActionListener(new LetterCellComponentActionListener());
            add(letterComponent);
        }
    }

    /**
     * Refresh presentation of game field using {@link eyaiis.lab1.erudit.model.GameFieldModel}.
     */
    public void refreshGameField() {
        for (LetterCellComponent letterCellComponent : letterCellComponentList) {
            Letter letter = game.getGameFieldModel().getLetter(letterCellComponent.getCellNumber());

            if (letter != null) {
                letterCellComponent.setLetter(letter);
                letterCellComponent.setAvailable(false);
            }
        }

        repaint();
    }

    /**
     * Return cells that were chosen during step.
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
            }
            else if (isAvailableAndChoose(letterCellComponent)) {
                handleAvailableAndChoose(letterCellComponent);
            }
            else if (isUnavailableAndChoose(letterCellComponent)) {
                handleUnavailableAndChoose(letterCellComponent);
            }
            else if (isUnavailableAndNotChoose(letterCellComponent)) {
                handleUnavailableAndNotChoose(letterCellComponent);
            }

            letterCellComponent.repaint();
        }

        private void handleAvailableButNotChoose(LetterCellComponent letterCellComponent) {
            User currentUser = game.getCurrentUser();
            String letterName = JOptionPane.showInputDialog(GameFieldComponent.this, "Choose letterName");

            if (!Strings.isNullOrEmpty(letterName) && currentUser.isHaveLetter(letterName)) {
                letterCellComponent.setLetter(currentUser.getLatterByName(letterName));
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
