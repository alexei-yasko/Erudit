package eyaiis.lab1.erudit.view;

import eyaiis.lab1.erudit.model.Game;
import eyaiis.lab1.erudit.model.Letter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;

/**
 * @author yaskoam
 */
public class UserLettersComponent extends JPanel {

    private static final int BORDER_SIZE = 2;
    private static final Font FONT = new Font("Verdana", Font.BOLD, 20);

    private Game game;

    private JPanel lettersPanel;

    public UserLettersComponent(Game game) {
        this.game = game;

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(createLabel("Your letters: ", FONT));

        lettersPanel = new JPanel();
        lettersPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        add(lettersPanel);

        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, BORDER_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        lettersPanel.removeAll();
        setLettersOnThePanel(lettersPanel, game);

        super.paintComponent(g);
    }

    private void setLettersOnThePanel(JPanel lettersPanel, Game game) {
        for (Letter letter : game.getCurrentUser().getLatterList()) {
            lettersPanel.add(createLabel(letter.getName(), FONT));
        }
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }
}
