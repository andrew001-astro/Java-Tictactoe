package client.listeners;

import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import client.GUI;

public class ServerResponseListener {
    private int btnImgMode = 0;
    private int matchId = 0;
    private boolean moveStatus = false;
    private boolean matchFound = false;
    private boolean isTurn = false;
    public HashMap buttons = new HashMap<String, JButton>();
    private String opponentMove = "";
    public HashMap<String, JDialog> dialogs = new HashMap<String, JDialog>();
    public String isWin;
    private GUI gui;
    private boolean isDraw = false;

    public GUI getGui() {
        return gui;
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public String getIsWin() {
        return isWin;
    }

    public void setIsWin(String isWin) {
        this.isWin = isWin;
        if (Boolean.parseBoolean(isWin))
            GUI.showNotifModal(this, gui.getCustomModal(), gui.getMainFrame(),
                    "Winner", "Congrats! you are the winner", true, true, "winner");
        else
            GUI.showNotifModal(this, gui.getCustomModal(), gui.getMainFrame(),
                    "You lose", "Do better next time.", true, true, "winner");
    }

    public String getOpponentMove() {
        return opponentMove;
    }

    public void setOpponentMove(String opponentMove) {
        this.opponentMove = opponentMove;

        BtnListeners.setButtonIcon((JButton) buttons.get(opponentMove), btnImgMode > 0 ? 0 : 1);
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }

    public boolean isMatchFound() {
        return matchFound;
    }

    public void setMatchFound(boolean matchFound) {
        this.matchFound = matchFound;
        if (matchFound) {
            if (dialogs.get("no_opponent") != null)
                dialogs.get("no_opponent").dispose();
        }
    }

    public boolean getMoveStatus() {
        return moveStatus;
    }

    public void setMoveStatus(boolean moveStatus) {
        this.moveStatus = moveStatus;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getBtnImgMode() {
        return btnImgMode;
    }

    public void setBtnImgMode(int btnImgMode) {
        this.btnImgMode = btnImgMode;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean isDraw) {
        this.isDraw = isDraw;
        if (isDraw)
            GUI.showNotifModal(this, gui.getCustomModal(), gui.getMainFrame(),
                    "Draw", "Oops! Its a draw", true, true, "draw");
    }

}
