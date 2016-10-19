import javafx.scene.web.HTMLEditor;

import javax.swing.*;
import java.awt.*;
import static java.lang.Boolean.*;

/**
 * The class FlashCardView shows the view of the FLashCards.
 * <p>
 * It extends JPanel.
 */
public class FlashCardView extends JPanel {

    private Model model;
    private int currentCardIndex;         // The index of the card currently displayed
    public final boolean QUESTION = TRUE; // Maybe enum instead?
    public final boolean ANSWER = FALSE;


    /**
     * Constructor for FlashCardView.
     *
     * @param model      the Model
     * @param controller the Controller
     */
    public FlashCardView(Model model, Controller controller) {
        this.model = model;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        currentCardIndex = -1;
    }

    /**
     * Updates the FlashCardView.
     */
    public void update() {
        revalidate();
        repaint();
    }

    /**
     * Displays the current FlashCard.
     */
    public void displayCard(boolean operation, int index) {
        // Update currentCardIndex
        currentCardIndex = index;

        // Prevent loading an empty flash card set
        if (model.getFlashCards().isEmpty()) {
            throw new NullPointerException("Null Pointer Exception.");
        }

        // Clear previous flash card
        this.removeAll();

        // Display question or answer based on value of operation
        String displayString;
        if (operation == QUESTION) {
            displayString = model.getFlashCards().get(currentCardIndex).getQuestion();
        } else {
            displayString = model.getFlashCards().get(currentCardIndex).getAnswer();
        }

        // JEditorPane displays the current FlashCard
        JEditorPane cardPane = new JEditorPane();

        cardPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, TRUE); // Allows setting font properties
        cardPane.setFont(new Font("Verdana", Font.PLAIN, 30));
        cardPane.setContentType("text/html"); // allow HTML
        cardPane.setText("<div>"
                + "<span>"
                + displayString
                + "</span>"
                + "</html>");
        cardPane.setEditable(false);

        this.add(cardPane, BorderLayout.CENTER);
        update();
    }

    /**
     * Advances forwards one card in the set by incrementing the currentCardIndex
     * and displaying the card in question.
     */
    public void nextCard() {
        if (currentCardIndex != model.getFlashCards().size() - 1 &&
                !model.getFlashCards().isEmpty()) {
            displayCard(QUESTION, ++currentCardIndex);
        }
    }

    /**
     * Advances backwards one card in the set by decrementing the currentCardIndex
     * and displaying the card in question.
     */
    public void prevCard() {
        if (currentCardIndex != 0 &&
                !model.getFlashCards().isEmpty()) {
            displayCard(QUESTION, --currentCardIndex);
        }
    }

    public void revealQuestion() {
        displayCard(QUESTION, currentCardIndex);
    }

    public void revealAnswer() {
        displayCard(ANSWER, currentCardIndex);
    }
}
