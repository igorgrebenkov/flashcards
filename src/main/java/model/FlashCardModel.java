package model;

/**
 * The model.FlashCardModel class represents a flash card.
 * In addition to the question and answer variables, it stores
 * a count of how many times the user got the current question right.
 *
 * @author Igor Grebenkov
 */
public class FlashCardModel {
    private String question;
    private String answer;
    private int numCountRight;    // Number of times user got this card right
    private int cardIndex;        // The index of the card in its set
    /**
     * Constructor
     * @param question the question string
     * @param answer the answer string
     */
    public FlashCardModel(String question, String answer, int cardIndex) {
        this.question = question;
        this.answer = answer;
        numCountRight = 0;
        this.cardIndex = cardIndex;
    }

    /**
     * Getter for the question string.
     * @return the question string
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Setter for the question string.
     * @param question  the question string
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Getter for the answer string.
     * @return  the answer string
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Setter for the answer string.
     * @param answer  the answer string
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Getter for the card's index
     * @return the card's index
     */
    public int getCardIndex() {
        return cardIndex;
    }


    /**
     * Getter for number of times right counter.
     * @return counter for number of times right
     */
    public int getNumCountRight() {
        return numCountRight;
    }

    /**
     * Setter for the number of times right counter.
     * @param numCountRight counter for number of times right
     */
    public void setNumCountRight(int numCountRight) {
        this.numCountRight = numCountRight;
    }

    /**
     * Used to display appropriate labels in the view.View's JList
     * @return a string representation of the card's index in the set
     */
    @Override
    public String toString() {
        return question;
    }
}
