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
        this.setLayout(new GridBagLayout());

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(640,380));
    }

    /**
     * Updates the FlashCardView.
     */
    public void update() {
        revalidate();
        repaint();
    }

    public void displayCard(int i) {
        //GridBagConstraints gbc = new GridBagConstraints();
        if (model.getFlashCards().isEmpty()) {
            throw new NullPointerException("Null Pointer Exception.");
        }

        JLabel flashCardText = new JLabel(model.getFlashCards().get(i).getQuestion());
        flashCardText.setFont(new Font("Verdana", 1, 20));
        this.add(flashCardText);
        update();
    }
}
