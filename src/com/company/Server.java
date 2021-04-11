package com.company;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    private InetAddress serverIPAddress;
    private int serverPort;

    public Server(InetAddress inputIP, int inputPort) {
        serverIPAddress = inputIP;
        serverPort = inputPort;
    }

    public void sendMessageToServer(String content) {
        try {
            DatagramPacket packet = new DatagramPacket(content.getBytes(), content.getBytes().length, serverIPAddress, serverPort);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
