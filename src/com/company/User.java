package com.company;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class User {
    private String username = "";
    private String hostname = "";
    private InetAddress userIP = null;
    private int port = 0;

    public User(String username) {
        this.username = username;
        hostname = "localhost";
        port = 4000;

        try {
            userIP = InetAddress.getByName(hostname);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() { return username; }
    public String getHostname() { return hostname; }
    public InetAddress getUserIP() { return userIP; }
    public int getPort() { return port; }

}
