package eyaiis.lab1.erudit.view;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import eyaiis.lab1.erudit.model.Game;
import eyaiis.lab1.erudit.model.User;

/**
 * @author yaskoam
 */
public class ScoreComponent extends JPanel {

    public ScoreComponent(Game game) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for (User user : game.getUserList()) {
            add(new UserStatisticsComponent(user));
        }
    }
}
