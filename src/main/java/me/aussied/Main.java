package me.aussied;

import javax.swing.*;
import java.io.*;

/** Crappy java switcher
 * @author Aussied
 */
public class Main {

    /** Main function to get the application running */
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

        /** Sets window theme to current GTK theme */
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");

        /** New window */
        JFrame frame = new JFrame("Java Switcher");

        /** Set close action to exit application */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /** Set window size */
        frame.setSize(500, 400);

        /** Make non resizable */
        frame.setResizable(false);

        /** Move the window to the center (kinda) of the screen. */
        frame.setLocationRelativeTo(null);

        /** Make window not hidden */
        frame.setVisible(true);

        /** Remove layout */
        frame.setLayout(null);

        /** New variable set to jvm directory */
        File file = new File("/usr/lib/jvm");

        /** Create a new file array for the amount of files in jvm directory */
        String[] files = file.list();

        /** Add options to list */
        JList list = new JList(files);

        /** Sets JList one option only (to select) */
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        /** Set action when an option gets selected */
        list.addListSelectionListener(listSelectionEvent -> {
            if(!listSelectionEvent.getValueIsAdjusting()) {
                System.out.println(list.getSelectedValue());
                try {
                    Runtime.getRuntime().exec("archlinux-java set " + list.getSelectedValue());
                } catch (IOException e) { e.printStackTrace(); }
            }
        });

        /** Create new scrollable list */
        JScrollPane scrollPane = new JScrollPane(list);

        /** Set size */
        scrollPane.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        /** Increase scroll speed */
        scrollPane.getVerticalScrollBar().setUnitIncrement(6);

        /** Add list including options */
        frame.add(scrollPane);

        /** Repaint the window to fix random problems from happening */
        frame.repaint();
    }

}
