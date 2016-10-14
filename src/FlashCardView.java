import javax.swing.*;
import java.awt.*;

/**
 * The class FlashCardView shows the view of the FLashCards.
 *
 * It extends JPanel.
 */
public class FlashCardView extends JPanel {

    private Model model;

    /**
     * Constructor for FlashCardView.
     * @param model       the Model
     * @param controller  the Controller
     */
    public FlashCardView(Model model, Controller controller) {
        this.model = model;

        setBackground(Color.WHITE);
    }

    /**
     * Updates the FlashCardView.
     */
    public void update() {
        repaint();
    }
}
