package eyaiis.lab1.erudit;

import java.util.Arrays;

import eyaiis.lab1.erudit.model.Game;
import eyaiis.lab1.erudit.model.User;

/**
 * @author Q-YAA
 */
public class Application {

    public static void main(String[] args) {
        Game game = new Game(Arrays.asList(
            new User("user1"),
            new User("Computer"))
        );

        MainFrame mainFrame = new MainFrame(game);
        mainFrame.setVisible(true);

        game.startGame();
    }
}
