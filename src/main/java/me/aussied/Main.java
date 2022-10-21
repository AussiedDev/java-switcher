package me.aussied;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/** java switcher
 * @author Aussied!
 */
public class Main {

    /** Main function to get the app running */
    public static void main(String[] args) {
        final boolean[] inSet = {false};

        /** Set look and feel to current GTK+ theme */
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        final File file = new File("/usr/lib/jvm");

        /** Window */
        JFrame frame = new JFrame("Java Version Switcher");
        frame.setTitle("Java Version Switcher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(null);

        /** Settings Panel */
        JPanel settingsPanel = new JPanel();
        settingsPanel.setBounds(6, 60, frame.getWidth() - 12, frame.getHeight() - 135);
        settingsPanel.setLayout(new GridLayout(4, 3));


        /** List of methods */
        JList methodList = new JList(new String[] {"Arch", "Ubuntu"});
        methodList.setSelectedIndex(1);
        methodList.setVisible(false);

        JLabel methodLabel = new JLabel("Method");
        methodLabel.setVisible(false);

        /** List of available javas */
        JList javaList = new JList(file.list());
        javaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        /** List of available javas but with scroll */
        JScrollPane javaScrollPane = new JScrollPane(javaList);
        javaScrollPane.setBounds(6, 60, frame.getWidth() - 12, frame.getHeight() - 135);
        javaScrollPane.getVerticalScrollBar().setUnitIncrement(6);

        /** Button on the bottom left */
        JButton leftButton = new JButton("Settings");
        leftButton.setBounds(6, frame.getHeight() - 70, 115, 34);
        leftButton.setFocusable(false);
        leftButton.setVisible(true);

        /** Button on the bottom right */
        JButton rightButton = new JButton("Quit");
        rightButton.setBounds(frame.getWidth() - 121, frame.getHeight() - 70, 115, 34);
        rightButton.setFocusable(false);
        rightButton.setVisible(true);


        /** When object from javaList clicked */
        javaList.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    System.out.println(javaList.getSelectedValue());
                    try {
                        /** Setting java version for ArchLinux */
                        if(methodList.getSelectedValue().equals("Arch")) {
                            Runtime.getRuntime().exec("archlinux-java set " + javaList.getSelectedValue());
                        } else {
                            /** Setting java version for UbuntuLinux */
                            Runtime.getRuntime().exec("sudo update-java-alternatives -s " + javaList.getSelectedValue());
                            Runtime.getRuntime().exec("export JAVA_HOME=/usr/lib/jvm/ " + javaList.getSelectedValue());
                            Runtime.getRuntime().exec("export PATH=$PATH:$JAVA_HOME");
                        }
                    } catch (IOException ex) { ex.printStackTrace(); }
                }
            }
        });

        /** When leftButton clicked */
        leftButton.addActionListener(e -> {
            inSet[0] = !inSet[0];
            boolean inSet2 = inSet[0] ? false : true;

            javaScrollPane.setVisible(inSet2);
            rightButton.setVisible(inSet2);
            methodList.setVisible(!inSet2);
            leftButton.setText(!inSet2 ? "Back" : "Settings");
            settingsPanel.setVisible(!inSet2);
            methodLabel.setVisible(!inSet2);

        });
        /** When rightButton clicked */
        rightButton.addActionListener(e -> System.exit(0));

        /** Add components to frame */
        settingsPanel.add(methodLabel);
        settingsPanel.add(methodList);
        frame.add(javaScrollPane);
        frame.add(rightButton);
        frame.add(leftButton);
        frame.add(settingsPanel);
        frame.repaint();
    }

}
