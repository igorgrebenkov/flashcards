public class DiscardedListView extends ListView {
    /**
     * Constructor
     * @param model       the model
     * @param controller  the controller
     */
    @SuppressWarnings({"unchecked"})
    public DiscardedListView(Model model, Controller controller) {
        super(model, controller);
    }


    /**
     * Updates the CardListView.
     */
    @SuppressWarnings({"unchecked"})
    public void update() {
        cardList.setListData(model.getDiscardedCards().toArray());
        revalidate();
        repaint();
    }
}
