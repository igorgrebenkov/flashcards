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

        flashCardView = new FlashCardView(model, controller);
        add(flashCardView, BorderLayout.CENTER);

        JButton loadFile = new JButton("Load");
        loadFile.setFocusPainted(false);
        loadFile.addActionListener(controller);

    }
}
