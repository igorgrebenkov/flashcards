/**
 * The class <b>CardListView</b> provides a view of the list of active
 * FlashCards in a JList embedded in a JScrollPane.
 * <p>
 * It extends ListView.
 *
 * @author Igor Grebenkov
 */
class CardListView extends ListView {
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
     * Updates the CardListView.
     */
    @SuppressWarnings({"unchecked"})
    public void update() {
        cardList.setListData(model.getFlashCards().toArray());
        //cardList.setSelectedIndex(5);
        revalidate();
        repaint();
    }
}
