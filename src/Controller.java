import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The controller. Handles Events. Implements ActionListener.
 *
 * @author Igor Grebenkov
 */
public class Controller implements ActionListener {

    private Model model;
    private View view;

    /**
     * Constructor
     * @param model the Model
     * @param view the View
     */
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

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
        if (action.equals("Yes")) {

        }
    }
}
