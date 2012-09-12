package eyaiis.lab1.erudit;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import eyaiis.lab1.erudit.graphics.GameFieldComponent;
import eyaiis.lab1.erudit.graphics.LetterCellComponent;
import eyaiis.lab1.erudit.model.Game;

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
        //Развернуть на весь экран
        //setExtendedState(JFrame.MAXIMIZED_BOTH);

        game.startGame();

        gameFieldComponent = new GameFieldComponent(game);
        add(gameFieldComponent, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        endStepButton = new JButton("End step");
        endStepButton.addActionListener(new EndStepButtonActionListener());
        buttonPanel.add(endStepButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public GameFieldComponent getGameFieldComponent() {
        return gameFieldComponent;
    }

    private class EndStepButtonActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            endStepButton.setEnabled(false);

            List<LetterCellComponent> chosenLetterCell = gameFieldComponent.getChosenLetterCell();

            for (LetterCellComponent letterCell : chosenLetterCell) {
                letterCell.setAvailable(false);
                letterCell.setChoose(false);
            }

            game.nextStep();
            gameFieldComponent.repaint();

            endStepButton.setEnabled(true);
        }
    }
}
