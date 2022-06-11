package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import server.listeners.ClientRequestListener;

public class ClientSocketService implements Runnable {
    private Socket client_socket;
    private BufferedReader in;
    private PrintWriter out;
    private ClientRequestListener clientRequestListener;
    private Thread readRequestThread;

    public ClientRequestListener getClientRequestListener() {
        return clientRequestListener;
    }

    public void setClientRequestListener(ClientRequestListener clientRequestListener) {
        this.clientRequestListener = clientRequestListener;
    }

    public ClientSocketService(Socket client_socket) {
        try {
            this.client_socket = client_socket;
            in = new BufferedReader(new InputStreamReader(this.client_socket.getInputStream()));
            out = new PrintWriter(this.client_socket.getOutputStream(), true);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            System.out.println("Client thread established...");
            sendResponse("You are now connected to server...");
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    System.out.println(readRequest());
                }
            };
            readRequestThread = new Thread(runnable);
            readRequestThread.start();

            ticker();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private String readRequest() {
        String inputLine = "";
        try {
            while (!(inputLine = in.readLine()).equals("")) {
                System.out.println("CLIENT REQUEST: " + inputLine);
                sendResponse("Your move: " + inputLine);

                if (inputLine.contains("move"))
                    clientRequestListener.setMove(inputLine.split("=")[1], this);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return inputLine;
    }

    public void sendResponse(String response) {
        out.println(response);
        System.out.println("response sent...");
        System.out.println("RESPONSE MESSAGE: " + response);
    }

    private void ticker() {
        System.out.println("Starting timer");
        Timer timer = new Timer();
        int interval = 5000;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                System.out.println(new Date().toString() + ": tick...");
            }

        }, 0, interval);
    }
}
