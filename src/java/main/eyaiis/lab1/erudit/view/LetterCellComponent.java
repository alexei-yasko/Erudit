package eyaiis.lab1.erudit.view;

import eyaiis.lab1.erudit.model.Letter;
import eyaiis.lab1.erudit.model.Position;

import javax.swing.JButton;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Component for display and set letter on games field.
 *
 * @author Q-YAA
 */
public class LetterCellComponent extends JButton {
    private Position position;

    private Letter letter;

    private boolean isAvailable = true;
    private boolean isChoose = false;

    public LetterCellComponent(int width, int height, Position position) {
        this.position = position;

        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
    }

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        if (isChoose()) {
            graphics2D.setColor(Color.decode("#87CEEB"));
        } else {
            graphics2D.setColor(Color.decode("#FAEBD7"));
        }

        graphics2D.fillRect(0, 0, getWidth(), getHeight());

        graphics2D.setColor(Color.decode("#DEB887"));
        Font font = new Font("Courier", Font.BOLD, 40);
        graphics2D.setFont(font);

        if (letter != null) {
            graphics2D.drawString(
                letter.getName().toString(), getViewPositionX(font, graphics2D), getViewPositionY(font, graphics2D));
        }
    }

    private int getViewPositionX(Font font, Graphics2D graphics2D) {
        FontMetrics fontMetrics = getFontMetrics(font);
        Rectangle2D letterSize = fontMetrics.getStringBounds(letter.getName().toString(), graphics2D);
        return (int) (getWidth() - letterSize.getWidth()) / 2;
    }

    private int getViewPositionY(Font font, Graphics2D graphics2D) {
        FontMetrics fontMetrics = getFontMetrics(font);
        Rectangle2D letterSize = fontMetrics.getStringBounds(letter.getName().toString(), graphics2D);
        return (int) (getHeight() - letterSize.getHeight()) / 2 + +fontMetrics.getAscent();
    }
}
