import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * The class Controller handles Events. Implements ActionListener and ListSelectionListener.
 *
 * @author Igor Grebenkov
 */
public class Controller implements ActionListener, ListSelectionListener {

    private File flashCardSet;  // the set of flash cards in a txt file
    private Model model;
    private View view;

    /**
     * Constructor
     */
    public Controller() {
        model = new Model(new ArrayList<FlashCard>());
        view = new View(model, this);
        //view.update();
    }

    /**
     * ActionListener for events.
     *
     * @param e the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        // Handles loading a FlashCard set from a txt file
        if (action.equals("loadFile")) {
            final File workingDirectory = new File(System.getProperty("user.dir"));
            final JFileChooser fileChooser = new JFileChooser(workingDirectory);
            final JFrame selectFrame = new JFrame("Select a file...");
            int returnVal = fileChooser.showOpenDialog(selectFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                flashCardSet = fileChooser.getSelectedFile();
                try {
                    this.model.setFlashCards(createFlashCards(flashCardSet));
                    view.getFlashCardView().displayCard(view.getFlashCardView().QUESTION, 0);
                    view.update();
                } catch (IOException IOe) {
                    System.err.println("IOException: " + IOe.getMessage());
                }
            }
        }

        // Reveals the question for the current card
        if (action.equals("revealQuestion")) {
            view.getFlashCardView().revealQuestion();
        }

        // Reveals the answer for the current card
        if (action.equals("revealAnswer")) {
            view.getFlashCardView().revealAnswer();
        }

        // Flips to the previous card in the set
        if (action.equals("prevCard")) {
            view.getFlashCardView().prevCard();
        }

        // Flips to the next card in the set
        if (action.equals("nextCard")) {
            view.getFlashCardView().nextCard();
        }

        try {
            // Discards the current card to the discard pile
            if (action.equals("discard")) {
                // Get the index of the card to discard
                int discardIndex = view.getFlashCardView().getCurrentCardIndex();

                // Discard it from the model
                model.discardFlashCard(discardIndex);

                // If we're discarding anything but the last card, show the next card
                // Else, show the previous card (since there is no next card)
                if (discardIndex < model.getFlashCards().size() - 1) {
                    view.getFlashCardView().setCurrentCardIndex(discardIndex);
                    view.getFlashCardView().displayCard(view.getFlashCardView().QUESTION, discardIndex);
                } else {
                    view.getFlashCardView().setCurrentCardIndex(discardIndex - 1);
                    view.getFlashCardView().displayCard(view.getFlashCardView().QUESTION, discardIndex - 1);
                }
                view.update();
            }
        } catch (NullPointerException NPe) { // If there's one card left, we can't discard it
            // Maybe pop up a window here?
            System.err.println("NullPointerException: " + NPe.getMessage());
        }
    }

    /**
     * List selection listener for JList card set View
     *
     * @param e the ListSelectionEvent
     */
    public void valueChanged(ListSelectionEvent e) {
        JList source = (JList) e.getSource();

        // If a card has been selected, display it
        //  -- Ensures selected index is in the range of the
        //     Arraylist of FlashCards
        if (!e.getValueIsAdjusting() &&
                (source.getSelectedIndex() >= 0) &&
                (source.getSelectedIndex() < model.getFlashCards().size())) {
            view.getFlashCardView().displayCard(view.getFlashCardView().QUESTION,
                    source.getSelectedIndex());
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
        }
        return flashCards;
    }
}
