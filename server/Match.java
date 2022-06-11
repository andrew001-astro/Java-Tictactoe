package server;

import java.util.Random;

import server.listeners.ClientRequestListener;

public class Match {
    private Player[] players = new Player[2];
    private Player playerInTurn = null;
    private long MatchId;
    private ClientRequestListener clientRequestListener;
    private int moveCount = 0;

    public Match(long matchId, ClientSocketService client) {
        this.clientRequestListener = new ClientRequestListener();
        client.setClientRequestListener(clientRequestListener);
        this.MatchId = matchId;
        players[0] = new Player(client);
        players[0].setBtnImgMode(new Random().nextInt(2));
        client.sendResponse("btn_img_mode=" + players[0].getBtnImgMode());
        client.sendResponse("is_turn=true");
        playerInTurn = players[0];
    }

    public long getMatchId() {
        return MatchId;
    }

    public void setMatchId(long matchId) {
        MatchId = matchId;
    }

    public Player getPlayerInTurn() {
        return playerInTurn;
    }

    public void setPlayerInTurn(Player player) {
        this.playerInTurn = player;
    }

    public boolean addPlayer(ClientSocketService client) {
        int playerCount = 0;
        boolean isAdded = false;
        for (int i = 0; i < players.length; i++) {
            playerCount++;
            if (players[i] == null) {
                client.setClientRequestListener(clientRequestListener);
                players[i] = new Player(client);
                players[i].setBtnImgMode(players[0].getBtnImgMode() > 0 ? 0 : 1);
                client.sendResponse("btn_img_mode=" + players[i].getBtnImgMode());
                return isAdded = true;
            }
        }
        if (playerCount >= 2)
            System.out.println("Matched filled with 2 players.");

        return isAdded;
    }

    public boolean setMove(ClientSocketService client, String move) {
        boolean hasMoved = false;
        if (players[1] == null) {
            System.out.println("No opponent is present");
            client.sendResponse("No opponent is present");
            return false;
        }

        Player currentPlayer = null;
        for (Player player : players) {
            if (client.equals(player.getClient())) {
                currentPlayer = player;
            }
        }

        int row = Integer.parseInt(move.split(",")[0]);
        int col = Integer.parseInt(move.split(",")[1]);
        if (playerInTurn.equals(currentPlayer)) {
            String moveResponse = currentPlayer.setMove(row, col);
            if (moveResponse != null && moveResponse.equals("win")) {
                currentPlayer.getClient().sendResponse("isWin=true");
                Server.matches.remove(MatchId);
            }
            for (Player _player : players) {
                if (!_player.equals(playerInTurn)) {
                    if (moveResponse != null && moveResponse.equals("win")) {
                        _player.getClient().sendResponse("isWin=false");
                    }
                    setPlayerInTurn(_player);
                    _player.getClient().sendResponse("is_turn=true");
                    _player.getClient().sendResponse("opponent_move=" + row + "x" + col);
                    break;
                }
            }

            hasMoved = true;
        }

        moveCount++;
        checkIfDraw();
        return hasMoved;
    }

    private void checkIfDraw() {
        if (moveCount >= 9) {
            for (Player player : players) {
                player.getClient().sendResponse("is_draw=true");
            }
        }
    }

    public Player[] getPlayers() {
        return players;
    }
}