import javax.swing.*;
import java.awt.*;

/**
 * The view. Displays the FlashCardView, and the program buttons in two JPanels.
 *
 * It extends JFrame.
 *
 * @author Igor Grebenkov
 */
public class View extends JFrame {
    private Model model;                  // Reference to the model
    private FlashCardView flashCardView;  // Reference to the FlashCardView

    /**
     * Constructor for the View.
     * @param model       the Model
     * @param controller  the Controller
     */
    public View(Model model, Controller controller) {
        super("FlashCards");
        this.model = model;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);

        // Add view of FlashCards
        flashCardView = new FlashCardView(model, controller);
        add(flashCardView, BorderLayout.CENTER);

        // JButton for loading a FlashCard set from a file
        JButton loadFile = new JButton("Load");
        loadFile.setFocusPainted(false);
        loadFile.addActionListener(controller);
        loadFile.setActionCommand("loadFile");

        JButton update = new JButton("Update");
        update.addActionListener(controller);
        update.setActionCommand("update");

        // Add JPanel for buttons
        JPanel control = new JPanel();
        control.setBackground(Color.darkGray);
        control.add(loadFile);
        control.add(update);
        add(control, BorderLayout.SOUTH);

        // Finish him!
        pack();
        setResizable(true);
        setVisible(true);

    }

    /**
     * Getter for the FlashCardView;
     * @return the FlashCardView
     */
    public FlashCardView getFlashCardView() {
        return flashCardView;
    }

    /**
     * Updates the FlashCardView.
     */
    public void update() {
        flashCardView.update();
    }
}
