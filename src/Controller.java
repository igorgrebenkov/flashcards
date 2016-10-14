import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

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
    public Controller(FlashCard[] fc) {
        model = new Model(fc);
        view = new View(model, this);
        view.update();

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
            final JFileChooser fileChooser = new JFileChooser();
            final JFrame selectFrame = new JFrame("Select a file...");
            int returnVal = fileChooser.showOpenDialog(selectFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                flashCardSet = fileChooser.getSelectedFile();
                try {
                    createFlashCards(flashCardSet);
                }
                catch (IOException IOe) {
                    System.out.println("IO Exception!");
                }
            }
        }
    }

    private void createFlashCards(File file) throws IOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String readLine = "";
            String question = "";
            String answer = "";

            boolean concatQ = false;    // Flag for active question concatenation
            boolean concatA = false;    // Flag for active answer concatenation
            boolean endConcat = false;  // flag for ending concatenation

            // Read the file line by line
            while ((readLine = br.readLine()) != null) {

                if (readLine.charAt(0) == 'Q') {         // start reading a question
                    concatQ = true;
                } else if (readLine.charAt(0) == 'A') {  // start reading an answer
                    concatA = true;
                }

                // The '~' character indicates the end of a question or answer and signals end of concatenation.
                if (readLine.charAt(readLine.length() - 1) == '~') {
                    endConcat = true;
                }

                // Concatenate question lines if active
                if (concatQ) {
                    question = question + readLine;

                }

                // Concatenate answer lines if active
                if (concatA) {
                    answer = answer + readLine;
                }

                // Processing/flag setting for the end of a concatenation of a question or answer
                if (endConcat) {  // If done concatenating ('~' encountered)
                    if (concatQ) {
                        concatQ = false;
                        question = question.substring(0, question.length() - 1); // Trim the '~' character
                    } else if (concatA) {
                        concatA = false;
                        answer = answer.substring(0, answer.length() - 1);       // Trim the '~' character
                    }

                    endConcat = false;
                }
            }

            FlashCard card = new FlashCard(question, answer);

        }
        catch ( FileNotFoundException FNFe) {
            System.out.println("Cannot find file!");
        }
    }
}
