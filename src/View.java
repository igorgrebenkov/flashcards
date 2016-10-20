import javax.swing.*;
import javax.swing.border.Border;
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
    private CardListView cardListView;      // Reference to the view of a JList of FlashCards
    private DiscardedListView discardedListView; // Reference to the view of a JList of discarded FlashCards

    /**
     * Constructor for the View.
     * @param model       the Model
     * @param controller  the Controller
     */
    public View(Model model, Controller controller) {
        super("FlashCards");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(800, 400));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        JPanel leftView = new JPanel();
        leftView.setLayout(new BoxLayout(leftView, BoxLayout.Y_AXIS));

        // Add view of FlashCards
        flashCardView = new FlashCardView(model, controller);
        leftView.add(flashCardView);

        // Add view of app controls
        ControlView controlView = new ControlView(controller);
        leftView.add(controlView);

        JPanel rightView = new JPanel();
        rightView.setLayout(new BoxLayout(rightView, BoxLayout.Y_AXIS));

        // Add JList view of current FlashCard set
        cardListView = new CardListView(model, controller);
        rightView.add(cardListView);

        // Add JList view of discarded FlashCards
        discardedListView = new DiscardedListView(model, controller);
        rightView.add(discardedListView);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.8;
        c.weighty = 1;
        add(leftView, c) ;

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.2;
        c.weighty = 1;
        add(rightView, c);

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

    public DiscardedListView getDiscardedListView() { return discardedListView; }

    /**
     * Updates the View (and all component views).
     */
    public void update() {
        flashCardView.update();
        cardListView.update();
        discardedListView.update();
    }
}
