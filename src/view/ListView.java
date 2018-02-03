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

        // Init new JList
        cardList = new JList();
        cardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cardList.setLayoutOrientation(JList.VERTICAL);
        cardList.setVisibleRowCount(-1); // display max items possible in space
        cardList.addListSelectionListener(controller);
        cardList.setFixedCellWidth(150);
        cardList.addKeyListener(controller);
        cardList.setFont(new Font("Arial", Font.PLAIN, 12));
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.DARK_GRAY));

        // Embed JList in a JScrollPane
        JScrollPane listScroller = new JScrollPane(cardList);

        // Keeps view.ListView from decreasing width suddenly when resizing
        listScroller.setMinimumSize(new Dimension(200, 300));
        listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(listScroller, BorderLayout.CENTER);
    }

    /**
     * Returns the card set's JList.
     *
     * @return the card set's JList
     */
    public JList getCardList() {
        return cardList;
    }


    // Updates the list view
    abstract public void update();
}
