package client;

import java.awt.Dimension;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import client.listeners.BtnListeners;
import client.listeners.ServerResponseListener;

public class GUI {
    private int width = 600;
    private int height = 600;
    private Color accentColor = new Color(247, 245, 245);
    private Color bgColor = new Color(255, 255, 255);

    private JFrame mainFrame;
    private JPanel mainPanel;
    private JButton button;
    private JDialog customModal;

    private Client client;
    private ServerResponseListener serverResponseListener;

    public GUI(Client client, ServerResponseListener serverResponseListener) {
        this.client = client;
        this.serverResponseListener = serverResponseListener;
        this.serverResponseListener.setGui(this);
    }

    public void init() {
        initMainFrame();
        initMainPanel();
        addButtons();
        mainFrame.pack();
        centerComponent(mainFrame, null);
        mainFrame.setVisible(true);
        if (!serverResponseListener.isMatchFound())
            showNotifModal(serverResponseListener, customModal, mainFrame,
                    "", "Waiting for opponent...", false, false, "no_opponent");
    }

    private void initMainFrame() {
        mainFrame = new JFrame();
        mainFrame.setPreferredSize(new Dimension(width, height));
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initMainPanel() {
        mainPanel = new JPanel(new GridLayout(3, 3));
        mainPanel.setBackground(bgColor);
        mainFrame.add(mainPanel);
    }

    private static void centerComponent(Component component, Component parent) {
        Component componentBase = parent != null ? parent : component;
        if (parent != null) {
            int xPosition = (int) (componentBase.getX()) + (int) (component.getSize().getWidth() / 2);
            int yPosition = (int) (componentBase.getY()) + (int) (component.getSize().getHeight() / 2);
            component.setLocation(xPosition, yPosition);
            return;
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xPosition = (int) (screenSize.getWidth() / 2) - (int) (component.getSize().getWidth() / 2);
        int yPosition = (int) (screenSize.getHeight() / 2) - (int) (component.getSize().getHeight() / 2);
        component.setLocation(xPosition, yPosition);
    }

    private void addButtons() {

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                mainPanel.add(initButton(new BtnListeners(row, col, serverResponseListener.getBtnImgMode(), client,
                        serverResponseListener.getMatchId(), serverResponseListener), row + "x" + col));
            }
        }
    }

    private JButton initButton(ActionListener actionListener, String buttonName) {
        button = new JButton();
        button.setName(buttonName);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder((bgColor), 5));
        button.setBackground(accentColor);
        button.addActionListener(actionListener);
        this.serverResponseListener.buttons.put(buttonName, button);
        return button;
    }

    public static void showNotifModal(ServerResponseListener serverResponseListener, JDialog customModal, JFrame parent,
            String title, String message, boolean enableCloseButton, boolean baseToParent, String name) {
        customModal = new JDialog(parent, title, true);
        if (!enableCloseButton)
            customModal.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        else {
            customModal.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            customModal.addWindowListener(customModalWindowListener(parent));
        }
        customModal.setSize(300, 200);
        JLabel label = new JLabel(message);
        label.setHorizontalAlignment(JLabel.CENTER);
        customModal.getContentPane().add(label);
        if (baseToParent)
            centerComponent(customModal, parent);
        else
            centerComponent(customModal, null);

        serverResponseListener.dialogs.put(name, customModal);
        customModal.setVisible(true);
    }

    public static void showNotifModal(ServerResponseListener serverResponseListener, JDialog customModal, JFrame parent,
            String title, String message, boolean enableCloseButton, boolean baseToParent, String name,
            boolean isDispose) {
        customModal = new JDialog(parent, title, true);
        if (!enableCloseButton)
            customModal.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        else {
            customModal.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            if (isDispose) {
                customModal.addWindowListener(customModalWindowListener(parent));
            }
        }
        customModal.setSize(300, 200);
        JLabel label = new JLabel(message);
        label.setHorizontalAlignment(JLabel.CENTER);
        customModal.getContentPane().add(label);
        if (baseToParent)
            centerComponent(customModal, parent);
        else
            centerComponent(customModal, null);

        serverResponseListener.dialogs.put(name, customModal);
        customModal.setVisible(true);
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public JDialog getCustomModal() {
        return customModal;
    }

    private static WindowListener customModalWindowListener(JFrame parent) {
        WindowListener windowListener = new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowClosing(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowClosed(WindowEvent e) {
                // TODO Auto-generated method stub
                parent.dispose();
                System.exit(1);
            }

            @Override
            public void windowIconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowActivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }

        };

        return windowListener;
    }
}
