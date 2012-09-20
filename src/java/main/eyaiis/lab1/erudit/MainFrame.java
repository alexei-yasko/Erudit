package eyaiis.lab1.erudit;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import eyaiis.lab1.erudit.model.Game;
import eyaiis.lab1.erudit.model.Letter;
import eyaiis.lab1.erudit.model.Word;
import eyaiis.lab1.erudit.view.GameFieldComponent;
import eyaiis.lab1.erudit.view.LetterCellComparator;
import eyaiis.lab1.erudit.view.LetterCellComponent;
import eyaiis.lab1.erudit.view.ScoreComponent;
import eyaiis.lab1.erudit.view.UserLettersComponent;

/**
 * @author Q-YAA
 */
public class MainFrame extends JFrame {

    private static final String TITLE = "Scrabble game";

    private static final int DEFAULT_WIDTH = 900;
    private static final int DEFAULT_HEIGHT = 700;

    private Game game;

    private GameFieldComponent gameFieldComponent;

    private JButton endStepButton;

    public MainFrame(Game game) {
        this.game = game;

        setTitle(TITLE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gameFieldComponent = new GameFieldComponent(game);
        add(gameFieldComponent, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        endStepButton = new JButton("End step");
        endStepButton.addActionListener(new EndStepButtonActionListener());
        buttonPanel.add(endStepButton);
        add(buttonPanel, BorderLayout.SOUTH);

        add(new ScoreComponent(game), BorderLayout.EAST);
        add(new UserLettersComponent(game), BorderLayout.NORTH);

        game.startGame();
        refresh();
    }

    private void refresh() {
        gameFieldComponent.refreshGameField();
        repaint();
    }

    private class EndStepButtonActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            endStepButton.setEnabled(false);

            List<LetterCellComponent> chosenLetterCell = gameFieldComponent.getChosenLetterCell();

            if (isStepValid(chosenLetterCell)) {
                processStep(game, chosenLetterCell);
            }
            else {
                JOptionPane.showMessageDialog(MainFrame.this, "Correct your word!");
            }

            MainFrame.this.refresh();

            endStepButton.setEnabled(true);
        }
    }

    private boolean isStepValid(List<LetterCellComponent> chosenLetterCells) {
        return chosenLetterCells.isEmpty() ||
            (isChosenCellsRight(chosenLetterCells) && isChosenWordValid(composeWord(chosenLetterCells)));
    }

    private void processStep(Game game, List<LetterCellComponent> chosenLetterCell) {

        for (LetterCellComponent letterCell : chosenLetterCell) {
            letterCell.setAvailable(false);
            letterCell.setChoose(false);

            game.getGameFieldModel().setLetter(letterCell.getLetter(), letterCell.getPosition());
        }

        game.nextStep(composeWord(chosenLetterCell));
    }

    private Word composeWord(List<LetterCellComponent> chosenLetterCell) {
        sortLetterCell(chosenLetterCell);

        List<Letter> wordLetters = new ArrayList<Letter>();
        for (LetterCellComponent letterCellComponent : chosenLetterCell) {
            wordLetters.add(letterCellComponent.getLetter());

            System.out.print(letterCellComponent.getLetter().getName());
        }

        System.out.println("");

        return new Word(wordLetters);
    }

    private boolean isChosenWordValid(Word word) {
        return game.getDictionary().isWordExist(word.convertToString());
    }

    private boolean isChosenCellsRight(List<LetterCellComponent> letterCellComponentList) {
        boolean isCellListIncludeUnavailableCell = false;
        boolean isCellListIncludeAvailableCell = false;

        for (LetterCellComponent letterCellComponent : letterCellComponentList) {
            if (!letterCellComponent.isAvailable()) {
                isCellListIncludeUnavailableCell = true;
            }
            if (letterCellComponent.isAvailable()) {
                isCellListIncludeAvailableCell = true;
            }
        }

        return isCellListIncludeAvailableCell && isCellListIncludeUnavailableCell;
    }

    private void sortLetterCell(List<LetterCellComponent> letterCellComponentList) {
        Collections.sort(letterCellComponentList, new LetterCellComparator());
    }
}
