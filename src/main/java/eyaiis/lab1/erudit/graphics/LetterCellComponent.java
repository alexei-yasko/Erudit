package eyaiis.lab1.erudit.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;

/**
 * Component for display and set letter on games field.
 *
 * @author Q-YAA
 */
public class LetterCellComponent extends JButton {
    private int cellNumber;

    private String letter = "";

    private boolean isAvailable = true;
    private boolean isChoose = false;

    public LetterCellComponent(int width, int height, int cellNumber) {
        this.cellNumber = cellNumber;

        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter.substring(0, 1);
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

        graphics2D.drawString(letter, getLetterPositionX(font, graphics2D), getLetterPositionY(font, graphics2D));
    }

    private int getLetterPositionX(Font font, Graphics2D graphics2D) {
        FontMetrics fontMetrics = getFontMetrics(font);
        Rectangle2D letterSize = fontMetrics.getStringBounds(letter, graphics2D);
        return (int) (getWidth() - letterSize.getWidth()) / 2;
    }

    private int getLetterPositionY(Font font, Graphics2D graphics2D) {
        FontMetrics fontMetrics = getFontMetrics(font);
        Rectangle2D letterSize = fontMetrics.getStringBounds(letter, graphics2D);
        return (int) (getHeight() - letterSize.getHeight()) / 2 + + fontMetrics.getAscent();
    }
}
