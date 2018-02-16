package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

 /**
 * The class <b>view.MenuBar</b> is the main menu at the top of the program.
 * <p>
 * It extends JMenuBar.
 *
 * @author Igor Grebenkov
 */
public class MenuBar extends JMenuBar {
    Controller controller;

     /**
      * The constructor.
      * @param c the controller
      */
    public MenuBar(Controller c) {
        super();
        controller = c;
        setOpaque(true);
        setBackground(Color.BLUE);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.getAccessibleContext().setAccessibleDescription("The file menu.");
        add(fileMenu);

        JMenuItem importXMLItem = new JMenuItem("Import XML", KeyEvent.VK_S);
        importXMLItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        importXMLItem.getAccessibleContext().setAccessibleDescription(
                "Create a set of flashcards from an XML file.");
        importXMLItem.addActionListener(controller);
        importXMLItem.setActionCommand("importXML");

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK));
        saveItem.getAccessibleContext().setAccessibleDescription(
                "Save the current state of the flashcard set.");
        saveItem.addActionListener(controller);
        saveItem.setActionCommand("save");

        JMenuItem saveAsItem = new JMenuItem("Save As");
        saveAsItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_3, ActionEvent.ALT_MASK));
        saveAsItem.getAccessibleContext().setAccessibleDescription(
                "Save the current state of the flashcard set as a new file.");
        saveAsItem.addActionListener(controller);
        saveAsItem.setActionCommand("saveAs");

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_4, ActionEvent.ALT_MASK));
        loadItem.getAccessibleContext().setAccessibleDescription(
                "Load the state of a flash card set from file.");
        loadItem.addActionListener(controller);
        loadItem.setActionCommand("load");

        fileMenu.add(importXMLItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(loadItem);

        JMenu helpMenu = new JMenu("Help");
        fileMenu.setMnemonic(KeyEvent.VK_H);
        fileMenu.getAccessibleContext().setAccessibleDescription("The help menu.");
        add(helpMenu);

        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_5, ActionEvent.ALT_MASK));
        helpItem.getAccessibleContext().setAccessibleDescription(
                "Open the help document.");
        helpItem.addActionListener(controller);
        helpItem.setActionCommand("help");

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_6, ActionEvent.ALT_MASK));
        aboutItem.getAccessibleContext().setAccessibleDescription(
                "Open the about page.");
        aboutItem.addActionListener(controller);
        aboutItem.setActionCommand("about");

        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);
    }
}
