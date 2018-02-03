import java.util.ArrayList;

import static java.lang.Integer.min;

/**
 * The class <b>Model</b> stores an array of all the FlashCards in the current set. Also stores a pile
 * of discarded FlashCards from the current set. This allows the user to discard
 * FlashCards when they feel confident they know the answer well enough.
 *
 * @author Igor Grebenkov
 */
public class Model {
    //instance variables

    private ArrayList<FlashCard> flashCards;     // The list of FlashCards
    private ArrayList<FlashCard> discardedCards;  // The list of discarded FlashCards

    public Model(ArrayList<FlashCard> fc) {
        flashCards = fc;
        discardedCards = new ArrayList<>();
    }

    /**
     * Getter method for the main FlashCard ArrayList
     *
     * @return the main FlashCard array
     */
    public ArrayList<FlashCard> getFlashCards() {
        return flashCards;
    }

    /**
     * Setter method for the main FlashCard ArrayList
     *
     * @param flashCards the main FlashCard array
     */
    public void setFlashCards(ArrayList<FlashCard> flashCards) {
        this.flashCards = flashCards;
    }

    /**
     * Getter method for the discarded FlashCard ArrayList
     *
     * @return the list of discarded cards
     */
    public ArrayList<FlashCard> getDiscardedCards() {
        return discardedCards;
    }

    /**
     * Setter method for the discarded FlashCard ArrayList
     * @param discardedCards the list of discarded cards
     */
    public void setDiscardedCards(ArrayList<FlashCard> discardedCards) {
        this.discardedCards = discardedCards;
    }

    /**
     * Randomly shuffles the pile of FlashCards
     */
    public void shufflePile() {

    }

    /**
     * Puts a FlashCard into the discarded pile
     */
    public void discardFlashCard(int index) {
        discardedCards.add(flashCards.remove(index));
    }

    /**
     * Returns a FlashCard to the main pile from the discard pile
     *
     * @return the index we actually returned it to
     */
    public int unDiscardFlashCard(int index) {
        // We try to return the flashcard to its original position,
        // but if that indexed position no longer exists we place it at the end.
        int returnIndex = min(flashCards.size(),
                discardedCards.get(index).getCardIndex());

        flashCards.add(returnIndex, discardedCards.remove(index));

        return returnIndex;
    }

    /**
     * Returns all FlashCards to the main pile from the discard pile
     */
    public void undiscardAllFlashCards() {

    }
}
