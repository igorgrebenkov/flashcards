package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * The class <b>view.ControlView</b> contains all
 * control buttons for interacting with the app.
 * <p>
 * It extends JPanel.
 *
 * @author Igor Grebenkov
 */
public class ControlView extends JPanel {

    /**
     * Constructor
     *
     * @param controller the controller.Controller
     */
    public ControlView(Controller controller) {
        setFocusable(false);
        setBackground(Color.darkGray);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.insets = new Insets(1,1,1,1);
        setPreferredSize(new Dimension(1250, 0));

        ControlButton loadFile = new ControlButton("Load", "loadFile", controller);

        ControlButton revealAnswer = new ControlButton("Answer", "revealAnswer", controller);

        ControlButton discardCard = new ControlButton("Discard", "discard", controller);

        ControlButton unDiscardCard = new ControlButton("Undiscard", "unDiscard", controller);

        ControlButton nextCard = new ControlButton("Next", "nextCard", controller);

        ControlButton prevCard = new ControlButton("Prev", "prevCard", controller);

        add(loadFile, gc);
        gc.gridx++;
        add(revealAnswer, gc);
        gc.gridx++;
        add(prevCard, gc);
        gc.gridx++;
        add(nextCard, gc);
        gc.gridx++;
        add(discardCard, gc);
        gc.gridx++;
        add(unDiscardCard, gc);
    }
}
