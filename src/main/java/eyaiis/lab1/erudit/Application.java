package eyaiis.lab1.erudit;

import eyaiis.lab1.erudit.model.Game;

/**
 * @author Q-YAA
 */
public class Application {

    public static void main(String[] args) {
        Game game = new Game();

        MainFrame mainFrame = new MainFrame(game);
        mainFrame.setVisible(true);

        game.startGame();
    }
}
