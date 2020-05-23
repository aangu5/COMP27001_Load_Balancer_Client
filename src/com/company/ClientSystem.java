package com.company;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientSystem extends Thread {

    Server mainNode;
    Client thisClient;

    public ClientSystem(int clientPort, InetAddress serverIP, int serverPort) {
        mainNode = new Server(serverIP, serverPort);
        thisClient = new Client(clientPort);
        run();
    }

    @Override
    public void run() {
        String messageToSend = "NEW," + thisClient.getNodeIPAddress().getHostAddress() + "," + thisClient.getNodePort();
        mainNode.sendMessageToServer(messageToSend);
        System.out.println("Main Thread: Message sent");

        while (true) {
            String messageReceived = awaitMessageFromServer();
            System.out.println("Main Thread: " + messageReceived);
            String[] elements = messageReceived.split(",".trim());
            if (elements[0].trim().equals("ACCEPTED")) {
                messageToSend = "READY," + thisClient.getNodeIPAddress().getHostAddress() + "," + thisClient.getNodePort();
                mainNode.sendMessageToServer(messageToSend);
            }
            if (elements[0].equals("WORK")) {
                int tempWorkID = Integer.parseInt(elements[1].trim());
                int tempWorkDuration = Integer.parseInt(elements[2].trim());
                Work newWork = new Work(tempWorkID, tempWorkDuration, mainNode);
                newWork.start();
            }
        }
    }

    private String awaitMessageFromServer() {
        try {
            byte buffer[] = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            DatagramSocket socket = new DatagramSocket(thisClient.getNodePort());
            socket.setSoTimeout(0);
            socket.receive(packet);
            String outputText = new String(buffer);
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
        mainNode.sendMessageToServer(message);
        return true;
    }
}
