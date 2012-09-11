package eyaiis.lab1.erudit.graphics;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * @author Q-YAA
 */
public class LetterCellComponentActionListener implements ActionListener {

    private Component parent;

    public LetterCellComponentActionListener(Component parent) {
        this.parent = parent;
    }

    public void actionPerformed(ActionEvent e) {
        LetterCellComponent letterCellComponent = (LetterCellComponent) e.getSource();

        if (isAvailableButNotChoose(letterCellComponent)) {
            String input = JOptionPane.showInputDialog(parent, "Choose latter");
            String letter = input != null ? input : letterCellComponent.getLetter();

            letterCellComponent.setLetter(letter);
            letterCellComponent.setChoose(true);
        }
        else if (isAvailableAndChoose(letterCellComponent)) {
            letterCellComponent.setLetter("");
            letterCellComponent.setChoose(false);
        }
        else if (isUnavailableAndChoose(letterCellComponent)) {
            letterCellComponent.setChoose(false);
        }
        else if (isUnavailableAndNotChoose(letterCellComponent)) {
            letterCellComponent.setChoose(true);
        }

        letterCellComponent.setAvailable(false);
        letterCellComponent.repaint();
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
