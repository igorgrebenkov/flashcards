/**
 * The class CardListView displays a list of FlashCards in a JList.
 */
class CardListView extends ListView {
    /**
     * Constructor
     * @param model       the model
     * @param controller  the controller
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
        revalidate();
        repaint();
    }
}
