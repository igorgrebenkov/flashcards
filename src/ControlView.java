import javax.swing.*;
import java.awt.*;

/**
 * The class <b>ControlView</b> contains all
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
     * @param controller the Controller
     */
    public ControlView(Controller controller) {
        setFocusable(false);
        setBackground(Color.darkGray);

        ControlButton loadFile = new ControlButton("Load", "loadFile", controller);

        ControlButton revealAnswer = new ControlButton("Answer", "revealAnswer", controller);

        ControlButton discardCard = new ControlButton("Discard", "discard", controller);

        ControlButton unDiscardCard = new ControlButton("Undiscard", "unDiscard", controller);

        ControlButton nextCard = new ControlButton("Next", "nextCard", controller);

        ControlButton prevCard = new ControlButton("Prev", "prevCard", controller);

        add(loadFile);
        add(revealAnswer);
        add(nextCard);
        add(prevCard);
        add(discardCard);
        add(unDiscardCard);
    }
}
