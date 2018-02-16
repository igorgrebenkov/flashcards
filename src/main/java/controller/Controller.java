package controller;

import model.*;
import view.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import org.jdom2.input.SAXBuilder;
import org.jdom2.*;

/**
 * The class <b>controller.Controller</b> handles Events.
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
    private JFileChooser fileChooser;
    private String lastSavePath;

    /**
     * Constructor.
     */
    public Controller() {
        model = new Model(new ArrayList<>());
        view = new View(model, this);

        final File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser = new JFileChooser(workingDirectory);
        lastSavePath = null;
    }

    /**
     * ActionListener for events.
     *
     * @param e the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        // Handles actions associated with JButtons
        if (e.getSource() instanceof JButton) {
            switch (action) {
                case "loadFile":
                    importXMLAction();
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

        if (e.getSource() instanceof JMenuItem) {
            switch (action) {
                case "importXML":
                    importXMLAction();
                    break;
                case "save":
                    saveAction();
                    break;
                case "saveAs":
                    saveAsAction();
                    break;
                case "load":
                    loadAction();
                    break;
                case "help":
                    break;
                case "about":
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
                case 'j':
                    nextButtonAction();
                    break;
                case 'k':
                    prevButtonAction();
                    break;
                case 'a':
                    view.getFlashCardView().revealAnswer();
                    break;
                case 'i':
                    e.consume();
                    view.getTextArea().requestFocus();
                    break;
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
     * List selection listener for JList card set view.View.
     *
     * @param e the ListSelectionEvent
     */
    public void valueChanged(ListSelectionEvent e) {
        // Handles displaying the selected model.FlashCardModel
        if (e.getSource() instanceof JList) {
            JList source = (JList) e.getSource();
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
    }

    /**
     * Action for changing focus between lists with the 'f' key.
     *
     * @param source the event source
     */
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
     * Loads an XML file and turns it into a set of FlashCards.
     */
    private void importXMLAction() {
        String filePath;
        if ((filePath = getOpenFilePath()) != null) {
            File flashCardSet = new File(filePath);
            try {
                if (!model.getFlashCards().isEmpty() || !model.getDiscardedCards().isEmpty()) {
                    model.setFlashCards(new ArrayList<>());
                    model.setDiscardedCards(new ArrayList<>());
                }

                // Create a model.FlashCardModel set and set the model
                model.setFlashCards(createFlashCards(flashCardSet));
                // Display the first card
                view.getFlashCardView().displayCard(view.getFlashCardView().QUESTION,
                        view.getFlashCardView().CARD, 0);
                view.getCardListView().getCardList().requestFocus();
                view.update();
                view.getCardListView().getCardList().setSelectedIndex(0);

                // Clear last save path since we're loading a new set.
                // TODO: dialog asking if user wants to discard current set if one exists
                lastSavePath = null;
            } catch (IOException IOe) {
                System.err.println("IOException: " + IOe.getMessage());
            }
        }
    }

    /**
     * Action for saving the current model without selecting a new filename.
     */
    public void saveAction() {
        if (lastSavePath != null) {
            try {
                FileOutputStream fileOut = new FileOutputStream(lastSavePath);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(model);
                out.close();
                fileOut.close();
            } catch (IOException IOe) {
                // TODO: Dialog for error?
                System.err.println("IOException: " + IOe.getMessage());
            }
        }
    }

    /**
     * Action for saving (as) the current model to file.
     */
    public void saveAsAction() {
        String filePath;

        if ((filePath = getSaveFilePath()) != null) {
            try {
                FileOutputStream fileOut = new FileOutputStream(filePath);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(model);
                out.close();
                fileOut.close();

                // Update the current save path
                // TODO: Dialog asking if user wants to discard current set if any
                lastSavePath = filePath;
            } catch (IOException IOe) {
                System.err.println("IOException: " + IOe.getMessage());
            }
        }
    }

    /**
     * Action for loading model from file.
     */
    public void loadAction() {
        String filePath;

        if ((filePath = getOpenFilePath()) != null) {
            try {
                FileInputStream fileIn = new FileInputStream(filePath);
                ObjectInputStream in = new ObjectInputStream(fileIn);

                // Update the model in the Controller and relevant views
                model = (Model) in.readObject();
                view.getFlashCardView().setModel(model);
                view.getCardListView().setModel(model);
                view.getDiscardedListView().setModel(model);

                in.close();
                fileIn.close();

                // Update view
                view.update();
                view.getFlashCardView().displayCard(view.getFlashCardView().QUESTION,
                        view.getFlashCardView().CARD, 0);
                view.getCardListView().getCardList().requestFocus();
                view.getCardListView().getCardList().setSelectedIndex(0);
                // Update path
                lastSavePath = filePath;
            } catch (IOException IOe) {
                System.err.println("IOException: " + IOe.getMessage());
            } catch (ClassNotFoundException CNFe) {
                System.err.print("ClassNotFoundException: " + CNFe.getMessage());
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
     * card from the model.FlashCardModel set and putting it in the discard pile.
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
                int discardSelectedIndex = view.getDiscardedListView().getCardList().getSelectedIndex();

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
                if (model.getDiscardedCards().size() >= 1) {
                    view.getDiscardedListView().getCardList().setSelectedIndex(discardSelectedIndex);
                }
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
     * card from the discarded model.FlashCardModel set and putting it in the active pile.
     * <p>
     * Then, it updates the view to reflect the change.
     */
    private void unDiscardAction() {
        // Returns the current card to the discard pile
        try {
            int unDiscardIndex = view.getDiscardedListView().getCardList().getSelectedIndex();

            // Un-discard the model.FlashCardModel and save the index it was really returned to.
            model.unDiscardFlashCard(unDiscardIndex);

            int cardListDisplayIndex = view.getCardListView().getCardList().getSelectedIndex();
            // If we're un-discarding anything but the last card, show the next card
            // Else, show the previous card (since there is no next card).
            int unDiscardDisplayIndex = unDiscardIndex < model.getDiscardedCards().size() ?
                    unDiscardIndex : unDiscardIndex-1;

            // Pick which card we display based on whether the un-discard list will be empty.
            if (model.getDiscardedCards().size() >= 1) {
                view.getFlashCardView().setCurrentCardIndex(unDiscardDisplayIndex);
                view.getFlashCardView().displayCard(view.getFlashCardView().QUESTION,
                        view.getFlashCardView().DISCARD,
                        unDiscardDisplayIndex);
                view.update();
                view.getCardListView().getCardList().setSelectedIndex(cardListDisplayIndex);
                view.getDiscardedListView().getCardList().setSelectedIndex(unDiscardDisplayIndex);
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
     * @return an ArrayList of model.FlashCardModel objects
     * @throws IOException
     */
    private ArrayList<FlashCardModel> createFlashCards(File file) throws IOException {
        ArrayList<FlashCardModel> flashCards = new ArrayList<>();
        String question;
        String answer;
        String title;

        SAXBuilder saxBuilder = new SAXBuilder();

        try {
            Document document = saxBuilder.build(file);
            Element rootNode = document.getRootElement();
            List<Element> cards = rootNode.getChildren();

            for (int i = 0; i < cards.size(); i++) {
                Element cardContents = cards.get(i);
                question = cardContents.getChild("question").getValue().trim();
                answer = cardContents.getChild("answer").getValue().trim();

                try {
                    title = cardContents.getChild("title").getValue();
                } catch (NullPointerException e) {
                    title = null;
                }

                // If the user didn't supply a title, we leave it null.
                if (title != null) {
                    flashCards.add(new FlashCardModel(title, question, answer, i));
                } else {
                    flashCards.add(new FlashCardModel(question, answer, i));
                }
            }
        } catch (JDOMException e) {
            System.err.println("JDOMException: " + e.getMessage());
        }
        return flashCards;
    }

    /*
     * Helper method that opens a JFileChooser to select a file path for opening a file.
     */
    public String getOpenFilePath() {
        final JFrame frame = new JFrame();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            return null;
        }
    }
     /*
     * Helper method that opens a JFileChooser to select a file path for saving a file.
     */
    public String getSaveFilePath() {
        final JFrame frame = new JFrame();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            return null;
        }
    }

}
