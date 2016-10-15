import javafx.scene.web.HTMLEditor;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

import static java.lang.Boolean.*;
import static javax.swing.SwingConstants.CENTER;

/**
 * The class FlashCardView shows the view of the FLashCards.
 * <p>
 * It extends JPanel.
 */
public class FlashCardView extends JPanel {

    private Model model;
    private Stack<FlashCard> undoStack; // Used for flipping to the next/previous card
    public final boolean QUESTION = TRUE;
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
        setPreferredSize(new Dimension(800, 380));
        undoStack = new Stack<>();
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
    public void displayCard(boolean operation) {
        // Prevent loading an empty flash card set
        if (model.getFlashCards().isEmpty()) {
            throw new NullPointerException("Null Pointer Exception.");
        }

        // Clear previous flash card
        this.removeAll();
        String displayString;

        if (operation == QUESTION) {
            displayString = model.getFlashCards().get(0).getQuestion();
        } else {
            displayString = model.getFlashCards().get(0).getAnswer();
        }

        // JEditorPane displays the current FlashCard
        JEditorPane cardPane = new JEditorPane();
        // Allows setting font properties
        cardPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, TRUE);
        cardPane.setFont(new Font("Verdana", Font.PLAIN, 26));
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
     * Advances forwards one card in the set.
     * Does this by pushing the currently displayed FlashCard onto a stack.
     * Call to displayCard() subsequently updates the view and displays the next card.
     */
    public void nextCard() {
        if (model.getFlashCards().size() <= 1) {
            throw new NullPointerException("Null Pointer Exception. No next card.");
        }
        undoStack.push(model.getFlashCards().remove(0));
        displayCard(QUESTION);
    }

    /**
     * Advances backwards one card in the set.
     * Does this by popping last displayed FlashCard off the stack to the original ArrayList of Flash Cards.
     * Call to displayCard() subsequently updates the view and displays the previous card.
     */
    public void prevCard() {
        if (undoStack.isEmpty()) {
            throw new NullPointerException("Null Pointer Exception. No previous card.");
        }
        model.getFlashCards().add(0, undoStack.pop());
        displayCard(QUESTION);

    }

    public void revealQuestion() {
        displayCard(QUESTION);
    }

    public void revealAnswer() {
        displayCard(ANSWER);
    }
}
