package eyaiis.lab1.erudit;

import java.util.Arrays;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import eyaiis.lab1.erudit.model.Bot;
import eyaiis.lab1.erudit.model.Game;
import eyaiis.lab1.erudit.model.User;

/**
 * @author Q-YAA
 */
public class Application {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("org.jb2011.lnf.beautyeye.BeautyEyeLookAndFeelCross");
            UIManager.put("RootPane.setupButtonVisible", false);
        }
        catch (ClassNotFoundException ignored) {
        }
        catch (InstantiationException ignored) {
        }
        catch (IllegalAccessException ignored) {
        }
        catch (UnsupportedLookAndFeelException ignored) {
        }

        Game game = new Game(Arrays.asList(
            new User("user"),
            new Bot("Computer"))
        );

        MainFrame mainFrame = new MainFrame(game);
        mainFrame.setVisible(true);
    }
}
