import javax.swing.*;

/**
 * The class <b>ControlButton</b> provides a common template
 * for creating the buttons in the ControlView
 *
 * It extends JButton
 */
public class ControlButton extends JButton {
    /**
     * Constructor.
     *
     * @param btnText       the button's text
     * @param actionCommand the action command for this button
     * @param controller    the Controller
     */
    public ControlButton(String btnText, String actionCommand, Controller controller) {
        super(btnText);
        setFocusPainted(false);
        addActionListener(controller);
        setActionCommand(actionCommand);
        setFont(this.getFont().deriveFont(13f));
    }
}
