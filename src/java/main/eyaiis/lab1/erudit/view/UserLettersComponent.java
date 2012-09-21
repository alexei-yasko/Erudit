package eyaiis.lab1.erudit.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import eyaiis.lab1.erudit.model.Game;
import eyaiis.lab1.erudit.model.Letter;

/**
 * @author yaskoam
 */
public class UserLettersComponent extends JPanel {

    private static final int BORDER_SIZE = 2;
    private static final String LETTER_SEPARATOR = ". ";
    private static final Font FONT = new Font("Segoe Print", Font.BOLD, 20);

    private Game game;

    private List<JLabel> letterLabelList = new ArrayList<JLabel>();

    public UserLettersComponent(Game game) {
        this.game = game;

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(createLabel("Your letters: ", FONT));

        for (int i = 0; i < Game.getCardOnHandsQuantity(); i++) {
            JLabel label = createLabel("", FONT);
            add(label);
            add(createLabel(LETTER_SEPARATOR, FONT));
            letterLabelList.add(label);
        }

        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, BORDER_SIZE));
    }

    public void refreshLettersComponent() {
        for (int i = 0; i < game.getCurrentUser().getLetterList().size(); i++) {
            letterLabelList.get(i).setText(getTextForLetterLabel(game.getCurrentUser().getLetterList().get(i)));
        }

        for (int i = game.getCurrentUser().getLetterList().size(); i < letterLabelList.size(); i++) {
            letterLabelList.get(i).setText("");
        }
    }

    private JLabel createLetterLabel(Letter letter, Font font) {
        return createLabel(getTextForLetterLabel(letter), font);
    }

    private String getTextForLetterLabel(Letter letter) {
        StringBuilder builder = new StringBuilder();
        builder.append(letter.getName());
        builder.append(" : ");
        builder.append(letter.getLetterPoints());
//        builder.append(" pts)");

        return builder.toString();
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }
}
