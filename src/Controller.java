import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

/**
 * The class <b>Controller</b> handles Events.
 * <p>
 * Implements ActionListener, ListSelectionListener and KeyListener.
 *
 * @author Igor Grebenkov
 */
public class Controller extends AbstractAction
        implements
        ActionListener,
        ListSelectionListener,
        KeyListener {
    private Model model;
    private View view;

    /**
     * Constructor.
     */
    public Controller() {
        model = new Model(new ArrayList<>());
        view = new View(model, this);
    }

    /**
     * ActionListener for events.
     *
     * @param e the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        // Handles actions associated with InputMap/ActionMap keyboard shortcuts
        // These work no matter where focus is
        if (e.getSource() instanceof JRootPane) {
            switch (action) {
                case "a":  // Toggles revealing answer/question
                    view.getFlashCardView().revealAnswer();
                    break;
                case "t":
                    view.getTextArea().requestFocus();
                    break;
                case "j":
                    nextButtonAction();
                    break;
                case "k":
                    prevButtonAction();
                    break;
            }
        }

        // Handles actions associated with JButtons
        if (e.getSource() instanceof JButton) {
            switch (action) {
                case "loadFile":
                    loadFileButtonAction();
                    break;
                case "revealAnswer":
                    view.getFlashCardView().revealAnswer();
                    break;
                case "nextCard":
                    nextButtonAction();
                    break;
                case "prevCard":
                    prevButtonAction();
                    break;
                case "discard":
                    discardAction();
                    break;
                case "unDiscard":
                    unDiscardAction();
                    break;
            }
        }
    }

    /**
     * The KeyListener method for key press events.
     * <p>
     * Used for Keyboard Shortcuts only relevant when a JList component is in focus.
     *
     * @param e the KeyEvent
     */
    public void keyPressed(KeyEvent e) {
        if (e.getSource() instanceof JList) {
            JList source = (JList) e.getSource();

            // These actions can only be performed when the discarded card JList is in focus
            char c = e.getKeyChar();
            switch (c) {
                case 'f':   // Switches focus between the two JLists
                    listFocusAction(source);
                    break;
                case 'd':   // Discards/un-discards cards (!!!MAKE THIS GLOBAL in JRootPane)
                    if (source == view.getDiscardedListView().getCardList()) {
                        unDiscardAction();
                    } else {
                        discardAction();
                    }
                    break;
            }
        }

        if (e.getSource() instanceof JTextArea) {
            char c = e.getKeyChar();
            if (c == KeyEvent.VK_ESCAPE) {
                view.getCardListView().getCardList().requestFocus();
            }
        }
    }

    /**
     * The KeyListener method for key release events
     *
     * @param e the KeyEvent
     */
    public void keyReleased(KeyEvent e) {}

    /**
     * The KeyListener method for key typed events
     *
     * @param e the KeyEvent
     */
    public void keyTyped(KeyEvent e) { }

    /**
     * List selection listener for JList card set View.
     *
     * @param e the ListSelectionEvent
     */
    public void valueChanged(ListSelectionEvent e) {
        JList source = (JList) e.getSource();

        // Handles displaying the selected FlashCard
        if (e.getSource() == view.getCardListView().getCardList()) {
            // If a card has been selected, display it
            //  -- Ensures selected index is in the range of the
            //     ArrayList of FlashCards
            if (!e.getValueIsAdjusting() &&
                    (source.getSelectedIndex() >= 0) &&
                    (source.getSelectedIndex() < model.getFlashCards().size())) {
                view.getFlashCardView().displayCard(view.getFlashCardView().QUESTION,
                        view.getFlashCardView().CARD,
                        source.getSelectedIndex());
            }
        } else {
            // Same as above for discarded cards
            if (!e.getValueIsAdjusting() &&
                    (source.getSelectedIndex() >= 0) &&
                    (source.getSelectedIndex() < model.getDiscardedCards().size())) {
                view.getFlashCardView().displayCard(view.getFlashCardView().QUESTION,
                        view.getFlashCardView().DISCARD,
                        source.getSelectedIndex());
            }

        }
    }

    public void listFocusAction(JList source) {
        if (source == view.getDiscardedListView().getCardList()) {
            int selectedIndex = view.getCardListView().getCardList().getSelectedIndex();
            view.getCardListView().getCardList().clearSelection();

            if (selectedIndex > 0) {
                view.getCardListView().getCardList().setSelectedIndex(selectedIndex);
            } else {
                view.getCardListView().getCardList().setSelectedIndex(0);
            }

            view.getCardListView().getCardList().requestFocus();
        } else {
            // Reject focus change if the list is empty
            if (model.getDiscardedCards().size() == 0) {
                return;
            }
            int selectedIndex = view.getDiscardedListView().getCardList().getSelectedIndex();
            view.getDiscardedListView().getCardList().clearSelection();

            if (selectedIndex > 0) {
                view.getDiscardedListView().getCardList().setSelectedIndex(selectedIndex);
            } else {
                view.getDiscardedListView().getCardList().setSelectedIndex(0);
            }

            view.getDiscardedListView().getCardList().requestFocus();
        }
    }

    /**
     * Loads a file in response to pressing the Load button and turns
     * it into a set of FlashCards used to update the model.
     */
    private void loadFileButtonAction() {
        // Handles loading a FlashCard set from a txt file
        final File workingDirectory = new File(System.getProperty("user.dir"));
        final JFileChooser fileChooser = new JFileChooser(workingDirectory);
        final JFrame selectFrame = new JFrame("Select a file...");
        int returnVal = fileChooser.showOpenDialog(selectFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File flashCardSet = fileChooser.getSelectedFile();
            try {
                if (!model.getFlashCards().isEmpty() || !model.getDiscardedCards().isEmpty()) {
                    model.setFlashCards(new ArrayList<>());
                    model.setDiscardedCards(new ArrayList<>());
                }

                // Create a FlashCard set and set the model
                model.setFlashCards(createFlashCards(flashCardSet));
                // Display the first card
                view.getFlashCardView().displayCard(view.getFlashCardView().QUESTION,
                        view.getFlashCardView().CARD, 0);
                // Focus on active card JList
                view.getCardListView().getCardList().requestFocus();
                view.update();
                view.getCardListView().getCardList().setSelectedIndex(0);
            } catch (IOException IOe) {
                System.err.println("IOException: " + IOe.getMessage());
            }
        }
    }

    /**
     * Actions for the Next button that displays the next card.
     */
    public void nextButtonAction() {
        view.getFlashCardView().nextCard();
        flipCardAction();
        view.getTextArea().setText(null);
    }

    /**
     * Actions for the Prev button that displays the prev card.
     */
    public void prevButtonAction() {
        view.getFlashCardView().prevCard();
        flipCardAction();
        view.getTextArea().setText(null);
    }

    /**
     * Actions associated with going to the next or previous card.
     */
    public void flipCardAction() {
        if (view.getFlashCardView().getIsActive()) {
            view.getCardListView().getCardList().setSelectedIndex(
                    view.getFlashCardView().getCurrentCardIndex());
            view.getCardListView().getCardList().ensureIndexIsVisible(
                    view.getFlashCardView().getCurrentCardIndex());
        } else {
            view.getDiscardedListView().getCardList().setSelectedIndex(
                    view.getFlashCardView().getCurrentCardIndex());
            view.getDiscardedListView().getCardList().ensureIndexIsVisible(
                    view.getFlashCardView().getCurrentCardIndex());
        }
    }

    /**
     * Actions for the Discard button that updates the model by removing a
     * card from the FlashCard set and putting it in the discard pile.
     * <p>
     * Then, it updates the view to reflect the change.
     */
    private void discardAction() {
        // Discards the current card to the discard pile
        try {
            // Can only discard if there is at least two cards
            if (model.getFlashCards().size() > 1) {
                // Get the index of the card to discard
                int discardIndex = view.getCardListView().getCardList().getSelectedIndex();

                // Discard it from the model
                model.discardFlashCard(discardIndex);

                // If we're discarding anything but the last card, show the next card
                // Else, show the previous card (since there is no next card)
                int displayIndex = discardIndex < model.getFlashCards().size() ?
                        discardIndex : discardIndex-1;

                view.getFlashCardView().setCurrentCardIndex(displayIndex);
                view.getFlashCardView().displayCard(view.getFlashCardView().QUESTION,
                        view.getFlashCardView().CARD,
                        displayIndex);
                view.update();
                view.getCardListView().getCardList().setSelectedIndex(displayIndex);
                view.getCardListView().getCardList().requestFocus();
            }
        } catch (NullPointerException NPe) { // If there's one card left, we can't discard it
            // Maybe pop up a window here?
            System.err.println("NullPointerException: " + NPe.getMessage());
        } catch (ArrayIndexOutOfBoundsException AIe) {
            System.err.println("ArrayIndexOutOfBoundsException: " + AIe.getMessage());
        } catch (IndexOutOfBoundsException IOBe) {
            System.err.println("IndexOutOfBoundsException: " + IOBe.getMessage());
        }
    }

    /**
     * Actions for the Undiscard button that updates the model by removing a
     * card from the discarded FlashCard set and putting it in the active pile.
     * <p>
     * Then, it updates the view to reflect the change.
     */
    private void unDiscardAction() {
        // Returns the current card to the discard pile
        try {
            int unDiscardIndex = view.getDiscardedListView().getCardList().getSelectedIndex();

            // Un-discard the FlashCard and save the index it was really returned to.
            model.unDiscardFlashCard(unDiscardIndex);

            int cardListDisplayIndex = view.getCardListView().getCardList().getSelectedIndex();
            // If we're un-discarding anything but the last card, show the next card
            // Else, show the previous card (since there is no next card).
            int unDiscardDisplayIndex = unDiscardIndex < model.getDiscardedCards().size() ?
                    unDiscardIndex : unDiscardIndex-1;

            // Pick which card we display based on whether the undiscard list will be empty.
            if (model.getDiscardedCards().size() >= 1) {
                view.getFlashCardView().setCurrentCardIndex(unDiscardDisplayIndex);
                view.getFlashCardView().displayCard(view.getFlashCardView().QUESTION,
                        view.getFlashCardView().DISCARD,
                        unDiscardDisplayIndex);
                view.update();
                view.getCardListView().getCardList().setSelectedIndex(cardListDisplayIndex);
                view.getDiscardedListView().getCardList().setSelectedIndex(unDiscardDisplayIndex);
                view.getDiscardedListView().getCardList().requestFocus();
            } else if (model.getDiscardedCards().size() == 0) {
                view.getFlashCardView().setCurrentCardIndex(cardListDisplayIndex);
                view.getFlashCardView().displayCard(view.getFlashCardView().QUESTION,
                        view.getFlashCardView().CARD,
                        cardListDisplayIndex);
                view.update();
                view.getCardListView().getCardList().setSelectedIndex(cardListDisplayIndex);
                view.getCardListView().getCardList().requestFocus();
            }

        } catch (NullPointerException NPe) { // If there's one card left, we can't discard it
            // Maybe pop up a window here?
            System.err.println("NullPointerException: " + NPe.getMessage());
        } catch (ArrayIndexOutOfBoundsException AIe) {
            System.err.println("ArrayIndexOutOfBoundsException: " + AIe.getMessage());
        } catch (IndexOutOfBoundsException IOBe) {
            System.err.println("IndexOutOfBoundsException: " + IOBe.getMessage());
        }
    }

    /**
     * Creates an ArrayList of FlashCards from a text file that is read line-by-line.
     * <p>
     * Used to update the model.
     *
     * @param file the input file to read
     * @return an ArrayList of FlashCard objects
     * @throws IOException
     */
    private ArrayList<FlashCard> createFlashCards(File file) throws IOException {
        ArrayList<FlashCard> flashCards = new ArrayList<>();
        try {
            int i = 0; // FlashCard index
            BufferedReader br = new BufferedReader(new FileReader(file));

            String readLine;       // reads the current line of text
            String question = "";  // the FlashCard's question
            String answer = "";    // The FlashCard's answer

            // Flags for if a question/answer have been found
            boolean haveQ = false, haveA = false;

            // Read the file line by line
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == 'Q') {         // start reading a question
                    question = question + readLine;
                    haveQ = true;
                } else if (readLine.charAt(0) == 'A') {  // start reading an answer
                    answer = answer + readLine;
                    haveA = true;
                }

                // We have a question and answer, so make a FlashCard and add it to the set
                if (haveQ && haveA) {
                    flashCards.add(new FlashCard(question, answer, i));
                    question = "";
                    answer = "";
                    haveQ = false;
                    haveA = false;
                    ++i;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println("StringIndexOutOfBoundsException: " + e.getMessage());
        }
        return flashCards;
    }
}
