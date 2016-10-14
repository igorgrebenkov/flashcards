/**
 * The model.
 *
 * Stores an array of all the FlashCards in the current set. Also stores a pile
 * of discarded FlashCards from the current set. This allows the user to discard
 * FlashCards when they feel confident they know the answer well enough.
 *
 * @author Igor Grebenkov
 */
public class Model {
    //instance variables

    private FlashCard[] flashCards;     // The list of FlashCards
    private FlashCard[] discardedPile;  // The list of discarded FlashCards

    public Model(FlashCard[] fc) {
        flashCards = fc;
    }

    /**
     * Getter method for the main FlashCard array
     * @return the main FlashCard array
     */
    public FlashCard[] getFlashCards() {
        return flashCards;
    }

    /**
     * Setter method for the main FlashCard array
     * @param flashCards  the main FlashCard array
     */
    public void setFlashCards(FlashCard[] flashCards) {
        this.flashCards = flashCards;
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

    }

    /**
     * Returns a FlashCard to the main pile from the discard pile
     */
    public void undiscardFlashCard(int index) {

    }

    /**
     * Returns all FlashCards to the main pile from the discard pile
     */
    public void undiscardAllFlashCards() {

    }
}
