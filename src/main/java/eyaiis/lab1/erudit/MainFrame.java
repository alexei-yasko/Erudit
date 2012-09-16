package eyaiis.lab1.erudit;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import eyaiis.lab1.erudit.model.Game;
import eyaiis.lab1.erudit.model.GameUtils;
import eyaiis.lab1.erudit.model.Letter;
import eyaiis.lab1.erudit.view.*;

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
                gameFieldComponent.refreshGameField();
            }
            else {
                JOptionPane.showMessageDialog(MainFrame.this, "Correct your word!");
            }

            MainFrame.this.refresh();

            endStepButton.setEnabled(true);
        }
    }

    private boolean isStepValid(List<LetterCellComponent> chosenLetterCell) {
        return chosenLetterCell.isEmpty() ||
            (isChosenCellRight(chosenLetterCell) && isChosenWordValid(composeWord(chosenLetterCell)));
    }

    private void processStep(Game game, List<LetterCellComponent> chosenLetterCell) {

        Map<Integer, Letter> newWordLetters = new HashMap<Integer, Letter>();

        for (LetterCellComponent letterCell : chosenLetterCell) {
            letterCell.setAvailable(false);
            letterCell.setChoose(false);

            newWordLetters.put(letterCell.getCellNumber(), letterCell.getLetter());
        }

        game.nextStep(newWordLetters);
    }

    private List<Letter> composeWord(List<LetterCellComponent> chosenLetterCell) {
        sortLetterCell(chosenLetterCell);

        List<Letter> word = new ArrayList<Letter>();
        for (LetterCellComponent letterCellComponent : chosenLetterCell) {
            word.add(letterCellComponent.getLetter());

            System.out.print(letterCellComponent.getLetter().getName());
        }

        System.out.println("");

        return word;
    }

    private boolean isChosenWordValid(List<Letter> word) {
        return GameUtils.isWordValid(word);
    }

    private boolean isChosenCellRight(List<LetterCellComponent> letterCellComponentList) {
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
