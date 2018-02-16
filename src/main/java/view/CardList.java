package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.text.Position;
import java.awt.*;
import java.awt.event.KeyEvent;


/**
 * The class <b>view.CardList</b> is a custom JList class used to display
 * the list of FlashCards.
 * <p>
 * It extends JList.
 *
 * @author Igor Grebenkov
 */
public class CardList extends JList {
    public CardList(Controller controller) {
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setLayoutOrientation(JList.VERTICAL);
        setVisibleRowCount(-1); // display max items possible in space
        addListSelectionListener(controller);
        setFixedCellWidth(150);
        addKeyListener(controller);
        setFont(new Font("Arial", Font.PLAIN, 12));
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "none");
    }

    @Override
    public int getNextMatch(String prefix, int startIndex, Position.Bias bias) {
        return -1;
    }
}
