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
    private CardListView cardListView;    // Reference to the CardListView
    private CardListView discardedListview; //

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
        flashCardView.setPreferredSize(new Dimension(1000, 380));

        // Add JList view of card set i
        cardListView = new CardListView(model, controller);
        add(cardListView, BorderLayout.EAST);

        // Add view of app controls
        add(new ControlsView(controller), BorderLayout.SOUTH);

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

    /**
     * Updates the View (and all component views).
     */
    public void update() {
        flashCardView.update();
        cardListView.update();
    }
}
