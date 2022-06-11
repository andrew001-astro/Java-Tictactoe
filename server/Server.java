package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private int port = 8138;
    private ServerSocket serverSocket;
    public static HashMap<Long, Match> matches = new HashMap<Long, Match>();
    private static long client_id_seq = 0;
    private static long match_id_seq = 0;
    private int thread_pool_size = 10;
    private ExecutorService threadpool;

    public Server() {
        threadpool = Executors.newFixedThreadPool(thread_pool_size);
        initServer();
    }

    private void initServer() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Listening to port " + port);

            while (true) {
                Socket client_socket = serverSocket.accept();
                client_id_seq++;
                checkForMatch(addClient(client_id_seq, client_socket));
                System.out.println("Connection established for client " + client_id_seq + " ...");
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private ClientSocketService addClient(long client_id, Socket client_socket) {
        ClientSocketService clientSocketService = new ClientSocketService(client_socket);
        this.threadpool.submit(clientSocketService);
        return clientSocketService;
    }

    private void checkForMatch(ClientSocketService client) {
        for (Object match : matches.values()) {
            if (((Match) match).addPlayer(client)) {
                client.sendResponse("match_id=" + ((Match) match).getMatchId());
                for (Player player : ((Match) match).getPlayers()) {
                    player.getClient().sendResponse("Match Found...");
                    player.getClient().sendResponse("match_found=true");
                }
                return;
            }
        }
        Match match = new Match(match_id_seq, client);
        matches.put(match.getMatchId(), match);
        client.sendResponse("Match Created, waiting for opponent...");
        client.sendResponse("match_id=" + match.getMatchId());
        match_id_seq++;
    }
}
