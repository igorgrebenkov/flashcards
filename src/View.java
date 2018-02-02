import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * The class <b>View</b> is the main view into the app's UI.
 * <p>
 * It extends JFrame.
 *
 * @author Igor Grebenkov
 */
public class View extends JFrame {
    private FlashCardView flashCardView;    // Reference to the FlashCardView
    private CardListView cardListView;      // Reference to the view of a JList of FlashCards
    private DiscardedListView discardedListView; // Reference to the view of a JList of discarded FlashCards
    private JTextArea textArea; // Text area for user input

    /**
     * Constructor for the View.
     *
     * @param model      the Model
     * @param controller the Controller
     */
    public View(Model model, Controller controller) {
        super("FlashCards");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(826, 413)); // Approximately proportional to the preferredArea
        setPreferredSize(new Dimension(1050, 525));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        /*************** Set up key mappings for keyboard shortcuts ***************/
        // Get the "focus is in the window" input map for the root panel
        JRootPane rootPane = getRootPane();
        rootPane.setFocusable(true);
        rootPane.requestFocusInWindow();

        InputMap iMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        // Put the keystroke objects into the panel's input map under the identifier "test"
        iMap.put(KeyStroke.getKeyStroke('a'), "revealAnswer");
        iMap.put(KeyStroke.getKeyStroke('d'), "discardCard");

        // Get the ActionMap for the panel
        ActionMap aMap = rootPane.getActionMap();

        // Put the object into the panel's ActionMap
        aMap.put("revealAnswer", controller);
        aMap.put("discardCard", controller);
        /************* End set up key mappings for keyboard shortcuts *************/


        /*********************** Set up Left JPanel in View  **********************/
        JPanel leftView = new JPanel();
        leftView.setLayout(new BoxLayout(leftView, BoxLayout.Y_AXIS));

        // Add view of FlashCards
        flashCardView = new FlashCardView(model);
        leftView.add(flashCardView);

        // Add TextArea
        textArea = new JTextArea();
        textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        leftView.add(textArea);

        // Add view of app controls
        ControlView controlView = new ControlView(controller);
        JScrollPane controlPane = new JScrollPane(controlView);
        controlPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        controlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        leftView.add(controlPane);
        /********************* End set up Left JPanel in View  ********************/


        /********************** Set up Right JPanel in View  **********************/
        JPanel rightView = new JPanel();
        rightView.setLayout(new BoxLayout(rightView, BoxLayout.Y_AXIS));
        rightView.setPreferredSize(new Dimension(200, getHeight()));

        // Add JList view of current FlashCard set
        cardListView = new CardListView(model, controller);
        rightView.add(cardListView);

        // Add JList view of discarded FlashCards
        discardedListView = new DiscardedListView(model, controller);
        rightView.add(discardedListView);
        /******************** End set up Right JPanel in View  ********************/

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.8;
        c.weighty = 1;
        add(leftView, c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.2;
        c.weighty = 1;
        add(rightView, c);

        // Finish him!
        pack();
        setResizable(true);
        setVisible(true);
        setFocusable(false);
    }

    /**
     * Getter for the FlashCardView.
     *
     * @return the FlashCardView
     */
    public FlashCardView getFlashCardView() {
        return flashCardView;
    }

    /**
     * Getter for the CardListView.
     *
     * @return the CardListView
     */
    public CardListView getCardListView() {
        return cardListView;
    }

    /**
     * Getter for the DiscardedListView.
     *
     * @return the DiscardedListView
     */
    public DiscardedListView getDiscardedListView() {
        return discardedListView;
    }

    /**
     * Getter for the JTextArea.
     *
     * @return the JTextArea
     */
    public JTextArea getTextArea() { return textArea; }

    /**
     * Updates the View (and all component views).
     */
    public void update() {
        requestFocus();
        textArea.setText(null);
        flashCardView.update();
        cardListView.update();
        discardedListView.update();
    }
}
