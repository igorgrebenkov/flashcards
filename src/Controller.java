import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The controller. Handles Events. Implements ActionListener.
 *
 * @author Igor Grebenkov
 */
public class Controller implements ActionListener {

    private File flashCardSet;  // the set of flash cards in a txt file
    private Model model;
    private View view;

    /**
     * Constructor
     * @param fc the FlashCard set
     */
    public Controller() {
        model = new Model(new ArrayList<FlashCard>());
        view = new View(model, this);
        //view.update();

    }

    /**
     * Getter for the Model.
     * @return the Model
     */
    public Model getModel() {
        return model;
    }

    /**
     * Setter for the model.
     * @param model the Model
     */
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * Getter for the View.
     * @return the View
     */
    public View getView() {
        return view;
    }

    /**
     * Setter for the View.
     * @param view the View
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * ActionListener for events.
     * @param e
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
                    view.getFlashCardView().displayCard(view.getFlashCardView().QUESTION);
                    view.update();
                }
                catch (IOException IOe) {
                    System.out.println("IO Exception!");
                }
            }
        }

        if (action.equals("revealQuestion")) {
            view.getFlashCardView().revealQuestion();
        }

        if (action.equals("revealAnswer")) {
            view.getFlashCardView().revealAnswer();
        }

        if (action.equals("prevCard")) {
            view.getFlashCardView().prevCard();
        }

        if (action.equals("nextCard")) {
            view.getFlashCardView().nextCard();
        }

        if (action.equals("update")) {
            view.update();
        }
    }

    private ArrayList<FlashCard> createFlashCards(File file) throws IOException {
        ArrayList<FlashCard> flashCards = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String readLine;       // reads the current line of text
            String question = "";  // the FlashCard's question
            String answer = "";    // The FlashCard's answer

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
                    flashCards.add(new FlashCard(question,answer));
                    question = "";
                    answer = "";
                    haveQ = false;
                    haveA = false;
                }
            }
        }
        catch ( FileNotFoundException FNFe) {
            System.out.println("Cannot find file!");
        }
        return flashCards;
    }
}
