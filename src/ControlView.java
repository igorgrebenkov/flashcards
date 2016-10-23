import javax.swing.*;
import java.awt.*;

/**
 * The class <b>ControlsView</b> contains all
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

        ControlButton loadFile = new ControlButton("Load", "loadFile", controller);

        ControlButton revealAnswer = new ControlButton("Answer", "revealAnswer", controller);

        ControlButton discardCard = new ControlButton("Discard", "discard", controller);

        ControlButton unDiscardCard = new ControlButton("Undiscard", "unDiscard", controller);

        setBackground(Color.darkGray);
        add(loadFile);
        add(revealAnswer);
        add(discardCard);
        add(unDiscardCard);
    }
}
