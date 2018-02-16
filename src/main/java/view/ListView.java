package view;

import controller.Controller;
import model.Model;

import javax.swing.*;
import java.awt.*;

/**
 * The abstract class <b>view.ListView</b> provides a JPanel containing
 * a JList, embedded in a JScrollPane used to display a list of FlashCards
 * <p>
 * It extends JPanel.
 *
 * @author Igor Grebenkov
 */
public abstract class ListView extends JPanel {
    protected Model model;
    JList cardList;

    /**
     * Constructor
     *
     * @param model      the model
     * @param controller the controller
     */
    @SuppressWarnings({"unchecked"})
    public ListView(Model model, Controller controller) {
        this.model = model;
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        setFocusable(false);

        cardList = new CardList(controller);
        JScrollPane listScroller = new JScrollPane(cardList);
        listScroller.setMinimumSize(new Dimension(200, 0));
        listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(listScroller, BorderLayout.CENTER);
    }

    /**
     * Setter for the ListView Model
     * @param m the new model
     */
    public void setModel(Model m) { model = m; }

    /**
     * Returns the card set's JList.
     *
     * @return the card set's JList
     */
    public JList getCardList() {
        return cardList;
    }

    /**
     * Updates the list view
     */
    abstract public void update();
}
