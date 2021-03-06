package view;

import controller.Controller;
import model.Model;

/**
 * The class <b>view.DiscardedListView</b> provides a view of the list of discarded
 * FlashCards in a JList embedded in a JScrollPane.
 * <p>
 * It extends view.ListView.
 *
 * @author Igor Grebenkov
 */
public class DiscardedListView extends ListView {
    /**
     * Constructor
     *
     * @param model      the model
     * @param controller the controller
     */
    @SuppressWarnings({"unchecked"})
    public DiscardedListView(Model model, Controller controller) {
        super(model, controller);
    }


    /**
     * Updates the view.CardListView.
     */
    @SuppressWarnings({"unchecked"})
    public void update() {
        cardList.setListData(model.getDiscardedCards().toArray());
        revalidate();
        repaint();
    }
}
