package view;

import model.Model;
import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * The class <b>view.View</b> is the main view into the app's UI.
 * <p>
 * It extends JFrame.
 *
 * @author Igor Grebenkov
 */
public class View extends JFrame {
    private FlashCardView flashCardView;    // Reference to the view.FlashCardView
    private CardListView cardListView;      // Reference to the view of a JList of FlashCards
    private DiscardedListView discardedListView; // Reference to the view of a JList of discarded FlashCards
    private JTextArea textArea; // Text area for user input
    private static final String placeholderText = "Enter text here..."; // Placeholder text for TextArea

    /**
     * Constructor for the view.View.
     *
     * @param model      the model.Model
     * @param controller the controller.Controller
     */
    public View(Model model, Controller controller) {
        super("FlashCards");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(826, 413));
        setPreferredSize(new Dimension(1150, 625));
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
        iMap.put(KeyStroke.getKeyStroke('t'), "focusTextArea");
        iMap.put(KeyStroke.getKeyStroke('j'), "nextCard");
        iMap.put(KeyStroke.getKeyStroke('k'), "prevCard");

        // Get the ActionMap for the panel
        ActionMap aMap = rootPane.getActionMap();

        // Put the object into the panel's ActionMap
        aMap.put("revealAnswer", controller);
        aMap.put("focusTextArea", controller);
        aMap.put("nextCard", controller);
        aMap.put("prevCard", controller);
        /************* End set up key mappings for keyboard shortcuts *************/


        /*********************** Set up Left JPanel in view.View  **********************/
        JPanel leftView = new JPanel();
        leftView.setLayout(new BoxLayout(leftView, BoxLayout.Y_AXIS));

        // Add view of FlashCards
        flashCardView = new FlashCardView(model);
        leftView.add(flashCardView);

        // Add TextArea
        textArea = new JTextArea();
        textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        textArea.setPreferredSize(new Dimension(1150, 75));
        textArea.setForeground(Color.GRAY);
        textArea.setTabSize(2);
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        textArea.setText(placeholderText);
        textArea.addKeyListener(controller);
        textArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textArea.getText().equals(placeholderText)) {
                    textArea.setText("");
                    textArea.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textArea.getText().isEmpty()) {
                    textArea.setForeground(Color.GRAY);
                    textArea.setText(placeholderText);
                }
            }
        });
        leftView.add(textArea);

        // Add view of app controls
        ControlView controlView = new ControlView(controller);

        JScrollPane controlPane = new JScrollPane(controlView);
        controlPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        controlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        leftView.add(controlPane);
        /********************* End set up Left JPanel in view.View  ********************/


        /********************** Set up Right JPanel in view.View  **********************/
        JPanel rightView = new JPanel();

        rightView.setLayout(new BoxLayout(rightView, BoxLayout.Y_AXIS));
        rightView.setPreferredSize(new Dimension(200, getHeight()));

        // Add JList view of current model.FlashCardModel set
        cardListView = new CardListView(model, controller);
        rightView.add(cardListView);

        // Add JList view of discarded FlashCards
        discardedListView = new DiscardedListView(model, controller);
        rightView.add(discardedListView);
        /******************** End set up Right JPanel in view.View  ********************/

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
     * Getter for the view.FlashCardView.
     *
     * @return the view.FlashCardView
     */
    public FlashCardView getFlashCardView() {
        return flashCardView;
    }

    /**
     * Getter for the view.CardListView.
     *
     * @return the view.CardListView
     */
    public CardListView getCardListView() {
        return cardListView;
    }

    /**
     * Getter for the view.DiscardedListView.
     *
     * @return the view.DiscardedListView
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
     * Updates the view.View (and all component views).
     */
    public void update() {
        requestFocus();
        textArea.setText(null);
        flashCardView.update();
        cardListView.update();
        discardedListView.update();
    }
}
