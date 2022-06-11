package server.listeners;

import server.ClientSocketService;
import server.Match;
import server.Server;

public class ClientRequestListener {
    public void setMove(String clientRequest, ClientSocketService client) {
        // clientRequest = row,col,<matchId>
        long matchId = Long.parseLong(clientRequest.split(",")[2].replaceAll("<", "").replaceAll(">", ""));
        String move = clientRequest.substring(0, clientRequest.indexOf("<"));
        Match match = (Match) Server.matches.get(matchId);
        if (match == null)
            return;

        boolean moveStatus = false;
        if (moveStatus = match.setMove(client, move))
            System.out.println("Move executed successfully");
        else {
            System.out.println("It's not the players turn");
        }
        client.sendResponse("move_status=" + moveStatus);
    }
}
