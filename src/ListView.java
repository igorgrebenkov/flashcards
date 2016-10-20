import javax.swing.*;
import java.awt.*;

/**
 * The abstract class ListView provides a general constructor
 * for a JList in a JPanel used to display a list of FlashCards
 */
public abstract class ListView extends JPanel {
    protected Model model;
    protected JList cardList;

    /**
     * Constructor
     * @param model       the model
     * @param controller  the controller
     */
    @SuppressWarnings({"unchecked"})
    public ListView(Model model, Controller controller) {
        this.model = model;
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        cardList = new JList();
        cardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cardList.setLayoutOrientation(JList.VERTICAL);
        cardList.setVisibleRowCount(-1); // display max items possible in space
        cardList.addListSelectionListener(controller);
        cardList.setFixedCellWidth(100);

        // Embed JList in a JScrollPane
        JScrollPane listScroller = new JScrollPane(cardList);
        add(listScroller);
    }

    // Updates the list view
    abstract public void update();
}