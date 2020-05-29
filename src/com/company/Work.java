package com.company;

import java.util.concurrent.TimeUnit;

public class Work extends Thread {
    private int workID;
    private int duration;
    private Client thisNode;
    private Server mainNode;

    public Work(int workID, int inputDuration, Server inputServer, Client inputClient){
        try {
            thisNode = inputClient;
            mainNode = inputServer;
            this.workID = workID;
            duration = inputDuration;
            System.out.println("Work Thread: Work created! ID: " + workID + ", duration: " + duration);
        } catch (Exception e) {
            System.out.println("Input not recognised: " + e.getMessage());
        }
    }

    private void workComplete() {
        String messageToSend = "COMPLETE," + workID;
        mainNode.sendMessageToServer(messageToSend);
        thisNode.jobCompleted();
    }

    @Override
    public void run() {
        for (int i = 0; i < duration; i++){
            System.out.println("Work Thread: Task " + workID + " progress " + i + "/" + duration);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Work Thread: Task " + workID + " is complete");
        workComplete();
    }
}
