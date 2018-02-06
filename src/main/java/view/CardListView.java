package view;

import controller.Controller;
import model.Model;

/**
 * The class <b>view.CardListView</b> provides a view of the list of active
 * FlashCards in a JList embedded in a JScrollPane.
 * <p>
 * It extends view.ListView.
 *
 * @author Igor Grebenkov
 */
public class CardListView extends ListView {
    /**
     * Constructor
     *
     * @param model      the model
     * @param controller the controller
     */
    @SuppressWarnings({"unchecked"})
    public CardListView(Model model, Controller controller) {
        super(model, controller);
    }

    /**
     * Updates the view.CardListView.
     */
    @SuppressWarnings({"unchecked"})
    public void update() {
        cardList.setListData(model.getFlashCards().toArray());
        revalidate();
        repaint();
    }
}
