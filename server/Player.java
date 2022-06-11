package server;

public class Player {
    private ClientSocketService client;
    private int btnImgMode = 0;

    public int getBtnImgMode() {
        return btnImgMode;
    }

    public void setBtnImgMode(int btnImgMode) {
        this.btnImgMode = btnImgMode;
    }

    public ClientSocketService getClient() {
        return client;
    }

    private Tictactoe tictactoe;

    public Player(ClientSocketService client) {
        this.client = client;
        tictactoe = new Tictactoe();
    }

    public String setMove(int row, int col) {
        String response = null;
        this.tictactoe.setMark(row, col);

        if (this.tictactoe.checkMarks() >= 3) {
            client.sendResponse("YOU ARE THE WINNER");
            response = "win";
        }
        return response;
    }
}
