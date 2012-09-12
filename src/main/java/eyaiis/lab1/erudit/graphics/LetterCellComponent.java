package eyaiis.lab1.erudit.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;

import eyaiis.lab1.erudit.model.Letter;

/**
 * Component for display and set letter on games field.
 *
 * @author Q-YAA
 */
public class LetterCellComponent extends JButton {
    private int cellNumber;

    private Letter letter;

    private boolean isAvailable = true;
    private boolean isChoose = false;

    public LetterCellComponent(int width, int height, int cellNumber) {
        this.cellNumber = cellNumber;

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

    public int getCellNumber() {
        return cellNumber;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        if (isChoose()) {
            graphics2D.setColor(Color.decode("#87CEEB"));
        }
        else {
            graphics2D.setColor(Color.decode("#FAEBD7"));
        }

        graphics2D.fillRect(0, 0, getWidth(), getHeight());

        graphics2D.setColor(Color.decode("#DEB887"));
        Font font = new Font("Courier", Font.BOLD, 40);
        graphics2D.setFont(font);

        if (letter != null) {
            graphics2D.drawString(letter.getName(), getLetterPositionX(font, graphics2D), getLetterPositionY(font, graphics2D));
        }
    }

    private int getLetterPositionX(Font font, Graphics2D graphics2D) {
        FontMetrics fontMetrics = getFontMetrics(font);
        Rectangle2D letterSize = fontMetrics.getStringBounds(letter.getName(), graphics2D);
        return (int) (getWidth() - letterSize.getWidth()) / 2;
    }

    private int getLetterPositionY(Font font, Graphics2D graphics2D) {
        FontMetrics fontMetrics = getFontMetrics(font);
        Rectangle2D letterSize = fontMetrics.getStringBounds(letter.getName(), graphics2D);
        return (int) (getHeight() - letterSize.getHeight()) / 2 + + fontMetrics.getAscent();
    }
}
