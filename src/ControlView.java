import javax.swing.*;
import java.awt.*;

/**
 * The class <b>ControsView</b> contains all
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
        // Loads and creates FlashCard set from a file
        JButton loadFile = new JButton("Load");
        loadFile.setFocusPainted(false);
        loadFile.addActionListener(controller);
        loadFile.setActionCommand("loadFile");
        loadFile.setFont(loadFile.getFont().deriveFont(13f));

        // Reveals the answer (if in a question)
        JButton revealAnswer = new JButton("Answer");
        revealAnswer.setFocusPainted(false);
        revealAnswer.addActionListener(controller);
        revealAnswer.setActionCommand("revealAnswer");
        revealAnswer.setFont(revealAnswer.getFont().deriveFont(13f));

        // Flips to the next card
        JButton nextCard = new JButton("Next");
        nextCard.setFocusPainted(false);
        nextCard.addActionListener(controller);
        nextCard.setActionCommand("nextCard");
        nextCard.setFont(nextCard.getFont().deriveFont(13f));

        // Flips to the previous card
        JButton prevCard = new JButton("Prev");
        prevCard.setFocusPainted(false);
        prevCard.addActionListener(controller);
        prevCard.setActionCommand("prevCard");
        prevCard.setFont(prevCard.getFont().deriveFont(13f));

        JButton discardCard = new JButton("Discard");
        discardCard.setFocusPainted(false);
        discardCard.addActionListener(controller);
        discardCard.setActionCommand("discard");
        discardCard.setFont(discardCard.getFont().deriveFont(13f));

        JButton unDiscardCard = new JButton("Undiscard");
        unDiscardCard.setFocusPainted(false);
        unDiscardCard.addActionListener(controller);
        unDiscardCard.setActionCommand("unDiscard");
        unDiscardCard.setFont(unDiscardCard.getFont().deriveFont(13f));

        setBackground(Color.darkGray);
        add(loadFile);
        add(prevCard);
        add(nextCard);
        add(revealAnswer);
        add(discardCard);
        add(unDiscardCard);
    }
}
