package eyaiis.lab1.erudit.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;

import eyaiis.lab1.erudit.model.Letter;
import eyaiis.lab1.erudit.model.Position;

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

        RenderingHints renderingHints = graphics2D.getRenderingHints();
        renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHints(renderingHints);

        super.paintComponent(graphics2D);

        if (isChoose()) {
            graphics2D.setColor(Color.GREEN);
            graphics2D.fillOval(0, 0, getWidth() / 5, getWidth() / 5);
        }

        graphics2D.setColor(Color.decode("#1E90FF"));
        Font font = new Font("Comic Sans MS", Font.BOLD, 30);
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
