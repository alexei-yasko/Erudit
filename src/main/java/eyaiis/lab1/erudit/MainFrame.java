package eyaiis.lab1.erudit;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import eyaiis.lab1.erudit.graphics.GameFieldComponent;

/**
 * @author Q-YAA
 */
public class MainFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    private GameFieldComponent gameFieldComponent;

    public MainFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gameFieldComponent = new GameFieldComponent();

        add(gameFieldComponent, BorderLayout.CENTER);
    }
}
