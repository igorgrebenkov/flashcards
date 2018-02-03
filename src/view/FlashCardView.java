package view;

import model.FlashCardModel;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.Boolean.*;

/**
 * The class <b>view.FlashCardView</b> shows the view of the FLashCards.
 * <p>
 * It extends JPanel.
 *
 * @author Igor Grebenkov
 */
public class FlashCardView extends JPanel {

    private Model model;
    private int currentCardIndex;         // The index of the card currently displayed
    private boolean isQuestion;           // Flag to indicate if the displayed card shows a question or answer
    private boolean isActive;             // Flag to indicate if the displayed card is in the active pile

    public final boolean QUESTION = TRUE; // Used to indicate displaying a question
    public final boolean ANSWER = FALSE;  // Used to indicated displaying an answer
    public final boolean CARD = TRUE;     // Used to indicate displaying from the active card pile
    public final boolean DISCARD = FALSE; // Used to indicate displaying from the discarded card pile


    /**
     * Constructor for view.FlashCardView.
     *
     * @param model      the model.Model
     */
    public FlashCardView(Model model) {
        this.model = model;
        setLayout(new BorderLayout());
        setFocusable(false);

        // Keeps this view.View from making ControlsView disappear when
        // view.View is resized and model.FlashCardModel text takes up more horizontal
        // space than the preferred size
        setMinimumSize(new Dimension(800, 320));
        //setPreferredSize(new Dimension(1250, 500));

        // Display a blank JEditorPane initially
        JEditorPane cardPane = new JEditorPane();
        cardPane.setText("");
        cardPane.setEditable(false);
        cardPane.setFocusable(false); // Ensures keyboard shortcuts always work
        cardPane.setLayout(new BorderLayout());
        cardPane.setBackground(new Color(0xFF, 0xFA, 0xCD));

        // Embed in JScrollPane to allow model.FlashCardModel contents to be larger than this view.View's current size
        // Not really necessary for a blank card, but keeps the view.View consistent when a card set is loaded
        JScrollPane cardScroller = new JScrollPane(cardPane);
        cardScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        cardScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        cardScroller.setFocusable(false);

        add(cardScroller);

        currentCardIndex = -1;
        isQuestion = true;
    }

    /**
     * Getter for the isActive flag.
     *
     * @return True if currently displaying active card; False otherwise.
     */
    public boolean getIsActive() { return isActive; }

    /**
     * Setter for the isActive flag.
     *
     * @param isActive the new state for isActive
     */
    public void setIsActive(boolean state) { isActive = state; }

    /**
     * Getter for the current card index
     *
     * @return the current model.FlashCardModel's index
     */
    public int getCurrentCardIndex() {
        return currentCardIndex;
    }

    /**
     * Setter for the current card index
     *
     * @param index the new index of the current model.FlashCardModel
     */
    public void setCurrentCardIndex(int index) {
        currentCardIndex = index;
    }

    /**
     * Displays the current model.FlashCardModel.
     *
     * @param operation controls whether to display a question or answer
     * @param cardPile  controls whether to display from active or discarded pile
     * @param index     the index of the card to display
     */
    public void displayCard(boolean operation, boolean cardPile, int index) {
        // Fetch whichever card set to display
        ArrayList<FlashCardModel> cardsToDisplay = cardPile ? model.getFlashCards() : model.getDiscardedCards();

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

        // Set flag to indicate if this card is a question based on operation type
        isQuestion = operation;
        // Set flag to indicate if this card is in the discarded pile
        isActive = cardPile;

        // JEditorPane displays the current model.FlashCardModel
        JEditorPane cardPane = new JEditorPane();

        cardPane.setSize(300, Integer.MAX_VALUE);
        cardPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, TRUE); // Allows setting font properties
        cardPane.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 26));
        cardPane.setContentType("text/html"); // allow HTML
        cardPane.setText("<html>" + displayString.substring(2) + "</html>");
        cardPane.setEditable(false);
        cardPane.setFocusable(false); // Ensures keyboard shortcuts always work
        cardPane.setBackground(new Color(0xFF, 0xFA, 0xCD));

        // Embed in JScrollPane to allow model.FlashCardModel contents to be larger than this view.View's current size
        JScrollPane cardScroller = new JScrollPane(cardPane);
        cardScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        cardScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.add(cardScroller, BorderLayout.CENTER);
        update();
    }

    /**
     * Advances forwards one card in the set by incrementing the currentCardIndex
     * and displaying the card in question.
     */
    public void nextCard() {
        // Get the relevant model.FlashCardModel list for bounds-checking
        ArrayList<FlashCardModel> fc = isActive ? model.getFlashCards() : model.getDiscardedCards();
        if (currentCardIndex != fc.size() - 1 && !fc.isEmpty()) {
            displayCard(QUESTION, isActive, ++currentCardIndex);
        }
    }

    /**
     * Advances backwards one card in the set by decrementing the currentCardIndex
     * and displaying the card in question.
     */
    public void prevCard() {
        // Get the relevant model.FlashCardModel list for bounds-checking
        ArrayList<FlashCardModel> fc = isActive ? model.getFlashCards() : model.getDiscardedCards();
        if (currentCardIndex != 0 && !fc.isEmpty()) {
            displayCard(QUESTION, isActive, --currentCardIndex);
        }
    }


    /**
     * Reveals the answer associated with this card.
     */
    public void revealAnswer() {
        if (currentCardIndex > -1 && isQuestion) { // Make sure there's something to reveal
            displayCard(ANSWER, isActive, currentCardIndex);     // Show active card
            isQuestion = false;
        } else {
            revealQuestion();
        }
    }

    /**
     * Updates the view.FlashCardView.
     */
    public void update() {
        revalidate();
        repaint();
    }

    /**
     * Reveals the question associated with this card.
     */
    private void revealQuestion() {
        if (currentCardIndex > -1) {  // Make sure there's something to reveal
                displayCard(QUESTION, isActive, currentCardIndex);
            isQuestion = true;
        }
    }
}
