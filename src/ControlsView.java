import javax.swing.*;
import java.awt.*;

/**
 * The class ControlsView is a JPanel that contains all
 * control buttons for interacting with the app.
 */
public class ControlsView extends JPanel {

    /**
     * Constructor
     * @param controller the controller
     */
    public ControlsView(Controller controller) {
        // Loads and creates FlashCard set from a file
        JButton loadFile = new JButton("Load");
        loadFile.setFocusPainted(false);
        loadFile.addActionListener(controller);
        loadFile.setActionCommand("loadFile");

        // Reveals the question (if in an answer)
        JButton revealQuestion = new JButton("Question");
        revealQuestion.setFocusPainted(false);
        revealQuestion.addActionListener(controller);
        revealQuestion.setActionCommand("revealQuestion");

        // Reveals the answer (if in a questtion)
        JButton revealAnswer = new JButton("Answer");
        revealAnswer.setFocusPainted(false);
        revealAnswer.addActionListener(controller);
        revealAnswer.setActionCommand("revealAnswer");

        // Flips to the next card
        JButton nextCard = new JButton("Next");
        nextCard.setFocusPainted(false);
        nextCard.addActionListener(controller);
        nextCard.setActionCommand("nextCard");

        // Flips to the previous card
        JButton prevCard = new JButton("Prev");
        prevCard.setFocusPainted(false);
        prevCard.addActionListener(controller);
        prevCard.setActionCommand("prevCard");

        /*
        JButton update = new JButton("Update");
        update.addActionListener(controller);
        update.setActionCommand("update");
        */
        setBackground(Color.darkGray);
        add(loadFile);
        add(prevCard);
        add(nextCard);
        add(revealQuestion);
        add(revealAnswer);
        //add(update);
    }
}
