package eyaiis.lab1.erudit.view;

import eyaiis.lab1.erudit.model.User;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * @author yaskoam
 */
public class UserStatisticsComponent extends JPanel {

    private static final int BORDER_SIZE = 2;
    private static final Font FONT = new Font("Verdana", Font.BOLD, 20);

    private User user;

    private JLabel userNameLabel;
    private JLabel userPointsLabel;

    public UserStatisticsComponent(User user) {
        this.user = user;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel userNamePanel = new JPanel();
        userNamePanel.add(createLabel("User name: ", FONT));
        userNameLabel = createLabel(user.getName(), FONT);
        userNamePanel.add(userNameLabel);

        JPanel userPointsPanel = new JPanel();
        userPointsPanel.add(createLabel("User points: ", FONT));
        userPointsLabel = createLabel(String.valueOf(user.getPoints()), FONT);
        userPointsPanel.add(userPointsLabel);
        userPointsLabel.setAlignmentX(RIGHT_ALIGNMENT);

        add(userNamePanel);
        add(userPointsPanel);

        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, BORDER_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        userNameLabel.setText(user.getName());
        userPointsLabel.setText(String.valueOf(user.getPoints()));

        super.paintComponent(g);
    }


    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }
}
