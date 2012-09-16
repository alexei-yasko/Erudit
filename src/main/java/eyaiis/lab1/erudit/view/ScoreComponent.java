package eyaiis.lab1.erudit.view;

import eyaiis.lab1.erudit.model.Game;
import eyaiis.lab1.erudit.model.User;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

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
