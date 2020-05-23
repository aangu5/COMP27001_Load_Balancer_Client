package com.company;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Sender {

    public static void main(String[] args){

        if(args.length != 3) {
            System.out.println("That is not enough arguments - Sender <client port> <server ip address> <server port>");
        } else {
            int clientPort = Integer.parseInt(args[0].trim());
            InetAddress tempIP = null;
            try {
                tempIP = InetAddress.getByName(InetAddress.getByName(args[1]).getHostAddress());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            int tempPort = Integer.parseInt(args[2].trim());
            ClientSystem client = new ClientSystem(clientPort,tempIP,tempPort);
            //client.serverOnline();
        }
    }
}
