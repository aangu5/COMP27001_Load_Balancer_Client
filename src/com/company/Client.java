package com.company;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
    private InetAddress nodeIPAddress = null;
    private int nodePort = 0;

    public Client(int inputPort) {
        try {
            nodeIPAddress = InetAddress.getLocalHost();
            nodePort = inputPort;
        } catch (UnknownHostException error) {
            error.printStackTrace();
        }
    }

    public InetAddress getNodeIPAddress() { return nodeIPAddress; }
    public int getNodePort() { return nodePort; }
}
