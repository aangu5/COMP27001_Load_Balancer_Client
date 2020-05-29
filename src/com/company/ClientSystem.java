package com.company;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientSystem extends Thread {

    Server mainNode;
    Client thisClient;

    public ClientSystem(int clientPort, InetAddress serverIP, int serverPort, int jobLimit) {
        mainNode = new Server(serverIP, serverPort);
        thisClient = new Client(clientPort, jobLimit);
    }

    @Override
    public void run() {
        String messageToSend = "NEW," + thisClient.getNodeIPAddress().getHostAddress() + "," + thisClient.getNodePort() + "," + thisClient.getMaxJobs();
        mainNode.sendMessageToServer(messageToSend);
        System.out.println("Main Thread: Message sent");

        while (true) {
            String messageReceived = awaitMessageFromServer();
            System.out.println("Main Thread: " + messageReceived);
            String[] elements = messageReceived.split(",".trim());
            String inputMessage = elements[0].trim();
            switch (inputMessage) {
                case "ACCEPTED" -> {
                    messageToSend = "READY";
                    mainNode.sendMessageToServer(messageToSend);
                }
                case "WORK" -> {
                    int tempWorkID = Integer.parseInt(elements[1].trim());
                    int tempWorkDuration = Integer.parseInt(elements[2].trim());
                    Work newWork = new Work(tempWorkID, tempWorkDuration, mainNode, thisClient);
                    thisClient.newJob();
                    newWork.start();
                }
                case "SHUTDOWN" -> {
                    System.out.println("Main Thread: Server has issued a SHUTDOWN command, trying to exit the program.");
                    System.exit(0);
                }
                case "STATUSCHECK" -> {
                    if (thisClient.getIsWorking()) {
                        messageToSend = "WORKING," + thisClient.getNodeIPAddress().getHostAddress() + "," + thisClient.getNodePort() + "," + thisClient.getCurrentJobs() + "," + thisClient.getMaxJobs() + " ";
                    } else {
                        messageToSend = "ALIVE," + thisClient.getNodeIPAddress().getHostAddress() + "," + thisClient.getNodePort();
                    }
                    System.out.println("Message to send is " + messageToSend);
                    mainNode.sendMessageToServer(messageToSend);
                }
                default -> System.out.println("Unknown message received: " + inputMessage);
            }
        }
    }

    private String awaitMessageFromServer() {
        try {
            byte[] buffer = new byte[1024];
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
}
