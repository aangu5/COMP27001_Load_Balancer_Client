package com.company;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientSystem {

    Server mainNode;
    Client thisClient;

    public ClientSystem(int clientPort, InetAddress serverIP, int serverPort) {
        mainNode = new Server(serverIP, serverPort);
        thisClient = new Client(clientPort);

        String messageToSend = "NEW,"+ thisClient.getNodeIPAddress().getHostAddress() + ", " + thisClient.getNodePort();
        mainNode.sendMessageToServer(messageToSend,thisClient);
        System.out.println(awaitMessageFromServer());
    }

    private String awaitMessageFromServer() {
        try {
            byte buffer[] = new byte[1024];

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            DatagramSocket socket = new DatagramSocket(15000);
            socket.setSoTimeout(0);
            socket.receive(packet);
            String outputText = String.valueOf(buffer);
            socket.close();
            return outputText;
        } catch (Exception error) {
            error.printStackTrace();
            return "Error!";
        }
    }

    public boolean serverOnline() throws UnknownHostException {
        InetAddress address = InetAddress.getByName("localhost");
        String message = "REGISTER, node2, 192.168.1.2,6001";
        mainNode.sendMessageToServer(message, thisClient);
        return true;
    }
}
