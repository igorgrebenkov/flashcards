import javax.swing.*;
import java.awt.*;

/**
 * The view. Displays the FlashCardView, and the program buttons in two JPanels.
 *
 * It extends JFrame.
 *
 * @author Igor Grebenkov
 */
public class View extends JFrame {
    private Model model;                  // Reference to the model
    private FlashCardView flashCardView;  // Reference to the FlashCardView

    /**
     * Constructor for the View.
     * @param model       the Model
     * @param controller  the Controller
     */
    public View(Model model, Controller controller) {
        super("FlashCards");
        this.model = model;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);

        // Add view of FlashCards
        flashCardView = new FlashCardView(model, controller);
        add(flashCardView, BorderLayout.CENTER);

        // JButton for loading a FlashCard set from a file
        JButton loadFile = new JButton("Load");
        loadFile.setFocusPainted(false);
        loadFile.addActionListener(controller);
        loadFile.setActionCommand("loadFile");

        JButton revealQuestion = new JButton("Question");
        revealQuestion.setFocusPainted(false);
        revealQuestion.addActionListener(controller);
        revealQuestion.setActionCommand("revealQuestion");

        JButton revealAnswer = new JButton("Answer");
        revealAnswer.setFocusPainted(false);
        revealAnswer.addActionListener(controller);
        revealAnswer.setActionCommand("revealAnswer");

        JButton nextCard = new JButton("Next");
        nextCard.setFocusPainted(false);
        nextCard.addActionListener(controller);
        nextCard.setActionCommand("nextCard");

        JButton prevCard = new JButton("Prev");
        prevCard.setFocusPainted(false);
        prevCard.addActionListener(controller);
        prevCard.setActionCommand("prevCard");

        JButton update = new JButton("Update");
        update.addActionListener(controller);
        update.setActionCommand("update");

        // Add JPanel for buttons
        JPanel control = new JPanel();
        control.setBackground(Color.darkGray);
        control.add(loadFile);
        control.add(prevCard);
        control.add(nextCard);
        control.add(revealQuestion);
        control.add(revealAnswer);
        control.add(update);
        add(control, BorderLayout.SOUTH);

        // Finish him!
        pack();
        setResizable(true);
        setVisible(true);

    }

    /**
     * Getter for the FlashCardView;
     * @return the FlashCardView
     */
    public FlashCardView getFlashCardView() {
        return flashCardView;
    }

    /**
     * Updates the FlashCardView.
     */
    public void update() {
        flashCardView.update();
    }
}
