
public class Main {
    public static void main(String[] args) {
        FlashCard card1 = new FlashCard("Question?", "Answer.");
        FlashCard card2 = new FlashCard("Question2?", "Answer2.");

        FlashCard[] flashCards = {card1, card2};

        Controller controller = new Controller(flashCards);

    }
}
