import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
    public final boolean CARD = TRUE;
    public final boolean DISCARD = FALSE;


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
        setPreferredSize(new Dimension(900, 300));
        currentCardIndex = -1;
    }

    /**
     * Getter for the current card index
     * @return the current FlashCard's index
     */
    public int getCurrentCardIndex() {
        return currentCardIndex;
    }

    /**
     * Setter for the current card index
     * @param index the new index of the current FlashCard
     */
    public void setCurrentCardIndex(int index) {
        currentCardIndex = index;
    }

    /**
     * Displays the current FlashCard.
     */
    public void displayCard(boolean operation, boolean cardPile, int index) {
        // Fetch whichever card set to display
        ArrayList<FlashCard> cardsToDisplay = cardPile ? model.getFlashCards() : model.getDiscardedCards();

        // Update currentCardIndex
        currentCardIndex = index;

        // Prevent loading an empty flash card set
        if (cardsToDisplay.isEmpty()) {
            throw new NullPointerException("NullPointerException.");
        }

        // Clear previous flash card
        removeAll();

        // Display question or answer based on value of operation
        String displayString = operation ?
                cardsToDisplay.get(currentCardIndex).getQuestion() :
                cardsToDisplay.get(currentCardIndex).getAnswer();


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
            displayCard(QUESTION, CARD, ++currentCardIndex);
        }
    }

    /**
     * Advances backwards one card in the set by decrementing the currentCardIndex
     * and displaying the card in question.
     */
    public void prevCard() {
        if (currentCardIndex != 0 &&
                !model.getFlashCards().isEmpty()) {
            displayCard(QUESTION, CARD, --currentCardIndex);
        }
    }

    /**
     * Reveals the question associated with this card.
     */
    public void revealQuestion() {

        displayCard(QUESTION, CARD, currentCardIndex);
    }

    /**
     * Reveals the answer associated with this card.
     */
    public void revealAnswer() {
        displayCard(ANSWER, CARD, currentCardIndex);
    }

    /**
     * Updates the FlashCardView.
     */
    public void update() {
        revalidate();
        repaint();
    }
}
