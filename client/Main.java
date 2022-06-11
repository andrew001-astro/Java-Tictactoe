package client;

import client.listeners.ServerResponseListener;

public class Main {

    private static GUI gui;

    public static void main(String[] args) {
        ServerResponseListener serverResponseListener = new ServerResponseListener();
        gui = new GUI(new Client(serverResponseListener), serverResponseListener);
        gui.init();
    }

    public static GUI getGui() {
        return gui;
    }

}
