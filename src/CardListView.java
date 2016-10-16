import javax.swing.*;
import java.awt.*;

/**
 * The class CardListView displays a list of FlashCards in a JList.
 */
public class CardListView extends JPanel {
    private Model model;
    private JList cardList;

    @SuppressWarnings({"unchecked"})
    public CardListView(Model model, Controller controller) {
        this.model = model;
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        cardList = new JList();
        cardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cardList.setLayoutOrientation(JList.VERTICAL);
        cardList.setVisibleRowCount(-1); // display max items possible in space
        cardList.addListSelectionListener(controller);
        cardList.setFixedCellWidth(100);

        JScrollPane listScroller = new JScrollPane(cardList);
        add(listScroller);
    }

    /**
     * Updates the CardListView.
     */
    @SuppressWarnings({"unchecked"})
    public void update() {
        cardList.setListData(model.getFlashCards().toArray());
        revalidate();
        repaint();
    }
}
