package client.listeners;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import client.Client;
import client.GUI;
import client.Main;

public class BtnListeners implements ActionListener {
    private int row;
    private int col;
    private int btnImgMode;
    private Client client;
    private long matchId;
    private ServerResponseListener serverResponseListener;

    public BtnListeners(int row, int col, int btnImgMode, Client client, long matchId,
            ServerResponseListener serverResponseListener) {
        this.row = row;
        this.col = col;
        this.btnImgMode = btnImgMode;
        this.matchId = matchId;

        this.client = client;

        this.serverResponseListener = serverResponseListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        if (!serverResponseListener.isMatchFound()) {
            System.out.println("No opponent found yet");
            return;
        }

        if (!serverResponseListener.isTurn()) {
            System.out.println("It's not your turn yet");
            GUI.showNotifModal(serverResponseListener, Main.getGui().getCustomModal(), Main.getGui().getMainFrame(),
                    "Turn", "It's not your turn yet, please wait...", true, true, "turn", false);
            return;
        }

        if (button.getName().contains("disabled")) {
            return;
        }

        client.sendRequest(String.format("move=%d,%d,<%d>", row, col, matchId));
        setButtonIcon(button, btnImgMode);
    }

    public static void setButtonIcon(JButton button, int btnImgMode) {
        String btnImgPath = "../assets/circle.png";
        if (btnImgMode > 0)
            btnImgPath = "../assets/cross.png";
        else
            btnImgPath = "../assets/circle.png";
        Image iconImg = new ImageIcon(BtnListeners.class.getResource(btnImgPath)).getImage();
        iconImg = iconImg.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon btnIcon = new ImageIcon(iconImg);

        button.setIcon(btnIcon);
        button.setName("disabled");
    }

}
