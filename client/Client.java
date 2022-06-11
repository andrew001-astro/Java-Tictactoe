package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import client.listeners.BtnListeners;
import client.listeners.ServerResponseListener;

public class Client {
    private Socket client;
    private String address = "localhost";
    private int port = 8138;
    private BufferedReader in;
    private PrintWriter out;
    private ServerResponseListener serverResponseListener;

    public Client(ServerResponseListener serverResponseListener) {
        try {
            client = new Socket(address, port);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
            this.serverResponseListener = serverResponseListener;
            new Thread(readResponse()).start();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private Runnable readResponse() {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    String responseLine = "";
                    while (!(responseLine = in.readLine()).equals("")) {
                        System.out.println("SERVER RESPONSE: " + responseLine);
                        if (responseLine.contains("btn_img_mode"))
                            serverResponseListener.setBtnImgMode(Integer.parseInt(responseLine.split("=")[1]));
                        if (responseLine.contains("match_id"))
                            serverResponseListener.setMatchId(Integer.parseInt(responseLine.split("=")[1]));
                        if (responseLine.contains("move_status")) {
                            serverResponseListener.setMoveStatus(Boolean.parseBoolean(responseLine.split("=")[1]));
                            if (Boolean.parseBoolean(responseLine.split("=")[1])) {
                                serverResponseListener.setTurn(false);
                            }
                        }
                        if (responseLine.contains("match_found"))
                            serverResponseListener.setMatchFound(Boolean.parseBoolean(responseLine.split("=")[1]));
                        if (responseLine.contains("is_turn"))
                            serverResponseListener.setTurn(Boolean.parseBoolean(responseLine.split("=")[1]));
                        if (responseLine.contains("opponent_move"))
                            serverResponseListener.setOpponentMove(responseLine.split("=")[1]);
                        if (responseLine.contains("isWin"))
                            serverResponseListener.setIsWin(responseLine.split("=")[1]);
                        if (responseLine.contains("is_draw"))
                            serverResponseListener.setDraw(Boolean.parseBoolean(responseLine.split("=")[1]));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        };

        return runnable;

    }

    public void sendRequest(String request) {
        out.println(request);
        System.out.println("YOUR REQUEST: " + request);
    }
}
