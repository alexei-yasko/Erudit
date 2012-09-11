package eyaiis.lab1.erudit.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

/**
 * Component for display and set letter on games field.
 *
 * @author Q-YAA
 */
public class LetterCellComponent extends JButton {

    private String letter = "";

    private int cellNumber;

    private boolean isAvailable = true;
    private boolean  isChoose = false;

    public LetterCellComponent(int width, int height, int cellNumber) {
        this.cellNumber = cellNumber;

        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
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
        Graphics2D graphics2D = (Graphics2D)g;

        if (isChoose()) {
            graphics2D.setColor(Color.decode("#87CEEB"));
        } else {
            graphics2D.setColor(Color.decode("#FAEBD7"));
        }

        graphics2D.fillRect(0, 0, getWidth(), getHeight());

        graphics2D.setColor(Color.decode("#DEB887"));
        graphics2D.setFont(new Font("Courier", Font.BOLD, 40));
        graphics2D.drawString(letter, 20, 20);
    }
}
