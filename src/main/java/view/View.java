package view;

import model.Model;
import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
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
        setMinimumSize(new Dimension(900, 522));
        setPreferredSize(new Dimension(1250, 725));

        Border emptyBorder = BorderFactory.createEmptyBorder();

        MenuBar menuBar = new MenuBar(controller);
        setJMenuBar(menuBar);

        /*********************** Set up Left JPanel in view.View  **********************/
        JPanel leftView = new JPanel(new GridBagLayout());
        GridBagConstraints leftViewGBC = new GridBagConstraints();
        leftViewGBC.fill = GridBagConstraints.BOTH;
        leftViewGBC.gridy = 0;
        leftViewGBC.gridx = 0;
        leftViewGBC.weightx = 1;

        flashCardView = new FlashCardView(model);

        JScrollPane textPane = new JScrollPane();
        textPane.setFocusable(true);
        textPane.setMinimumSize(new Dimension(800, 0));
        textArea = new JTextArea();
        textArea.setForeground(Color.GRAY);
        textArea.setTabSize(2);
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        textArea.setText(placeholderText);
        textArea.addKeyListener(controller);
        textArea.setLineWrap(true);
        textArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textArea.getText().equals(placeholderText) ||
                        textArea.getText().equals("")) {
                    textArea.setText("");
                    textArea.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {}
        });
        textPane.getViewport().setView(textArea);


        leftViewGBC.weighty = 1;
        JSplitPane textSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, flashCardView, textPane);
        textSplitPane.setBorder(emptyBorder);
        textSplitPane.setResizeWeight(0.8);
        textSplitPane.setDividerSize(2);
        BasicSplitPaneDivider divider = (BasicSplitPaneDivider) textSplitPane.getComponent(2);
        divider.setBorder(emptyBorder);
        leftView.add(textSplitPane, leftViewGBC);

        ControlView controlView = new ControlView(controller);
        controlView.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.DARK_GRAY));
        controlView.setMinimumSize(new Dimension(800, 30));

        leftViewGBC.gridy++;
        leftViewGBC.weighty = 0;
        leftView.add(controlView, leftViewGBC);
        /********************* End set up Left JPanel in view.View  ********************/


        /********************** Set up Right JPanel in view.View  **********************/
        JPanel rightView = new JPanel();

        rightView.setLayout(new BoxLayout(rightView, BoxLayout.Y_AXIS));
        rightView.setPreferredSize(new Dimension(200, getHeight()));

        cardListView = new CardListView(model, controller);
        discardedListView = new DiscardedListView(model, controller);

        JSplitPane cardListSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, cardListView, discardedListView);
        cardListSplitPane.setResizeWeight(0.5);
        cardListSplitPane.setDividerSize(2);
        divider = (BasicSplitPaneDivider) cardListSplitPane.getComponent(2);
        divider.setBorder(emptyBorder);
        rightView.add(cardListSplitPane);
        /******************** End set up Right JPanel in view.View  ********************/

        JSplitPane mainHorizontalSplitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, leftView, rightView);
        mainHorizontalSplitPane.setResizeWeight(0.8);
        mainHorizontalSplitPane.setDividerSize(2);
        divider = (BasicSplitPaneDivider) mainHorizontalSplitPane.getComponent(2);
        divider.setBorder(emptyBorder);
        add(mainHorizontalSplitPane);

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
