import javax.swing.*;
import java.awt.*;

/**
 * The class View is the main view into the app's UI.
 *
 * It extends JFrame.
 *
 * @author Igor Grebenkov
 */
public class View extends JFrame {
    private FlashCardView flashCardView;    // Reference to the FlashCardView
    private CardListView cardListView;      // Reference to the CardListView
    private CardListView discardedListview; //

    /**
     * Constructor for the View.
     * @param controller  the ControllerS
     */
    public View(Model model, Controller controller) {
        super("FlashCards");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);

        // Add view of FlashCards
        flashCardView = new FlashCardView(model, controller);
        add(flashCardView, BorderLayout.CENTER);
        flashCardView.setPreferredSize(new Dimension(1000, 380));

        // Add JList view of card set i
        cardListView = new CardListView(model, controller);
        add(cardListView, BorderLayout.EAST);

        // Add view of app controls
        add(new ControlView(controller), BorderLayout.SOUTH);

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

    public CardListView getCardListView() {
        return cardListView;
    }

    public CardListView getDiscardedListView() { return discardedListview; }

    /**
     * Updates the View (and all component views).
     */
    public void update() {
        flashCardView.update();
        cardListView.update();
    }
}
